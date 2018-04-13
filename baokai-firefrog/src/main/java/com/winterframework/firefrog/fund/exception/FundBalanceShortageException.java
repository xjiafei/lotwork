/**   
* @Title: FundBalanceShortageException.java 
* @Package com.winterframework.firefrog.fund.exception 
* @Description: TODO(用一句话描述该文件做什么) 
* @author 你的名字   
* @date 2013-7-23 下午2:22:23 
* @version V1.0   
*/
package com.winterframework.firefrog.fund.exception;

/** 
* @ClassName: FundBalanceShortageException 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author 你的名字 
* @date 2013-7-23 下午2:22:23 
*  
*/
public class FundBalanceShortageException extends RuntimeException {

	private static final long serialVersionUID = -439873258672943470L;

	public final static Long CODE = 2005L;

	public final static String MSG = "余额不足";

	public FundBalanceShortageException() {
		super(MSG);
	}

	public FundBalanceShortageException(Throwable cause) {
		super(String.valueOf(CODE), cause);
	}

	public Long getStatus() {
		return CODE;
	}

}
