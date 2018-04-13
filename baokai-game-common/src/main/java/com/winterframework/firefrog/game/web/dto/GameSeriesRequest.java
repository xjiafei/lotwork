package com.winterframework.firefrog.game.web.dto;

import java.io.Serializable;

public class GameSeriesRequest implements Serializable {

	private static final long serialVersionUID = -1379655898436910829L;

	private Long lotteryId;
	private Integer status;
	
	public GameSeriesRequest(){
		
	}

	public Long getLotteryId() {
		return lotteryId;
	}

	public void setLotteryId(Long lotteryId) {
		this.lotteryId = lotteryId;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	
}
