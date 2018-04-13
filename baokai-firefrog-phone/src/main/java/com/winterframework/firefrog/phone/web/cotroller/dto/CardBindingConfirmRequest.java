package com.winterframework.firefrog.phone.web.cotroller.dto;

import java.io.Serializable;

public class CardBindingConfirmRequest implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3026929800653265276L;
	private String account;//	账号
	private String  accountName;//	账号名称
	private Integer id;//银行id
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getAccountName() {
		return accountName;
	}
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}

	
}
