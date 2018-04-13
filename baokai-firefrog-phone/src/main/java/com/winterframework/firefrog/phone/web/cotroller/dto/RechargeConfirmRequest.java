package com.winterframework.firefrog.phone.web.cotroller.dto;

import java.io.Serializable;

public class RechargeConfirmRequest implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4208664029059127722L;
	
	private Integer bid;//	银行id	int
//	private String bankradio;//	银行选项值	varchar
	private Long bank;//	银行编号	int
	private Long amount;//	充值金额	int
	private Integer alertmin;//	int
	private String 	secpwd;//	资金密码	varchar
	private Long mcBankId;
	public Long getMcBankId() {
		return mcBankId;
	}
	public void setMcBankId(Long mcBankId) {
		this.mcBankId = mcBankId;
	}
	public Integer getBid() {
		return bid;
	}
	public void setBid(Integer bid) {
		this.bid = bid;
	}
//	public String getBankradio() {
//		return bankradio;
//	}
//	public void setBankradio(String bankradio) {
//		this.bankradio = bankradio;
//	}
	public Long getBank() {
		return bank;
	}
	public void setBank(Long bank) {
		this.bank = bank;
	}
	public Long getAmount() {
		return amount;
	}
	public void setAmount(Long amount) {
		this.amount = amount;
	}
	public Integer getAlertmin() {
		return alertmin;
	}
	public void setAlertmin(Integer alertmin) {
		this.alertmin = alertmin;
	}
	public String getSecpwd() {
		return secpwd;
	}
	public void setSecpwd(String secpwd) {
		this.secpwd = secpwd;
	}

	
}
