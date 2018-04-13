/**
* Copyright (c) 2005-2012 winterframework.com
* Licensed under the Apache License, Version 2.0 (the "License");
*/
package com.winterframework.firefrog.fund.entity;


/**
 * @author cms group
 * @version 1.0
 * @since 1.0
 */

public class FundSoloRemarkSystem {

	//columns START
	private Long id;
	private String remark;

	//columns END

	public FundSoloRemarkSystem() {
	}

	public FundSoloRemarkSystem(Long id) {
		this.setId(id);
	}

	public void setRemark(String value) {
		this.remark = value;
	}

	public String getRemark() {
		return this.remark;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
}
