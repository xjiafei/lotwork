package com.winterframework.firefrog.game.exception;

/** 
* @ClassName: GameIssueStatusErrorException 
* @Description: 奖期状态错误 
* @author david
* @date 2013-7-29 下午2:15:33 
*  
*/
public class GameOrderStatusErrorException extends RuntimeException {

	private static final long serialVersionUID = 6249158835809275309L;

	public final static Long CODE = 202007L;

	public final static String MSG = "订单状态错误";

	public GameOrderStatusErrorException() {
		super(MSG);
	}

	public GameOrderStatusErrorException(Throwable cause) {
		super(String.valueOf(CODE), cause);
	}

	public Long getStatus() {
		return CODE;
	}
}
