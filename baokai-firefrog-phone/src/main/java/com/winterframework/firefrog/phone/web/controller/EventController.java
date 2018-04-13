
package com.winterframework.firefrog.phone.web.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.winterframework.firefrog.common.util.DateUtils;
import com.winterframework.firefrog.phone.web.cotroller.dto.ActAuguestResponse;
import com.winterframework.firefrog.phone.web.cotroller.dto.ActivityConfig;
import com.winterframework.firefrog.phone.web.cotroller.dto.ActivityConfigRequest;
import com.winterframework.firefrog.phone.web.cotroller.dto.ActivityDoubleboxRequest;
import com.winterframework.firefrog.phone.web.cotroller.dto.ActivityDoubleboxResponse;
import com.winterframework.firefrog.phone.web.cotroller.dto.ActivityLogRequest;
import com.winterframework.firefrog.phone.web.cotroller.dto.ActivityRedbowRequest;
import com.winterframework.firefrog.phone.web.cotroller.dto.ActivityRedbowResponse;
import com.winterframework.firefrog.phone.web.cotroller.dto.ActivityRequest;
import com.winterframework.firefrog.phone.web.cotroller.dto.ActivityResponse;
import com.winterframework.firefrog.phone.web.cotroller.dto.ActivityVipRequest;
import com.winterframework.firefrog.phone.web.cotroller.dto.FundGameVo;
import com.winterframework.firefrog.phone.web.cotroller.dto.JanRedBowActivity;
import com.winterframework.firefrog.phone.web.cotroller.dto.UserToken;
import com.winterframework.modules.spring.exetend.PropertyConfig;
import com.winterframework.modules.web.jsonresult.Request;
import com.winterframework.modules.web.jsonresult.Response;
import com.winterframework.modules.web.util.JsonMapper;

@Controller("eventController")
@RequestMapping("/iapi/event")
public class EventController extends BaseController {

	private static final Logger log = LoggerFactory.getLogger(EventController.class);
	private static final String ACTIVITY_REASON_KEY = "PM-PGXX-3";
	private static JsonMapper jmapper = JsonMapper.nonEmptyMapper();
	@PropertyConfig(value = "url.game.getActivityConfig")
	private String getActivityConfig;
	
	@PropertyConfig(value = "url.fund.api.game.action")
	private String setFundChangeLog;
	
	@PropertyConfig(value = "url.game.saveActivityLog")
	private String saveActivityLog;
	
	@PropertyConfig(value = "url.game.queryActivityLogs")
	private String queryActivityLogs;
	
	@PropertyConfig(value = "url.game.queryPrizeTotal")
	private String queryPrizeTotal;
	
	@PropertyConfig(value = "url.activity.doubleboxInitData")
	private String doubleboxInitData;
	
	@PropertyConfig(value = "url.activity.signUp")
	private String activitySignUp;
	@PropertyConfig(value = "url.activity.vip")
	private String activityVip;
	
	@PropertyConfig(value = "url.activity.olympicInit")
	private String olympicInit;
	
	@PropertyConfig(value = "url.activity.initRedEnvelope")
	private String initRedEnvelope;
	
	@PropertyConfig(value = "url.activity.doubleboxAward")
	private String doubleboxAward;
	
	@PropertyConfig(value = "url.activeapi.janredbowcheck")
	private String janRedbowCheck;
	
	@PropertyConfig(value = "url.activeapi.janredbowsave")
	private String janRedbowSave;
	
