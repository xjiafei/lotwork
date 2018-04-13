/**
 * Copyright (c) 2005-2012 winterframework.com
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.winterframework.firefrog.active.web.dto;

import java.io.Serializable;

/**
 * @author cms group
 * @version 1.0
 * @since 1.0
 */

public class UserPtRegister implements Serializable {

	// columns START
	private Long userid;
	private Long parentid;
	private Long type;
	
	
	public Long getUserid() {
		return userid;
	}
	public void setUserid(Long user_id) {
		this.userid = user_id;
	}
	public Long getParentid() {
		return parentid;
	}
	public void setParentid(Long parent_id) {
		this.parentid = parent_id;
	}
	public Long getType() {
		return type;
	}
	public void setType(Long type) {
		this.type = type;
	}
	
	
	
}
