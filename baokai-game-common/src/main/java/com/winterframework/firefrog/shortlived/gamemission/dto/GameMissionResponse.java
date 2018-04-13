package com.winterframework.firefrog.shortlived.gamemission.dto;

import java.util.List;

import com.winterframework.firefrog.shortlived.gamemission.vo.GameMissionVo;

public class GameMissionResponse {

	private List<GameMissionVo> missions;

	public List<GameMissionVo> getMissions() {
		return missions;
	}

	public void setMissions(List<GameMissionVo> missions) {
		this.missions = missions;
	}

}
