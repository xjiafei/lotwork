/**
* Copyright (c) 2005-2012 winterframework.com
* Licensed under the Apache License, Version 2.0 (the "License");
*/
package com.winterframework.firefrog.acl.dao.vo;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import com.winterframework.orm.dal.ibatis3.BaseEntity;

/**
 * @author cms group
 * @version 1.0
 * @since 1.0
 */

public class AclVO extends BaseEntity {
	private static final long serialVersionUID = -345632221L;
	//alias
	public static final String TABLE_ALIAS = "权限控制点，包括url，url分组，以及页面功能";
	public static final String ALIAS_PID = "上级id";
	public static final String ALIAS_NAME = "名字";
	public static final String ALIAS_TYPE = "1)url 2)label 3)operation";
	public static final String ALIAS_ORDERS = "次序";
	public static final String ALIAS_LABEL = "标签";

	//date formats

	//columns START
	private Long pid;
	private String name;
	private Long type;
	private Long orders;
	private String label;

	//columns END

	public AclVO() {
	}

	public AclVO(Long id) {
		this.id = id;
	}

	public void setPid(Long value) {
		this.pid = value;
	}

	public Long getPid() {
		return this.pid;
	}

	public void setName(String value) {
		this.name = value;
	}

	public String getName() {
		return this.name;
	}

	public void setType(Long value) {
		this.type = value;
	}

	public Long getType() {
		return this.type;
	}

	public void setOrders(Long value) {
		this.orders = value;
	}

	public Long getOrders() {
		return this.orders;
	}

	public void setLabel(String value) {
		this.label = value;
	}

	public String getLabel() {
		return this.label;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).append("Id", getId()).append("Pid", getPid()).append("Name", getName())
				.append("Type", getType()).append("Orders", getOrders()).append("Label", getLabel()).toString();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(getId()).append(getPid()).append(getName()).append(getType())
				.append(getOrders()).append(getLabel()).toHashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof AclVO == false)
			return false;
		if (this == obj)
			return true;
		AclVO other = (AclVO) obj;
		return new EqualsBuilder().append(getId(), other.getId())

		.append(getPid(), other.getPid())

		.append(getName(), other.getName())

		.append(getType(), other.getType())

		.append(getOrders(), other.getOrders())

		.append(getLabel(), other.getLabel())

		.isEquals();
	}
}
