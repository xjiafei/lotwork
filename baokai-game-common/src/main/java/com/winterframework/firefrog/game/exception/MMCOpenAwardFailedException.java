package com.winterframework.firefrog.game.exception;

/** 
* @ClassName: MMCBetFailedException 
* @Description: 秒秒彩开奖失败异常
* @author david
* @date 2013-7-29 下午2:15:33 
*  
*/
public class MMCOpenAwardFailedException extends RuntimeException {

	private static final long serialVersionUID = 6249158835809275309L;

	public final static Long CODE = 204005L;

	public final static String MSG = "秒开时时彩开奖失败";

	public MMCOpenAwardFailedException() {
		super(MSG);
	}

	public MMCOpenAwardFailedException(Throwable cause) {
		super(String.valueOf(CODE), cause);
	}

	public Long getStatus() {
		return CODE;
	}
}
