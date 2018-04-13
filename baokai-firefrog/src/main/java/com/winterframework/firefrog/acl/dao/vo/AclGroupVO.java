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

public class AclGroupVO extends BaseEntity {

	private static final long serialVersionUID = -22124465651L;
	//alias
	public static final String TABLE_ALIAS = "AclGroup";
	public static final String ALIAS_CREATORER = "chuangji";
	public static final String ALIAS_MODIFIERER = "modifierer";
	public static final String ALIAS_IN_USE = "0 禁用  1 正常";
	public static final String ALIAS_NAME = "组名";
	public static final String ALIAS_FULL_NAME = "/a/b/c  分别为上级组名";
	public static final String ALIAS_PID = "上级id";
	public static final String ALIAS_LVL = "级别";

	//date formats

	//columns START
	private String creatorer;
	private String modifierer;
	private Long inUse;
	private String name;
	private String fullName;
	private Long pid;
	private Long lvl;

	//columns END

	public AclGroupVO() {
	}

	public AclGroupVO(Long id) {
		this.id = id;
	}

	public void setCreatorer(String value) {
		this.creatorer = value;
	}

	public String getCreatorer() {
		return this.creatorer;
	}

	public void setModifierer(String value) {
		this.modifierer = value;
	}

	public String getModifierer() {
		return this.modifierer;
	}

	public void setInUse(Long value) {
		this.inUse = value;
	}

	public Long getInUse() {
		return this.inUse;
	}

	public void setName(String value) {
		this.name = value;
	}

	public String getName() {
		return this.name;
	}

	public void setFullName(String value) {
		this.fullName = value;
	}

	public String getFullName() {
		return this.fullName;
	}

	public void setPid(Long value) {
		this.pid = value;
	}

	public Long getPid() {
		return this.pid;
	}

	public void setLvl(Long value) {
		this.lvl = value;
	}

	public Long getLvl() {
		return this.lvl;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).append("Id", getId()).append("Creatorer", getCreatorer())
				.append("Modifierer", getModifierer()).append("GmtCreated", getGmtCreated())
				.append("GmtModified", getGmtModified()).append("InUse", getInUse()).append("Name", getName())
				.append("FullName", getFullName()).append("Pid", getPid()).append("Lvl", getLvl()).toString();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(getId()).append(getCreatorer()).append(getModifierer())
				.append(getGmtCreated()).append(getGmtModified()).append(getInUse()).append(getName())
				.append(getFullName()).append(getPid()).append(getLvl()).toHashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof AclGroupVO == false)
			return false;
		if (this == obj)
			return true;
		AclGroupVO other = (AclGroupVO) obj;
		return new EqualsBuilder().append(getId(), other.getId())

		.append(getCreatorer(), other.getCreatorer())

		.append(getModifierer(), other.getModifierer())

		.append(getGmtCreated(), other.getGmtCreated())

		.append(getGmtModified(), other.getGmtModified())

		.append(getInUse(), other.getInUse())

		.append(getName(), other.getName())

		.append(getFullName(), other.getFullName())

		.append(getPid(), other.getPid())

		.append(getLvl(), other.getLvl())

		.isEquals();
	}
}
