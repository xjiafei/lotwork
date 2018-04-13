 /**
 * Copyright (c) 2005-2012 winterframework.com
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.winterframework.firefrog.notice.dao.vo;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import com.winterframework.orm.dal.ibatis3.BaseEntity;

/**
 * @author cms group
 * @version 1.0
 * @since 1.0
 */


public class UserNoticeTaskVO extends BaseEntity {
	
	/** 
	* @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么) 
	*/ 
	private static final long serialVersionUID = 8775332366789L;
	//alias
	public static final String TABLE_ALIAS = "UserNoticeTask";
	public static final String ALIAS_MODULE = "模块 信息变动，资金变动";
	public static final String ALIAS_TASK = "任务";
	public static final String ALIAS_ACTIVATED = "1)启用 0)禁用 通知任务";
	public static final String ALIAS_INNER_MSG_ACTIVATED = "1)启用 0)禁用 站内信";
	public static final String ALIAS_INNER_MSG_USED = "1)可用 0)不可用 站内信";
	public static final String ALIAS_EMAIL_ACTIVATED = "1)启用 0)禁用 邮箱";
	public static final String ALIAS_EMAIL_USED = "1)可用 0)不可用 邮箱";
	public static final String ALIAS_NOTE_ACTIVATED = "1)启用 0)禁用 桌面通知";
	public static final String ALIAS_NOTE_USED = "1)可用 0)不可用 桌面通知";
	public static final String ALIAS_EMS_ACTIVATED = "1)启用 0)禁用 短信";
	public static final String ALIAS_EMS_USED = "1)可用 0)不可用 短信";
	public static final String USER_ID = "用户";
	
	//date formats
	
	//columns START
	private String module;
	private String task;
	private Long activated;
	private Long innerMsgActivated;
	private Long innerMsgUsed;
	private Long emailActivated;
	private Long emailUsed;
	private Long noteActivated;
	private Long noteUsed;
	private Long emsActivated;
	private Long emsUsed;
	private Long userId;
	//columns END

	public UserNoticeTaskVO(){
	}

	public UserNoticeTaskVO(
		Long id
	){
		this.id = id;
	}

	public void setModule(String value) {
		this.module = value;
	}
	
	public String getModule() {
		return this.module;
	}
	public void setTask(String value) {
		this.task = value;
	}
	
	public String getTask() {
		return this.task;
	}
	public void setActivated(Long value) {
		this.activated = value;
	}
	
	public Long getActivated() {
		return this.activated;
	}
	public void setInnerMsgActivated(Long value) {
		this.innerMsgActivated = value;
	}
	
	public Long getInnerMsgActivated() {
		return this.innerMsgActivated;
	}
	public void setInnerMsgUsed(Long value) {
		this.innerMsgUsed = value;
	}
	
	public Long getInnerMsgUsed() {
		return this.innerMsgUsed;
	}
	public void setEmailActivated(Long value) {
		this.emailActivated = value;
	}
	
	public Long getEmailActivated() {
		return this.emailActivated;
	}
	public void setEmailUsed(Long value) {
		this.emailUsed = value;
	}
	
	public Long getEmailUsed() {
		return this.emailUsed;
	}
	public void setNoteActivated(Long value) {
		this.noteActivated = value;
	}
	
	public Long getNoteActivated() {
		return this.noteActivated;
	}
	public void setNoteUsed(Long value) {
		this.noteUsed = value;
	}
	
	public Long getNoteUsed() {
		return this.noteUsed;
	}
	public void setEmsActivated(Long value) {
		this.emsActivated = value;
	}
	
	public Long getEmsActivated() {
		return this.emsActivated;
	}
	public void setEmsUsed(Long value) {
		this.emsUsed = value;
	}
	
	public Long getEmsUsed() {
		return this.emsUsed;
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
		.append("Module",getModule())		
		.append("Task",getTask())		
		.append("Activated",getActivated())		
		.append("InnerMsgActivated",getInnerMsgActivated())		
		.append("InnerMsgUsed",getInnerMsgUsed())		
		.append("EmailActivated",getEmailActivated())		
		.append("EmailUsed",getEmailUsed())		
		.append("NoteActivated",getNoteActivated())		
		.append("NoteUsed",getNoteUsed())		
		.append("EmsActivated",getEmsActivated())		
		.append("EmsUsed",getEmsUsed())
		.append("UserId",getUserId())
			.toString();
	}
    @Override
	public int hashCode() {
		return new HashCodeBuilder()
		.append(getId())
		.append(getModule())
		.append(getTask())
		.append(getActivated())
		.append(getInnerMsgActivated())
		.append(getInnerMsgUsed())
		.append(getEmailActivated())
		.append(getEmailUsed())
		.append(getNoteActivated())
		.append(getNoteUsed())
		.append(getEmsActivated())
		.append(getEmsUsed())
		.append(getUserId())
			.toHashCode();
	}
    @Override
	public boolean equals(Object obj) {
		if(obj instanceof UserNoticeTaskVO == false) return false;
		if(this == obj) return true;
		UserNoticeTaskVO other = (UserNoticeTaskVO)obj;
		return new EqualsBuilder()
		.append(getId(),other.getId())

		.append(getModule(),other.getModule())

		.append(getTask(),other.getTask())

		.append(getActivated(),other.getActivated())

		.append(getInnerMsgActivated(),other.getInnerMsgActivated())

		.append(getInnerMsgUsed(),other.getInnerMsgUsed())

		.append(getEmailActivated(),other.getEmailActivated())

		.append(getEmailUsed(),other.getEmailUsed())

		.append(getNoteActivated(),other.getNoteActivated())

		.append(getNoteUsed(),other.getNoteUsed())

		.append(getEmsActivated(),other.getEmsActivated())

		.append(getEmsUsed(),other.getEmsUsed())
		.append(getUserId(), other.getUserId())

			.isEquals();
	}
}

