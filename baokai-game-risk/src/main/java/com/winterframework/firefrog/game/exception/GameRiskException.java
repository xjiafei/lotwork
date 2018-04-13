package com.winterframework.firefrog.game.exception;

/** 
* @ClassName: GameRiskException 
* @Description: 
* @author hugh
* @date 2014-4-10 下午2:15:33 
*  
*/
public class GameRiskException extends RuntimeException {
 
	private static final long serialVersionUID = -3254986524486715350L;

	public final static Long CODE = 1000000L;

	public final static String MSG = "审核异常 ";

	public GameRiskException() {
		super(MSG);
	}
	
	public GameRiskException(String _msg) {
		super(MSG + _msg);
	}
	
	public GameRiskException(Throwable cause) {
		super(String.valueOf(CODE), cause);
	}

	public Long getStatus() {
		return CODE;
	}
}
