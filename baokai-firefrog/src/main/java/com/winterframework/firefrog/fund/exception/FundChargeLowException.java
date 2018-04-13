/**   
* @Title: FundChargeLowException.java 
* @Package com.winterframework.firefrog.fund.exception 
* @Description: TODO(用一句话描述该文件做什么) 
* @author 你的名字   
* @date 2013-7-23 下午2:10:26 
* @version V1.0   
*/
package com.winterframework.firefrog.fund.exception;

/** 
* @ClassName: FundChargeLowException 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author 你的名字 
* @date 2013-7-23 下午2:10:26 
*  
*/
public class FundChargeLowException extends RuntimeException {

	private static final long serialVersionUID = -8864237321803484400L;

	public final static Long CODE = 2001L;

	public final static String MSG = "充值金额低于下限";

	public FundChargeLowException() {
		super(MSG);
	}

	public FundChargeLowException(Throwable cause) {
		super(String.valueOf(CODE), cause);
	}

	public Long getStatus() {
		return CODE;
	}
}
