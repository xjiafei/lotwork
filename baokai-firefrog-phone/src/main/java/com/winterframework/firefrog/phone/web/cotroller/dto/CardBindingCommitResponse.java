package com.winterframework.firefrog.phone.web.cotroller.dto;

import java.io.Serializable;

public class CardBindingCommitResponse implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1290797008500911416L;
	
	private String status ;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
