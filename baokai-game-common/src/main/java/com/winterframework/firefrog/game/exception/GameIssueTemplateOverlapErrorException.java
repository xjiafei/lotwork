package com.winterframework.firefrog.game.exception;

/** 
* @ClassName: GameIssueStatusErrorException 
* @Description: 分段奖期时间有重叠项
* @author david
* @date 2013-7-29 下午2:15:33 
*  
*/
public class GameIssueTemplateOverlapErrorException extends RuntimeException {

	private static final long serialVersionUID = 6249158835809275309L;

	public final static Long CODE = 203002L;

	public final static String MSG = "分段奖期时间有重叠项";

	public GameIssueTemplateOverlapErrorException() {
		super(MSG);
	}

	public GameIssueTemplateOverlapErrorException(Throwable cause) {
		super(String.valueOf(CODE), cause);
	}

	public Long getStatus() {
		return CODE;
	}
}
