/**
* Copyright (c) 2005-2012 winterframework.com
* Licensed under the Apache License, Version 2.0 (the "License");
*/
package com.winterframework.firefrog.fund.dao.vo;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import com.winterframework.orm.dal.ibatis3.BaseEntity;

/**
 * @author cms group
 * @version 1.0
 * @since 1.0
 */

public class UserBank extends BaseEntity {

	//alias
	public static final String TABLE_ALIAS = "UserBank";
	public static final String ALIAS_USER_ID = "userId";
	public static final String ALIAS_BANK_ID = "bankId";
	public static final String ALIAS_BANK_NUMBER = "账号";
	public static final String ALIAS_PROVINCE = "省";
	public static final String ALIAS_CITY = "市";
	public static final String ALIAS_BRANCH_NAME = "支行名称";
	public static final String ALIAS_BANK_ACCOUNT = "开户人姓名";
	public static final String ALIAS_MC_BANK_ID = "mc bank id";

	//date formats

	//columns START
	private Long userId;
	private Long bankId;
	private String bankNumber;
	private String province;
	private String city;
	private String branchName;
	private String bankAccount;
	private Long mcBankId;
	private Long id;
	private String account;
	private String topAcc;
	private Long isFreeze;
	private Long freezeMethod;
	private Long vipLvl;
	private Boolean isBlackList;

	//columns END

	public UserBank() {
	}

	public Long getVipLvl() {
		return vipLvl;
	}

	public void setVipLvl(Long vipLvl) {
		this.vipLvl = vipLvl;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getTopAcc() {
		return topAcc;
	}

	public void setTopAcc(String topAcc) {
		this.topAcc = topAcc;
	}

	public Long getIsFreeze() {
		return isFreeze;
	}

	public void setIsFreeze(Long isFreeze) {
		this.isFreeze = isFreeze;
	}

	public Long getFreezeMethod() {
		return freezeMethod;
	}

	public void setFreezeMethod(Long freezeMethod) {
		this.freezeMethod = freezeMethod;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public UserBank(Long id) {
		this.id = id;
	}

	public void setUserId(Long value) {
		this.userId = value;
	}

	public Long getUserId() {
		return this.userId;
	}

	public void setBankId(Long value) {
		this.bankId = value;
	}

	public Long getBankId() {
		return this.bankId;
	}

	public void setBankNumber(String value) {
		this.bankNumber = value;
	}

	public String getBankNumber() {
		return this.bankNumber;
	}

	public void setProvince(String value) {
		this.province = value;
	}

	public String getProvince() {
		return this.province;
	}

	public void setCity(String value) {
		this.city = value;
	}

	public String getCity() {
		return this.city;
	}

	public void setBranchName(String value) {
		this.branchName = value;
	}

	public String getBranchName() {
		return this.branchName;
	}

	public void setBankAccount(String value) {
		this.bankAccount = value;
	}

	public String getBankAccount() {
		return this.bankAccount;
	}

	public void setMcBankId(Long value) {
		this.mcBankId = value;
	}

	public Long getMcBankId() {
		return this.mcBankId;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).append("Id", getId()).append("UserId", getUserId())
				.append("BankId", getBankId()).append("BankNumber", getBankNumber()).append("Province", getProvince())
				.append("City", getCity()).append("BranchName", getBranchName()).append("GmtCreated", getGmtCreated())
				.append("GmtModified", getGmtModified()).append("BankAccount", getBankAccount())
				.append("McBankId", getMcBankId()).toString();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(getId()).append(getUserId()).append(getBankId()).append(getBankNumber())
				.append(getProvince()).append(getCity()).append(getBranchName()).append(getGmtCreated())
				.append(getGmtModified()).append(getBankAccount()).append(getMcBankId()).toHashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof UserBank == false) {
			return false;
		}
		if (this == obj) {
			return true;
		}
		UserBank other = (UserBank) obj;
		return new EqualsBuilder().append(getId(), other.getId())

		.append(getUserId(), other.getUserId())

		.append(getBankId(), other.getBankId())

		.append(getBankNumber(), other.getBankNumber())

		.append(getProvince(), other.getProvince())

		.append(getCity(), other.getCity())

		.append(getBranchName(), other.getBranchName())

		.append(getGmtCreated(), other.getGmtCreated())

		.append(getGmtModified(), other.getGmtModified())

		.append(getBankAccount(), other.getBankAccount())

		.append(getMcBankId(), other.getMcBankId())

		.isEquals();
	}

	public Boolean getIsBlackList() {
		return isBlackList;
	}

	public void setIsBlackList(Boolean isBlackList) {
		this.isBlackList = isBlackList;
	}

}
