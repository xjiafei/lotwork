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


public class AdDeviceResource extends BaseEntity {
	
	//alias
	public static final String TABLE_ALIAS = "AdDeviceResource";
	public static final String ALIAS_RESOURCE_ID = "资源id";
	public static final String ALIAS_DEVICE_ID = "设备id";
	public static final String ALIAS_STATUS = "设备更新状态  0无  1有";
	public static final String ALIAS_REMARK = "备注";
	public static final String ALIAS_CREATOR_ID = "创建人";
	public static final String ALIAS_CREATE_TIME = "创建时间";
	public static final String ALIAS_UPDATOR_ID = "更新人";
	public static final String ALIAS_UPDATE_TIME = "更新时间";
	
	//date formats
			public static final String FORMAT_CREATE_TIME = DATE_TIME_FORMAT;
			public static final String FORMAT_UPDATE_TIME = DATE_TIME_FORMAT;
	
	//columns START
	private Long resourceId;
	private Long deviceId;
	private Integer status;
	private String remark;
	
	//columns END

	public AdDeviceResource(){
	}

	public AdDeviceResource(
		Long id
	){
		this.id = id;
	}

	
	public Long getResourceId() {
		return resourceId;
	}

	public void setResourceId(Long resourceId) {
		this.resourceId = resourceId;
	}

	public Long getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(Long deviceId) {
		this.deviceId = deviceId;
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
	
	
    @Override
	public String toString() {
		return new ToStringBuilder(this)
		.append("Id",getId())		
		.append("ResourceId",getResourceId())		
		.append("DeviceId",getDeviceId())		
		.append("Status",getStatus())		
		.append("Remark",getRemark())		
		
			.toString();
	}
    @Override
	public int hashCode() {
		return new HashCodeBuilder()
		.append(getId())
		.append(getResourceId())
		.append(getDeviceId())
		.append(getStatus())
		.append(getRemark())
		
			.toHashCode();
	}
    @Override
	public boolean equals(Object obj) {
		if(obj instanceof AdDeviceResource == false) return false;
		if(this == obj) return true;
		AdDeviceResource other = (AdDeviceResource)obj;
		return new EqualsBuilder()
		.append(getId(),other.getId())

		.append(getResourceId(),other.getResourceId())

		.append(getDeviceId(),other.getDeviceId())

		.append(getStatus(),other.getStatus())

		.append(getRemark(),other.getRemark())

		

			.isEquals();
	}
}

