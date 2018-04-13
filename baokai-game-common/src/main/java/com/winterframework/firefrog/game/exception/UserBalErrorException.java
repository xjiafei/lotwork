package com.winterframework.firefrog.game.exception;

/** 
* @ClassName: GameIssueStatusErrorException 
* @Description: 用户余额不足
* @author david
* @date 2013-7-29 下午2:15:33 
*  
*/
public class UserBalErrorException extends RuntimeException {

	private static final long serialVersionUID = 6249158835809275309L;

	public final static Long CODE = 204001L;

	public final static String MSG = "余额不足";

	public UserBalErrorException() {
		super(MSG);
	}

	public UserBalErrorException(Throwable cause) {
		super(String.valueOf(CODE), cause);
	}

	public Long getStatus() {
		return CODE;
	}
}
