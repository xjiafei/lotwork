package com.winterframework.firefrog.user.exception;

import com.winterframework.firefrog.common.exception.ServiceException;

/**
 * 判断用户存在的exception.
 * 
 * 继承自ServiceException, 从由Spring管理事务的函数中抛出时会触发事务回滚.
 * 
 */
public class UserExistServiceException extends ServiceException {

	/** 
	* @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么) 
	*/ 
	private static final long serialVersionUID = -3181614323070536329L;

	public UserExistServiceException() {
		super();
	}

	public UserExistServiceException(String description) {
		super(description);
	}

	public UserExistServiceException(Throwable cause) {
		super(cause);
	}
}
