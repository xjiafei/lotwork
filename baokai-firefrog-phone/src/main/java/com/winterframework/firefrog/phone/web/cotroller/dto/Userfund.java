package com.winterframework.firefrog.phone.web.cotroller.dto;

public class Userfund {

	private Float availablebalance;//	可用金额
	private Long userid;//	使用者id
	private String username	;//使用者帐号
	public Float getAvailablebalance() {
		return availablebalance;
	}
	public void setAvailablebalance(Float availablebalance) {
		this.availablebalance = availablebalance;
	}
	public Long getUserid() {
		return userid;
	}
	public void setUserid(Long userid) {
		this.userid = userid;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	
	
}
