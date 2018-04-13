package com.winterframework.firefrog.shortlived.gamemission.dao.entity;

import com.winterframework.orm.dal.ibatis3.BaseEntity;
import java.util.Date;

public class GameMission extends BaseEntity {

	private static final long serialVersionUID = 1L;

	private Long id;

	private String code;

	private Long itemSeq;

	private String name;

	private Date startTime;

	private Date endTime;

	private String params;

	private Long isActive;

	private String createDate;

	private Long targetId;

	private String targetCode;

	private Long targetItemSeq;

	private Date targetStartTimeStart;

	private Date targetStartTimeEnd;

	private Date targetEndTimeStart;

	private Date targetEndTimeEnd;

	private String targetParams;

	private Long targetIsActive;

	private String targetCreateDate;

	private String targetName;

	public void setId(Long id) {
		this.id = id;
	}

	public Long getId() {
		return this.id;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getCode() {
		return this.code;
	}

	public void setItemSeq(Long itemSeq) {
		this.itemSeq = itemSeq;
	}

	public Long getItemSeq() {
		return this.itemSeq;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getStartTime() {
		return this.startTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public Date getEndTime() {
		return this.endTime;
	}

	public void setParams(String params) {
		this.params = params;
	}

	public String getParams() {
		return this.params;
	}

	public void setIsActive(Long isActive) {
		this.isActive = isActive;
	}

	public Long getIsActive() {
		return this.isActive;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public String getCreateDate() {
		return this.createDate;
	}

	public void setTargetId(Long targetId) {
		this.targetId = targetId;
	}

	public Long getTargetId() {
		return this.targetId;
	}

	public void setTargetCode(String targetCode) {
		this.targetCode = targetCode;
	}

	public String getTargetCode() {
		return this.targetCode;
	}

	public void setTargetItemSeq(Long targetItemSeq) {
		this.targetItemSeq = targetItemSeq;
	}

	public Long getTargetItemSeq() {
		return this.targetItemSeq;
	}

	public void setTargetStartTimeStart(Date targetStartTimeStart) {
		this.targetStartTimeStart = targetStartTimeStart;
	}

	public Date getTargetStartTimeStart() {
		return this.targetStartTimeStart;
	}

	public void setTargetStartTimeEnd(Date targetStartTimeEnd) {
		this.targetStartTimeEnd = targetStartTimeEnd;
	}

	public Date getTargetStartTimeEnd() {
		return this.targetStartTimeEnd;
	}

	public void setTargetEndTimeStart(Date targetEndTimeStart) {
		this.targetEndTimeStart = targetEndTimeStart;
	}

	public Date getTargetEndTimeStart() {
		return this.targetEndTimeStart;
	}

	public void setTargetEndTimeEnd(Date targetEndTimeEnd) {
		this.targetEndTimeEnd = targetEndTimeEnd;
	}

	public Date getTargetEndTimeEnd() {
		return this.targetEndTimeEnd;
	}

	public void setTargetParams(String targetParams) {
		this.targetParams = targetParams;
	}

	public String getTargetParams() {
		return this.targetParams;
	}

	public void setTargetIsActive(Long targetIsActive) {
		this.targetIsActive = targetIsActive;
	}

	public Long getTargetIsActive() {
		return this.targetIsActive;
	}

	public void setTargetCreateDate(String targetCreateDate) {
		this.targetCreateDate = targetCreateDate;
	}

	public String getTargetCreateDate() {
		return this.targetCreateDate;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTargetName() {
		return targetName;
	}

	public void setTargetName(String targetName) {
		this.targetName = targetName;
	}

}
