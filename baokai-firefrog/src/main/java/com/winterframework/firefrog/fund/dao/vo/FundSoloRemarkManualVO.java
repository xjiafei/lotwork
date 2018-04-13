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

public class FundSoloRemarkManualVO extends BaseEntity {

	/** 
	* @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么) 
	*/
	private static final long serialVersionUID = 1108895207201073918L;
	//alias
	public static final String TABLE_ALIAS = "自定义附言表";
	public static final String ALIAS_REMARK = "remark";
	public static final String ALIAS_ISUSED = "1表示使用中  0表示可使用";

	//columns START
	private String remark;
	private Integer isused;

	//columns END

	public FundSoloRemarkManualVO() {
	}

	public FundSoloRemarkManualVO(Long id) {
		this.id = id;
	}

	public void setRemark(String value) {
		this.remark = value;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setIsused(Integer value) {
		this.isused = value;
	}

	public Integer getIsused() {
		return this.isused;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).append("Id", getId()).append("Remark", getRemark())
				.append("Isused", getIsused()).toString();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(getId()).append(getRemark()).append(getIsused()).toHashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof FundSoloRemarkManualVO == false)
			return false;
		if (this == obj)
			return true;
		FundSoloRemarkManualVO other = (FundSoloRemarkManualVO) obj;
		return new EqualsBuilder().append(getId(), other.getId())

		.append(getRemark(), other.getRemark())

		.append(getIsused(), other.getIsused())

		.isEquals();
	}
}