	private static final Long ACTIVITYID = 1L;//砸蛋活動ID
	private static final Long GUAGUACARD_ACTIVITYID = 3L;//刮刮卡活動ID
	private static final Long SPRING_ACTIVITYID = 4L;//春節活動ID
	private static final Long GUAGUACARD_MAX_AWARD_COUNTS = 21L;
	private static final Long GUAGUACARD_MAX_PRIZE_SUM = 25176L;
	
	
	@ResponseBody
	@RequestMapping("/doMoneyAct")
	public Response<ActivityResponse> doMoneyAct(@RequestBody Request<ActivityRequest> request) throws Exception{
		
		Response<ActivityResponse> response = new Response<ActivityResponse>(request);
		String token = request.getHead().getSessionId();
		try {
			String account = getUserNameByToken(token);
			if(null == account){
				response.getHead().setStatus(109990L);
				return response;
			}
			UserToken ut = getUserToken(account);
			//test code
//			UserToken ut = new UserToken();
//			ut.setUserName("captainray");
//			ut.setUserId(1292830L);
			
			
				
			//接收參數
			Integer system = request.getBody().getParam().getSystem().intValue();
			log.info("system : " + system);
			Float prize = request.getBody().getParam().getPrize();
			log.info("prize : " + prize);
			String note = request.getBody().getParam().getNote();
			//檢查完成//
			//寫入ACTIVITY_LOG 需要參數:活動ID、USER_ID、獎金、MEMO
			StringBuilder sb = new StringBuilder();
			sb.append("{\"system\":\"").append(system).append("\"}");
						
			String memo = sb.toString();
			//抓CONFIG設定資訊
			
			boolean isSuccess = doActivity(ut, SPRING_ACTIVITYID, prize, system, memo, note);
			if(!isSuccess){
				throw new Exception("doActivity error~~!!");
			}
			
			ActivityResponse result = new ActivityResponse();
			result.setStatus("success");
			response.setResult(result);
		} catch (Exception e) {
			log.error(e.getMessage() + " : ", e);
			response.getHead().setStatus(901000L);
		}
		
		return response;
	}
	
	
	@ResponseBody
	@RequestMapping("/doEggAct")
	public Response<ActivityResponse> doEggAct(@RequestBody Request<ActivityRequest> request) throws Exception{
		
		Response<ActivityResponse> response = new Response<ActivityResponse>(request);
		log.info("---------------------------doEggAct request begin--------------------------");
		String token = request.getHead().getSessionId();
		log.info("token : " + token);
		try {
			log.info("token : " + token);
			String account = getUserNameByToken(token);
			log.info("account : " + account);
			if(null == account){
				response.getHead().setStatus(109990L);
				return response;
			}
			UserToken ut = getUserToken(account);
			
			//接收參數
			String uuid = request.getBody().getParam().getUuid();
			log.info("uuid : " + uuid);
			Integer system = request.getBody().getParam().getSystem().intValue();
			log.info("system : " + system);
			Float prize = request.getBody().getParam().getPrize();
			log.info("prize : " + prize);
			
			//抓CONFIG設定資訊
			ActivityConfigRequest cfgRequest = new ActivityConfigRequest();
			cfgRequest.setId(ACTIVITYID);
			Response<ActivityConfig> rspCfg = httpClient.invokeHttp(gameUrl + getActivityConfig, cfgRequest, ActivityConfig.class );
			//做相關驗證
			ActivityConfig config = rspCfg.getBody().getResult();
			log.info("id : " + config.getId());
			log.info("beginTime : " + config.getBeginTime());
			log.info("endTime : " + config.getEndTime());
			log.info("maxPrize : " + config.getMaxPrize());
			log.info("minPrize : " + config.getMinPrize());
			log.info("memo : " + config.getMemo());
			Calendar cal = Calendar.getInstance();
			Date now = cal.getTime();
			log.info("begin time " + config.getBeginTime());
			log.info("end time " + config.getEndTime());
			log.info("now " + now);
			log.info("now after begin time " + now.after(config.getBeginTime()));
			log.info("now before end time " + now.before(config.getEndTime()));
			
			
			//if(config.getBeginTime().before(now))
			if( now.before(config.getBeginTime()) || now.after(config.getEndTime()))
			{
				//超過活動時間
				response.getHead().setStatus(109988L);
				log.info("return wrong");
				return response;
				
			}
			if(prize < config.getMinPrize() || 
					prize > config.getMaxPrize()){
				//獎金不符合 規定
				response.getHead().setStatus(109989L);
				return response;
			}
			ActivityLogRequest req = new ActivityLogRequest();
			req.setActivityId(ACTIVITYID);
			req.setPrize(prize);
			req.setUserId(ut.getUserId());
			//req.setUserId(112L);
			Response<Long> eggsRsp = httpClient.invokeHttp(gameUrl + queryActivityLogs,req ,Long.class);
			long todayEggs = eggsRsp.getBody().getResult().longValue();
			log.info("todayEggs : " + todayEggs);
			if(todayEggs> 0){
				//本日已砸過蛋
				response.getHead().setStatus(109987L);
				return response;
			}
			
			//檢查完成//
			//寫入ACTIVITY_LOG 需要參數:活動ID、USER_ID、獎金、MEMO
			StringBuilder sb = new StringBuilder();
			sb.append("{\"system\":\"").append(system).append("\",");
			sb.append("\"uuid\":\"").append(uuid).append("\"}");
			
			log.info("sb : " + sb.toString());
			req.setMemo(sb.toString());
			Response<Object> rsp = httpClient.invokeHttp(gameUrl + saveActivityLog,req, Object.class);
			//寫入fund_change_log 需要參數:
			FundGameVo vo = new FundGameVo();
			vo.setUserId(ut.getUserId());
			//vo.setUserId(112L);
			Long amount = (long)(prize*10000);
			vo.setAmount(amount);
			vo.setIsAclUser(0L);
			vo.setOperator(0L);
			vo.setReason(ACTIVITY_REASON_KEY);
			vo.setNote("手机砸蛋活动奖金");
			List<FundGameVo> list = new ArrayList<FundGameVo>();
			list.add(vo);
				
			log.info("firefrogUrl + setFundChangeLog : " + firefrogUrl + setFundChangeLog);
			//httpClient.invokeHttpWithoutResultType(firefrogUrl + setFundChangeLog, list,112L ,"sksk");
			httpClient.invokeHttpWithoutResultType(firefrogUrl + setFundChangeLog, list,ut.getUserId() ,ut.getUserName());
			
			ActivityResponse result = new ActivityResponse();
			result.setStatus("success");
			response.setResult(result);
		} catch (Exception e) {
			log.error("doEggAct error:", e);
			response.getHead().setStatus(901000L);
		}
		
		return response;
	}

