package com.winterframework.firefrog.active.dao;

import java.util.Date;
import java.util.List;

import com.winterframework.firefrog.active.dao.vo.ActivityWinningLog;
import com.winterframework.orm.dal.ibatis3.BaseDao;

/**
 * 活動得獎名單 DAO 介面。
 * @author Pogi.Lin
 */
public interface IActivityWinningLogDao extends BaseDao<ActivityWinningLog> {

	/**
	 * 依據 ID 取得唯一的 ActivityWinningLog 物件。
	 * @param vo VO物件
	 * @return
	 */
	public ActivityWinningLog queryActivityWinningLogById(ActivityWinningLog vo);
	
	/**
	 * 依據 activityId 取得 ActivityWinningLog 物件。
	 * @param vo VO物件
	 * @return
	 */
	public List<ActivityWinningLog> queryActivityWinningLogByActivityId(ActivityWinningLog vo);
	
	/**
	 * 依據 activityId、account 取得 ActivityWinningLog 物件。
	 * @param vo VO物件
	 * @return
	 */
	public List<ActivityWinningLog> queryByActivityIdAndAccount(ActivityWinningLog vo);
	
	/**
	 * 依據 id 更新核准資料。<br>
	 * approver, gmtAppr, approverMemo, fundChangeLogId。
	 * @param id ACTIVITY_WINNING_LOG.id
	 * @param approverAmt 實發獎金
	 * @param approver 審批者帳號
	 * @param gmtAppr 審批時間
	 * @param approverMemo 審批註解
	 * @param fundChangeLogSN FUND_CHANGE_LOG.sn
	 * @return 更新成功筆數
	 */
	public int updateApproval(Long id, String approver, Date gmtAppr, String approverMemo, String fundChangeLogSN,Long approverAmt);
	
	/**
	 * 取得某活動已中獎且未派發的獎金列表。
	 * @param vo VO物件
	 * @return
	 */
	public List<ActivityWinningLog> queryForApproveList(ActivityWinningLog vo);
	
	/**
	 * 取得某活動已中獎且已派發的獎金列表。
	 * @param vo VO物件
	 * @return
	 */
	public List<ActivityWinningLog> queryForBeApprovedList(ActivityWinningLog vo);
	
	/**
	 * 計算某日某活動使用者總共中獎次數
	 * @param activityId 活動ID
	 * @param account 使用者帳號
	 * @param someDay 統計日
	 * @return 
	 */
	public Long countUserWinningTimesByDay(Long activityId, String account, Date someDay);
	
	
	/**
	 * 檢查是否已派過獎
	 * param  List<Long> ids 資料群的id
	 * return Long 已派過獎筆數
	 */
	public Long queryNumOfAwardsLog(List<Long> ids);
	/**
	 * 刪除派獎資料
	 * @param activityId   活動ID
	 * @param activityWeek 活動周數
	 */
	public void deleteAwardList(Long activityId,Long activityWeek);
	/**
	 * 新增派獎資料
	 * @param activityId   活動ID
	 * @param activityWeek 活動周數
	 * @param account      用戶名
	 * @param userId       用戶id
	 * return Long 刪除筆數
	 */
	public Long addAwardList(Long activityId,Long activityWeek,String account,Long userId);
	/**
	 * 查詢以派獎資料筆數
	 * @param activityId   活動ID
	 * @param activityWeek 活動周數
	 */
	public Long queryAlreadyAwarded(Long activityId,Long activityWeek);
	/**
	 * 查詢用戶名單
	 * @param account      用戶名
	 * @param activityId   活動ID
	 * @param activityWeek 活動周數
	 * return List<ActivityWinningLog>
	 */
	public List<ActivityWinningLog> checkUserIsUpload(String account,Long activityId,Long activityWeek);
	/**
	 * 查詢已上傳用戶總數
	 * @param activityId   活動ID
	 * @param activityWeek 活動周數
	 * return Long 用戶總數
	 */
	public Long queryUserUploadTotal(Long activityId,Long activityWeek);
}
