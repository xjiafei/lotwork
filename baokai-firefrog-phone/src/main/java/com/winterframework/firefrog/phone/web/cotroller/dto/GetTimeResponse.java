package com.winterframework.firefrog.phone.web.cotroller.dto;

import java.io.Serializable;

public class GetTimeResponse implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5332411203599660553L;
	private String serverTime;

	public String getServerTime() {
		return serverTime;
	}

	public void setServerTime(String serverTime) {
		this.serverTime = serverTime;
	}
}
