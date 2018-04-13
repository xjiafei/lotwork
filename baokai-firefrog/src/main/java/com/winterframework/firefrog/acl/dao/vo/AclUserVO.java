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


public class AclUserVO extends BaseEntity {
	
	private static final long serialVersionUID = -2916412632973959227L;
	//alias
	public static final String TABLE_ALIAS = "AclUser";
	public static final String ALIAS_ACCOUNT = "用户id";
	public static final String ALIAS_GROUP_ID = "组id";
	public static final String ALIAS_BIND_PASSWD = "1 绑定 0 未绑定";
	public static final String ALIAS_CELLPHONE = "手机号码";
	public static final String ALIAS_TELEPHONE = "座机号码";
	public static final String ALIAS_DEP = "组织架构";
	public static final String ALIAS_STATUS = "-1 待删除  0 正常  1锁定";
	public static final String ALIAS_PASSWD = "passwd";
	public static final String ALIAS_EMAIL = "email";
	
	//date formats
	
	//columns START
	private String account;
	private Long groupId;
	private String bindPasswd;
	private String cellphone;
	private String telephone;
	private String dep;
	private Long status;
	private String passwd;
	private String email;
	private String groupName;
	private String createder;
	private String modifieder;
	private String bindCard;
	private Long lastIp;
	//columns END

	public AclUserVO(){
	}

	public String getBindCard() {
		return bindCard;
	}

	public void setBindCard(String bindCard) {
		this.bindCard = bindCard;
	}

	public AclUserVO(
		Long id
	){
		this.id = id;
	}

	public Long getLastIp() {
		return lastIp;
	}

	public void setLastIp(Long lastIp) {
		this.lastIp = lastIp;
	}

	public void setAccount(String value) {
		this.account = value;
	}
	
	public String getAccount() {
		return this.account;
	}
	public void setGroupId(Long value) {
		this.groupId = value;
	}
	
	public Long getGroupId() {
		return this.groupId;
	}
	public void setBindPasswd(String value) {
		this.bindPasswd = value;
	}
	
	public String getBindPasswd() {
		return this.bindPasswd;
	}
	public void setCellphone(String value) {
		this.cellphone = value;
	}
	
	public String getCellphone() {
		return this.cellphone;
	}
	public void setTelephone(String value) {
		this.telephone = value;
	}
	
	public String getTelephone() {
		return this.telephone;
	}
	public void setDep(String value) {
		this.dep = value;
	}
	
	public String getDep() {
		return this.dep;
	}
	public void setStatus(Long value) {
		this.status = value;
	}
	
	public Long getStatus() {
		return this.status;
	}
	public void setPasswd(String value) {
		this.passwd = value;
	}
	
	public String getPasswd() {
		return this.passwd;
	}
	public void setEmail(String value) {
		this.email = value;
	}
	
	public String getEmail() {
		return this.email;
	}
    @Override
	public String toString() {
		return new ToStringBuilder(this)
		.append("Id",getId())		
		.append("Account",getAccount())		
		.append("GroupId",getGroupId())		
		.append("BindPasswd",getBindPasswd())		
		.append("Cellphone",getCellphone())		
		.append("Telephone",getTelephone())		
		.append("Dep",getDep())		
		.append("Status",getStatus())		
		.append("Passwd",getPasswd())		
		.append("Email",getEmail())		
			.toString();
	}
    @Override
	public int hashCode() {
		return new HashCodeBuilder()
		.append(getId())
		.append(getAccount())
		.append(getGroupId())
		.append(getBindPasswd())
		.append(getCellphone())
		.append(getTelephone())
		.append(getDep())
		.append(getStatus())
		.append(getPasswd())
		.append(getEmail())
			.toHashCode();
	}
    @Override
	public boolean equals(Object obj) {
		if(obj instanceof AclUserVO == false) return false;
		if(this == obj) return true;
		AclUserVO other = (AclUserVO)obj;
		return new EqualsBuilder()
		.append(getId(),other.getId())

		.append(getAccount(),other.getAccount())

		.append(getGroupId(),other.getGroupId())

		.append(getBindPasswd(),other.getBindPasswd())

		.append(getCellphone(),other.getCellphone())

		.append(getTelephone(),other.getTelephone())

		.append(getDep(),other.getDep())

		.append(getStatus(),other.getStatus())

		.append(getPasswd(),other.getPasswd())

		.append(getEmail(),other.getEmail())

			.isEquals();
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getCreateder() {
		return createder;
	}

	public void setCreateder(String createder) {
		this.createder = createder;
	}

	public String getModifieder() {
		return modifieder;
	}

	public void setModifieder(String modifieder) {
		this.modifieder = modifieder;
	}

}

