package com.winterframework.firefrog.user.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.winterframework.firefrog.user.dao.IUserSlotExchangeActivityDao;
import com.winterframework.firefrog.user.dao.vo.UserSlotExchangeActivity;
import com.winterframework.firefrog.user.dao.vo.ViewUserSlotExchange;
import com.winterframework.orm.dal.ibatis3.BaseIbatis3Dao;

@Repository("userSlotExchangeActivityDaoImpl")
public class UserSlotExchangeActivityDaoImpl extends BaseIbatis3Dao<ViewUserSlotExchange>
		implements IUserSlotExchangeActivityDao {

	@Override
	public UserSlotExchangeActivity getUserSlotExchangeActivity(UserSlotExchangeActivity vo) {
		return sqlSessionTemplate.selectOne("getUserSlotExchangeActivity", vo);
	}

	@Override
	public List<UserSlotExchangeActivity> getUserSlotExchangeActivityList(UserSlotExchangeActivity vo) {
		return sqlSessionTemplate.selectList("getUserSlotExchangeActivityList", vo);
	}

	@Override
	public void insertUserSlotExchangeActivity(UserSlotExchangeActivity vo) {
		sqlSessionTemplate.update("insertUserSlotExchangeActivity", vo);
	}
	
	@Override
	public void updateUserSlotExchangeActivity(UserSlotExchangeActivity vo) {
		sqlSessionTemplate.update("updateUserSlotExchangeActivity", vo);
	}
}
