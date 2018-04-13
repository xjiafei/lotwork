package com.winterframework.firefrog.event.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.firefrog.event.service.IEventHandler;
import com.winterframework.firefrog.game.dao.vo.GameControlEvent;
import com.winterframework.firefrog.game.service.IGameDrawResultService;

/**
 * @ClassName: GameDrawECResultEventHandler
 * @Description: EC開獎任務處理
 * @author Leo_Chen
 * 
 */
@Service("gameDrawECResultEventHandler")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, readOnly = false)
public class GameDrawECResultEventHandler implements IEventHandler {

	@Resource(name = "gameDrawResultServiceImpl")
	private IGameDrawResultService gameDrawResultServiceImpl;

	@Override
	public void handleEvent(GameControlEvent event) throws Exception {
		gameDrawResultServiceImpl.addOrUpdateDrawResult(event);
	}
}
