package com.winterframework.firefrog.active.web.controller;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.type.TypeReference;
import com.google.common.collect.Maps;
import com.winterframework.firefrog.active.dao.IActivityConfigDao;
import com.winterframework.firefrog.active.dao.IActivityLogDao;
import com.winterframework.firefrog.active.dao.IActivityRedenvelopeLogDao;
import com.winterframework.firefrog.active.dao.vo.ActivityConfig;
import com.winterframework.firefrog.active.dao.vo.ActivityRedenvelopeLog;
import com.winterframework.firefrog.active.entity.ActivityAuguest;
import com.winterframework.firefrog.active.service.IActivityVipService;
import com.winterframework.firefrog.active.service.IAuguestActivityService;
import com.winterframework.firefrog.active.web.dto.ActAuguestResponse;
import com.winterframework.firefrog.active.web.dto.ActivityRequest;
import com.winterframework.firefrog.active.web.dto.ActivityVipRequest;
import com.winterframework.firefrog.active.web.dto.DtoConverter;
import com.winterframework.firefrog.common.config.service.IConfigService;
import com.winterframework.firefrog.common.httpjsonclient.IHttpJsonClient;
import com.winterframework.firefrog.common.util.RandomVal;
import com.winterframework.firefrog.fund.service.IFundChargeService;
import com.winterframework.firefrog.fund.util.MowNumTool;
import com.winterframework.firefrog.user.dao.vo.VipActivityVo;
import com.winterframework.firefrog.user.entity.User;
import com.winterframework.firefrog.user.service.IUserProfileService;
import com.winterframework.modules.spring.exetend.PropertyConfig;
import com.winterframework.modules.web.jsonresult.Request;
import com.winterframework.modules.web.jsonresult.Response;

@Controller
@RequestMapping(value = "/activity/auguest")
public class AuguestActivityController {
	
	private static final Logger log = LoggerFactory.getLogger(AuguestActivityController.class);	
	@Resource(name = "HttpJsonClientImpl")
	protected IHttpJsonClient httpJsonClient;
	
	@PropertyConfig("game_get_queryUserPeriodBets")
	private String queryUserPeriodBets;

	@Resource(name = "userProfileServiceImpl")
	private IUserProfileService userServcie;
	
	@Resource(name = "activityRedenvelopeLogDaoImpl")
	private IActivityRedenvelopeLogDao activityRedenvelopeLogDao;
	
	@Resource(name = "activityConfigDaoImpl")
	private IActivityConfigDao activityConfigDaoImpl;
	
	@Resource(name = "activityLogDaoImpl")
	private IActivityLogDao activityLogDaoImpl;
	
	@Resource(name = "activityVipImpl")
	private IActivityVipService activityServiceImpl;
	
	@Resource(name = "fundChargeServiceImpl")
	private IFundChargeService fundChargeService;
	
	@Resource(name = "configServiceImpl")
	private IConfigService configService;
	
	@Resource(name = "auguestActivityImpl")
	private IAuguestActivityService auguestActivityService;
	
	//搶紅包活動ID
	private static final Long AUGUEST_RED_ENVELOPE_ACTIVITY_ID = 7L;
	//搶紅包活動代碼
	private static final String AUGUEST_RED_ENVELOPE_ACTIVITY_CODE = "chb";
	
	//奧運活動ID
	private static final Long AUGUEST_OLYMPIC_ACTIVITY_ID = 8L;
	
	private static final Long AUGUEST_OLYMPIC_SIGNUP_MONTH = 8L;
	
	private static final Long RATE_RAND_MAX = 100000L;
	
	private static final Long RATE_RAND_MIN = 0L;
	
	private static final Long LOTTO_PRIZE = 5888L;
	
	private Long lottoCount = 0l;
	
	//private List<String> whiteLists;
	
	private static final Map<Integer,Long> RED_ENVELOPE_MAPPING_COUNT= new TreeMap<Integer,Long> ();
    static {
    	RED_ENVELOPE_MAPPING_COUNT.put(0,100l);
    	RED_ENVELOPE_MAPPING_COUNT.put(1,100l);
    	RED_ENVELOPE_MAPPING_COUNT.put(2,35l);
    	RED_ENVELOPE_MAPPING_COUNT.put(3,25l);
    	RED_ENVELOPE_MAPPING_COUNT.put(4,6l);
    }
    
