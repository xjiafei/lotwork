package com.winterframework.firefrog.phone.web.cotroller.dto;

import java.io.Serializable;

public class ProxyDto implements Serializable {

	
	private static final long serialVersionUID = -821674703704541527L;
	
	private String name;//	用户名
	private String uid;	
	private Double balance;//	用户余额
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public Double getBalance() {
		return balance;
	}
	public void setBalance(Double balance) {
		this.balance = balance;
	}
	
}
