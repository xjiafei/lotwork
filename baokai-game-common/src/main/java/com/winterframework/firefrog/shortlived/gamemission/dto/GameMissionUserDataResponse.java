package com.winterframework.firefrog.shortlived.gamemission.dto;

import java.util.List;

import com.winterframework.firefrog.shortlived.gamemission.vo.GameMissionUserDataVo;

public class GameMissionUserDataResponse {

	private List<GameMissionUserDataVo> userDatas;

	public List<GameMissionUserDataVo> getUserDatas() {
		return userDatas;
	}

	public void setUserDatas(List<GameMissionUserDataVo> userDatas) {
		this.userDatas = userDatas;
	}

}
