package com.winterframework.firefrog.phone.web.cotroller.dto;

import java.io.Serializable;

public class CardBindingConfirmResponse implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2293313283285840816L;

	private String status;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}
