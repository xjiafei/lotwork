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


public class AdTopicVO extends BaseEntity {
	
	private static final long serialVersionUID = -798530839359416783L;
	//alias
	public static final String TABLE_ALIAS = "AdTopic";
	public static final String ALIAS_CATE_ID = "类别id";
	public static final String ALIAS_TITLE = "标题";
	public static final String ALIAS_OPERATOR = "创建人";
	
	//date formats
	
	//columns START
	private Long cateId;
	private String title;
	private String urls;
	private String operator;
	//columns END

	public AdTopicVO(){
	}

	public AdTopicVO(
		Long id
	){
		this.id = id;
	}

	public void setCateId(Long value) {
		this.cateId = value;
	}
	
	public Long getCateId() {
		return this.cateId;
	}
	public void setTitle(String value) {
		this.title = value;
	}
	
	public String getTitle() {
		return this.title;
	}
	public void setUrls(String value) {
		this.urls = value;
	}
	
	public String getUrls() {
		return this.urls;
	}
	public void setOperator(String value) {
		this.operator = value;
	}
	
	public String getOperator() {
		return this.operator;
	}
    @Override
	public String toString() {
		return new ToStringBuilder(this)
		.append("Id",getId())		
		.append("CateId",getCateId())		
		.append("Title",getTitle())		
		.append("Urls",getUrls())		
		.append("Operator",getOperator())		
		.append("GmtCreated",getGmtCreated())		
			.toString();
	}
    @Override
	public int hashCode() {
		return new HashCodeBuilder()
		.append(getId())
		.append(getCateId())
		.append(getTitle())
		.append(getUrls())
		.append(getOperator())
		.append(getGmtCreated())
			.toHashCode();
	}
    @Override
	public boolean equals(Object obj) {
		if(obj instanceof AdTopicVO == false) return false;
		if(this == obj) return true;
		AdTopicVO other = (AdTopicVO)obj;
		return new EqualsBuilder()
		.append(getId(),other.getId())

		.append(getCateId(),other.getCateId())

		.append(getTitle(),other.getTitle())

		.append(getUrls(),other.getUrls())

		.append(getOperator(),other.getOperator())

		.append(getGmtCreated(),other.getGmtCreated())

			.isEquals();
	}
}

