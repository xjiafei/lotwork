package com.winterframework.firefrog.user.dao.vo;

import java.util.Date;

import com.winterframework.orm.dal.ibatis3.BaseEntity;

public class UserSlotExchangeActivity extends BaseEntity {
	private static final long serialVersionUID = 1L;
	private Long id;
	private String name;
	private Long status;
	private Date gmtActivityStart;
	private Date gmtActivityEnd;
	private Date gmtCreate;
	private Date gmtUpdate;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getStatus() {
		return status;
	}

	public void setStatus(Long status) {
		this.status = status;
	}

	public Date getGmtActivityStart() {
		return gmtActivityStart;
	}

	public void setGmtActivityStart(Date gmtActivityStart) {
		this.gmtActivityStart = gmtActivityStart;
	}

	public Date getGmtActivityEnd() {
		return gmtActivityEnd;
	}

	public void setGmtActivityEnd(Date gmtActivityEnd) {
		this.gmtActivityEnd = gmtActivityEnd;
	}

	public Date getGmtCreate() {
		return gmtCreate;
	}

	public void setGmtCreate(Date gmtCreate) {
		this.gmtCreate = gmtCreate;
	}

	public Date getGmtUpdate() {
		return gmtUpdate;
	}

	public void setGmtUpdate(Date gmtUpdate) {
		this.gmtUpdate = gmtUpdate;
	}

}
