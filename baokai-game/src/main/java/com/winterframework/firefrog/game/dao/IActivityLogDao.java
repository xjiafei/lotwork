package com.winterframework.firefrog.game.dao;

import java.util.Date;

import com.winterframework.firefrog.game.dao.vo.ActivityLog;
import com.winterframework.orm.dal.ibatis3.BaseDao;

public interface IActivityLogDao  extends BaseDao<ActivityLog>{
	void saveActivityLog(ActivityLog log);
	Long queryTodayActivityLogCount(Long userId, Long activityId, Date beginTime, Date endTime);
	Float queryPrizeTotal(Long userId, Long activityId, Date beginTime, Date endTime);
}
