package com.winterframework.firefrog.game.exception;

/** 
* @ClassName: GametotbetsErrorException 
* @Description: 投注数错误
* @author david
* @date 2013-7-29 下午2:15:33 
*  
*/
public class GameTotbetsErrorException extends RuntimeException {

	private static final long serialVersionUID = 6249158835809275309L;

	public final static Long CODE = 202003L;

	public final static String MSG = "投注注数有误";

	public GameTotbetsErrorException() {
		super(MSG);
	}

	public GameTotbetsErrorException(Throwable cause) {
		super(String.valueOf(CODE), cause);
	}

	public Long getStatus() {
		return CODE;
	}
}
