package com.winterframework.firefrog.active.web.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.winterframework.firefrog.active.dao.vo.Activity;
import com.winterframework.firefrog.active.dao.vo.ActivityConfig;
import com.winterframework.firefrog.active.dao.vo.ActivityResult;
import com.winterframework.firefrog.active.service.IActivitySeptemberService;
import com.winterframework.firefrog.active.service.IActivityVipService;
import com.winterframework.firefrog.active.service.IAuguestActivityService;
import com.winterframework.firefrog.active.web.dto.Act20160903Request;
import com.winterframework.firefrog.active.web.dto.Act20160903Response;
import com.winterframework.firefrog.active.web.dto.ActivityVipRequest;
import com.winterframework.firefrog.common.util.DataConverterUtil;
import com.winterframework.firefrog.common.util.DateUtils;
import com.winterframework.firefrog.user.dao.vo.VipActivityVo;
import com.winterframework.firefrog.user.entity.User;
import com.winterframework.firefrog.user.service.IUserProfileService;
import com.winterframework.modules.web.jsonresult.Request;
import com.winterframework.modules.web.jsonresult.Response;

@Controller
@RequestMapping(value = "/activity/20160903")
public class Activity20160903Controller {

	private static final Logger log = LoggerFactory.getLogger(Activity20160903Controller.class);
	
	@Resource(name ="userProfileServiceImpl")
	private IUserProfileService userProfileService;
	
	@Resource(name = "auguestActivityImpl")
	private IAuguestActivityService auguestActivityService;
	
	@Resource(name = "activityVipImpl")
	private IActivityVipService activityServiceImpl;
	
	@Resource(name = "activitySeptemberServiceImpl")
	private IActivitySeptemberService activitySeptemberServiceImpl;
	
	private final static String ACTIVITY_CODE = "160903";
	
	private final static String KEY_NORMAL = "normal";
	
	private final static String KEY_VIP = "vip";
	
//	private static long ACTIVITY_ID = -1;
	
	private final static SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd");
	
	private final static Map<String, ActivityConfig> activityConfigs = new HashMap<String, ActivityConfig>();
	
	/**尚未達標的LEVEL*/
	private final static String NO_COMPLIANCE_LEVEL = "0";
	
	/**
	 * 取得活動設定
	 * @return
	 * @throws Exception
	 */
	private Map<String, ActivityConfig> getConfigs() throws Exception{
		if(activityConfigs.isEmpty()){
			long activityid = activitySeptemberServiceImpl.queryActivityId(ACTIVITY_CODE);
			//取得設定檔
			List<ActivityConfig> configs = activitySeptemberServiceImpl.queryActivityConfig(activityid);
			
			for(ActivityConfig config : configs){
				activityConfigs.put(config.getType(), config);
			}
		}
		return activityConfigs;
	}
	
	@RequestMapping(value = "getVipLvl")
	public @ResponseBody
	Response<Act20160903Response> getVipLvl(@RequestBody Request<Act20160903Request> request) throws Exception {
		Act20160903Request req = request.getBody().getParam();
		long userid = req.getUserId();
		log.debug("initData userId : " + userid);
		
		User user = getUSER(request.getBody().getParam());
		user.getVipLvl();
		
		Response<Act20160903Response> response = new Response<Act20160903Response>(request);
		Act20160903Response resp = new Act20160903Response();
		resp.setViplvl(user.getVipLvl());
		response.setResult(resp);
		return response;
	}
	
	
	
	@RequestMapping(value = "getUserData")
	public @ResponseBody
	Response<Act20160903Response> openDraw(@RequestBody Request<Act20160903Request> request) throws Exception {
		Calendar now = Calendar.getInstance();
		Act20160903Request req = request.getBody().getParam();
		long userid = req.getUserId();
		log.debug("initData userId : " + userid);
		int vipLvl = req.getVipLvl();
		log.debug("initData vipLvl : " + vipLvl);
		
		User user = getUSER(request.getBody().getParam());
		
		Activity activity = activitySeptemberServiceImpl.getActivityByCode(ACTIVITY_CODE);
		
		Response<Act20160903Response> response = new Response<Act20160903Response>(request);
		Act20160903Response resp = new Act20160903Response();
		
		// isJoin
		resp.setJoin(checkIsJoin(user, activity));
		
		//isFinished
		resp.setFinished(checkIsFinished(user, now, activity));
		
		//prize -> get  依據活動 ID取得　設定資料
		ActivityConfig activityConfig = getActivityConfig(user, now, activity);
		//TODO, 尚未取得正確Result
		resp.setPrize(getPrize(user, activityConfig));
		
		//position
		//1. level
		resp.setLevel(null == activityConfig ? "0" : activityConfig.getType());
		
		//2. day
		resp.setDay(getTheDay(now, activity));
		
		//今日是否達標
		resp.setBetToday(checkBetToday(user, now, activity));
		response.setResult(resp);
		return response;
	}
	
	private int getTheDay(Calendar now, Activity activity){
		int theDay = 0;
		if(now.getTime().before(activity.getStartTime())){
			
		} else {
			theDay = Integer.parseInt( 
					String.valueOf( DateUtils.calcDateBetween(
							activity.getStartTime(), 
							now.getTime()))
					);
		}
		return theDay;
	}
	
