package com.winterframework.firefrog.common.exception;

public class ValidExcetion extends RuntimeException {
	private static final long serialVersionUID = 3583566093089790852L;

	public ValidExcetion() {
		super();
	}

	public ValidExcetion(String message) {
		super(message);
	}

	public ValidExcetion(Throwable cause) {
		super(cause);
	}

	public ValidExcetion(String message, Throwable cause) {
		super(message, cause);
	}

}