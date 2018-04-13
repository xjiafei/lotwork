package com.winterframework.firefrog.fund.web.dto;

import java.util.Date;

public class FundChangeLogSubResponse {
	
	Integer Status;
	String ErrMsg;
	Date Gmt_Create;
	Long balance;
	
	
	public Integer getStatus() {
		return Status;
	}
	public void setStatus(Integer status) {
		Status = status;
	}
	public String getErrMsg() {
		return ErrMsg;
	}
	public void setErrMsg(String errMsg) {
		ErrMsg = errMsg;
	}
	public Date getGmt_Create() {
		return Gmt_Create;
	}
	public void setGmt_Create(Date gmt_Create) {
		Gmt_Create = gmt_Create;
	}
	public Long getBalance() {
		return balance;
	}
	public void setBalance(Long balance) {
		this.balance = balance;
	}
	
}
