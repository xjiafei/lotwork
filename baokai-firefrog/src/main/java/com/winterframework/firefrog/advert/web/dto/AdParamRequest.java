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

public class AdParamRequest extends BaseEntity {

	//alias
	public static final String TABLE_ALIAS = "AdParam";
	public static final String ALIAS_PAGE_ID = "pageId";
	public static final String ALIAS_NAME = "name";
	public static final String ALIAS_POSITION = "位置描述";
	public static final String ALIAS_WIDTH = "宽度 -1 表示无限制";
	public static final String ALIAS_HEIGHT = "长度 -1表示无限制";

	//date formats

	//columns START
	private Long pageId;
	private String name;
	private String position;
	private Long width;
	private Long height;

	//columns END

	public AdParamRequest() {
	}

	public AdParamRequest(Long id) {
		this.id = id;
	}

	public void setPageId(Long value) {
		this.pageId = value;
	}

	public Long getPageId() {
		return this.pageId;
	}

	public void setName(String value) {
		this.name = value;
	}

	public String getName() {
		return this.name;
	}

	public void setPosition(String value) {
		this.position = value;
	}

	public String getPosition() {
		return this.position;
	}

	public void setWidth(Long value) {
		this.width = value;
	}

	public Long getWidth() {
		return this.width;
	}

	public void setHeight(Long value) {
		this.height = value;
	}

	public Long getHeight() {
		return this.height;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).append("Id", getId()).append("PageId", getPageId()).append("Name", getName())
				.append("Position", getPosition()).append("Width", getWidth()).append("Height", getHeight()).toString();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(getId()).append(getPageId()).append(getName()).append(getPosition())
				.append(getWidth()).append(getHeight()).toHashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof AdParamRequest == false)
			return false;
		if (this == obj)
			return true;
		AdParamRequest other = (AdParamRequest) obj;
		return new EqualsBuilder().append(getId(), other.getId())

		.append(getPageId(), other.getPageId())

		.append(getName(), other.getName())

		.append(getPosition(), other.getPosition())

		.append(getWidth(), other.getWidth())

		.append(getHeight(), other.getHeight())

		.isEquals();
	}
}
