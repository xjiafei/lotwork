package com.winterframework.firefrog.notice.web.dto;

import java.io.Serializable;

public class NoticeMsgStatusRequest implements Serializable {

	private static final long serialVersionUID = 3614300890502147503L;

	private Long id;

	private Long status;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getStatus() {
		return status;
	}

	public void setStatus(Long status) {
		this.status = status;
	}

}
