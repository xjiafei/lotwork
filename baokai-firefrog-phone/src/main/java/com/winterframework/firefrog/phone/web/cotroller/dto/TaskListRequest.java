package com.winterframework.firefrog.phone.web.cotroller.dto;

import java.io.Serializable;

public class TaskListRequest implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4974338363237151821L;
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
