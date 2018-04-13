/**   
* @Title: IPaymentCallbackDispatcher.java 
* @Package com.winterframework.firefrog.fund.service.impl 
* @Description: TODO(用一句话描述该文件做什么) 
* @author 你的名字   
* @date 2013-8-13 下午3:17:38 
* @version V1.0   
*/
package com.winterframework.firefrog.fund.service;

import com.winterframework.firefrog.fund.service.impl.mow.WithdrawConfirmRequest;

/** 
* @ClassName: IPaymentCallbackDispatcher 
* @Description: momnecum  
* @author page
* @date 2013-8-13 下午3:17:38 
*  
*/
public interface IPaymentCallbackDispatcher {

	public abstract void doDispatch(String str, WithdrawConfirmRequest confirmRequest) throws Exception;

}