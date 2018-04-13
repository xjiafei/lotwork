package com.winterframework.firefrog.beginmession.dao.vo;

import java.util.Date;

import com.winterframework.orm.dal.ibatis3.BaseEntity;

/**
 * 
 * @author Ami.Tsai
 *
 */
public class BeginMission extends BaseEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = -423020648955700763L;

	private Long userId;

	private Long status;

	private Date bindCardTime;

	private Long chargeAmt;
	
	private Long withdrawAmt;
	
	private Date missionStartTime;
	
	private String cancelReason;
	
	private Date chargeTime;
	
	private Date withdrawTime;
	
	private Date bindCardEndTime;
	
	private Date missionEndTime;
	
	private Date cancelTime;
	
	private String cancelUser;
	
	private Date bindCardLockTime;
	
	private Date missionValidTime;
	
	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getStatus() {
		return status;
	}

	public void setStatus(Long status) {
		this.status = status;
	}

	public Date getBindCardTime() {
		return bindCardTime;
	}

	public void setBindCardTime(Date bindCardTime) {
		this.bindCardTime = bindCardTime;
	}

	public Date getMissionStartTime() {
		return missionStartTime;
	}

	public void setMissionStartTime(Date missionStartTime) {
		this.missionStartTime = missionStartTime;
	}

	public Long getChargeAmt() {
		return chargeAmt;
	}

	public void setChargeAmt(Long chargeAmt) {
		this.chargeAmt = chargeAmt;
	}

	public Long getWithdrawAmt() {
		return withdrawAmt;
	}

	public void setWithdrawAmt(Long withdrawAmt) {
		this.withdrawAmt = withdrawAmt;
	}

	public String getCancelReason() {
		return cancelReason;
	}

	public void setCancelReason(String cancelReason) {
		this.cancelReason = cancelReason;
	}

	public Date getChargeTime() {
		return chargeTime;
	}

	public void setChargeTime(Date chargeTime) {
		this.chargeTime = chargeTime;
	}

	public Date getWithdrawTime() {
		return withdrawTime;
	}

	public void setWithdrawTime(Date withdrawTime) {
		this.withdrawTime = withdrawTime;
	}

	public Date getBindCardEndTime() {
		return bindCardEndTime;
	}

	public void setBindCardEndTime(Date bindCardEndTime) {
		this.bindCardEndTime = bindCardEndTime;
	}

	public Date getMissionEndTime() {
		return missionEndTime;
	}

	public void setMissionEndTime(Date missionEndTime) {
		this.missionEndTime = missionEndTime;
	}

	public Date getCancelTime() {
		return cancelTime;
	}

	public void setCancelTime(Date cancelTime) {
		this.cancelTime = cancelTime;
	}

	public String getCancelUser() {
		return cancelUser;
	}

	public void setCancelUser(String cancelUser) {
		this.cancelUser = cancelUser;
	}

	public Date getBindCardLockTime() {
		return bindCardLockTime;
	}

	public void setBindCardLockTime(Date bindCardLockTime) {
		this.bindCardLockTime = bindCardLockTime;
	}

	public Date getMissionValidTime() {
		return missionValidTime;
	}

	public void setMissionValidTime(Date missionValidTime) {
		this.missionValidTime = missionValidTime;
	}

}
