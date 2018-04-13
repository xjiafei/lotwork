package com.winterframework.firefrog.phone.web.cotroller.dto;

import java.io.Serializable;
import java.util.Date;

public class UserBankStruc implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5788567231468349907L;

	private Long id;

	private Long bankId;

	private String bankAccount;

	private String bankNumber;
	private String province;
	private String city;
	private String branchName;
	private Long mcBankId;
	private Date bindDate;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getBankId() {
		return bankId;
	}
	public void setBankId(Long bankId) {
		this.bankId = bankId;
	}
	public String getBankAccount() {
		return bankAccount;
	}
	public void setBankAccount(String bankAccount) {
		this.bankAccount = bankAccount;
	}
	public String getBankNumber() {
		return bankNumber;
	}
	public void setBankNumber(String bankNumber) {
		this.bankNumber = bankNumber;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getBranchName() {
		return branchName;
	}
	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}
	public Long getMcBankId() {
		return mcBankId;
	}
	public void setMcBankId(Long mcBankId) {
		this.mcBankId = mcBankId;
	}
	public Date getBindDate() {
		return bindDate;
	}
	public void setBindDate(Date bindDate) {
		this.bindDate = bindDate;
	}
}
