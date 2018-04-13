package com.winterframework.firefrog.fund.web.dto;

import java.util.Date;

public class FundChargeAppealResponse {

	private Long userId;

	private String account;

	private String chargeSn;

	private Integer depositeMode;

	private Integer bankId;

	private String bankName;

	private String bankCardNumber;

	private String chargeMemo;

	private Long chargeAmt;

	private Date chargeTime;

	private Integer chargeStatus;
	
	private boolean hasBeenAppeal;
	
	private Long canRechargeAppeal;
	
	private Long otherCanRechargeAppeal;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getChargeSn() {
		return chargeSn;
	}

	public void setChargeSn(String chargeSn) {
		this.chargeSn = chargeSn;
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

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getBankCardNumber() {
		return bankCardNumber;
	}

	public void setBankCardNumber(String bankCardNumber) {
		this.bankCardNumber = bankCardNumber;
	}

	public String getChargeMemo() {
		return chargeMemo;
	}

	public void setChargeMemo(String chargeMemo) {
		this.chargeMemo = chargeMemo;
	}

	public Long getChargeAmt() {
		return chargeAmt;
	}

	public void setChargeAmt(Long chargeAmt) {
		this.chargeAmt = chargeAmt;
	}

	public Date getChargeTime() {
		return chargeTime;
	}

	public void setChargeTime(Date chargeTime) {
		this.chargeTime = chargeTime;
	}

	public Integer getChargeStatus() {
		return chargeStatus;
	}

	public void setChargeStatus(Integer chargeStatus) {
		this.chargeStatus = chargeStatus;
	}

	public boolean isHasBeenAppeal() {
		return hasBeenAppeal;
	}

	public void setHasBeenAppeal(boolean hasBeenAppeal) {
		this.hasBeenAppeal = hasBeenAppeal;
	}

	public Long getCanRechargeAppeal() {
		return canRechargeAppeal;
	}

	public void setCanRechargeAppeal(Long canRechargeAppeal) {
		this.canRechargeAppeal = canRechargeAppeal;
	}

	public Long getOtherCanRechargeAppeal() {
		return otherCanRechargeAppeal;
	}

	public void setOtherCanRechargeAppeal(Long otherCanRechargeAppeal) {
		this.otherCanRechargeAppeal = otherCanRechargeAppeal;
	}

	
}
