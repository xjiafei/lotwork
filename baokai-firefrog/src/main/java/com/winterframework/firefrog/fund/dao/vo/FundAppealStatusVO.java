package com.winterframework.firefrog.fund.dao.vo;

import java.math.BigDecimal;
import java.util.Date;

import com.winterframework.orm.dal.ibatis3.BaseEntity;

public class FundAppealStatusVO extends BaseEntity {

	private static final long serialVersionUID = 1L;

	private Long userId;

	private String account;

	private String appealSn;

	private String type;

	private Date appealTime;

	private String appealCreator;

	private Date argueTime;

	private String memo;

	private Integer status;

	private String fundSn;

	private BigDecimal fundAmt;

	private Date fundTime;

	private String fundCard;

	private String fundCardUser;
	
	private String tenpayAccount;
	
	private String tenpayName;
	
	private String depositeMode;
	
	private String bankName;
	
	private String chargeMemo;

	private String isSeperate;
	
	public String getIsSeperate() {
		return isSeperate;
	}

	public void setIsSeperate(String isSeperate) {
		this.isSeperate = isSeperate;
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

	public String getDepositeMode() {
		return depositeMode;
	}

	public void setDepositeMode(String depositeMode) {
		this.depositeMode = depositeMode;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getAppealSn() {
		return appealSn;
	}

	public void setAppealSn(String appealSn) {
		this.appealSn = appealSn;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getAppealCreator() {
		return appealCreator;
	}

	public void setAppealCreator(String appealCreator) {
		this.appealCreator = appealCreator;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getFundSn() {
		return fundSn;
	}

	public void setFundSn(String fundSn) {
		this.fundSn = fundSn;
	}

	public Date getFundTime() {
		return fundTime;
	}

	public void setFundTime(Date fundTime) {
		this.fundTime = fundTime;
	}

	public String getFundCard() {
		return fundCard;
	}

	public void setFundCard(String fundCard) {
		this.fundCard = fundCard;
	}

	public String getFundCardUser() {
		return fundCardUser;
	}

	public void setFundCardUser(String fundCardUser) {
		this.fundCardUser = fundCardUser;
	}

	public Date getAppealTime() {
		return appealTime;
	}

	public void setAppealTime(Date appealTime) {
		this.appealTime = appealTime;
	}

	public Date getArgueTime() {
		return argueTime;
	}

	public void setArgueTime(Date argueTime) {
		this.argueTime = argueTime;
	}

	public String getChargeMemo() {
		return chargeMemo;
	}

	public void setChargeMemo(String chargeMemo) {
		this.chargeMemo = chargeMemo;
	}

	public BigDecimal getFundAmt() {
		return fundAmt;
	}

	public void setFundAmt(BigDecimal fundAmt) {
		this.fundAmt = fundAmt;
	}

}
