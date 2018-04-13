package com.winterframework.firefrog.phone.web.cotroller.dto;

import java.io.Serializable;

public class UserStruc implements Serializable{
	
	private static final long serialVersionUID = 7977924043065375589L;
	private Long userId;
	private String account;
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}

}
