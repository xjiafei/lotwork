package com.winterframework.firefrog.fund.web.dto;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.winterframework.modules.web.jsonresult.FirefrogDateDeSerializer;
import com.winterframework.modules.web.jsonresult.FirefrogDateSerializer;

public class WithdrawApplyRequest implements Serializable {

	private static final long serialVersionUID = -1158201137704036645L;
	@NotNull
	private Long bankId;
	@NotNull
	private Long userId;
	@NotNull
	private Long preWithdrawAmt;
	@JsonSerialize(using = FirefrogDateSerializer.class)  
	@JsonDeserialize(using = FirefrogDateDeSerializer.class) 
	private Date applyTime;
	@NotNull
	private UserBankStruc userBankStruc;
	@NotNull
	private Long ipAddr;
	private Long isVip;

	

	public WithdrawApplyRequest() {

	}

	public Long getBankId() {
		return bankId;
	}

	public void setBankId(Long bankId) {
		this.bankId = bankId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getPreWithdrawAmt() {
		return preWithdrawAmt;
	}

	public void setPreWithdrawAmt(Long preWithdrawAmt) {
		this.preWithdrawAmt = preWithdrawAmt;
	}

	public Date getApplyTime() {
		return applyTime;
	}

	public void setApplyTime(Date applyTime) {
		this.applyTime = applyTime;
	}

	
	public UserBankStruc getUserBankStruc() {
		return userBankStruc;
	}

	public void setUserBankStruc(UserBankStruc userBankStruc) {
		this.userBankStruc = userBankStruc;
	}

	public Long getIpAddr() {
		return ipAddr;
	}

	public void setIpAddr(Long ipAddr) {
		this.ipAddr = ipAddr;
	}

	public Long getIsVip() {
		return isVip;
	}

	public void setIsVip(Long isVip) {
		this.isVip = isVip;
	}

}
