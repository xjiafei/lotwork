package com.winterframework.firefrog.game.exception;

/** 
* @ClassName: GameBetAmountErrorException 
* @Description: 投注金额错误异常 
* @author david
* @date 2013-7-29 下午2:15:33 
*  
*/
public class GameBetAmountErrorException extends RuntimeException {

	private static final long serialVersionUID = 6249158835809275309L;

	public final static Long CODE = 202001L;

	public final static String MSG = "投注金额有误";

	public GameBetAmountErrorException() {
		super(MSG);
	}

	public GameBetAmountErrorException(Throwable cause) {
		super(String.valueOf(CODE), cause);
	}

	public Long getStatus() {
		return CODE;
	}
}
