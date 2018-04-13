package com.winterframework.firefrog.beginmession.web.dto;

import java.io.Serializable;

public class BeginMissionRequest implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1022522455524316053L;

	private Long userId;
	
	//傳入的砸蛋類型
	private Long lotteryType;
	
	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getLotteryType() {
		return lotteryType;
	}

	public void setLotteryType(Long lotteryType) {
		this.lotteryType = lotteryType;
	}
	
}
