package com.winterframework.firefrog.game.exception;

/** 
* @ClassName: GameRiskException 
* @Description: 支付网关错误
* @author hugh
* @date 2013-7-29 下午2:15:33 
*  
*/
public class GameRiskException extends RuntimeException {

	private static final long serialVersionUID = 6249158835809275309L;

	public final static Long CODE = 202110L;

	public final static String MSG = "支付网关错误";

	public GameRiskException() {
		super(MSG);
	}

	public GameRiskException(Throwable cause) {
		super(String.valueOf(CODE), cause);
	}

	public Long getStatus() {
		return CODE;
	}
}
