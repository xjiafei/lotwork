package com.winterframework.firefrog.game.web.dto;

import java.util.Date;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.winterframework.modules.web.jsonresult.FirefrogDateDeSerializer;
import com.winterframework.modules.web.jsonresult.FirefrogDateSerializer;

public class FundLogReq {

	private String reason;
	private Long userId;
	private String account;
	private String sn;
	@JsonSerialize(using = FirefrogDateSerializer.class)  
	@JsonDeserialize(using = FirefrogDateDeSerializer.class) 
	private Date fromDate;
	@JsonSerialize(using = FirefrogDateSerializer.class)  
	@JsonDeserialize(using = FirefrogDateDeSerializer.class) 
	private Date toDate;
	private String[] summary;
	private String userChain;
	private String planCode;
	private String exCode;
	
	
	public String getUserChain() {
		return userChain;
	}

	public void setUserChain(String userChain) {
		this.userChain = userChain;
	}

	public String[] getSummary() {
		return summary;
	}
	
	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public void setSummary(String[] summary) {
		this.summary = summary;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getSn() {
		return sn;
	}
	public void setSn(String sn) {
		this.sn = sn;
	}
	public Date getFromDate() {
		return fromDate;
	}
	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}
	public Date getToDate() {
		return toDate;
	}
	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}

	public String getPlanCode() {
		return planCode;
	}

	public void setPlanCode(String planCode) {
		this.planCode = planCode;
	}

	public String getExCode() {
		return exCode;
	}

	public void setExCode(String exCode) {
		this.exCode = exCode;
	}
}
