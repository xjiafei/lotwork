package com.winterframework.firefrog.game.exception;

/** 
* @ClassName: GameRiskException 
* @Description: 
* @author hugh
* @date 2014-4-10 下午2:15:33 
*  
*/
public class GameReportException extends RuntimeException {
 
	private static final long serialVersionUID = -3254986524486715350L;

	public final static Long CODE = 3000000L;

	public final static String MSG = "报表异常 ";

	public GameReportException() {
		super(MSG);
	}
	
	public GameReportException(String _msg) {
		super(MSG + _msg);
	}
	
	public GameReportException(Throwable cause) {
		super(String.valueOf(CODE), cause);
	}

	public Long getStatus() {
		return CODE;
	}
}
