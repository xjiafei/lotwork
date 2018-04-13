package com.winterframework.firefrog.fund.web.dto;

import javax.validation.constraints.NotNull;

public class FundTransferRequestDTO {
	@NotNull
	private Long rcvUserId;
	private String rcvAccount;
	private Long isUpward;
	private Long userId;
	private Long transferAmt;
	private Long applyTime;
	
	private Long startDate;
	private Long endDate;
	//0 转出 1 转入
	private Long direction;
	
    

	public Long getStartDate() {
		return startDate;
	}



	public void setStartDate(Long startDate) {
		this.startDate = startDate;
	}



	public Long getEndDate() {
		return endDate;
	}



	public void setEndDate(Long endDate) {
		this.endDate = endDate;
	}



	public Long getDirection() {
		return direction;
	}



	public void setDirection(Long direction) {
		this.direction = direction;
	}



	public Long getRcvUserId() {
		return rcvUserId;
	}

	

	public void setRcvUserId(Long rcvUserId) {
		this.rcvUserId = rcvUserId;
	}

	public String getRcvAccount() {
		return rcvAccount;
	}

	public void setRcvAccount(String rcvAccount) {
		this.rcvAccount = rcvAccount;
	}

	public Long getIsUpward() {
		return isUpward;
	}

	public void setIsUpward(Long isUpward) {
		this.isUpward = isUpward;
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
