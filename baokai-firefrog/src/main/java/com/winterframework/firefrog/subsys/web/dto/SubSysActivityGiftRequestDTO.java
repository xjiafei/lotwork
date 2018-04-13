package com.winterframework.firefrog.subsys.web.dto;

import java.util.List;

public class SubSysActivityGiftRequestDTO {

	public String token;
	public String account;
	public String roundId;
	public Long amount;
	public String debitSn;
	public String activitySn;
	public String note;
	public int debitDirection;
	public int activityDirection;
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getRoundId() {
		return roundId;
	}
	public void setRoundId(String roundId) {
		this.roundId = roundId;
	}
	public Long getAmount() {
		return amount;
	}
	public void setAmount(Long amount) {
		this.amount = amount;
	}
	public String getDebitSn() {
		return debitSn;
	}
	public void setDebitSn(String debitSn) {
		this.debitSn = debitSn;
	}
	public String getActivitySn() {
		return activitySn;
	}
	public void setActivitySn(String activitySn) {
		this.activitySn = activitySn;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public int getDebitDirection() {
		return debitDirection;
	}
	public void setDebitDirection(int debitDirection) {
		this.debitDirection = debitDirection;
	}
	public int getActivityDirection() {
		return activityDirection;
	}
	public void setActivityDirection(int activityDirection) {
		this.activityDirection = activityDirection;
	}
	
	
}
