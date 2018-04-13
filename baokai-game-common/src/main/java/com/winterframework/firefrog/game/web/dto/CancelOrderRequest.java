package com.winterframework.firefrog.game.web.dto;

import javax.validation.constraints.NotNull;

public class CancelOrderRequest {
	@NotNull
	private Long orderId;
	@NotNull
	private Long cancelTime;

	private Long userId;
	
	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public Long getCancelTime() {
		return cancelTime;
	}

	public void setCancelTime(Long cancelTime) {
		this.cancelTime = cancelTime;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

}
