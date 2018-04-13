/**
* Copyright (c) 2005-2012 winterframework.com
* Licensed under the Apache License, Version 2.0 (the "License");
*/
package com.winterframework.firefrog.help.dao.vo;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import com.winterframework.orm.dal.ibatis3.BaseEntity;

/**
 * @author cms group
 * @version 1.0
 * @since 1.0
 */

public class HelpCategoryVO extends BaseEntity {

	private static final long serialVersionUID = -7654587798781L;
	//alias
	public static final String TABLE_ALIAS = "HelpCategory";
	public static final String ALIAS_C_NAME = "类目名称";
	public static final String ALIAS_C_LEVEL = "层级";
	public static final String ALIAS_C_NO = "序号";
	public static final String ALIAS_P_ID = "父id";

	//date formats

	//columns START
	private String cname;
	private Long clevel;
	private Long cno;
	private Long pid;

	//columns END

	public HelpCategoryVO() {
	}

	public HelpCategoryVO(Long id) {
		this.id = id;
	}

	public void setCname(String value) {
		this.cname = value;
	}

	public String getCname() {
		return this.cname;
	}

	public void setClevel(Long value) {
		this.clevel = value;
	}

	public Long getClevel() {
		return this.clevel;
	}

	public void setCno(Long value) {
		this.cno = value;
	}

	public Long getCno() {
		return this.cno;
	}

	public void setPid(Long value) {
		this.pid = value;
	}

	public Long getPid() {
		return this.pid;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).append("Id", getId()).append("Cname", getCname())
				.append("Clevel", getClevel()).append("Cno", getCno()).append("GmtCreated", getGmtCreated())
				.append("Creator", getCreator()).append("Pid", getPid()).toString();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(getId()).append(getCname()).append(getClevel()).append(getCno())
				.append(getGmtCreated()).append(getCreator()).append(getPid()).toHashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof HelpCategoryVO == false)
			return false;
		if (this == obj)
			return true;
		HelpCategoryVO other = (HelpCategoryVO) obj;
		return new EqualsBuilder().append(getId(), other.getId())

		.append(getCname(), other.getCname())

		.append(getClevel(), other.getClevel())

		.append(getCno(), other.getCno())

		.append(getGmtCreated(), other.getGmtCreated())

		.append(getCreator(), other.getCreator())

		.append(getPid(), other.getPid())

		.isEquals();
	}
}
