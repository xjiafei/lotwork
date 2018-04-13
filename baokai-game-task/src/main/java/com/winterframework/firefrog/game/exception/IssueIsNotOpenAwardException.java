package com.winterframework.firefrog.game.exception;

/** 
* @ClassName: QueryBeginTimeAfterEndTimeException 
* @Description: 查询结束时间不得早于开始时间
* @author david
* @date 2013-7-29 下午2:15:33 
*  
*/
public class IssueIsNotOpenAwardException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3655116616319528957L;

	public final static Long CODE = 208009L;

	public final static String MSG = "并非处于开奖号码已确认状态";

	public IssueIsNotOpenAwardException() {
		super(MSG);
	}
	
	public IssueIsNotOpenAwardException(String msg) {
		super(msg);
	}

	public IssueIsNotOpenAwardException(Throwable cause) {
		super(String.valueOf(CODE), cause);
	}

	public Long getStatus() {
		return CODE;
	}
}
