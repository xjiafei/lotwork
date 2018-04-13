package com.winterframework.firefrog.user.entity;

import java.io.Serializable;
import java.util.Date;

public class GameAwardUserGroupDTO implements Serializable{

	private static final long serialVersionUID = 1L;
	
	//用户ID
	private Long userId;
	
	private int betType;
	
	private Long lotteryId;
	
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public int getBetType() {
		return betType;
	}
	public void setBetType(int betType) {
		this.betType = betType;
	}
	public Long getLotteryId() {
		return lotteryId;
	}
	public void setLotteryId(Long lotteryId) {
		this.lotteryId = lotteryId;
	}	
	
}
