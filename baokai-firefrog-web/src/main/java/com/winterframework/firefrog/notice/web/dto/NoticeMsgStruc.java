/**
* Copyright (c) 2005-2012 winterframework.com
* Licensed under the Apache License, Version 2.0 (the "License");
*/
package com.winterframework.firefrog.notice.web.dto;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author cms group
 * @version 1.0
 * @since 1.0
 */
public class NoticeMsgStruc implements Serializable {

	private static final long serialVersionUID = 1697982563823233574L;
	private Long id;
	private String operater;
	private String receives;
	@NotNull
	@Size(min = 1, max = 40)
	private String title;
	@NotNull
	private String content;
	private Date gmtCreated;
	private Date gmtSended;
	private Date gmtExpired;
	@NotNull
	@Min(1)
	@Max(2)
	private Long status;
	private Long isDeleted;
	private String deleter;
	@NotNull
	@Min(1)
	@Max(2)
	private Long sendType;
	@NotNull
	@Min(0)
	private Long effectHours;
	private String recGroups;
	private String messagePush;
	public String getMessagePush() {
		return messagePush;
	}

	public void setMessagePush(String messagePush) {
		this.messagePush = messagePush;
	}

	private Long sendSatus;
	private String gmtSendedStr;

	public NoticeMsgStruc() {
	}

	public NoticeMsgStruc(Long id) {
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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getGmtCreated() {
		return gmtCreated;
	}

	public void setGmtCreated(Date gmtCreated) {
		this.gmtCreated = gmtCreated;
	}

	public Long getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(Long isDeleted) {
		this.isDeleted = isDeleted;
	}

	public String getRecGroups() {
		return recGroups;
	}

	public void setRecGroups(String recGroups) {
		this.recGroups = recGroups;
	}

	public Long getSendSatus() {
		return sendSatus;
	}

	public void setSendSatus(Long sendSatus) {
		this.sendSatus = sendSatus;
	}

	public String getGmtSendedStr() {
		return gmtSendedStr;
	}

	public void setGmtSendedStr(String gmtSendedStr) {
		this.gmtSendedStr = gmtSendedStr;
	}
}
