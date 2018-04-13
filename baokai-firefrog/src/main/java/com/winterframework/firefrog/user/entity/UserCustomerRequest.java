package com.winterframework.firefrog.user.entity;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.winterframework.modules.web.jsonresult.FirefrogDateDeSerializer;

public class UserCustomerRequest implements Serializable {
	private static final long serialVersionUID = 1L;
	private String ptAccount;//PT帳號
	private String ptPassword;//PT密碼
	private String phoneNum;//PT手機號碼
	private String externaltransactionid;
	private Long frozen;//凍結狀態
	private Long userId;//使用者ID
	
	@JsonDeserialize(using = FirefrogDateDeSerializer.class)
	private Date startDate;//開始日期or註冊時間起
	
	@JsonDeserialize(using = FirefrogDateDeSerializer.class)
	private Date endDate;//結束日期or註冊時間迄
	
	private Long ptBalMin;//PT餘額最小值
	private Long ptBalMax;//PT餘額最大值
	private String orderStr;//排序
	private Long accountType;//帳號類型
	private Long customerType;//PT代理類型

	private Long customerStatus;//PT代理狀態
	private Long effectStatus;//生效狀態
	private Long registerIP; //註冊IP
	
	public Long getRegisterIP() {
		return registerIP;
	}

	public void setRegisterIP(Long registerIP) {
		this.registerIP = registerIP;
	}

	private String gameCode;

	
	
	public String getPtAccount() {
		return ptAccount;
	}

	public void setPtAccount(String ptAccount) {
		this.ptAccount = ptAccount;
	}

	public String getPtPassword() {
		return ptPassword;
	}

	public void setPtPassword(String ptPassword) {
		this.ptPassword = ptPassword;
	}


	public String getPhoneNum() {
		return phoneNum;
	}

	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}

	public String getExternaltransactionid() {
		return externaltransactionid;
	}

	public void setExternaltransactionid(String externaltransactionid) {
		this.externaltransactionid = externaltransactionid;
	}

	public Long getFrozen() {
		return frozen;
	}

	public void setFrozen(Long frozen) {
		this.frozen = frozen;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Long getPtBalMin() {
		return ptBalMin;
	}

	public void setPtBalMin(Long ptBalMin) {
		this.ptBalMin = ptBalMin;
	}

	public Long getPtBalMax() {
		return ptBalMax;
	}

	public void setPtBalMax(Long ptBalMax) {
		this.ptBalMax = ptBalMax;
	}

	public String getOrderStr() {
		return orderStr;
	}

	public void setOrderStr(String orderStr) {
		this.orderStr = orderStr;
	}

	public Long getAccountType() {
		return accountType;
	}

	public void setAccountType(Long accountType) {
		this.accountType = accountType;
	}

	public Long getCustomerType() {
		return customerType;
	}

	public void setCustomerType(Long customerType) {
		this.customerType = customerType;
	}

	public Long getCustomerStatus() {
		return customerStatus;
	}

	public void setCustomerStatus(Long customerStatus) {
		this.customerStatus = customerStatus;
	}

	public Long getEffectStatus() {
		return effectStatus;
	}

	public void setEffectStatus(Long effectStatus) {
		this.effectStatus = effectStatus;
	}

	public String getGameCode() {
		return gameCode;
	}

	public void setGameCode(String gameCode) {
		this.gameCode = gameCode;
	}
}
