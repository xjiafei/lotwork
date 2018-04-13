package com.winterframework.firefrog.user.web.dto;

public class UserSlotOccupancyResponse {
	private String cellularPhone;
	private String exchangeNumber;
	private Long exchangeAmount;
	private Long exchangePrize;
	private Long status;

	public String getCellularPhone() {
		return cellularPhone;
	}

	public void setCellularPhone(String cellularPhone) {
		this.cellularPhone = cellularPhone;
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

	public Long getExchangePrize() {
		return exchangePrize;
	}

	public void setExchangePrize(Long exchangePrize) {
		this.exchangePrize = exchangePrize;
	}

	public Long getStatus() {
		return status;
	}

	public void setStatus(Long status) {
		this.status = status;
	}

}
