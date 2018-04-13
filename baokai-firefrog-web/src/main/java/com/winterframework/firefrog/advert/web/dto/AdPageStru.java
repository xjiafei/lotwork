/**
* Copyright (c) 2005-2012 winterframework.com
* Licensed under the Apache License, Version 2.0 (the "License");
*/
package com.winterframework.firefrog.advert.web.dto;


/**
 * @author cms group
 * @version 1.0
 * @since 1.0
 */

public class AdPageStru {

	//alias
	public static final String TABLE_ALIAS = "AdPage";
	public static final String ALIAS_NAME = "名字";
	public static final String ALIAS_MEMO = "描述";

	//date formats

	//columns START
	private String name;
	private String memo;

	//columns END

	public AdPageStru() {
	}


	public void setName(String value) {
		this.name = value;
	}

	public String getName() {
		return this.name;
	}

	public void setMemo(String value) {
		this.memo = value;
	}

	public String getMemo() {
		return this.memo;
	}
}
