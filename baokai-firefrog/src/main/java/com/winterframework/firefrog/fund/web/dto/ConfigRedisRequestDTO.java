package com.winterframework.firefrog.fund.web.dto;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

public class ConfigRedisRequestDTO implements Serializable {

	private static final long serialVersionUID = 7269477303867629786L;
	@NotNull
	private String module;
	@NotNull
	private String key;
	@NotNull
	private String Function;

	public String getModule() {
		return module;
	}

	public void setModule(String module) {
		this.module = module;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getFunction() {
		return Function;
	}

	public void setFunction(String function) {
		Function = function;
	}

}
