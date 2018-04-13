/**
* Copyright (c) 2005-2012 winterframework.com
* Licensed under the Apache License, Version 2.0 (the "License");
*/
package com.winterframework.firefrog.common.config.entity;

/** 
* @ClassName: Config  entity
* @Description: 
* @author david
* @date 2013-7-5 下午5:24:59 
*  
*/
public class ConfigEntity {

	private String module;
	private String key;
	private String value;
	private Long type;
	private String memo;
	private String defaultValue;
	private String function;

	public ConfigEntity() {
	}

	public void setModule(String value) {
		this.module = value;
	}

	public String getModule() {
		return this.module;
	}

	public void setKey(String value) {
		this.key = value;
	}

	public String getKey() {
		return this.key;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getValue() {
		return this.value;
	}

	public void setType(Long value) {
		this.type = value;
	}

	public Long getType() {
		return this.type;
	}

	public void setMemo(String value) {
		this.memo = value;
	}

	public String getMemo() {
		return this.memo;
	}

	public void setDefaultValue(String value) {
		this.defaultValue = value;
	}

	public String getDefaultValue() {
		return this.defaultValue;
	}

	public String getFunction() {
		return function;
	}

	public void setFunction(String function) {
		this.function = function;
	}
}
