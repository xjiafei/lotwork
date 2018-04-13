package com.winterframework.firefrog.game.exception;

/** 
* @ClassName: GameRevocationException 
* @Description: 撤销异常
* @author hugh
* @date 2014-5-6 下午2:15:33 
*  
*/
public class GameRevocationException extends RuntimeException {
 
	private static final long serialVersionUID = -3254986524486715350L;

	public final static Long CODE = 2000000L;

	public final static String MSG = "撤销异常 ";

	public GameRevocationException() {
		super(MSG);
	}
	
	public GameRevocationException(String _msg) {
		super(MSG+_msg);
	}
	
	public GameRevocationException(Throwable cause) {
		super(String.valueOf(CODE), cause);
	}

	public Long getStatus() {
		return CODE;
	}
}
