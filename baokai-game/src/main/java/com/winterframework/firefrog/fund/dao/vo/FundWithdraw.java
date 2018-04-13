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

public class FundWithdraw extends BaseEntity {

	private static final long serialVersionUID = -3808360725578224868L;
	//alias
	public static final String TABLE_ALIAS = "FundWithdraw";
	public static final String ALIAS_USER_ID = "userId";
	public static final String ALIAS_BANK_ID = "bankId";
	public static final String ALIAS_BANK_CARD_NAME = "卡主人姓名";
	public static final String ALIAS_CARD_NUMBER = "卡号";
	public static final String ALIAS_APPLY_TIME = "申请时间";
	public static final String ALIAS_WITHDRAW_AMT = "金额";
	public static final String ALIAS_APPROVER_ID = "审核人id";
	public static final String ALIAS_APPROVE_TIME = "审核通过时间";
	public static final String ALIAS_MC_REMIT_TIME = "mc汇款时间";
	public static final String ALIAS_STATUS = "0 未处理、1 待复审、2 处理中、3 已拒绝、4 提现完全成功、5 提现部分成功、 6提现失败";
	public static final String ALIAS_SN = "流水号";
	public static final String ALIAS_IP_ADDR = "ip地址";
	public static final String ALIAS_APPROVE_MEMO = "审核备注";

	public static final String ALIAS_APPLY_EXPIRE_TIME = "申请过期时间";
	public static final String ALIAS_APPROVER_ACT = "审核人账号";
	public static final String ALIAS_MEMO = "失败原因";
	public static final String ALIAS_MC_SN = "mc 单号";
	public static final String ALIAS_MC_NOTICE_TIME = "mc 通知时间";
	public static final String ALIAS_MC_FEE = "mc 服务费";
	public static final String ALIAS_MC_MEMO = "mc失败原因";
	public static final String ALIAS_FUND_FREEZE_ID = "冻结id";

	//date formats

	//columns START
	private Long userId;
	private Date applyTime;
	private Long withdrawAmt;
	private String apprAccount;
	private Date apprTime;
	private Date mcRemitTime;
	private Long status;
	private String sn;
	private Long ipAddr;
	private String approveMemo;
	private Date applyExpireTime;
	private String memo;
	private String mcSn;
	private String topAcc;	
	private Date mcNoticeTime;
	private Long mcFee;
	private String mcMemo;
	private String userBankStruc;
	private String applyAccount;
	private String appr2Acct;
	private Date appr2Time;
	private Long apprBeginStatus;
	private Date noticeMowTime;
	private Date apprBeginTime;
	private String currApprer;
	private Long isVip;
	private Long riskType;
	private String attach;
	private Date appr2BeginTime;
	private Date currDate;
	private Long bankId;
	private Long realWithdrawAmt;
	//columns END
	private String apChannel; 
	private String apProject;
	public FundWithdraw() {
	}

	

	public String getApChannel() {
		return apChannel;
	}



	public void setApChannel(String apChannel) {
		this.apChannel = apChannel;
	}



	public String getApProject() {
		return apProject;
	}



	public void setApProject(String apProject) {
		this.apProject = apProject;
	}









	public String getTopAcc() {
		return topAcc;
	}



	public void setTopAcc(String topAcc) {
		this.topAcc = topAcc;
	}



	public Long getRealWithdrawAmt() {
		return realWithdrawAmt;
	}



	public void setRealWithdrawAmt(Long realWithdrawAmt) {
		this.realWithdrawAmt = realWithdrawAmt;
	}



	public Long getBankId() {
		return bankId;
	}



	public void setBankId(Long bankId) {
		this.bankId = bankId;
	}



	public Date getAppr2BeginTime() {
		return appr2BeginTime;
	}



	public void setAppr2BeginTime(Date appr2BeginTime) {
		this.appr2BeginTime = appr2BeginTime;
	}



	public Date getCurrDate() {
		return currDate;
	}

	public void setCurrDate(Date currDate) {
		this.currDate = currDate;
	}

	public String getAttach() {
		return attach;
	}

	public void setAttach(String attach) {
		this.attach = attach;
	}

