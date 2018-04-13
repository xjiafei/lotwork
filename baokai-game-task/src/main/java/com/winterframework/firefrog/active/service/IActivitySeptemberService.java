package com.winterframework.firefrog.active.service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.winterframework.firefrog.active.dao.vo.ActivityConfig;
import com.winterframework.firefrog.active.dao.vo.ActivityResult;

/**
 * 
 * @author Ami.Tsai
 *
 */
public interface IActivitySeptemberService {

	/**
	 * 第二波活動派獎
	 */
	public void drawStep2() throws Exception;
	
	/**
	 * 第三波活動派獎
	 */
	public void drawStep3() throws Exception;
	
	/**
	 * 區間內統計 專門update type欄位, 只算當日的資料
	 * @param calendar 計算的日期
	 * @param isDaily 是否為計算某天全部
	 * @throws Exception
	 */
	public void intevalSumUp(Date date, boolean isDaily) throws Exception;
	
	/**
	 * 昨日統計 update type, status 欄位, 日期往前減一天
	 * @throws Exception
	 */
	public void daySumUp(Calendar calendar) throws Exception;
	
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
	public List<ActivityResult> getListAwardRecord(Long userId, Long activityid);
	
}
