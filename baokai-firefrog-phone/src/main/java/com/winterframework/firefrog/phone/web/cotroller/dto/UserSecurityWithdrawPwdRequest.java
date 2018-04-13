package com.winterframework.firefrog.phone.web.cotroller.dto;

import java.io.Serializable;

public class UserSecurityWithdrawPwdRequest implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6524467460352672540L;
	private String withdrawPasswd;

	public String getWithdrawPasswd() {
		return withdrawPasswd;
	}

	public void setWithdrawPasswd(String withdrawPasswd) {
		this.withdrawPasswd = withdrawPasswd;
	}
	

}
