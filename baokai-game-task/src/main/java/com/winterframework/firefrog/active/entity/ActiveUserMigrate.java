 /**
 * Copyright (c) 2005-2012 winterframework.com
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
 package com.winterframework.firefrog.active.entity;

import java.util.Date;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.winterframework.modules.web.jsonresult.FirefrogDateDeSerializer;
import com.winterframework.modules.web.jsonresult.FirefrogDateSerializer;
import com.winterframework.orm.dal.ibatis3.BaseEntity;

/**
 * @author cms group
 * @version 1.0
 * @since 1.0
 */


public class ActiveUserMigrate extends BaseEntity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2746637120573958103L;
	//alias
	public static final String TABLE_ALIAS = "ActiveUserMigrate";
	public static final String ALIAS_UPDATE_TIME = "updateTime";
	public static final String ALIAS_FUND_TIME = "fundTime";
	public static final String ALIAS_BET_TIME = "betTime";
	
	//date formats
			public static final String FORMAT_UPDATE_TIME = DATE_TIME_FORMAT;
			public static final String FORMAT_FUND_TIME = DATE_TIME_FORMAT;
			public static final String FORMAT_BET_TIME = DATE_TIME_FORMAT;
	
	//columns START
			@JsonSerialize(using = FirefrogDateSerializer.class)  
			@JsonDeserialize(using = FirefrogDateDeSerializer.class) 

	private Date updateTime;
			@JsonSerialize(using = FirefrogDateSerializer.class)  
			@JsonDeserialize(using = FirefrogDateDeSerializer.class) 

	private Date fundTime;
			@JsonSerialize(using = FirefrogDateSerializer.class)  
			@JsonDeserialize(using = FirefrogDateDeSerializer.class) 

	private Date betTime;
			@JsonSerialize(using = FirefrogDateSerializer.class)  
			@JsonDeserialize(using = FirefrogDateDeSerializer.class) 

			private Date testTime;
	//columns END

	public ActiveUserMigrate(){
	}

	public Date getTestTime() {
		return testTime;
	}

	public void setTestTime(Date testTime) {
		this.testTime = testTime;
	}

	public ActiveUserMigrate(
		Long id
	){
		this.id = id;
	}

	public String getUpdateTimeString() {
		return date2String(getUpdateTime(), FORMAT_UPDATE_TIME);
	}
	public void setUpdateTimeString(String value) {
		setUpdateTime(string2Date(value, FORMAT_UPDATE_TIME,Date.class));
	}
	
	public void setUpdateTime(Date value) {
		this.updateTime = value;
	}
	
	public Date getUpdateTime() {
		return this.updateTime;
	}
	public String getFundTimeString() {
		return date2String(getFundTime(), FORMAT_FUND_TIME);
	}
	public void setFundTimeString(String value) {
		setFundTime(string2Date(value, FORMAT_FUND_TIME,Date.class));
	}
	
	public void setFundTime(Date value) {
		this.fundTime = value;
	}
	
	public Date getFundTime() {
		return this.fundTime;
	}
	public String getBetTimeString() {
		return date2String(getBetTime(), FORMAT_BET_TIME);
	}
	public void setBetTimeString(String value) {
		setBetTime(string2Date(value, FORMAT_BET_TIME,Date.class));
	}
	
	public void setBetTime(Date value) {
		this.betTime = value;
	}
	
	public Date getBetTime() {
		return this.betTime;
	}
    @Override
	public String toString() {
		return new ToStringBuilder(this)
		.append("UpdateTime",getUpdateTime())		
		.append("FundTime",getFundTime())		
		.append("BetTime",getBetTime())		
		.append("Id",getId())		
			.toString();
	}
    @Override
	public int hashCode() {
		return new HashCodeBuilder()
		.append(getUpdateTime())
		.append(getFundTime())
		.append(getBetTime())
		.append(getId())
			.toHashCode();
	}
    @Override
	public boolean equals(Object obj) {
		if(obj instanceof ActiveUserMigrate == false) return false;
		if(this == obj) return true;
		ActiveUserMigrate other = (ActiveUserMigrate)obj;
		return new EqualsBuilder()
		.append(getUpdateTime(),other.getUpdateTime())

		.append(getFundTime(),other.getFundTime())

		.append(getBetTime(),other.getBetTime())

		.append(getId(),other.getId())

			.isEquals();
	}
}

