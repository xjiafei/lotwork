/**   
* @Title: GameBonusPoolServiceImpl.java 
* @Package com.winterframework.firefrog.game.service.impl 
* @Description: TODO(用一句话描述该文件做什么) 
* @author 你的名字   
* @date 2014-7-29 下午3:28:14 
* @version V1.0   
*/
package com.winterframework.firefrog.game.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.firefrog.game.dao.IGameBonusPoolDao;
import com.winterframework.firefrog.game.dao.vo.GameBonusPool;
import com.winterframework.firefrog.game.service.IGameBonusPoolService;

/** 
* @ClassName: GameBonusPoolServiceImpl 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author 你的名字 
* @date 2014-7-29 下午3:28:14 
*  
*/
@Service("gameBonusPoolServiceImpl")
@Transactional(rollbackFor = Exception.class)
public class GameBonusPoolServiceImpl implements IGameBonusPoolService {

	@Resource(name = "gameBonusPoolDaoImpl")
	private IGameBonusPoolDao gameBonusPoolDao;

	/**
	* Title: queryGameBonus
	* Description:
	* @param lotteryId
	* @return
	* @throws Exception 
	* @see com.winterframework.firefrog.game.service.IGameBonusPoolService#queryGameBonus(java.lang.Long) 
	*/
	@Override
	public GameBonusPool queryGameBonus(Long lotteryId) throws Exception {
		return gameBonusPoolDao.queryGameBonus(lotteryId);
	}

	/**
	* Title: updateGameBonusPool
	* Description:
	* @param gameBonusPool
	* @return
	* @throws Exception 
	* @see com.winterframework.firefrog.game.service.IGameBonusPoolService#updateGameBonusPool(com.winterframework.firefrog.game.dao.vo.GameBonusPool) 
	*/
	@Override
	public int updateGameBonusPool(GameBonusPool gameBonusPool) throws Exception {
		if (gameBonusPool.getId() == null) {
			return gameBonusPoolDao.insert(gameBonusPool);
		} else {
			return gameBonusPoolDao.update(gameBonusPool);
		}
	}

}
