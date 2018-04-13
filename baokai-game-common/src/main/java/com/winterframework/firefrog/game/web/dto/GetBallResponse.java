package com.winterframework.firefrog.game.web.dto;

import javax.validation.constraints.NotNull;

public class GetBallResponse {
	@NotNull
	private int result = 0; //0成功 非0失败
	
	private String message; //消息  （一般失败，返回失败原因）
	private Long lotteryId;
	private Long issueCode;
	private String orderCode;
	private String ball;

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

	public int getResult() {
		return result;
	}

	public void setResult(int result) {
		this.result = result;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getBall() {
		return ball;
	}

	public void setBall(String ball) {
		this.ball = ball;
	}

	public String getOrderCode() {
		return orderCode;
	}

	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}
	
}
