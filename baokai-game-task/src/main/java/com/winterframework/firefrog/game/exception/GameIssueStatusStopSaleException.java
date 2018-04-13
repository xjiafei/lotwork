package com.winterframework.firefrog.game.exception;

/** 
* @ClassName: GameIssueStatusStopSaleException 
* @Description: 当前奖期已停止销售
* @author david
* @date 2013-7-29 下午2:15:33 
*  
*/
public class GameIssueStatusStopSaleException extends RuntimeException {

	private static final long serialVersionUID = 6249158835809275309L;

	public final static Long CODE = 201004L;

	public final static String MSG = "当前奖期已停止销售";

	public GameIssueStatusStopSaleException() {
		super(MSG);
	}

	public GameIssueStatusStopSaleException(Throwable cause) {
		super(String.valueOf(CODE), cause);
	}

	public Long getStatus() {
		return CODE;
	}
}
