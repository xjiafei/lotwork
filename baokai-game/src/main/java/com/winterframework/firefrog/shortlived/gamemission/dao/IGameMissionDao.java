package com.winterframework.firefrog.shortlived.gamemission.dao;

import java.util.List;

import com.winterframework.firefrog.shortlived.gamemission.dao.entity.GameMission;

public interface IGameMissionDao {

	public List<GameMission> queryMissions(GameMission request);

	public GameMission queryMission(GameMission request);

}

