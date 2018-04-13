package com.winterframework.firefrog.subsys.web.dto;

import java.util.Date;

public class SubSysFundRequestDTO {

	private String token;
	private String account;
	private Long lotteryId;
	
	
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
	public Long getLotteryId() {
		return lotteryId;
	}
	public void setLotteryId(Long lotteryId) {
		this.lotteryId = lotteryId;
	}
	
	
}
