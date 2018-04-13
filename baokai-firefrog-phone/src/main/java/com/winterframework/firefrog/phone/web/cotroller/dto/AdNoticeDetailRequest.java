package com.winterframework.firefrog.phone.web.cotroller.dto;

import java.io.Serializable;

public class AdNoticeDetailRequest implements Serializable{
	
	private static final long serialVersionUID = 5194764709982602448L;
	private Long id;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

}
