 /**
 * Copyright (c) 2005-2012 winterframework.com
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
 package com.winterframework.firefrog.game.dao.vo;

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


public class GameTransactionFund extends BaseEntity {
	
	private static final long serialVersionUID = -3698004485826869274L;
	//alias
	public static final String TABLE_ALIAS = "风控中心交易资金表";
	public static final String ALIAS_USERID = "执行用户ID 系统默认1";
	public static final String ALIAS_AMOUNT = "操作金额";
	public static final String ALIAS_FUND_TYPE = "资金摘要";
	public static final String ALIAS_STATUS = "状态 1发送成功  2 发送失败";
	public static final String ALIAS_OPERATORID = "资金变更用户id";
	public static final String ALIAS_CREATE_TIME = "创建时间";
	public static final String ALIAS_UPDATE_TIME = "更新时间";
	public static final String ALIAS_TID = "资金交易流水号";
	public static final String ALIAS_REASON = "资金交易原因";
	public static final String ALIAS_SYMBOL = "交易符号(-+)";
	public static final String ALIAS_ORDER_CODE = "订单编码";
	public static final String ALIAS_CHECK_TYPE = "审核类型 1 投注冻结 2 派发奖金 3 投注扣款 4 撤销扣款";
	public static final String ALIAS_PLAN_CODE = "计划编码";
	public static final String ALIAS_LOTTERYID = "彩种ID";
	public static final String ALIAS_ISSUE_CODE = "奖期";
	public static final String ALIAS_WEB_ISSUE_CODE = "WEB显示奖期";
	public static final String ALIAS_BALANCE="余额";
	//date formats
	
	//columns START
	private Long userid;
	private Long amount;
	private String fundType;
	private Long status;
	private Long operatorid;
	private Date createTime;
	private Date updateTime;
	//交易流水号
	private String tid;
	private String reason;
	//交易符号
	private String symbol;
	
	private String orderCode;
	private Long checkType;
	private String planCode;
	private Long lotteryId;
	private Long issueCode;
	private String webIssueCode;
	private Long balance;
	//columns END

	//查询值
	private String lotteryName;
	private String operatorName;
	
	public GameTransactionFund(){
	}

	public String getOrderCode() {
		return orderCode;
	}

	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}

	public Long getCheckType() {
		return checkType;
	}

	public void setCheckType(Long checkType) {
		this.checkType = checkType;
	}

	public String getPlanCode() {
		return planCode;
	}

	public void setPlanCode(String planCode) {
		this.planCode = planCode;
	}

	public Long getLotteryId() {
		return lotteryId;
	}

	public void setLotteryId(Long lotteryId) {
		this.lotteryId = lotteryId;
	}

	public Long getIssueCode() {
		return issueCode;
	}

	public void setIssueCode(Long issueCode) {
		this.issueCode = issueCode;
	}

	public String getWebIssueCode() {
		return webIssueCode;
	}

	public void setWebIssueCode(String webIssueCode) {
		this.webIssueCode = webIssueCode;
	}

	public GameTransactionFund(
		Long id
	){
		this.id = id;
	}

	public void setUserid(Long value) {
		this.userid = value;
	}
	
	public Long getUserid() {
		return this.userid;
	}
	public void setAmount(Long value) {
		this.amount = value;
	}
	
	public Long getAmount() {
		return this.amount;
	}
	
	public void setStatus(Long value) {
		this.status = value;
	}
	
	public Long getStatus() {
		return this.status;
	}
	public void setOperatorid(Long value) {
		this.operatorid = value;
	}
	
	public Long getOperatorid() {
		return this.operatorid;
	}

    public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getFundType() {
		return fundType;
	}

	public void setFundType(String fundType) {
		this.fundType = fundType;
	}

	public String getTid() {
		return tid;
	}

	public void setTid(String tid) {
		this.tid = tid;
	}

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}
	

	@Override
	public String toString() {
		return new ToStringBuilder(this)
		.append("Id",getId())		
		.append("Userid",getUserid())		
		.append("Amount",getAmount())		
		.append("FundType",getFundType())		
		.append("Status",getStatus())		
		.append("Operatorid",getOperatorid())		
		.append("CreateTime",getCreateTime())		
		.append("UpdateTime",getUpdateTime())	
		.append("tid",getTid())
		.append("symbol",getSymbol())
		.append("orderCode",getOrderCode())
		.append("checkType",getCheckType())
		.append("planCode",getPlanCode())
		.append("lotteryId",getLotteryId())
		.append("issueCode",getIssueCode())
		.append("webIssueCode",getWebIssueCode())
			.toString();
	}

    @Override
	public int hashCode() {
		return new HashCodeBuilder()
		.append(getId())
		.append(getUserid())
		.append(getAmount())
		.append(getFundType())
		.append(getStatus())
		.append(getOperatorid())
		.append(getCreateTime())
		.append(getUpdateTime())
		.append(getTid())
		.append(getSymbol())
		.append(getOrderCode())
		.append(getCheckType())
		.append(getPlanCode())
		.append(getLotteryId())
		.append(getIssueCode())
		.append(getWebIssueCode())
			.toHashCode();
	}
    @Override
	public boolean equals(Object obj) {
		if(obj instanceof GameTransactionFund == false) return false;
		if(this == obj) return true;
		GameTransactionFund other = (GameTransactionFund)obj;
		return new EqualsBuilder()
		.append(getId(),other.getId())
		.append(getUserid(),other.getUserid())
		.append(getAmount(),other.getAmount())
		.append(getFundType(),other.getFundType())
		.append(getStatus(),other.getStatus())
		.append(getOperatorid(),other.getOperatorid())
		.append(getCreateTime(),other.getCreateTime())
		.append(getUpdateTime(),other.getUpdateTime())
		.append(getTid(),other.getTid())
		.append(getSymbol(),other.getSymbol())
		.append(getOrderCode(),other.getOrderCode())
		.append(getCheckType(),other.getCheckType())
		.append(getPlanCode(),other.getPlanCode())
		.append(getLotteryId(),other.getLotteryId())
		.append(getIssueCode(),other.getIssueCode())
		.append(getWebIssueCode(),other.getWebIssueCode())
			.isEquals();
	}

	public String getLotteryName() {
		return lotteryName;
	}

	public void setLotteryName(String lotteryName) {
		this.lotteryName = lotteryName;
	}

	public String getOperatorName() {
		return operatorName;
	}

	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public Long getBalance() {
		return balance;
	}

	public void setBalance(Long balance) {
		this.balance = balance;
	}

}

