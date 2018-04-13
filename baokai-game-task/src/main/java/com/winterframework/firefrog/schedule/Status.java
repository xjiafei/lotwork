package com.winterframework.firefrog.schedule;

import com.winterframework.firefrog.fund.entity.FundStatus;

public enum Status implements FundStatus {
	untreated(0L), addCoin(1L), confiscate(2L), refundCheck(3L), refunding(4L), refundSucces(5L), refundFailed(6L), partRefund(
			7L), addCoinrefundCheck(8L), confiscaterefundCheck(9L);
	private Long statis;

	private Status(Long value) {
		this.statis = value;
	}
	
	public  Status creatStatus(Long value) {
		for (Status aLight : Status.values()) {
			if (aLight.getStatis().equals(value)) {
				return aLight;
			}
		}
		return null;
	}

	public Long getStatis() {
		return statis;
	}

	public void setStatis(Long statis) {
		this.statis = statis;
	}

	

}