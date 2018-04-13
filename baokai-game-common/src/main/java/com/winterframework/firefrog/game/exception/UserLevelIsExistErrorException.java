package com.winterframework.firefrog.game.exception;

/** 
* @ClassName: UserIsExistErrorException 
* @Description: 用户不存在
* @author 
* @date 2013-7-29 下午2:15:33 
*  
*/
public class UserLevelIsExistErrorException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4772111538039510926L;

	public final static Long CODE = 205001L;

	public final static String MSG = "用户级别不存在";

	public UserLevelIsExistErrorException() {
		super(MSG);
	}

	public UserLevelIsExistErrorException(Throwable cause) {
		super(String.valueOf(CODE), cause);
	}

	public Long getStatus() {
		return CODE;
	}
}
