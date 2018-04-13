 /**
 * Copyright (c) 2005-2012 winterframework.com
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
 package com.winterframework.firefrog.fund.dao.vo;

import java.util.Date;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import com.winterframework.orm.dal.ibatis3.BaseEntity;

/**
 * @author cms group
 * @version 1.0
 * @since 1.0
 */


public class FundMowPay extends BaseEntity {
	
	private static final long serialVersionUID = 7178065768599853279L;
	//alias
	public static final String TABLE_ALIAS = "FundMowPay";
	public static final String ALIAS_FF_COMPANY_ID = "企业id";
	public static final String ALIAS_FF_BANK_ID = "银行id";
	public static final String ALIAS_FF_AMOUNT = "金额";
	public static final String ALIAS_FF_CARD_NUM = "卡号";
	public static final String ALIAS_FF_CARD_NAME = "用户名";
	public static final String ALIAS_FF_ISSUE_BANK_NAME = "支行名称";
	public static final String ALIAS_FF_ISSUE_BANK_ADDRESS = "支行地址";
	public static final String ALIAS_FF_COMPANY_USER = "用户名";
	public static final String ALIAS_MOWNECUM_ORDER_NUM = "mow 订单号";
	public static final String ALIAS_MOW_STATUS = "订单处理状态";
	public static final String ALIAS_MOW_TRANSACTION_CHARGE = "订单预计手续费";
	public static final String ALIAS_MOW_EXACT_TRANSACTION_CHARGE = "订单实际手续费";
	public static final String ALIAS_APPLY_TIME = "申请时间";
	public static final String ALIAS_RESPONSE_TIME = "响应时间";
	public static final String ALIAS_NOTICE_TIME = "通知时间";
	public static final String ALIAS_TYPE = "类型 外键类型 1）用户提现  2）异常退款  3）人工打款  4）异常补款";
	public static final String ALIAS_EX_ID = "外键id";
	public static final String ALIAS_EX_SN = "外键流水号";
	public static final String ALIAS_MOW_DETAIL = "付款明细";
	public static final String ALIAS_MOW_AMOUNT = "实际打款金额";
	
	//date formats
	
	//columns START
	private String ffCompanyId;
	private Long ffBankId;
	private Long ffAmount;
	private String ffCardNum;
	private String ffCardName;
	private String ffIssueBankName;
	private String ffIssueBankAddress;
	private String ffCompanyUser;
	private String mownecumOrderNum;
	private Long mowStatus;
	private Long mowTransactionCharge;
	private Long mowExactTransactionCharge;
	private Date applyTime;
	private Date responseTime;
	private Date noticeTime;
	private Long type;
	private Long exId;
	private String exSn;
	private String mowDetail;
	private Long mowAmount;
	private Long confirmCnt;
	//columns END

	public FundMowPay(){
	}

	public Long getConfirmCnt() {
		return confirmCnt;
	}

	public void setConfirmCnt(Long confirmCnt) {
		this.confirmCnt = confirmCnt;
	}

	public void setFfCompanyId(String value) {
		this.ffCompanyId = value;
	}
	
	public String getFfCompanyId() {
		return this.ffCompanyId;
	}
	public void setFfBankId(Long value) {
		this.ffBankId = value;
	}
	
	public Long getFfBankId() {
		return this.ffBankId;
	}
	public void setFfAmount(Long value) {
		this.ffAmount = value;
	}
	
	public Long getFfAmount() {
		return this.ffAmount;
	}
	public void setFfCardNum(String value) {
		this.ffCardNum = value;
	}
	
	public String getFfCardNum() {
		return this.ffCardNum;
	}
	public void setFfCardName(String value) {
		this.ffCardName = value;
	}
	
	public String getFfCardName() {
		return this.ffCardName;
	}
	public void setFfIssueBankName(String value) {
		this.ffIssueBankName = value;
	}
	
	public String getFfIssueBankName() {
		return this.ffIssueBankName;
	}
	public void setFfIssueBankAddress(String value) {
		this.ffIssueBankAddress = value;
	}
	
	public String getFfIssueBankAddress() {
		return this.ffIssueBankAddress;
	}
	public void setFfCompanyUser(String value) {
		this.ffCompanyUser = value;
	}
	
	public String getFfCompanyUser() {
		return this.ffCompanyUser;
	}
	public void setMownecumOrderNum(String value) {
		this.mownecumOrderNum = value;
	}
	
	public String getMownecumOrderNum() {
		return this.mownecumOrderNum;
	}
	public void setMowStatus(Long value) {
		this.mowStatus = value;
	}
	
	public Long getMowStatus() {
		return this.mowStatus;
	}
	public void setMowTransactionCharge(Long value) {
		this.mowTransactionCharge = value;
	}
	
