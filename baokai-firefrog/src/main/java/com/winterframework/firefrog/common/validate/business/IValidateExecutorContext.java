/**   
* @Title: IValidateExecutorContext.java 
* @Package com.winterframework.firefrog.common.validate.complex 
* @Description: TODO(用一句话描述该文件做什么) 
* @author 你的名字   
* @date 2013-8-2 上午10:49:45 
* @version V1.0   
*/
package com.winterframework.firefrog.common.validate.business;

/** 
* @ClassName: IValidateExecutorContext 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author 你的名字 
* @date 2013-8-2 上午10:49:45 
*  
*/
public interface IValidateExecutorContext {
	IKeyGenerator getKeyGenerator();

	void setKeyGenerator(IKeyGenerator keyGenerator);
}
