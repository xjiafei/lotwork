package com.winterframework.firefrog.game.exception;

/** 
* @ClassName: GameIssueStatusErrorException 
* @Description: 奖期修改审核新增时的生效时间应该在当前起始时间n天之后
* @author david
* @date 2013-7-29 下午2:15:33 
*  
*/
public class GameIssueRuleStartTimeShouldSuitableException extends RuntimeException {

	private static final long serialVersionUID = 6249158835809275309L;

	public final static Long CODE = 203005L;

	public final static String MSG = "奖期修改审核新增时的生效时间应该在当前起始时间3天之后";

	public GameIssueRuleStartTimeShouldSuitableException() {
		super(MSG);
	}

	public GameIssueRuleStartTimeShouldSuitableException(Throwable cause) {
		super(String.valueOf(CODE), cause);
	}

	public Long getStatus() {
		return CODE;
	}
}