	public Long getMowTransactionCharge() {
		return this.mowTransactionCharge;
	}
	public void setMowExactTransactionCharge(Long value) {
		this.mowExactTransactionCharge = value;
	}
	
	public Long getMowExactTransactionCharge() {
		return this.mowExactTransactionCharge;
	}
	public void setApplyTime(Date value) {
		this.applyTime = value;
	}
	
	public Date getApplyTime() {
		return this.applyTime;
	}
	public void setResponseTime(Date value) {
		this.responseTime = value;
	}
	
	public Date getResponseTime() {
		return this.responseTime;
	}
	public void setNoticeTime(Date value) {
		this.noticeTime = value;
	}
	
	public Date getNoticeTime() {
		return this.noticeTime;
	}
	public void setType(Long value) {
		this.type = value;
	}
	
	public Long getType() {
		return this.type;
	}
	public void setExId(Long value) {
		this.exId = value;
	}
	
	public Long getExId() {
		return this.exId;
	}
	public void setExSn(String value) {
		this.exSn = value;
	}
	
	public String getExSn() {
		return this.exSn;
	}
	public void setMowDetail(String value) {
		this.mowDetail = value;
	}
	
	public String getMowDetail() {
		return this.mowDetail;
	}
	public void setMowAmount(Long value) {
		this.mowAmount = value;
	}
	
	public Long getMowAmount() {
		return this.mowAmount;
	}
    @Override
	public String toString() {
		return new ToStringBuilder(this)
		.append("Id",getId())		
		.append("FfCompanyId",getFfCompanyId())		
		.append("FfBankId",getFfBankId())		
		.append("FfAmount",getFfAmount())		
		.append("FfCardNum",getFfCardNum())		
		.append("FfCardName",getFfCardName())		
		.append("FfIssueBankName",getFfIssueBankName())		
		.append("FfIssueBankAddress",getFfIssueBankAddress())		
		.append("FfCompanyUser",getFfCompanyUser())		
		.append("MownecumOrderNum",getMownecumOrderNum())		
		.append("MowStatus",getMowStatus())		
		.append("MowTransactionCharge",getMowTransactionCharge())		
		.append("MowExactTransactionCharge",getMowExactTransactionCharge())		
		.append("ApplyTime",getApplyTime())		
		.append("ResponseTime",getResponseTime())		
		.append("NoticeTime",getNoticeTime())		
		.append("Type",getType())		
		.append("ExId",getExId())		
		.append("ExSn",getExSn())		
		.append("MowDetail",getMowDetail())		
		.append("MowAmount",getMowAmount())		
			.toString();
	}
    @Override
	public int hashCode() {
		return new HashCodeBuilder()
		.append(getId())
		.append(getFfCompanyId())
		.append(getFfBankId())
		.append(getFfAmount())
		.append(getFfCardNum())
		.append(getFfCardName())
		.append(getFfIssueBankName())
		.append(getFfIssueBankAddress())
		.append(getFfCompanyUser())
		.append(getMownecumOrderNum())
		.append(getMowStatus())
		.append(getMowTransactionCharge())
		.append(getMowExactTransactionCharge())
		.append(getApplyTime())
		.append(getResponseTime())
		.append(getNoticeTime())
		.append(getType())
		.append(getExId())
		.append(getExSn())
		.append(getMowDetail())
		.append(getMowAmount())
			.toHashCode();
	}
    @Override
	public boolean equals(Object obj) {
		if(obj instanceof FundMowPay == false) return false;
		if(this == obj) return true;
		FundMowPay other = (FundMowPay)obj;
		return new EqualsBuilder()
		.append(getId(),other.getId())

		.append(getFfCompanyId(),other.getFfCompanyId())

		.append(getFfBankId(),other.getFfBankId())

		.append(getFfAmount(),other.getFfAmount())

		.append(getFfCardNum(),other.getFfCardNum())

		.append(getFfCardName(),other.getFfCardName())

		.append(getFfIssueBankName(),other.getFfIssueBankName())

		.append(getFfIssueBankAddress(),other.getFfIssueBankAddress())

		.append(getFfCompanyUser(),other.getFfCompanyUser())

		.append(getMownecumOrderNum(),other.getMownecumOrderNum())

		.append(getMowStatus(),other.getMowStatus())

		.append(getMowTransactionCharge(),other.getMowTransactionCharge())

		.append(getMowExactTransactionCharge(),other.getMowExactTransactionCharge())

		.append(getApplyTime(),other.getApplyTime())

		.append(getResponseTime(),other.getResponseTime())

		.append(getNoticeTime(),other.getNoticeTime())

		.append(getType(),other.getType())

		.append(getExId(),other.getExId())

		.append(getExSn(),other.getExSn())

		.append(getMowDetail(),other.getMowDetail())

		.append(getMowAmount(),other.getMowAmount())

			.isEquals();
	}
}

