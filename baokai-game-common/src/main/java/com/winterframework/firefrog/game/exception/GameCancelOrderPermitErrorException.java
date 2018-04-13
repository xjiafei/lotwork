package com.winterframework.firefrog.game.exception;

/** 
* @ClassName: GameIssueStatusErrorException 
* @Description: 用户撤销权限错误 
* @author david
* @date 2013-7-29 下午2:15:33 
*  
*/
public class GameCancelOrderPermitErrorException extends RuntimeException {

	private static final long serialVersionUID = 6243788835809275309L;

	public final static Long CODE = 999999L;

	public final static String MSG = "用户撤销权限错误";

	public GameCancelOrderPermitErrorException() {
		super(MSG);
	}

	public GameCancelOrderPermitErrorException(Throwable cause) {
		super(String.valueOf(CODE), cause);
	}

	public Long getStatus() {
		return CODE;
	}
}
