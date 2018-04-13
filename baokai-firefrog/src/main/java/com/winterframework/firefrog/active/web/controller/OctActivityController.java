package com.winterframework.firefrog.active.web.controller;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.winterframework.firefrog.active.dao.IActivityConfigDao;
import com.winterframework.firefrog.active.dao.IActivityLogDao;
import com.winterframework.firefrog.active.dao.vo.ActivityConfig;
import com.winterframework.firefrog.active.dao.vo.ActivityLog;
import com.winterframework.firefrog.active.service.IOctActivityService;
import com.winterframework.firefrog.active.web.dto.OctActivity4Response;
import com.winterframework.firefrog.active.web.dto.OctActivityResponse;
import com.winterframework.firefrog.active.web.dto.WebOrMbRequest;
import com.winterframework.firefrog.common.redis.RedisClient;
import com.winterframework.firefrog.common.util.DateUtils;
import com.winterframework.firefrog.common.util.Encrypter;
import com.winterframework.firefrog.fund.service.IFundChargeService;
import com.winterframework.firefrog.user.entity.User;
import com.winterframework.firefrog.user.service.IUserProfileService;
import com.winterframework.modules.spring.exetend.PropertyConfig;
import com.winterframework.modules.web.jsonresult.Request;
import com.winterframework.modules.web.jsonresult.Response;

@Controller
@RequestMapping(value = "/activity/octActivity")
public class OctActivityController {

	private static final Logger log = LoggerFactory.getLogger(OctActivityController.class);	
	
	@Resource(name="RedisClient")
	private RedisClient redisClient;
	
	@Resource(name = "activityConfigDaoImpl")
	private IActivityConfigDao activityConfigDaoImpl;
	
	@Resource(name = "activityLogDaoImpl")
	private IActivityLogDao activityLogDaoImpl;
	
	@Resource(name = "octActivityServiceImpl")
	private IOctActivityService octActivityServiceImpl;
	
	@Resource(name ="userProfileServiceImpl")
	private IUserProfileService userProfileService;
	
	@Resource(name = "userProfileServiceImpl")
	private IUserProfileService userServcie;
	
	@Resource(name = "fundChargeServiceImpl")
	private IFundChargeService fundChargeServiceImpl;
	
	@PropertyConfig(value="encrypter.Key")
	protected String encrypterKey;
	
	@PropertyConfig(value="encrypter.value")
	protected String encrypterValue;
	
	public static final Long OCT_ACTIVITY_CONFID_ID = 9l;
	public static final Long OCT_ACTIVITY_2_CONFID_ID = 11l;
	public static final Long OCT_ACTIVITY_3_CONFID_ID = 12l;
	public static final Long OCT_ACTIVITY_4_CONFID_ID = 13l;
	public static final Long OCT_ACTIVITY_4_ADDR_CONFID_ID = 14l;
	
	
	@RequestMapping(value = "/info")
	public @ResponseBody
	Response<OctActivityResponse> octActivityInfo(@RequestBody Request<WebOrMbRequest> request) throws Exception{
		
		WebOrMbRequest channelInfo = request.getBody().getParam();
		Long userId = 0l;
		
		if(channelInfo.getUserId() != null){
			userId = channelInfo.getUserId();
		}else{
			log.info("--------- token = "+channelInfo.getToken() );
			userId = tokenGetUserId(channelInfo.getToken());
		}
		log.info("--------- userId = "+userId );
		
		Response<OctActivityResponse> response = new Response<OctActivityResponse>();
		OctActivityResponse result = new OctActivityResponse();
		
		Date todate = DateUtils.currentDate();
		ActivityConfig activityConf = activityConfigDaoImpl.getActCfgById(OCT_ACTIVITY_CONFID_ID);
		//判斷是否在活動時間開始之後
		if(todate.getTime() >= activityConf.getBeginTime().getTime()){
			//判斷是否在活動時間結束之前
			if(todate.getTime() <= activityConf.getEndTime().getTime()){
				//判斷當日是否打過鬼子
				ActivityLog isPrize = octActivityServiceImpl.isGetPrize(userId);
				if(isPrize != null ){
					String[] activityInfo = isPrize.getMemo().split(";");
					result.setLeftAmount(activityInfo[0]);
					result.setBetAmount(activityInfo[1]);
					result.setBetScale(activityInfo[2]);
					result.setBetMutile(activityInfo[3]);
					result.setPrize(activityInfo[4]);
				}else{
					BigDecimal leftAmount = null;
					//獲取有效投注量
					Long amount = octActivityServiceImpl.queryUserPeriodBets(userId);
					//獲得兌換比例
					if(amount >= 20000000l){
						//是否是vip
						boolean isVip = userProfileService.queryUserById(userId).getVipLvl() > 0;
						//兌換比例
						result.setBetScale(octActivityServiceImpl.getScale(amount,isVip));
						//有效投注量跟下一級距離差
						leftAmount =  BigDecimal.valueOf(octActivityServiceImpl.getLeftAmount(amount)).divide(BigDecimal.valueOf(10000l));
					}else{
						leftAmount = (BigDecimal.valueOf(20000000l).subtract(BigDecimal.valueOf(amount))).divide(BigDecimal.valueOf(10000l));
					}
					result.setIsGetPrize(false);
					result.setLeftAmount(leftAmount.toString());
					result.setBetAmount(BigDecimal.valueOf(amount).divide(BigDecimal.valueOf(10000l)).toString());
				}
			}
		}else{
			result.setIsSuccess(false);
		}
		response.setResult(result);
		return response;
	}
	
