package com.winterframework.firefrog.game.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.firefrog.game.dao.IGameMultipleDao;
import com.winterframework.firefrog.game.entity.BetLimitAssist;
import com.winterframework.firefrog.game.entity.GameMultipleEntity;
import com.winterframework.firefrog.game.service.IGameMultipleService;

/** 
* @ClassName: GameMutipleServiceImpl 
* @Description: 投注限制Service Impl 
* @author Alan
* @date 2013-8-26 下午1:56:12 
*  
*/
@Service("gameMultipleServiceImpl")
@Transactional(rollbackFor = Exception.class)
public class GameMultipleServiceImpl implements IGameMultipleService {
	
	@Resource(name = "gameMultipleDaoImpl")
	private IGameMultipleDao gameMultipleDao;

	@Override
	public Integer queryMaxMutiple(GameMultipleEntity entity) {
		GameMultipleEntity gameMultiple = gameMultipleDao.queryGameMultiple(entity);
		return gameMultiple.getMaxMultiple();
	}

	@Override
	public GameMultipleEntity queryGameMultiple(GameMultipleEntity entity) {
		GameMultipleEntity gameMultiple = gameMultipleDao.queryGameMultiple(entity);
		return gameMultiple;
	}

	@Override
	public List<BetLimitAssist> getGameMultipleAssistParentId(long id) throws Exception {
		return gameMultipleDao.getGameMultipleAssistParentId(id);
	}

}
