package com.winterframework.firefrog.phone.web.cotroller.dto;

public class Banks {

	private Integer id;//	银行id	int
	private Integer bankId;//	银行编号	int
	private String bankName	;//开户银行名称	varchar
	private String accountName;//	开户人姓名	varchar
	private String account;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
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
	
	public String getAccountName() {
		return accountName;
	}
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}

	
}
