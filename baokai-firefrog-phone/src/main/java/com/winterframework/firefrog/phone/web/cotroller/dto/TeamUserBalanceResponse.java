package com.winterframework.firefrog.phone.web.cotroller.dto;

import java.io.Serializable;

public class TeamUserBalanceResponse implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1142152187973802050L;

	private Float balance;//	余额
	private String username;//	用户名
	public Float getBalance() {
		return balance;
	}
	public void setBalance(Float balance) {
		this.balance = balance;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	
}
