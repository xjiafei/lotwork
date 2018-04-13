/**   
* @Title: GameIssueReDrawEventHandler.java 
* @Package com.winterframework.firefrog.event.service.impl 
* @Description: TODO(用一句话描述该文件做什么) 
* @author 你的名字   
* @date 2014-5-8 下午3:33:54 
* @version V1.0   
*/
package com.winterframework.firefrog.event.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.firefrog.event.service.IEventHandler;
import com.winterframework.firefrog.game.dao.vo.GameControlEvent;
import com.winterframework.firefrog.game.service.revocation.IGameRevocationIssueService;

/** 
* @ClassName: GameRevocationIssueEventHandler 
* @Description: 撤销奖期
* @author 你的名字 
* @date 2014-5-8 下午4:15:36 
*  
*/
@Service("gameRevocationIssueEventHandler")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, readOnly = false)
public class GameRevocationIssueEventHandler implements IEventHandler {
	@Resource(name = "gameRevocationIssueServiceImpl")
	private IGameRevocationIssueService gameRevocationIssueServiceImpl;

	/**
	* Title: handleEvent
	* Description:
	* @param event
	* @throws Exception 
	* @see com.winterframework.firefrog.event.service.IEventHandler#handleEvent(com.winterframework.firefrog.game.dao.vo.GameControlEvent) 
	*/
	@Override
	public void handleEvent(GameControlEvent event) throws Exception {
		gameRevocationIssueServiceImpl.doRevocation(event.getLotteryid(), event.getStartIssueCode());
	}
}
