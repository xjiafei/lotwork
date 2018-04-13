package com.winterframework.firefrog.config.web.controller.dto;

public class RegisterLoginConfigDto {
	
	private Long reregister;
	
	private Long overTime;
	
	private Long multLogin;

	public Long getOverTime() {
		return overTime;
	}

	public void setOverTime(Long overTime) {
		this.overTime = overTime;
	}

	public Long getMultLogin() {
		return multLogin;
	}

	public void setMultLogin(Long multLogin) {
		this.multLogin = multLogin;
	}

	public Long getReregister() {
		return reregister;
	}

	public void setReregister(Long reregister) {
		this.reregister = reregister;
	}

}