	@RequestMapping(value = "/shotKill")
	public @ResponseBody
	Response<OctActivityResponse> shotKill(@RequestBody Request<WebOrMbRequest> request) throws Exception{
		
		WebOrMbRequest channelInfo = request.getBody().getParam();
		Long userId = 0l;
		if(channelInfo.getUserId() != null){
			userId = channelInfo.getUserId();
		}else{
			userId = tokenGetUserId(channelInfo.getToken());
		}
		
		Response<OctActivityResponse> response = new Response<OctActivityResponse>();
		OctActivityResponse result = new OctActivityResponse();
		
		String key = new StringBuffer().append("OctActivity").append("-").append(userId).toString();
		if(redisClient.get(key)==null){
			redisClient.set(key,"1",5);
		}else{
			result.setIsSuccess(false);
			response.setResult(result);	
			return response;
		}
		
		
		Date todate = DateUtils.currentDate();
		ActivityConfig activityConf = activityConfigDaoImpl.getActCfgById(OCT_ACTIVITY_CONFID_ID);
		//判斷是否在活動時間開始之後
		if(todate.getTime() >= activityConf.getBeginTime().getTime()){
			//判斷是否在活動時間結束之前
			if(todate.getTime() <= activityConf.getEndTime().getTime()){
				//判斷當日是否打過鬼子
				ActivityLog isPrize = octActivityServiceImpl.isGetPrize(userId);
				if(isPrize != null ){
					String[] activityInfo = isPrize.getMemo().split(";");
					result.setLeftAmount(activityInfo[0]);
					result.setBetAmount(activityInfo[1]);
					result.setBetScale(activityInfo[2]);
					result.setBetMutile(activityInfo[3]);
					result.setPrize(activityInfo[4]);
				}else{
					BigDecimal leftAmount = null;
					//兌換比例
					String scale = null;
					//倍數
					String multiple = null;
					//獲得獎金
					BigDecimal prize = null;
					//重置概率
					Boolean resetMultiple = false;
					//獲取有效投注量
					Long amount = octActivityServiceImpl.queryUserPeriodBets(userId);
					//獲得兌換比例
					if(amount >= 20000000l){
						//是否是vip
						boolean isVip = userProfileService.queryUserById(userId).getVipLvl() > 0;
						scale = octActivityServiceImpl.getScale(amount,isVip);
						//有效投注量跟下一級距離差
						leftAmount =  BigDecimal.valueOf(octActivityServiceImpl.getLeftAmount(amount)).divide(BigDecimal.valueOf(10000l));
						multiple = octActivityServiceImpl.getMultiple(amount);
						//一天只會有一次 10的概率
						if(multiple.equals("10.0")){
							List<String> multipleList = octActivityServiceImpl.checkMultiple();
							for(String mList : multipleList){
								if(mList.equals("10.0")){
									resetMultiple = true;
									break;
								}
							}
							
							while (resetMultiple) {
								multiple = octActivityServiceImpl.getMultiple(amount);
								if(multiple != "10.0"){
									resetMultiple = false;
								}
							}
						}
						prize = BigDecimal.valueOf(amount).multiply(BigDecimal.valueOf(Double.valueOf(scale)/100).multiply(BigDecimal.valueOf(Double.valueOf(multiple)))).divide(BigDecimal.valueOf(10000l)) ;
						result.setLeftAmount(leftAmount.toString());
						result.setBetAmount(BigDecimal.valueOf(amount).divide(BigDecimal.valueOf(10000l)).toString());
						result.setBetScale(scale);
						result.setBetMutile(multiple);
						result.setPrize(prize.toString());
						ActivityLog log = octActivityServiceImpl.saveActivityLog(userId,OCT_ACTIVITY_CONFID_ID,result.creatParam(),
								DateUtils.getStartTimeOfCurrentDate(),DateUtils.getEndTimeOfCurrentDate());
						//已經打過鬼子
						if(log != null){
							String[] activityInfo = log.getMemo().split(";");
							result.setLeftAmount(activityInfo[0]);
							result.setBetAmount(activityInfo[1]);
							result.setBetScale(activityInfo[2]);
							result.setBetMutile(activityInfo[3]);
							result.setPrize(activityInfo[4]);
						}else{
							octActivityServiceImpl.saveFundChaneLog(userId,(prize.multiply(BigDecimal.valueOf(10000))).longValue());
						}
					}else{
						result.setBetAmount(BigDecimal.valueOf(amount).divide(BigDecimal.valueOf(10000l)).toString());
						result.setLeftAmount((BigDecimal.valueOf(20000000l).subtract(BigDecimal.valueOf(amount))).divide(BigDecimal.valueOf(10000l)).toString());
						result.setIsSuccess(false);
					}
				}
			}
		}else{
			result.setIsSuccess(false);
		}
		response.setResult(result);	
		return response;
	}
	
