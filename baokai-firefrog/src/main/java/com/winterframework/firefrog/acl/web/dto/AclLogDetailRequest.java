package com.winterframework.firefrog.acl.web.dto;

import java.io.Serializable;

public class AclLogDetailRequest implements Serializable {

	private static final long serialVersionUID = -3093823128675289285L;

	private Long id;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

}
