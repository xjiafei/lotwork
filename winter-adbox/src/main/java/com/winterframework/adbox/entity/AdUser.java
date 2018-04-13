 /**
 * Copyright (c) 2005-2012 winterframework.com
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
 package com.winterframework.adbox.entity;

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


public class AdUser extends BaseEntity {
	
	//alias
	public static final String TABLE_ALIAS = "AdUser";
	public static final String ALIAS_NAME = "用户名";
	public static final String ALIAS_PASSWORD = "用户密码";
	public static final String ALIAS_TYPE = "用户类型 1管理员 2用户";
	public static final String ALIAS_STATUS = "0不可用  1可用";
	public static final String ALIAS_REMARK = "备注";
	public static final String ALIAS_CREATOR_ID = "创建人";
	public static final String ALIAS_CREATE_TIME = "创建时间";
	public static final String ALIAS_UPDATOR_ID = "更新人";
	public static final String ALIAS_UPDATE_TIME = "更新时间";
	
	//date formats
			public static final String FORMAT_CREATE_TIME = DATE_TIME_FORMAT;
			public static final String FORMAT_UPDATE_TIME = DATE_TIME_FORMAT;
	
	//columns START
	private String name;
	private String password;
	private Integer type;
	private Integer status;
	private String remark;
	private String creatorName;
	private String updateName;
	private String createTimeString;
	private String updateTimeString;
	private Long parentId;
	//columns END

	public AdUser(){
	}

	public AdUser(
		Long id
	){
		this.id = id;
	}

	public void setName(String value) {
		this.name = value;
	}
	
	public String getName() {
		return this.name;
	}
	public void setPassword(String value) {
		this.password = value;
	}
	
	public String getPassword() {
		return this.password;
	}
	public void setType(Integer value) {
		this.type = value;
	}
	
	public Integer getType() {
		return this.type;
	}
	public void setStatus(Integer value) {
		this.status = value;
	}
	
	public Integer getStatus() {
		return this.status;
	}
	public void setRemark(String value) {
		this.remark = value;
	}
	
	public String getRemark() {
		return this.remark;
	}

	public String getCreateTimeString() {
		return date2String(this.getGmtCreated(), FORMAT_CREATE_TIME);
	}
	public void setCreateTimeString(String value) {
		setGmtCreated(string2Date(value, FORMAT_CREATE_TIME,Date.class));
	}
	
	
	public String getUpdateTimeString() {
		return date2String(this.getGmtModified(), FORMAT_UPDATE_TIME);
	}
	public void setUpdateTimeString(String value) {
		setGmtModified(string2Date(value, FORMAT_UPDATE_TIME,Date.class));
	}
	
	
	
	
    public String getCreatorName() {
		return creatorName;
	}

	public void setCreatorName(String creatorName) {
		this.creatorName = creatorName;
	}

	public String getUpdateName() {
		return updateName;
	}

	public void setUpdateName(String updateName) {
		this.updateName = updateName;
	}

	
	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this)
		.append("Id",getId())		
		.append("Name",getName())		
		.append("Password",getPassword())		
		.append("Type",getType())		
		.append("Status",getStatus())		
		.append("Remark",getRemark())			
			.toString();
	}
    @Override
	public int hashCode() {
		return new HashCodeBuilder()
		.append(getId())
		.append(getName())
		.append(getPassword())
		.append(getType())
		.append(getStatus())
		.append(getRemark())

			.toHashCode();
	}
    @Override
	public boolean equals(Object obj) {
		if(obj instanceof AdUser == false) return false;
		if(this == obj) return true;
		AdUser other = (AdUser)obj;
		return new EqualsBuilder()
		.append(getId(),other.getId())

		.append(getName(),other.getName())

		.append(getPassword(),other.getPassword())

		.append(getType(),other.getType())

		.append(getStatus(),other.getStatus())

		.append(getRemark(),other.getRemark())



			.isEquals();
	}
}

