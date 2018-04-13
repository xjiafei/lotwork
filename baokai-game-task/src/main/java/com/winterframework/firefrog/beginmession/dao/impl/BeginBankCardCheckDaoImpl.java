package com.winterframework.firefrog.beginmession.dao.impl;

import java.util.Date;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.google.common.collect.Maps;
import com.winterframework.firefrog.beginmession.dao.BeginBankCardCheckDao;
import com.winterframework.firefrog.beginmession.dao.vo.BeginBankCardCheck;
import com.winterframework.orm.dal.ibatis3.BaseIbatis3Dao;

@Repository
public class BeginBankCardCheckDaoImpl extends BaseIbatis3Dao<BeginBankCardCheck> implements
		BeginBankCardCheckDao {
	@Override
	public Integer updateCheckStatus(Long userId, String checkUser, Long checkStatus) {
		Map<String,Object> params =Maps.newHashMap();
		params.put("checkUser", checkUser);
		params.put("checkStatus", checkStatus);
		params.put("checkTime", new Date());
		params.put("userId",userId);
		return this.sqlSessionTemplate.update(this.getQueryPath("update"), params);
	}

	@Override
	public Long getByUserIdCount(Long userId) {
		return this.sqlSessionTemplate.selectOne(this.getQueryPath("getByUserIdCount"), userId);
	}
}
