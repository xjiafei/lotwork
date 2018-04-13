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

public class FundUserRemarkVO extends BaseEntity {

	/** 
	* @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么) 
	*/
	private static final long serialVersionUID = 7064927354557940382L;
	//alias
	public static final String TABLE_ALIAS = "客户与附言管理表";
	public static final String ALIAS_USER_ID = "userId";
	public static final String ALIAS_REMARK = "remark";
	public static final String ALIAS_GMT_AUTOUNLOCKED = "gmtAutounlocked";
	public static final String ALIAS_GMT_CANSETREMARK = "下一个可设置附言的时间";

	//date formats

	//columns START
	private Long userId;
	private String remark;
	private Date gmtAutounlocked;
	private Date gmtCansetremark;
	private Boolean setRemarkNull;

	//columns END

	public FundUserRemarkVO() {
	}

	public FundUserRemarkVO(Long id) {
		this.id = id;
	}

	public void setUserId(Long value) {
		this.userId = value;
	}

	public Long getUserId() {
		return this.userId;
	}

	public void setRemark(String value) {
		this.remark = value;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setGmtAutounlocked(Date value) {
		this.gmtAutounlocked = value;
	}

	public Date getGmtAutounlocked() {
		return this.gmtAutounlocked;
	}

	public void setGmtCansetremark(Date value) {
		this.gmtCansetremark = value;
	}

	public Date getGmtCansetremark() {
		return this.gmtCansetremark;
	}
	

	public Boolean isSetRemarkNull() {
		return setRemarkNull;
	}

	public void setSetRemarkNull(Boolean setRemarkNull) {
		this.setRemarkNull = setRemarkNull;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).append("Id", getId()).append("UserId", getUserId())
				.append("Remark", getRemark()).append("GmtCreated", getGmtCreated())
				.append("GmtModified", getGmtModified()).append("GmtAutounlocked", getGmtAutounlocked())
				.append("GmtCansetremark", getGmtCansetremark()).toString();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(getId()).append(getUserId()).append(getRemark()).append(getGmtCreated())
				.append(getGmtModified()).append(getGmtAutounlocked()).append(getGmtCansetremark()).toHashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof FundUserRemarkVO == false)
			return false;
		if (this == obj)
			return true;
		FundUserRemarkVO other = (FundUserRemarkVO) obj;
		return new EqualsBuilder().append(getId(), other.getId())

		.append(getUserId(), other.getUserId())

		.append(getRemark(), other.getRemark())

		.append(getGmtCreated(), other.getGmtCreated())

		.append(getGmtModified(), other.getGmtModified())

		.append(getGmtAutounlocked(), other.getGmtAutounlocked())

		.append(getGmtCansetremark(), other.getGmtCansetremark())

		.isEquals();
	}
}
