/**   
* @Title: SimpleValidatorFactory.java 
* @Package com.winterframework.firefrog.common.validate.business 
* @Description: TODO(用一句话描述该文件做什么) 
* @author 你的名字   
* @date 2013-8-2 下午2:17:32 
* @version V1.0   
*/
package com.winterframework.firefrog.common.validate.business;

import java.util.Map;

/** 
* @ClassName: SimpleValidatorFactory 
* @Description:  
* @author page
* @date 2013-8-2 下午2:17:32 
*  
*/
public class SimpleValidatorFactory<T> implements IValidatorFactory {

	private Map<String, IValidateExecutor<T>> validatorExecutorMap;

	public Map<String, IValidateExecutor<T>> getValidatorExecutorMap() {
		return validatorExecutorMap;
	}

	public void setValidatorExecutorMap(Map<String, IValidateExecutor<T>> validatorExecutorMap) {
		this.validatorExecutorMap = validatorExecutorMap;
	}

	/**
	* Title: getValidateExecuter
	* Description:
	* @param keyGenerator
	* @return 
	* @see com.winterframework.firefrog.common.validate.business.IValidatorFactory#getValidateExecutor(com.winterframework.firefrog.common.validate.business.IKeyGenerator) 
	*/
	@Override
	public IValidateExecutor<T> getValidateExecutor(IKeyGenerator keyGenerator) {
		return validatorExecutorMap.get(keyGenerator.generateKey());
	}

}
