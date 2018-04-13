/**   
* @Title: FundWithdrawTooMuchException.java 
* @Package com.winterframework.firefrog.fund.exception 
* @Description: TODO(用一句话描述该文件做什么) 
* @author 你的名字   
* @date 2013-7-23 下午2:23:55 
* @version V1.0   
*/
package com.winterframework.firefrog.fund.exception;

/** 
* @ClassName: FundWithdrawTooMuchException 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author 你的名字 
* @date 2013-7-23 下午2:23:55 
*  
*/
public class FundWithdrawTooMuchException extends RuntimeException {

	private static final long serialVersionUID = -2623216906287480678L;

	public final static Long CODE = 2006L;

	public final static String MSG = "提现次数超过当日次数上限";

	public FundWithdrawTooMuchException() {
		super(MSG);
	}

	public FundWithdrawTooMuchException(Throwable cause) {
		super(String.valueOf(CODE), cause);
	}

	public Long getStatus() {
		return CODE;
	}

}