	@RequestMapping(value = "/oct2Info")
	public @ResponseBody
	Response<OctActivityResponse> oct2Info(@RequestBody Request<WebOrMbRequest> request) throws Exception{
		
		Response<OctActivityResponse> response = new Response<OctActivityResponse>();
		OctActivityResponse result = new OctActivityResponse();
		result.setIsSuccess(false);
		//web送userId mb送token
		WebOrMbRequest channelInfo = request.getBody().getParam();
		Long userId = 0l;
		if(channelInfo.getUserId() != null){
			userId = channelInfo.getUserId();
		}else{
			userId = tokenGetUserId(channelInfo.getToken());
		}
		
		//判斷是否是VIP_LVL 3 以上
		User user = userServcie.queryUserById(userId);
		if(user.getVipLvl() >= 3){
			result.setIsSuccess(true);
			response.setResult(result);	
			return response;
		}
		
		Date todate = DateUtils.currentDate();
		ActivityConfig activityConf = activityConfigDaoImpl.getActCfgById(OCT_ACTIVITY_2_CONFID_ID);
		//判斷是否在活動時間開始之後
		if(todate.getTime() >= activityConf.getBeginTime().getTime()){
			//判斷是否在活動時間結束之前
			if(todate.getTime() <= activityConf.getEndTime().getTime()){
				//判斷是否報名過
				ActivityLog isSignUp = octActivityServiceImpl.isSignUp(userId,OCT_ACTIVITY_2_CONFID_ID,activityConf.getBeginTime(),activityConf.getEndTime());
				//報名
				if(isSignUp != null){
					result.setIsSuccess(true);
				}
			}
		}
		
		response.setResult(result);	
		return response;
	}
	
