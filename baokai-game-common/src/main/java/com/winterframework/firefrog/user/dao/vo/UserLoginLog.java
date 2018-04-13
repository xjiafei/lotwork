/**
 * Copyright (c) 2005-2012 winterframework.com
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.winterframework.firefrog.user.dao.vo;

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

public class UserLoginLog extends BaseEntity {

	private static final long serialVersionUID = -1458561835326044059L;
	// alias
	public static final String TABLE_ALIAS = "UserLoginLog";
	public static final String ALIAS_USER_ID = "userId";
	public static final String ALIAS_LOGIN_DATE = "loginDate";
	public static final String ALIAS_LOGIN_IP = "loginIp";

	// date formats

	// columns START
	private Long userId;
	private Date loginDate;
	private Long loginIp;

	// columns END

	public UserLoginLog() {
	}

	public void setUserId(Long value) {
		this.userId = value;
	}

	public Long getUserId() {
		return this.userId;
	}

	public void setLoginDate(Date value) {
		this.loginDate = value;
	}

	public Date getLoginDate() {
		return this.loginDate;
	}

	public void setLoginIp(Long value) {
		this.loginIp = value;
	}

	public Long getLoginIp() {
		return this.loginIp;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).append("Id", getId()).append("UserId", getUserId())
				.append("LoginDate", getLoginDate()).append("LoginIp", getLoginIp()).toString();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(getId()).append(getUserId()).append(getLoginDate()).append(getLoginIp())
				.toHashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof UserLoginLog == false) {
			return false;
		}
		if (this == obj) {
			return true;
		}
		UserLoginLog other = (UserLoginLog) obj;
		return new EqualsBuilder().append(getId(), other.getId())

		.append(getUserId(), other.getUserId())

		.append(getLoginDate(), other.getLoginDate())

		.append(getLoginIp(), other.getLoginIp())

		.isEquals();
	}
}
