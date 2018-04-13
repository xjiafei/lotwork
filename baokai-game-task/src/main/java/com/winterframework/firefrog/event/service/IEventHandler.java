/**   
* @Title: IEventHandler.java 
* @Package com.winterframework.firefrog.event.service 
* @Description: TODO(用一句话描述该文件做什么) 
* @author 你的名字   
* @date 2014-5-8 上午9:12:27 
* @version V1.0   
*/
package com.winterframework.firefrog.event.service;

import com.winterframework.firefrog.game.dao.vo.GameControlEvent;

/** 
* @ClassName: IEventHandler 
* @Description: 
* @author page
* @date 2014-5-8 上午9:12:27 
*  
*/
public interface IEventHandler {
	void handleEvent(GameControlEvent event) throws Exception;
}
