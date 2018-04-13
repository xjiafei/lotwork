package com.winterframework.firefrog.game.exception;

/** 
* @ClassName: QueryBeginTimeAfterEndTimeException 
* @Description: 查询结束时间不得早于开始时间
* @author david
* @date 2013-7-29 下午2:15:33 
*  
*/
public class QueryBeginTimeAfterEndTimeException extends RuntimeException {

	private static final long serialVersionUID = 6249158835809275309L;

	public final static Long CODE = 203008L;

	public final static String MSG = "查询结束时间不得早于开始时间";

	public QueryBeginTimeAfterEndTimeException() {
		super(MSG);
	}

	public QueryBeginTimeAfterEndTimeException(Throwable cause) {
		super(String.valueOf(CODE), cause);
	}

	public Long getStatus() {
		return CODE;
	}
}
