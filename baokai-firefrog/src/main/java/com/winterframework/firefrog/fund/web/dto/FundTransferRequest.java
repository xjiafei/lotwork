package com.winterframework.firefrog.fund.web.dto;

public class FundTransferRequest {

	private String account;	
	private Long startDate;
	private Long endDate;
	private Long direction;
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public Long getStartDate() {
		return startDate;
	}
	public void setStartDate(Long startDate) {
		this.startDate = startDate;
	}
	public Long getEndDate() {
		return endDate;
	}
	public void setEndDate(Long endDate) {
		this.endDate = endDate;
	}
	public Long getDirection() {
		return direction;
	}
	public void setDirection(Long direction) {
		this.direction = direction;
	}



}
