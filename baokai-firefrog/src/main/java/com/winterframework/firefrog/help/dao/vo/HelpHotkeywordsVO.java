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

public class HelpHotkeywordsVO extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -34565121l;
	//alias
	public static final String TABLE_ALIAS = "HelpHotkeywords";
	public static final String ALIAS_KEYWORD = "热门关键字";
	public static final String ALIAS_NO = "序号";

	//date formats

	//columns START
	private String keyword;
	private Long no;

	//columns END

	public HelpHotkeywordsVO() {
	}

	public HelpHotkeywordsVO(Long id) {
		this.id = id;
	}

	public void setKeyword(String value) {
		this.keyword = value;
	}

	public String getKeyword() {
		return this.keyword;
	}

	public void setNo(Long value) {
		this.no = value;
	}

	public Long getNo() {
		return this.no;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).append("Id", getId()).append("Keyword", getKeyword()).append("No", getNo())
				.append("GmtCreated", getGmtCreated()).append("Creator", getCreator())
				.append("GmtModified", getGmtModified()).append("Modifier", getModifier()).toString();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(getId()).append(getKeyword()).append(getNo()).append(getGmtCreated())
				.append(getCreator()).append(getGmtModified()).append(getModifier()).toHashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof HelpHotkeywordsVO == false)
			return false;
		if (this == obj)
			return true;
		HelpHotkeywordsVO other = (HelpHotkeywordsVO) obj;
		return new EqualsBuilder().append(getId(), other.getId())

		.append(getKeyword(), other.getKeyword())

		.append(getNo(), other.getNo())

		.append(getGmtCreated(), other.getGmtCreated())

		.append(getCreator(), other.getCreator())

		.append(getGmtModified(), other.getGmtModified())

		.append(getModifier(), other.getModifier())

		.isEquals();
	}
}
