package com.winterframework.firefrog.advert.web.dto;

import java.io.Serializable;

public class AdNoticeDetailRequest implements Serializable {

	private static final long serialVersionUID = 7351555364697678626L;
	
	private Long id;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

}
