package com.winterframework.firefrog.fund.listener;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.winterframework.firefrog.common.event.FirefrogEvent;
import com.winterframework.firefrog.common.event.FirefrogEventListener;
import com.winterframework.firefrog.fund.dao.IFundDao;
import com.winterframework.firefrog.fund.entity.UserFund;
import com.winterframework.firefrog.user.event.LoginEvent;

/** 
*  登陆系统时的事件监听类
*/
@Component("loginEventListener")
public class LoginEventListener extends FirefrogEventListener{
	
	@Resource(name = "fundDaoImpl")
	private IFundDao fundDao;

	@Override
	public void onApplicationEvent(FirefrogEvent event) {
		if (event instanceof LoginEvent) {
			LoginEvent loginEvent =(LoginEvent) event;
			UserFund fund = fundDao.getUserFund(Long.valueOf(loginEvent.getSource().toString()));
			loginEvent.setUserFund(fund);
		}
	}

}
