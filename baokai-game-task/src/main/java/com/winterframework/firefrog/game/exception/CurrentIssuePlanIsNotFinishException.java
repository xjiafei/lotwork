package com.winterframework.firefrog.game.exception;

/** 
* @ClassName: QueryBeginTimeAfterEndTimeException 
* @Description: 查询结束时间不得早于开始时间
* @author david
* @date 2013-7-29 下午2:15:33 
*  
*/
public class CurrentIssuePlanIsNotFinishException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3655116616319528957L;

	public final static Long CODE = 208008L;

	public final static String MSG = "本期奖期计划未完成";

	public CurrentIssuePlanIsNotFinishException() {
		super(MSG);
	}
	
	public CurrentIssuePlanIsNotFinishException(String msg) {
		super(msg);
	}

	public CurrentIssuePlanIsNotFinishException(Throwable cause) {
		super(String.valueOf(CODE), cause);
	}

	public Long getStatus() {
		return CODE;
	}
}
