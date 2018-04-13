/**
* Copyright (c) 2005-2012 winterframework.com
* Licensed under the Apache License, Version 2.0 (the "License");
*/
package com.winterframework.firefrog.advert.entity;


/**
 * @author cms group
 * @version 1.0
 * @since 1.0
 */

public class AdPage {

	//columns START
	private Long id;
	private String name;
	private String memo;

	//columns END

	public AdPage() {
	}

	public AdPage(Long id) {
		this.id = id;
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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
}
