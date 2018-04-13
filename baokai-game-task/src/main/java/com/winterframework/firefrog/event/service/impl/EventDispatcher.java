/**   
* @Title: EventDispatcher.java 
* @Package com.winterframework.firefrog.event.service.impl 
* @Description: TODO(用一句话描述该文件做什么) 
* @author 你的名字   
* @date 2014-5-8 上午11:34:32 
* @version V1.0   
*/
package com.winterframework.firefrog.event.service.impl;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.firefrog.event.service.IEventDispatcher;
import com.winterframework.firefrog.event.service.IEventHandler;
import com.winterframework.firefrog.game.dao.vo.GameControlEvent;

/** 
* @ClassName: EventDispatcher 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author 你的名字 
* @date 2014-5-8 上午11:34:32 
*  
*/
@Service("eventDispatcher")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, readOnly = false)
public class EventDispatcher implements IEventDispatcher {
	
	@Resource(name = "eventHandlerMap")
	private Map<Long, IEventHandler> eventHandlerMap;
	
	public Map<Long, IEventHandler> getEventHandlerMap() {
		return eventHandlerMap;
	}

	public void setEventHandlerMap(Map<Long, IEventHandler> eventHandlerMap) {
		this.eventHandlerMap = eventHandlerMap;
	}

	/**
	* Title: dispatchEvent
	* Description:
	* @param event 
	* @see com.winterframework.firefrog.event.service.IEventDispatcher#dispatchEvent(com.winterframework.firefrog.game.dao.vo.GameControlEvent) 
	*/
	@Override
	public void dispatchEvent(GameControlEvent event) throws Exception{
		IEventHandler handler = eventHandlerMap.get(event.getEnentType());
		if(handler!=null){
			handler.handleEvent(event);
		}else{
			throw new RuntimeException("No handler to handle this event type: "+event.getEnentType());
		}
	}

}
