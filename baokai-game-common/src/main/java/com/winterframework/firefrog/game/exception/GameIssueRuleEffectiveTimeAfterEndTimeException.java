package com.winterframework.firefrog.game.exception;

/** 
* @ClassName: GameIssueRuleEffectiveTimeBeforeCurrentTimeException 
* @Description: 结束执行时间早于开始执行时间
* @author david
* @date 2013-7-29 下午2:15:33 
*  
*/
public class GameIssueRuleEffectiveTimeAfterEndTimeException extends RuntimeException {

	private static final long serialVersionUID = 6249158835809275309L;

	public final static Long CODE = 203004L;

	public final static String MSG = "结束执行时间早于开始执行时间";

	public GameIssueRuleEffectiveTimeAfterEndTimeException() {
		super(MSG);
	}

	public GameIssueRuleEffectiveTimeAfterEndTimeException(Throwable cause) {
		super(String.valueOf(CODE), cause);
	}

	public Long getStatus() {
		return CODE;
	}
}
