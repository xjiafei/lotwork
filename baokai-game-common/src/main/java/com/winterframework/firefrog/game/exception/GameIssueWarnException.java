package com.winterframework.firefrog.game.exception;

/** 
* @ClassName: GameIssueWarnException
* @Description: 异常处理时间超限
* @author lex
*  
*/
public class GameIssueWarnException extends RuntimeException {

	private static final long serialVersionUID = 6249158835809275309L;

	public final static Long CODE = 201005L;

	public final static String MSG = "异常处理时间超限";

	public GameIssueWarnException() {
		super(MSG);
	}

	public GameIssueWarnException(Throwable cause) {
		super(String.valueOf(CODE), cause);
	}

	public Long getStatus() {
		return CODE;
	}
}
