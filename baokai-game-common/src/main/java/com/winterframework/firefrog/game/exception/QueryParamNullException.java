package com.winterframework.firefrog.game.exception;

/** 
* @ClassName: QueryParamNullException 
* @Description: 查询字段为空 （某些情况如DB查询时，某些字段为空会异常）
* @author hugh
* @date 2014-4-10 下午2:15:33 
*  
*/
public class QueryParamNullException extends RuntimeException {

	private static final long serialVersionUID = 6249158835809275309L;

	public final static Long CODE = 207001L;

	public final static String MSG = " 查询字段为空";

	public QueryParamNullException() {
		super(MSG);
	}
	
	public QueryParamNullException(String _msg) {
		super(_msg + MSG);
	}
	
	public QueryParamNullException(Throwable cause) {
		super(String.valueOf(CODE), cause);
	}

	public Long getStatus() {
		return CODE;
	}
}
