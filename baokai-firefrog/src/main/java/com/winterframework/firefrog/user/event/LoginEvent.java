package com.winterframework.firefrog.user.event;

import com.winterframework.firefrog.common.event.FirefrogEvent;
import com.winterframework.firefrog.fund.entity.UserFund;

/** 
*  登陆系统时是事件类
*/
public class LoginEvent extends FirefrogEvent {

	private UserFund userFund;

	public LoginEvent(Object source) {
		super(source);
	}

	public UserFund getUserFund() {
		return userFund;
	}

	public void setUserFund(UserFund userFund) {
		this.userFund = userFund;
	}

	private static final long serialVersionUID = -7641669719634075013L;

}
