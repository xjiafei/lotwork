/**
* Copyright (c) 2005-2012 winterframework.com
* Licensed under the Apache License, Version 2.0 (the "License");
*/
package com.winterframework.firefrog.game.dao.vo;

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

public class GamePackageAssist extends BaseEntity {

	/** 
	* @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么) 
	*/
	private static final long serialVersionUID = -7099826022002230235L;
	//alias
	public static final String TABLE_ALIAS = "方案明细辅助表";
	public static final String ALIAS_ITEMID = "itemID";
	public static final String ALIAS_METHOD_TYPE = "玩法类型 玩法群-玩法组-玩法-辅助玩法";
	public static final String ALIAS_CREATE_TIME = "创建时间";
	public static final String ALIAS_EVALUATE_AWARD = "预计奖金";
	public static final String ALIAS_UPDATE_TIME = "更新时间";

	//date formats

	//columns START
	private Long itemid;
	private String betTypeCode;
	private Date createTime;
	private Long evaluateAward;
	private Long evaluateAwardDown;	//预计最小奖金
	private Date updateTime;
	private Long winNumber = 0L;//WIN_NUMBER 中奖注数
	//columns END

	public GamePackageAssist() {
	}

	public GamePackageAssist(Long id) {
		this.id = id;
	}

	public void setItemid(Long value) {
		this.itemid = value;
	}

	public Long getItemid() {
		return this.itemid;
	}

	public void setBetTypeCode(String value) {
		this.betTypeCode = value;
	}

	public String getBetTypeCode() {
		return this.betTypeCode;
	}

	public void setCreateTime(Date value) {
		this.createTime = value;
	}

	public Date getCreateTime() {
		return this.createTime;
	}

	public void setEvaluateAward(Long value) {
		this.evaluateAward = value;
	}

	public Long getEvaluateAward() {
		return this.evaluateAward;
	}

	public void setUpdateTime(Date value) {
		this.updateTime = value;
	}

	public Date getUpdateTime() {
		return this.updateTime;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).append("Id", getId()).append("Itemid", getItemid())
				.append("MethodType", getBetTypeCode()).append("CreateTime", getCreateTime())
				.append("EvaluateAward", getEvaluateAward()).append("UpdateTime", getUpdateTime()).toString();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(getId()).append(getItemid()).append(getBetTypeCode())
				.append(getCreateTime()).append(getEvaluateAward()).append(getUpdateTime()).toHashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof GamePackageAssist == false)
			return false;
		if (this == obj)
			return true;
		GamePackageAssist other = (GamePackageAssist) obj;
		return new EqualsBuilder().append(getId(), other.getId())

		.append(getItemid(), other.getItemid())

		.append(getBetTypeCode(), other.getBetTypeCode())

		.append(getCreateTime(), other.getCreateTime())

		.append(getEvaluateAward(), other.getEvaluateAward())

		.append(getUpdateTime(), other.getUpdateTime())

		.isEquals();
	}

	public Long getWinNumber() {
		return winNumber;
	}

	public void setWinNumber(Long winNumber) {
		this.winNumber = winNumber;
	}

	public Long getEvaluateAwardDown() {
		return evaluateAwardDown;
	}

	public void setEvaluateAwardDown(Long evaluateAwardDown) {
		this.evaluateAwardDown = evaluateAwardDown;
	}
	
}
