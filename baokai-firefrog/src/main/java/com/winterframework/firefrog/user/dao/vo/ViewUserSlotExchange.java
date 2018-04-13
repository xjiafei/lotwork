package com.winterframework.firefrog.user.dao.vo;

import java.util.Date;

import com.winterframework.orm.dal.ibatis3.BaseEntity;

public class ViewUserSlotExchange extends BaseEntity {
	private static final long serialVersionUID = 1L;

	private String exchangeNumber;
	private Long userId;
	private Long exchangeAmount;
	private Long exchangePrize;
	private Long isBinding;
	private Long notBinding;
	private Long isAward;
	private Long sendStatus;
	private String cellularPhone;
	private Long activityId;
	private String activityName;
	private Date gmtActivityStart;
	private Date gmtActivityEnd;
	private Long activityStatus;
	private Date gmtBindingStart;
	private Date gmtBindingEnd;
	private Date gmtBindingDate;
	private Date gmtAwardDate;
	private Long type;

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

	public Long getExchangePrize() {
		return exchangePrize;
	}

	public void setExchangePrize(Long exchangePrize) {
		this.exchangePrize = exchangePrize;
	}

	public Long getIsBinding() {
		return isBinding;
	}

	public void setIsBinding(Long isBinding) {
		this.isBinding = isBinding;
	}

	public Long getNotBinding() {
		return notBinding;
	}

	public void setNotBinding(Long notBinding) {
		this.notBinding = notBinding;
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

	public String getCellularPhone() {
		return cellularPhone;
	}

	public void setCellularPhone(String cellularPhone) {
		this.cellularPhone = cellularPhone;
	}

	public Long getActivityId() {
		return activityId;
	}

	public void setActivityId(Long activityId) {
		this.activityId = activityId;
	}

	public String getActivityName() {
		return activityName;
	}

	public void setActivityName(String activityName) {
		this.activityName = activityName;
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

	public Long getActivityStatus() {
		return activityStatus;
	}

	public void setActivityStatus(Long activityStatus) {
		this.activityStatus = activityStatus;
	}

	public Date getGmtBindingStart() {
		return gmtBindingStart;
	}

	public void setGmtBindingStart(Date gmtBindingStart) {
		this.gmtBindingStart = gmtBindingStart;
	}

	public Date getGmtBindingEnd() {
		return gmtBindingEnd;
	}

	public void setGmtBindingEnd(Date gmtBindingEnd) {
		this.gmtBindingEnd = gmtBindingEnd;
	}

	public Date getGmtBindingDate() {
		return gmtBindingDate;
	}

	public void setGmtBindingDate(Date gmtBindingDate) {
		this.gmtBindingDate = gmtBindingDate;
	}

	public Date getGmtAwardDate() {
		return gmtAwardDate;
	}

	public void setGmtAwardDate(Date gmtAwardDate) {
		this.gmtAwardDate = gmtAwardDate;
	}

	public Long getType() {
		return type;
	}

	public void setType(Long type) {
		this.type = type;
	}

}
