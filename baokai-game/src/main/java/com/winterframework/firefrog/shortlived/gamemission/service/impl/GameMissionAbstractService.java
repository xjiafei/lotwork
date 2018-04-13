package com.winterframework.firefrog.shortlived.gamemission.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.winterframework.firefrog.shortlived.gamemission.dao.IGameMissionCompleteDao;
import com.winterframework.firefrog.shortlived.gamemission.dao.IGameMissionDao;
import com.winterframework.firefrog.shortlived.gamemission.dao.entity.GameMission;
import com.winterframework.firefrog.shortlived.gamemission.dao.entity.GameMissionComplete;
import com.winterframework.firefrog.shortlived.gamemission.dao.entity.convertor.GameMissionEntityVoConvertor;
import com.winterframework.firefrog.shortlived.gamemission.dto.GameMissionCompleteRequest;
import com.winterframework.firefrog.shortlived.gamemission.dto.GameMissionRequest;
import com.winterframework.firefrog.shortlived.gamemission.dto.GameMissionUserDataRequest;
import com.winterframework.firefrog.shortlived.gamemission.service.IGameMissionService;
import com.winterframework.firefrog.shortlived.gamemission.vo.GameMissionUserDataVo;
import com.winterframework.firefrog.shortlived.gamemission.vo.GameMissionVo;
import com.winterframework.modules.web.util.JsonMapper;

public abstract class GameMissionAbstractService implements IGameMissionService {
	
	protected JsonMapper jsonMapper = JsonMapper.nonEmptyMapper();
	
	protected Logger logger = LoggerFactory.getLogger(getClass());
	
	@Resource(name = "gameMissionDaoImpl")
	protected IGameMissionDao gameMissionDao;

	@Resource(name = "gameMissionCompleteDaoImpl")
	protected IGameMissionCompleteDao gameMissionCompleteDao;

	@Override
	public List<GameMissionVo> queryMissions(GameMissionRequest request) {
		Date date = new Date();
		GameMission req = new GameMission();
		req.setTargetCode(request.getMissionCode());
		req.setTargetIsActive(1L);
		req.setTargetStartTimeEnd(date);
		req.setTargetEndTimeStart(date);
		List<GameMission> entitys = gameMissionDao.queryMissions(req);
		List<GameMissionVo> results = new ArrayList<GameMissionVo>();
		for (GameMission entity : entitys) {
			results.add(GameMissionEntityVoConvertor
					.convertGameMissionToVo(entity));
		}
		return results;
	}

	@Override
	public GameMissionVo queryMission(GameMissionRequest request) {
		Date date = new Date();
		GameMission req = new GameMission();
		req.setTargetItemSeq(request.getItemSeq());
		req.setTargetCode(request.getMissionCode());
		req.setTargetStartTimeEnd(date);
		req.setTargetEndTimeStart(date);
		req.setTargetIsActive(1L);
		GameMission entity = gameMissionDao.queryMission(req);
		return GameMissionEntityVoConvertor.convertGameMissionToVo(entity);
	}

	@Override
	public void completeMission(GameMissionVo mission,GameMissionCompleteRequest request) {
		GameMissionComplete req = new GameMissionComplete();
		req.setMissionId(request.getMissionId());
		req.setParam(jsonMapper.toJson(mission.getParams()));
		req.setUserId(request.getUserId());
		req.setCreateDate(new Date());
		gameMissionCompleteDao.addGameMissionComplete(req);
		doAfterInsertCompleteMission(mission, request);
	}
	

	@Override
	public abstract List<GameMissionUserDataVo> queryUserMissionData(GameMissionUserDataRequest request);

	@Override
	public abstract boolean validateUserMssion(GameMissionVo mission,GameMissionCompleteRequest request);
	
	protected abstract void doAfterInsertCompleteMission(GameMissionVo mission,GameMissionCompleteRequest request);

}
