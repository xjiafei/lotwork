package com.winterframework.firefrog.shortlived.gamemission.dao;

import java.util.List;

import com.winterframework.firefrog.shortlived.gamemission.dao.entity.GameMissionComplete;
import com.winterframework.firefrog.shortlived.gamemission.dao.entity.GameMissionUserData;

public interface IGameMissionCompleteDao {

	public void addGameMissionComplete(GameMissionComplete request);

	public List<GameMissionUserData> queryMissionCompletes(
			GameMissionComplete request);

	public Long queryUserDiamondCount(GameMissionUserData request);

}

