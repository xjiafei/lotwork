package com.winterframework.firefrog.game.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.firefrog.game.dao.IGameWarnIssueLogDao;
import com.winterframework.firefrog.game.dao.vo.GameWarnIssueLog;
import com.winterframework.firefrog.game.service.IGameWarnIssueLogService;
/**
 * 
* @ClassName: GamWarnIssueLogServiceImpl 
* @Description: 风险奖期日志service实现类
* @author Richard & Alan
* @date 2013-12-27 下午4:29:40 
*
 */
@Service("gameWarnIssueLogServiceImpl")
@Transactional(rollbackFor = Exception.class)
public class GamWarnIssueLogServiceImpl implements IGameWarnIssueLogService {
	
	@Resource(name = "gameWarnIssueLogDaoImpl")
	private IGameWarnIssueLogDao gameWarnIssueLogDao;

	@Override
	public void addWarnIssueLog(GameWarnIssueLog log) throws Exception {
		gameWarnIssueLogDao.addGameWarnIssueLog(log);
	}

	@Override
	public void saveGameWarnIssueLog(GameWarnIssueLog gameWarnIssueLog) {
		gameWarnIssueLogDao.insert(gameWarnIssueLog);
	}


}
