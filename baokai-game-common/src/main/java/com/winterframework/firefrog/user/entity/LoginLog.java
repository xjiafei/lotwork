package com.winterframework.firefrog.user.entity;

import java.util.Date;

public class LoginLog {

	private User user;

	private Date loginDate;

	private Long loginIP;

	public Date getLoginDate() {
		return loginDate;
	}

	public void setLoginDate(Date loginDate) {
		this.loginDate = loginDate;
	}

	public Long getLoginIP() {
		return loginIP;
	}

	public void setLoginIP(Long loginIP) {
		this.loginIP = loginIP;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}
