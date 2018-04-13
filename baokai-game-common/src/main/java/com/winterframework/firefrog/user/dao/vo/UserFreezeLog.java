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

public class UserFreezeLog extends BaseEntity {

	private static final long serialVersionUID = 6802266880430609156L;
	// alias
	public static final String TABLE_ALIAS = "UserFreezeLog";
	public static final String ALIAS_RANGE = "冻结范围";
	public static final String ALIAS_METHOD = "冻结方式";
	public static final String ALIAS_MEMO = "冻结原因";
	public static final String ALIAS_FROZEN = "被冻结人";
	public static final String ALIAS_ACTOR = "操作人";
	public static final String ALIAS_ACTION = "动作 0：冻结 1：解冻";

	// date formats

	// columns START
	private Integer range;
	private Integer method;
	private String memo;
	private Long frozen;
	private Long actor;
	private Integer action;

	// columns 
	private Integer unfreezeRange;
	private Integer unfreezeMethod;
	private String unfreezeMemo;
	private Long unfreezeActor;
	private Date unfreezeDate;

	//查询解冻记录冗余字段，数据库中需要关联查询获取值内容
	private Date freezeDate;
	private String frozenAccount;
	private String actorAccount;
	private Integer userLvl;
	private Long frozenAccountBal;
	private String freezeAccount;
	private String unfreezeAccount;

	// columns END

	public UserFreezeLog() {
	}

	public void setRange(Integer value) {
		this.range = value;
	}

	public Integer getRange() {
		return this.range;
	}

	public void setMethod(Integer value) {
		this.method = value;
	}

	public Integer getMethod() {
		return this.method;
	}

	public String getFreezeAccount() {
		return freezeAccount;
	}

	public void setFreezeAccount(String freezeAccount) {
		this.freezeAccount = freezeAccount;
	}

	public String getUnfreezeAccount() {
		return unfreezeAccount;
	}

	public void setUnfreezeAccount(String unfreezeAccount) {
		this.unfreezeAccount = unfreezeAccount;
	}

	public void setMemo(String value) {
		this.memo = value;
	}

	public String getMemo() {
		return this.memo;
	}

	public void setFrozen(Long value) {
		this.frozen = value;
	}

	public Long getFrozen() {
		return this.frozen;
	}

	public void setActor(Long value) {
		this.actor = value;
	}

	public Long getActor() {
		return this.actor;
	}

	public void setAction(Integer value) {
		this.action = value;
	}

	public Integer getAction() {
		return this.action;
	}

	public Date getFreezeDate() {
		return freezeDate;
	}

	public void setFreezeDate(Date freezeDate) {
		this.freezeDate = freezeDate;
	}

	public String getFrozenAccount() {
		return frozenAccount;
	}

	public void setFrozenAccount(String frozenAccount) {
		this.frozenAccount = frozenAccount;
	}

	public String getActorAccount() {
		return actorAccount;
	}

	public void setActorAccount(String actorAccount) {
		this.actorAccount = actorAccount;
	}

	public Integer getUserLvl() {
		return userLvl;
	}

	public void setUserLvl(Integer userLvl) {
		this.userLvl = userLvl;
	}

	public Long getFrozenAccountBal() {
		return frozenAccountBal;
	}

	public void setFrozenAccountBal(Long frozenAccountBal) {
		this.frozenAccountBal = frozenAccountBal;
	}

	public Integer getUnfreezeRange() {
		return unfreezeRange;
	}

	public void setUnfreezeRange(Integer unfreezeRange) {
		this.unfreezeRange = unfreezeRange;
	}

	public Integer getUnfreezeMethod() {
		return unfreezeMethod;
	}

	public void setUnfreezeMethod(Integer unfreezeMethod) {
		this.unfreezeMethod = unfreezeMethod;
	}

	public String getUnfreezeMemo() {
		return unfreezeMemo;
	}

	public void setUnfreezeMemo(String unfreezeMemo) {
		this.unfreezeMemo = unfreezeMemo;
	}

	public Long getUnfreezeActor() {
		return unfreezeActor;
	}

	public void setUnfreezeActor(Long unfreezeActor) {
		this.unfreezeActor = unfreezeActor;
	}

	public Date getUnfreezeDate() {
		return unfreezeDate;
	}

	public void setUnfreezeDate(Date unfreezeDate) {
		this.unfreezeDate = unfreezeDate;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).append("Id", getId()).append("Range", getRange())
				.append("Method", getMethod()).append("Memo", getMemo()).append("GmtCreated", getGmtCreated())
				.append("Frozen", getFrozen()).append("Actor", getActor()).append("Action", getAction()).toString();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(getId()).append(getRange()).append(getMethod()).append(getMemo())
				.append(getGmtCreated()).append(getFrozen()).append(getActor()).append(getAction()).toHashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof UserFreezeLog == false) {
			return false;
		}
		if (this == obj) {
			return true;
		}
		UserFreezeLog other = (UserFreezeLog) obj;
		return new EqualsBuilder().append(getId(), other.getId())

		.append(getRange(), other.getRange())

		.append(getMethod(), other.getMethod())

		.append(getMemo(), other.getMemo())

		.append(getGmtCreated(), other.getGmtCreated())

		.append(getFrozen(), other.getFrozen())

		.append(getActor(), other.getActor())

		.append(getAction(), other.getAction())

		.isEquals();
	}
}
