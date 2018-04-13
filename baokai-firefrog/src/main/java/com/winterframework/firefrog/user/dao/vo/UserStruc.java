package com.winterframework.firefrog.user.dao.vo;

import java.util.List;

public class UserStruc {
	private Long userId;
	private String account;
	private List<String> accounts;
	

	public List<String> getAccounts() {
		return accounts;
	}

	public void setAccounts(List<String> accounts) {
		this.accounts = accounts;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}
}