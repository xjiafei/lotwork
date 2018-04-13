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

public class FundFreeze extends BaseEntity {

	//alias
	public static final String TABLE_ALIAS = "FundFreeze";
	public static final String ALIAS_AMT = "amt";
	public static final String ALIAS_TYPE = "类型 1）取现冻结 ";
	public static final String ALIAS_STATUS = "0）冻结  1）扣款成功 2）退款成功";
	public static final String ALIAS_FREEZE_TIME = "freezeTime";
	public static final String ALIAS_DEAL_TIIME = "最后处理时间";
	public static final String ALIAS_FUND_ID = "资金id";

	//date formats

	//columns START
	private Long amt;
	private Long type;
	private Long status;
	private Date freezeTime;
	private Date dealTiime;
	private Long fundId;

	//columns END

	public FundFreeze() {
	}

	public FundFreeze(Long id) {
		this.id = id;
	}

	public void setAmt(Long value) {
		this.amt = value;
	}

	public Long getAmt() {
		return this.amt;
	}

	public void setType(Long value) {
		this.type = value;
	}

	public Long getType() {
		return this.type;
	}

	public void setStatus(Long value) {
		this.status = value;
	}

	public Long getStatus() {
		return this.status;
	}

	public void setFundId(Long value) {
		this.fundId = value;
	}

	public Long getFundId() {
		return this.fundId;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).append("Id", getId()).append("Amt", getAmt()).append("Type", getType())
				.append("Status", getStatus()).append("FreezeTime", getFreezeTime())
				.append("DealTiime", getDealTiime()).append("FundId", getFundId()).toString();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(getId()).append(getAmt()).append(getType()).append(getStatus())
				.append(getFreezeTime()).append(getDealTiime()).append(getFundId()).toHashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof FundFreeze == false) {
			return false;
		}
		if (this == obj) {
			return true;
		}
		FundFreeze other = (FundFreeze) obj;
		return new EqualsBuilder().append(getId(), other.getId())

		.append(getAmt(), other.getAmt())

		.append(getType(), other.getType())

		.append(getStatus(), other.getStatus())

		.append(getFreezeTime(), other.getFreezeTime())

		.append(getDealTiime(), other.getDealTiime())

		.append(getFundId(), other.getFundId())

		.isEquals();
	}

	public Date getFreezeTime() {
		return freezeTime;
	}

	public void setFreezeTime(Date freezeTime) {
		this.freezeTime = freezeTime;
	}

	public Date getDealTiime() {
		return dealTiime;
	}

	public void setDealTiime(Date dealTiime) {
		this.dealTiime = dealTiime;
	}
}
