package com.winterframework.firefrog.game.exception;

/** 
* @ClassName: GameIssueStatusErrorException 
* @Description: 奖期状态错误 
* @author david
* @date 2013-7-29 下午2:15:33 
*  
*/
public class GameIssueStatusErrorException extends RuntimeException {

	private static final long serialVersionUID = 6249158835809275309L;

	public final static Long CODE = 201005L;

	public final static String MSG = "奖期状态错误";

	public GameIssueStatusErrorException() {
		super(MSG);
	}

	public GameIssueStatusErrorException(Throwable cause) {
		super(String.valueOf(CODE), cause);
	}

	public Long getStatus() {
		return CODE;
	}
}
