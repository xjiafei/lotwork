package com.winterframework.firefrog.game.web.controller;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.winterframework.firefrog.common.annotation.ValidRequestBody;
import com.winterframework.firefrog.game.dao.IActivityLogDao;
import com.winterframework.firefrog.game.dao.vo.ActivityLog;
import com.winterframework.firefrog.shortlived.sheepactivity.service.IActivityUserAwardLogService;
import com.winterframework.firefrog.game.web.dto.ActivityLogRequest;
import com.winterframework.firefrog.game.web.dto.ActivityUserAwardRequest;
import com.winterframework.firefrog.game.web.dto.ActivityUserAwardResponse;
import com.winterframework.firefrog.game.web.dto.DailyBetPrizeRequest;
import com.winterframework.firefrog.game.web.dto.DailyBetPrizeResponse;
import com.winterframework.modules.web.jsonresult.Request;
import com.winterframework.modules.web.jsonresult.Response;

/** 
* @ClassName ActivityUserAwardLogController 
* @Description 用户获奖情况相关接口 
* @author  hugh
* @date 2014年12月4日 下午4:06:44 
*  
*/
@Controller("activityUserAwardLogController")
@RequestMapping("/game")
public class ActivityUserAwardLogController {

private Logger log = LoggerFactory.getLogger(ActivityUserAwardLogController.class);
	
	@Resource(name = "activityUserAwardLogServiceImpl")
	private IActivityUserAwardLogService activityUserAwardLogServiceImpl;
	
	@Resource(name = "activityLogDaoImpl")
	private IActivityLogDao activityLogDaoImpl;
	
	/** 
	* @Title: queryUserAwardLog 
	* @Description: 用户获奖情况
	* @param request
	* @return
	* @throws Exception
	*/
	@RequestMapping(value = "/queryUserAwardLog")
	@ResponseBody
	public Response<ActivityUserAwardResponse> queryUserAwardLog(
			@RequestBody @ValidRequestBody Request<ActivityUserAwardRequest> request) throws Exception {

	
		log.info("开始查询用户获奖相关情况......");
		
		Response<ActivityUserAwardResponse> response = new Response<ActivityUserAwardResponse>(request);
		long userId = request.getBody().getParam().getUserId();
		ActivityUserAwardResponse result = new  ActivityUserAwardResponse();
		
		try {
			result.setLogs(activityUserAwardLogServiceImpl.getUserAward(userId));
						
		} catch (Exception e) {
			log.error("查询查询用户获奖相关情况异常 ", e);
			throw e;
		}


		response.setResult(result);
		log.info("查询查询用户获奖相关情况完成。");
		return response;
	}
	
	/** 
	* @Title: queryTodayUserAwardLog 
	* @Description: 当前所有用户获奖情况
	* @param request
	* @return
	* @throws Exception
	*/
	@RequestMapping(value = "/queryTodayUserAwardLog")
	@ResponseBody
	public Response<ActivityUserAwardResponse> queryTodayUserAwardLog(
			@RequestBody  Request<ActivityUserAwardRequest> request) throws Exception {

	
		log.info("开始查询用户获奖相关情况......");
		
		Response<ActivityUserAwardResponse> response = new Response<ActivityUserAwardResponse>(request);

		ActivityUserAwardResponse result = new  ActivityUserAwardResponse();
		
		try {
			result.setLogs(activityUserAwardLogServiceImpl.getToday());
						
		} catch (Exception e) {
			log.error("查询查询用户获奖相关情况异常 ", e);
			throw e;
		}


		response.setResult(result);
		log.info("查询查询用户获奖相关情况完成。");
		return response;
	}
	
	
	/** 
	* @Title: getDailyBetPrize 
	* @Description: 用户投注日常奖励领取
	* @param request
	* @return
	* @throws Exception
	*/
	@RequestMapping(value = "/getDailyBetPrize")
	@ResponseBody
	public Response<DailyBetPrizeResponse> getDailyBetPrize(
			@RequestBody @ValidRequestBody Request<DailyBetPrizeRequest> request) throws Exception {

	
		log.info("开始日常活动投注奖励领取");
		
		Response<DailyBetPrizeResponse> response = new Response<DailyBetPrizeResponse>(request);
		long userId = request.getBody().getParam().getUserId();
		DailyBetPrizeResponse result = new  DailyBetPrizeResponse();
		
		try {
			activityUserAwardLogServiceImpl.getDailyBetPrize(userId,request.getBody().getParam().getBetDate(),request.getBody().getParam().getMoney(),request.getBody().getParam().getChannel());
						
		} catch (Exception e) {
			log.error("日常活动投注奖励领取异常 ", e);
			response.getHead().setStatus(500);
			return response;
		}


		response.setResult(result);
		log.info("日常活动投注奖励领取完成。");
		return response;
	}
	
