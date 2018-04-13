/**
* Copyright (c) 2005-2012 winterframework.com
* Licensed under the Apache License, Version 2.0 (the "License");
*/
package com.winterframework.firefrog.fund.dao.vo;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import com.winterframework.orm.dal.ibatis3.BaseEntity;

/**
 * @author cms group
 * @version 1.0
 * @since 1.0
 */

public class Fund extends BaseEntity {

	/** 
	* @Fields serialVersionUID :
	*/
	private static final long serialVersionUID = -5099386609280284355L;
	//alias
	public static final String TABLE_ALIAS = "Fund";
	public static final String ALIAS_BAL = "0.01分";
	public static final String ALIAS_DISABLE_AMT = "不可提现余额 0.01分";
	public static final String ALIAS_USER_ID = "userId";
	public static final String ALIAS_SECURITY = "安全验证";
	public static final String ALIAS_FROZEN_AMT = "冻结金额";

	//date formats

	//columns START
	private Long bal;
	private Long disableAmt;
	private Long userId;
	private String security;
	private Long frozenAmt;

	//columns END

	public Fund() {
	}

	public Fund(Long id) {
		this.id = id;
	}

	public void setBal(Long value) {
		this.bal = value;
	}

	public Long getBal() {
		return this.bal;
	}

	public void setDisableAmt(Long value) {
		this.disableAmt = value;
	}

	public Long getDisableAmt() {
		return this.disableAmt;
	}

	public void setUserId(Long value) {
		this.userId = value;
	}

	public Long getUserId() {
		return this.userId;
	}

	public void setSecurity(String value) {
		this.security = value;
	}

	public String getSecurity() {
		return this.security;
	}

	public void setFrozenAmt(Long value) {
		this.frozenAmt = value;
	}

	public Long getFrozenAmt() {
		return this.frozenAmt;
	}

	public static Fund getZeroFund(Long userId) {
		Fund fund = new Fund();
		fund.setBal(0L);
		fund.setUserId(userId);
		fund.setFrozenAmt(0L);
		fund.setDisableAmt(0L);
		return fund;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).append("Id", getId()).append("Bal", getBal())
				.append("DisableAmt", getDisableAmt()).append("UserId", getUserId()).append("Security", getSecurity())
				.append("FrozenAmt", getFrozenAmt()).toString();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(getId()).append(getBal()).append(getDisableAmt()).append(getUserId())
				.append(getSecurity()).append(getFrozenAmt()).toHashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Fund == false) {
			return false;
		}
		if (this == obj) {
			return true;
		}
		Fund other = (Fund) obj;
		return new EqualsBuilder().append(getId(), other.getId())

		.append(getBal(), other.getBal())

		.append(getDisableAmt(), other.getDisableAmt())

		.append(getUserId(), other.getUserId())

		.append(getSecurity(), other.getSecurity())

		.append(getFrozenAmt(), other.getFrozenAmt())

		.isEquals();
	}
}
