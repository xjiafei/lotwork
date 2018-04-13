package com.winterframework.firefrog.phone.web.cotroller.dto;

import java.io.Serializable;
import java.util.Date;

public class WithdrawApplyRequest implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1277380406618036687L;
	private Long bankId;
	private Long userId;
	private Long preWithdrawAmt;
	private UserBankStruc userBankStruc;
	private Long ipAddr;
	private Long isVip;
	private Date applyTime;
	public Date getApplyTime() {
		return applyTime;
	}
	public void setApplyTime(Date applyTime) {
		this.applyTime = applyTime;
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
