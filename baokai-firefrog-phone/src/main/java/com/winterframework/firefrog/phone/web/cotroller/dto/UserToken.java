package com.winterframework.firefrog.phone.web.cotroller.dto;

import java.io.Serializable;
import java.util.Date;

public class UserToken implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5119348940655215965L;
	private String userName;
	private Long userId;
	private Date loginDate;
	private Date timeOut;
	private String token;
	private String security; //是否设置安全密码1设置，0未设置
	
	
	public String getSecurity() {
		return security;
	}
	public void setSecurity(String security) {
		this.security = security;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public Date getTimeOut() {
		return timeOut;
	}
	public void setTimeOut(Date timeOut) {
		this.timeOut = timeOut;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public Date getLoginDate() {
		return loginDate;
	}
	public void setLoginDate(Date loginDate) {
		this.loginDate = loginDate;
	}

}
