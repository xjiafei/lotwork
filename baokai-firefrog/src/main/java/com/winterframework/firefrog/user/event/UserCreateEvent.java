package com.winterframework.firefrog.user.event;

import com.winterframework.firefrog.common.event.FirefrogEvent;

/** 
*  创建用户时的事件
*/
public class UserCreateEvent extends FirefrogEvent {


	/** 
	* @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么) 
	*/ 
	private static final long serialVersionUID = -8628205624005395539L;

	public UserCreateEvent(Object source) {
		super(source);
	}

}
