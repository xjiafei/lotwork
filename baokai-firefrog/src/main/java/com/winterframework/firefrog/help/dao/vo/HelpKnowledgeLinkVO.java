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

public class HelpKnowledgeLinkVO extends BaseEntity {

	private static final long serialVersionUID = 4201593706644460901L;
	//alias
	public static final String TABLE_ALIAS = "HelpKnowledgeLink";
	public static final String ALIAS_K_ID = "知识目录ID";
	public static final String ALIAS_HELP_ID = "帮助ID";
	public static final String ALIAS_CONTENT = "目录对应的内容";

	//columns START
	private Long kid;
	private Long helpId;
	private String content;

	//columns END

	public HelpKnowledgeLinkVO() {
	}

	public HelpKnowledgeLinkVO(Long id) {
		this.id = id;
	}

	public void setKid(Long value) {
		this.kid = value;
	}

	public Long getKid() {
		return this.kid;
	}

	public void setHelpId(Long value) {
		this.helpId = value;
	}

	public Long getHelpId() {
		return this.helpId;
	}

	public void setContent(String value) {
		this.content = value;
	}

	public String getContent() {
		return this.content;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).append("Id", getId()).append("Kid", getKid()).append("HelpId", getHelpId())
				.append("Content", getContent()).toString();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(getId()).append(getKid()).append(getHelpId()).append(getContent())
				.toHashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof HelpKnowledgeLinkVO == false)
			return false;
		if (this == obj)
			return true;
		HelpKnowledgeLinkVO other = (HelpKnowledgeLinkVO) obj;
		return new EqualsBuilder().append(getId(), other.getId())

		.append(getKid(), other.getKid())

		.append(getHelpId(), other.getHelpId())

		.append(getContent(), other.getContent())

		.isEquals();
	}
}
