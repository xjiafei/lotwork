package com.winterframework.firefrog.phone.web.cotroller.dto;

import java.io.Serializable;

public class ModifyLoginpassResponse implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5787368086384281997L;
	private String status ;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}
