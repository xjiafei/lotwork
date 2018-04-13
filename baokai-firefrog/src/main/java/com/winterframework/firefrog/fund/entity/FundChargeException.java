/**
* Copyright (c) 2005-2012 winterframework.com
* Licensed under the Apache License, Version 2.0 (the "License");
*/
package com.winterframework.firefrog.fund.entity;

import java.util.Date;

/**
 * @author cms group
 * @version 1.0
 * @since 1.0
 */

public class FundChargeException {



	private Long id;
	private Long userId;
	private Long bankId;
	private String cardNumber;
	private String rcvCardNumber;
	private String rcvAccName;
	private String rcvEmail;
	private Long rcvBank;
	private Long realChargeAmt;
	private Date mcNoticeTime;
	private Long status;
	private String memo;
	private Long mcFee;
	private String sn;
	private String mcChannel;
	private String mcSn;
	private Long mcBankFee;
	private String cardAcct;
	private String attachment;
	private String bankName;
	private String bankAddr;
	private String apprMemo;
	private Date apprTime;
	private String apprAccount;
	private Date mcExactTime;
	private Long refundAmt;
	private String applyMemo;
	private Date applyTime;
	private String applyAccount;
	private Date mcSecondNoticeTime;
	private String exceptionOrderNo;
	private String userChain;
	private String currApprer;
	private Date operatingTime;
	private String baseInfo;
	private Long[] checkStatuses;
	//columns END

	public String getUserChain() {
		return userChain;
	}

	public String getCurrApprer() {
		return currApprer;
	}

	public void setCurrApprer(String currApprer) {
		this.currApprer = currApprer;
	}

	public void setUserChain(String userChain) {
		this.userChain = userChain;
	}

	public FundChargeException() {
	}

	public FundChargeException(Long id) {
		this.id = id;
	}

	public String getExceptionOrderNo() {
		return exceptionOrderNo;
	}

	public void setExceptionOrderNo(String exceptionOrderNo) {
		this.exceptionOrderNo = exceptionOrderNo;
	}

	public Long getRcvBank() {
		return rcvBank;
	}

	public void setRcvBank(Long rcvBank) {
		this.rcvBank = rcvBank;
	}

	public void setUserId(Long value) {
		this.userId = value;
	}

	public Long getUserId() {
		return this.userId;
	}

	public void setBankId(Long value) {
		this.bankId = value;
	}

	public Long getBankId() {
		return this.bankId;
	}

	public void setCardNumber(String value) {
		this.cardNumber = value;
	}

	public String getCardNumber() {
		return this.cardNumber;
	}

	public void setRcvCardNumber(String value) {
		this.rcvCardNumber = value;
	}

	public String getRcvCardNumber() {
		return this.rcvCardNumber;
	}

	public void setRcvAccName(String value) {
		this.rcvAccName = value;
	}

	public String getRcvAccName() {
		return this.rcvAccName;
	}

	public void setRcvEmail(String value) {
		this.rcvEmail = value;
	}

	public String getRcvEmail() {
		return this.rcvEmail;
	}

	public void setRealChargeAmt(Long value) {
		this.realChargeAmt = value;
	}

	public Long getRealChargeAmt() {
		return this.realChargeAmt;
	}

	public void setMcNoticeTime(Date value) {
		this.mcNoticeTime = value;
	}

	public Date getMcNoticeTime() {
		return this.mcNoticeTime;
	}

	public void setStatus(Long value) {
		this.status = value;
	}


	public Long getStatus() {
		return this.status;
	}

	public void setMemo(String value) {
		this.memo = value;
	}

	public String getMemo() {
		return this.memo;
	}

	public void setMcFee(Long value) {
		this.mcFee = value;
	}

	public Long getMcFee() {
		return this.mcFee;
	}

	public void setSn(String value) {
		this.sn = value;
	}

	public String getSn() {
		return this.sn;
	}

	public void setMcChannel(String value) {
		this.mcChannel = value;
	}

	public String getMcChannel() {
		return this.mcChannel;
	}

	public void setMcSn(String value) {
		this.mcSn = value;
	}

	public String getMcSn() {
		return this.mcSn;
	}

	public void setMcBankFee(Long value) {
		this.mcBankFee = value;
	}

	public Long getMcBankFee() {
		return this.mcBankFee;
	}

	public void setCardAcct(String value) {
		this.cardAcct = value;
	}

	public String getCardAcct() {
		return this.cardAcct;
	}

	public void setAttachment(String value) {
		this.attachment = value;
	}

	public String getAttachment() {
		return this.attachment;
	}

	public void setBankName(String value) {
		this.bankName = value;
	}

	public String getBankName() {
		return this.bankName;
	}

	public void setBankAddr(String value) {
		this.bankAddr = value;
	}

	public String getBankAddr() {
		return this.bankAddr;
	}

	public void setApprMemo(String value) {
		this.apprMemo = value;
	}

	public String getApprMemo() {
		return this.apprMemo;
	}

	public Date getMcExactTime() {
		return mcExactTime;
	}

	public void setMcExactTime(Date mcExactTime) {
		this.mcExactTime = mcExactTime;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getApplyTime() {
		return applyTime;
	}

	public void setApplyTime(Date applyTime) {
		this.applyTime = applyTime;
	}

	public Date getMcSecondNoticeTime() {
		return mcSecondNoticeTime;
	}

	public void setMcSecondNoticeTime(Date mcSecondNoticeTime) {
		this.mcSecondNoticeTime = mcSecondNoticeTime;
	}

	public Date getApprTime() {
		return apprTime;
	}

	public void setApprTime(Date apprTime) {
		this.apprTime = apprTime;
	}

	public String getApprAccount() {
		return apprAccount;
	}

	public void setApprAccount(String apprAccount) {
		this.apprAccount = apprAccount;
	}

	public Long getRefundAmt() {
		return refundAmt;
	}

	public void setRefundAmt(Long refundAmt) {
		this.refundAmt = refundAmt;
	}

	public String getApplyMemo() {
		return applyMemo;
	}

	public void setApplyMemo(String applyMemo) {
		this.applyMemo = applyMemo;
	}

	public String getApplyAccount() {
		return applyAccount;
	}

	public void setApplyAccount(String applyAccount) {
		this.applyAccount = applyAccount;
	}

	public Date getOperatingTime() {
		return operatingTime;
	}

	public void setOperatingTime(Date operatingTime) {
		this.operatingTime = operatingTime;
	}

	public String getBaseInfo() {
		return baseInfo;
	}

	public void setBaseInfo(String baseInfo) {
		this.baseInfo = baseInfo;
	}

	public Long[] getCheckStatuses() {
		return checkStatuses;
	}

	public void setCheckStatuses(Long[] checkStatuses) {
		this.checkStatuses = checkStatuses;
	}

	
}
