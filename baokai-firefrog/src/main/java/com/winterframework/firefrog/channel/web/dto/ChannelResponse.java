package com.winterframework.firefrog.channel.web.dto;

import java.io.Serializable;

public class ChannelResponse implements Serializable{

	private String url;
	private Long status;
	
	
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public Long getStatus() {
		return status;
	}
	public void setStatus(Long status) {
		this.status = status;
	}
}
