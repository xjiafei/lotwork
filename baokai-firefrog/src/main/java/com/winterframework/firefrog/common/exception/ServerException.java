package com.winterframework.firefrog.common.exception;

public class ServerException extends Exception {
	private static final long serialVersionUID = 3583566093089790852L;
	private String code;
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

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

}
