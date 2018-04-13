/**
* Copyright (c) 2005-2012 winterframework.com
* Licensed under the Apache License, Version 2.0 (the "License");
*/
package com.winterframework.firefrog.fund.dao.vo;

import java.util.Date;

import com.winterframework.orm.dal.ibatis3.BaseEntity;

/**
 * @author cms group
 * @version 1.0
 * @since 1.0
 */

public class FundManualDepositVO extends BaseEntity {

	private static final long serialVersionUID = -2734369444551334369L;

	private Long typeId;

	private String rcvAccount;
	private Long rcvId;
	private String chargeSn;

	private String sn;

	private Long depositAmt;

	private Long status;

	private String approver;

	private Date approveTime;

	private String applyAccount;

	private Date applyTime;

	private String userBankStruc;

	private Date mcNoticeTime;
	private Long mcAmount;
	private Long mcStatus;

	private String memo;
	private String note;
	
	public String getNote() {
		return note;
	}
	
	public void setNote(String note) {
		this.note = note;
	}

	private String apprDemo;
	private String attach;
	private Long vipLvl;
	private Long isBatch=0L;
	
	
	public Long getMcAmount() {
		return mcAmount;
	}

	public void setMcAmount(Long mcAmount) {
		this.mcAmount = mcAmount;
	}

	public Long getIsBatch() {
		return isBatch;
	}

	public void setIsBatch(Long isBatch) {
		this.isBatch = isBatch;
	}

	public Long getVipLvl() {
		return vipLvl;
	}

	public Long getRcvId() {
		return rcvId;
	}

	public void setRcvId(Long rcvId) {
		this.rcvId = rcvId;
	}

	public String getChargeSn() {
		return chargeSn;
	}

	public void setChargeSn(String chargeSn) {
		this.chargeSn = chargeSn;
	}

	public void setVipLvl(Long vipLvl) {
		this.vipLvl = vipLvl;
	}

	public String getAttach() {
		return attach;
	}

	public void setAttach(String attach) {
		this.attach = attach;
	}

	public Long getTypeId() {
		return typeId;
	}

	public void setTypeId(Long typeId) {
		this.typeId = typeId;
	}

	public String getRcvAccount() {
		return rcvAccount;
	}

	public void setRcvAccount(String rcvAccount) {
		this.rcvAccount = rcvAccount;
	}

	public String getSn() {
		return sn;
	}

	public void setSn(String sn) {
		this.sn = sn;
	}

	public Long getDepositAmt() {
		return depositAmt;
	}

	public void setDepositAmt(Long depositAmt) {
		this.depositAmt = depositAmt;
	}

	public Long getStatus() {
		return status;
	}

	

	public void setStatus(Long status) {
		this.status = status;
	}

	public String getApprover() {
		return approver;
	}

	public void setApprover(String approver) {
		this.approver = approver;
	}

	public Date getApproveTime() {
		return approveTime;
	}

	public void setApproveTime(Date approveTime) {
		this.approveTime = approveTime;
	}

	public String getApplyAccount() {
		return applyAccount;
	}

	public void setApplyAccount(String applyAccount) {
		this.applyAccount = applyAccount;
	}

	public Date getApplyTime() {
		return applyTime;
	}

	public void setApplyTime(Date applyTime) {
		this.applyTime = applyTime;
	}

	public String getUserBankStruc() {
		return userBankStruc;
	}

	public void setUserBankStruc(String userBankStruc) {
		this.userBankStruc = userBankStruc;
	}

	public Date getMcNoticeTime() {
		return mcNoticeTime;
	}

	public void setMcNoticeTime(Date mcNoticeTime) {
		this.mcNoticeTime = mcNoticeTime;
	}

	public Long getMcStatus() {
		return mcStatus;
	}

	public void setMcStatus(Long mcStatus) {
		this.mcStatus = mcStatus;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getApprDemo() {
		return apprDemo;
	}

	public void setApprDemo(String apprDemo) {
		this.apprDemo = apprDemo;
	}

}