	public Long getIsVip() {
		return isVip;
	}

	public void setIsVip(Long isVip) {
		this.isVip = isVip;
	}

	public Long getRiskType() {
		return riskType;
	}

	public void setRiskType(Long riskType) {
		this.riskType = riskType;
	}

	public FundWithdraw(Long id) {
		this.id = id;
	}

	public void setUserId(Long value) {
		this.userId = value;
	}

	public Long getUserId() {
		return this.userId;
	}

	public void setWithdrawAmt(Long value) {
		this.withdrawAmt = value;
	}

	public Long getWithdrawAmt() {
		return this.withdrawAmt;
	}

	public void setStatus(Long value) {
		this.status = value;
	}

	public Long getStatus() {
		return this.status;
	}

	public void setSn(String value) {
		this.sn = value;
	}

	public String getCurrApprer() {
		return currApprer;
	}

	public void setCurrApprer(String currApprer) {
		this.currApprer = currApprer;
	}

	public String getSn() {
		return this.sn;
	}

	public void setApproveMemo(String value) {
		this.approveMemo = value;
	}

	public String getApproveMemo() {
		return this.approveMemo;
	}

	public void setMemo(String value) {
		this.memo = value;
	}

	public String getMemo() {
		return this.memo;
	}

	public void setMcSn(String value) {
		this.mcSn = value;
	}

	public String getMcSn() {
		return this.mcSn;
	}

	public void setMcNoticeTime(Date value) {
		this.mcNoticeTime = value;
	}

	public Date getMcNoticeTime() {
		return this.mcNoticeTime;
	}

	public void setMcFee(Long value) {
		this.mcFee = value;
	}

	public Long getMcFee() {
		return this.mcFee;
	}

	public void setMcMemo(String value) {
		this.mcMemo = value;
	}

	public String getMcMemo() {
		return this.mcMemo;
	}

	public Long getIpAddr() {
		return ipAddr;
	}

	public void setIpAddr(Long ipAddr) {
		this.ipAddr = ipAddr;
	}

	public Date getApplyTime() {
		return applyTime;
	}

	public void setApplyTime(Date applyTime) {
		this.applyTime = applyTime;
	}

	public Date getMcRemitTime() {
		return mcRemitTime;
	}

	public void setMcRemitTime(Date mcRemitTime) {
		this.mcRemitTime = mcRemitTime;
	}

	public Date getApplyExpireTime() {
		return applyExpireTime;
	}

	public void setApplyExpireTime(Date applyExpireTime) {
		this.applyExpireTime = applyExpireTime;
	}

	public String getUserBankStruc() {
		return userBankStruc;
	}

	public void setUserBankStruc(String userBankStruc) {
		this.userBankStruc = userBankStruc;
	}

	public String getApprAccount() {
		return apprAccount;
	}

	public void setApprAccount(String apprAccount) {
		this.apprAccount = apprAccount;
	}

	public String getApplyAccount() {
		return applyAccount;
	}

	public void setApplyAccount(String applyAccount) {
		this.applyAccount = applyAccount;
	}

	public Date getApprTime() {
		return apprTime;
	}

	public void setApprTime(Date apprTime) {
		this.apprTime = apprTime;
	}

	public String getAppr2Acct() {
		return appr2Acct;
	}

	public void setAppr2Acct(String appr2Acct) {
		this.appr2Acct = appr2Acct;
	}

	public Date getAppr2Time() {
		return appr2Time;
	}

	public void setAppr2Time(Date appr2Time) {
		this.appr2Time = appr2Time;
	}

	public Long getApprBeginStatus() {
		return apprBeginStatus;
	}

	public void setApprBeginStatus(Long apprBeginStatus) {
		this.apprBeginStatus = apprBeginStatus;
	}

	public Date getNoticeMowTime() {
		return noticeMowTime;
	}

	public void setNoticeMowTime(Date noticeMowTime) {
		this.noticeMowTime = noticeMowTime;
	}

	public Date getApprBeginTime() {
		return apprBeginTime;
	}

	public void setApprBeginTime(Date apprBeginTime) {
		this.apprBeginTime = apprBeginTime;
	}
	
}
