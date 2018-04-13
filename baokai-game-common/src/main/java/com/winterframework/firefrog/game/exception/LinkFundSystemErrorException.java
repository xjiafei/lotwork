package com.winterframework.firefrog.game.exception;

/** 
* @ClassName: LinkFundSystemErrorException 
* @Description: 连接资金系统异常
* @author david
* @date 2013-7-29 下午2:15:33 
*  
*/
public class LinkFundSystemErrorException extends RuntimeException {

	private static final long serialVersionUID = 6249158835809275309L;

	public final static Long CODE = 206001L;

	public final static String MSG = "连接资金系统异常";

	public LinkFundSystemErrorException() {
		super(MSG);
	}

	public LinkFundSystemErrorException(Throwable cause) {
		super(String.valueOf(CODE), cause);
	}

	public Long getStatus() {
		return CODE;
	}
}
