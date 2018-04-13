package com.winterframework.firefrog.active.dao;

import java.util.List;

import com.winterframework.firefrog.active.dao.vo.ActivityResult;
import com.winterframework.orm.dal.ibatis3.BaseDao;

/**
 * 活動結果資料表
 * @author David.Wu
 *
 */
public interface IActivityResultDao extends BaseDao<ActivityResult>{
	
	/**
	 * 取得用戶今日中獎金額
	 * @param actRsVO userId 用戶ID ,activityId 活動ID
	 * @return
	 */
	public List<ActivityResult> getUserPrize(ActivityResult actRsVO);
	
	/**
	 * 取得今日大獎紀錄
	 * @param actRsVO
	 * @return
	 */
	public Long getAwardRecord(ActivityResult actRsVO);
	
	/**
	 * 根據用戶ID及類型取得中獎資訊
	 * @param userId
	 * @param type
	 * @return
	 */
	ActivityResult queryActivityResultByUserIdAndType(Long userId,String type);
	
	/**
	 * 取得今日得獎獎紀錄
	 * @param List<actRsVO>
	 * @return
	 */
	public List<ActivityResult> getListAwardRecord(ActivityResult actRsVO);
	
	/**
	 * 取得活動開始第N天的紀錄 
	 * @param userId
	 * @param activityid 活動流水號
	 * @param day 活動開始第N天
	 * @return
	 */
	public ActivityResult queryActivityResultByUserIdAndActivityIdAndResult(Long userId, Long activityId, String day);
	
	public String queryMinTypeByUserIdAndActivityIdAndLessThanResult(Long userId, Long activityid, String day);
}
