package com.winterframework.firefrog.active.web.controller;

import java.util.Date;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.winterframework.firefrog.active.entity.ActivitySeptEntity;
import com.winterframework.firefrog.active.service.IActivitySeptemberService;
import com.winterframework.firefrog.active.service.IActivityVipService;
import com.winterframework.firefrog.active.service.IAuguestActivityService;
import com.winterframework.firefrog.active.web.dto.ActSeptemberRequest;
import com.winterframework.firefrog.active.web.dto.ActSeptemberResponse;
import com.winterframework.firefrog.active.web.dto.ActivityVipRequest;
import com.winterframework.firefrog.common.redis.RedisClient;
import com.winterframework.firefrog.common.util.DateUtils;
import com.winterframework.firefrog.common.util.Encrypter;
import com.winterframework.firefrog.user.dao.vo.VipActivityVo;
import com.winterframework.firefrog.user.entity.User;
import com.winterframework.firefrog.user.service.IUserProfileService;
import com.winterframework.modules.spring.exetend.PropertyConfig;
import com.winterframework.modules.web.jsonresult.Request;
import com.winterframework.modules.web.jsonresult.Response;

@Controller
@RequestMapping(value = "/activity/20160902")
public class Activity20160902Controller {


	private static final Logger log = LoggerFactory.getLogger(Activity20160902Controller.class);	

	@Resource(name="RedisClient")
	private RedisClient redisClient;
			
	@Resource(name="activitySeptemberServiceImpl")
	private IActivitySeptemberService activitySeptemberServiceImpl;
	
	@Resource(name = "userProfileServiceImpl")
	private IUserProfileService userServcie;
	
	@Resource(name = "auguestActivityImpl")
	private IAuguestActivityService auguestActivityService;
	
	@Resource(name = "activityVipImpl")
	private IActivityVipService activityServiceImpl;
	
	private static final Long SEPT_SIGNUP_MONTH = 9L;
	
	private static final String SEPT_SIGNUP_DATE = "20160912";
	//private static final String SEPT_SIGNUP_DATE = "20160901";// for test
	
	@PropertyConfig(value="encrypter.Key")
	protected String encrypterKey;
	@PropertyConfig(value="encrypter.value")
	protected String encrypterValue;
	
	
	@RequestMapping(value = "/getActivityStep2Info")
	public @ResponseBody                                                    
	Response<ActSeptemberResponse> getActivityStep2Info(@RequestBody Request<ActSeptemberRequest> request) throws Exception {
		Response<ActSeptemberResponse> response = new Response<ActSeptemberResponse>(request);
		ActSeptemberResponse result = new ActSeptemberResponse();
		ActSeptemberRequest septemberRequest= request.getBody().getParam();
		User user = getUSER(septemberRequest);
		
		log.info("user vip lvl: " + user.getVipLvl() + "userId:"+ user.getId());
		
		
		//是否有報名
		ActivityVipRequest appeal = new ActivityVipRequest();
		appeal.setAccount(user.getAccount());
		appeal.setMonth(SEPT_SIGNUP_MONTH);
		appeal.setStartTime(SEPT_SIGNUP_DATE);
		VipActivityVo vo= activityServiceImpl.getActivityInfo(appeal);
		boolean inGameTime = activitySeptemberServiceImpl.isGameTime();
    	if(!inGameTime){
    		log.warn("septactivity2 error: game not start");
			result.setHasRight(0l);
			response.getBody().setResult(result);
			return response;
    	}else if(!vo.getIsRegister() && user.getVipLvl().intValue() < 3){
			log.warn("septactivity2 error: user not signup~~");
			result.setHasRight(0l);
			response.getBody().setResult(result);
			return response;
		}else{
			result.setHasRight(1L);
		}
		//0 false 1 true 
		//目前可點燈數，先查詢當日投注金額
		ActivitySeptEntity activitySeptEntity=activitySeptemberServiceImpl.queryLevel(user.getId());
		result.setLeftMoney(activitySeptEntity.getLeftMoney());
		result.setLevel(activitySeptEntity.getLevel());
		result.setIsFinished(activitySeptEntity.getIsFinished());
		result.setNowLevel(activitySeptEntity.getNowLevel());
		result.setIsTop(activitySeptEntity.getIsTop());
		if(user.getVipLvl().intValue()>0){
			result.setIsVip(1l);
		}else{
			result.setIsVip(0l);
		}
		
		response.setResult(result);
		return response;
	}
	
	@RequestMapping(value = "/openDraw")
	public @ResponseBody
	Response<ActSeptemberResponse> openDraw(@RequestBody Request<ActSeptemberRequest> request) throws Exception {

		ActSeptemberRequest req = request.getBody().getParam();
		Response<ActSeptemberResponse> response = new Response<ActSeptemberResponse>(request);
		User user = getUSER(req);

		log.info("activity-20160902 userid={},drawlvl={}",new Object[]{user.getId(),req.getDrawLv()});
		
		String nowTime= DateUtils.format(new Date(), "yyyy-MM-dd");
		
		String key = new StringBuffer().append(nowTime).append("-")
								       .append("20160902activity").append("-")
								       .append(req.getDrawLv()).append("-")
								       .append(user.getId()).toString();
		ActSeptemberResponse res = new ActSeptemberResponse();
		//是否派過錢
		res.setIsDraw("Y");
		//控制一秒內用戶只可點擊一次,避免工具刷入
		if(redisClient.get(key)!=null){
			response.setResult(res);
			return response;
		}else{
			redisClient.set(key,"1",1);
		}
		//首充檢核
		
		Boolean isDraw = activitySeptemberServiceImpl.activityDraw(user.getId(), req.getDrawLv());
		if(isDraw){
			//是否派過錢
			res.setIsDraw("N");
			res.setIsSuccess(0l);
		}else{
			res.setIsSuccess(0l);
		}
		response.setResult(res);
		return response;
	}
	
    
    /**
     * get User Profile By Name
     * @param userAccunt
     * @return
     */
    private User getUSER(ActSeptemberRequest request){
    	User userProfile = null;
    	String uAccunt = "";
    	try{  		
    		if (request.getToken() != null && request.getToken().equals("") == false){
    			log.info("getActivityStep2Info : UserToken= " + request.getToken());
    			uAccunt = auguestActivityService.getUserNameByToken(request.getToken());
    		}

    		if(uAccunt.isEmpty()){
    			userProfile = userServcie.queryUserById(request.getUserId());
    		}else{
    			userProfile = userServcie.queryUserByName(uAccunt);
    		}

    	}
    	catch(Exception ex){
    		log.error("Activity20160902.getUSER error = userAccunt:" + uAccunt);
    		ex.printStackTrace();
    	}
    	return userProfile;
    }
}
