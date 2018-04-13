package com.winterframework.firefrog.beginmession.dao.vo;

import com.winterframework.orm.dal.ibatis3.BaseEntity;


public class BeginMissionVer extends BaseEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2207775236852234340L;

	private Long userId;

	private String userAccount;

	private Long eggVer;

	private Long chargeVer;

	private Long daybetVer;

	private Long dayquesVer;

	private Long newMissionVer;


	private Long tolbetVer;

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

	public Long getEggVer() {
		return eggVer;
	}

	public void setEggVer(Long eggVer) {
		this.eggVer = eggVer;
	}

	public Long getChargeVer() {
		return chargeVer;
	}

	public void setChargeVer(Long chargeVer) {
		this.chargeVer = chargeVer;
	}

	public Long getDaybetVer() {
		return daybetVer;
	}

	public void setDaybetVer(Long daybetVer) {
		this.daybetVer = daybetVer;
	}

	public Long getDayquesVer() {
		return dayquesVer;
	}

	public void setDayquesVer(Long dayquesVer) {
		this.dayquesVer = dayquesVer;
	}

	public Long getNewMissionVer() {
		return newMissionVer;
	}

	public void setNewMissionVer(Long newMissionVer) {
		this.newMissionVer = newMissionVer;
	}

	public Long getTolbetVer() {
		return tolbetVer;
	}

	public void setTolbetVer(Long tolbetVer) {
		this.tolbetVer = tolbetVer;
	}

}
