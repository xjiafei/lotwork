package com.winterframework.firefrog.phone.web.cotroller.dto;

import java.util.Date;

public class ChargeApplyRequest {

	private Long bankId;

	private Long userId;

	private Long mcBankId;

	private String userAct;
	
	private Long depositMode;
	
	private Long platfom;
	
	private String ver;
	
	private String bankNumber;
	
	private String bankAccount;
	private String customerIp;
	
	public String getBankNumber() {
		return bankNumber;
	}
	public void setBankNumber(String bankNumber) {
		this.bankNumber = bankNumber;
	}
	public String getBankAccount() {
		return bankAccount;
	}
	public void setBankAccount(String bankAccount) {
		this.bankAccount = bankAccount;
	}
	public Long getPlatfom() {
		return platfom;
	}
	public void setPlatfom(Long platfom) {
		this.platfom = platfom;
	}
	public String getVer() {
		return ver;
	}
	public void setVer(String ver) {
		this.ver = ver;
	}
	public Long getBankId() {
		return bankId;
	}
	public void setBankId(Long bankId) {
		this.bankId = bankId;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public Long getMcBankId() {
		return mcBankId;
	}
	public void setMcBankId(Long mcBankId) {
		this.mcBankId = mcBankId;
	}
	public String getUserAct() {
		return userAct;
	}
	public void setUserAct(String userAct) {
		this.userAct = userAct;
	}
	public Long getDepositMode() {
		return depositMode;
	}
	public void setDepositMode(Long depositMode) {
		this.depositMode = depositMode;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	public Long getPreChargeAmt() {
		return preChargeAmt;
	}
	public void setPreChargeAmt(Long preChargeAmt) {
		this.preChargeAmt = preChargeAmt;
	}
	public Date getApplyTime() {
		return applyTime;
	}
	public void setApplyTime(Date applyTime) {
		this.applyTime = applyTime;
	}
	
	public String getCustomerIp() {
		return customerIp;
	}
	public void setCustomerIp(String customerIp) {
		this.customerIp = customerIp;
	}

	private String memo;
	private Long preChargeAmt;
	private Date applyTime;
	
	
}
