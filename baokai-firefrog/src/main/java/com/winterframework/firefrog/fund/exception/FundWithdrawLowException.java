/**   
* @Title: FundWithdrawLowException.java 
* @Package com.winterframework.firefrog.fund.exception 
* @Description: TODO(用一句话描述该文件做什么) 
* @author 你的名字   
* @date 2013-7-23 下午2:18:35 
* @version V1.0   
*/
package com.winterframework.firefrog.fund.exception;

/** 
* @ClassName: FundWithdrawLowException 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author 你的名字 
* @date 2013-7-23 下午2:18:35 
*  
*/
public class FundWithdrawLowException extends RuntimeException {

	private static final long serialVersionUID = 2958885507534231064L;

	public final static Long CODE = 2003L;

	public final static String MSG = "提现金额低于下限";

	public FundWithdrawLowException() {
		super(MSG);
	}

	public FundWithdrawLowException(Throwable cause) {
		super(String.valueOf(CODE), cause);
	}

	public Long getStatus() {
		return CODE;
	}

}