    @PostConstruct
	public void init() throws Exception{
		//取活動白名單用戶
		//whiteLists = activityLogDaoImpl.getActWhiteList();
	}
    
    /**
     * @param request
     * @return
     * @throws Exception
     * 1.VIP0~4的名单
     * 2.已开奖注单,两个平台（3.0、4.0）分别独立计算
     * 3.骰宝大小单双玩法不计入活动销量
     * 4.超级2000玩法按实际投注额的70%进行结算
     * 5.不同VIP等级的用户，翻卡抽奖的金额范围不同
     * 6.每天VIP4 仅产出一个超级大奖，一旦产出，当天不再开出
     * 7.用户抽中奖金后，系统自动结算，奖金额X25=需要打够的销量（4.0的超级2000按70%计算，骰宝大小单双不计入销量）
     * 8.活动期间8月24~8月28日，0:00系统自动产出报表
     * 9.产出用户活动效果对比的数据表（8月15-8月30日  20:00:00-23:59:59）（4.0需要去除骰宝的大小单双玩法，超级2000玩法按实际投注额的70%进行结算）
     * 10.已領過採用　減法計算
     * 11.翻完第一張牌代表今日已完成
     * 
     * initial 工作
     * 1.確認白名單
     * 2.確認紅包是否已領取
     * 3.確認剩餘紅包數量
     * 4.取得當天已領紅包資料
     * 
     */
    @RequestMapping(value = "initRedEnvelope")
	public @ResponseBody
	Response<ActAuguestResponse> initRedEnvelope(@RequestBody Request<ActivityRequest> request) throws Exception {
    	log.info("-------------------initRedEnvelope-----------------");
    	log.info("-------------------initRedEnvelope : UserToken1= " + request.getBody().getParam().getUserToken() );
    	Response<ActAuguestResponse> response = new Response<ActAuguestResponse>(request);
    	ActivityAuguest actEntity = new ActivityAuguest();
		ActAuguestResponse result = new ActAuguestResponse();
		String uAccunt = "";
		User user = null;
		actEntity.setFinished(false);
		if(request.getBody().getParam().getUserToken() != null){
			log.info("initRedEnvelope : UserToken= " + request.getBody().getParam().getUserToken());
			uAccunt = auguestActivityService.getUserNameByToken(request.getBody().getParam().getUserToken());
		}
		try{
			//取得活動資訊			
			actEntity.setActivityCode(AUGUEST_RED_ENVELOPE_ACTIVITY_CODE);
			actEntity = auguestActivityService.getAcvitityByCode(actEntity);
			log.info("-------------------initRedEnvelope取得活動資訊-----------------");
			//1.VIP0~4的名单　檢查是否在名單中
			if(uAccunt.isEmpty()){
				user = userServcie.queryUserById(request.getBody().getParam().getUserId());
			}else{
				user = getUSER(uAccunt);
			}
			log.info("initRedEnvelope : userId= " + user.getId() + " userAcunt =" + user.getAccount());
			actEntity.setUserId(user.getId());
			actEntity.setUserAccunt(user.getAccount());
			actEntity.setUserLv(user.getVipLvl());
			actEntity.setUserSource(user.getUserProfile().getSource());
			//今日活動開始與結束時間
			actEntity.setTodayStartTime(auguestActivityService.getWantedTime(0, actEntity.getDayStart().intValue(), 0, 0, 0));
			actEntity.setTodayEndTime(auguestActivityService.getWantedTime(0, actEntity.getDayEnd().intValue(), 0, 0, 0));
			//ACCUNT白名單
			actEntity = auguestActivityService.checkQualification(actEntity);
			
			actEntity.setMemo("用戶 "+ actEntity.getUserAccunt() +" 登陸活動頁");
			auguestActivityService.activityLog(actEntity);//LOG紀錄
			
			//依據活動 ID取得　設定資料
			Integer userSource = (actEntity.getUserSource() == null) ? 4:3;
			Integer userLvl = actEntity.getUserLv();
			actEntity.setConfigType(userSource + "V" + userLvl);
			actEntity = auguestActivityService.getActivityConfig(actEntity);
			//設定基本資訊
			actEntity = auguestActivityService.setDefaultParam(actEntity);	
			log.info("initRedEnvelope :  userId= " + user.getId() + " userAcunt =" + user.getAccount() + " 狀態= " + actEntity.getStatus() + " 參加資格 =" + 
					(actEntity.isQualification()? "true" : "false") + "  今日已完成="  + (actEntity.isFinished()?"true" : "false"));
			
			/*
			if(!actEntity.isQualification()){//翻卡抽奖的资格
				
				actEntity.setMemo("用戶 "+ actEntity.getUserAccunt() +" 無翻卡抽奖的资格");
				auguestActivityService.activityLog(actEntity);//LOG紀錄
				result.setQualification(0L);
				result.setStatus(0l);
				result.setErrorMsg("对不起，您没有翻卡抽奖的资格。如有疑问请联系客服~");
				response.getBody().setResult(result);
				return response;
			}
			*/
			/*
			//相關檢查(當天是否領過，一天只能一次)
			ActivityRedenvelopeLog redEnvelopeLog = activityRedenvelopeLogDao.queryTodayRedEnvelopeByUserId(AUGUEST_RED_ENVELOPE_ACTIVITY_ID, user.getId());
			//查詢今日領紅包後總投注量
			Map<String, Object> map = Maps.newHashMap();
			map.put("userId", user.getId());
			map.put("startTime", redEnvelopeLog.getCreateTime());
			map.put("endTime", redEnvelopeLog.getCreateTime());
			Long betAmount = (Long) httpJsonClient.invokeHttp(queryUserPeriodBets,
					map, new TypeReference<Long>(){});
			log.debug("betAmount : " + betAmount);
			//如果當天有紀錄 則回失敗(已領獎)
			if(redEnvelopeLog == null){
				log.debug("已領獎~~~!!");
				result.setStatus(0l);
				result.setRedEnvelope(redEnvelopeLog.getRedEnvelope());
				result.setBetsTotal(redEnvelopeLog.getRedEnvelope()*25);
				result.setBetAmount(betAmount);
				response.getBody().setResult(result);
				return response;
			}
				*/	
			/*
			//用戶對應VIP_LVL的每日紅包限制數　已領過採用　減法計算
			Long redsLimit = RED_ENVELOPE_MAPPING_COUNT.get(user.getVipLvl());
			lottoCount = 0l;
			if(user.getVipLvl() >= 4){
				lottoCount = activityRedenvelopeLogDao.queryTodayLottoCount(user.getVipLvl(),AUGUEST_RED_ENVELOPE_ACTIVITY_ID,LOTTO_PRIZE);
			}
			Long lvlCount = activityRedenvelopeLogDao.queryTodayCountgByVipLvl(user.getVipLvl(), AUGUEST_RED_ENVELOPE_ACTIVITY_ID);
			Long totalCount = lottoCount + lvlCount;
			if(totalCount.intValue() >= redsLimit.intValue()){
				result.setStatus(0l);
				result.setErrorMsg("对不起，红包已经被抢光了~下次请早啊！");
				response.getBody().setResult(result);
				return response;
			}
			//取得 紅包金額
			long redEnvelope = doRandRedEnvelope(user.getVipLvl());
			*/
			result = DtoConverter.activity2response(actEntity);
			result.setResult(1L);
			response.getBody().setResult(result);
		}catch(Exception e){
			log.error("redEnvelope activity initData error:",e);
			actEntity.setMemo("用戶 "+ actEntity.getUserAccunt() +" 登陸活動頁異常");
			actEntity.setStatus(2L);
			result = DtoConverter.activity2response(actEntity);
			result.setResult(0L);
			result.setErrorMsg("server error~!");
			response.getBody().setResult(result);
		}
		
		return response;
	}	
    
