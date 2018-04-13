package com.winterframework.firefrog.shortlived.gamemission.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.winterframework.firefrog.shortlived.gamemission.dao.IGameMissionCompleteDao;
import com.winterframework.firefrog.shortlived.gamemission.dao.entity.GameMissionComplete;
import com.winterframework.firefrog.shortlived.gamemission.dao.entity.GameMissionUserData;
import com.winterframework.orm.dal.ibatis3.BaseIbatis3Dao;

@Repository("gameMissionCompleteDaoImpl")
public class GameMissionCompleteDaoImpl extends
		BaseIbatis3Dao<GameMissionComplete> implements IGameMissionCompleteDao {

	@Override
	public void addGameMissionComplete(GameMissionComplete request) {
		this.sqlSessionTemplate.insert(this.getQueryPath("insert"), request);
	}

	@Override
	public List<GameMissionUserData> queryMissionCompletes(
			GameMissionComplete request) {
		return this.sqlSessionTemplate.selectList(
				this.getQueryPath("queryMissionCompletes"), request);
	}

	@Override
	public Long queryUserDiamondCount(GameMissionUserData request){
		return this.sqlSessionTemplate.selectOne(
				this.getQueryPath("queryUserDiamondCount"), request);
	}

}