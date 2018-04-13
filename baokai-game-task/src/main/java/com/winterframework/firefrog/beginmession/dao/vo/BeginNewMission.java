package com.winterframework.firefrog.beginmession.dao.vo;

import java.util.Date;

import com.winterframework.orm.dal.ibatis3.BaseEntity;



public class BeginNewMission extends BaseEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4206560491969068492L;

	private String createUser;

	private Date createTime;

	private String modifyUser;

	private Date modifyTime;

	private String bindCardText;

	private Long bindCardPremium;

	private String chargeText;

	private String withdrawText;

	private Long withdrawFactor;

	private Long withdrawPremium;

	private Long dayBetFactor;

	private Long dayAnsLow;
	
	private Long dayAnsHigh;
	
	private Long dayAnsUnit;
	
	private Long version;
	
	private String bindCardCheck;

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getModifyUser() {
		return modifyUser;
	}

	public void setModifyUser(String modifyUser) {
		this.modifyUser = modifyUser;
	}

	public Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	public String getBindCardText() {
		return bindCardText;
	}

	public void setBindCardText(String bindCardText) {
		this.bindCardText = bindCardText;
	}

	public Long getBindCardPremium() {
		return bindCardPremium;
	}

	public void setBindCardPremium(Long bindCardPremium) {
		this.bindCardPremium = bindCardPremium;
	}

	public String getChargeText() {
		return chargeText;
	}

	public void setChargeText(String chargeText) {
		this.chargeText = chargeText;
	}

	public String getWithdrawText() {
		return withdrawText;
	}

	public void setWithdrawText(String withdrawText) {
		this.withdrawText = withdrawText;
	}

	public Long getWithdrawFactor() {
		return withdrawFactor;
	}

	public void setWithdrawFactor(Long withdrawFactor) {
		this.withdrawFactor = withdrawFactor;
	}

	public Long getWithdrawPremium() {
		return withdrawPremium;
	}

	public void setWithdrawPremium(Long withdrawPremium) {
		this.withdrawPremium = withdrawPremium;
	}

	public Long getVersion() {
		return version;
	}

	public void setVersion(Long version) {
		this.version = version;
	}

	public String getBindCardCheck() {
		return bindCardCheck;
	}

	public void setBindCardCheck(String bindCardCheck) {
		this.bindCardCheck = bindCardCheck;
	}

	public Long getDayBetFactor() {
		return dayBetFactor;
	}

	public void setDayBetFactor(Long dayBetFactor) {
		this.dayBetFactor = dayBetFactor;
	}

	public Long getDayAnsLow() {
		return dayAnsLow;
	}

	public void setDayAnsLow(Long dayAnsLow) {
		this.dayAnsLow = dayAnsLow;
	}

	public Long getDayAnsHigh() {
		return dayAnsHigh;
	}

	public void setDayAnsHigh(Long dayAnsHigh) {
		this.dayAnsHigh = dayAnsHigh;
	}

	public Long getDayAnsUnit() {
		return dayAnsUnit;
	}

	public void setDayAnsUnit(Long dayAnsUnit) {
		this.dayAnsUnit = dayAnsUnit;
	}

}
