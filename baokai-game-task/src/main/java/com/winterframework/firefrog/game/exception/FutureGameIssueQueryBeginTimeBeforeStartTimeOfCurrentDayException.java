package com.winterframework.firefrog.game.exception;

/** 
* @ClassName: FutureGameIssueQueryBeginTimeBeforeStartTimeOfCurrentDayException 
* @Description: 未来奖期查询开始时间不得早于当前起始时间
* @author david
* @date 2013-7-29 下午2:15:33 
*  
*/
public class FutureGameIssueQueryBeginTimeBeforeStartTimeOfCurrentDayException extends RuntimeException {

	private static final long serialVersionUID = 6249158835809275309L;

	public final static Long CODE = 203010L;

	public final static String MSG = "未来奖期查询开始时间不得早于当前起始时间";

	public FutureGameIssueQueryBeginTimeBeforeStartTimeOfCurrentDayException() {
		super(MSG);
	}

	public FutureGameIssueQueryBeginTimeBeforeStartTimeOfCurrentDayException(Throwable cause) {
		super(String.valueOf(CODE), cause);
	}

	public Long getStatus() {
		return CODE;
	}
}
