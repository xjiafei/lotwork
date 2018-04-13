package com.winterframework.firefrog.fund.entity;

import java.util.Date;

import com.winterframework.firefrog.fund.web.dto.UserBankStruc;

public class FundWithdrawDTO {

	private Long bankId;
	private Long userId;
	private Long preWithdrawAmt;
	private Date applyTime;
	private UserBankStruc userBankStruc;
	private Long ipAddr;

	public FundWithdrawDTO() {

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

	public Long getIpAddr() {
		return ipAddr;
	}

	public void setIpAddr(Long ipAddr) {
		this.ipAddr = ipAddr;
	}

	public UserBankStruc getUserBankStruc() {
		return userBankStruc;
	}

	public void setUserBankStruc(UserBankStruc userBankStruc) {
		this.userBankStruc = userBankStruc;
	}

}
