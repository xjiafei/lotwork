package com.winterframework.firefrog.phone.web.cotroller.dto;

import java.io.Serializable;

public class UserSecurityUsernameRequest implements Serializable {

	private static final long serialVersionUID = -4030546380771460482L;
	private String account;

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}
	
}
