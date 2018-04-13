/**
* Copyright (c) 2005-2012 winterframework.com
* Licensed under the Apache License, Version 2.0 (the "License");
*/
package com.winterframework.firefrog.fund.entity;

import java.util.Date;

/**
 * @author cms group
 * @version 1.0
 * @since 1.0
 */

public class FundUserRemark {
	//columns START
	private Long id;
	private Long userId;
	private String remark;
	private Date gmtCreated;
	private Date gmtModified;
	private Date gmtAutounlocked;
	private Date gmtCansetremark;
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

	//columns END

	public FundUserRemark() {
	}

	public FundUserRemark(Long id) {
		this.setId(id);
	}

	public void setUserId(Long value) {
		this.userId = value;
	}

	public Long getUserId() {
		return this.userId;
	}

	public void setRemark(String value) {
		this.remark = value;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setGmtAutounlocked(Date value) {
		this.gmtAutounlocked = value;
	}

	public Date getGmtAutounlocked() {
		return this.gmtAutounlocked;
	}

	public void setGmtCansetremark(Date value) {
		this.gmtCansetremark = value;
	}

	public Date getGmtCansetremark() {
		return this.gmtCansetremark;
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

	public Date getGmtModified() {
		return gmtModified;
	}

	public void setGmtModified(Date gmtModified) {
		this.gmtModified = gmtModified;
	}
}
