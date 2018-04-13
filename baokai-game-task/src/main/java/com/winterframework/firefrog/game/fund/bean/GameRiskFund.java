 /**
 * Copyright (c) 2005-2012 winterframework.com
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
 package com.winterframework.firefrog.game.fund.bean;

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


public class GameRiskFund extends BaseEntity {
	
	private static final long serialVersionUID = -278693360453228150L;
	//alias
	public static final String TABLE_ALIAS = "风控中心资金表";
	public static final String ALIAS_USERID = "用户ID";
	public static final String ALIAS_ORDER_CODE = "订单编号";
	public static final String ALIAS_PLAN_CODE = "计划编号";
	public static final String ALIAS_PLAN_DETAIL_ID = "计划预约ID";
	public static final String ALIAS_LOTTERYID = "彩种";
	public static final String ALIAS_ISSUE_CODE = "奖期编码";
	public static final String ALIAS_AMOUNT = "操作金额";
	public static final String ALIAS_FUND_TYPE = "资金摘要";
	public static final String ALIAS_STATUS = "状态 1 冻结  2 解冻 3 加款 4 减款";
	public static final String ALIAS_CANCEL_STATUS = "取消状态 0 未撤销 1 已撤销";
	public static final String ALIAS_CREATE_TIME = "创建时间";
	public static final String ALIAS_UPDATE_TIME = "更新时间";
	
	//date formats
	
	//columns START
	private Long userid;
	private String orderCode;
	private String planCode;
	private Long planDetailId;
	private Long lotteryid;
	private Long issueCode;
	private Long amount;
	private Long fundType;
	private Long status;
	private Long cancelStatus;
	private Date createTime;
	private Date updateTime;
	//columns END

	public GameRiskFund(){
	}

	public GameRiskFund(
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
	public void setOrderCode(String value) {
		this.orderCode = value;
	}
	
	public String getOrderCode() {
		return this.orderCode;
	}
	public void setPlanCode(String value) {
		this.planCode = value;
	}
	
	public String getPlanCode() {
		return this.planCode;
	}
	public void setPlanDetailId(Long value) {
		this.planDetailId = value;
	}
	
	public Long getPlanDetailId() {
		return this.planDetailId;
	}
	public void setLotteryid(Long value) {
		this.lotteryid = value;
	}
	
	public Long getLotteryid() {
		return this.lotteryid;
	}
	public void setIssueCode(Long value) {
		this.issueCode = value;
	}
	
	public Long getIssueCode() {
		return this.issueCode;
	}
	public void setAmount(Long value) {
		this.amount = value;
	}
	
	public Long getAmount() {
		return this.amount;
	}
	public void setFundType(Long value) {
		this.fundType = value;
	}
	
	public Long getFundType() {
		return this.fundType;
	}
	public void setStatus(Long value) {
		this.status = value;
	}
	
	public Long getStatus() {
		return this.status;
	}
	public void setCancelStatus(Long value) {
		this.cancelStatus = value;
	}
	
	public Long getCancelStatus() {
		return this.cancelStatus;
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

	@Override
	public String toString() {
		return new ToStringBuilder(this)
		.append("Id",getId())		
		.append("Userid",getUserid())		
		.append("OrderCode",getOrderCode())		
		.append("PlanCode",getPlanCode())		
		.append("PlanDetailId",getPlanDetailId())		
		.append("Lotteryid",getLotteryid())		
		.append("IssueCode",getIssueCode())		
		.append("Amount",getAmount())		
		.append("FundType",getFundType())		
		.append("Status",getStatus())		
		.append("CancelStatus",getCancelStatus())		
		.append("CreateTime",getCreateTime())		
		.append("UpdateTime",getUpdateTime())		
			.toString();
	}
    @Override
	public int hashCode() {
		return new HashCodeBuilder()
		.append(getId())
		.append(getUserid())
		.append(getOrderCode())
		.append(getPlanCode())
		.append(getPlanDetailId())
		.append(getLotteryid())
		.append(getIssueCode())
		.append(getAmount())
		.append(getFundType())
		.append(getStatus())
		.append(getCancelStatus())
		.append(getCreateTime())
		.append(getUpdateTime())
			.toHashCode();
	}
    @Override
	public boolean equals(Object obj) {
		if(obj instanceof GameRiskFund == false) return false;
		if(this == obj) return true;
		GameRiskFund other = (GameRiskFund)obj;
		return new EqualsBuilder()
		.append(getId(),other.getId())

		.append(getUserid(),other.getUserid())

		.append(getOrderCode(),other.getOrderCode())

		.append(getPlanCode(),other.getPlanCode())

		.append(getPlanDetailId(),other.getPlanDetailId())

		.append(getLotteryid(),other.getLotteryid())

		.append(getIssueCode(),other.getIssueCode())

		.append(getAmount(),other.getAmount())

		.append(getFundType(),other.getFundType())

		.append(getStatus(),other.getStatus())

		.append(getCancelStatus(),other.getCancelStatus())

		.append(getCreateTime(),other.getCreateTime())

		.append(getUpdateTime(),other.getUpdateTime())

			.isEquals();
	}
}

