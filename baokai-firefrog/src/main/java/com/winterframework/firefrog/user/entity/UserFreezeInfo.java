package com.winterframework.firefrog.user.entity;

import java.util.Date;

public class UserFreezeInfo {

	public enum FreezeMethodType {
		// 1 完全冻结 ABSOLUTELY
		// 2 可登录，不可投注，不可充提 JUST_FOR_LOGIN
		// 3 不可投注，可充提 JUST_FOR_RECHARGEANDWITHDRAWAL
		// 4 不可转账，不可提现 JUST_FOR_ORDER
		//5 申述
		ABSOLUTELY, JUST_FOR_LOGIN, JUST_FOR_RECHARGEANDWITHDRAWAL, JUST_FOR_ORDER, APPREAL;
	}

	public static int FREEZE_METHOD_ABSOLUTELY = 1;// 冻结方式完全冻结不能登录

	private boolean isFreeze;

	private Date freezeDate;

	private Long freezer;

	private String freezeMemo;

	private String freezeAccount;
	private Long freezeId;

	private FreezeMethodType freezeMethodType;

	public boolean isFreeze() {
		return isFreeze;
	}

	public void setFreeze(boolean isFreeze) {
		this.isFreeze = isFreeze;
	}

	public Date getFreezeDate() {
		return freezeDate;
	}

	public void setFreezeDate(Date freezeDate) {
		this.freezeDate = freezeDate;
	}

	public Long getFreezer() {
		return freezer;
	}

	public void setFreezer(Long freezer) {
		this.freezer = freezer;
	}

	public FreezeMethodType getFreezeMethodType() {
		return freezeMethodType;
	}

	public void setFreezeMethodType(FreezeMethodType freezeMethodType) {
		this.freezeMethodType = freezeMethodType;
	}

	public String getFreezeMemo() {
		return freezeMemo;
	}

	public void setFreezeMemo(String freezeMemo) {
		this.freezeMemo = freezeMemo;
	}

	public String getFreezeAccount() {
		return freezeAccount;
	}

	public void setFreezeAccount(String freezeAccount) {
		this.freezeAccount = freezeAccount;
	}

	public Long getFreezeId() {
		return freezeId;
	}

	public void setFreezeId(Long freezeId) {
		this.freezeId = freezeId;
	}

}
