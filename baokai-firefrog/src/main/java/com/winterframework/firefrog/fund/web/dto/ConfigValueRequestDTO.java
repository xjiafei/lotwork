package com.winterframework.firefrog.fund.web.dto;

import javax.validation.constraints.NotNull;

public class ConfigValueRequestDTO {
	@NotNull
	private String module;
	@NotNull
	private String function;

	public String getModule() {
		return module;
	}

	public void setModule(String module) {
		this.module = module;
	}

	public String getFunction() {
		return function;
	}

	public void setFunction(String function) {
		this.function = function;
	}

	

}
