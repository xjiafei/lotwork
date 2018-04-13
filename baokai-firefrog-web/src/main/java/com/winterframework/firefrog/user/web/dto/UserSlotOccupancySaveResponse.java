package com.winterframework.firefrog.user.web.dto;

import java.io.Serializable;

public class UserSlotOccupancySaveResponse implements Serializable {
	private static final long serialVersionUID = 1L;
	private Long status;

	public Long getStatus() {
		return status;
	}

	public void setStatus(Long status) {
		this.status = status;
	}

}
