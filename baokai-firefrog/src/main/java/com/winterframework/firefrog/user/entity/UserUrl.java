/**
* Copyright (c) 2005-2012 winterframework.com
* Licensed under the Apache License, Version 2.0 (the "License");
*/
package com.winterframework.firefrog.user.entity;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import com.winterframework.firefrog.user.web.dto.UserAwardStruc;
import com.winterframework.modules.web.util.JsonMapper;
import com.winterframework.orm.dal.ibatis3.BaseEntity;

/**
 * @author cms group
 * @version 1.0
 * @since 1.0
 */

public class UserUrl extends BaseEntity {

	//alias
	public static final String TABLE_ALIAS = "UserUrl";
	public static final String ALIAS_URL = "url";
	public static final String ALIAS_MEMO = "备注";
	public static final String ALIAS_TYPE = "1 代理  0 普通用户";
	public static final String ALIAS_USERAWARD_LIST_STRUC = "奖金组列表基本结构";
	public static final String ALIAS_REGISTERS = "已注册用户 0为未注册 其他为已注册";

	//date formats

	//columns START
	private String url;
	private String memo;
	private Long type;
	private Long creator;
	
	public Long getCreator() {
		return creator;
	}

	public void setCreator(Long creator) {
		this.creator = creator;
	}

	private UserAwardStruc[] userawardListStruc;

	public UserAwardStruc[] getUserawardListStruc() {
		return userawardListStruc;
	}

	private Long registers;
	private Long size;
	private String uuid;
	private Long days;
	private String qq;
	//columns END

	/**
	 * @return the qq
	 */
	public String getQq() {
		return qq;
	}

	/**
	 * @param qq the qq to set
	 */
	public void setQq(String qq) {
		this.qq = qq;
	}

	public UserUrl() {
	}

	public Long getDays() {
		return days;
	}

	public void setDays(Long days) {
		this.days = days;
	}

	public UserUrl(Long id) {
		this.id = id;
	}

	public void setUrl(String value) {
		this.url = value;
	}

	public String getUrl() {
		return this.url;
	}

	public void setMemo(String value) {
		this.memo = value;
	}

	public String getMemo() {
		return this.memo;
	}

	public void setType(Long value) {
		this.type = value;
	}

	public Long getType() {
		return this.type;
	}

	public void setUserawardListStruc(UserAwardStruc[] value) {
		this.userawardListStruc = value;
	}

	public void setStrUserawardListStruc(String str) {
		userawardListStruc=JsonMapper.nonDefaultMapper().fromJson(str,UserAwardStruc[].class);	
	}

	public String getStrUserawardListStruc() {
		if (userawardListStruc != null)
			return JsonMapper.nonEmptyMapper().toJson(this.userawardListStruc);
		else
			return null;
	}

	public void setRegisters(Long value) {
		this.registers = value;
	}

	public Long getRegisters() {
		return this.registers;
	}

	public Long getSize() {
		return size;
	}

	public void setSize(Long size) {
		this.size = size;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).append("Id", getId()).append("Creator", getCreator())
				.append("GmtCreated", getGmtCreated()).append("Url", getUrl()).append("Memo", getMemo())
				.append("Type", getType()).append("UserawardListStruc", getUserawardListStruc())
				.append("Registers", getRegisters()).toString();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(getId()).append(getCreator()).append(getGmtCreated()).append(getUrl())
				.append(getMemo()).append(getType()).append(getUserawardListStruc()).append(getRegisters())
				.toHashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof UserUrl == false)
			return false;
		if (this == obj)
			return true;
		UserUrl other = (UserUrl) obj;
		return new EqualsBuilder().append(getId(), other.getId())

		.append(getCreator(), other.getCreator())

		.append(getGmtCreated(), other.getGmtCreated())

		.append(getUrl(), other.getUrl())

		.append(getMemo(), other.getMemo())

		.append(getType(), other.getType())

		.append(getUserawardListStruc(), other.getUserawardListStruc())

		.append(getRegisters(), other.getRegisters())

		.isEquals();
	}
}
