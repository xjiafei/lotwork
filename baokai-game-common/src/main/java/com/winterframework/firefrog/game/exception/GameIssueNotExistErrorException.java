package com.winterframework.firefrog.game.exception;

/** 
* @ClassName: GameIssueStatusErrorException 
* @Description: 奖期不存在错误 
* @author david
* @date 2013-7-29 下午2:15:33 
*  
*/
public class GameIssueNotExistErrorException extends RuntimeException {

	private static final long serialVersionUID = 6249158835809275309L;

	public final static Long CODE = 201001L;

	public final static String MSG = "奖期不存在";

	public GameIssueNotExistErrorException() {
		super(MSG);
	}

	public GameIssueNotExistErrorException(Throwable cause) {
		super(String.valueOf(CODE), cause);
	}

	public Long getStatus() {
		return CODE;
	}
}