    @RequestMapping(value = "getRedEnvelope")
	public @ResponseBody
	Response<ActAuguestResponse> getRedEnvelope(@RequestBody Request<ActivityRequest> request) throws Exception {
    	log.info("-------------------getRedEnvelope-----------------");
    	Response<ActAuguestResponse> response = new Response<ActAuguestResponse>(request);
    	ActivityAuguest actEntity = new ActivityAuguest();
		ActAuguestResponse result = new ActAuguestResponse();
		String uAccunt = "";
		User user = null;
		if(request.getBody().getParam().getUserToken() != null){
			log.info("getRedEnvelope : UserToken= " + request.getBody().getParam().getUserToken());
			uAccunt = auguestActivityService.getUserNameByToken(request.getBody().getParam().getUserToken());
			
		}
		Long resLong = 0L;
		
		try{
			//取得活動資訊
			actEntity.setActivityCode(AUGUEST_RED_ENVELOPE_ACTIVITY_CODE);
			actEntity = auguestActivityService.getAcvitityByCode(actEntity);
			//1.VIP0~4的名单　檢查是否在名單中
			if(uAccunt.isEmpty()){
				user = userServcie.queryUserById(request.getBody().getParam().getUserId());
			}else{
				user = getUSER(uAccunt);
			}
			log.info("getRedEnvelope : userId= " + user.getId() + " userAcunt =" + user.getAccount());
			if(user != null){
				actEntity.setUserId(user.getId());
				actEntity.setUserAccunt(user.getAccount());
				actEntity.setUserLv(user.getVipLvl());
				actEntity.setUserSource(user.getUserProfile().getSource());
				//今日活動開始與結束時間
				actEntity.setTodayStartTime(auguestActivityService.getWantedTime(0, actEntity.getDayStart().intValue(), 0, 0, 0));
				actEntity.setTodayEndTime(auguestActivityService.getWantedTime(0, actEntity.getDayEnd().intValue(), 0, 0, 0));
				//ACCUNT白名單
				actEntity = auguestActivityService.checkQualification(actEntity);
				log.info("getRedEnvelope :  userId= " + user.getId() + " userAcunt =" + user.getAccount() + " 狀態= " + actEntity.getStatus() + " 參加資格 =" + 
						(actEntity.isQualification()? "true" : "false") + "  今日已完成="  + (actEntity.isFinished()?"true" : "false"));
				if(actEntity.isQualification()){
					//依據活動 ID取得　設定資料
					Integer userSource = (actEntity.getUserSource() == null) ? 4:3;
					Integer userLvl = actEntity.getUserLv();
					actEntity.setConfigType(userSource + "V" + userLvl);
					actEntity = auguestActivityService.getActivityConfig(actEntity);
					//設定中獎資訊
					actEntity = auguestActivityService.setPrize(actEntity);	
				}else{//無參加資格不派獎
					actEntity.setStatus(0L);
				}
				resLong = 1L;
			} else{ 
				//取不到 user 資料 視為無參加資格 因為是派獎所以不吐獎號
				log.debug("redEnvelope activity getRedEnvelope error: 查無使用者資料" + request.getBody().getParam().getUserId());
				actEntity.setStatus(0L);
			}
			result = DtoConverter.activity2response(actEntity);
			result.setResult(resLong);
			response.getBody().setResult(result);
		}catch(Exception e){ //錯誤
			log.error("redEnvelope activity getRedEnvelope error:",e);
			actEntity.setStatus(0L);
			result = DtoConverter.activity2response(actEntity);
			result.setResult(0L);
			result.setErrorMsg("server error~!");
			response.getBody().setResult(result);
		}		
		return response;
	}
    
    
    /**
     * get User Profile By Name
     * @param userAccunt
     * @return
     */
    private User getUSER(String userAccunt){
    	User userProfile = null;
    	try{
    		userProfile = userServcie.queryUserByName(userAccunt);
    	}
    	catch(Exception ex){
    		log.error("AuguestActivity.getUSER error = userAccunt:" + userAccunt);
    		ex.printStackTrace();
    	}
    	return userProfile;
    }
    
