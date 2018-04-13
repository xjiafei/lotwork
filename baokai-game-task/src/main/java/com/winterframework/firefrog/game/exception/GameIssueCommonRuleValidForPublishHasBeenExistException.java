package com.winterframework.firefrog.game.exception;

/** 
* @ClassName: GameIssueCommonRuleValidForPublishHasBeenExistException 
* @Description: 不能存在两条有效待发布常规规则
* @author david
* @date 2013-7-29 下午2:15:33 
*  
*/
public class GameIssueCommonRuleValidForPublishHasBeenExistException extends RuntimeException {

	private static final long serialVersionUID = 6249158835809275309L;

	public final static Long CODE = 203006L;

	public final static String MSG = "不能存在两条有效待发布常规规则";

	public GameIssueCommonRuleValidForPublishHasBeenExistException() {
		super(MSG);
	}

	public GameIssueCommonRuleValidForPublishHasBeenExistException(Throwable cause) {
		super(String.valueOf(CODE), cause);
	}

	public Long getStatus() {
		return CODE;
	}
}
