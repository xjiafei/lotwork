package com.winterframework.firefrog.phone.web.cotroller.dto;

import java.io.Serializable;

public class BankCardQueryRequest implements Serializable {
	
	
	private static final long serialVersionUID = -902932770988966024L;
	private long userId;
	private String bankCard;
	private long bindCardType;
	private String bankNumber;
	private String bankAccount;
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
	public long getBindCardType() {
		return bindCardType;
	}
	public void setBindCardType(long bindCardType) {
		this.bindCardType = bindCardType;
	}
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	public String getBankCard() {
		return bankCard;
	}
	public void setBankCard(String bankCard) {
		this.bankCard = bankCard;
	}
	
}
