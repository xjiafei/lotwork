package com.winterframework.firefrog.fund.web.dto;

import java.io.Serializable;

public class ExceptionAuditRequest implements Serializable {

	private static final long serialVersionUID = -1937024351395352408L;
	
	private Long exceptionId;
	
	private Long status;

	public Long getExceptionId() {
		return exceptionId;
	}

	public void setExceptionId(Long exceptionId) {
		this.exceptionId = exceptionId;
	}

	public Long getStatus() {
		return status;
	}

	public void setStatus(Long status) {
		this.status = status;
	}

}
