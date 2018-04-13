package com.winterframework.firefrog.phone.web.cotroller.dto;

import java.io.Serializable;

public class CardBindingCommitRequest implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6490598285191527773L;
	
	private Integer bankId;//	银行代号
	private String bank	;//银行名称
	private Integer provinceId;//省份代号
	private String province;//	省份名称
	private Integer cityId;//	城市代号
	private String city;//	城市名称
	private String branch;//	银行分行
	private String accountName;//	账号名称
	private String account;//	账号
	private String secpass;//	资金密码
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
	public Integer getProvinceId() {
		return provinceId;
	}
	public void setProvinceId(Integer provinceId) {
		this.provinceId = provinceId;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public Integer getCityId() {
		return cityId;
	}
	public void setCityId(Integer cityId) {
		this.cityId = cityId;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getBranch() {
		return branch;
	}
	public void setBranch(String branch) {
		this.branch = branch;
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
	public String getSecpass() {
		return secpass;
	}
	public void setSecpass(String secpass) {
		this.secpass = secpass;
	}
}
