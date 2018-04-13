package com.winterframework.firefrog.game.exception;

/** 
* @ClassName: GameCancalOutTimeErrorException 
* @Description: 超过订单撤销时间区间报该错
* @author hugh
* @date 2013-7-29 下午2:15:33 
*  
*/
public class GameCancalOutTimeErrorException extends RuntimeException {

	private static final long serialVersionUID = 6249158835809275309L;

	public final static Long CODE = 301001L;

	public final static String MSG = "超过订单允许撤销时间";

	public GameCancalOutTimeErrorException() {
		super(MSG);
	}

	public GameCancalOutTimeErrorException(Throwable cause) {
		super(String.valueOf(CODE), cause);
	}

	public Long getStatus() {
		return CODE;
	}
}
