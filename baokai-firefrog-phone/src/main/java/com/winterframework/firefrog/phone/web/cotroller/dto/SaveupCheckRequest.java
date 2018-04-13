package com.winterframework.firefrog.phone.web.cotroller.dto;

import java.io.Serializable;

public class SaveupCheckRequest implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4678360560229580187L;
	private String uid;//	欲充值uid
	private Double money;//	充值金额
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public Double getMoney() {
		return money;
	}
	public void setMoney(Double money) {
		this.money = money;
	}
	
	

}
