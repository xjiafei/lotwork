package com.winterframework.firefrog.subsys.web.dto;

import java.util.List;


public class SubSysActivityGiftResponseDTO {

	public Long status;
	public String errMsg;
	public String debitSn;
	public String activitySn;
	public Long amount;
	public String roundId;
	public Long getStatus() {
		return status;
	}
	public void setStatus(Long status) {
		this.status = status;
	}
	public String getErrMsg() {
		return errMsg;
	}
	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}
	public String getDebitSn() {
		return debitSn;
	}
	public void setDebitSn(String debitSn) {
		this.debitSn = debitSn;
	}
	public String getActivitySn() {
		return activitySn;
	}
	public void setActivitySn(String activitySn) {
		this.activitySn = activitySn;
	}
	public Long getAmount() {
		return amount;
	}
	public void setAmount(Long amount) {
		this.amount = amount;
	}
	public String getRoundId() {
		return roundId;
	}
	public void setRoundId(String roundId) {
		this.roundId = roundId;
	}
	
	
	
	
}