	@RequestMapping(value = "/oct2SignUp")
	public @ResponseBody
	Response<OctActivityResponse> oct2SignUp(@RequestBody Request<WebOrMbRequest> request) throws Exception{
		
		Response<OctActivityResponse> response = new Response<OctActivityResponse>();
		OctActivityResponse result = new OctActivityResponse();
		
		//web送userId mb送token
		WebOrMbRequest channelInfo = request.getBody().getParam();
		Long userId = 0l;
		if(channelInfo.getUserId() != null){
			userId = channelInfo.getUserId();
		}else{
			userId = tokenGetUserId(channelInfo.getToken());
		}
		
		//判斷是否是VIP_LVL 3 以上
		User user = userServcie.queryUserById(userId);
		if(user.getVipLvl() >= 3){
			response.setResult(result);	
			return response;
		}
		//阻擋連續發送
		String key = new StringBuffer().append("OctActivity").append("-").append(userId).toString();
		if(redisClient.get(key)==null){
			redisClient.set(key,"1",5);
		}else{
			result.setIsSuccess(false);
			response.setResult(result);	
			return response;
		}
		
		Date todate = DateUtils.currentDate();
		ActivityConfig activityConf = activityConfigDaoImpl.getActCfgById(OCT_ACTIVITY_2_CONFID_ID);
		//判斷是否在活動時間開始之後
		if(todate.getTime() >= activityConf.getBeginTime().getTime()){
			//判斷是否在活動時間結束之前
			if(todate.getTime() <= activityConf.getEndTime().getTime()){
				//判斷是否報名過
				ActivityLog isSignUp = octActivityServiceImpl.isSignUp(userId,OCT_ACTIVITY_2_CONFID_ID,activityConf.getBeginTime(),activityConf.getEndTime());
				//報名
				if(isSignUp == null){
					log.info("---------十月活動第二週報名   userId = "+userId);
					octActivityServiceImpl.saveActivityLog(userId,OCT_ACTIVITY_2_CONFID_ID,"十月活動-今秋十月-鸿运当头",
							activityConf.getBeginTime(),activityConf.getEndTime());
				}
			}else{
				result.setIsSuccess(false);
			}
		}else{
			result.setIsSuccess(false);
		}
		
		response.setResult(result);	
		return response;
	}
	
	@RequestMapping(value = "/oct3Info")
	public @ResponseBody
	Response<OctActivityResponse> oct3Info(@RequestBody Request<WebOrMbRequest> request) throws Exception{
		
		Response<OctActivityResponse> response = new Response<OctActivityResponse>();
		OctActivityResponse result = new OctActivityResponse();
		result.setIsSuccess(false);
		//web送userId mb送token
		WebOrMbRequest channelInfo = request.getBody().getParam();
		Long userId = 0l;
		if(channelInfo.getUserId() != null){
			userId = channelInfo.getUserId();
		}else{
			userId = tokenGetUserId(channelInfo.getToken());
		}
		
		//判斷是否是VIP_LVL 3 以上
		User user = userServcie.queryUserById(userId);
		if(user.getVipLvl() >= 3){
			result.setIsSuccess(true);
			response.setResult(result);	
			return response;
		}
		
		Date todate = DateUtils.currentDate();
		ActivityConfig activityConf = activityConfigDaoImpl.getActCfgById(OCT_ACTIVITY_3_CONFID_ID);
		//判斷是否在活動時間開始之後
		if(todate.getTime() >= activityConf.getBeginTime().getTime()){
			//判斷是否在活動時間結束之前
			if(todate.getTime() <= activityConf.getEndTime().getTime()){
				//判斷是否報名過
				ActivityLog isSignUp = octActivityServiceImpl.isSignUp(userId,OCT_ACTIVITY_3_CONFID_ID,activityConf.getBeginTime(),activityConf.getEndTime());
				//報名
				if(isSignUp != null){
					result.setIsSuccess(true);
				}
			}
		}
		
		response.setResult(result);	
		return response;
	}
	
