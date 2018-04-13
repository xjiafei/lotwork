package com.winterframework.firefrog.phone.web.cotroller.dto;

import java.io.Serializable;

public class BankCardApplyBindRequest implements Serializable {

	private static final long serialVersionUID = 829072851214773065L;

	private Long userId;

	private Long mcBankId;

	private Long bankId;
	/** 支行名称 */
	private String branchName;

	private String province;

	private String city;
	/** 开户人姓名 */
	private String bankAccount;
	/** 卡号 */
	private String bankNumber;
	
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
	public String getBranchName() {
		return branchName;
	}
	public void setBranchName(String branchName) {
		this.branchName = branchName;
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
	
}
