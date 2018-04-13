package com.winterframework.firefrog.shortlived.controller;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.winterframework.firefrog.common.httpjsonclient.IHttpJsonClient;
import com.winterframework.firefrog.shortlived.gamemission.dto.GameMissionCompleteRequest;
import com.winterframework.firefrog.shortlived.gamemission.dto.GameMissionCompleteResponse;
import com.winterframework.firefrog.shortlived.gamemission.dto.GameMissionRequest;
import com.winterframework.firefrog.shortlived.gamemission.dto.GameMissionResponse;
import com.winterframework.firefrog.shortlived.gamemission.dto.GameMissionUserDataRequest;
import com.winterframework.firefrog.shortlived.gamemission.dto.GameMissionUserDataResponse;
import com.winterframework.modules.spring.exetend.PropertyConfig;
import com.winterframework.modules.web.jsonresult.Response;
import com.winterframework.modules.web.util.RequestContext;

@Controller("gameMissionController")
@RequestMapping(value = "/gamemission")
public class GameMissionController {

	private Logger logger = LoggerFactory
			.getLogger(GameMissionController.class);

	@Resource(name = "httpJsonClientImpl")
	private IHttpJsonClient httpClient;

	@PropertyConfig(value = "url.connect")
	private String serverPath;

	@PropertyConfig(value = "url.game.queryGameMissions")
	private String queryGameMissionsUrl;

	@PropertyConfig(value = "url.game.queryUserMissionData")
	private String queryUserMissionDataUrl;

	@PropertyConfig(value = "url.game.completeMission")
	private String completeMissionUrl;

	@RequestMapping(value = "/queryMissions")
	@ResponseBody
	public GameMissionResponse queryMissions(GameMissionRequest request)
			throws Exception {
		logger.info("/gamemission/queryMissions start");
		logger.info("missionCode:"+request.getMissionCode());
		logger.info("itemSeq:"+request.getItemSeq());
		Response<GameMissionResponse> result = httpClient.invokeHttp(serverPath
				+ queryGameMissionsUrl, request, GameMissionResponse.class);
		return result.getBody().getResult();
	}

	@RequestMapping(value = "/querUserMissionData")
	@ResponseBody
	public GameMissionUserDataResponse querUserMissionData(
			GameMissionUserDataRequest request) throws Exception {
		logger.info("/gamemission/querUserMissionData start");
		logger.info("missionCode:"+request.getMissionCode());
		logger.info("itemSeq:"+request.getItemSeq());
		request.setUserId(RequestContext.getCurrUser().getId());
		Response<GameMissionUserDataResponse> result = httpClient.invokeHttp(
				serverPath + queryUserMissionDataUrl, request,
				GameMissionUserDataResponse.class);
		return result.getBody().getResult();
	}

	@RequestMapping(value = "/completeMission")
	@ResponseBody
	public GameMissionCompleteResponse completeMission(
			GameMissionCompleteRequest request) throws Exception {
		logger.info("/gamemission/completeMission start");
		logger.info("missionCode:"+request.getMissionCode());
		logger.info("itemSeq:"+request.getItemSeq());
		request.setUserId(RequestContext.getCurrUser().getId());
		Response<GameMissionCompleteResponse> result = httpClient.invokeHttp(
				serverPath + completeMissionUrl, request,
				GameMissionCompleteResponse.class);
		return result.getBody().getResult();
	}
}
