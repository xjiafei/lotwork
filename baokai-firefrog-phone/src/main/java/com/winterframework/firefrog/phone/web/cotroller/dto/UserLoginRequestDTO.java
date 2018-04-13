package com.winterframework.firefrog.phone.web.cotroller.dto;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import com.winterframework.modules.validate.FFLength;

public class UserLoginRequestDTO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 204516480635735121L;
	@NotNull
	@FFLength(max = 16, min = 2)
	private String account;
	@NotNull
	private String passwd;
	@NotNull
	private Long loginIp;
	/**
	 *  瀏覽器
	 */
	private String userAgent;

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getPasswd() {
		return passwd;
	}

	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}

	public Long getLoginIp() {
		return loginIp;
	}

	public void setLoginIp(Long loginIp) {
		this.loginIp = loginIp;
	}
	/**
	 *  取得瀏覽器
	 */
	public String getUserAgent() {
		return userAgent;
	}
	/**
	 *  設定瀏覽器
	 */
	public void setUserAgent(String userAgent) {
		this.userAgent = userAgent;
	}

}
