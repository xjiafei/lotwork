package com.winterframework.firefrog.game.dao.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.winterframework.firefrog.game.dao.IGameRiskConfigDao;
import com.winterframework.firefrog.game.entity.GameRiskConfig;
import com.winterframework.orm.dal.ibatis3.BaseIbatis3Dao;

@Repository("gameRiskConfigDaoImpl")
public class GameRiskConfigDaoImpl extends BaseIbatis3Dao<GameRiskConfig> implements IGameRiskConfigDao {

	/**
	* Title: queryGameRiskConfig
	* Description: 查询审核参数信息
	* @return GameRiskConfig
	* @throws Exception 
	* @see com.winterframework.firefrog.game.dao.IGameRiskConfigDao#queryGameRiskConfig() 
	*/
	@Override
	public GameRiskConfig queryGameRiskConfig(Long lotteryId) throws Exception {		
		GameRiskConfig config = this.sqlSessionTemplate.selectOne("selectGameRiskConfig",lotteryId);
		return config;
	}

	@Override
	public void insertGameRiskConfig(GameRiskConfig config) throws Exception {
		this.insert(config);
	}

	@Override
	public void updateGameRiskConfig(GameRiskConfig config) throws Exception {
		this.update(config);
	}

}
