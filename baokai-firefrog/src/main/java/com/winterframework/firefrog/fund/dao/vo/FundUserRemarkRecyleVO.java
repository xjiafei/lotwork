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

public class FundUserRemarkRecyleVO extends BaseEntity {

	/** 
	* @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么) 
	*/
	private static final long serialVersionUID = -991517470683204886L;
	//alias
	public static final String TABLE_ALIAS = "附言回收表";
	public static final String ALIAS_REMARK = "remark";

	//date formats

	//columns START
	private String remark;

	private Long userId;

	//columns END

	public FundUserRemarkRecyleVO() {
	}

	public FundUserRemarkRecyleVO(Long id) {
		this.id = id;
	}

	public void setRemark(String value) {
		this.remark = value;
	}

	public String getRemark() {
		return this.remark;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).append("Id", getId()).append("Remark", getRemark())
				.append("GmtCreated", getGmtCreated()).append("userId", getUserId()).toString();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(getId()).append(getRemark()).append(getGmtCreated()).append(getUserId())
				.toHashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof FundUserRemarkRecyleVO == false)
			return false;
		if (this == obj)
			return true;
		FundUserRemarkRecyleVO other = (FundUserRemarkRecyleVO) obj;
		return new EqualsBuilder().append(getId(), other.getId())

		.append(getRemark(), other.getRemark())

		.append(getGmtCreated(), other.getGmtCreated()).append(getUserId(), other.getUserId())

		.isEquals();
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}
}
