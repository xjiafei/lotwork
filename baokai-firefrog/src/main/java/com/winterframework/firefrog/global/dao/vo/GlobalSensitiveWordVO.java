 /**
 * Copyright (c) 2005-2012 winterframework.com
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
 package com.winterframework.firefrog.global.dao.vo;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import com.winterframework.orm.dal.ibatis3.BaseEntity;

/**
 * @author cms group
 * @version 1.0
 * @since 1.0
 */


public class GlobalSensitiveWordVO extends BaseEntity {
	
	private static final long serialVersionUID = 7667279284937069101L;
	//alias
	public static final String TABLE_ALIAS = "GlobalSensitiveWord";
	public static final String ALIAS_TYPE = "1)注册页";
	public static final String ALIAS_WORD = "敏感词";
	
	//date formats
	
	//columns START
	private Long type;
	private String word;
	//columns END

	public GlobalSensitiveWordVO(){
	}

	public GlobalSensitiveWordVO(
		Long id
	){
		this.id = id;
	}

	public void setType(Long value) {
		this.type = value;
	}
	
	public Long getType() {
		return this.type;
	}
	public void setWord(String value) {
		this.word = value;
	}
	
	public String getWord() {
		return this.word;
	}
    @Override
	public String toString() {
		return new ToStringBuilder(this)
		.append("Id",getId())		
		.append("Type",getType())		
		.append("Word",getWord())		
			.toString();
	}
    @Override
	public int hashCode() {
		return new HashCodeBuilder()
		.append(getId())
		.append(getType())
		.append(getWord())
			.toHashCode();
	}
    @Override
	public boolean equals(Object obj) {
		if(obj instanceof GlobalSensitiveWordVO == false) return false;
		if(this == obj) return true;
		GlobalSensitiveWordVO other = (GlobalSensitiveWordVO)obj;
		return new EqualsBuilder()
		.append(getId(),other.getId())

		.append(getType(),other.getType())

		.append(getWord(),other.getWord())

			.isEquals();
	}
}

