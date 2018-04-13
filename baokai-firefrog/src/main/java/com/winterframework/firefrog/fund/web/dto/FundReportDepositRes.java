package com.winterframework.firefrog.fund.web.dto;

import com.winterframework.orm.dal.ibatis3.BaseEntity;

public class FundReportDepositRes extends BaseEntity{
	private Long uid;
	private String userChain;
	private Long inTime;
	private Long inSum;
	private Long outTime;
	private Long outSum;
	private String account;
	

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public Long getUid() {
		return uid;
	}

	public void setUid(Long uid) {
		this.uid = uid;
	}

	public String getUserChain() {
		return userChain;
	}

	public void setUserChain(String userChain) {
		this.userChain = userChain;
	}

	public Long getInTime() {
		return inTime;
	}

	public void setInTime(Long inTime) {
		this.inTime = inTime;
	}

	public Long getInSum() {
		return inSum;
	}

	public void setInSum(Long inSum) {
		this.inSum = inSum;
	}

	public Long getOutTime() {
		return outTime;
	}

	public void setOutTime(Long outTime) {
		this.outTime = outTime;
	}

	public Long getOutSum() {
		return outSum;
	}

	public void setOutSum(Long outSum) {
		this.outSum = outSum;
	}

}
