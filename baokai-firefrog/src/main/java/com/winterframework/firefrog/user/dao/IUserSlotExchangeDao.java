package com.winterframework.firefrog.user.dao;

import java.util.List;

import com.winterframework.firefrog.user.dao.vo.UserSlotExchange;
import com.winterframework.firefrog.user.dao.vo.UserSlotExchangeCount;
import com.winterframework.firefrog.user.dao.vo.ViewUserSlotExchange;

public interface IUserSlotExchangeDao {

	public Long getViewUserSlotExchangeCount(ViewUserSlotExchange vo);
	
	public ViewUserSlotExchange getRandomViewUserSlotExchange(ViewUserSlotExchange vo);
	
	public ViewUserSlotExchange getViewUserSlotExchange(ViewUserSlotExchange vo);

	public UserSlotExchangeCount getUserSlotExchangeCheck(UserSlotExchange vo);
	
	public List<UserSlotExchangeCount> getUserSlotExchangeCheckList(UserSlotExchange vo);

	public List<ViewUserSlotExchange> getViewUserSlotExchangeList(ViewUserSlotExchange vo);

	public void updateUserSlotExchangeByRestart(UserSlotExchange vo);
	
	public void updateUserSlotExchange(UserSlotExchange vo);

}
