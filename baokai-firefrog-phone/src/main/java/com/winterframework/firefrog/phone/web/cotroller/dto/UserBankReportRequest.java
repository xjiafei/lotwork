package com.winterframework.firefrog.phone.web.cotroller.dto;

import java.io.Serializable;

public class UserBankReportRequest implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3847204264073998687L;
	private String username;//	查询帐变得用户名
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}

}
