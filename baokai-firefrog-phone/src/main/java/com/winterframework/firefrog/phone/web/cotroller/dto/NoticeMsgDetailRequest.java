package com.winterframework.firefrog.phone.web.cotroller.dto;

import java.io.Serializable;

public class NoticeMsgDetailRequest implements Serializable {
	
	private static final long serialVersionUID = 858444713432283547L;
	private Long id;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
}
