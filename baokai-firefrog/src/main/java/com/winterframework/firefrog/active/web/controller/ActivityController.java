package com.winterframework.firefrog.active.web.controller;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.winterframework.firefrog.active.dao.IActivityConfigDao;
import com.winterframework.firefrog.active.dao.vo.ActivityConfig;
import com.winterframework.firefrog.active.service.IActivityAwardsService;
import com.winterframework.firefrog.active.service.IActivityService;
import com.winterframework.firefrog.active.service.IAuguestActivityService;
import com.winterframework.firefrog.active.web.dto.ActivityAwardsRequest;
import com.winterframework.firefrog.active.web.dto.ActivitySignUpRequest;
import com.winterframework.firefrog.common.redis.RedisClient;
import com.winterframework.firefrog.common.util.DateUtils;
import com.winterframework.firefrog.game.service.IGameBettypeStatusService;
import com.winterframework.firefrog.index.web.dto.RedBowActivity;
import com.winterframework.firefrog.index.web.dto.RedBowRequest;
import com.winterframework.firefrog.user.entity.User;
import com.winterframework.firefrog.user.service.IUserProfileService;
import com.winterframework.modules.web.jsonresult.Request;
import com.winterframework.modules.web.jsonresult.Response;

@Controller("activeController")
@RequestMapping(value = "/activity")
public class ActivityController {

	
	@Resource(name = "activityImpl")
	private IActivityService activityServiceImpl;
	
	@Resource(name = "activityConfigDaoImpl")
	private IActivityConfigDao activityConfigDaoImpl;
	
	@Resource(name = "activityAwardsImpl")
	private IActivityAwardsService activityAwardsImpl;
	
	@Resource(name = "gameBettypeStatusServiceImpl")
	private IGameBettypeStatusService gameBettypeStatusService;
	
//	@Resource(name = "userCustomerDaoImpl")
//	private UserCustomerDaoImpl userCustomerDao;
	
	@Resource(name ="userProfileServiceImpl")
	private IUserProfileService userProfileService;
	
	@Resource(name = "auguestActivityImpl")
	private IAuguestActivityService auguestActivityService;
	
	@Resource(name = "RedisClient")
	private RedisClient rc;

	private static final Logger log = LoggerFactory.getLogger(ActivityController.class);	

	/**
	* @Title: signUp 
	* @Description: 11月活動報名
	* @param request
	* @return
	* @throws Exception
	 */
	@RequestMapping(value = "/signUp")
	public @ResponseBody
	Response<Object> signUp(@RequestBody Request<ActivitySignUpRequest> request) throws Exception {
		Response<Object> response = new Response<Object>(request);
		ActivitySignUpRequest struc = request.getBody().getParam();
		log.info("11月活動報名 account="+struc.getAccount()+" source="+struc.getSource());
		ActivityConfig actCfgVO = new ActivityConfig();
		actCfgVO.setActivityId(struc.getMonth());
		actCfgVO.setType(String.valueOf(struc.getWeek()));
		
		ActivityConfig actCfg = activityConfigDaoImpl.getActCfg(actCfgVO);
		struc.setActId(actCfg.getId());
		
		if(StringUtils.isNotBlank(struc.getToken())){
			User user = getUSER(null, struc.getToken());
			if(null == user){
				log.info("11月活動報名 token={}, 無法取得用戶資料", struc.getToken());
				response.setResult(3l);
				return response;
			} else {
				struc.setAccount(user.getAccount());
			}
		}
			
		Long userCount =  activityServiceImpl.querySignUp(struc);
		if(userCount != 0 ){
			log.info("11月活動報名 account="+struc.getAccount()+" 已報名"+userCount);
			response.setResult(2l);
			return response;
		}
		Integer result = activityServiceImpl.saveSignUp(struc);
		log.info("11月活動報名 account="+struc.getAccount()+" result="+result);
		response.setResult(result);
		return response;
		
	}
	
	
	/**
	* @Title: querySignUp 
	* @Description: 11月活動報名查詢
	* @param request
	* @return
	* @throws Exception
	 */
	@RequestMapping(value = "/querySignUp")
	public @ResponseBody
	Response<Object> querySignUp(@RequestBody Request<ActivitySignUpRequest> request) throws Exception {
		Response<Object> response = new Response<Object>(request);
		ActivitySignUpRequest struc = request.getBody().getParam();
		log.info("11月活動報名 account="+struc.getAccount());
		
		ActivityConfig actCfgVO = new ActivityConfig();
		actCfgVO.setActivityId(struc.getMonth());
		actCfgVO.setType(String.valueOf(struc.getWeek()));
		
		ActivityConfig actCfg = activityConfigDaoImpl.getActCfg(actCfgVO);
		struc.setActId(actCfg.getId());
		
		if(StringUtils.isNotBlank(struc.getToken())){
			User user = getUSER(null, struc.getToken());
			if(null == user){
				log.info("11月活動報名 token={}, 無法取得用戶資料", struc.getToken());
				response.setResult(3l);
				return response;
			} else {
				struc.setAccount(user.getAccount());
			}
		}
			
		Long userCount =  activityServiceImpl.querySignUp(struc);
		if(userCount != 0 ){
			response.setResult(2l);
			return response;
		}else{
			response.setResult(0l);
			return response;
		}
		
	}
	
