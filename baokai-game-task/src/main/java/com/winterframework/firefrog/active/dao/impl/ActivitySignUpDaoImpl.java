package com.winterframework.firefrog.active.dao.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.winterframework.firefrog.active.dao.IActivitySignUpDao;
import com.winterframework.firefrog.active.dao.vo.ActivitySignUp;
import com.winterframework.orm.dal.ibatis3.BaseIbatis3Dao;

@Repository("activitySignUpDaoImpl")
public class ActivitySignUpDaoImpl extends BaseIbatis3Dao<ActivitySignUp> implements IActivitySignUpDao{

	private Logger log = LoggerFactory.getLogger(ActivitySignUpDaoImpl.class);
	
	@Override
	public Long getUserQuanlification(ActivitySignUp actSignUpVO) {
		log.debug("getUserQuanlification: Accunt=" + actSignUpVO.getAccunt() + " ActivityId=" + actSignUpVO.getActivityId());
		return this.sqlSessionTemplate.selectOne("queryUser", actSignUpVO);
	}

}
