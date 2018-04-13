package com.winterframework.firefrog.phone.web.cotroller.dto;

import java.io.Serializable;

public class SearchUserResponse implements Serializable{
	
	private static final long serialVersionUID = 1610974483624776242L;
	private String username	;//用户帐号
	private String uid;
	private Float balance;//	用户馀额
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public Float getBalance() {
		return balance;
	}
	public void setBalance(Float balance) {
		this.balance = balance;
	}


}
