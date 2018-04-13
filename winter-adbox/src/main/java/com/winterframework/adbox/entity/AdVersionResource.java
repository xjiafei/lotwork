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


public class AdVersionResource extends BaseEntity {
	
	//alias
	public static final String TABLE_ALIAS = "AdVersionResource";
	public static final String ALIAS_VERSION = "版本";
	public static final String ALIAS_RESOURCE_ID = "资源id";
	public static final String ALIAS_REMARK = "备注";
	public static final String ALIAS_CREATOR_ID = "创建人";
	public static final String ALIAS_CREATE_TIME = "创建时间";
	public static final String ALIAS_UPDATOR_ID = "更新人";
	public static final String ALIAS_UPDATE_TIME = "更新时间";
	
	//date formats
			public static final String FORMAT_CREATE_TIME = DATE_TIME_FORMAT;
			public static final String FORMAT_UPDATE_TIME = DATE_TIME_FORMAT;
	
	//columns START
	private Long version;
	private String resourceId;
	private Long userId;
	private String remark;
	private Integer status;
	//columns END

	public AdVersionResource(){
	}

	public AdVersionResource(
		Long id
	){
		this.id = id;
	}

	public void setVersion(Long value) {
		this.version = value;
	}
	
	public Long getVersion() {
		return this.version;
	}
	public void setResourceId(String value) {
		this.resourceId = value;
	}
	
	public String getResourceId() {
		return this.resourceId;
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
	
    public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	
	

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this)
		.append("Id",getId())		
		.append("Version",getVersion())		
		.append("ResourceId",getResourceId())		
		.append("Remark",getRemark())			
			.toString();
	}
    @Override
	public int hashCode() {
		return new HashCodeBuilder()
		.append(getId())
		.append(getVersion())
		.append(getResourceId())
		.append(getRemark())
			.toHashCode();
	}
    @Override
	public boolean equals(Object obj) {
		if(obj instanceof AdVersionResource == false) return false;
		if(this == obj) return true;
		AdVersionResource other = (AdVersionResource)obj;
		return new EqualsBuilder()
		.append(getId(),other.getId())

		.append(getVersion(),other.getVersion())

		.append(getResourceId(),other.getResourceId())

		.append(getRemark(),other.getRemark())

			.isEquals();
	}
}

