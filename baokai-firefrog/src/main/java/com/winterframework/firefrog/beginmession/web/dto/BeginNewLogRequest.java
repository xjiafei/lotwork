package com.winterframework.firefrog.beginmession.web.dto;

import java.io.Serializable;

public class BeginNewLogRequest implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6737401802994077816L;

	private Long type;

	public Long getType() {
		return type;
	}

	public void setType(Long type) {
		this.type = type;
	}
	
	
}
