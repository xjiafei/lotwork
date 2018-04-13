package com.winterframework.firefrog.fund.web.dto;

import javax.validation.constraints.NotNull;

public class FundTransferSubRequestDTO {
	@NotNull

	private String rcvAccount;
	private Long userId;
	private Long transferAmt;
	private Long applyTime;
	private Long retTime;
	private String sn;
	
	
	public String getSn() {
		return sn;
	}

	public void setSn(String sn) {
		this.sn = sn;
	}

	public Long getRetTime() {
		return retTime;
	}

	public void setRetTime(Long retTime) {
		this.retTime = retTime;
	}

	//0 转出 1 转入
	private int direction;

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

}
