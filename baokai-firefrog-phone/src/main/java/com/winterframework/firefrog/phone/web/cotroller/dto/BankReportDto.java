package com.winterframework.firefrog.phone.web.cotroller.dto;

public class BankReportDto {

	private Double amount;//	异动金额	float
	private Double balance;//	馀额	float
	private String ordertype;//	类型	varchar
	private String time;//	时间	varchar
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	public Double getBalance() {
		return balance;
	}
	public void setBalance(Double balance) {
		this.balance = balance;
	}
	public String getOrdertype() {
		return ordertype;
	}
	public void setOrdertype(String ordertype) {
		this.ordertype = ordertype;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}

}
