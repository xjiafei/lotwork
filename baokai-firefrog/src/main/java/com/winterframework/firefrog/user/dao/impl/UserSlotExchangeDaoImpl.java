package com.winterframework.firefrog.user.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.winterframework.firefrog.user.dao.IUserSlotExchangeDao;
import com.winterframework.firefrog.user.dao.vo.UserSlotExchange;
import com.winterframework.firefrog.user.dao.vo.UserSlotExchangeCount;
import com.winterframework.firefrog.user.dao.vo.ViewUserSlotExchange;
import com.winterframework.orm.dal.ibatis3.BaseIbatis3Dao;

@Repository("userSlotExchangeDaoImpl")
public class UserSlotExchangeDaoImpl extends BaseIbatis3Dao<UserSlotExchange>
		implements IUserSlotExchangeDao {

	@Override
	public Long getViewUserSlotExchangeCount(ViewUserSlotExchange vo) {
		return sqlSessionTemplate.selectOne("getViewUserSlotExchangeCount", vo);
	}
	
	@Override
	public ViewUserSlotExchange getRandomViewUserSlotExchange(ViewUserSlotExchange vo) {
		return sqlSessionTemplate.selectOne("getRandomViewUserSlotExchange", vo);
	}
	
	@Override
	public ViewUserSlotExchange getViewUserSlotExchange(ViewUserSlotExchange vo) {
		return sqlSessionTemplate.selectOne("getViewUserSlotExchange", vo);
	}

	@Override
	public UserSlotExchangeCount getUserSlotExchangeCheck(UserSlotExchange vo) {
		return sqlSessionTemplate.selectOne("getUserSlotExchangeCheck", vo);
	}
	
	@Override
	public List<UserSlotExchangeCount> getUserSlotExchangeCheckList(UserSlotExchange vo) {
		return sqlSessionTemplate.selectList("getUserSlotExchangeCheckList", vo);
	}

	@Override
	public List<ViewUserSlotExchange> getViewUserSlotExchangeList(ViewUserSlotExchange vo) {
		return sqlSessionTemplate.selectList("getViewUserSlotExchangeList", vo);
	}

	@Override
	public void updateUserSlotExchangeByRestart(UserSlotExchange vo) {
		sqlSessionTemplate.update("updateUserSlotExchangeByRestart", vo);
	}
	
	@Override
	public void updateUserSlotExchange(UserSlotExchange vo) {
		sqlSessionTemplate.update("updateUserSlotExchange", vo);
	}
}
