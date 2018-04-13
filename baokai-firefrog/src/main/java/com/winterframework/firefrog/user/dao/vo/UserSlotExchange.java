package com.winterframework.firefrog.user.dao.vo;

import java.util.Date;

import com.winterframework.orm.dal.ibatis3.BaseEntity;

public class UserSlotExchange extends BaseEntity {
	private static final long serialVersionUID = 1L;

	public static Long IS_BINDING_FAIL = 0L;
	public static Long IS_BINDING_ONGOING = 1L;
	public static Long IS_BINDING_SUCCESS = 2L;

	public static Long IS_AWARD_FAIL = 0L;
	public static Long IS_AWARD_ONGOING = 1L;
	public static Long IS_AWARD_SUCCESS = 2L;

	public static Long STATUS_FAIL = 0L;
	public static Long STATUS_SUCCESS = 1L;

	private String exchangeNumber;
	private Long userId;
	private Long exchangeAmount;
	private Long exchangePrize;
	private Long isBinding;
	private Long isAward;
	private Long type;
	private Long sendStatus;
	private String cellularPhone;
	private Date gmtCreate;
	private Date gmtUpdate;
	private Date gmtBindingDate;
	private Date gmtAwardDate;

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

	public Long getIsAward() {
		return isAward;
	}

	public void setIsAward(Long isAward) {
		this.isAward = isAward;
	}

	public Long getType() {
		return type;
	}

	public void setType(Long type) {
		this.type = type;
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

}
