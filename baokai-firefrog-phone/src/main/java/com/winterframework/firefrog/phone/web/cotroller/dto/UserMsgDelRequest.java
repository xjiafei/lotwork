package com.winterframework.firefrog.phone.web.cotroller.dto;

import java.io.Serializable;

public class UserMsgDelRequest implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4032522510389379451L;
	private String mid;

	public String getMid() {
		return mid;
	}

	public void setMid(String mid) {
		this.mid = mid;
	}

}
