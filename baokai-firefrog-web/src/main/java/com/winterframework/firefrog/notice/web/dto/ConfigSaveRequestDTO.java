package com.winterframework.firefrog.notice.web.dto;

import javax.validation.constraints.NotNull;

public class ConfigSaveRequestDTO<T> {
	@NotNull
	private String module;
	@NotNull
	private String function;
	@NotNull
	private T val;

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

	public T getVal() {
		return val;
	}

	public void setVal(T val) {
		this.val = val;
	}

	

}
