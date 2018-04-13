 /**
 * Copyright (c) 2005-2012 winterframework.com
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
 package com.winterframework.firefrog.acl.dao.vo;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import com.winterframework.orm.dal.ibatis3.BaseEntity;

/**
 * @author cms group
 * @version 1.0
 * @since 1.0
 */


public class AclOperateLogVO extends BaseEntity {
	
	private static final long serialVersionUID = -3325223611778469004L;
	//alias
	public static final String TABLE_ALIAS = "AclOperateLog";
	public static final String ALIAS_ACCOUNT = "账号";
	public static final String ALIAS_IP = "ip";
	public static final String ALIAS_URL = "访问url 如果是按钮则用 ";
	public static final String ALIAS_ACTION = "acl控制点名称";
	public static final String ALIAS_DETAIL = "详情 成功 失败  无权限";
	
	//date formats
	
	//columns START
	private String account;
	private String ip;
	private String url;
	private String action;
	private Long detail;
	//columns END

	public AclOperateLogVO(){
	}

	public AclOperateLogVO(
		Long id
	){
		this.id = id;
	}

	public void setAccount(String value) {
		this.account = value;
	}
	
	public String getAccount() {
		return this.account;
	}
	public void setIp(String value) {
		this.ip = value;
	}
	
	public String getIp() {
		return this.ip;
	}
	public void setUrl(String value) {
		this.url = value;
	}
	
	public String getUrl() {
		return this.url;
	}
	public void setAction(String value) {
		this.action = value;
	}
	
	public String getAction() {
		return this.action;
	}
	public void setDetail(Long value) {
		this.detail = value;
	}
	
	public Long getDetail() {
		return this.detail;
	}
    @Override
	public String toString() {
		return new ToStringBuilder(this)
		.append("Id",getId())		
		.append("Account",getAccount())		
		.append("Ip",getIp())		
		.append("Url",getUrl())		
		.append("Action",getAction())		
		.append("Detail",getDetail())		
			.toString();
	}
    @Override
	public int hashCode() {
		return new HashCodeBuilder()
		.append(getId())
		.append(getAccount())
		.append(getIp())
		.append(getUrl())
		.append(getAction())
		.append(getDetail())
			.toHashCode();
	}
    @Override
	public boolean equals(Object obj) {
		if(obj instanceof AclOperateLogVO == false) return false;
		if(this == obj) return true;
		AclOperateLogVO other = (AclOperateLogVO)obj;
		return new EqualsBuilder()
		.append(getId(),other.getId())

		.append(getAccount(),other.getAccount())

		.append(getIp(),other.getIp())

		.append(getUrl(),other.getUrl())

		.append(getAction(),other.getAction())

		.append(getDetail(),other.getDetail())

			.isEquals();
	}
}

