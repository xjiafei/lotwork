package com.winterframework.firefrog.game.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.winterframework.firefrog.game.dao.IGameIssueDao;
import com.winterframework.firefrog.game.dao.vo.GameRiskIssue;
import com.winterframework.firefrog.game.service.IGameIssueService;
import com.winterframework.firefrog.game.web.dto.GameRiskIssueListQueryRequest;
import com.winterframework.modules.page.Page;
import com.winterframework.modules.page.PageRequest;

@Service("gameIssueServiceImpl")
public class GameIssueServiceImpl implements IGameIssueService {

	@Resource(name = "gameIssueDaoImpl")
	private IGameIssueDao gameIssueDao;

	/**
	* @Title: queryRiskGameIssues
	* @Description: 查询审核奖期list 
	* @param pr
	* @return 
	* @see com.winterframework.firefrog.game.service.IGameIssueService#queryRiskGameIssues(com.winterframework.modules.page.PageRequest) 
	*/
	@Override
	public Page<GameRiskIssue> queryRiskGameIssues(PageRequest<GameRiskIssueListQueryRequest> pr) {
		return gameIssueDao.queryRiskGameIssues(pr);
	}

	/**
	* Title: setOperator
	* Description:
	* @param gameIssue
	* @throws Exception 
	* @see com.winterframework.firefrog.game.service.IGameIssueService#setOperator(com.winterframework.firefrog.game.dao.vo.GameIssue) 
	*/
	@Override
	public void setOperator(GameRiskIssue gameIssue) throws Exception {
		this.gameIssueDao.setOperator(gameIssue);

	}

}
