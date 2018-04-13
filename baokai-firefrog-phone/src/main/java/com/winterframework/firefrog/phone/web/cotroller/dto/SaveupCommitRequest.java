package com.winterframework.firefrog.phone.web.cotroller.dto;

import java.io.Serializable;

public class SaveupCommitRequest implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4897368361658538930L;
	
	private String uid	;//欲充值uid	varchar
	private String secpwd;//	资金密码	varchar
	private Double money;//	充值金额	float
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public String getSecpwd() {
		return secpwd;
	}
	public void setSecpwd(String secpwd) {
		this.secpwd = secpwd;
	}
	public Double getMoney() {
		return money;
	}
	public void setMoney(Double money) {
		this.money = money;
	}

}