    @RequestMapping(value = "initOlympic")
   	public @ResponseBody
   	Response<ActAuguestResponse> initOlympic(@RequestBody Request<ActivityRequest> request) throws Exception {
    	log.info("-------------------initOlympic-----------------");
    	Response<ActAuguestResponse> response = new Response<ActAuguestResponse>(request);
		ActAuguestResponse result = new ActAuguestResponse();
		ActivityConfig olympicCfg = activityConfigDaoImpl.getActCfgById(AUGUEST_OLYMPIC_ACTIVITY_ID);
		//檢查是否在活動期間
		boolean isPeriod = checkIsActPeriod(olympicCfg);
		log.info("isPeriod : " + isPeriod);
		if(!isPeriod){
			log.warn("olympic activity error: not in activity time~~");
			result.setBetAmount(0l);
			result.setChargeAmount(0l);
			result.setMedals(0l);
			result.setResult(0l);
			result.setStatus(-1l);
			response.getBody().setResult(result);
			return response;
		}
		//玩家資料
		User user = userServcie.queryUserById(request.getBody().getParam().getUserId());
		log.info("user vip lvl: " + user.getVipLvl());
		
		//查詢是否有報名過
		ActivityVipRequest appeal = new ActivityVipRequest();
		appeal.setAccount(user.getAccount());
		appeal.setMonth(AUGUEST_OLYMPIC_SIGNUP_MONTH);
		VipActivityVo vo = activityServiceImpl.getActivityInfo(appeal);
				
		//取得目前金牌數
		Long medals = Long.valueOf(configService.getConfigValueByKey("activity","olympic_gold_medals"));
		//取得當天00:00:00~23:00:00充值金額
		BigDecimal dailyCharge = MowNumTool.fromFrifrog(fundChargeService.queryPeriodChargeSum(user.getId(),
				getWantedTime(0,0,0,0),getWantedTime(23,0,0,0)));
		Map<String, Object> map = Maps.newHashMap();
		map.put("userId", user.getId());
		map.put("startTime", getWantedTime(0,0,0,0).getTime());
		map.put("endTime", getWantedTime(24,0,0,0).getTime());
		log.info("map : " + map.toString());
		Long betAmount = (Long) httpJsonClient.invokeHttp(queryUserPeriodBets,
				map, new TypeReference<Long>(){});
		BigDecimal betAmt = MowNumTool.fromFrifrog(betAmount);
		log.info("betAmt : " + betAmt);
		
		
		//取得返利比例與返利百分比 0:返利比例　1:加獎百分比
		Long rebateRatio = getRebateRatio(dailyCharge.longValue());
		Long plusRatio = getplusRatio(dailyCharge.longValue());
		//判斷要不要秀獎金
		
		Double prize = checkShowPrize(medals,dailyCharge , betAmt, rebateRatio,plusRatio);
		result.setResult(1l);
		result.setStatus(1l);
		log.info("vo.getIsRegister() : " + vo.getIsRegister());
		log.info("user.getVipLvl().intValue() : " + user.getVipLvl().intValue());
		if(!vo.getIsRegister() && user.getVipLvl().intValue() < 3 ){
			log.warn("olympic activity error: user not signup~~");
			result.setStatus(0l);
			response.getBody().setResult(result);
			return response;
		}
		
		result.setMedals(medals);
		log.info("medals : " + medals);
		result.setBetAmount(betAmt.longValue());
		log.info("betAmt : " + betAmt);
		result.setPrize(prize.toString());
		log.info("prize : " + prize);
		result.setChargeAmount(dailyCharge.longValue());
		log.info("dailyCharge : " + dailyCharge);
		result.setRebateRatio(rebateRatio);
		log.info("rebateRatio : " + rebateRatio);
		result.setPlusRatio(plusRatio);
		log.info("plusRatio : " + plusRatio);
		response.getBody().setResult(result);
		log.info("result : " + result.toString());
		log.info("response : " + response.toString());
		return response;
    }
    
