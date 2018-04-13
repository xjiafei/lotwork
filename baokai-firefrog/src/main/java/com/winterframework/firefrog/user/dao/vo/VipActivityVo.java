 /**
 * Copyright (c) 2005-2012 winterframework.com
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.winterframework.firefrog.user.dao.vo;
import java.util.Date;

import com.winterframework.orm.dal.ibatis3.BaseEntity;

/**
 * @author cms group
 * @version 1.0
 * @since 1.0
 */


public class VipActivityVo extends BaseEntity {
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 6121875996326644869L;
	//alias

	//columns START
	private String account;
	private Long month;
	private String source;
	private Date startTime;
	private Date endTime;
	private Boolean isRegister;
	private Integer registerCount;
	
	/**
	 * @return the account
	 */
	public String getAccount() {
		return account;
	}
	/**
	 * @param account the account to set
	 */
	public void setAccount(String account) {
		this.account = account;
	}
	/**
	 * @return the month
	 */
	public Long getMonth() {
		return month;
	}
	/**
	 * @param month the month to set
	 */
	public void setMonth(Long month) {
		this.month = month;
	}
	/**
	 * @return the source
	 */
	public String getSource() {
		return source;
	}
	/**
	 * @param source the source to set
	 */
	public void setSource(String source) {
		this.source = source;
	}
	/**
	 * @return the endTime
	 */
	public Date getEndTime() {
		return endTime;
	}
	/**
	 * @param endTime the endTime to set
	 */
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	/**
	 * @return the startTime
	 */
	public Date getStartTime() {
		return startTime;
	}
	/**
	 * @param startTime the startTime to set
	 */
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	/**
	 * @return the isRegister
	 */
	public Boolean getIsRegister() {
		return isRegister;
	}
	/**
	 * @param isRegister the isRegister to set
	 */
	public void setIsRegister(Boolean isRegister) {
		this.isRegister = isRegister;
	}
	/**
	 * @return the registerCount
	 */
	public Integer getRegisterCount() {
		return registerCount;
	}
	/**
	 * @param registerCount the registerCount to set
	 */
	public void setRegisterCount(Integer registerCount) {
		this.registerCount = registerCount;
	}

	//columns END
	


	
}

