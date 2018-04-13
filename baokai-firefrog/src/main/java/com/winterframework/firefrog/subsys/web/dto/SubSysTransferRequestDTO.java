package com.winterframework.firefrog.subsys.web.dto;

public class SubSysTransferRequestDTO {

	private String token;
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	private String rcvAccount;
	private Long userId;
	private Long transferAmt;
	private Long applyTime;
	private String sn;
	//0 转出 1 转入
	private int direction;
	//可提現額度
	private Long amountBal;
	private String retDate;
	
	public int getDirection() {
		return direction;
	}
	public void setDirection(int direction) {
		this.direction = direction;
	}
	public String getRcvAccount() {
		return rcvAccount;
	}
	public void setRcvAccount(String rcvAccount) {
		this.rcvAccount = rcvAccount;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getTransferAmt() {
		return transferAmt;
	}
	public void setTransferAmt(Long transferAmt) {
		this.transferAmt = transferAmt;
	}
	public Long getApplyTime() {
		return applyTime;
	}
	public void setApplyTime(Long applyTime) {
		this.applyTime = applyTime;
	}
	public String getSn() {
		return sn;
	}
	public void setSn(String sn) {
		this.sn = sn;
	}
	public Long getAmountBal() {
		return amountBal;
	}
	public void setAmountBal(Long amountBal) {
		this.amountBal = amountBal;
	}
	public String getRetDate() {
		return retDate;
	}
	public void setRetDate(String retDate) {
		this.retDate = retDate;
	}

	
	
}
