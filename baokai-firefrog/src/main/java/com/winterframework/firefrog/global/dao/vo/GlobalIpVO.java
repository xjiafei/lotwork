 /**
 * Copyright (c) 2005-2012 winterframework.com
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
 package com.winterframework.firefrog.global.dao.vo;

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


public class GlobalIpVO extends BaseEntity {
	
	private static final long serialVersionUID = -2516354907732919178L;
	//alias
	public static final String TABLE_ALIAS = "GlobalIp";
	public static final String ALIAS_AREA = "地址(-)";
	public static final String ALIAS_IP = "ip(可*)";
	public static final String ALIAS_EFFECT_TIME = "生效日期";
	public static final String ALIAS_EXPIRE_TIE = "实效日期";
	public static final String ALIAS_OPERATOR = "操作者";
	public static final String ALIAS_TYPE = "0:黑名单  1：白名单";
	
	//date formats
	
	//columns START
	private String area;
	private String ip;
	private Date effectTime;
	private Date expireTime;
	private String operator;
	private Long type;
	//columns END

	public GlobalIpVO(){
	}

	public GlobalIpVO(
		Long id
	){
		this.id = id;
	}

	public void setArea(String value) {
		this.area = value;
	}
	
	public String getArea() {
		return this.area;
	}
	public void setIp(String value) {
		this.ip = value;
	}
	
	public String getIp() {
		return this.ip;
	}
	public void setEffectTime(Date value) {
		this.effectTime = value;
	}
	
	public Date getEffectTime() {
		return this.effectTime;
	}
	public void setExpireTime(Date value) {
		this.expireTime = value;
	}
	
	public Date getExpireTime() {
		return this.expireTime;
	}
	public void setOperator(String value) {
		this.operator = value;
	}
	
	public String getOperator() {
		return this.operator;
	}
	public void setType(Long value) {
		this.type = value;
	}
	
	public Long getType() {
		return this.type;
	}
    @Override
	public String toString() {
		return new ToStringBuilder(this)
		.append("Id",getId())		
		.append("Area",getArea())		
		.append("Ip",getIp())		
		.append("EffectTime",getEffectTime())		
		.append("ExpireTie",getExpireTime())		
		.append("Operator",getOperator())		
		.append("Type",getType())		
			.toString();
	}
    @Override
	public int hashCode() {
		return new HashCodeBuilder()
		.append(getId())
		.append(getArea())
		.append(getIp())
		.append(getEffectTime())
		.append(getExpireTime())
		.append(getOperator())
		.append(getType())
			.toHashCode();
	}
    @Override
	public boolean equals(Object obj) {
		if(obj instanceof GlobalIpVO == false) return false;
		if(this == obj) return true;
		GlobalIpVO other = (GlobalIpVO)obj;
		return new EqualsBuilder()
		.append(getId(),other.getId())

		.append(getArea(),other.getArea())

		.append(getIp(),other.getIp())

		.append(getEffectTime(),other.getEffectTime())

		.append(getExpireTime(),other.getExpireTime())

		.append(getOperator(),other.getOperator())

		.append(getType(),other.getType())

			.isEquals();
	}
}

