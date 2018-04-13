package com.winterframework.firefrog.common.validate.business;

/** 
* @ClassName: IValidateExecutorContext 
* @Description: 
* @author 你的名字 
* @date 2013-8-2 上午10:49:45 
*  
*/
public interface IValidateExecutorContext {
	IKeyGenerator getKeyGenerator();

	void setKeyGenerator(IKeyGenerator keyGenerator);
}
