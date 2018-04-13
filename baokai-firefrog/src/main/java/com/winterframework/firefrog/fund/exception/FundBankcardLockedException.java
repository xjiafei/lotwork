/**   
* @Title: FundBankcardLocked.java 
* @Package com.winterframework.firefrog.fund.exception 
* @Description: TODO(用一句话描述该文件做什么) 
* @author 你的名字   
* @date 2013-7-23 下午2:25:18 
* @version V1.0   
*/
package com.winterframework.firefrog.fund.exception;

/** 
* @ClassName: FundBankcardLocked 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author 你的名字 
* @date 2013-7-23 下午2:25:18 
*  
*/
public class FundBankcardLockedException extends RuntimeException {

	private static final long serialVersionUID = 5406881912069075913L;

	public final static Long CODE = 2007L;

	public final static String MSG = "银行卡绑定功能被锁定";

	public FundBankcardLockedException() {
		super(MSG);
	}

	public FundBankcardLockedException(Throwable cause) {
		super(String.valueOf(CODE), cause);
	}

	public Long getStatus() {
		return CODE;
	}

}
