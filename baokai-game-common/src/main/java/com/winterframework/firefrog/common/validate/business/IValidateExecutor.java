package com.winterframework.firefrog.common.validate.business;

/** 
* @ClassName: IValidatorExecutor 
* @Description: 验证执行器，主要执行验证功能.
* @author page
* @date 2013-8-2 上午10:48:33 
*  
*/
public interface IValidateExecutor<T> {
	String getName();

	void execute(T validatedBean, IValidateExecutorContext context) throws BusinessValidateException, Exception;
}
