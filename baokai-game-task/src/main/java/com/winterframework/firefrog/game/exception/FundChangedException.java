/**   
* @Title: fundChargeHighException.java 
* @Package com.winterframework.firefrog.fund.exception 
* @Description: TODO(用一句话描述该文件做什么) 
* @author 你的名字   
* @date 2013-7-23 下午2:15:33 
* @version V1.0   
*/
package com.winterframework.firefrog.game.exception;

/** 
* @ClassName: fundChargeHighException 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author 你的名字 
* @date 2013-7-23 下午2:15:33 
*  
*/
public class FundChangedException extends RuntimeException {

	private static final long serialVersionUID = 6249158835809275309L;

	public final static Long CODE = 2009L;

	public final static String MSG = "改动资金异常";

	public FundChangedException() {
		super(MSG);
	}

	public FundChangedException(Throwable cause) {
		super(String.valueOf(CODE), cause);
	}

	public Long getStatus() {
		return CODE;
	}
}
