/**   
* @Title: FireFrogEventPublisher.java 
* @Package com.winterframework.firefrog.event 
* @Description: TODO(用一句话描述该文件做什么) 
* @author 你的名字   
* @date 2013-10-28 下午4:37:22 
* @version V1.0   
*/
package com.winterframework.firefrog.common.event;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/** 
*  事件发布的超类
*/
@Component("fireFrogEventPublisher")
public class FireFrogEventPublisher implements ApplicationContextAware {
	private ApplicationContext ctx;

	/**
	* Title: setApplicationContext
	* Description:
	* @param applicationContext
	* @throws BeansException 
	* @see org.springframework.context.ApplicationContextAware#setApplicationContext(org.springframework.context.ApplicationContext) 
	*/
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.ctx = applicationContext;
	}

	public void publish(FirefrogEvent event) {
		ctx.publishEvent(event);
	}

}
