package com.winterframework.firefrog.user.web.dto;

public class UserSlotOccupancyRequest {
	private String cellularPhone;
	private String exchangeNumber;
	private Long exchangePrize;
	private String activityType;

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

	public Long getExchangePrize() {
		return exchangePrize;
	}

	public void setExchangePrize(Long exchangePrize) {
		this.exchangePrize = exchangePrize;
	}

	public String getActivityType() {
		return activityType;
	}

	public void setActivityType(String activityType) {
		this.activityType = activityType;
	}

}
