/**   
* @Title: FireFrogEvent.java 
* @Package com.winterframework.firefrog.event 
* @Description: TODO(用一句话描述该文件做什么) 
* @author 你的名字   
* @date 2013-10-28 下午4:07:44 
* @version V1.0   
*/
package com.winterframework.firefrog.common.event;

import org.springframework.context.ApplicationEvent;

/** 
* @ClassName: FireFrogEvent 
* @Description: Event class for firefrog application. All event should implement this class. 
* @author page
* @date 2013-10-28 下午4:07:44 
*  
*/
public class FirefrogEvent extends ApplicationEvent {
	/** 
	* @Fields serialVersionUID
	*/ 
	private static final long serialVersionUID = -7823648197977287037L;

	/** 
	* Title:
	* Description:
	* @param source 
	*/
	public FirefrogEvent(Object source) {
		super(source);
	}

	
}
