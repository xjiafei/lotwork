package com.winterframework.firefrog.game.exception;

/** 
* @ClassName: GameFundException 
* @Description: 审核中心资金交互异常
* @author hugh
* @date 2014-4-10 下午2:15:33 
*  
*/
public class GameFundException extends Exception {
 
	private static final long serialVersionUID = -3254986524486715350L;

	public final static Long CODE = 2000000L;

	public final static String MSG = "Task资金交互异常 ";

	public GameFundException() {
		super(MSG);
	}
	
	public GameFundException(String _msg) {
		super(MSG + _msg);
	}
	
	public GameFundException(Throwable cause) {
		super(String.valueOf(CODE), cause);
	}

	public Long getStatus() {
		return CODE;
	}
}
