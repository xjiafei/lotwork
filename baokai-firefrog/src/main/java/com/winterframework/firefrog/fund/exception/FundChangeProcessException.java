package com.winterframework.firefrog.fund.exception;

public class FundChangeProcessException extends RuntimeException{
	
	private static final long serialVersionUID = 6249158835809275309L;

	public final static Long CODE = 100002L;

	public final static String MSG = "订单已被处理过";

	public FundChangeProcessException() {
		super(MSG);
	}

	public FundChangeProcessException(Throwable cause) {
		super(String.valueOf(CODE), cause);
	}

	public Long getStatus() {
		return CODE;
	}
}
