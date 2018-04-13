package com.winterframework.firefrog.phone.web.cotroller.dto;

import java.io.Serializable;

public class AddCustomizedUserResponse implements Serializable {

	private static final long serialVersionUID = -6576136159671076428L;

	private String status;
	private Long uid;
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Long getUid() {
		return uid;
	}
	public void setUid(Long uid) {
		this.uid = uid;
	}
}
