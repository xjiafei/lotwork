package com.winterframework.firefrog.phone.web.cotroller.dto;

import java.io.Serializable;

public class ChargeRecordDto implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1357156768589167533L;
	private String orderId;
	private String time;
	private Double applyMoney;
	private Double realMoney;
	private String type;
	private Integer status;
	private Integer payBankId;
	private Boolean appealStatus;
	private Long waitTime;
	
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public Double getApplyMoney() {
		return applyMoney;
	}
	public void setApplyMoney(Double applyMoney) {
		this.applyMoney = applyMoney;
	}
	public Double getRealMoney() {
		return realMoney;
	}
	public void setRealMoney(Double realMoney) {
		this.realMoney = realMoney;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Integer getPayBankId() {
		return payBankId;
	}
	public void setPayBankId(Integer payBankId) {
		this.payBankId = payBankId;
	}
	public Boolean getAppealStatus() {
		return appealStatus;
	}
	public void setAppealStatus(Boolean appealStatus) {
		this.appealStatus = appealStatus;
	}
	public Long getWaitTime() {
		return waitTime;
	}
	public void setWaitTime(Long waitTime) {
		this.waitTime = waitTime;
	}
	
	
}
