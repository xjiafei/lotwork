package com.winterframework.firefrog.shortlived.poolking.controller;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.winterframework.firefrog.common.annotation.ValidRequestBody;
import com.winterframework.firefrog.shortlived.poolking.dao.vo.LeaderboardVO;
import com.winterframework.firefrog.shortlived.poolking.dao.vo.MonkeyHistoryRequest;
import com.winterframework.firefrog.shortlived.poolking.dto.PoolKingScore;
import com.winterframework.firefrog.shortlived.poolking.dto.PoolKingScoreRequest;
import com.winterframework.firefrog.shortlived.poolking.dto.PoolKingScoreResponse;
import com.winterframework.firefrog.shortlived.poolking.service.ILeaderboardService;
import com.winterframework.firefrog.shortlived.poolking.service.IPoolKingService;
import com.winterframework.modules.web.jsonresult.Pager;
import com.winterframework.modules.web.jsonresult.Request;
import com.winterframework.modules.web.jsonresult.Response;

@Controller("poolKingController")
@RequestMapping(value = "/game")
public class PoolKingController {

	private Logger logger = LoggerFactory.getLogger(PoolKingController.class);
	
	@Resource(name = "poolKingServiceImpl")
	private IPoolKingService poolKingService;
	
	@Resource(name = "leaderboardServiceImpl")
	private ILeaderboardService leaderboardService;

	@RequestMapping(value = "/queryPoolKingScore")
	@ResponseBody
	public Response<PoolKingScoreResponse> queryPoolKingScore(
			@RequestBody @ValidRequestBody Request<PoolKingScoreRequest> request)
			throws Exception {
		logger.info("start queryPoolKingScore");
		Response<PoolKingScoreResponse> response = new Response<PoolKingScoreResponse>();
		
		PoolKingScoreRequest param = request.getBody().getParam();
		logger.info("==startDate:"+param.getStartDate());
		logger.info("==endDate:"+param.getEndDate());
		Pager pager = new Pager(1,100);
		
		List<PoolKingScore> scores = poolKingService.queryPoolKingScore(param,pager);
		
		PoolKingScoreResponse result = new PoolKingScoreResponse(); 
		result.setScores(scores);
		response.setResult(result);
		
		
		logger.info("end queryPoolKingScore");
		return response;
	}

	@RequestMapping(value = "/queryPoolKingUserScore")
	@ResponseBody
	public Response<PoolKingScoreResponse> queryPoolKingUserScore(
			@RequestBody @ValidRequestBody Request<PoolKingScoreRequest> request)
			throws Exception {
		logger.info("start queryPoolKingUserScore");
		Response<PoolKingScoreResponse> response = new Response<PoolKingScoreResponse>();
		
		PoolKingScoreRequest param = request.getBody().getParam();
		Long userId = request.getHead().getUserId();
		logger.info("==startDate:"+param.getStartDate());
		logger.info("==endDate:"+param.getEndDate());
		logger.info("==userId:"+userId);
		
		PoolKingScore score = poolKingService.queryPoolKingUserScore(param,userId);
		
		PoolKingScoreResponse result = new PoolKingScoreResponse(); 
		result.setUserScore(score);
		response.setResult(result);
		logger.info("end queryPoolKingUserScore");
		return response;
	}
	
	
	@RequestMapping(value = "/queryMonkeyActivityScore")
	@ResponseBody
	public Response<PoolKingScoreResponse> queryMonkeyActivityScore(
			@RequestBody @ValidRequestBody Request<PoolKingScoreRequest> request)
			throws Exception {
		logger.info("start queryMonkeyActivityScore");
		Response<PoolKingScoreResponse> response = new Response<PoolKingScoreResponse>();
		
		PoolKingScoreRequest param = request.getBody().getParam();
		logger.info("==startDate:"+param.getStartDate());
		logger.info("==endDate:"+param.getEndDate());
		Pager pager = new Pager(1,100);
		
		List<PoolKingScore> scores = poolKingService.queryMonkeyActivityScore(param,pager);
		
		PoolKingScoreResponse result = new PoolKingScoreResponse(); 
		result.setScores(scores);
		response.setResult(result);
		
		
		logger.info("end queryMonkeyActivityScore");
		return response;
	}
	
	@RequestMapping(value = "/queryScoresHistory")
	@ResponseBody
	public Response<List<LeaderboardVO>> queryScoresHistory(
			@RequestBody @ValidRequestBody Request<MonkeyHistoryRequest> request)
			throws Exception {
		logger.info("start queryScoresHistory");
		Response<List<LeaderboardVO>> response = new Response<List<LeaderboardVO>>();
		
		List<LeaderboardVO> scores =leaderboardService.queryScoresHistory(request.getBody().getParam());
		response.setResult(scores);
		logger.info("end queryScoresHistory");
		return response;
	}

}
