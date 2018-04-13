 /**
 * Copyright (c) 2005-2012 winterframework.com
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.winterframework.firefrog.game.entity;

import java.io.Serializable;





/**
 * @author cms group
 * @version 1.0
 * @since 1.0
 */


public class GameLockAppraise implements Serializable {

	/** 
	* @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么) 
	*/ 
	private static final long serialVersionUID = -232312321441L;
	private Long gameId;
	private String title;
	private String templete;
	private Long status;
	private Long minVal;
	private String changeStruc;
	private Long currUse;
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
}

