/**
* Copyright (c) 2005-2012 winterframework.com
* Licensed under the Apache License, Version 2.0 (the "License");
*/
package com.winterframework.firefrog.advert.web.dto;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import com.winterframework.orm.dal.ibatis3.BaseEntity;

/**
 * @author cms group
 * @version 1.0
 * @since 1.0
 */

public class AdPageRequest extends BaseEntity {

	//alias
	public static final String TABLE_ALIAS = "AdPage";
	public static final String ALIAS_NAME = "名字";
	public static final String ALIAS_MEMO = "描述";

	//date formats

	//columns START
	private String name;
	private String memo;

	//columns END

	public AdPageRequest() {
	}

	public AdPageRequest(Long id) {
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
		if (obj instanceof AdPageRequest == false)
			return false;
		if (this == obj)
			return true;
		AdPageRequest other = (AdPageRequest) obj;
		return new EqualsBuilder().append(getId(), other.getId())

		.append(getName(), other.getName())

		.append(getMemo(), other.getMemo())

		.isEquals();
	}
}
