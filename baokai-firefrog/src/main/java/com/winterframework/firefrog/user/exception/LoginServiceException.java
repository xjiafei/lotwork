package com.winterframework.firefrog.user.exception;

import com.winterframework.firefrog.common.exception.ServiceException;

/**
 * loginService层公用的Exception.
 * 
 * 继承自ServiceException, 从由Spring管理事务的函数中抛出时会触发事务回滚.
 * 
 * @author david
 */
public class LoginServiceException extends ServiceException {

	private Long status;

	private static final long serialVersionUID = -1096252931379589472L;

	public LoginServiceException() {
		super();
	}

	public LoginServiceException(Long status, String description) {
		super(description);
		this.status = status;
	}

	public LoginServiceException(Throwable cause) {
		super(cause);
	}

	public LoginServiceException(Long status, Throwable cause) {
		super(String.valueOf(status), cause);
	}

	public Long getStatus() {
		return status;
	}
}
