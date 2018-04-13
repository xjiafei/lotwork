package com.winterframework.firefrog.phone.web.cotroller.dto;

public class MethodInitRequest {
	private Long lotteryId;
	private String betTypeCode;
	
	public Long getLotteryId() {
		return lotteryId;
	}
	public void setLotteryId(Long lotteryId) {
		this.lotteryId = lotteryId;
	}
	public String getBetTypeCode() {
		return betTypeCode;
	}
	public void setBetTypeCode(String betTypeCode) {
		this.betTypeCode = betTypeCode;
	}
	 
	
	
}
