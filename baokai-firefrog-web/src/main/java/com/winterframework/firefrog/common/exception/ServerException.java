package com.winterframework.firefrog.common.exception;

public class ServerException extends Exception {
	private static final long serialVersionUID = 3583566093089790852L;

	public ServerException() {
		super();
	}

	public ServerException(String message) {
		super(message);
	}

	public ServerException(Throwable cause) {
		super(cause);
	}

	public ServerException(String message, Throwable cause) {
		super(message, cause);
	}

}
