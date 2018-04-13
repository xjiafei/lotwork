 /**
 * Copyright (c) 2005-2012 winterframework.com
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.winterframework.firefrog.game.dao.vo;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import com.winterframework.orm.dal.ibatis3.BaseEntity;



/**
 * @author cms group
 * @version 1.0
 * @since 1.0
 */


public class GameLockAppraise extends BaseEntity {
	
	//alias
	public static final String TABLE_ALIAS = "GameLockAppraise";
	public static final String ALIAS_GAME_ID = "游戏id";
	public static final String ALIAS_TITLE = "方案标题";
	public static final String ALIAS_TEMPLETE = "是哪个模板：a,b,c";
	public static final String ALIAS_STATUS = "1:待审核 2：审核通过 3：审核不通过 4：已发布";
	public static final String ALIAS_MIN_VAL = "最小值";
	public static final String ALIAS_CHANGE_STRUC = "[{from:1 to:20 rate:0},{from:20 to:40 rate:20%}]";
	public static final String ALIAS_CURR_USE = "currUse";
	
	//date formats
	
	//columns START
	private Long gameId;
	private String title;
	private String templete;
	private Long status;
	private Long minVal;
	private String changeStruc;
	private Long currUse;
	private String changeStrucProcess;
	//columns END

	public GameLockAppraise(){
	}

	public GameLockAppraise(
		Long id
	){
		this.id = id;
	}

	public void setGameId(Long value) {
		this.gameId = value;
	}
	
	public Long getGameId() {
		return this.gameId;
	}
	public void setTitle(String value) {
		this.title = value;
	}
	
	public String getTitle() {
		return this.title;
	}
	public void setTemplete(String value) {
		this.templete = value;
	}
	
	public String getTemplete() {
		return this.templete;
	}
	public void setStatus(Long value) {
		this.status = value;
	}
	
	public Long getStatus() {
		return this.status;
	}
	public void setMinVal(Long value) {
		this.minVal = value;
	}
	
	public Long getMinVal() {
		return this.minVal;
	}
	public void setChangeStruc(String value) {
		this.changeStruc = value;
	}
	
	public String getChangeStruc() {
		return this.changeStruc;
	}
	public void setCurrUse(Long value) {
		this.currUse = value;
	}
	
	public Long getCurrUse() {
		return this.currUse;
	}

	public String getChangeStrucProcess() {
		return changeStrucProcess;
	}

	public void setChangeStrucProcess(String changeStrucProcess) {
		this.changeStrucProcess = changeStrucProcess;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this)
		.append("Id",getId())		
		.append("GameId",getGameId())		
		.append("Title",getTitle())		
		.append("Templete",getTemplete())		
		.append("GmtModified",getGmtModified())		
		.append("GmtCreated",getGmtCreated())		
		.append("Status",getStatus())		
		.append("Modifier",getModifier())		
		.append("MinVal",getMinVal())		
		.append("ChangeStruc",getChangeStruc())		
		.append("CurrUse",getCurrUse())		
			.toString();
	}
    @Override
	public int hashCode() {
		return new HashCodeBuilder()
		.append(getId())
		.append(getGameId())
		.append(getTitle())
		.append(getTemplete())
		.append(getGmtModified())
		.append(getGmtCreated())
		.append(getStatus())
		.append(getModifier())
		.append(getMinVal())
		.append(getChangeStruc())
		.append(getCurrUse())
			.toHashCode();
	}
    @Override
	public boolean equals(Object obj) {
		if(obj instanceof GameLockAppraise == false) return false;
		if(this == obj) return true;
		GameLockAppraise other = (GameLockAppraise)obj;
		return new EqualsBuilder()
		.append(getId(),other.getId())

		.append(getGameId(),other.getGameId())

		.append(getTitle(),other.getTitle())

		.append(getTemplete(),other.getTemplete())

		.append(getGmtModified(),other.getGmtModified())

		.append(getGmtCreated(),other.getGmtCreated())

		.append(getStatus(),other.getStatus())

		.append(getModifier(),other.getModifier())

		.append(getMinVal(),other.getMinVal())

		.append(getChangeStruc(),other.getChangeStruc())

		.append(getCurrUse(),other.getCurrUse())

			.isEquals();
	}
}

