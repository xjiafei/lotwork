/**
* Copyright (c) 2005-2012 winterframework.com
* Licensed under the Apache License, Version 2.0 (the "License");
*/
package com.winterframework.firefrog.fund.web.dto;

/** 
* @ClassName: FundTransferStrc 
* @Description: 用户返回转账列表数据结构
* @author david
* @date 2013-7-2 上午11:56:15 
*  
*/
public class FundTransferStrc {

	private Long transferTime;
	private Long transferAmt;
	private String userAccount;
	private String userChain;
	private Long isUpward;
	private String sn;
	private String rcvAccount;

	public Long getTransferTime() {
		return transferTime;
	}

	public void setTransferTime(Long transferTime) {
		this.transferTime = transferTime;
	}

	public String getUserChain() {
		return userChain;
	}

	public void setUserChain(String userChain) {
		this.userChain = userChain;
	}

	public Long getTransferAmt() {
		return transferAmt;
	}

	public void setTransferAmt(Long transferAmt) {
		this.transferAmt = transferAmt;
	}

	public String getUserAccount() {
		return userAccount;
	}

	public void setUserAccount(String userAccount) {
		this.userAccount = userAccount;
	}

	public Long getIsUpward() {
		return isUpward;
	}

	public void setIsUpward(Long isUpward) {
		this.isUpward = isUpward;
	}



	public String getSn() {
		return sn;
	}

	public void setSn(String sn) {
		this.sn = sn;
	}

	public String getRcvAccount() {
		return rcvAccount;
	}

	public void setRcvAccount(String rcvAccount) {
		this.rcvAccount = rcvAccount;
	}

}
