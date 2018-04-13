package com.winterframework.firefrog.phone.web.cotroller.dto;

import java.io.Serializable;

public class CheckSecpassResponse implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7831371403500631532L;

	private String status;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
}