    //奧運活動報名
    @RequestMapping(value = "signUp")
   	public @ResponseBody
   	Response<ActAuguestResponse> signUp(@RequestBody Request<ActivityRequest> request) throws Exception {
    	Response<ActAuguestResponse> response = new Response<ActAuguestResponse>(request);
		ActAuguestResponse result = new ActAuguestResponse();
		//檢查是否在活動期間
		ActivityConfig olympicCfg = activityConfigDaoImpl.getActCfgById(AUGUEST_OLYMPIC_ACTIVITY_ID);
		boolean isPeriod = checkIsActPeriod(olympicCfg);
		if(!isPeriod){
			log.warn("red envelope activity error: not in activity time~~");
			result.setResult(0l);
			response.getBody().setResult(result);
			return response;
		}
		result.setResult(1l);
		response.getBody().setResult(result);
		return response;
    }
    
	private void addActivityLog(User user,Long redEnvelope){
		ActivityRedenvelopeLog record = new ActivityRedenvelopeLog();
		record.setActivityId(AUGUEST_RED_ENVELOPE_ACTIVITY_ID);
		record.setCreateTime(new Date());
		record.setRedEnvelope(redEnvelope);
		record.setUserAccount(user.getAccount());
		record.setUserId(user.getId());
		record.setVipLvl(user.getVipLvl());
		activityRedenvelopeLogDao.saveActivityDoubleboxLog(record);
	}
	
