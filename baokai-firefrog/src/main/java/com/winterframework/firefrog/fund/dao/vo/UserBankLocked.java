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

public class UserBankLocked extends BaseEntity {

	//alias
	public static final String TABLE_ALIAS = "UserBankLocked";
	public static final String ALIAS_USER_ID = "用户id";
	public static final String ALIAS_OVER_TIME = "解锁时间";
	public static final String OPERATOR = "操作人";

	//date formats

	//columns START
	private Long userId;
	private Date overTime;
	private String operator;
	private Long bindcardType;

	//columns END

	public UserBankLocked() {
	}

	public UserBankLocked(Long id) {
		this.id = id;
	}

	public void setUserId(Long value) {
		this.userId = value;
	}

	public Long getUserId() {
		return this.userId;
	}

	public void setOverTime(Date value) {
		this.overTime = value;
	}

	public Date getOverTime() {
		return this.overTime;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).append("Id", getId()).append("UserId", getUserId())
				.append("OverTime", getOverTime()).append("Operator", getOperator()).toString();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(getId()).append(getUserId()).append(getOverTime()).append(getOperator())
				.toHashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof UserBankLocked == false) {
			return false;
		}
		if (this == obj) {
			return true;
		}
		UserBankLocked other = (UserBankLocked) obj;
		return new EqualsBuilder().append(getId(), other.getId())

		.append(getUserId(), other.getUserId())

		.append(getOverTime(), other.getOverTime())

		.append(getOperator(), other.getOperator())

		.isEquals();
	}

	public Long getBindcardType() {
		return bindcardType;
	}

	public void setBindcardType(Long bindcardType) {
		this.bindcardType = bindcardType;
	}
}
