package com.winterframework.firefrog.game.web.dto;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

public class DelGameAwardGroupRequest implements Serializable {

	private static final long serialVersionUID = 5028453435302612651L;

	@NotNull
	private Long lotteryId;
	@NotNull
	private String awardId;
	
	public DelGameAwardGroupRequest(){
		
	}

	public Long getLotteryId() {
		return lotteryId;
	}

	public void setLotteryId(Long lotteryId) {
		this.lotteryId = lotteryId;
	}

	public String getAwardId() {
		return awardId;
	}

	public void setAwardId(String awardId) {
		this.awardId = awardId;
	}
	
	
}
