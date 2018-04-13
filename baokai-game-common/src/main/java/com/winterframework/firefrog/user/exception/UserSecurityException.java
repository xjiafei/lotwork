package com.winterframework.firefrog.user.exception;

import com.winterframework.firefrog.common.exception.ServiceException;

public class UserSecurityException extends ServiceException {

	private static final long serialVersionUID = 8361139388833937808L;

	private Long status;

	public UserSecurityException() {
		super();
	}

	public UserSecurityException(Long status, String description) {
		super(description);
		this.status = status;
	}

	public UserSecurityException(Throwable cause) {
		super(cause);
	}

	public UserSecurityException(Long status, Throwable cause) {
		super(String.valueOf(status), cause);
	}

	public Long getStatus() {
		return status;
	}
}
