package com.winterframework.firefrog.user.entity;

import java.util.Date;

public class LoginLog {

	private User user;

	private Date loginDate;

	private Long loginIP;
	/**
	 * 登入來源渠道 
	 * 
	 */
	private Integer channelId;

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

	/**
	 * 取得登入來源渠道 
	 * return Integer
	 */
	public Integer getChannelId() {
		return channelId;
	}
	/**
	 * 設置登入來源渠道 
	 * 
	 */
	public void setChannelId(Integer channelId) {
		this.channelId = channelId;
	}

}
