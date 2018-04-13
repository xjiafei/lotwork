package com.winterframework.firefrog.config.web.controller.dto;

public class DefaultConfig<T> extends Config{
	
	private T val;
	

	public T getVal() {
		return val;
	}

	public void setVal(T val) {
		this.val = val;
	}

	

}
