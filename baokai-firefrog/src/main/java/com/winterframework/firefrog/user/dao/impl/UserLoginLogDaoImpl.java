package com.winterframework.firefrog.user.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.winterframework.firefrog.user.dao.IUserLoginLogDao;
import com.winterframework.firefrog.user.dao.vo.UserLoginLog;
import com.winterframework.firefrog.user.dao.vo.VOConverter;
import com.winterframework.firefrog.user.entity.LoginLog;
import com.winterframework.modules.page.Page;
import com.winterframework.modules.page.PageRequest;
import com.winterframework.orm.dal.ibatis3.BaseIbatis3Dao;

@Repository("userLoginLogDaoImpl")
public class UserLoginLogDaoImpl extends BaseIbatis3Dao<UserLoginLog> implements IUserLoginLogDao {

	@Override
	public List<LoginLog> queryUserLoginLog(long userId) {
		UserLoginLog entity = new UserLoginLog();
		entity.setUserId(userId);
		PageRequest<UserLoginLog> page = new PageRequest<UserLoginLog>();
		UserLoginLog searchDao = new UserLoginLog();
		searchDao.setUserId(userId);
		page.setSearchDo(searchDao);
		page.setPageSize(5);
		page.setSortColumns("LOGIN_DATE desc");
		Page<UserLoginLog> pageResult = this.pageQuery(page, "");
		List<LoginLog> list = new ArrayList<LoginLog>();
		for (UserLoginLog log : pageResult.getResult()) {
			LoginLog login = new LoginLog();
			login.setLoginDate(log.getLoginDate());
			login.setLoginIP(log.getLoginIp());
			list.add(login);
		}
		return list;
	}

	@Override
	public void saveLoginInfo(LoginLog loginLog) {
		UserLoginLog userLoginLog = VOConverter.loginLog2UserLoginLog(loginLog);
		insert(userLoginLog);
	}
}
