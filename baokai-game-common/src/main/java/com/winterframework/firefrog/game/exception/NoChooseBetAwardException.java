package com.winterframework.firefrog.game.exception;

/** 
* @ClassName: NoChooseBetAwardException 
* @Description: 未选择投注奖金组异常
* @author david
* @date 2013-7-29 下午2:15:33 
*  
*/
public class NoChooseBetAwardException extends RuntimeException {

	private static final long serialVersionUID = 6249158835809275309L;

	public final static Long CODE = 204004L;

	public final static String MSG = "未选择投注奖金组异常";

	public NoChooseBetAwardException() {
		super(MSG);
	}

	public NoChooseBetAwardException(Throwable cause) {
		super(String.valueOf(CODE), cause);
	}

	public Long getStatus() {
		return CODE;
	}
}
