package com.winterframework.firefrog.user.entity;

public class UserResetPwdRequest {
	private String ptAccount;
	private Long userIp;

	public String getPtAccount() {
		return ptAccount;
	}

	public void setPtAccount(String ptAccount) {
		this.ptAccount = ptAccount;
	}

	public Long getUserIp() {
		return userIp;
	}

	public void setUserIp(Long userIp) {
		this.userIp = userIp;
	}

}
