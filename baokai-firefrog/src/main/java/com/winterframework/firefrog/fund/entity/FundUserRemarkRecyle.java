/**
* Copyright (c) 2005-2012 winterframework.com
* Licensed under the Apache License, Version 2.0 (the "License");
*/
package com.winterframework.firefrog.fund.entity;

import java.util.Date;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.winterframework.modules.web.jsonresult.FirefrogDateDeSerializer;
import com.winterframework.modules.web.jsonresult.FirefrogDateSerializer;
import com.winterframework.orm.dal.ibatis3.BaseEntity;

public class FundUserRemarkRecyle extends BaseEntity{

	//columns START
	private String remark;
	private Long userId;

	//columns END
	/** 
	* 账号
	*/
	private String account;
	/** 
	*用户类型 0:非vip用户，大于0：vip
	*/
	private Integer vipLvl;
	/** 
	*用户状态 是否冻结 1：冻结 0：未冻结
	*/
	private Integer isFreeze;
	/** 
	* 所属总代  /abc/dd/aa/ abc为总代用户
	*/
	private String userChain;
	@JsonSerialize(using = FirefrogDateSerializer.class)
	@JsonDeserialize(using = FirefrogDateDeSerializer.class)
	private Date bindStatTime;

	public Date getBindStatTime() {
		return bindStatTime;
	}

	public void setBindStatTime(Date bindStatTime) {
		this.bindStatTime = bindStatTime;
	}

	public Date getBindEndTime() {
		return bindEndTime;
	}

	public void setBindEndTime(Date bindEndTime) {
		this.bindEndTime = bindEndTime;
	}

	@JsonSerialize(using = FirefrogDateSerializer.class)
	@JsonDeserialize(using = FirefrogDateDeSerializer.class)
	private Date bindEndTime;

	public FundUserRemarkRecyle() {
	}

	public FundUserRemarkRecyle(Long id) {
		this.setId(id);
	}

	public void setRemark(String value) {
		this.remark = value;
	}

	public String getRemark() {
		return this.remark;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public Integer getVipLvl() {
		return vipLvl;
	}

	public void setVipLvl(Integer vipLvl) {
		this.vipLvl = vipLvl;
	}

	public Integer getIsFreeze() {
		return isFreeze;
	}

	public void setIsFreeze(Integer isFreeze) {
		this.isFreeze = isFreeze;
	}

	public String getUserChain() {
		return userChain;
	}

	public void setUserChain(String userChain) {
		this.userChain = userChain;
	}

	public Date getGmtCreated() {
		return gmtCreated;
	}

	public void setGmtCreated(Date gmtCreated) {
		this.gmtCreated = gmtCreated;
	}
}
