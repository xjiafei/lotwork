package com.winterframework.firefrog.active.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.winterframework.firefrog.active.dao.IActivityDoubleboxLogDao;
import com.winterframework.firefrog.active.dao.vo.ActivityDoubleboxLog;
import com.winterframework.orm.dal.ibatis3.BaseIbatis3Dao;

@Repository("activityDoubleboxLogDaoImpl")
public class ActivityDobuleboxLogDaoImpl  extends BaseIbatis3Dao<ActivityDoubleboxLog> implements IActivityDoubleboxLogDao{
	private Logger log = LoggerFactory.getLogger(ActivityDobuleboxLogDaoImpl.class);
	
	
	public void saveActivityDoubleboxLog(ActivityDoubleboxLog info){
		insert(info);
	}
	
	public List<ActivityDoubleboxLog> queryTodayLogByUserId(Long userId, Long activityId){
		Map<String, Object> map =new HashMap<String, Object>();
		map.put("userId", userId);
		map.put("activityId", activityId);
		return this.sqlSessionTemplate.selectList(this.getQueryPath("queryTodayLogByUserId"), map);
	}
	
	public List<ActivityDoubleboxLog> getWinList(Long activityId,Long rownum){
		Map<String, Object> map =new HashMap<String, Object>();
		map.put("activityId", activityId);
		map.put("rownum", rownum);
		return this.sqlSessionTemplate.selectList(this.getQueryPath("getWinList"), map);
	}
	
}
