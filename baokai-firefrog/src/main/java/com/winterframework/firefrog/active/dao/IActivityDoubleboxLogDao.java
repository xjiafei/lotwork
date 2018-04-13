package com.winterframework.firefrog.active.dao;

import java.util.List;

import com.winterframework.firefrog.active.dao.vo.ActivityDoubleboxLog;
import com.winterframework.firefrog.active.dao.vo.ActivityLog;
import com.winterframework.orm.dal.ibatis3.BaseDao;

public interface IActivityDoubleboxLogDao  extends BaseDao<ActivityDoubleboxLog>{
	void saveActivityDoubleboxLog(ActivityDoubleboxLog log);
	List<ActivityDoubleboxLog> queryTodayLogByUserId(Long userId, Long activityId);
	List<ActivityDoubleboxLog> getWinList(Long activityId,Long rownum);
}
