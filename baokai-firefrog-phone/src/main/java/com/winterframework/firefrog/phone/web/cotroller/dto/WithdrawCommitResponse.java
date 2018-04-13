package com.winterframework.firefrog.phone.web.cotroller.dto;

import java.io.Serializable;

public class WithdrawCommitResponse implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2503010760015069501L;
	
	private String status;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}
