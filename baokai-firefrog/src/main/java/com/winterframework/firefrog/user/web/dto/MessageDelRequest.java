package com.winterframework.firefrog.user.web.dto;

import java.io.Serializable;

public class MessageDelRequest implements Serializable {

	private static final long serialVersionUID = 4972255829957121409L;
	
	private Long id;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

}
