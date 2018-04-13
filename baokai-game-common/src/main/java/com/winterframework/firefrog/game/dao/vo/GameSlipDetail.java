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
 * 注单明细
 * @ClassName
 * @Description
 * @author ibm
 * 2014年10月13日
 */
public class GameSlipDetail extends BaseEntity { 
	private static final long serialVersionUID = 5867558040096294806L;
	//alias
	public static final String TABLE_ALIAS = "注单明细表";
	public static final String ALIAS_PARENT_ID = "注单父ID";
	public static final String ALIAS_METHOD_TYPE = "玩法类型 玩法群-玩法组-玩法-辅助玩法";
	public static final String ALIAS_REAL_NUMBER = "判奖号码";
	public static final String ALIAS_STATUS = "状态"; 
	public static final String ALIAS_WIN_LEVEL = "中奖级别"; 
	public static final String ALIAS_WIN_AMOUNT = "中奖金额"; 
	public static final String ALIAS_CREATE_TIME = "创建时间";
	public static final String ALIAS_UPDATE_TIME = "更新时间";

	//date formats

	//columns START
	private Long parentId;
	private String betTypeCode;
	private String realNumber;
	private Integer status;
	private Integer winLevel;
	private Long winAmount;
	private Date updateTime;
	private Date createTime; 
	//columns END
	public enum Status{
		//注单状态 1:等待开奖 2:中奖 3:未中奖 4:撤销
		INIT(0),WIN(2),UN_WIN(3),CANCEL(4);
		private int _value;
		Status(int value){
			this._value=value;
		}
		public int getValue(){
			return this._value;
		}
	}
	public Long getParentId() {
		return parentId;
	}
	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}
	public String getBetTypeCode() {
		return betTypeCode;
	}
	public void setBetTypeCode(String betTypeCode) {
		this.betTypeCode = betTypeCode;
	}
	public String getRealNumber() {
		return realNumber;
	}
	public void setRealNumber(String realNumber) {
		this.realNumber = realNumber;
	}
	
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Integer getWinLevel() {
		return winLevel;
	}
	public void setWinLevel(Integer winLevel) {
		this.winLevel = winLevel;
	}
	
	public Long getWinAmount() {
		return winAmount;
	}
	public void setWinAmount(Long winAmount) {
		this.winAmount = winAmount;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
 
	
}
