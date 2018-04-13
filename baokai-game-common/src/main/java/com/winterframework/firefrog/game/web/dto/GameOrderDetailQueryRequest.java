package com.winterframework.firefrog.game.web.dto;

import java.io.Serializable;

public class GameOrderDetailQueryRequest implements Serializable {

	private static final long serialVersionUID = 4610165398610161890L;

	/** 订单ID */
	private Long orderId;
	
	private String orderCode;
	
	private Long userId;
	
	private Long sysUserId;

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public String getOrderCode() {
		return orderCode;
	}

	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getSysUserId() {
		return sysUserId;
	}

	public void setSysUserId(Long sysUserId) {
		this.sysUserId = sysUserId;
	}
}
