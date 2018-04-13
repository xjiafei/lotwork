package com.winterframework.firefrog.game.web.dto;

import java.io.Serializable;

public class WinsSumReportQueryRequest implements Serializable {

	private static final long serialVersionUID = 3610712773743687509L;

	private Long lotteryid;
	private Long startCreateTime;
	private Long endCreateTime;

	public Long getLotteryid() {
		return lotteryid;
	}

	public void setLotteryid(Long lotteryid) {
		this.lotteryid = lotteryid;
	}

	public Long getStartCreateTime() {
		return startCreateTime;
	}

	public void setStartCreateTime(Long startCreateTime) {
		this.startCreateTime = startCreateTime;
	}

	public Long getEndCreateTime() {
		return endCreateTime;
	}

	public void setEndCreateTime(Long endCreateTime) {
		this.endCreateTime = endCreateTime;
	}

}
