package com.winterframework.firefrog.phone.web.cotroller.dto;

import java.io.Serializable;

public class CheckSecpassRequest implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6930262828665475699L;
	private String secpass;//	资金密码

	public String getSecpass() {
		return secpass;
	}

	public void setSecpass(String secpass) {
		this.secpass = secpass;
	}

}
