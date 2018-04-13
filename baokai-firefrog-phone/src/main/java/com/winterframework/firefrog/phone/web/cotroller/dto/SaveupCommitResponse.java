package com.winterframework.firefrog.phone.web.cotroller.dto;

import java.io.Serializable;

public class SaveupCommitResponse implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5221682955114408893L;
	
	private String username;//	充值帐号	varchar
	private Double money;//	充值金额	float
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public Double getMoney() {
		return money;
	}
	public void setMoney(Double money) {
		this.money = money;
	}

	

}
