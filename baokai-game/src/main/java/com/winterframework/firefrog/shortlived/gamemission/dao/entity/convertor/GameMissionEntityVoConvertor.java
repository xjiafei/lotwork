package com.winterframework.firefrog.shortlived.gamemission.dao.entity.convertor;

import com.winterframework.firefrog.shortlived.gamemission.dao.entity.GameMission;
import com.winterframework.firefrog.shortlived.gamemission.dao.entity.GameMissionComplete;
import com.winterframework.firefrog.shortlived.gamemission.vo.GameMissionCompleteVo;
import com.winterframework.firefrog.shortlived.gamemission.vo.GameMissionVo;

public class GameMissionEntityVoConvertor {

	public static GameMissionVo convertGameMissionToVo(GameMission entity) {

		GameMissionVo vo = new GameMissionVo();
		vo.setId(entity.getId());
		vo.setCode(entity.getCode());
		vo.setItemSeq(entity.getItemSeq());
		vo.setName(entity.getName());
		vo.setStartTime(entity.getStartTime());
		vo.setEndTime(entity.getEndTime());
		vo.setParams(entity.getParams());
		vo.setIsActive(entity.getIsActive());
		vo.setCreateDate(entity.getCreateDate());
		return vo;
	}

	public static GameMission convertGameMissionToEntity(GameMissionVo vo) {

		GameMission entity = new GameMission();
		entity.setId(vo.getId());
		entity.setCode(vo.getCode());
		entity.setItemSeq(vo.getItemSeq());
		entity.setName(vo.getName());
		entity.setStartTime(vo.getStartTime());
		entity.setEndTime(vo.getEndTime());
		entity.setParams(vo.getParams());
		entity.setIsActive(vo.getIsActive());
		entity.setCreateDate(vo.getCreateDate());
		return entity;
	}

	public static GameMissionCompleteVo convertGameMissionCompleteToVo(
			GameMissionComplete entity) {

		GameMissionCompleteVo vo = new GameMissionCompleteVo();
		vo.setId(entity.getId());
		vo.setMissionId(entity.getMissionId());
		vo.setUserId(entity.getUserId());
		vo.setParam(entity.getParam());
		vo.setCreateDate(entity.getCreateDate());
		return vo;
	}

	public static GameMissionComplete convertGameMissionCompleteToEntity(
			GameMissionCompleteVo vo) {

		GameMissionComplete entity = new GameMissionComplete();
		entity.setId(vo.getId());
		entity.setMissionId(vo.getMissionId());
		entity.setUserId(vo.getUserId());
		entity.setParam(vo.getParam());
		entity.setCreateDate(vo.getCreateDate());
		return entity;
	}
}