	private boolean checkIsActPeriod(ActivityConfig actCfg){
		boolean isPeriod = true;
		Calendar cal = Calendar.getInstance();
		Date now = cal.getTime();
		log.info("begin time : " + actCfg.getBeginTime());
		log.info("end time : " + actCfg.getEndTime());
		log.info("now.before(actCfg.getBeginTime() : " + now.before(actCfg.getBeginTime()));
		log.info("now.after(actCfg.getEndTime() : " + now.after(actCfg.getEndTime()));
		
		if(now.before(actCfg.getBeginTime()) || now.after(actCfg.getEndTime())){
			isPeriod = false;
		}
		return isPeriod;
	}
	
	private double checkShowPrize(Long medals, BigDecimal dailyCharge ,BigDecimal betAmt,Long rebateRatio,Long plusRatio){
		Double prize = 0.0d;
		if(dailyCharge.intValue() > 0){
			long compare = betAmt.longValue()/dailyCharge.longValue();
			log.info("compare : " + compare);
			if(compare >= 10l){
				prize = new BigDecimal(plusRatio).divide(new BigDecimal(100)).multiply(new BigDecimal(medals))
						.add(new BigDecimal(1)).multiply(new BigDecimal(rebateRatio).divide(new BigDecimal(100)))
						.multiply(dailyCharge).setScale(2, BigDecimal.ROUND_DOWN).doubleValue();
			}
		}
		return prize;
	}
	
	private Long getRebateRatio(long dailyCharge){
		long rebateRatio = 0l; 
		if(dailyCharge >= 500 ){
			rebateRatio = 8l;
		}
		if(dailyCharge >= 5000 ){
			rebateRatio = 10l;
		}
		if(dailyCharge >= 50000 ){
			rebateRatio = 12l;
		}
		if(dailyCharge >= 500000 ){
			rebateRatio = 15l;
		}
		return rebateRatio;
	}
	
	private Long getplusRatio(long dailyCharge){
		Long plusRatio = 0l;
		if(dailyCharge >= 500 ){
			plusRatio = 1l;
		}
		if(dailyCharge >= 5000 ){
			plusRatio = 2l;
		}
		if(dailyCharge >= 50000 ){
			plusRatio = 3l;
		}
		if(dailyCharge >= 500000 ){
			plusRatio = 5l;
		}
		return plusRatio;
	}
	
	private Date getWantedTime(int hourOfday, int second,int minute,int millisec){
		Calendar cal = Calendar.getInstance();  
	    cal.set(Calendar.HOUR_OF_DAY, hourOfday);  
	    cal.set(Calendar.SECOND, second);  
	    cal.set(Calendar.MINUTE, minute);  
	    cal.set(Calendar.MILLISECOND, millisec);
	    return cal.getTime();
	}
	
	private long doRandRedEnvelope(Integer userLvl){
		long redEnvelope = 0l;
		long rand_max = 0l;
		long rand_min = 0l;
		
		switch(userLvl.intValue()){
		case 0:
			rand_max = 29l;
			rand_min = 50l;
			break;
		case 1:
			rand_max = 149l;
			rand_min = 200l;
			break;
		case 2:
			rand_max = 349l;
			rand_min = 450l;
			break;
		case 3:
			rand_max = 799l;
			rand_min = 1000l;
			break;
		case 4:
			//已出過大獎
			if(lottoCount > 0){
				rand_max = 2499l;
				rand_min = 3500l;
			}else{//未出過大獎 有機會得大獎
				long rate = RandomVal.randlong(RATE_RAND_MAX,RATE_RAND_MIN);
				if(70000 <= rate){
					rand_max = 5888l;
					rand_min = 5888l;
				}
			}
			break;
		}
		redEnvelope = RandomVal.randlong(rand_max, rand_min);
		return redEnvelope;
	}
}
