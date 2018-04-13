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


public class AdResource extends BaseEntity {
	
	//alias
	public static final String TABLE_ALIAS = "AdResource";
	public static final String ALIAS_EXT_TYPE = "扩展类型(jpg,png,mp3...)";
	public static final String ALIAS_FILE_PATH = "文件路径";
	public static final String ALIAS_FILE_NAME = "文件名称";
	public static final String ALIAS_REMARK = "备注";
	public static final String ALIAS_CREATOR_ID = "创建人";
	public static final String ALIAS_CREATE_TIME = "创建时间";
	public static final String ALIAS_UPDATOR_ID = "更新人";
	public static final String ALIAS_UPDATE_TIME = "更新时间";
	
	//date formats
			public static final String FORMAT_CREATE_TIME = DATE_TIME_FORMAT;
			public static final String FORMAT_UPDATE_TIME = DATE_TIME_FORMAT;
	
	//columns START
	private String extType;
	private String filePath;
	private String fileName;
	private Long userId;
	private String remark;
	private String createTimeString;
	private String updateTimeString;
	private String createName;

	//columns END

	public AdResource(){
	}

	public AdResource(
		Long id
	){
		this.id = id;
	}

	public void setExtType(String value) {
		this.extType = value;
	}
	
	public String getExtType() {
		return this.extType;
	}
	public void setFilePath(String value) {
		this.filePath = value;
	}
	
	public String getFilePath() {
		return this.filePath;
	}
	public void setFileName(String value) {
		this.fileName = value;
	}
	
	public String getFileName() {
		return this.fileName;
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
	
    public String getCreateName() {
		return createName;
	}

	public void setCreateName(String createName) {
		this.createName = createName;
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
		.append("ExtType",getExtType())		
		.append("FilePath",getFilePath())		
		.append("FileName",getFileName())		
		.append("Remark",getRemark())			
			.toString();
	}
    @Override
	public int hashCode() {
		return new HashCodeBuilder()
		.append(getId())
		.append(getExtType())
		.append(getFilePath())
		.append(getFileName())
		.append(getRemark())

			.toHashCode();
	}
    @Override
	public boolean equals(Object obj) {
		if(obj instanceof AdResource == false) return false;
		if(this == obj) return true;
		AdResource other = (AdResource)obj;
		return new EqualsBuilder()
		.append(getId(),other.getId())

		.append(getExtType(),other.getExtType())

		.append(getFilePath(),other.getFilePath())

		.append(getFileName(),other.getFileName())

		.append(getRemark(),other.getRemark())

			.isEquals();
	}
}

