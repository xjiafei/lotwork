package com.winterframework.firefrog.game.exception;

 
/**
 * 系統正在開獎不可撤銷
 * @ClassName
 * @Description
 * @author ibm
 * 2014年10月11日
 */
public class GameIssueISOpenAwardException extends RuntimeException {  
	private static final long serialVersionUID = 50675686415014956L;

	public final static Long CODE = 200513L;

	public final static String MSG = "开奖中，撤销失败";

	public GameIssueISOpenAwardException() {
		super(MSG);
	}

	public GameIssueISOpenAwardException(Throwable cause) {
		super(String.valueOf(CODE), cause);
	}

	public Long getStatus() {
		return CODE;
	}
}
