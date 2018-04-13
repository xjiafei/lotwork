package com.winterframework.firefrog.game.exception;

/** 
* @ClassName: GameIssueRuleEffectiveTimeBeforeCurrentTimeException 
* @Description: 开始执行时间早于当前时间
* @author david
* @date 2013-7-29 下午2:15:33 
*  
*/
public class GameIssueRuleEffectiveTimeBeforeCurrentTimeException extends RuntimeException {

	private static final long serialVersionUID = 6249158835809275309L;

	public final static Long CODE = 203003L;

	public final static String MSG = "开始执行时间早于当前时间";

	public GameIssueRuleEffectiveTimeBeforeCurrentTimeException() {
		super(MSG);
	}

	public GameIssueRuleEffectiveTimeBeforeCurrentTimeException(Throwable cause) {
		super(String.valueOf(CODE), cause);
	}

	public Long getStatus() {
		return CODE;
	}
}
