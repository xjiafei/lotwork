 /**
 * Copyright (c) 2005-2012 winterframework.com
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
 package com.winterframework.adbox.entity;


import java.util.Date;
import java.util.Locale;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.web.servlet.LocaleResolver;

import com.winterframework.orm.dal.ibatis3.BaseEntity;






/**
 * @author cms group
 * @version 1.0
 * @since 1.0
 */


public class AdDevice extends BaseEntity {
	//alias
	public static final String TABLE_ALIAS = "AdDevice";
	public static final String ALIAS_USER_ID = "用户id";
	public static final String ALIAS_CODE = "设备号";
	public static final String ALIAS_ADDRESS = "地址";
	public static final String ALIAS_ONFFLINE = "0不在线  1在线";
	public static final String ALIAS_BATTERY = "电量";
	public static final String ALIAS_LOCK_SCREEN = "0锁屏  1不锁屏";
	public static final String ALIAS_REMARK = "备注";
	public static final String ALIAS_CREATOR_ID = "创建人";
	public static final String ALIAS_CREATE_TIME = "创建时间";
	public static final String ALIAS_UPDATOR_ID = "更新人";
	public static final String ALIAS_UPDATE_TIME = "更新时间";
	
	//date formats
			public static final String FORMAT_CREATE_TIME = DATE_TIME_FORMAT;
			public static final String FORMAT_UPDATE_TIME = DATE_TIME_FORMAT;
	
	//columns START
	private Long userId;
	private String code;
	private String address;
	private Integer onffline;
	private Integer battery;
	private Integer lockScreen;
	private String remark;
	private Date heartbeatTime;
	private Integer status;
	//columns END
	
	private String updateTimeString;
	private String updateName;
	private String onfflineName;
	private String statusName;
	
	private String createTimeString;

	public AdDevice(){
	}

	public AdDevice(
		Long id
	){
		this.id = id;
	}

	public void setUserId(Long value) {
		this.userId = value;
	}
	
	public Long getUserId() {
		return this.userId;
	}
	public void setCode(String value) {
		this.code = value;
	}
	
	public String getCode() {
		return this.code;
	}
	public void setAddress(String value) {
		this.address = value;
	}
	
	public String getAddress() {
		return this.address;
	}
	public void setOnffline(Integer value) {
		this.onffline = value;
	}
	
	public Integer getOnffline() {
		return this.onffline;
	}
	public void setBattery(Integer value) {
		this.battery = value;
	}
	
	public Integer getBattery() {
		return this.battery;
	}
	public void setLockScreen(Integer value) {
		this.lockScreen = value;
	}
	
	public Integer getLockScreen() {
		return this.lockScreen;
	}
	public void setRemark(String value) {
		this.remark = value;
	}
	
	public String getRemark() {
		return this.remark;
	}
	
	public Date getHeartbeatTime() {
		return heartbeatTime;
	}

	public void setHeartbeatTime(Date heartbeatTime) {
		this.heartbeatTime = heartbeatTime;
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
    public String getOnfflineName() {
		return onfflineName;
	}

	public void setOnfflineName(String onfflineName) {
		this.onfflineName = onfflineName;
	}

	public String getStatusName() {
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	
	

	public String getUpdateName() {
		return updateName;
	}

	public void setUpdateName(String updateName) {
		this.updateName = updateName;
	}
	
	

	@Override
	public String toString() {
		return new ToStringBuilder(this)
		.append("Id",getId())		
		.append("UserId",getUserId())		
		.append("Code",getCode())		
		.append("Address",getAddress())		
		.append("Onffline",getOnffline())		
		.append("Battery",getBattery())		
		.append("LockScreen",getLockScreen())		
		.append("Remark",getRemark())		
			.toString();
	}
    @Override
	public int hashCode() {
		return new HashCodeBuilder()
		.append(getId())
		.append(getUserId())
		.append(getCode())
		.append(getAddress())
		.append(getOnffline())
		.append(getBattery())
		.append(getLockScreen())
		.append(getRemark())
		
			.toHashCode();
	}
    @Override
	public boolean equals(Object obj) {
		if(obj instanceof AdDevice == false) return false;
		if(this == obj) return true;
		AdDevice other = (AdDevice)obj;
		return new EqualsBuilder()
		.append(getId(),other.getId())

		.append(getUserId(),other.getUserId())

		.append(getCode(),other.getCode())

		.append(getAddress(),other.getAddress())

		.append(getOnffline(),other.getOnffline())

		.append(getBattery(),other.getBattery())

		.append(getLockScreen(),other.getLockScreen())

		.append(getRemark(),other.getRemark())
			.isEquals();
	}
}

