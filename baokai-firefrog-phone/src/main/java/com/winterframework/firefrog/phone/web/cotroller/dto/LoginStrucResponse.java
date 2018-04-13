package com.winterframework.firefrog.phone.web.cotroller.dto;

import java.io.Serializable;

public class LoginStrucResponse implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4077005421292132159L;
	private Long loginDate;
	private Long loginIp;
	private String loginAddress;

	public LoginStrucResponse() {

	}

	public Long getLoginDate() {
		return loginDate;
	}

	public void setLoginDate(Long loginDate) {
		this.loginDate = loginDate;
	}

	public Long getLoginIp() {
		return loginIp;
	}

	public void setLoginIp(Long loginIp) {
		this.loginIp = loginIp;
	}

	public String getLoginAddress() {
		return loginAddress;
	}

	public void setLoginAddress(String loginAddress) {
		this.loginAddress = loginAddress;
	}


}
