package com.winterframework.firefrog.user.dao.impl;

import org.springframework.stereotype.Repository;

import com.winterframework.firefrog.user.dao.IUserSlotExchangeLogDao;
import com.winterframework.firefrog.user.dao.vo.UserSlotExchangeLog;
import com.winterframework.orm.dal.ibatis3.BaseIbatis3Dao;

@Repository("userSlotExchangeLogDaoImpl")
public class UserSlotExchangeLogDaoImpl extends
		BaseIbatis3Dao<UserSlotExchangeLog> implements IUserSlotExchangeLogDao {

	@Override
	public void insertUserSlotExchangeLog(UserSlotExchangeLog vo) {
		sqlSessionTemplate.insert("insertUserSlotExchangeLog", vo);
	}
}
