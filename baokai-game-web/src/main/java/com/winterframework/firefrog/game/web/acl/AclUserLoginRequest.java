package com.winterframework.firefrog.game.web.acl;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

public class AclUserLoginRequest implements Serializable {
	private static final long serialVersionUID = -6857553049874227345L;
	
	@NotNull
	@Length(max = 16, min = 4)
	private String account;
	@NotNull
	private String passwd;
	
	@NotNull
	private String loginIp;

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

	public String getLoginIp() {
		return loginIp;
	}

	public void setLoginIp(String loginIp) {
		this.loginIp = loginIp;
	}

}
