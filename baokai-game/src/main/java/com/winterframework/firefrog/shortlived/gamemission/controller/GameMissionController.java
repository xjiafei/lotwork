package com.winterframework.firefrog.shortlived.gamemission.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.winterframework.firefrog.common.annotation.ValidRequestBody;
import com.winterframework.firefrog.common.redis.RedisClient;
import com.winterframework.firefrog.shortlived.gamemission.dto.GameMissionCompleteRequest;
import com.winterframework.firefrog.shortlived.gamemission.dto.GameMissionCompleteResponse;
import com.winterframework.firefrog.shortlived.gamemission.dto.GameMissionRequest;
import com.winterframework.firefrog.shortlived.gamemission.dto.GameMissionResponse;
import com.winterframework.firefrog.shortlived.gamemission.dto.GameMissionUserDataRequest;
import com.winterframework.firefrog.shortlived.gamemission.dto.GameMissionUserDataResponse;
import com.winterframework.firefrog.shortlived.gamemission.enums.GameMissionCompleteStatus;
import com.winterframework.firefrog.shortlived.gamemission.service.IGameMissionService;
import com.winterframework.firefrog.shortlived.gamemission.vo.GameMissionUserDataVo;
import com.winterframework.firefrog.shortlived.gamemission.vo.GameMissionVo;
import com.winterframework.modules.web.jsonresult.Request;
import com.winterframework.modules.web.jsonresult.Response;

@Controller("gameMissionController")
@RequestMapping(value = "/game")
public class GameMissionController {
	
	private Logger logger = LoggerFactory.getLogger(GameMissionController.class);

	@Resource(name = "gameMissionServiceMap")
	private Map<String,IGameMissionService> gameMissionServiceMap;

	@Resource(name = "RedisClient")
	private RedisClient redisClient;

	@RequestMapping(value = "/queryMissions")
	@ResponseBody
	public Response<GameMissionResponse> queryMissions(
			@RequestBody @ValidRequestBody Request<GameMissionRequest> request) {
		logger.info("/game/queryMissions start");
		GameMissionRequest param = request.getBody().getParam();
		logger.info("missionCode:"+param.getMissionCode());
		logger.info("itemSeq:"+param.getItemSeq());
		IGameMissionService gameMissionService = mappingMissionService(param.getMissionCode());
		Response<GameMissionResponse> response = new Response<GameMissionResponse>();
		List<GameMissionVo> missions = gameMissionService.queryMissions(param);
		GameMissionResponse result = new GameMissionResponse();
		result.setMissions(missions);
		logger.info("missions:"+missions.size());
		response.setResult(result);
		return response;
	}

	@RequestMapping(value = "/queryUserMissionData")
	@ResponseBody
	public Response<GameMissionUserDataResponse> queryUserMissionData(
			@RequestBody @ValidRequestBody Request<GameMissionUserDataRequest> request) {
		logger.info("/game/queryUserMissionData start");
		GameMissionUserDataRequest param = request.getBody().getParam();
		logger.info("missionCode:"+param.getMissionCode());
		logger.info("itemSeq:"+param.getItemSeq());
		logger.info("userId:"+param.getUserId());
		IGameMissionService gameMissionService = mappingMissionService(param.getMissionCode());
		Response<GameMissionUserDataResponse> response = new Response<GameMissionUserDataResponse>();
		List<GameMissionUserDataVo> userDatas = gameMissionService.queryUserMissionData(param);
		GameMissionUserDataResponse result = new GameMissionUserDataResponse();
		result.setUserDatas(userDatas);
		logger.info("userDatas:"+userDatas.size());
		response.setResult(result);
		return response;
	}

	@RequestMapping(value = "/completeMission")
	@ResponseBody
	public Response<GameMissionCompleteResponse> completeMission(
			@RequestBody @ValidRequestBody Request<GameMissionCompleteRequest> request) {
		logger.info("/game/completeMission start");
		Response<GameMissionCompleteResponse> response = new Response<GameMissionCompleteResponse>();
		GameMissionCompleteRequest param = request.getBody().getParam();
		logger.info("missionCode:"+param.getMissionCode());
		logger.info("itemSeq:"+param.getItemSeq());
		logger.info("userId:"+param.getUserId());
		IGameMissionService gameMissionService = mappingMissionService(param.getMissionCode());
		GameMissionRequest req = new GameMissionRequest();
		req.setMissionCode(param.getMissionCode());
		req.setItemSeq(param.getItemSeq());
		GameMissionVo mission = gameMissionService.queryMission(req);
		param.setMissionId(mission.getId());
		logger.info("missionId:"+param.getMissionId());
		GameMissionCompleteResponse result = new GameMissionCompleteResponse();
		try {
			boolean isComplete = gameMissionService.validateUserMssion(mission,
					param);
			if (isComplete) {
				if (redisClient.acquireLock("GAME_MISSION_" + mission.getCode()
						+ param.getUserId(), 5000)) {
					gameMissionService.completeMission(mission,param);
					result.setStatus(GameMissionCompleteStatus.SUCCESS.value());
					logger.info("mission complete success");
				} else {
					result.setStatus(GameMissionCompleteStatus.ERROR_REQUEST_AGAIN.value());
					logger.info("send too much request in the 5 sec.");
				}
			}else{
				result.setStatus(GameMissionCompleteStatus.ERROR_NOT_COMPLETE.value());
				logger.info("mission not complete");
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.setStatus(GameMissionCompleteStatus.ERROR.value());
		}
		response.setResult(result);
		return response;
	}
	
	private IGameMissionService mappingMissionService(String missionCode)throws RuntimeException{
		IGameMissionService service = gameMissionServiceMap.get(missionCode);
		if(service == null){
			throw new RuntimeException("NO THIS MISSION SERVICE");
		}
		return service;
	}

}
