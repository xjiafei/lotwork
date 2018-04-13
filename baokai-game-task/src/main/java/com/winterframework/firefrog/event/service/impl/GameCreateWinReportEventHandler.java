package com.winterframework.firefrog.event.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.firefrog.event.service.IEventHandler;
import com.winterframework.firefrog.game.dao.vo.GameControlEvent;
import com.winterframework.firefrog.game.service.IGameWinsReportService;

@Service("gameCreateWinReportEventHandler")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, readOnly = false)
public class GameCreateWinReportEventHandler implements IEventHandler {

	@Resource(name = "gameWinsReportServiceImpl")
	private IGameWinsReportService gameWinsReportService;
	
	@Override
	public void handleEvent(GameControlEvent event) throws Exception {
		
		gameWinsReportService.createGameWinReport(event.getEndIssueCode(), event.getStartIssueCode());	//lotteryId取值改变
		
	}

}
