package com.winterframework.firefrog.phone.web.cotroller.dto;

import java.io.Serializable;
import java.util.Date;

public class NoticeMsgStruc implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7882865354661312600L;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getOperater() {
		return operater;
	}
	public void setOperater(String operater) {
		this.operater = operater;
	}
	public String getReceives() {
		return receives;
	}
	public void setReceives(String receives) {
		this.receives = receives;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Date getGmtCreated() {
		return gmtCreated;
	}
	public void setGmtCreated(Date gmtCreated) {
		this.gmtCreated = gmtCreated;
	}
	public Date getGmtSended() {
		return gmtSended;
	}
	public void setGmtSended(Date gmtSended) {
		this.gmtSended = gmtSended;
	}
	public Date getGmtExpired() {
		return gmtExpired;
	}
	public void setGmtExpired(Date gmtExpired) {
		this.gmtExpired = gmtExpired;
	}
	public Long getStatus() {
		return status;
	}
	public void setStatus(Long status) {
		this.status = status;
	}
	public Long getIsDeleted() {
		return isDeleted;
	}
	public void setIsDeleted(Long isDeleted) {
		this.isDeleted = isDeleted;
	}
	public String getDeleter() {
		return deleter;
	}
	public void setDeleter(String deleter) {
		this.deleter = deleter;
	}
	public Long getSendType() {
		return sendType;
	}
	public void setSendType(Long sendType) {
		this.sendType = sendType;
	}
	public Long getEffectHours() {
		return effectHours;
	}
	public void setEffectHours(Long effectHours) {
		this.effectHours = effectHours;
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
	private Long id;
	private String operater;
	private String receives;
	private String title;
	private String content;
	private Date gmtCreated;
	private Date gmtSended;
	private Date gmtExpired;
	private Long status;
	private Long isDeleted;
	private String deleter;
	private Long sendType;
	private Long effectHours;
	private String recGroups;
	private Long sendSatus;
	private String gmtSendedStr;
}
