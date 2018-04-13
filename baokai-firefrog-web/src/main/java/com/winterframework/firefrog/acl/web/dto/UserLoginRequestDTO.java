package com.winterframework.firefrog.acl.web.dto;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

public class UserLoginRequestDTO implements Serializable {

	private static final long serialVersionUID = -3409142989536567282L;

	@NotNull
	@Length(max = 16, min = 2)
	private String account;
	@NotNull
	private String passwd;
	@NotNull
	private Long loginIp;

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

}
