/**   
* @Title: GameBonusPoolDaoImpl.java 
* @Package com.winterframework.firefrog.game.dao.impl 
* @Description: TODO(用一句话描述该文件做什么) 
* @author 你的名字   
* @date 2014-7-29 下午3:01:44 
* @version V1.0   
*/
package com.winterframework.firefrog.game.dao.impl;

import org.springframework.stereotype.Repository;

import com.winterframework.firefrog.game.dao.IGameBonusPoolDao;
import com.winterframework.firefrog.game.dao.vo.GameBonusPool;
import com.winterframework.orm.dal.ibatis3.BaseIbatis3Dao;

/** 
* @ClassName: GameBonusPoolDaoImpl 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author 你的名字 
* @date 2014-7-29 下午3:01:44 
*  
*/
@Repository("gameBonusPoolDaoImpl")
public class GameBonusPoolDaoImpl extends BaseIbatis3Dao<GameBonusPool> implements IGameBonusPoolDao{

	/**
	* Title: queryGameBonus
	* Description:
	* @param lotteryId
	* @return
	* @throws Exception 
	* @see com.winterframework.firefrog.game.dao.IGameBonusPoolDao#queryGameBonus(java.lang.Long) 
	*/
	@Override
	public GameBonusPool queryGameBonus(Long lotteryId) throws Exception {
		return this.sqlSessionTemplate.selectOne("com.winterframework.firefrog.game.dao.vo.GameBonusPool.getByLotteryId", lotteryId);
	}

}
