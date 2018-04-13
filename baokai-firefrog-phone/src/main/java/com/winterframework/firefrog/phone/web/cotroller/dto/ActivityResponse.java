package com.winterframework.firefrog.phone.web.cotroller.dto;

import java.io.Serializable;

public class ActivityResponse implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3687064693754764602L;

	private String status;
	
	private Long prize;
	
	private Long display;

	public Long getDisplay() {
		return display;
	}

	public void setDisplay(Long display) {
		this.display = display;
	}

	public Long getPrize() {
		return prize;
	}

	public void setPrize(Long prize) {
		this.prize = prize;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
}
