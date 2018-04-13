package com.winterframework.firefrog.advert.web.dto;

import java.io.Serializable;

public class AdNoticeStatusCount implements Serializable {

	private static final long serialVersionUID = 6007751824110509591L;

	private Long status;

	private Long count;

	public Long getStatus() {
		return status;
	}

	public void setStatus(Long status) {
		this.status = status;
	}

	public Long getCount() {
		return count;
	}

	public void setCount(Long count) {
		this.count = count;
	}

}