	@ResponseBody
	@RequestMapping("/doGuaguacardAct")
	public Response<ActivityResponse> doGuaguacardAct(@RequestBody Request<ActivityRequest> request) throws Exception{
		
		Response<ActivityResponse> response = new Response<ActivityResponse>(request);
		log.info("---------------------------doGuaguacardAct request begin--------------------------");
		String token = request.getHead().getSessionId();
		log.info("token : " + token);
		try {
			String account = getUserNameByToken(token);
			log.info("account : " + account);
			if(null == account){
				response.getHead().setStatus(109990L);
				return response;
			}
			UserToken ut = getUserToken(account);
			
			//接收參數
			String uuid = request.getBody().getParam().getUuid();
			log.info("uuid : " + uuid);
			Integer system = request.getBody().getParam().getSystem().intValue();
			log.info("system : " + system);
			Float prize = request.getBody().getParam().getPrize();
			log.info("prize : " + prize);
			
			//抓CONFIG設定資訊
			ActivityConfigRequest cfgRequest = new ActivityConfigRequest();
			cfgRequest.setId(GUAGUACARD_ACTIVITYID);
			Response<ActivityConfig> rspCfg = httpClient.invokeHttp(gameUrl + getActivityConfig, cfgRequest, ActivityConfig.class );
			//做相關驗證
			ActivityConfig config = rspCfg.getBody().getResult();
			Calendar cal = Calendar.getInstance();
			Date now = cal.getTime();
			
			//擋活動時間
			if( now.before(config.getBeginTime()) || now.after(config.getEndTime()))
			{
				response.getHead().setStatus(109988L);
				return response;
				
			}
			//擋活動時間內領獎次數
			ActivityLogRequest req = new ActivityLogRequest();
			req.setActivityId(GUAGUACARD_ACTIVITYID);
			req.setPrize(prize);
			req.setBeginTime(config.getBeginTime());
			req.setEndTime(config.getEndTime());
			log.info("beginTime : " + config.getBeginTime());
			log.info("endTime : " + config.getEndTime());
			req.setUserId(ut.getUserId());
			//req.setUserId(112L);
			
			Response<Long> cardRsp = httpClient.invokeHttp(gameUrl + queryActivityLogs,req ,Long.class);
			long prizeCounts = cardRsp.getBody().getResult().longValue();
			log.info("prizeCounts : " + prizeCounts);
			if(prizeCounts >= GUAGUACARD_MAX_AWARD_COUNTS ){
				log.info("109987:超過領獎次數");
				response.getHead().setStatus(109987L);
				return response;
			}
			
			Response<Long> prizeRsp = httpClient.invokeHttp(gameUrl + queryPrizeTotal,req ,Long.class);
			float total_prize = prizeRsp.getBody().getResult().floatValue();
			log.info("#####total_prize : " + total_prize);
			float add_total_prize = total_prize + (prize * 10000);
			log.info("#####add_total_prize : " + add_total_prize);
			if(add_total_prize >= GUAGUACARD_MAX_PRIZE_SUM * 10000){
				log.info("109989:獎金不符合 規定");
				//獎金不符合 規定
				response.getHead().setStatus(109989L);
				return response;
			}
			
			
			//檢查完成//
			//寫入ACTIVITY_LOG 需要參數:活動ID、USER_ID、獎金、MEMO
			StringBuilder sb = new StringBuilder();
			sb.append("{\"system\":\"").append(system).append("\",");
			sb.append("\"uuid\":\"").append(uuid).append("\"}");
			
			log.info("sb : " + sb.toString());
			req.setMemo(sb.toString());
			log.info("URL : " + gameUrl + saveActivityLog);
			Response<Object> rsp = httpClient.invokeHttp(gameUrl + saveActivityLog,req, Object.class);
			//寫入fund_change_log 需要參數:
			FundGameVo vo = new FundGameVo();
			vo.setUserId(ut.getUserId());
			//vo.setUserId(112L);
			Long amount = (long)(prize*10000);
			vo.setAmount(amount);
			vo.setIsAclUser(0L);
			vo.setOperator(0L);
			vo.setReason(ACTIVITY_REASON_KEY);
			vo.setNote("刮刮卡活动奖金");
			List<FundGameVo> list = new ArrayList<FundGameVo>();
			list.add(vo);
				
			log.info("firefrogUrl + setFundChangeLog : " + firefrogUrl + setFundChangeLog);
			//httpClient.invokeHttpWithoutResultType(firefrogUrl + setFundChangeLog, list,112L ,"sksk");
			httpClient.invokeHttpWithoutResultType(firefrogUrl + setFundChangeLog, list,ut.getUserId() ,ut.getUserName());
			
			ActivityResponse result = new ActivityResponse();
			result.setStatus("success");
			response.setResult(result);
		} catch (Exception e) {
			log.error("doEggAct error:", e);
			response.getHead().setStatus(901000L);
		}
		
		return response;
	}
	//五月活動
	@ResponseBody
	@RequestMapping("/doMayActInit")
	public Response<ActivityDoubleboxResponse> doMayActInit(@RequestBody Request<ActivityRequest> request) throws Exception{
		
		Response<ActivityDoubleboxResponse> response = new Response<ActivityDoubleboxResponse>(request);
		String token = request.getHead().getSessionId();
		try {
			String account = getUserNameByToken(token);
			if(null == account){
				response.getHead().setStatus(109990L);
				return response;
			}
			UserToken ut = getUserToken(account);
			ActivityDoubleboxRequest requestData = new ActivityDoubleboxRequest();
			requestData.setUserId(ut.getUserId());
			response = httpClient.invokeHttp(firefrogUrl + doubleboxInitData, requestData, ActivityDoubleboxResponse.class);
		} catch (Exception e) {
			response.getHead().setStatus(901000L);
		}
		return response;
	}
	
