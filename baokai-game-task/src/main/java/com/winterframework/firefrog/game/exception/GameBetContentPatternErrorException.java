package com.winterframework.firefrog.game.exception;

/** 
* @ClassName: GametotbetsErrorException 
* @Description: 投注内容错误
* @author david
* @date 2013-7-29 下午2:15:33 
*  
*/
public class GameBetContentPatternErrorException extends RuntimeException {

	private static final long serialVersionUID = 6249158835809275309L;

	public final static Long CODE = 202005L;

	public final static String MSG = "投注内容格式错误";

	public GameBetContentPatternErrorException() {
		super(MSG);
	}

	public GameBetContentPatternErrorException(Throwable cause) {
		super(String.valueOf(CODE), cause);
	}

	public Long getStatus() {
		return CODE;
	}
}