	@RequestMapping(value = "/queryTodayTotalBets")
	public @ResponseBody
	Response<RedBowActivity> queryTodayTotalBets(@RequestBody Request<RedBowRequest> request) throws Exception {
		Response<RedBowActivity> response = new Response<RedBowActivity>(request);
		Integer[] lotteryArray = new Integer[]{99106, 99111, 99112};
		List<Integer> lotteryList = Arrays.asList(lotteryArray);
//		Long userId = request.getBody().getParam().getUserId();
		RedBowRequest redBowRequest = request.getBody().getParam();
		RedBowActivity recBowActivity = new RedBowActivity();
		recBowActivity.setPrize(0);
		recBowActivity.setUserLv(0);
		
		ActivityConfig actCfgVO = new ActivityConfig();
		actCfgVO.setActivityId(201611l);
		actCfgVO.setType("3");
		
		ActivityConfig actCfg = activityConfigDaoImpl.getActCfg(actCfgVO);
		
		Long actId = actCfg.getId();
		
		User user = getUSER(redBowRequest.getUserId(), redBowRequest.getToken());
		if(null == user){
//			throw new Exception("無此用戶id : " + userId);
			log.error("無此用戶id : {}, token : {}", redBowRequest.getUserId(), redBowRequest.getToken());
			recBowActivity.setStatus(-1);
		} else {
			ActivitySignUpRequest struc = new ActivitySignUpRequest();
			struc.setActId(actId);
			struc.setAccount(user.getAccount());
			//先判斷有沒有報名 11月第三周活動
			Long userCount =  activityServiceImpl.querySignUp(struc);
			if(userCount.equals(0l) && user.getVipLvl() < 3l){
				log.error("此用戶[{}, {}]尚未報名11月第三周活動", user.getAccount(), user.getId());
				recBowActivity.setStatus(-2);
			} else {
				//取得第三周設定檔
				ActivityConfig activityConfig = activityConfigDaoImpl.getActCfgById(actId);
				//判斷是否在活動時間內
				Calendar cal = Calendar.getInstance();
				if(cal.getTime().after(activityConfig.getBeginTime()) && 
						cal.getTime().before(activityConfig.getEndTime()))
				{
					Long todayTotalBets = gameBettypeStatusService.getTotalBetsByInterval(user.getId(), 
							DateUtils.getStartTimeOfCurrentDate(), 
							DateUtils.getEndTimeOfCurrentDate(), 
							lotteryList);
					
					Long[] intervalAmounts = new Long[]{5000l, 10000l, 50000l, 100000l, 500000l};
					
					Integer result = 5;
					for(int i = 0 ; i < intervalAmounts.length ; i++){
						if(todayTotalBets.compareTo(intervalAmounts[i] * 10000) < 0){
							result = i;
							break;
						}
					}
					recBowActivity.setPrize(result);
					recBowActivity.setStatus(0);
					recBowActivity.setUserLv(user.getVipLvl() > 0 ? 1 : 0);
				} else {
					log.error("目前非活動時間");
					recBowActivity.setStatus(-3);
				}
			}
		}
		
		response.setResult(recBowActivity);
		return response;
	}
	/**
	* @Title: uploadAwardList 
	* @Description: 上傳活動派獎名單(問卷調查)
	* @param request
	* @return
	* @throws Exception
	 */
	@RequestMapping(value = "/uploadAwardList")
	public @ResponseBody
	Response<String> uploadAwardList(@RequestBody Request<ActivityAwardsRequest> request) throws Exception {
		Response<String> response = new Response<String>();
		String insertNum = "";
		String key = "/activity-uploadAwardList";
		try {
			//鎖定只能一名使用者寫入派獎帳變資料
			if (!rc.acquireLock(key,30000)) {
				log.info("employee submit in same time:"+new Date());
				response.setResult("请勿重複点击");
				return response;
			} else {
				log.info("/uploadAwardList---------------------start");
				insertNum = activityAwardsImpl.addAwardList(request.getBody().getParam());
				log.info("/uploadAwardList---------------------end");		
			}
			
			response.setResult(insertNum);
			
			//完成後解除鎖定
			log.info("/uploadAwardList releaseLock");
			rc.releaseLock(key);			
		} catch (Exception e) {
			log.error("/activity/uploadAwardList has error：", e);
			//完成後解除鎖定
			log.info("/uploadAwardList releaseLock");
			rc.releaseLock(key);

			throw e;
		}
		
		return response;		
	}
	
	
	/**
     * get User Profile By Name
     * @param userAccunt
     * @return
     */
    private User getUSER(Long userId, String token){
    	User userProfile = null;
    	String uAccunt = "";
    	try{  		
    		if (StringUtils.isNotBlank(token)){
    			log.info("ActivityController : UserToken= " + token);
    			uAccunt = auguestActivityService.getUserNameByToken(token);
    			userProfile = userProfileService.queryUserByName(uAccunt);
    		} else {
    			userProfile = userProfileService.queryUserById(userId);
    		}
    	}
    	catch(Exception ex){
    		log.error("ActivityController.getUSER error = userAccunt:" + uAccunt);
    		ex.printStackTrace();
    	}
    	return userProfile;
    }
    
}
