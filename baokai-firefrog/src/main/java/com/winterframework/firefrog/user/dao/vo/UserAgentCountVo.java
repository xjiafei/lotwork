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


public class UserAgentCountVo extends BaseEntity {
	
	private static final long serialVersionUID = 4024343544012691541L;
	//alias
	public static final String TABLE_ALIAS = "UserAgentCount";
	public static final String ALIAS_DAY = "20121212";
	public static final String ALIAS_TIME = "1-24";
	public static final String ALIAS_WITH_DRAW = "提现";
	public static final String ALIAS_CHARGE = "充值";
	public static final String ALIAS_BET = "投注";
	public static final String ALIAS_REWARD = "返点";
	public static final String ALIAS_NEW_USER = "新增用户";
	public static final String ALIAS_USER_ID = "userId";
	
	//date formats
	public static final String FORMAT_DAY = DATE_TIME_FORMAT;
	
	//columns START
	private Date day;
	private Long time;
	private Long withDraw = 0L;
	private Long charge = 0L;
	private Long bet = 0L;
	private Long reward = 0L;
	private Long newUser = 0L;
	private Long userId;
	//columns END

	public UserAgentCountVo(){
	}

	public UserAgentCountVo(
		Long id
	){
		this.id = id;
	}

	public String getDayString() {
		return date2String(getDay(), FORMAT_DAY);
	}
	public void setDayString(String value) {
		setDay(string2Date(value, FORMAT_DAY,Date.class));
	}
	
	public void setDay(Date value) {
		this.day = value;
	}
	
	public Date getDay() {
		return this.day;
	}
	public void setTime(Long value) {
		this.time = value;
	}
	
	public Long getTime() {
		return this.time;
	}
	public void setWithDraw(Long value) {
		this.withDraw = value;
	}
	
	public Long getWithDraw() {
		return this.withDraw;
	}
	public void setCharge(Long value) {
		this.charge = value;
	}
	
	public Long getCharge() {
		return this.charge;
	}
	public void setBet(Long value) {
		this.bet = value;
	}
	
	public Long getBet() {
		return this.bet;
	}
	public void setReward(Long value) {
		this.reward = value;
	}
	
	public Long getReward() {
		return this.reward;
	}
	public void setNewUser(Long value) {
		this.newUser = value;
	}
	
	public Long getNewUser() {
		return this.newUser;
	}
	public void setUserId(Long value) {
		this.userId = value;
	}
	
	public Long getUserId() {
		return this.userId;
	}
}

