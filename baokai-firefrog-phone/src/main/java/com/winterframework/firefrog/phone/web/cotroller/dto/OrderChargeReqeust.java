package com.winterframework.firefrog.phone.web.cotroller.dto;

public class OrderChargeReqeust {

	private Long lotteryId;
	private String amount;
	public Long getLotteryId() {
		return lotteryId;
	}
	public void setLotteryId(Long lotteryId) {
		this.lotteryId = lotteryId;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	
	
}
