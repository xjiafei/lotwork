/**   
* @Title: fundChargeHighException.java 
* @Package com.winterframework.firefrog.fund.exception 
* @Description: TODO(用一句话描述该文件做什么) 
* @author 你的名字   
* @date 2013-7-23 下午2:15:33 
* @version V1.0   
*/
package com.winterframework.firefrog.fund.exception;

/** 
* @ClassName: fundChargeHighException 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author 你的名字 
* @date 2013-7-23 下午2:15:33 
*  
*/
public class FundChargeHighException extends RuntimeException {

	private static final long serialVersionUID = -107425167126803142L;

	public final static Long CODE = 2002L;

	public final static String MSG = "充值金额高于上限";

	public FundChargeHighException() {
		super(MSG);
	}

	public FundChargeHighException(Throwable cause) {
		super(String.valueOf(CODE), cause);
	}

	public Long getStatus() {
		return CODE;
	}
}
