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
public class FundWithdrawHighException extends RuntimeException {

	private static final long serialVersionUID = 2958885507534231064L;

	public final static Long CODE = 2004L;

	public final static String MSG = "提现金额高于上限";

	public FundWithdrawHighException() {
		super(MSG);
	}

	public FundWithdrawHighException(Throwable cause) {
		super(String.valueOf(CODE), cause);
	}

	public Long getStatus() {
		return CODE;
	}

}
