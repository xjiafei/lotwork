package com.winterframework.firefrog.active.dao.impl;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.winterframework.firefrog.active.dao.IActivityRedenvelopeLogDao;
import com.winterframework.firefrog.active.dao.vo.ActivityRedenvelopeLog;
import com.winterframework.orm.dal.ibatis3.BaseIbatis3Dao;

@Repository("activityRedenvelopeLogDaoImpl")
public class ActivityRedenvelopeLogDaoImpl  extends BaseIbatis3Dao<ActivityRedenvelopeLog> implements IActivityRedenvelopeLogDao{
	private Logger log = LoggerFactory.getLogger(ActivityRedenvelopeLogDaoImpl.class);
	
	
	public void saveActivityDoubleboxLog(ActivityRedenvelopeLog info){
		insert(info);
	}
	
	public Long queryTodayCountgByVipLvl(Integer vipLvl, Long activityId){
		Map<String, Object> map =new HashMap<String, Object>();
		map.put("vipLvl", vipLvl);
		map.put("activityId", activityId);
		return this.sqlSessionTemplate.selectOne(this.getQueryPath("queryTodayCountgByVipLvl"), map);
	}
	
	public Long queryTodayLottoCount(Integer vipLvl, Long activityId, Long lotto_prize){
		Map<String, Object> map =new HashMap<String, Object>();
		map.put("vipLvl", vipLvl);
		map.put("activityId", activityId);
		map.put("redEnvelope", lotto_prize);
		return this.sqlSessionTemplate.selectOne(this.getQueryPath("queryTodayLottoCount"), map);
	}
	
	public ActivityRedenvelopeLog queryTodayRedEnvelopeByUserId(Long activityId, Long userId){
		Map<String, Object> map =new HashMap<String, Object>();
		map.put("userId", userId);
		map.put("activityId", activityId);
		return this.sqlSessionTemplate.selectOne(this.getQueryPath("queryTodayRedEnvelopeByUserId"), map);
	}

	
	
}
