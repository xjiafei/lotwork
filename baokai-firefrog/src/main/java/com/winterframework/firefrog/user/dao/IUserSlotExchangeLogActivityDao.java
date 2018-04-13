package com.winterframework.firefrog.user.dao;

import java.util.List;

import com.winterframework.firefrog.user.dao.vo.UserSlotExchangeActivity;

public interface IUserSlotExchangeLogActivityDao {

	public UserSlotExchangeActivity getUserSlotExchangeActivity(UserSlotExchangeActivity vo);

	public List<UserSlotExchangeActivity> getUserSlotExchangeActivityList(UserSlotExchangeActivity vo);

	public void insertUserSlotExchangeActivity(UserSlotExchangeActivity vo);

	public void updateUserSlotExchangeActivity(UserSlotExchangeActivity vo);

}
