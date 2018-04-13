package com.winterframework.firefrog.fund.service.impl.th;

import java.io.Serializable;

public class PayQueryResponse implements Serializable{
	private String orderNo;
	private String orderAmount;
	private String orderTime;
	private String platOrderNo;
	private String status;
	private String retMsg;
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public String getOrderAmount() {
		return orderAmount;
	}
	public void setOrderAmount(String orderAmount) {
		this.orderAmount = orderAmount;
	}
	public String getOrderTime() {
		return orderTime;
	}
	public void setOrderTime(String orderTime) {
		this.orderTime = orderTime;
	}
	public String getPlatOrderNo() {
		return platOrderNo;
	}
	public void setPlatOrderNo(String platOrderNo) {
		this.platOrderNo = platOrderNo;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getRetMsg() {
		return retMsg;
	}
	public void setRetMsg(String retMsg) {
		this.retMsg = retMsg;
	}

	
}
