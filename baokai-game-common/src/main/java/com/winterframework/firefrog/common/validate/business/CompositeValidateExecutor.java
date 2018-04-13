package com.winterframework.firefrog.common.validate.business;

import java.util.List;

/** 
* @ClassName: CompositeValidateExecutor 
* @Description: 使用组合模式，遍历所有子验证器进行验证。 
* @author page
* @date 2013-8-2 上午11:22:11 
*  
*/
public class CompositeValidateExecutor<T> implements IValidateExecutor<T> {
	private String name;

	private List<CompositeValidateExecutor<T>> validateExecutors;

	public void setValidateExecutors(List<CompositeValidateExecutor<T>> validateExecutors) {
		this.validateExecutors = validateExecutors;
	}

	public List<CompositeValidateExecutor<T>> getValidateExecutors() {
		return validateExecutors;
	}

	public void setName(String name) {
		this.name = name;
	}

	/**
	* Title: getName
	* Description:
	* @return 
	* @see com.winterframework.firefrog.common.validate.business.IValidateExecutor#getName() 
	*/
	@Override
	public String getName() {
		return this.name;
	}

	/**
	* Title: execute
	* Description:
	* @param validatedBean
	* @param context 
	 * @throws Exception 
	* @see com.winterframework.firefrog.common.validate.business.IValidateExecutor#execute(java.lang.Object, com.winterframework.firefrog.common.validate.business.IValidateExecutorContext) 
	*/
	@Override
	public void execute(T validatedBean, IValidateExecutorContext context) throws Exception {
		for (CompositeValidateExecutor<T> validator : validateExecutors) {
			validator.execute(validatedBean, context);
		}
	}

}
