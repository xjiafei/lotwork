package com.winterframework.firefrog.fund.service.impl.pt;

public class PtSysTransferRequest {

	Long userID;
	Long amount;
	Integer direct;
	String sn;
	Long bal;
	String manualSn;
	Integer type;

	public Long getBal() {
		return bal;
	}

	public void setBal(Long bal) {
		this.bal = bal;
	}

	public String getSn() {
		return sn;
	}

	public void setSn(String sn) {
		this.sn = sn;
	}

	public Long getUserID() {
		return userID;
	}

	public void setUserID(Long userID) {
		this.userID = userID;
	}

	public Long getAmount() {
		return amount;
	}

	public void setAmount(Long amount) {
		this.amount = amount;
	}

	public Integer getDirect() {
		return direct;
	}

	public void setDirect(Integer direct) {
		this.direct = direct;
	}

	public String getManualSn() {
		return manualSn;
	}

	public void setManualSn(String manualSn) {
		this.manualSn = manualSn;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

}
