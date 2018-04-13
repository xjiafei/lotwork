/**
* Copyright (c) 2005-2012 winterframework.com
* Licensed under the Apache License, Version 2.0 (the "License");
*/
package com.winterframework.firefrog.fund.dao.vo;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import com.winterframework.firefrog.fund.web.controller.charge.Status;
import com.winterframework.orm.dal.ibatis3.BaseEntity;

/**
 * @author cms group
 * @version 1.0
 * @since 1.0
 */

public class FundChargeExceptionVO extends BaseEntity {

	private static final long serialVersionUID = -3413801089276932025L;
	//alias
	public static final String TABLE_ALIAS = "FundChargeException";
	public static final String ALIAS_USER_ID = "用户id";
	public static final String ALIAS_BANK_ID = "付款银行id";
	public static final String ALIAS_CARD_NUMBER = "付款卡号";
	public static final String ALIAS_RCV_CARD_NUMBER = "接受卡号";
	public static final String ALIAS_RCV_ACC_NAME = "接收账号";
	public static final String ALIAS_RCV_EMAIL = "接受卡email";
	public static final String ALIAS_REAL_CHARGE_AMT = "实际到账金额";
	public static final String ALIAS_MC_NOTICE_TIME = "mc通知时间";
	public static final String ALIAS_STATUS = "状态0:未处理 1）加游戏币 2）没收 3）可退款 4）退款审核通过 5）退款审核未过 6）已通知mowcum退款 7）已退款 8)退款失败";
	public static final String ALIAS_MEMO = "附言";
	public static final String ALIAS_MC_FEE = "mc小费";
	public static final String ALIAS_SN = "流水号";
	public static final String ALIAS_MC_CHANNEL = "mc 充值渠道";
	public static final String ALIAS_MC_AREA = "mc充值区域";
	public static final String ALIAS_MC_SN = "mc 订单号";
	public static final String ALIAS_MC_BANK_FEE = "mc 收集的用户充值手续费";
	public static final String ALIAS_CARD_ACCT = "付款账户";
	public static final String ALIAS_ATTACHMENT = "附件地址";
	public static final String ALIAS_BANK_NAME = "开户行名称";
	public static final String ALIAS_BANK_ADDR = "开户行地址";
	public static final String ALIAS_APPR_MEMO = "审核备注";

	//date formats

	//columns START
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
	private String mcArea;
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
	private String userChain;
	private String currApprer;
	private Date currDate;
	private Date apprBeginTime;
	private Date appr2BeginTime;
	private Date operatingTime;
	private String baseInfo;
	private Long[] checkStatuses;

	private static Map<Long, Status> map1 = new HashMap<Long, Status>();
	private static Map<Status, Long> map2 = new HashMap<Status, Long>();

	
	//columns END

	public Date getApprBeginTime() {
		return apprBeginTime;
	}

	public void setApprBeginTime(Date apprBeginTime) {
		this.apprBeginTime = apprBeginTime;
	}

	public Date getAppr2BeginTime() {
		return appr2BeginTime;
	}

	public void setAppr2BeginTime(Date appr2BeginTime) {
		this.appr2BeginTime = appr2BeginTime;
	}

	public String getUserChain() {
		return userChain;
	}

	public void setUserChain(String userChain) {
		this.userChain = userChain;
	}

	public FundChargeExceptionVO() {
	}

	public FundChargeExceptionVO(Long id) {
		this.id = id;
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

	public void setMcArea(String value) {
		this.mcArea = value;
	}

	public String getMcArea() {
		return this.mcArea;
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

	public Status status2Entity(long status) {
		return map1.get(status);
	}

	public long status2Bean(Status status) {
		return map2.get(status);
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).append("Id", getId()).append("UserId", getUserId())
				.append("BankId", getBankId()).append("CardNumber", getCardNumber())
				.append("RcvCardNumber", getRcvCardNumber()).append("RcvAccName", getRcvAccName())
				.append("RcvEmail", getRcvEmail()).append("RealChargeAmt", getRealChargeAmt())
				.append("McNoticeTime", getMcNoticeTime()).append("Status", getStatus()).append("Memo", getMemo())
				.append("McFee", getMcFee()).append("Sn", getSn()).append("McChannel", getMcChannel())
				.append("McArea", getMcArea()).append("McSn", getMcSn()).append("McBankFee", getMcBankFee())
				.append("CardAcct", getCardAcct()).append("Attachment", getAttachment())
				.append("BankName", getBankName()).append("BankAddr", getBankAddr()).append("ApprMemo", getApprMemo())
				.toString();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(getId()).append(getUserId()).append(getBankId()).append(getCardNumber())
				.append(getRcvCardNumber()).append(getRcvAccName()).append(getRcvEmail()).append(getRealChargeAmt())
				.append(getMcNoticeTime()).append(getStatus()).append(getMemo()).append(getMcFee()).append(getSn())
				.append(getMcChannel()).append(getMcArea()).append(getMcSn()).append(getMcBankFee())
				.append(getCardAcct()).append(getAttachment()).append(getBankName()).append(getBankAddr())
				.append(getApprMemo()).toHashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof FundChargeExceptionVO == false) {
			return false;
		}
		if (this == obj) {
			return true;
		}
		FundChargeExceptionVO other = (FundChargeExceptionVO) obj;
		return new EqualsBuilder().append(getId(), other.getId())

		.append(getUserId(), other.getUserId())

		.append(getBankId(), other.getBankId())

		.append(getCardNumber(), other.getCardNumber())

		.append(getRcvCardNumber(), other.getRcvCardNumber())

		.append(getRcvAccName(), other.getRcvAccName())

		.append(getRcvEmail(), other.getRcvEmail())

		.append(getRealChargeAmt(), other.getRealChargeAmt())

		.append(getMcNoticeTime(), other.getMcNoticeTime())

		.append(getStatus(), other.getStatus())

		.append(getMemo(), other.getMemo())

		.append(getMcFee(), other.getMcFee())

		.append(getSn(), other.getSn())

		.append(getMcChannel(), other.getMcChannel())

		.append(getMcArea(), other.getMcArea())

		.append(getMcSn(), other.getMcSn())

		.append(getMcBankFee(), other.getMcBankFee())

		.append(getCardAcct(), other.getCardAcct())

		.append(getAttachment(), other.getAttachment())

		.append(getBankName(), other.getBankName())

		.append(getBankAddr(), other.getBankAddr())

		.append(getApprMemo(), other.getApprMemo())

		.isEquals();
	}

	public Date getMcExactTime() {
		return mcExactTime;
	}

	public void setMcExactTime(Date mcExactTime) {
		this.mcExactTime = mcExactTime;
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

	public String getCurrApprer() {
		return currApprer;
	}

	public void setCurrApprer(String currApprer) {
		this.currApprer = currApprer;
	}

	public Date getCurrDate() {
		return currDate;
	}

	public void setCurrDate(Date currDate) {
		this.currDate = currDate;
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
