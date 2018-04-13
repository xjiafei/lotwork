package com.winterframework.firefrog.global.web.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class RegisterLoginConfigDto {
	
	@NotNull
	@Min(0)
	private Long reregister;
	
	@NotNull
	@Min(0)
	private Long overTime;
	
	@NotNull
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
