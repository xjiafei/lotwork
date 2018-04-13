package com.winterframework.firefrog.game.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.winterframework.firefrog.game.dao.IGameMultipleDao;
import com.winterframework.firefrog.game.dao.vo.GameMultiple;
import com.winterframework.firefrog.game.dao.vo.VOConverter;
import com.winterframework.firefrog.game.entity.BetLimitAssist;
import com.winterframework.firefrog.game.entity.GameMultipleEntity;
import com.winterframework.orm.dal.ibatis3.BaseIbatis3Dao;

/** 
* @ClassName: GameMultipleDaoImpl 
* @Description: 投注限制DAO实现类
* @author Denny 
* @date 2013-8-25 下午10:21:17 
*  
*/
@Repository("gameMultipleDaoImpl")
public class GameMultipleDaoImpl extends BaseIbatis3Dao<GameMultiple> implements IGameMultipleDao {

	@Override
	public List<GameMultiple> getGameMultipleByLotteryId(long lotteryid) {
		List<GameMultiple> gameMultipleList = sqlSessionTemplate.selectList("com.winterframework.firefrog.game.dao.vo.GameMultiple.getByLotteryId", lotteryid);
		return gameMultipleList;
	}

	@Override
	public void addGameMultiple(long lotteryid) {

	}

	@Override
	public void removeGameMultipleByLotteryId(long lotteryid) {
		List<GameMultiple> gms = this.getGameMultipleByLotteryId(lotteryid);
		for (GameMultiple gm : gms) {
			long id = gm.getId();
			this.delete(id);
		}
		
	}

	@Override
	public void updateGameMultiple(GameMultiple gameMultiple) {

	}
	
	/**
	* Title: queryGameMutiple
	* Description:
	* @param gameMutiple
	* @return 
	* @see com.winterframework.firefrog.game.dao.IGameMutipleDao#queryGameMutiple(com.winterframework.firefrog.game.entity.GameMultipleEntity) 
	*/
	@Override
	public GameMultipleEntity queryGameMultiple(GameMultipleEntity gameMultiple) {
		GameMultiple result = this.sqlSessionTemplate.selectOne("getGameMultilple", VOConverter.gameMultipleEntity2GameMultiple(gameMultiple));
		return VOConverter.gameMutiple2GameMutipleEntity(result);
	}

	@Override
	public void updateGameMultipleList(List<GameMultiple> gameMultipleList) {
		for (GameMultiple gm : gameMultipleList) {
			this.sqlSessionTemplate.update("com.winterframework.firefrog.game.dao.vo.GameMultiple.updateForPublish", gm);
		}
	}

	@Override
	public List<BetLimitAssist> getGameMultipleAssistParentId(long parentId) throws Exception {
		return this.sqlSessionTemplate.selectList("getGameMultipleAssistParentId", parentId);
	}

	@Override
	public void modifyBetLimitAssist(BetLimitAssist assist) throws Exception {
		this.sqlSessionTemplate.update("updateAssist", assist);
	}
	
	
}
