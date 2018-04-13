package com.winterframework.firefrog.active.dao;

import java.util.List;

import com.winterframework.firefrog.active.dao.vo.ActivityLog;
import com.winterframework.orm.dal.ibatis3.BaseDao;

public interface IActivityLogDao  extends BaseDao<ActivityLog>{
	void saveActivityLog(ActivityLog log);
	Long queryActivityLogCount(Long userId, Long activityId);
	List<String> getActWhiteList();
	//ActivityLog getActivtyLogByUserId(Long userId,Long activityId);
}
