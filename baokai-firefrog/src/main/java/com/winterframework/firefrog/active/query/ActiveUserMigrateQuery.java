 /**
 * Copyright (c) 2005-2012 winterframework.com
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
 package com.winterframework.firefrog.active.query;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * @author cms group
 * @version 1.0
 * @since 1.0
 */


public class ActiveUserMigrateQuery implements Serializable {
	

	/** updateTime */
	private java.util.Date updateTime;
	/** fundTime */
	private java.util.Date fundTime;
	/** betTime */
	private java.util.Date betTime;
	/** id */
	private java.lang.Long id;

	public java.util.Date getUpdateTime() {
		return this.updateTime;
	}
	
	public void setUpdateTime(java.util.Date value) {
		this.updateTime = value;
	}
	
	public java.util.Date getFundTime() {
		return this.fundTime;
	}
	
	public void setFundTime(java.util.Date value) {
		this.fundTime = value;
	}
	
	public java.util.Date getBetTime() {
		return this.betTime;
	}
	
	public void setBetTime(java.util.Date value) {
		this.betTime = value;
	}
	
	public java.lang.Long getId() {
		return this.id;
	}
	
	public void setId(java.lang.Long value) {
		this.id = value;
	}
	

	public String toString() {
		return ToStringBuilder.reflectionToString(this,ToStringStyle.MULTI_LINE_STYLE);
	}
	
}

