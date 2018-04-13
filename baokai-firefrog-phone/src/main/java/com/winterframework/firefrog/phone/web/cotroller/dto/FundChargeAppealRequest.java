package com.winterframework.firefrog.phone.web.cotroller.dto;

import java.io.Serializable;

public class FundChargeAppealRequest implements Serializable{
	
	private Long userId;
	private String chargeSn; 
	private String userAccount;
	private Long chargeAmt;
	private Integer depositeMode;
	private Integer bankId;
	private String transactionNum;
	
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getChargeSn() {
		return chargeSn;
	}
	public void setChargeSn(String chargeSn) {
		this.chargeSn = chargeSn;
	}
	public String getUserAccount() {
		return userAccount;
	}
	public void setUserAccount(String userAccount) {
		this.userAccount = userAccount;
	}
	public Long getChargeAmt() {
		return chargeAmt;
	}
	public void setChargeAmt(Long chargeAmt) {
		this.chargeAmt = chargeAmt;
	}
	public Integer getDepositeMode() {
		return depositeMode;
	}
	public void setDepositeMode(Integer depositeMode) {
		this.depositeMode = depositeMode;
	}
	public Integer getBankId() {
		return bankId;
	}
	public void setBankId(Integer bankId) {
		this.bankId = bankId;
	}
	public String getTransactionNum() {
		return transactionNum;
	}
	public void setTransactionNum(String transactionNum) {
		this.transactionNum = transactionNum;
	}
	
}