	//奧運活動
	@ResponseBody
	@RequestMapping("doOlympicInit")
	public Response<ActAuguestResponse> doOlympicInit(@RequestBody Request<ActivityRequest> request) throws Exception{
		
		Response<ActAuguestResponse> response = new Response<ActAuguestResponse>(request);
		String token = request.getHead().getSessionId();
		try {
			String account = getUserNameByToken(token);
			if(null == account){
				response.getHead().setStatus(109990L);
				return response;
			}
			UserToken ut = getUserToken(account);

			ActivityDoubleboxRequest requestData = new ActivityDoubleboxRequest();
			requestData.setUserId(ut.getUserId());
			response = httpClient.invokeHttp(firefrogUrl + olympicInit, requestData, ActAuguestResponse.class);
		} catch (Exception e) {
			response.getHead().setStatus(901000L);
		}
		return response;
	}
	
	//奧運活動
	@ResponseBody
	@RequestMapping("doOlympicSignUp")
	public Response<Object> doOlympicSignUp(@RequestBody Request<ActivityVipRequest> request) throws Exception{
		Response<Object> response = new Response<Object>(request);
		String token = request.getHead().getSessionId();
		try {
			String account = getUserNameByToken(token);
			if(null == account){
				response.getHead().setStatus(109990L);
				return response;
			}
			UserToken ut = getUserToken(account);
			ActivityVipRequest requestData = new ActivityVipRequest();
			requestData.setAccount(ut.getUserName());
			requestData.setMonth(request.getBody().getParam().getMonth());
			requestData.setSource(request.getBody().getParam().getSource());
			response = httpClient.invokeHttp(firefrogUrl + activityVip, requestData, Object.class);
		} catch (Exception e) {
			response.getHead().setStatus(901000L);
		}
		return response;
	}
	
