package com.winterframework.firefrog.user.web.dto;

import java.util.Date;

public class UserSlotExchangeByUserResponse {
	private String exchangeNumber;
	private Long userId;
	private Long exchangeAmount;
	private Long isBinding;
	private Long isAward;
	private Long sendStatus;
	private Long status;
	private String cellularPhone;
	private Date gmtCreate;
	private Date gmtUpdate;

	public String getExchangeNumber() {
		return exchangeNumber;
	}

	public void setExchangeNumber(String exchangeNumber) {
		this.exchangeNumber = exchangeNumber;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getExchangeAmount() {
		return exchangeAmount;
	}

	public void setExchangeAmount(Long exchangeAmount) {
		this.exchangeAmount = exchangeAmount;
	}

	public Long getIsBinding() {
		return isBinding;
	}

	public void setIsBinding(Long isBinding) {
		this.isBinding = isBinding;
	}

	public Long getIsAward() {
		return isAward;
	}

	public void setIsAward(Long isAward) {
		this.isAward = isAward;
	}

	public Long getSendStatus() {
		return sendStatus;
	}

	public void setSendStatus(Long sendStatus) {
		this.sendStatus = sendStatus;
	}

	public Long getStatus() {
		return status;
	}

	public void setStatus(Long status) {
		this.status = status;
	}

	public String getCellularPhone() {
		return cellularPhone;
	}

	public void setCellularPhone(String cellularPhone) {
		this.cellularPhone = cellularPhone;
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
