package com.winterframework.firefrog.phone.web.cotroller.dto;

import java.io.Serializable;

public class GameListRequest implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7536222359973425756L;
	private Long chanId;
	private Long lotteryId;
	public Long getChanId() {
		return chanId;
	}

	public void setChanId(Long chanId) {
		this.chanId = chanId;
	}
	public Long getLotteryId() {
		return lotteryId;
	}

	public void setLotteryId(Long lotteryId) {
		this.lotteryId = lotteryId;
	}
}
