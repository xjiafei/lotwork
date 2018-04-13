package com.winterframework.firefrog.notice.web.dto;

import java.io.Serializable;

public class NoticeMsgDetailRequest implements Serializable {
	
	private static final long serialVersionUID = -5047448293166970693L;
	
	private Long id;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

}
