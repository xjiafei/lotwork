package com.winterframework.firefrog.game.dao.impl;

import org.springframework.stereotype.Repository;

import com.winterframework.firefrog.game.dao.IGameSeriesConfigCheckDao;
import com.winterframework.firefrog.game.dao.vo.GameSeriesConfigCheck;
import com.winterframework.firefrog.game.dao.vo.VOConverter;
import com.winterframework.firefrog.game.entity.GameSeriesConfigEntity;
import com.winterframework.orm.dal.ibatis3.BaseIbatis3Dao;

/** 
* @ClassName: GameSeriesConfigCheckDaoImpl 
* @Description: 运营参数Check操作DAO
* @author Alan
* @date 2013-9-17 下午6:02:48 
*  
*/
@Repository("gameSeriesConfigCheckDaoImpl")
public class GameSeriesConfigCheckDaoImpl extends BaseIbatis3Dao<GameSeriesConfigCheck> implements
		IGameSeriesConfigCheckDao {

	@Override
	public GameSeriesConfigEntity getGameSeriesConfigByLotteryId(Long lotteryId) {
		GameSeriesConfigCheck vo = this.sqlSessionTemplate.
				selectOne("com.winterframework.firefrog.game.dao.vo.GameSeriesConfigCheck.getByLotteryId", lotteryId);
		if(null == vo){
			return null;
		}
		GameSeriesConfigEntity entity = VOConverter.gameSeriesConfigCheck2GameSeriesConfigEntity(vo);
		return entity;
	}

	@Override
	public void updateGameSeriesConfigCheck(GameSeriesConfigEntity gameSeriesConfig) throws Exception {
		GameSeriesConfigCheck vo = VOConverter.gameSeriesConfigEntity2GameSeriesConfigCheck(gameSeriesConfig);
		this.update(vo);
	}

	@Override
	public void deleteGameSeriesConfigCheck(Long lotteryId) {
		this.sqlSessionTemplate.
		delete("com.winterframework.firefrog.game.dao.vo.GameSeriesConfigCheck.deleteGameSeriesConfigCheckByLotteryId", lotteryId);
	}

}
