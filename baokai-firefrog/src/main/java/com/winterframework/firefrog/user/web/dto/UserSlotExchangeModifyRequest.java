package com.winterframework.firefrog.user.web.dto;

public class UserSlotExchangeModifyRequest {
	private Long type;
	private String exchangeNumber;

	public Long getType() {
		return type;
	}
	public void setType(Long type) {
		this.type = type;
	}
	public String getExchangeNumber() {
		return exchangeNumber;
	}
	public void setExchangeNumber(String exchangeNumber) {
		this.exchangeNumber = exchangeNumber;
	}
}
