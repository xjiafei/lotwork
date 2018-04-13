package com.winterframework.firefrog.fund.service.impl.pt;

public class FundSysResult {
	String ptAccount;
	String ffAccount;
	int	   sucees;
	public String getPtAccount() {
		return ptAccount;
	}
	public void setPtAccount(String ptAccount) {
		this.ptAccount = ptAccount;
	}
	public String getFfAccount() {
		return ffAccount;
	}
	public void setFfAccount(String ffAccount) {
		this.ffAccount = ffAccount;
	}
	public int getSucees() {
		return sucees;
	}
	public void setSucees(int sucees) {
		this.sucees = sucees;
	}
}
