 /**
 * Copyright (c) 2005-2012 winterframework.com
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
 package com.winterframework.firefrog.help.dao.vo;

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


public class HelpFeedbackVO extends BaseEntity {
	
	private static final long serialVersionUID = 7599944851649935776L;
	//alias
	public static final String TABLE_ALIAS = "HelpFeedback";
	public static final String ALIAS_U_ID = "用户ID";
	public static final String ALIAS_HELP_ID = "帮助ID";
	public static final String ALIAS_REASON_ID = "原因ID 1)太简单  2）字太多 3）太复杂 4）其他";
	public static final String ALIAS_REASON_CONTENT = "原因内容";
	public static final String ALIAS_IS_SOLVED = "是否解决 1:已解决 0：未解决";
	public static final String ALIAS_GMT_CREATE = "创建时间";
	
	//date formats
	public static final String FORMAT_GMT_CREATE = DATE_TIME_FORMAT;
	
	//columns START
	private Long uid;
	private Long helpId;
	private Long reasonId;
	private String reasonContent;
	private Long isSolved;
	private Date gmtCreate;
	//columns END

	public HelpFeedbackVO(){
	}

	public HelpFeedbackVO(
		Long id
	){
		this.id = id;
	}

	public void setUid(Long value) {
		this.uid = value;
	}
	
	public Long getUid() {
		return this.uid;
	}
	public void setHelpId(Long value) {
		this.helpId = value;
	}
	
	public Long getHelpId() {
		return this.helpId;
	}
	public void setReasonId(Long value) {
		this.reasonId = value;
	}
	
	public Long getReasonId() {
		return this.reasonId;
	}
	public void setReasonContent(String value) {
		this.reasonContent = value;
	}
	
	public String getReasonContent() {
		return this.reasonContent;
	}
	public void setIsSolved(Long value) {
		this.isSolved = value;
	}
	
	public Long getIsSolved() {
		return this.isSolved;
	}
	public String getGmtCreateString() {
		return date2String(getGmtCreate(), FORMAT_GMT_CREATE);
	}
	public void setGmtCreateString(String value) {
		setGmtCreate(string2Date(value, FORMAT_GMT_CREATE,Date.class));
	}
	
	public void setGmtCreate(Date value) {
		this.gmtCreate = value;
	}
	
	public Date getGmtCreate() {
		return this.gmtCreate;
	}
    @Override
	public String toString() {
		return new ToStringBuilder(this)
		.append("Id",getId())		
		.append("Uid",getUid())		
		.append("HelpId",getHelpId())		
		.append("ReasonId",getReasonId())		
		.append("ReasonContent",getReasonContent())		
		.append("IsSolved",getIsSolved())		
		.append("GmtCreate",getGmtCreate())		
			.toString();
	}
    @Override
	public int hashCode() {
		return new HashCodeBuilder()
		.append(getId())
		.append(getUid())
		.append(getHelpId())
		.append(getReasonId())
		.append(getReasonContent())
		.append(getIsSolved())
		.append(getGmtCreate())
			.toHashCode();
	}
    @Override
	public boolean equals(Object obj) {
		if(obj instanceof HelpFeedbackVO == false) return false;
		if(this == obj) return true;
		HelpFeedbackVO other = (HelpFeedbackVO)obj;
		return new EqualsBuilder()
		.append(getId(),other.getId())

		.append(getUid(),other.getUid())

		.append(getHelpId(),other.getHelpId())

		.append(getReasonId(),other.getReasonId())

		.append(getReasonContent(),other.getReasonContent())

		.append(getIsSolved(),other.getIsSolved())

		.append(getGmtCreate(),other.getGmtCreate())

			.isEquals();
	}
}