	/**
	 * 判斷用戶是否參加
	 * @param user
	 * @return
	 * @throws Exception
	 */
	private boolean checkIsJoin(User user, Activity activity)  throws Exception {
		if(user.getVipLvl() >= 3){
			//本次活动采取报名制，用户需要活动开始前点击“立即报名”方可参与活动。注：VIP3（包含VIP3）以上用户无需报名即可参加。
			return Boolean.TRUE;
		} else {
			ActivityVipRequest struc = new ActivityVipRequest();
			struc.setAccount(user.getAccount());
			struc.setMonth(9l);
//			struc.setToken("");
//			struc.setSource("web");
			struc.setStartTime(sf.format(activity.getStartTime()));
			struc.setEndTime(sf.format(activity.getEndTime()));
			VipActivityVo result = activityServiceImpl.getActivityInfo(struc);
			return result.getIsRegister();
		}
	}
	
	/**
	 * 判斷活動是否結束 2016/9/24 23:59:59 結束
	 * @return true : 已結束,  false : 尚未結束
	 */
	private boolean checkIsFinished(User user, Calendar now, Activity activity){
		//是否活動結束
		boolean isEnd = activity.getEndTime().before(now.getTime());
		
		//活動第幾天
		int theDay = getTheDay(now, activity);
		
		//是否還有資格
		boolean onStatus = false;
		if(theDay <= 1){
			onStatus = true;
		} else {
			String minType = 
					activitySeptemberServiceImpl.queryMinTypeByUserIdAndActivityIdAndLessThanResult(user.getId(), activity.getId(), String.valueOf(theDay));
			
			if(StringUtils.isBlank(minType)){
				log.error("checkIsFinished, 無法取得活動結果資料歷程 UserId : " + user.getId() + 
						", activityId:" + activity.getId() + 
						", 活動第" + theDay + "天");
				minType = "0";
			}
			
			if(NO_COMPLIANCE_LEVEL.equals(minType)){
				//已經無效
				onStatus = false;
			} else {
				onStatus = true;
			}
		}
		return !onStatus || isEnd;  //無資格 或者  活動已經結束,  都判斷為結束
	}
	
	/**
	 * 取得目前對應的設定檔
	 * @param user
	 * @return
	 * @throws Exception
	 */
	private ActivityConfig getActivityConfig(User user, Calendar now, Activity activity) throws Exception {
		Map<String, ActivityConfig> configs =  getConfigs();
		int theDay = getTheDay(now, activity);
		if(theDay > 6){
			theDay = 6;
		}
		//取得RESULT
		ActivityResult activityResult = 
				activitySeptemberServiceImpl.queryActivityResultByUserIdAndActivityIdAndResult(user.getId(), activity.getId(), String.valueOf(theDay));
		
		//依照TYPE 取得Config設定檔
		ActivityConfig config = configs.get(null == activityResult ? "0" : activityResult.getType());
		return config;
	}
	
	/**
	 * 取得目前能獲的的金錢
	 * @param user
	 * @param activityConfig
	 * @return
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	private long getPrize(User user, ActivityConfig activityConfig) throws JsonParseException, JsonMappingException, IOException{
		if(null == activityConfig || null == activityConfig.getRule()){
			return 0;
		} else {
			Map<String, Object> map = DataConverterUtil.convertJson2Map(activityConfig.getRule());
			
			if(user.getVipLvl() > 0){
				//VIP用戶
				return Long.parseLong(map.get(KEY_VIP).toString())/10000;
			} else {
				//普通用戶
				return Long.parseLong(map.get(KEY_NORMAL).toString())/10000;
			}
		}
	}
	
	/**
	 * 判斷今日是否已經投注
	 * @param user
	 * @param now
	 * @param activity
	 * @return true : 已經投注,  false : 今日尚未投注
	 * @throws Exception
	 */
	private boolean checkBetToday(User user, Calendar now, Activity activity) throws Exception{
		int theDay = getTheDay(now, activity);
		
		if(theDay > 6){
			theDay = 6;
		}
		
		//取得RESULT
		ActivityResult activityResult = 
				activitySeptemberServiceImpl.queryActivityResultByUserIdAndActivityIdAndResult(user.getId(), activity.getId(), String.valueOf(theDay));
				
		boolean isBet = false;
		if(theDay <= 1){
			String resultType = null == activityResult ? NO_COMPLIANCE_LEVEL : activityResult.getType();
			if(NO_COMPLIANCE_LEVEL.equals(resultType)){
				//代表尚未投注
			} else {
				isBet = true;
			}
		} else {
			String minType = 
					activitySeptemberServiceImpl.queryMinTypeByUserIdAndActivityIdAndLessThanResult(user.getId(), activity.getId(), String.valueOf(theDay));
			
			if(StringUtils.isBlank(minType)){
				log.error("checkBetToday, 無法取得活動結果資料歷程 UserId : " + user.getId() + 
						", activityId:" + activity.getId() + 
						", 活動第" + theDay + "天");
				minType = "0";
			}
			
			if(NO_COMPLIANCE_LEVEL.equals(minType)){
				//已經無效, 這個bet就不應該判斷, 應該先給finish做
				isBet = true;
			} else {
				if(NO_COMPLIANCE_LEVEL.equals(activityResult.getType())){
					//代表尚未投注
				} else {
					isBet = true;
				}
			}
		}
		return isBet;
	}
	
	/**
     * get User Profile By Name
     * @param userAccunt
     * @return
     */
    private User getUSER(Act20160903Request request){
    	User userProfile = null;
    	String uAccunt = "";
    	try{  		
    		if (StringUtils.isNotBlank(request.getToken())){
    			log.info("getActivityStep2Info : UserToken= " + request.getToken());
    			uAccunt = auguestActivityService.getUserNameByToken(request.getToken());
    			userProfile = userProfileService.queryUserByName(uAccunt);
    		} else {
    			userProfile = userProfileService.queryUserById(request.getUserId());
    		}
    	}
    	catch(Exception ex){
    		log.error("Activity20160902.getUSER error = userAccunt:" + uAccunt);
    		ex.printStackTrace();
    	}
    	return userProfile;
    }
}
