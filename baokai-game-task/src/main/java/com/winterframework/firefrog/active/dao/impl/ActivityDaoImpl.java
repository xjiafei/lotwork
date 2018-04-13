package com.winterframework.firefrog.active.dao.impl;


import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.winterframework.firefrog.active.dao.IActivityDao;
import com.winterframework.firefrog.active.dao.vo.Activity;
import com.winterframework.orm.dal.ibatis3.BaseIbatis3Dao;

@Repository("activityDaoImpl")
public class ActivityDaoImpl extends BaseIbatis3Dao<Activity> implements IActivityDao{

	private Logger log = LoggerFactory.getLogger(ActivityDaoImpl.class);
	
	@Override
	public Activity getActivityByCode(Activity actVO) {
		log.debug("getActivityByCode: ActivityCode=" + actVO.getActivityCode());
		return this.sqlSessionTemplate.selectOne("queryActivityByCode", actVO);		
	}

	@Override
	public Long getUserAmountPerDay(Map<String, Long> amountMap) {
		log.debug("getUserAmountPerDay: userId=" + amountMap.get("userId"));
		return this.sqlSessionTemplate.selectOne("queryUserAmountPerDay", amountMap);		
	}
}
