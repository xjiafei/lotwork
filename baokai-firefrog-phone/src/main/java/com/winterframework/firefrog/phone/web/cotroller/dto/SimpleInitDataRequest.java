package com.winterframework.firefrog.phone.web.cotroller.dto;

import java.io.Serializable;

public class SimpleInitDataRequest implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5463221841141101040L;

	private Integer lotteryId;

	public Integer getLotteryId() {
		return lotteryId;
	}

	public void setLotteryId(Integer lotteryId) {
		this.lotteryId = lotteryId;
	}
	
}
