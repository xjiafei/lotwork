package com.winterframework.firefrog.game.exception;

/** 
* @ClassName: GameIssueStatusErrorException 
* @Description: 总代不能投注
* @author david
* @date 2013-7-29 下午2:15:33 
*  
*/
public class UserTopAgentBetException extends RuntimeException {

	private static final long serialVersionUID = 6249158835809275309L;

	public final static Long CODE = 204002L;

	public final static String MSG = "总代不能投注";

	public UserTopAgentBetException() {
		super(MSG);
	}

	public UserTopAgentBetException(Throwable cause) {
		super(String.valueOf(CODE), cause);
	}

	public Long getStatus() {
		return CODE;
	}
}
