package com.winterframework.firefrog.subsys.web.dto;

import java.util.List;

public class SubSysUserStrucResponse {

	private List<SubSysUserCustomer> user;
	private String errMsg;
	private Integer status;
	private Long id;
	private String account;
	private Integer userLvl;
	private String passwd;
	private Integer isFreeze;
	private Long lastLoginDate;
	private Long availBal;
	private Long parentId;
	private Integer passwdLvl;
	private Integer vipLvl;
	private Long freezeMethod;
	private List<SubSysActivityBankCard> bankList;
	private List<SubSysActivityWithdraw> withdrawList;

	public List<SubSysUserCustomer> getUser() {
		return user;
	}

	public void setUser(List<SubSysUserCustomer> user) {
		this.user = user;
	}

	public String getErrMsg() {
		return errMsg;
	}

	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public Integer getUserLvl() {
		return userLvl;
	}

	public void setUserLvl(Integer userLvl) {
		this.userLvl = userLvl;
	}

	public String getPasswd() {
		return passwd;
	}

	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}

	public Integer getIsFreeze() {
		return isFreeze;
	}

	public void setIsFreeze(Integer isFreeze) {
		this.isFreeze = isFreeze;
	}

	public Long getLastLoginDate() {
		return lastLoginDate;
	}

	public void setLastLoginDate(Long lastLoginDate) {
		this.lastLoginDate = lastLoginDate;
	}

	public Long getAvailBal() {
		return availBal;
	}

	public void setAvailBal(Long availBal) {
		this.availBal = availBal;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public Integer getPasswdLvl() {
		return passwdLvl;
	}

	public void setPasswdLvl(Integer passwdLvl) {
		this.passwdLvl = passwdLvl;
	}

	public Integer getVipLvl() {
		return vipLvl;
	}

	public void setVipLvl(Integer vipLvl) {
		this.vipLvl = vipLvl;
	}

	public Long getFreezeMethod() {
		return freezeMethod;
	}

	public void setFreezeMethod(Long freezeMethod) {
		this.freezeMethod = freezeMethod;
	}

	public List<SubSysActivityBankCard> getBankList() {
		return bankList;
	}

	public void setBankList(List<SubSysActivityBankCard> bankList) {
		this.bankList = bankList;
	}

	public List<SubSysActivityWithdraw> getWithdrawList() {
		return withdrawList;
	}

	public void setWithdrawList(List<SubSysActivityWithdraw> withdrawList) {
		this.withdrawList = withdrawList;
	}

}
