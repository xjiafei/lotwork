/**
* Copyright (c) 2005-2012 winterframework.com
* Licensed under the Apache License, Version 2.0 (the "License");
*/
package com.winterframework.firefrog.user.dao.vo;

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

public class UserMessageReply extends BaseEntity {

	private static final long serialVersionUID = -5468039198443379700L;
	//alias
	public static final String TABLE_ALIAS = "UserMessageReply";
	public static final String ALIAS_ROOT_ID = "主消息id";
	public static final String ALIAS_CONTENT = "内容";
	public static final String ALIAS_SENDER = "回复人";
	public static final String ALIAS_SENDER_DATE = "回复时间";
	public static final String ALIAS_PARENT_ID = "回复的那条消息";
	public static final String ALIAS_RECEIVE = "接收人";
	public static final String ALIAS_SENDER_ACCOUNT = "senderAccount";
	public static final String ALIAS_RECEIVE_ACCOUNT = "接受者账号";

	//date formats

	//columns START
	private Long rootId;
	private String content;
	private Long sender;
	private Date senderDate;
	private Long parentId;
	private Long receive;
	private String senderAccount;
	private String receiveAccount;

	//columns END

	public UserMessageReply() {
	}

	public UserMessageReply(Long id) {
		this.id = id;
	}

	public void setRootId(Long value) {
		this.rootId = value;
	}

	public Long getRootId() {
		return this.rootId;
	}

	public void setContent(String value) {
		this.content = value;
	}

	public String getContent() {
		return this.content;
	}

	public void setSender(Long value) {
		this.sender = value;
	}

	public Long getSender() {
		return this.sender;
	}

	public void setSenderDate(Date value) {
		this.senderDate = value;
	}

	public Date getSenderDate() {
		return this.senderDate;
	}

	public void setParentId(Long value) {
		this.parentId = value;
	}

	public Long getParentId() {
		return this.parentId;
	}

	public void setReceive(Long value) {
		this.receive = value;
	}

	public Long getReceive() {
		return this.receive;
	}

	public void setSenderAccount(String value) {
		this.senderAccount = value;
	}

	public String getSenderAccount() {
		return this.senderAccount;
	}

	public void setReceiveAccount(String value) {
		this.receiveAccount = value;
	}

	public String getReceiveAccount() {
		return this.receiveAccount;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).append("Id", getId()).append("RootId", getRootId())
				.append("Content", getContent()).append("Sender", getSender()).append("SenderDate", getSenderDate())
				.append("ParentId", getParentId()).append("Receive", getReceive())
				.append("SenderAccount", getSenderAccount()).append("ReceiveAccount", getReceiveAccount()).toString();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(getId()).append(getRootId()).append(getContent()).append(getSender())
				.append(getSenderDate()).append(getParentId()).append(getReceive()).append(getSenderAccount())
				.append(getReceiveAccount()).toHashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof UserMessageReply == false) {
			return false;
		}
		if (this == obj) {
			return true;
		}
		UserMessageReply other = (UserMessageReply) obj;
		return new EqualsBuilder().append(getId(), other.getId())

		.append(getRootId(), other.getRootId())

		.append(getContent(), other.getContent())

		.append(getSender(), other.getSender())

		.append(getSenderDate(), other.getSenderDate())

		.append(getParentId(), other.getParentId())

		.append(getReceive(), other.getReceive())

		.append(getSenderAccount(), other.getSenderAccount())

		.append(getReceiveAccount(), other.getReceiveAccount())

		.isEquals();
	}
}
