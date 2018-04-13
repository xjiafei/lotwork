package com.winterframework.firefrog.game.exception;

/** 
* @ClassName: PastGameIssueQueryEndTimeAfterStartTimeOfCurrentDayException 
* @Description: 过去奖期查询结束时间不得晚于当前起始时间
* @author david
* @date 2013-7-29 下午2:15:33 
*  
*/
public class PastGameIssueQueryEndTimeAfterStartTimeOfCurrentDayException extends RuntimeException {

	private static final long serialVersionUID = 6249158835809275309L;

	public final static Long CODE = 203009L;

	public final static String MSG = "过去奖期查询结束时间不得晚于当前起始时间";

	public PastGameIssueQueryEndTimeAfterStartTimeOfCurrentDayException() {
		super(MSG);
	}

	public PastGameIssueQueryEndTimeAfterStartTimeOfCurrentDayException(Throwable cause) {
		super(String.valueOf(CODE), cause);
	}

	public Long getStatus() {
		return CODE;
	}
}
