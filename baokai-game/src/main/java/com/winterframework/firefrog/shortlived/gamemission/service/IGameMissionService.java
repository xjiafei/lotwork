package com.winterframework.firefrog.shortlived.gamemission.service;

import java.util.List;

import com.winterframework.firefrog.shortlived.gamemission.dto.GameMissionCompleteRequest;
import com.winterframework.firefrog.shortlived.gamemission.dto.GameMissionRequest;
import com.winterframework.firefrog.shortlived.gamemission.dto.GameMissionUserDataRequest;
import com.winterframework.firefrog.shortlived.gamemission.vo.GameMissionUserDataVo;
import com.winterframework.firefrog.shortlived.gamemission.vo.GameMissionVo;

public interface IGameMissionService {

	public List<GameMissionVo> queryMissions(GameMissionRequest request);

	public GameMissionVo queryMission(GameMissionRequest request);

	public List<GameMissionUserDataVo> queryUserMissionData(
			GameMissionUserDataRequest request);

	public boolean validateUserMssion(GameMissionVo mission,
			GameMissionCompleteRequest request);

	public void completeMission(GameMissionVo mission,GameMissionCompleteRequest request);
	
}
