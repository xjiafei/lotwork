package com.winterframework.firefrog.active.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.winterframework.firefrog.active.dao.IActivityWinningLogDao;
import com.winterframework.firefrog.active.dao.vo.ActivityWinningLog;
import com.winterframework.orm.dal.ibatis3.BaseIbatis3Dao;

/**
 * 活動得獎名單 DAO 實作。
 * @author Pogi.Lin
 */
@Repository("activityWinningLogDaoImpl")
public class ActivityWinningLogDaoImpl extends
		BaseIbatis3Dao<ActivityWinningLog> implements IActivityWinningLogDao {

	/* (non-Javadoc)
	 * @see com.winterframework.firefrog.active.dao.IActivityWinningLogDao#queryActivityWinningLogById(com.winterframework.firefrog.active.dao.vo.ActivityWinningLog)
	 */
	@Override
	public ActivityWinningLog queryActivityWinningLogById(ActivityWinningLog vo) {
		return this.sqlSessionTemplate.selectOne("queryActivityWinningLogById", vo);
	}

	/* (non-Javadoc)
	 * @see com.winterframework.firefrog.active.dao.IActivityWinningLogDao#queryActivityWinningLogByActivityId(com.winterframework.firefrog.active.dao.vo.ActivityWinningLog)
	 */
	@Override
	public List<ActivityWinningLog> queryActivityWinningLogByActivityId(ActivityWinningLog vo) {
		return this.sqlSessionTemplate.selectList("queryActivityWinningLogByActivityId", vo);
	}

	/* (non-Javadoc)
	 * @see com.winterframework.firefrog.active.dao.IActivityWinningLogDao#queryByActivityIdAndAccount(com.winterframework.firefrog.active.dao.vo.ActivityWinningLog)
	 */
	@Override
	public List<ActivityWinningLog> queryByActivityIdAndAccount(
			ActivityWinningLog vo) {
		return this.sqlSessionTemplate.selectList("queryByActivityIdAndAccount", vo);
	}

	@Override
	public int updateApproval(Long id, String approver, Date gmtAppr,
			String approverMemo, String fundChangeLogSN,Long approverAmt) {
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		params.put("approver", approver);
		params.put("gmtAppr", gmtAppr);
		params.put("approverMemo", approverMemo);
		params.put("fundChangeLogSN", fundChangeLogSN);
		params.put("approverAmt", approverAmt);
		
		return this.sqlSessionTemplate.update("updateApproval", params);
	}

	@Override
	public List<ActivityWinningLog> queryForApproveList(ActivityWinningLog vo) {
		return this.sqlSessionTemplate.selectList("queryForApproveList", vo);
	}

	@Override
	public List<ActivityWinningLog> queryForBeApprovedList(ActivityWinningLog vo) {
		return this.sqlSessionTemplate.selectList("queryForBeApprovedList", vo);
	}

	@Override
	public Long countUserWinningTimesByDay(Long activityId, String account,
			Date someDay) {
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("activityId", activityId);
		params.put("account", account);
		params.put("someDay", someDay);
		return this.sqlSessionTemplate.selectOne("countUserWinningTimesByDay", params);
	}
	
	@Override
	public Long queryNumOfAwardsLog(List<Long> ids) {
		Long num =sqlSessionTemplate.selectOne("queryNumOfAwardsLog",ids);
		return num;
	}

	@Override
	public void deleteAwardList(Long activityId, Long activityWeek) {
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("activityId", activityId);
		params.put("activityWeek", activityWeek);
		sqlSessionTemplate.delete("deleteAwardByCodeAndWeek",params);
	}


	@Override
	public Long addAwardList(Long activityId, Long activityWeek, String account, Long userId) {
		ActivityWinningLog vo = new ActivityWinningLog();
		vo.setAccount(account);
		vo.setActivityId(activityId);
		vo.setUserId(userId);
		vo.setActivityWeek(activityWeek);
		vo.setWinningPrize(0L);
		Long num = Long.valueOf(insert(vo));
		return num;
	}

	@Override
	public Long queryAlreadyAwarded(Long activityId, Long activityWeek) {
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("activityId", activityId);
		params.put("activityWeek", activityWeek);
		Long num = sqlSessionTemplate.selectOne("queryAlreadyAwarded",params);
		return num != null? num:0L;
	}

	@Override
	public List<ActivityWinningLog> checkUserIsUpload(String account, Long activityId, Long activityWeek) {
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("account", account);
		params.put("activityId", activityId);
		params.put("activityWeek", activityWeek);
		List<ActivityWinningLog> list = sqlSessionTemplate.selectList("checkUserIsUpload",params);
		return list;
	}

	@Override
	public Long queryUserUploadTotal(Long activityId, Long activityWeek) {
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("activityId", activityId);
		params.put("activityWeek", activityWeek);
		Long num = sqlSessionTemplate.selectOne("queryUserUploadToal",params);
		return num != null? num:0L;
	}
}