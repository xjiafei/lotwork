package com.winterframework.firefrog.user.web.dto;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import com.winterframework.modules.validate.FFLength;

public class UserLoginRequestDTO implements Serializable {

	private static final long serialVersionUID = -3409142989536567282L;

	private String account;
	
	private String passwd;
	
	@NotNull
	private Long loginIp;
	
	private Integer isNewUser;
	
	private String token;
	/**
	 *  瀏覽器
	 */
	private String userAgent;

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

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

	public Integer getIsNewUser() {
		return isNewUser;
	}

	public void setIsNewUser(Integer isNewUser) {
		this.isNewUser = isNewUser;
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
