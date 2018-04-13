/**
 * Copyright (c) 2005-2012 winterframework.com
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.winterframework.firefrog.subsys.vo;

import java.util.Date;

import com.winterframework.orm.dal.ibatis3.BaseEntity;

/**
 * @author cms group
 * @version 1.0
 * @since 1.0
 */

public class SubSystemActivityLog extends BaseEntity {

	private String account;
	private Long amount;
	private String roundId;
	private Date createTime;
	private Long status;
	private String memo;
	private String activitySn;
	private String debitSn;
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
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
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Long getStatus() {
		return status;
	}
	public void setStatus(Long status) {
		this.status = status;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	public String getActivitySn() {
		return activitySn;
	}
	public void setActivitySn(String activitySn) {
		this.activitySn = activitySn;
	}
	public String getDebitSn() {
		return debitSn;
	}
	public void setDebitSn(String debitSn) {
		this.debitSn = debitSn;
	}
	
}
