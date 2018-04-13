 /**
 * Copyright (c) 2005-2012 winterframework.com
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.winterframework.firefrog.game.dao.vo;

import java.util.Date;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.winterframework.orm.dal.ibatis3.BaseEntity;

/**
 * 封鎖變價參數
 * @author cms group
 * @version 1.0
 * @since 1.0
 */
public class GameLockParam extends BaseEntity {
	
	private static final long serialVersionUID = 1L;
	
	/**彩種ID*/
	private Long gameId;
	/**狀態；1：待審核、2：審核通過、3：審核不通過、4：已發佈*/
	private Long status;
	/**變價最小值*/
	private Long minVal;
	/**起始時間*/
	private Date startTime;
	/**結束時間*/
	private Date endTime;
	/**老的變價最小值*/
	private Long minValProcess;
	/**老的起始時間*/
	private Date startTimeProcess;
	/**老的結束時間*/
	private Date endTimeProcess;
	/**最後修改後台人員帳號*/
	private String modifierStr;
	/**後台建立人員帳號*/
	private String operator;

	public GameLockParam(){}

	public GameLockParam(Long id){
		this.id = id;
	}
	/**
	 * 設定彩種ID。
	 * @param value
	 */
	public void setGameId(Long value) {
		this.gameId = value;
	}
	/**
	 * 取得彩種ID。
	 * @return
	 */
	public Long getGameId() {
		return this.gameId;
	}
	/**
	 * 設定狀態。
	 * @param value 1：待審核、2：審核通過、3：審核不通過、4：已發佈
	 */
	public void setStatus(Long value) {
		this.status = value;
	}
	/**
	 * 取得狀態。
	 * @return 1：待審核、2：審核通過、3：審核不通過、4：已發佈
	 */
	public Long getStatus() {
		return this.status;
	}
	/**
	 * 設定變價最小值。
	 * @param value
	 */
	public void setMinVal(Long value) {
		this.minVal = value;
	}
	/**
	 * 取得變價最小值。
	 * @return
	 */
	public Long getMinVal() {
		return this.minVal;
	}
	/**
	 * 設定起始時間。
	 * @param value
	 */
	public void setStartTime(Date value) {
		this.startTime = value;
	}
	/**
	 * 取得起始時間。
	 * @return
	 */
	public Date getStartTime() {
		return this.startTime;
	}
	/**
	 * 設定結束時間。
	 * @param value
	 */
	public void setEndTime(Date value) {
		this.endTime = value;
	}
	/**
	 * 取得結束時間。
	 * @return
	 */
	public Date getEndTime() {
		return this.endTime;
	}
	/**
	 * 設定老的變價最小值。
	 * @param value
	 */
	public void setMinValProcess(Long value) {
		this.minValProcess = value;
	}
	/**
	 * 取得老的變價最小值。
	 * @return
	 */
	public Long getMinValProcess() {
		return this.minValProcess;
	}
	/**
	 * 設定老的起始時間。
	 * @param value
	 */
	public void setStartTimeProcess(Date value) {
		this.startTimeProcess = value;
	}
	/**
	 * 取得老的起始時間。
	 * @return
	 */
	public Date getStartTimeProcess() {
		return this.startTimeProcess;
	}
	/**
	 * 設定老的結束時間。
	 * @param value
	 */
	public void setEndTimeProcess(Date value) {
		this.endTimeProcess = value;
	}
	/**
	 * 取得老的結束時間。
	 * @return
	 */
	public Date getEndTimeProcess() {
		return this.endTimeProcess;
	}
	/**
	 * 取得後台建立人員帳號。
	 * @return
	 */
	public String getOperator() {
		return operator;
	}
	/**
	 * 後台建立人員帳號。
	 * @param operator
	 */
	public void setOperator(String operator) {
		this.operator = operator;
	}
	/**
	 * 取得最後修改後台人員帳號。
	 * @return
	 */
	public String getModifierStr() {
		return modifierStr;
	}
	/**
	 * 設定最後修改後台人員帳號。
	 * @param modifierStr
	 */
	public void setModifierStr(String modifierStr) {
		this.modifierStr = modifierStr;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}
    @Override
	public int hashCode() {
		return new HashCodeBuilder()
		.append(getId())
		.append(getGameId())
		.append(getStatus())
		.append(getMinVal())
		.append(getStartTime())
		.append(getEndTime())
		.append(getMinValProcess())
		.append(getStartTimeProcess())
		.append(getEndTimeProcess())
			.toHashCode();
	}
    @Override
	public boolean equals(Object obj) {
		if(obj instanceof GameLockParam == false) return false;
		if(this == obj) return true;
		GameLockParam other = (GameLockParam)obj;
		return new EqualsBuilder()
		.append(getId(),other.getId())

		.append(getGameId(),other.getGameId())

		.append(getStatus(),other.getStatus())

		.append(getMinVal(),other.getMinVal())

		.append(getStartTime(),other.getStartTime())

		.append(getEndTime(),other.getEndTime())

		.append(getMinValProcess(),other.getMinValProcess())

		.append(getStartTimeProcess(),other.getStartTimeProcess())

		.append(getEndTimeProcess(),other.getEndTimeProcess())

			.isEquals();
	}
}

