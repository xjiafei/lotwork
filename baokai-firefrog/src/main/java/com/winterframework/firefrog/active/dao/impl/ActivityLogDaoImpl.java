package com.winterframework.firefrog.active.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.winterframework.firefrog.active.dao.IActivityLogDao;
import com.winterframework.firefrog.active.dao.vo.ActivityLog;
import com.winterframework.orm.dal.ibatis3.BaseIbatis3Dao;

@Repository("activityLogDaoImpl")
public class ActivityLogDaoImpl  extends BaseIbatis3Dao<ActivityLog> implements IActivityLogDao{
	private Logger log = LoggerFactory.getLogger(ActivityLogDaoImpl.class);

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
	
	public Long queryActivityLogCount(Long userId, Long activityId){
		Map<String, Object> map =new HashMap<String, Object>();
		map.put("userId", userId);
		map.put("activityId", activityId);
		
		return this.sqlSessionTemplate.selectOne(this.getQueryPath("queryActivityLogCount"), map);
		
	}
	
	public List<String> getActWhiteList(Long activityId){
		List<String> lists = null;
		Map<String, Object> map =new HashMap<String, Object>();
		map.put("activityId", activityId);
		lists = this.sqlSessionTemplate.selectList(this.getQueryPath("queryWhiteList"),map);
		log.info("lists : " + lists);
		log.info("lists size : " + lists.size());
		if(lists.size() > 0){
			log.info("Optional.of(lists).isPresent()");
			return lists;
		}
		log.info("return null~~~~~!");
		return null;
	}
	
	public Integer getActWhiteListByIdAndAccount(Long activityId, String account){
		Map<String, Object> map =new HashMap<String, Object>();
		map.put("activityId", activityId);
		map.put("account", account);
		Integer count = this.sqlSessionTemplate.selectOne(this.getQueryPath("queryWhiteListByIdAndAccount"), map);
		log.info("activityId : {}, account : {}, count : {}", activityId, account, count);
		return count;
	}

	public ActivityLog isSignUp(Long userId, Long activityId, Date beginTime,Date endTime) {
		Map<String, Object> map =new HashMap<String, Object>();
		map.put("userId", userId);
		map.put("activityId", activityId);
		map.put("startTime", beginTime);
		map.put("endTime", endTime);
		return this.sqlSessionTemplate.selectOne(this.getQueryPath("isSignUp"), map);
	}

	@Override
	public List<String> checkMultiple() {
		return this.sqlSessionTemplate.selectList(this.getQueryPath("checkMultiple"));
	}
	
//	public ActivityLog getActivtyLogByUserId(Long userId,Long activityId){
//		Map<String, Object> map =new HashMap<String, Object>();
//		map.put("userId", userId);
//		map.put("activityId", activityId);
//		return (ActivityLog)this.sqlSessionTemplate.selectList(this.getQueryPath("getActivtyLogByUserId"),map).get(0);
//	}
	
}
