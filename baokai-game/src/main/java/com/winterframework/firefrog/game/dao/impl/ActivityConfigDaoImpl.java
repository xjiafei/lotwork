package com.winterframework.firefrog.game.dao.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.winterframework.firefrog.game.dao.IActivityConfigDao;
import com.winterframework.firefrog.game.dao.vo.ActivityConfig;
import com.winterframework.orm.dal.ibatis3.BaseIbatis3Dao;

@Repository("activityConfigDaoImpl")
public class ActivityConfigDaoImpl  extends BaseIbatis3Dao<ActivityConfig> implements IActivityConfigDao{
	private Logger log = LoggerFactory.getLogger(ActivityConfigDaoImpl.class);
	@Override
	public ActivityConfig getActCfgById(Long act_id) {
		return sqlSessionTemplate.selectOne("getActCfgById",act_id);
	}
	
}
