package com.winterframework.firefrog.phone.web.cotroller.dto;

import java.io.Serializable;

public class Bank implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -291242952586585348L;
	private Integer bankId	;//银行编号
	private String bank;//	银行名称
	public Integer getBankId() {
		return bankId;
	}
	public void setBankId(Integer bankId) {
		this.bankId = bankId;
	}
	public String getBank() {
		return bank;
	}
	public void setBank(String bank) {
		this.bank = bank;
	}

}
