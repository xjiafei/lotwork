package com.winterframework.firefrog.game.exception;

/** 
* @ClassName: GameIssueStatusErrorException 
* @Description: 设置的时间与现有特例奖期规则时间有重叠
* @author david
* @date 2013-7-29 下午2:15:33 
*  
*/
public class GameIssueRuleOverlapErrorException extends RuntimeException {

	private static final long serialVersionUID = 6249158835809275309L;

	public final static Long CODE = 203001L;

	public final static String MSG = "设置的时间与现有特例奖期规则时间有重叠";

	public GameIssueRuleOverlapErrorException() {
		super(MSG);
	}

	public GameIssueRuleOverlapErrorException(Throwable cause) {
		super(String.valueOf(CODE), cause);
	}

	public Long getStatus() {
		return CODE;
	}
}
