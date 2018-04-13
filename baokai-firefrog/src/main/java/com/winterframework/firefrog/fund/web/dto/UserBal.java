package com.winterframework.firefrog.fund.web.dto;

public class UserBal {
	Long Bal;
	Long disableAmt;
	public Long getBal() {
		return Bal;
	}
	public void setBal(Long bal) {
		Bal = bal;
	}
	public Long getDisableAmt() {
		return disableAmt;
	}
	public void setDisableAmt(Long disableAmt) {
		this.disableAmt = disableAmt;
	}
}
