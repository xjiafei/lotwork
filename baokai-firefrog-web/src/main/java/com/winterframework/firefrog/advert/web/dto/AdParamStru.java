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

public class AdParamStru {

	//alias
	public static final String TABLE_ALIAS = "AdParam";
	public static final String ALIAS_PAGE_ID = "pageId";
	public static final String ALIAS_NAME = "name";
	public static final String ALIAS_POSITION = "位置描述";
	public static final String ALIAS_WIDTH = "宽度 -1 表示无限制";
	public static final String ALIAS_HEIGHT = "长度 -1表示无限制";

	//date formats
	
	
	//columns START
	private Long id;
	private String pageMemo;
	
	private String name;
	private String position;
	private Long width;
	private Long height;

	//columns END

	public AdParamStru() {
	}
	public void setName(String value) {
		this.name = value;
	}

	public String getName() {
		return this.name;
	}

	public void setPosition(String value) {
		this.position = value;
	}

	public String getPosition() {
		return this.position;
	}

	public void setWidth(Long value) {
		this.width = value;
	}

	public Long getWidth() {
		return this.width;
	}

	public void setHeight(Long value) {
		this.height = value;
	}

	public Long getHeight() {
		return this.height;
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}
	public String getPageMemo() {
		return pageMemo;
	}
	public void setPageMemo(String pageMemo) {
		this.pageMemo = pageMemo;
	}
}
