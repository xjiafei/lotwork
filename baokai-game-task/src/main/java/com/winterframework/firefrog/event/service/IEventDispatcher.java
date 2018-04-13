/**   
* @Title: IEventDispatcher.java 
* @Package com.winterframework.firefrog.event.service 
* @Description: TODO(用一句话描述该文件做什么) 
* @author 你的名字   
* @date 2014-5-8 上午9:12:12 
* @version V1.0   
*/
package com.winterframework.firefrog.event.service;

import com.winterframework.firefrog.game.dao.vo.GameControlEvent;

/** 
* @ClassName: IEventDispatcher 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author 你的名字 
* @date 2014-5-8 上午9:12:12 
*  
*/
public interface IEventDispatcher {

	void dispatchEvent(GameControlEvent event) throws Exception;

}
