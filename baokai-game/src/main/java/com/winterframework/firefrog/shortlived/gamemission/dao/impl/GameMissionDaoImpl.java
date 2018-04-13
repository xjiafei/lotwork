package com.winterframework.firefrog.shortlived.gamemission.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.winterframework.firefrog.shortlived.gamemission.dao.IGameMissionDao;
import com.winterframework.firefrog.shortlived.gamemission.dao.entity.GameMission;
import com.winterframework.orm.dal.ibatis3.BaseIbatis3Dao;

@Repository("gameMissionDaoImpl")
public class GameMissionDaoImpl extends BaseIbatis3Dao<GameMission> implements IGameMissionDao {

	@Override
	public List<GameMission> queryMissions(GameMission request) {
		return this.sqlSessionTemplate.selectList(this.getQueryPath("queryMissions"),request);
	}

	@Override
	public GameMission queryMission(GameMission request) {
		return this.sqlSessionTemplate.selectOne(this.getQueryPath("queryMission"),request);
	}

}

