package com.winterframework.firefrog.game.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.firefrog.game.dao.IGameLockParamDao;
import com.winterframework.firefrog.game.dao.IGameSeriesDao;
import com.winterframework.firefrog.game.dao.vo.GameLockParam;
import com.winterframework.firefrog.game.service.IGameLockParamService;

/**
 * 变价参数设置
 * @author 你的名字 
 * @date 2014-5-5 下午5:39:39 
 */
@Service("gameLockParamServiceImpl")
@Transactional(rollbackFor = Exception.class)
public class GameLockParamServiceImpl implements IGameLockParamService {

	@Resource(name = "gameLockParamDaoImpl")
	private IGameLockParamDao gameLockParamDaoImpl;
	
	@Resource(name = "gameSeriesDaoImpl")
	private IGameSeriesDao gameSeriesDao;

	@Override
	public GameLockParam queryGameLockParam(Long gameId) throws Exception {
		return gameLockParamDaoImpl.queryGameLockParam(gameId);
	}

	@Override
	public int updateGameLockParam(GameLockParam gameLockParam) throws Exception {
		Long status = gameLockParam.getStatus();
		gameSeriesDao.updateGameSeriesChangeStatus(gameLockParam.getGameId(), 3, status == 1 ? 3 : status == 2 ? 4
				: status == 3 ? 5 : status == 4 ? 1 : 6);
		return gameLockParamDaoImpl.update(gameLockParam);
	}
}
