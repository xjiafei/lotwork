package com.winterframework.firefrog.phone.web.cotroller.dto;

import java.io.Serializable;

public class InitDataRequest implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2850324123501933186L;

	private Integer lotteryId;

	public Integer getLotteryId() {
		return lotteryId;
	}

	public void setLotteryId(Integer lotteryId) {
		this.lotteryId = lotteryId;
	}
	
}
