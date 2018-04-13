package com.winterframework.firefrog.phone.web.cotroller.dto;

import java.io.Serializable;

public class RechargeConfirmResponse implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8963438224987201936L;
	
	private String email;
	private String account;//	收款帐号
//	private String area;//	开户城市
	private String shortname;//	收款银行
	private String accName;//收款帐户名
	private Double	amount;//	充值金额
	private String key;//	附言
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	/*public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}*/
	public String getShortname() {
		return shortname;
	}
	public void setShortname(String shortname) {
		this.shortname = shortname;
	}
	public String getAccName() {
		return accName;
	}
	public void setAccName(String accName) {
		this.accName = accName;
	}
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}


}
