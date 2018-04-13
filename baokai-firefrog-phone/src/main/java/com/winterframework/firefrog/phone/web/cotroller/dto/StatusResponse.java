package com.winterframework.firefrog.phone.web.cotroller.dto;

import java.io.Serializable;

public class StatusResponse implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3687064693724764602L;

	private String status;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
}
