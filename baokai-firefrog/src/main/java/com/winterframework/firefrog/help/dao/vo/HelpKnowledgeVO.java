/**
* Copyright (c) 2005-2012 winterframework.com
* Licensed under the Apache License, Version 2.0 (the "License");
*/
package com.winterframework.firefrog.help.dao.vo;

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

public class HelpKnowledgeVO extends BaseEntity {

	private static final long serialVersionUID = -65676878781L;

	//alias
	public static final String TABLE_ALIAS = "HelpKnowledge";

	public static final String ALIAS_NAME = "彩种知识目录名称";

	public static final String ALIAS_NO = "序号";

	public static final String ALIAS_GMT_CREATE = "创建时间";

	//date formats

	//columns START
	private String name;

	private Long no;

	private Date gmtCreate;

	//columns END

	public HelpKnowledgeVO() {
	}

	public HelpKnowledgeVO(Long id) {
		this.id = id;
	}

	public void setName(String value) {
		this.name = value;
	}

	public String getName() {
		return this.name;
	}

	public void setNo(Long value) {
		this.no = value;
	}

	public Long getNo() {
		return this.no;
	}

	public void setGmtCreate(Date value) {
		this.gmtCreate = value;
	}

	public Date getGmtCreate() {
		return this.gmtCreate;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).append("Id", getId()).append("Name", getName()).append("No", getNo())
				.append("GmtCreate", getGmtCreate()).append("Creator", getCreator())
				.append("GmtModified", getGmtModified()).append("Modifier", getModifier()).toString();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(getId()).append(getName()).append(getNo()).append(getGmtCreate())
				.append(getCreator()).append(getGmtModified()).append(getModifier()).toHashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof HelpKnowledgeVO == false)
			return false;
		if (this == obj)
			return true;
		HelpKnowledgeVO other = (HelpKnowledgeVO) obj;
		return new EqualsBuilder().append(getId(), other.getId())

		.append(getName(), other.getName())

		.append(getNo(), other.getNo())

		.append(getGmtCreate(), other.getGmtCreate())

		.append(getCreator(), other.getCreator())

		.append(getGmtModified(), other.getGmtModified())

		.append(getModifier(), other.getModifier())

		.isEquals();
	}
}
