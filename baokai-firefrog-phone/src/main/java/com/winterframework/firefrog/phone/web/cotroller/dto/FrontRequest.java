package com.winterframework.firefrog.phone.web.cotroller.dto;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

public class FrontRequest implements Serializable {
	
	private static final long serialVersionUID = 6016473873552557827L;
	@NotNull
	private String username;//	用户名
	private String loginpass;//	加密后的密码信息
	private String loginpassSource; //	加密后的密码信息
	private Long loginIp;
	/**
	 * 手機渠道來源
	 * 
	 */
	private Integer device;;
	
	public void setLoginIp(Long loginIp) {
		this.loginIp = loginIp;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getLoginpass() {
		return loginpass;
	}
	public void setLoginpass(String loginpass) {
		this.loginpass = loginpass;
	}
	public String getLoginpassSource() {
		return loginpassSource;
	}
	public void setLoginpassSource(String loginpassSource) {
		this.loginpassSource = loginpassSource;
	}
	public Long getLoginIp() {
		return loginIp;
	}
	public Integer getDevice() {
		return device;
	}
	public void setDevice(Integer device) {
		this.device = device;
	}
	
}
