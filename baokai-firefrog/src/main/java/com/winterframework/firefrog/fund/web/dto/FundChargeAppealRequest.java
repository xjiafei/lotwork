package com.winterframework.firefrog.fund.web.dto;

import java.io.Serializable;
import java.util.Date;

public class FundChargeAppealRequest implements Serializable {

	private static final long serialVersionUID = 1L;
	private Long id;

	private Long userId;

	private String userAccount;

	private String appealSn;

	private Integer appealStatus;

	private String appealAcct;

	private Date reviewStartTime;
	
	private Date reviewEndTime;

	private String appealMemo;

	private Date argueTime;

	private String argueAcct;

	private String chargeSn;

	private String chargeUserName;

	private Long chargeAmt;

	private Date chargeTime;
	
	private String chargeMemo;
	
	private String tenpayAccount;
	
	private String tenpayName;

	private Integer depositeMode;

	private Integer bankId;

	private String bankCardNumber;

	private Long electronicNumber;

	private String uploadImages;
	
	private String transactionNum;

	public Date getReviewStartTime() {
		return reviewStartTime;
	}

	public void setReviewStartTime(Date reviewStartTime) {
		this.reviewStartTime = reviewStartTime;
	}

	public Date getReviewEndTime() {
		return reviewEndTime;
	}

	public void setReviewEndTime(Date reviewEndTime) {
		this.reviewEndTime = reviewEndTime;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getUserAccount() {
		return userAccount;
	}

	public void setUserAccount(String userAccount) {
		this.userAccount = userAccount;
	}
	public String getAppealSn() {
		return appealSn;
	}

	public void setAppealSn(String appealSn) {
		this.appealSn = appealSn;
	}

	public Integer getAppealStatus() {
		return appealStatus;
	}

	public void setAppealStatus(Integer appealStatus) {
		this.appealStatus = appealStatus;
	}

	public String getAppealAcct() {
		return appealAcct;
	}

	public void setAppealAcct(String appealAcct) {
		this.appealAcct = appealAcct;
	}

	public String getAppealMemo() {
		return appealMemo;
	}

	public void setAppealMemo(String appealMemo) {
		this.appealMemo = appealMemo;
	}

	public Date getArgueTime() {
		return argueTime;
	}

	public void setArgueTime(Date argueTime) {
		this.argueTime = argueTime;
	}

	public String getArgueAcct() {
		return argueAcct;
	}

	public void setArgueAcct(String argueAcct) {
		this.argueAcct = argueAcct;
	}

	public String getChargeSn() {
		return chargeSn;
	}

	public void setChargeSn(String chargeSn) {
		this.chargeSn = chargeSn;
	}

	public String getChargeUserName() {
		return chargeUserName;
	}

	public void setChargeUserName(String chargeUserName) {
		this.chargeUserName = chargeUserName;
	}

	public Long getChargeAmt() {
		return chargeAmt;
	}

	public void setChargeAmt(Long chargeAmt) {
		this.chargeAmt = chargeAmt;
	}

	public Date getChargeTime() {
		return chargeTime;
	}

	public void setChargeTime(Date chargeTime) {
		this.chargeTime = chargeTime;
	}

	public Integer getDepositeMode() {
		return depositeMode;
	}

	public void setDepositeMode(Integer depositeMode) {
		this.depositeMode = depositeMode;
	}

	public Integer getBankId() {
		return bankId;
	}

	public void setBankId(Integer bankId) {
		this.bankId = bankId;
	}
	
	public String getBankCardNumber() {
		return bankCardNumber;
	}

	public void setBankCardNumber(String bankCardNumber) {
		this.bankCardNumber = bankCardNumber;
	}

	public Long getElectronicNumber() {
		return electronicNumber;
	}

	public void setElectronicNumber(Long electronicNumber) {
		this.electronicNumber = electronicNumber;
	}

	public String getUploadImages() {
		return uploadImages;
	}

	public void setUploadImages(String uploadImages) {
		this.uploadImages = uploadImages;
	}

	public String getChargeMemo() {
		return chargeMemo;
	}

	public void setChargeMemo(String chargeMemo) {
		this.chargeMemo = chargeMemo;
	}

	public String getTenpayAccount() {
		return tenpayAccount;
	}

	public void setTenpayAccount(String tenpayAccount) {
		this.tenpayAccount = tenpayAccount;
	}

	public String getTenpayName() {
		return tenpayName;
	}

	public void setTenpayName(String tenpayName) {
		this.tenpayName = tenpayName;
	}

	public String getTransactionNum() {
		return transactionNum;
	}

	public void setTransactionNum(String transactionNum) {
		this.transactionNum = transactionNum;
	}



}
