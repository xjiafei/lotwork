package com.winterframework.firefrog.game.web.dto;

import java.util.Date;

import javax.validation.constraints.NotNull;

public class ZKBallRequest {
	@NotNull
	private Long LotteryId;
	
	@NotNull
	private String webIssueCode;
	
	@NotNull
	private Long issueCode;
	
	@NotNull
	private String ball;
	
	@NotNull
	private Date  sendTime;

	public Long getLotteryId() {
		return LotteryId;
	}

	public void setLotteryId(Long lotteryId) {
		LotteryId = lotteryId;
	}

	public String getWebIssueCode() {
		return webIssueCode;
	}

	public void setWebIssueCode(String webIssueCode) {
		this.webIssueCode = webIssueCode;
	}


	public Long getIssueCode() {
		return issueCode;
	}

	public void setIssueCode(Long issueCode) {
		this.issueCode = issueCode;
	}

	public String getBall() {
		return ball;
	}

	public void setBall(String ball) {
		this.ball = ball;
	}

	public Date getSendTime() {
		return sendTime;
	}

	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}
}
