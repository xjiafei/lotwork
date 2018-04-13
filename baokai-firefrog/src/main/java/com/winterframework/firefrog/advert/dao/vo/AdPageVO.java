/**
* Copyright (c) 2005-2012 winterframework.com
* Licensed under the Apache License, Version 2.0 (the "License");
*/
package com.winterframework.firefrog.advert.dao.vo;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import com.winterframework.orm.dal.ibatis3.BaseEntity;

/**
 * @author cms group
 * @version 1.0
 * @since 1.0
 */

public class AdPageVO extends BaseEntity {

	/** 
	* @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么) 
	*/ 
	private static final long serialVersionUID = -4646389375617847638L;
	//alias
	public static final String TABLE_ALIAS = "AdPage";
	public static final String ALIAS_NAME = "名字";
	public static final String ALIAS_MEMO = "描述";

	//date formats

	//columns START
	private String name;
	private String memo;

	//columns END

	public AdPageVO() {
	}

	public AdPageVO(Long id) {
		this.id = id;
	}

	public void setName(String value) {
		this.name = value;
	}

	public String getName() {
		return this.name;
	}

	public void setMemo(String value) {
		this.memo = value;
	}

	public String getMemo() {
		return this.memo;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).append("Id", getId()).append("Name", getName()).append("Memo", getMemo())
				.toString();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(getId()).append(getName()).append(getMemo()).toHashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof AdPageVO == false)
			return false;
		if (this == obj)
			return true;
		AdPageVO other = (AdPageVO) obj;
		return new EqualsBuilder().append(getId(), other.getId())

		.append(getName(), other.getName())

		.append(getMemo(), other.getMemo())

		.isEquals();
	}
}
