package com.winterframework.firefrog.game.exception;

/** 
* @ClassName: GameIssueTemplateLastAwardTimeBeforeFirstAwardTimeException 
* @Description: 分段期间最后一期开奖时间要大于第一期开奖时间
* @author david
* @date 2013-7-29 下午2:15:33 
*  
*/
public class GameIssueTemplateLastAwardTimeBeforeFirstAwardTimeException extends RuntimeException {

	private static final long serialVersionUID = 6249158835809275309L;

	public final static Long CODE = 203007L;

	public final static String MSG = "分段期间最后一期开奖时间要大于第一期开奖时间";

	public GameIssueTemplateLastAwardTimeBeforeFirstAwardTimeException() {
		super(MSG);
	}

	public GameIssueTemplateLastAwardTimeBeforeFirstAwardTimeException(Throwable cause) {
		super(String.valueOf(CODE), cause);
	}

	public Long getStatus() {
		return CODE;
	}
}
