/**
* Copyright (c) 2005-2012 winterframework.com
* Licensed under the Apache License, Version 2.0 (the "License");
*/
package com.winterframework.firefrog.common.config.dao.vo;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import com.winterframework.orm.dal.ibatis3.BaseEntity;

/**
 * @author cms group
 * @version 1.0
 * @since 1.0
 */

public class Config extends BaseEntity {

	private static final long serialVersionUID = -2503216236600409041L;
	//alias
	public static final String TABLE_ALIAS = "Config";
	public static final String ALIAS_MODULE = "user,fund,advertisment,game";
	public static final String ALIAS_KEY = "键";
	public static final String ALIAS_VALUE = " fund：{charge_ratio：“30”，bell_ratio：“1”，charge：“1”，bel：“1”}";
	public static final String ALIAS_TYPE = "类型 1:字符串 2：数字";
	public static final String ALIAS_MEMO = "备注";
	public static final String ALIAS_DEFAULT_VALUE = "默认值";

	//date formats

	//columns START
	private String module;
	private String key;
	private String value;
	private Long type;
	private String memo;
	private String defaultValue;
	private String function;

	//columns END

	public Config() {
	}

	public Config(Long id) {
		this.id = id;
	}

	public void setModule(String value) {
		this.module = value;
	}

	public String getModule() {
		return this.module;
	}

	public void setKey(String value) {
		this.key = value;
	}

	public String getKey() {
		return this.key;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getValue() {
		return this.value;
	}

	public void setType(Long value) {
		this.type = value;
	}

	public Long getType() {
		return this.type;
	}

	public void setMemo(String value) {
		this.memo = value;
	}

	public String getMemo() {
		return this.memo;
	}

	public void setDefaultValue(String value) {
		this.defaultValue = value;
	}

	public String getDefaultValue() {
		return this.defaultValue;
	}

	public String getFunction() {
		return function;
	}

	public void setFunction(String function) {
		this.function = function;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).append("Id", getId()).append("Module", getModule()).append("Key", getKey())
				.append("Value", getValue()).append("Type", getType()).append("GmtCreated", getGmtCreated())
				.append("Creator", getCreator()).append("Modifier", getModifier())
				.append("GmtModified", getGmtModified()).append("Memo", getMemo())
				.append("DefaultValue", getDefaultValue()).toString();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(getId()).append(getModule()).append(getKey()).append(getValue())
				.append(getType()).append(getGmtCreated()).append(getCreator()).append(getModifier())
				.append(getGmtModified()).append(getMemo()).append(getDefaultValue()).toHashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Config == false) {
			return false;
		}
		if (this == obj) {
			return true;
		}
		Config other = (Config) obj;
		return new EqualsBuilder().append(getId(), other.getId())

		.append(getModule(), other.getModule())

		.append(getKey(), other.getKey())

		.append(getValue(), other.getValue())

		.append(getType(), other.getType())

		.append(getGmtCreated(), other.getGmtCreated())

		.append(getCreator(), other.getCreator())

		.append(getModifier(), other.getModifier())

		.append(getGmtModified(), other.getGmtModified())

		.append(getMemo(), other.getMemo())

		.append(getDefaultValue(), other.getDefaultValue())

		.isEquals();
	}
}
