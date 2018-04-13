package com.winterframework.firefrog.phone.web.cotroller.dto;

import java.io.Serializable;

public class QuickConfirmRequest implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2500588873573801853L;

	private Long bankId;
	private Double money;
	private Long system;
	private String version;
	private String bankNumber;
	private String bankAccount;
	private Integer depositMode;
	private String customerIp;
	
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
	public Long getBankId() {
		return bankId;
	}
	public void setBankId(Long bankId) {
		this.bankId = bankId;
	}
	public Double getMoney() {
		return money;
	}
	public void setMoney(Double money) {
		this.money = money;
	}
	
	public Long getSystem() {
		return system;
	}
	public void setSystem(Long system) {
		this.system = system;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public Integer getDepositMode() {
		return depositMode;
	}
	public void setDepositMode(Integer depositMode) {
		this.depositMode = depositMode;
	}
	public String getCustomerIp() {
		return customerIp;
	}
	public void setCustomerIp(String customerIp) {
		this.customerIp = customerIp;
	}
	
}
