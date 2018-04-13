package com.winterframework.firefrog.phone.web.cotroller.dto;

import java.io.Serializable;
import java.util.List;

public class InitMissionResponse implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1501643144951706636L;
	
	private Long isSuccess;
	
	private String message;
	
	private Object data;

	public Long getIsSuccess() {
		return isSuccess;
	}

	public void setIsSuccess(Long isSuccess) {
		this.isSuccess = isSuccess;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	

	
	
}
