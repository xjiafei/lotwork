 /**
 * Copyright (c) 2005-2012 winterframework.com
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
 package com.winterframework.firefrog.advert.dao.vo;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import com.winterframework.orm.dal.ibatis3.BaseEntity;

/**
 * @author cms group
 * @version 1.0
 * @since 1.0
 */


public class AdTopicCategoryVO extends BaseEntity {
	
	private static final long serialVersionUID = -4337384335774945522L;
	//alias
	public static final String TABLE_ALIAS = "AdTopicCategory";
	public static final String ALIAS_NAME = "类别";
	
	//date formats
	
	//columns START
	private String name;
	//columns END

	public AdTopicCategoryVO(){
	}

	public AdTopicCategoryVO(
		Long id
	){
		this.id = id;
	}

	public void setName(String value) {
		this.name = value;
	}
	
	public String getName() {
		return this.name;
	}
    @Override
	public String toString() {
		return new ToStringBuilder(this)
		.append("Id",getId())		
		.append("Name",getName())		
			.toString();
	}
    @Override
	public int hashCode() {
		return new HashCodeBuilder()
		.append(getId())
		.append(getName())
			.toHashCode();
	}
    @Override
	public boolean equals(Object obj) {
		if(obj instanceof AdTopicCategoryVO == false) return false;
		if(this == obj) return true;
		AdTopicCategoryVO other = (AdTopicCategoryVO)obj;
		return new EqualsBuilder()
		.append(getId(),other.getId())

		.append(getName(),other.getName())

			.isEquals();
	}
}

