package com.winterframework.firefrog.phone.web.cotroller.dto;

import java.io.Serializable;

public class UserBetInfoSumStruc implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7337205709882369264L;

	private Long betAmount;
	
	public Long getBetAmount() {
		return betAmount;
	}

	public void setBetAmount(Long betAmount) {
		this.betAmount = betAmount;
	}

	public Long getRetPoint() {
		return retPoint;
	}

	public void setRetPoint(Long retPoint) {
		this.retPoint = retPoint;
	}

	public Long getWinAmount() {
		return winAmount;
	}

	public void setWinAmount(Long winAmount) {
		this.winAmount = winAmount;
	}

	private Long retPoint;
	
	private Long winAmount;
}
