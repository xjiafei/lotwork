package com.winterframework.firefrog.game.web.dto;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

public class AuditLotteryMonitorOrderRequest implements Serializable {

	private static final long serialVersionUID = -1700260853838435356L;
	
	@NotNull
	private String orderCode;
	private Integer status;

	public String getOrderCode() {
		return orderCode;
	}

	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	

}
