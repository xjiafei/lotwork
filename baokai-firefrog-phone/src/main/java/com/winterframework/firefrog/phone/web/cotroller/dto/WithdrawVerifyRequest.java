package com.winterframework.firefrog.phone.web.cotroller.dto;

import java.io.Serializable;

public class WithdrawVerifyRequest implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -353834406921954838L;

	private Double money;	//提现金额
	private String bankinfo	;//提现初始化 取得(id+#+bank_id)
	public Double getMoney() {
		return money;
	}
	public void setMoney(Double money) {
		this.money = money;
	}
	public String getBankinfo() {
		return bankinfo;
	}
	public void setBankinfo(String bankinfo) {
		this.bankinfo = bankinfo;
	}
	
}
