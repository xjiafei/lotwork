package com.winterframework.adbox.exception;

public class BizException extends Exception {
	

	/**
	 * 
	 */
	private static final long serialVersionUID = -4155383900948675082L;
	
	private int code=99999;
	private String[] params;
	
	public BizException() {
		super();
	}

	public BizException(String message) {
		super(message);
	}

	public BizException(Throwable cause) {
		super(cause);
	}
	public BizException(int code) {
		super();
		this.code = code;
	}
	public BizException(int code,Throwable exception) {
		super(exception);
		this.code = code;
	}
	public BizException(int code, String msg, Throwable exception) {
		super(msg, exception);
		this.code = code;
	}
	public BizException(int code,String[] params) {
		super();
		this.code = code;
		this.params = params;
	}
	public BizException(int code,String[] params,Throwable exception) {
		super(exception);
		this.code = code;
		this.params = params;
	}

	public int getCode() {
		return this.code;
	}

	public String[] getParams() {
		return params;
	}
	@Override
	public String toString() {
		String s = getClass().getName();
        return s + ": " + code + " params:"+params;
	}
	

}
