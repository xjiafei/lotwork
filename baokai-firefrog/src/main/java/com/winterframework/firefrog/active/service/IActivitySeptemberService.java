package com.winterframework.firefrog.active.service;

import java.util.List;

import com.winterframework.firefrog.active.dao.vo.Activity;
import com.winterframework.firefrog.active.dao.vo.ActivityConfig;
import com.winterframework.firefrog.active.dao.vo.ActivityResult;
import com.winterframework.firefrog.active.entity.ActivitySeptEntity;

public interface IActivitySeptemberService {
	
	Boolean activityDraw(Long userid,Integer drawLv);
	
	/** 
	* @Title: queryLevel 
	* @Description: 查詢使用者可點燈數
	* @param  ActivitySeptEntity
	* @throws JsonProcessingException
	* @throws Exception
	*/
	public ActivitySeptEntity  queryLevel(Long userId) throws Exception;
	
	/** 
	* @Title: queryUserBets 
	* @Description: 查詢使用者投注金額 
	* @param  ActivitySeptEntity
	* @throws JsonProcessingException
	* @throws Exception
	*/
	public Long  queryUserBets(ActivitySeptEntity entity) throws Exception;
	
	/** 
	* @Title: queryActivityId
	* @Description: 查詢活動ID
	* @param  ActivityCode
	* @throws JsonProcessingException
	* @throws Exception
	*/
	public Long  queryActivityId(String activityCode) throws Exception;
	
	/** 
	* @Title: queryActivityConfig
	* @Description: 查詢活動ID
	* @param  ActivityCode
	* @throws JsonProcessingException
	* @throws Exception
	*/
	public List<ActivityConfig> queryActivityConfig(Long  activityId) ;
		
	

	/**
	 * 取得派獎資料
	 * @param userId  
	 * @param activityid
	 * @return
	 */
	public List<ActivityResult> getListAwardRecord(ActivityResult activityResultVO);
	
	public String queryMinTypeByUserIdAndActivityIdAndLessThanResult(Long userId, Long activityid, String day);
	
	public ActivityResult queryActivityResultByUserIdAndActivityIdAndResult(Long userId, Long activityid, String day);
	
	public Activity getActivityByCode(String activityCode) throws Exception;

	public boolean isGameTime() throws Exception ;
}
