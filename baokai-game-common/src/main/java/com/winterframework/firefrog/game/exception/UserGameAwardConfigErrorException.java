package com.winterframework.firefrog.game.exception;

/** 
* @ClassName: GameIssueStatusErrorException 
* @Description: 用户投注奖金组配置异常
* @author david
* @date 2013-7-29 下午2:15:33 
*  
*/
public class UserGameAwardConfigErrorException extends RuntimeException {

	private static final long serialVersionUID = 6249158835809275309L;

	public final static Long CODE = 204003L;

	public final static String MSG = "用户投注奖金组配置异常";

	public UserGameAwardConfigErrorException() {
		super(MSG);
	}

	public UserGameAwardConfigErrorException(Throwable cause) {
		super(String.valueOf(CODE), cause);
	}

	public Long getStatus() {
		return CODE;
	}
}
