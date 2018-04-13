package com.winterframework.firefrog.phone.web.cotroller.dto;

import java.io.Serializable;

public class NoticeListRequest implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2325570145919593166L;
	private Long chanId;

	public Long getChanId() {
		return chanId;
	}

	public void setChanId(Long chanId) {
		this.chanId = chanId;
	}
	
}
