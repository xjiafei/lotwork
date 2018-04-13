package com.winterframework.firefrog.game.exception;

/** 
* @ClassName: GameBetOverMultipleLimitException 
* @Description: 投注倍数超出限制错误
* @author david
* @date 2013-7-29 下午2:15:33 
*  
*/
public class GameBetOverMultipleLimitException extends RuntimeException {

	private static final long serialVersionUID = 6249158835809275309L;

	public final static Long CODE = 202005L;

	public final static String MSG = "投注倍数超出限制错误";

	public GameBetOverMultipleLimitException() {
		super(MSG);
	}

	public GameBetOverMultipleLimitException(Throwable cause) {
		super(String.valueOf(CODE), cause);
	}

	public Long getStatus() {
		return CODE;
	}
}
