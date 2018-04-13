package com.winterframework.firefrog.user.web.dto;

public class UserSlotExchangeCheckResponse {
	private Long status;
	private Long type;
	private String exchangeNumber;
	private Long exchangeAmount;

	public Long getStatus() {
		return status;
	}
	public void setStatus(Long status) {
		this.status = status;
	}
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
	public Long getExchangeAmount() {
		return exchangeAmount;
	}
	public void setExchangeAmount(Long exchangeAmount) {
		this.exchangeAmount = exchangeAmount;
	}
}
