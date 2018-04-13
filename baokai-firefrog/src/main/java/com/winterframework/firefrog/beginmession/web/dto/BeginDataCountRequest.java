package com.winterframework.firefrog.beginmession.web.dto;

import java.io.Serializable;

public class BeginDataCountRequest implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5390318785289655827L;

	private String startTime;
	
	private String endTime;

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

}
