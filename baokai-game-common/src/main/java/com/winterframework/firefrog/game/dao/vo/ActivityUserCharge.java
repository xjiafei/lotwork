 /**
 * Copyright (c) 2005-2012 winterframework.com
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
 package com.winterframework.firefrog.game.dao.vo;

import com.winterframework.orm.dal.ibatis3.BaseEntity;

/**
 * @author cms group
 * @version 1.0
 * @since 1.0
 */


public class ActivityUserCharge extends BaseEntity {
	
	private static final long serialVersionUID = 5090778315708270353L;
	//alias
	public static final String TABLE_ALIAS = "活动用户充值投注金额记录表";
	public static final String ALIAS_TYPE = "0 投注金额  1 充值金额";
	public static final String ALIAS_USER_ID = "userId";
	public static final String ALIAS_OLD_SYS_TOTAL_AMOUNT = "3.0金额 ";
	public static final String ALIAS_NEW_SYS_TOTAL_AMOUNT = "4.0 金额";
	public static final String ALIAS_MONEY_DATE = "金额时间  格式 yyyy-mm-dd";
	
	//date formats
	
	//columns START
	private Long type;
	private Long userId;
	private Long amount;
	private String moneyDate;
	private String source;
	//columns END

	public ActivityUserCharge(){
	}

	public ActivityUserCharge(
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
	public void setUserId(Long value) {
		this.userId = value;
	}
	
	public Long getUserId() {
		return this.userId;
	}

	public Long getAmount() {
		return amount;
	}

	public void setAmount(Long amount) {
		this.amount = amount;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public void setMoneyDate(String value) {
		this.moneyDate = value;
	}
	
	public String getMoneyDate() {
		return this.moneyDate;
	}
  
}

