package com.winterframework.firefrog.game.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.firefrog.game.dao.IGameWarnIssueDao;
import com.winterframework.firefrog.game.dao.vo.GameWarnIssue;
import com.winterframework.firefrog.game.service.IGameWarnIssueService;

/** 
* @ClassName: GamWarnIssueServiceImpl 
* @Description: 风险奖期service 
* @author david 
* @date 2014-3-7 下午1:34:37 
*  
*/
@Service("gameWarnIssueServiceImpl")
@Transactional(rollbackFor = Exception.class)
public class GamWarnIssueServiceImpl implements IGameWarnIssueService {
		
	@Resource(name = "gameWarnIssueDaoImpl")
	private IGameWarnIssueDao gameWarnIssueDao;

	@Override
	public GameWarnIssue getGameWarnIssueById(Long warnIssueId) {
		return gameWarnIssueDao.getById(warnIssueId);
	}

	@Override
	public void updateGameWarnIssue(GameWarnIssue gameWarnIssue) {
		gameWarnIssueDao.update(gameWarnIssue);
	}


}
