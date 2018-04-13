package com.winterframework.firefrog.game.exception;

/** 
* @ClassName: UserSubmitBusyErrorException 
* @Description: 用户提交数据过于频繁
* @author david
* @date 2013-7-29 下午2:15:33 
*  
*/
public class UserSubmitBusyErrorException extends RuntimeException {

	private static final long serialVersionUID = 6249158835809275309L;

	public final static Long CODE = 1000L;

	public final static String MSG = "用户提交数据过于频繁";

	public UserSubmitBusyErrorException() {
		super(MSG);
	}

	public UserSubmitBusyErrorException(Throwable cause) {
		super(String.valueOf(CODE), cause);
	}

	public Long getStatus() {
		return CODE;
	}
}
