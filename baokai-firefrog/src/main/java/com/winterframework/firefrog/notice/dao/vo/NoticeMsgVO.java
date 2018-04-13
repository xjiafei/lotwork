/**
* Copyright (c) 2005-2012 winterframework.com
* Licensed under the Apache License, Version 2.0 (the "License");
*/
package com.winterframework.firefrog.notice.dao.vo;

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
public class NoticeMsgVO extends BaseEntity {

	private static final long serialVersionUID = -3839295959946372200L;
	//alias
	public static final String TABLE_ALIAS = "NoticeMsg";
	public static final String ALIAS_OPERATER = "操作人账号";
	public static final String ALIAS_RECEIVES = "接收人";
	public static final String ALIAS_TITLE = "标题";
	public static final String ALIAS_CONTENT = "内容";
	public static final String ALIAS_GMT_SENDED = "发送时间";
	public static final String ALIAS_GMT_EXPIRED = "过期时间";
	public static final String ALIAS_STATUS = "1)新建 2)已发送";
	public static final String ALIAS_DELETER = "删除人账号";
	public static final String ALIAS_SEND_TYPE = "1)按用户组  2）按用户名";
	public static final String ALIAS_EFFECT_HOURS = "有效时长";

	//date formats

	//columns START
	private String operater;
	private String receives;
	private String title;
	private String content;
	private Date gmtSended;
	private Date gmtExpired;
	private Long status;
	private String deleter;
	private Long sendType;
	private Long effectHours;
	private String receiveGroups;
	private String messagePush;

	//columns END

	public String getMessagePush() {
		return messagePush;
	}

	public void setMessagePush(String messagePush) {
		this.messagePush = messagePush;
	}

	public NoticeMsgVO() {
	}

	public NoticeMsgVO(Long id) {
		this.id = id;
	}

	public void setOperater(String value) {
		this.operater = value;
	}

	public String getOperater() {
		return this.operater;
	}

	public void setReceives(String value) {
		this.receives = value;
	}

	public String getReceives() {
		return this.receives;
	}

	public void setTitle(String value) {
		this.title = value;
	}

	public String getTitle() {
		return this.title;
	}

	public void setContent(String value) {
		this.content = value;
	}

	public String getContent() {
		return this.content;
	}

	public void setGmtSended(Date value) {
		this.gmtSended = value;
	}

	public Date getGmtSended() {
		return this.gmtSended;
	}

	public void setGmtExpired(Date value) {
		this.gmtExpired = value;
	}

	public Date getGmtExpired() {
		return this.gmtExpired;
	}

	public void setStatus(Long value) {
		this.status = value;
	}

	public Long getStatus() {
		return this.status;
	}

	public void setDeleter(String value) {
		this.deleter = value;
	}

	public String getDeleter() {
		return this.deleter;
	}

	public void setSendType(Long value) {
		this.sendType = value;
	}

	public Long getSendType() {
		return this.sendType;
	}

	public void setEffectHours(Long value) {
		this.effectHours = value;
	}

	public Long getEffectHours() {
		return this.effectHours;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).append("Id", getId()).append("Operater", getOperater())
				.append("Receives", getReceives()).append("Title", getTitle()).append("Content", getContent())
				.append("GmtCreated", getGmtCreated()).append("GmtSended", getGmtSended())
				.append("GmtExpired", getGmtExpired()).append("Status", getStatus())
				.append("IsDeleted", getIsDeleted()).append("Deleter", getDeleter()).append("SendType", getSendType())
				.append("EffectHours", getEffectHours()).toString();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(getId()).append(getOperater()).append(getReceives()).append(getTitle())
				.append(getContent()).append(getGmtCreated()).append(getGmtSended()).append(getGmtExpired())
				.append(getStatus()).append(getIsDeleted()).append(getDeleter()).append(getSendType())
				.append(getEffectHours()).toHashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof NoticeMsgVO == false)
			return false;
		if (this == obj)
			return true;
		NoticeMsgVO other = (NoticeMsgVO) obj;
		return new EqualsBuilder().append(getId(), other.getId())

		.append(getOperater(), other.getOperater())

		.append(getReceives(), other.getReceives())

		.append(getTitle(), other.getTitle())

		.append(getContent(), other.getContent())

		.append(getGmtCreated(), other.getGmtCreated())

		.append(getGmtSended(), other.getGmtSended())

		.append(getGmtExpired(), other.getGmtExpired())

		.append(getStatus(), other.getStatus())

		.append(getIsDeleted(), other.getIsDeleted())

		.append(getDeleter(), other.getDeleter())

		.append(getSendType(), other.getSendType())

		.append(getEffectHours(), other.getEffectHours())

		.isEquals();
	}

	public String getReceiveGroups() {
		return receiveGroups;
	}

	public void setReceiveGroups(String receiveGroups) {
		this.receiveGroups = receiveGroups;
	}
}
