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
* @author Tod
* @date 2013-7-23 下午2:15:33 
*  
*/
public class FundBankcardBindLimitException extends RuntimeException {

	private static final long serialVersionUID = -7588568931857616285L;

	public final static Long CODE = 2008L;

	public final static String MSG = "绑卡功能达到可绑定上限";

	public FundBankcardBindLimitException() {
		super(MSG);
	}

	public FundBankcardBindLimitException(Throwable cause) {
		super(String.valueOf(CODE), cause);
	}

	public Long getStatus() {
		return CODE;
	}
}
