package com.winterframework.firefrog.game.dao.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.winterframework.firefrog.game.dao.IActivityLogDao;
import com.winterframework.firefrog.game.dao.vo.ActivityLog;
import com.winterframework.orm.dal.ibatis3.BaseIbatis3Dao;

@Repository("activityLogDaoImpl")
public class ActivityLogDaoImpl  extends BaseIbatis3Dao<ActivityLog> implements IActivityLogDao{
	private Logger log = LoggerFactory.getLogger(ActivityLogDaoImpl.class);
	private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
	public void saveActivityLog(ActivityLog info){
		insert(info);
//		Map<String, Object> map =new HashMap<String, Object>();
//		map.put("userId", info.getUserId());
//		map.put("activityId", info.getActivityId());
//		map.put("prize", info.getPrize());
//		map.put("createTime", info.getCreateTime());
//		map.put("awardTime", info.getAwardTime());
//		map.put("status", info.getStatus());
//		map.put("memo", info.getMemo());
//		log.info("before insert param : " + map.toString());
//		this.sqlSessionTemplate.insert(this.getQueryPath("saveActivityLog"), map);
	}
	
	public Long queryTodayActivityLogCount(Long userId, Long activityId, Date beginTime, Date endTime){
		Map<String, Object> map =new HashMap<String, Object>();
		
		map.put("userId", userId);
		map.put("activityId", activityId);
		map.put("beginTime", beginTime);
		map.put("endTime", endTime);
		return this.sqlSessionTemplate.selectOne(this.getQueryPath("queryTodayActivityLogCount"), map);
		
	}
	
	public Float queryPrizeTotal(Long userId, Long activityId, Date beginTime, Date endTime){
		Map<String, Object> map =new HashMap<String, Object>();
		map.put("userId", userId);
		map.put("activityId", activityId);
		map.put("beginTime", beginTime);
		map.put("endTime", endTime);
		return this.sqlSessionTemplate.selectOne(this.getQueryPath("queryPrizeTotal"), map);		
	}
}
