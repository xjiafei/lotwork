/**   
* @Title: IValidatorFactory.java 
* @Package com.winterframework.firefrog.common.validate.business
* @Description: 
* @author page
* @date 2013-8-2 上午10:47:52 
* @version V1.0   
*/
package com.winterframework.firefrog.common.validate.business;

/** 
* @ClassName: IValidatorFactory 
* @Description: 得到一个验证执行器
* @author page
* @date 2013-8-2 上午10:47:52 
*  
*/
public interface IValidatorFactory<T> {
	IValidateExecutor<T> getValidateExecutor(String lotteryCode, IKeyGenerator keyGenerator);
}
