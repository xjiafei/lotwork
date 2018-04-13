package com.winterframework.firefrog.phone.web.cotroller.dto;

import java.io.Serializable;

public class SafeQuestSetResponse implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2728516562574410531L;
	private String status;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
