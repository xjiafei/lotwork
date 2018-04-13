package com.winterframework.firefrog.shortlived.gamemission.vo;

import java.io.Serializable;
import java.util.Date;

public class GameMissionVo implements Serializable {

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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
