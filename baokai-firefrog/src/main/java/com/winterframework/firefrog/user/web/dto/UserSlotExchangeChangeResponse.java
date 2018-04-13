package com.winterframework.firefrog.user.web.dto;

public class UserSlotExchangeChangeResponse {
	private Long exchangePrize;
	private String exchangeNumber;
	private Long status;
	private String url;

	public Long getExchangePrize() {
		return exchangePrize;
	}

	public void setExchangePrize(Long exchangePrize) {
		this.exchangePrize = exchangePrize;
	}

	public String getExchangeNumber() {
		return exchangeNumber;
	}

	public void setExchangeNumber(String exchangeNumber) {
		this.exchangeNumber = exchangeNumber;
	}

	public Long getStatus() {
		return status;
	}

	public void setStatus(Long status) {
		this.status = status;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}
