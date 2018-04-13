package com.winterframework.firefrog.user.dao.vo;

import java.util.Date;

import com.winterframework.orm.dal.ibatis3.BaseEntity;

public class UserSlotExchangeLog extends BaseEntity {
	private static final long serialVersionUID = 1L;

	public static Long STATUS_FAIL = 0L;
	public static Long STATUS_SUCCESS = 1L;

	private Long id;
	private String cellularPhone;
	private String exchangeNumber;
	private Long activityId;
	private Long status;
	private String note;
	private Date gmtCreate;

	public static Long getSTATUS_FAIL() {
		return STATUS_FAIL;
	}

	public static void setSTATUS_FAIL(Long sTATUS_FAIL) {
		STATUS_FAIL = sTATUS_FAIL;
	}

	public static Long getSTATUS_SUCCESS() {
		return STATUS_SUCCESS;
	}

	public static void setSTATUS_SUCCESS(Long sTATUS_SUCCESS) {
		STATUS_SUCCESS = sTATUS_SUCCESS;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCellularPhone() {
		return cellularPhone;
	}

	public void setCellularPhone(String cellularPhone) {
		this.cellularPhone = cellularPhone;
	}

	public String getExchangeNumber() {
		return exchangeNumber;
	}

	public void setExchangeNumber(String exchangeNumber) {
		this.exchangeNumber = exchangeNumber;
	}

	public Long getActivityId() {
		return activityId;
	}

	public void setActivityId(Long activityId) {
		this.activityId = activityId;
	}

	public Long getStatus() {
		return status;
	}

	public void setStatus(Long status) {
		this.status = status;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public Date getGmtCreate() {
		return gmtCreate;
	}

	public void setGmtCreate(Date gmtCreate) {
		this.gmtCreate = gmtCreate;
	}

}