	@ResponseBody
	@RequestMapping("/doMayActAward")
	public Response<ActivityDoubleboxResponse> doMayActAward(@RequestBody Request<ActivityRequest> request) throws Exception{
		
		Response<ActivityDoubleboxResponse> response = new Response<ActivityDoubleboxResponse>(request);
		String token = request.getHead().getSessionId();
		try {
			String account = getUserNameByToken(token);
			if(null == account){
				response.getHead().setStatus(109990L);
				return response;
			}
			UserToken ut = getUserToken(account);
			
			ActivityDoubleboxRequest requestData = new ActivityDoubleboxRequest();
			requestData.setUserId(ut.getUserId());
			response = httpClient.invokeHttp(firefrogUrl + doubleboxAward, requestData, ActivityDoubleboxResponse.class);
		} catch (Exception e) {
			response.getHead().setStatus(901000L);
		}
		return response;
	}
	
	//2017一月紅包活動
	@ResponseBody
	@RequestMapping("/janRedbowInit")
	public Response<ActivityRedbowResponse> janRedbowInit(@RequestBody Request<ActivityRedbowRequest> request) throws Exception{
		log.info("janRedbowInit begin");
		Response<ActivityRedbowResponse> response = new Response<ActivityRedbowResponse>(request);
		String token = request.getHead().getSessionId();
		try {
			String account = getUserNameByToken(token);
			if(null == account){
				response.getHead().setStatus(109990L);
				return response;
			}
			UserToken ut = getUserToken(account);
			log.info("ut account : " + ut.getUserName());
			
			JanRedBowActivity requestData = new JanRedBowActivity();
			requestData.setUserId(ut.getUserId());
			requestData.setDevice(2l);
			Response<Object> resp = httpClient.invokeHttp(activeUrl + janRedbowCheck, requestData,Object.class);
			ActivityRedbowResponse result = new ActivityRedbowResponse();
			result.setStatus(Long.valueOf(resp.getBody().getResult().toString()));
			Calendar cal = Calendar.getInstance();
			cal.set(2017, Calendar.JANUARY, 15);
			Date low = cal.getTime();
			log.info("low : " + low);
			cal.set(2017, Calendar.JANUARY, 22);
			Date high = cal.getTime();
			log.info("high : " + low);
			boolean display = DateUtils.between(low, high);
			log.info("display : " + display);
			result.setDisplay(display?1l:0l);
			response.getBody().setResult(result);
		} catch (Exception e) {
			log.error("janRedbowInit exception : ", e);
			response.getHead().setStatus(901000L);
		}
		return response;
	}
	
	//2017一月紅包活動()
	@ResponseBody
	@RequestMapping("/janRedbowAward")
	public Response<ActivityResponse> janRedbowAward(@RequestBody Request<ActivityRequest> request) throws Exception{
		
		Response<ActivityResponse> response = new Response<ActivityResponse>(request);
		String token = request.getHead().getSessionId();
		try {
			String account = getUserNameByToken(token);
			if(null == account){
				response.getHead().setStatus(109990L);
				return response;
			}
			UserToken ut = getUserToken(account);

			JanRedBowActivity requestData = new JanRedBowActivity();
			requestData.setUserId(ut.getUserId());
			requestData.setDevice(2l);
			response = httpClient.invokeHttp(activeUrl + janRedbowSave, requestData, ActivityResponse.class);
		} catch (Exception e) {
			response.getHead().setStatus(901000L);
		}
		return response;
	}
		
	
	//寫入活動紀錄與fund_change_log
	private boolean doActivity(UserToken ut, Long activity_id, Float prize, Integer system,String memo,String note){
		
		try{
			ActivityLogRequest req = new ActivityLogRequest();
			req.setActivityId(activity_id);
			req.setPrize(prize);
			req.setUserId(ut.getUserId());
						
			req.setMemo(memo);
			Response<Object> rsp = httpClient.invokeHttp(gameUrl + saveActivityLog,req, Object.class);
			//寫入fund_change_log 需要參數:
			FundGameVo vo = new FundGameVo();
			vo.setUserId(ut.getUserId());
			//vo.setUserId(112L);
			Long amount = (long)(prize*10000);
			vo.setAmount(amount);
			vo.setIsAclUser(0L);
			vo.setOperator(0L);
			vo.setReason(ACTIVITY_REASON_KEY);
			vo.setNote(note);
			List<FundGameVo> list = new ArrayList<FundGameVo>();
			list.add(vo);
				
			
			httpClient.invokeHttpWithoutResultType(firefrogUrl + setFundChangeLog, list, ut.getUserId() ,ut.getUserName());
		}catch(Exception e){
			return false;
		}
		return true;
	}
}	

	

	