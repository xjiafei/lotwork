package com.winterframework.firefrog.game.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.firefrog.common.util.GameContext;
import com.winterframework.firefrog.game.dao.IGamePlanDao;
import com.winterframework.firefrog.game.service.IGameDrawService;
import com.winterframework.firefrog.game.service.IGameIssueReDrawService;
import com.winterframework.firefrog.game.service.IGameOrderService;
import com.winterframework.firefrog.game.service.IGamePlanService;
import com.winterframework.firefrog.game.service.revocation.IGameRevocationIssueService;

@Transactional
@Service("gameIssueReDrawServiceImpl")
public class GameIssueReDrawServiceImpl implements IGameIssueReDrawService { 

	@Resource(name = "gameDrawReDrawServiceImpl")
	private IGameDrawService gameDrawReDrawServiceImpl;  

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
	public void reDrawGameIssue(Long lotteryId, Long issueCode) throws Exception { 
		//调用重新计奖的新开奖流程
		gameDrawReDrawServiceImpl.doBusiness(lotteryId, issueCode);
	}
}
