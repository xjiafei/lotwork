package com.winterframework.firefrog.active.web.dto;

import java.io.Serializable;

public class OctActivity4Response implements Serializable{

	private String signUp = "0";
	private String todayDeposit = "0";
	private String todayBets = "0";
	private String todayPrize = "0";
	private String depositCount = "0";
	private String isAddr = "0";
	
	
	public String getSignUp() {
		return signUp;
	}
	public void setSignUp(String signUp) {
		this.signUp = signUp;
	}
	public String getTodayDeposit() {
		return todayDeposit;
	}
	public void setTodayDeposit(String todayDeposit) {
		this.todayDeposit = todayDeposit;
	}
	public String getTodayBets() {
		return todayBets;
	}
	public void setTodayBets(String todayBets) {
		this.todayBets = todayBets;
	}
	public String getTodayPrize() {
		return todayPrize;
	}
	public void setTodayPrize(String todayPrize) {
		this.todayPrize = todayPrize;
	}
	public String getDepositCount() {
		return depositCount;
	}
	public void setDepositCount(String depositCount) {
		this.depositCount = depositCount;
	}
	public String getIsAddr() {
		return isAddr;
	}
	public void setIsAddr(String isAddr) {
		this.isAddr = isAddr;
	}
}
