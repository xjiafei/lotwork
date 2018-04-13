package com.winterframework.firefrog.user.dao;

import java.util.List;

import com.winterframework.firefrog.user.entity.LoginLog;

public interface IUserLoginLogDao {

	public List<LoginLog> queryUserLoginLog(long id);

	/** 
	* @Title: saveLoginInfo 
	* @Description: 保存用户登录记录 
	* @param loginLog
	*/
	public void saveLoginInfo(LoginLog loginLog);

}
