package com.winterframework.firefrog.fund.web.dto;

public class FundTransferSubSystemRequest {

	private String account;	
	private Long ID;
	private Long Amount;
	private Long SN;
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public Long getID() {
		return ID;
	}
	public void setID(Long iD) {
		ID = iD;
	}
	public Long getAmount() {
		return Amount;
	}
	public void setAmount(Long amount) {
		Amount = amount;
	}
	public Long getSN() {
		return SN;
	}
	public void setSN(Long sN) {
		SN = sN;
	}
	


}
