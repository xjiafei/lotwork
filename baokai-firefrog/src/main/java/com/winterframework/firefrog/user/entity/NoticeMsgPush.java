package com.winterframework.firefrog.user.entity;

import java.util.Date;

import com.winterframework.orm.dal.ibatis3.BaseEntity;

public class NoticeMsgPush extends BaseEntity {

	private static final long serialVersionUID = 1L;

	private Long id;

	private Long userId;

	private Long noticeMsgId;

	private Long isRead;

	private Date createDate;

	private Date updateDate;

	private Long targetId;

	private Long targetUserId;

	private Long targetNoticeMsgId;

	private Long targetIsRead;

	private Date targetCreateDateStart;

	private Date targetCreateDateEnd;

	private Date targetUpdateDateStart;

	private Date targetUpdateDateEnd;

	public void setId(Long id) {
		this.id = id;
	}

	public Long getId() {
		return this.id;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getUserId() {
		return this.userId;
	}

	public void setNoticeMsgId(Long noticeMsgId) {
		this.noticeMsgId = noticeMsgId;
	}

	public Long getNoticeMsgId() {
		return this.noticeMsgId;
	}

	public void setIsRead(Long isRead) {
		this.isRead = isRead;
	}

	public Long getIsRead() {
		return this.isRead;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getCreateDate() {
		return this.createDate;
	}

	public void setTargetId(Long targetId) {
		this.targetId = targetId;
	}

	public Long getTargetId() {
		return this.targetId;
	}

	public void setTargetUserId(Long targetUserId) {
		this.targetUserId = targetUserId;
	}

	public Long getTargetUserId() {
		return this.targetUserId;
	}

	public void setTargetNoticeMsgId(Long targetNoticeMsgId) {
		this.targetNoticeMsgId = targetNoticeMsgId;
	}

	public Long getTargetNoticeMsgId() {
		return this.targetNoticeMsgId;
	}

	public void setTargetIsRead(Long targetIsRead) {
		this.targetIsRead = targetIsRead;
	}

	public Long getTargetIsRead() {
		return this.targetIsRead;
	}

	public void setTargetCreateDateStart(Date targetCreateDateStart) {
		this.targetCreateDateStart = targetCreateDateStart;
	}

	public Date getTargetCreateDateStart() {
		return this.targetCreateDateStart;
	}

	public void setTargetCreateDateEnd(Date targetCreateDateEnd) {
		this.targetCreateDateEnd = targetCreateDateEnd;
	}

	public Date getTargetCreateDateEnd() {
		return this.targetCreateDateEnd;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public Date getTargetUpdateDateStart() {
		return targetUpdateDateStart;
	}

	public void setTargetUpdateDateStart(Date targetUpdateDateStart) {
		this.targetUpdateDateStart = targetUpdateDateStart;
	}

	public Date getTargetUpdateDateEnd() {
		return targetUpdateDateEnd;
	}

	public void setTargetUpdateDateEnd(Date targetUpdateDateEnd) {
		this.targetUpdateDateEnd = targetUpdateDateEnd;
	}

}
