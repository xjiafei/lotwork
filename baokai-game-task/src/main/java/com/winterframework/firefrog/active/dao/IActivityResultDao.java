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
	public ActivityResult getUserPrize(ActivityResult actRsVO);
	
	/**
	 * 取得今日大獎紀錄
	 * @param actRsVO
	 * @return
	 */
	public Long queryActivityResultAwardToday(ActivityResult actRsVO);
	
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
	
	/**
	 * 修改當下結果資料
	 * @param id
	 * @param type
	 * @return
	 */
	public Integer updateTypeById(Long id, String type);
	
	public Integer updateTypeAndStatusById(Long id, String type, String status);
	
	/**
	 * 取得當日之前最大的level
	 * @param userId
	 * @param activityId
	 * @param day
	 * @return
	 */
	public String queryMinTypeByUserIdAndActivityIdAndLessThanResult(Long userId, Long activityId, String day);
	
	/**
	 * 查詢是否有失效的結果
	 * @param userId
	 * @param activityId
	 * @return
	 */
	public Long queryStatusZeroByUserIdAndActivityId(Long userId, Long activityId);
	
	/**
	 * 查詢活動紀錄 依照result排序
	 * @param userId
	 * @param activityId
	 * @return
	 */
	public List<ActivityResult> queryAllResultByUserIdAndActivityIdOrderByResult(Long userId, Long activityId);
	
	public List<ActivityResult> getBeforeOneDayResultByActivityIdAndTypeAndUserIdAndActivityId(Long userId, Long activityId,String type);
}
