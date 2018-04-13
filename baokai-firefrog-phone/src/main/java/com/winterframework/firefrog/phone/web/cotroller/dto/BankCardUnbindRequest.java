package com.winterframework.firefrog.phone.web.cotroller.dto;

import java.io.Serializable;


public class BankCardUnbindRequest implements Serializable {

	private static final long serialVersionUID = -93395902247066018L;
	/** 用户id */
	private Long userId;
	/** MC银行id */
	private Long mcBankId;
	/** 银行id */
	private Long bankId;
	private Long bindId;
	private Long bindcardType;
	public Long getBindcardType() {
		return bindcardType;
	}
	public void setBindcardType(Long bindcardType) {
		this.bindcardType = bindcardType;
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
	public Long getBankId() {
		return bankId;
	}
	public void setBankId(Long bankId) {
		this.bankId = bankId;
	}
	public Long getBindId() {
		return bindId;
	}
	public void setBindId(Long bindId) {
		this.bindId = bindId;
	}
	
	
}
