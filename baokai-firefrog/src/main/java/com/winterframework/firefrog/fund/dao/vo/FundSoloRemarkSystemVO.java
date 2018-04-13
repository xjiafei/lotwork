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

public class FundSoloRemarkSystemVO extends BaseEntity {

	//alias
	public static final String TABLE_ALIAS = "系统附言，长度为5，系统默认初始化10w条记录，通过seq获取下一条";
	public static final String ALIAS_REMARK = "remark";

	//date formats

	//columns START
	private String remark;

	//columns END

	public FundSoloRemarkSystemVO() {
	}

	public FundSoloRemarkSystemVO(Long id) {
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
		return new ToStringBuilder(this).append("Id", getId()).append("Remark", getRemark()).toString();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(getId()).append(getRemark()).toHashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof FundSoloRemarkSystemVO == false)
			return false;
		if (this == obj)
			return true;
		FundSoloRemarkSystemVO other = (FundSoloRemarkSystemVO) obj;
		return new EqualsBuilder().append(getId(), other.getId())

		.append(getRemark(), other.getRemark())

		.isEquals();
	}
}
