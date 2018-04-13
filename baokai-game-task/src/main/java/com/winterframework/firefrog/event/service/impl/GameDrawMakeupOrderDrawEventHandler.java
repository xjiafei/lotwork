/**   
* @Title: GameIssueReDrawEventHandler.java 
* @Package com.winterframework.firefrog.event.service.impl 
* @Description: TODO(用一句话描述该文件做什么) 
* @author 你的名字   
* @date 2014-5-8 下午3:33:54 
* @version V1.0   
*/
package com.winterframework.firefrog.event.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.firefrog.event.service.IEventHandler;
import com.winterframework.firefrog.game.dao.vo.GameControlEvent;
import com.winterframework.firefrog.game.service.IGameDrawService;
 
/**
 *GameDrawMakeupOrderDrawEventHandler
 * @ClassName
 * @Description	补充订单开奖事件处理类
 * @author ibm
 * 2014年8月22日
 */
@Service("gameDrawMakeupOrderDrawEventHandler")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, readOnly = false)
public class GameDrawMakeupOrderDrawEventHandler implements IEventHandler {
	@Autowired //(name = "gameDrawMakeupOrderDrawServiceImpl")
	private IGameDrawService gameDrawMakeupOrderDrawServiceImpl;
 
	@Override
	public void handleEvent(GameControlEvent event) throws Exception { 
		gameDrawMakeupOrderDrawServiceImpl.doBusiness(event);
	}
}
