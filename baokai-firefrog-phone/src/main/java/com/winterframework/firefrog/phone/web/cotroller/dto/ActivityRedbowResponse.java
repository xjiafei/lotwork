package com.winterframework.firefrog.phone.web.cotroller.dto;

import java.io.Serializable;

public class ActivityRedbowResponse implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4025270031667047333L;

	private Long status;
	
	private String prize;
	
	private Long display; 

	public Long getDisplay() {
		return display;
	}

	public void setDisplay(Long display) {
		this.display = display;
	}

	public String getPrize() {
		return prize;
	}

	public void setPrize(String prize) {
		this.prize = prize;
	}

	public Long getStatus() {
		return status;
	}

	public void setStatus(Long status) {
		this.status = status;
	}
	
}
