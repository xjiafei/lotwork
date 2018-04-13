/**
* Copyright (c) 2005-2012 winterframework.com
* Licensed under the Apache License, Version 2.0 (the "License");
*/
package com.winterframework.firefrog.game.dao.vo;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import com.winterframework.orm.dal.ibatis3.BaseEntity;

/**
 * @author cms group
 * @version 1.0
 * @since 1.0
 */

public class GameBettypeAssist extends BaseEntity {

	/** 
	* @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么) 
	*/
	private static final long serialVersionUID = -8795807777308001582L;
	//alias
	public static final String TABLE_ALIAS = "投注方式辅助表";
	public static final String ALIAS_BETTYPEID = "投注方式ID";
	public static final String ALIAS_CREATE_TIME = "创建时间";
	public static final String ALIAS_UPDATE_TIME = "更新时间";
	public static final String ALIAS_THEORY_BONUS = "理论奖金";
	public static final String ALIAS_METHOD_TYPE = "玩法类型";

	//date formats

	//columns START
	private Long bettypeid;
	private Object createTime;
	private Object updateTime;
	private Long theoryBonus;
	private Long methodType;

	//columns END

	public GameBettypeAssist() {
	}

	public GameBettypeAssist(Long id) {
		this.id = id;
	}

	public void setBettypeid(Long value) {
		this.bettypeid = value;
	}

	public Long getBettypeid() {
		return this.bettypeid;
	}

	public void setCreateTime(Object value) {
		this.createTime = value;
	}

	public Object getCreateTime() {
		return this.createTime;
	}

	public void setUpdateTime(Object value) {
		this.updateTime = value;
	}

	public Object getUpdateTime() {
		return this.updateTime;
	}

	public void setTheoryBonus(Long value) {
		this.theoryBonus = value;
	}

	public Long getTheoryBonus() {
		return this.theoryBonus;
	}

	public void setMethodType(Long value) {
		this.methodType = value;
	}

	public Long getMethodType() {
		return this.methodType;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).append("Id", getId()).append("Bettypeid", getBettypeid())
				.append("CreateTime", getCreateTime()).append("UpdateTime", getUpdateTime())
				.append("TheoryBonus", getTheoryBonus()).append("MethodType", getMethodType()).toString();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(getId()).append(getBettypeid()).append(getCreateTime())
				.append(getUpdateTime()).append(getTheoryBonus()).append(getMethodType()).toHashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof GameBettypeAssist == false)
			return false;
		if (this == obj)
			return true;
		GameBettypeAssist other = (GameBettypeAssist) obj;
		return new EqualsBuilder().append(getId(), other.getId())

		.append(getBettypeid(), other.getBettypeid())

		.append(getCreateTime(), other.getCreateTime())

		.append(getUpdateTime(), other.getUpdateTime())

		.append(getTheoryBonus(), other.getTheoryBonus())

		.append(getMethodType(), other.getMethodType())

		.isEquals();
	}
}
