package com.winterframework.firefrog.game.web.dto;

import java.io.Serializable;

public class GameWarnDetailQueryDto implements Serializable {
	
	private static final long serialVersionUID = 5825833055197820957L;
	private Long lotteryId;
	private Long issueCode;
	public Long getLotteryId() {
		return lotteryId;
	}
	public void setLotteryId(Long lotteryId) {
		this.lotteryId = lotteryId;
	}
	public Long getIssueCode() {
		return issueCode;
	}
	public void setIssueCode(Long issueCode) {
		this.issueCode = issueCode;
	}
	
	

}
