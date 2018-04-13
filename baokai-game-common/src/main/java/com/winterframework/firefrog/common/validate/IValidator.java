package com.winterframework.firefrog.common.validate;

public interface IValidator {

	/**
	 * 对Request参数进行验证，如果验证失败，抛出ValidExcetion
	 * 
	 * @param t
	 *            泛型，Request参数中的DTO
	 */
	<T> void validate(T t);

}