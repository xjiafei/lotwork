package com.winterframework.firefrog.phone.web.cotroller.dto;

import java.io.Serializable;

public class CardBindingDeleteRequest  implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7767710358779051420L;

	private String account;//	账号
	private String accountName;//	账号名称
	private Integer id;//	银行id
	private Long bindId;
	private Long mcBankId;
	public Long getBindId() {
		return bindId;
	}
	public void setBindId(Long bindId) {
		this.bindId = bindId;
	}
	public Long getMcBankId() {
		return mcBankId;
	}
	public void setMcBankId(Long mcBankId) {
		this.mcBankId = mcBankId;
	}
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
