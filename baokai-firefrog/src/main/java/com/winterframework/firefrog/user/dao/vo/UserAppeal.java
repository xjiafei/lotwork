/**
 * Copyright (c) 2005-2012 winterframework.com
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.winterframework.firefrog.user.dao.vo;

import java.util.Date;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import com.winterframework.orm.dal.ibatis3.BaseEntity;

/**
 * @author cms group
 * @version 1.0
 * @since 1.0
 */

public class UserAppeal extends BaseEntity {

	private static final long serialVersionUID = -6366848803605423534L;
	// alias
	public static final String TABLE_ALIAS = "UserAppeal";
	public static final String ALIAS_ACCOUNT = "账号";
	public static final String ALIAS_APPEAL_TYPE = "0 安全问题 1 邮箱";
	public static final String ALIAS_ID_COPY = "身份证复印件";
	public static final String ALIAS_CARD_STRUC = "银行卡姓名";
	public static final String ALIAS_REGISTER_AREA = "注册地点";
	public static final String ALIAS_LOGIN_AREA = " 经常登录地点";
	public static final String ALIAS_RECEIVE_EMAIL = "接收邮箱";
	public static final String ALIAS_PASSED = "是否通过";
	public static final String ALIAS_OPERATER = "操作人";
	public static final String ALIAS_NOTICE = "备注";
	public static final String ALIAS_CREATE_DATE = "申诉时间";
	public static final String ALIAS_PASS_DATE = "申诉通过时间";
	public static final String ALIAS_ACTIVED_DAYS = "安全信息激活时间 0 1 3  5 7";

	// date formats

	// columns START
	private String account;
	private Long appealType;
	private String idCopy;
	private String cardStruc;
	private String registerArea;
	private String loginArea;
	private String receiveEmail;
	private Long passed;
	private Long operater;
	private String notice;
	private Date createDate;
	private Date passDate;
	private Long activedDays;
	private String operaterAccount;
	private Long vipLvl;
	// columns END

	public UserAppeal() {
	}

	
	/**
	 * @return the vipLvl
	 */
	public Long getVipLvl() {
		return vipLvl;
	}


	/**
	 * @param vipLvl the vipLvl to set
	 */
	public void setVipLvl(Long vipLvl) {
		this.vipLvl = vipLvl;
	}


	public UserAppeal(Long id) {
		this.id = id;
	}

	public void setAccount(String value) {
		this.account = value;
	}

	public String getAccount() {
		return this.account;
	}

	public void setAppealType(Long value) {
		this.appealType = value;
	}

	public Long getAppealType() {
		return this.appealType;
	}

	public void setIdCopy(String value) {
		this.idCopy = value;
	}

	public String getIdCopy() {
		return this.idCopy;
	}

	public void setCardStruc(String value) {
		this.cardStruc = value;
	}

	public String getCardStruc() {
		return this.cardStruc;
	}

	public void setRegisterArea(String value) {
		this.registerArea = value;
	}

	public String getRegisterArea() {
		return this.registerArea;
	}

	public void setLoginArea(String value) {
		this.loginArea = value;
	}

	public String getLoginArea() {
		return this.loginArea;
	}

	public void setReceiveEmail(String value) {
		this.receiveEmail = value;
	}

	public String getReceiveEmail() {
		return this.receiveEmail;
	}

	public Long getPassed() {
		return passed;
	}

	public void setPassed(Long passed) {
		this.passed = passed;
	}

	public void setOperater(Long value) {
		this.operater = value;
	}

	public Long getOperater() {
		return this.operater;
	}

	public void setNotice(String value) {
		this.notice = value;
	}

	public String getNotice() {
		return this.notice;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getPassDate() {
		return passDate;
	}

	public void setPassDate(Date passDate) {
		this.passDate = passDate;
	}

	public void setActivedDays(Long value) {
		this.activedDays = value;
	}

	public Long getActivedDays() {
		return this.activedDays;
	}

	public String getOperaterAccount() {
		return operaterAccount;
	}

	public void setOperaterAccount(String operaterAccount) {
		this.operaterAccount = operaterAccount;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).append("Id", getId()).append("Account", getAccount())
				.append("AppealType", getAppealType()).append("IdCopy", getIdCopy())
				.append("CardStruc", getCardStruc()).append("RegisterArea", getRegisterArea())
				.append("LoginArea", getLoginArea()).append("ReceiveEmail", getReceiveEmail())
				.append("Passed", getPassed()).append("Operater", getOperater()).append("Notice", getNotice())
				.append("CreateDate", getCreateDate()).append("PassDate", getPassDate())
				.append("ActivedDays", getActivedDays()).toString();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(getId()).append(getAccount()).append(getAppealType()).append(getIdCopy())
				.append(getCardStruc()).append(getRegisterArea()).append(getLoginArea()).append(getReceiveEmail())
				.append(getPassed()).append(getOperater()).append(getNotice()).append(getCreateDate())
				.append(getPassDate()).append(getActivedDays()).toHashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof UserAppeal == false) {
			return false;
		}
		if (this == obj) {
			return true;
		}
		UserAppeal other = (UserAppeal) obj;
		return new EqualsBuilder().append(getId(), other.getId())

		.append(getAccount(), other.getAccount())

		.append(getAppealType(), other.getAppealType())

		.append(getIdCopy(), other.getIdCopy())

		.append(getCardStruc(), other.getCardStruc())

		.append(getRegisterArea(), other.getRegisterArea())

		.append(getLoginArea(), other.getLoginArea())

		.append(getReceiveEmail(), other.getReceiveEmail())

		.append(getPassed(), other.getPassed())

		.append(getOperater(), other.getOperater())

		.append(getNotice(), other.getNotice())

		.append(getCreateDate(), other.getCreateDate())

		.append(getPassDate(), other.getPassDate())

		.append(getActivedDays(), other.getActivedDays())

		.isEquals();
	}
}