	@RequestMapping(value = "/oct3SignUp")
	public @ResponseBody
	Response<OctActivityResponse> oct3SignUp(@RequestBody Request<WebOrMbRequest> request) throws Exception{
		
		Response<OctActivityResponse> response = new Response<OctActivityResponse>();
		OctActivityResponse result = new OctActivityResponse();
		
		//web送userId mb送token
		WebOrMbRequest channelInfo = request.getBody().getParam();
		Long userId = 0l;
		if(channelInfo.getUserId() != null){
			userId = channelInfo.getUserId();
		}else{
			userId = tokenGetUserId(channelInfo.getToken());
		}

		//判斷是否是VIP_LVL 3 以上
		User user = userServcie.queryUserById(userId);
		if(user.getVipLvl() >= 3){
			response.setResult(result);	
			return response;
		}
		
		//阻擋連續發送
		String key = new StringBuffer().append("OctActivity").append("-").append(userId).toString();
		if(redisClient.get(key)==null){
			redisClient.set(key,"1",5);
		}else{
			result.setIsSuccess(false);
			response.setResult(result);	
			return response;
		}
		
		Date todate = DateUtils.currentDate();
		ActivityConfig activityConf = activityConfigDaoImpl.getActCfgById(OCT_ACTIVITY_3_CONFID_ID);
		//判斷是否在活動時間開始之後
		if(todate.getTime() >= activityConf.getBeginTime().getTime()){
			//判斷是否在活動時間結束之前
			if(todate.getTime() <= activityConf.getEndTime().getTime()){
				//判斷是否報名過
				ActivityLog isSignUp = octActivityServiceImpl.isSignUp(userId,OCT_ACTIVITY_3_CONFID_ID,activityConf.getBeginTime(),activityConf.getEndTime());
				//報名
				if(isSignUp == null){
					log.info("---------十月活動第三週報名   userId = "+userId);
					octActivityServiceImpl.saveActivityLog(userId,OCT_ACTIVITY_3_CONFID_ID,"十月活動-不废话一起壕",
							activityConf.getBeginTime(),activityConf.getEndTime());
				}
			}else{
				result.setIsSuccess(false);
			}
		}else{
			result.setIsSuccess(false);
		}
		
		response.setResult(result);	
		return response;
	}
	
	
	@RequestMapping(value = "/oct4Info")
	public @ResponseBody
	Response<OctActivity4Response> oct4Info(@RequestBody Request<WebOrMbRequest> request) throws Exception{
		
		Response<OctActivity4Response> response = new Response<OctActivity4Response>();
		OctActivity4Response result = new OctActivity4Response();
		//web送userId mb送token
		WebOrMbRequest channelInfo = request.getBody().getParam();
		Long userId = 0l;
		if(channelInfo.getUserId() != null){
			userId = channelInfo.getUserId();
		}else{
			userId = tokenGetUserId(channelInfo.getToken());
		}
		
		Date todate = DateUtils.currentDate();
		ActivityConfig activityConf = activityConfigDaoImpl.getActCfgById(OCT_ACTIVITY_4_CONFID_ID);
		ActivityLog isSignUp = null;
		//判斷是否是VIP_LVL 3 以上
		User user = userServcie.queryUserById(userId);
		if(user.getVipLvl() >= 3){
			result.setSignUp("1");
			isSignUp = new ActivityLog();
		}else{
			//判斷是否報名過
			isSignUp = octActivityServiceImpl.isSignUp(userId,OCT_ACTIVITY_4_CONFID_ID,DateUtils.getStartDateTimeOfMonth(2016,10),activityConf.getEndTime());
			if(isSignUp != null){
				result.setSignUp("1");
			}
		}
		
		//判斷是否在活動時間開始之後
		if(todate.getTime() >= activityConf.getBeginTime().getTime()){
			//判斷是否在活動時間結束之前
			if(todate.getTime() <= activityConf.getEndTime().getTime()){
				
				//今日充值金額
				Long charge = null;
				if(isSignUp != null){
					charge = fundChargeServiceImpl.queryPeriodChargeSum(userId,DateUtils.getStartTimeOfCurrentDate(),DateUtils.getEndTimeOfCurrentDate());
				}
				
				//今日有效投注
				Long amount = octActivityServiceImpl.queryUserPeriodBets(userId);
				
				if(charge != null){
					//今日可獲得禮金
					String prize = octActivityServiceImpl.getPrize(charge,amount,user.getVipLvl() > 0);
					result.setTodayDeposit(longToString(charge));
					result.setTodayPrize(prize);
				}else{
					result.setTodayDeposit("0");
					result.setTodayPrize("0");
				}
				//累積充值
				Long totcharge = null;
				if(isSignUp != null){
					totcharge = fundChargeServiceImpl.queryPeriodChargeSum(userId,activityConf.getBeginTime(),activityConf.getEndTime());
				}
				//是否輸入過地址
				ActivityLog isAddr = octActivityServiceImpl.isSignUp(userId,OCT_ACTIVITY_4_ADDR_CONFID_ID,activityConf.getBeginTime(),activityConf.getEndTime());
				if(isAddr != null){
					result.setIsAddr("1");
				}
				result.setTodayBets(longToString(amount));
				result.setDepositCount(longToString(totcharge == null? 0 : totcharge));
			}
		}
		
		response.setResult(result);	
		return response;
	}
	
