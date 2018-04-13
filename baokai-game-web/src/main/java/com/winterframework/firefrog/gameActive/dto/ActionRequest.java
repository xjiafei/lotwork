package com.winterframework.firefrog.gameActive.dto;

import java.io.Serializable;

public class ActionRequest implements Serializable{

	private String param;
	private String ip;
	
	public String getParam() {
		return param;
	}
	public void setParam(String param) {
		this.param = param;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
}
