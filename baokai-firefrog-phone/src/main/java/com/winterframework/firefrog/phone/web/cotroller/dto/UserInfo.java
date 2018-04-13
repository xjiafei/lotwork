package com.winterframework.firefrog.phone.web.cotroller.dto;

public class UserInfo {

	private Long userid	;//id
	private String username	;//用户名
	private Double  availablebalance;//	可提现金额
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
	public Double getAvailablebalance() {
		return availablebalance;
	}
	public void setAvailablebalance(Double availablebalance) {
		this.availablebalance = availablebalance;
	}

}
