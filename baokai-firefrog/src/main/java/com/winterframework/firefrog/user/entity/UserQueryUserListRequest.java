package com.winterframework.firefrog.user.entity;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.winterframework.modules.web.jsonresult.FirefrogDateDeSerializer;

public class UserQueryUserListRequest implements Serializable {

	private static final long serialVersionUID = 1L;
	private Long accountType;//帳號類型
	private String ptAccount;//PT帳號
	private Long customerType;//PT代理類型
	
	@JsonDeserialize(using = FirefrogDateDeSerializer.class)
	private Date startDate;//開始日期or註冊時間起
	
	@JsonDeserialize(using = FirefrogDateDeSerializer.class)
	private Date endDate;//結束日期or註冊時間迄

	public Long getAccountType() {
		return accountType;
	}

	public void setAccountType(Long accountType) {
		this.accountType = accountType;
	}

	public String getPtAccount() {
		return ptAccount;
	}

	public void setPtAccount(String ptAccount) {
		this.ptAccount = ptAccount;
	}

	public Long getCustomerType() {
		return customerType;
	}

	public void setCustomerType(Long customerType) {
		this.customerType = customerType;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
}
