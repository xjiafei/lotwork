package com.winterframework.firefrog.phone.web.cotroller.dto;

import java.io.Serializable;

public class UserMsgDetailRequest implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4383132594625425540L;
	private String mid;

	public String getMid() {
		return mid;
	}

	public void setMid(String mid) {
		this.mid = mid;
	}
	
}