	@RequestMapping(value = "/oct4SignUp")
	public @ResponseBody
	Response<OctActivityResponse> oct4SignUp(@RequestBody Request<WebOrMbRequest> request) throws Exception{
		
		Response<OctActivityResponse> response = new Response<OctActivityResponse>();
		OctActivityResponse result = new OctActivityResponse();
		
		//web送userId mb送token
		WebOrMbRequest channelInfo = request.getBody().getParam();
		Long userId = 0l;
		if(channelInfo.getUserId() != null){
			userId = channelInfo.getUserId();
		}else{
			userId = tokenGetUserId(channelInfo.getToken());
		}

		//判斷是否是VIP_LVL 3 以上
		User user = userServcie.queryUserById(userId);
		if(user.getVipLvl() >= 3){
			response.setResult(result);	
			return response;
		}
		
		//阻擋連續發送
		String key = new StringBuffer().append("OctActivity").append("-").append(userId).toString();
		if(redisClient.get(key)==null){
			redisClient.set(key,"1",5);
		}else{
			result.setIsSuccess(false);
			response.setResult(result);	
			return response;
		}
		
		Date todate = DateUtils.currentDate();
		ActivityConfig activityConf = activityConfigDaoImpl.getActCfgById(OCT_ACTIVITY_4_CONFID_ID);
		//判斷是否在活動時間結束之前
		if(todate.getTime() <= activityConf.getEndTime().getTime()){
			//報名
			log.info("---------十月活動第四週報名   userId = "+userId);
			octActivityServiceImpl.saveActivityLog(userId,OCT_ACTIVITY_4_CONFID_ID,"十月活動-RichMan",
					DateUtils.getStartDateTimeOfMonth(2016,10),activityConf.getEndTime());
		}else{
			result.setIsSuccess(false);
		}
		
		response.setResult(result);	
		return response;
	}
	
	@RequestMapping(value = "/oct4Addr")
	public @ResponseBody
	Response<OctActivityResponse> oct4Addr(@RequestBody Request<WebOrMbRequest> request) throws Exception{
		
		Response<OctActivityResponse> response = new Response<OctActivityResponse>();
		OctActivityResponse result = new OctActivityResponse();
		
		//web送userId mb送token
		WebOrMbRequest channelInfo = request.getBody().getParam();
		Long userId = 0l;
		if(channelInfo.getUserId() != null){
			userId = channelInfo.getUserId();
		}else{
			userId = tokenGetUserId(channelInfo.getToken());
		}

		//判斷是否是VIP_LVL 3 以上
		User user = userServcie.queryUserById(userId);
		if(user.getVipLvl() >= 3){
			response.setResult(result);	
			return response;
		}
		
		//阻擋連續發送
		String key = new StringBuffer().append("OctActivity").append("-").append(userId).toString();
		if(redisClient.get(key)==null){
			redisClient.set(key,"1",5);
		}else{
			result.setIsSuccess(false);
			response.setResult(result);	
			return response;
		}
		
		Date todate = DateUtils.currentDate();
		ActivityConfig activityConf = activityConfigDaoImpl.getActCfgById(OCT_ACTIVITY_4_ADDR_CONFID_ID);
		//判斷是否在活動時間結束之前
		if(todate.getTime() <= activityConf.getEndTime().getTime()){
			//報名
			log.info("---------十月活動第四週報名_ADDR   userId = "+userId);
			octActivityServiceImpl.saveActivityLog(userId,OCT_ACTIVITY_4_ADDR_CONFID_ID,"十月活動-RichMan_Addr",
					DateUtils.getStartDateTimeOfMonth(2016,10),activityConf.getEndTime());
		}else{
			result.setIsSuccess(false);
		}
		
		response.setResult(result);	
		return response;
	}
	
	private String longToString(Long amount){
		return BigDecimal.valueOf(amount).divide(BigDecimal.valueOf(10000l)).toString();
	}
	
	private Long tokenGetUserId(String token) throws Exception{
		Encrypter en = Encrypter.getInstance(encrypterKey,encrypterValue);
		String tt = en.decrypt(token);
		String[] str = tt.split("\\|");
		return userServcie.userByName(str[0]);
	}
	
}