	/** 
	* @Title: saveActivityLog
	* @Description: 用戶活動記錄寫入
	* @param request
	* @return
	* @throws Exception
	*/
	@RequestMapping(value = "/saveActivityLog")
	@ResponseBody
	public Response<Object> saveActivityLog(
			@RequestBody @ValidRequestBody Request<ActivityLogRequest> request) throws Exception {
		Response<Object> response = new Response<Object>(request);
		Date now = Calendar.getInstance().getTime();
		ActivityLog al = new ActivityLog();
		log.info("activity id : " + request.getBody().getParam().getActivityId());
		al.setActivityId(request.getBody().getParam().getActivityId());
		log.info("activity id : " + request.getBody().getParam().getMemo());
		al.setMemo(request.getBody().getParam().getMemo());
		al.setStatus(1L);
		log.info("user id : " + request.getBody().getParam().getUserId());
		al.setUserId(request.getBody().getParam().getUserId());
		log.info("prize: " + request.getBody().getParam().getPrize());
		al.setPrize(request.getBody().getParam().getPrize());
		al.setAwardTime(now);
		al.setCreateTime(now);
		try{
			activityLogDaoImpl.saveActivityLog(al);
		} catch (Exception e) {
			log.error("查询查询用户获奖相关情况异常 ", e);
			throw e;
		}
		
		return response;
	}
	
	/** 
	* @Title: queryActivityLogs
	* @Description: 用戶活動記錄寫入
	* @param request
	* @return
	* @throws Exception
	*/
	@RequestMapping(value = "/queryActivityLogs")
	@ResponseBody
	public Response<Long> queryActivityLogs(
			@RequestBody @ValidRequestBody Request<ActivityLogRequest> request) throws Exception {
		Response<Long> response = new Response<Long>(request);
		log.info("-----------------queryActivityLogs----------------");
		Long count = 0L;
		try{
			Long userId = request.getBody().getParam().getUserId();
			log.info("prize: " + request.getBody().getParam().getUserId());
			Long activityId = request.getBody().getParam().getActivityId();
			log.info("prize: " + request.getBody().getParam().getActivityId());
			Date beginTime = request.getBody().getParam().getBeginTime();
			Date endTime = request.getBody().getParam().getEndTime();
			
			count = activityLogDaoImpl.queryTodayActivityLogCount(userId, activityId, beginTime, endTime);
		} catch (Exception e) {
			log.error("查询查询用户获奖相关情况异常 ", e);
			throw e;
		}
		response.setResult(count);
		return response;
	}
	
	/** 
	* @Title: queryPrizeTotal
	* @Description: 用戶活動獎金查詢
	* @param request
	* @return
	* @throws Exception
	*/
	@RequestMapping(value = "/queryPrizeTotal")
	@ResponseBody
	public Response<Long> queryPrizeTotal(
			@RequestBody @ValidRequestBody Request<ActivityLogRequest> request) throws Exception {
		Response<Long> response = new Response<Long>(request);
		float totalPrize = 0F;
		try{
			Long userId = request.getBody().getParam().getUserId();
			Long activityId = request.getBody().getParam().getActivityId();
			Date beginTime = request.getBody().getParam().getBeginTime();
			Date endTime = request.getBody().getParam().getEndTime();
			totalPrize = activityLogDaoImpl.queryPrizeTotal(userId, activityId, beginTime, endTime);
		} catch (Exception e) {
			log.error("查询用户奖金相关情况异常 ", e);
			throw e;
		}
		Long finalTotalPrize = (long)(totalPrize * 10000);
		response.setResult(finalTotalPrize);
		return response;
	}
	
	public float mul(float v1,float v2) {
		BigDecimal b1 = new BigDecimal(Float.toString(v1));
		BigDecimal b2 = new BigDecimal(Float.toString(v2));
		return b1.multiply(b2).floatValue();
	}

			
	

}
