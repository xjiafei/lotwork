package com.winterframework.firefrog.game.web.controller;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.winterframework.firefrog.common.annotation.ValidRequestBody;
import com.winterframework.firefrog.common.util.ProcessResult;
import com.winterframework.firefrog.game.service.ICommonGamePlanService;
import com.winterframework.firefrog.game.service.IGamePlanService;
import com.winterframework.firefrog.game.web.dto.GamePlanDTO;
import com.winterframework.firefrog.game.web.dto.GenerateGamePlanRequest;
import com.winterframework.firefrog.game.web.dto.GenerateGamePlanResponse;
import com.winterframework.modules.web.jsonresult.Request;
import com.winterframework.modules.web.jsonresult.Response;

@Controller("taskGamePlanController")
@RequestMapping("/taskGame")
public class GamePlanController {
	
	private static final Logger log = LoggerFactory.getLogger(GamePlanController.class);
	
	@Resource(name = "generateGamePlanServiceImpl")
	private IGamePlanService gamePlanService;
	
	@Resource(name="gamePlanService")
	private ICommonGamePlanService commonGamePlanService;

	@RequestMapping(value = "/generateGamePlan")
	@ResponseBody
	public Response<GenerateGamePlanResponse> generateGamePlan(
			@RequestBody @ValidRequestBody Request<GenerateGamePlanRequest> request) throws Exception {

		Response<GenerateGamePlanResponse> response = new Response<GenerateGamePlanResponse>(request);

		try {
			
			GenerateGamePlanRequest gp = request.getBody().getParam();
			for(GamePlanDTO plan: gp.getPlanList()){
				Long lotteryId=gp.getLotteryId();
				ProcessResult result=new ProcessResult();
				gamePlanService.generateGamePlan(result,lotteryId, gp.getIssueCode(), plan.getGamePlanId());
				
				//生成调度任务(可能存在多个奖期）
				List<Long> issueCodeList = (List<Long>) result.getFromRetParaMap(String.valueOf(lotteryId));
				if (issueCodeList != null) {
					for (Long ret_issueCode : issueCodeList) {
						//这里不作奖期是否开奖判断，因为存在并发，添加调度任务，由调度控制并发
						commonGamePlanService.addMakeupOrderDrawEvent(lotteryId, ret_issueCode);
					}
				}
			}

		} catch (Exception e) {

			log.error("generateGamePlan error:", e);
			response.getHead().setStatus(1);
		}

		return response;
	}
	
	@RequestMapping(value = "/pauseGamePlan")
	@ResponseBody
	public Response<GenerateGamePlanResponse> pauseGamePlan(
			@RequestBody @ValidRequestBody Request<GenerateGamePlanRequest> request) throws Exception{
		
		Response<GenerateGamePlanResponse> response = new Response<GenerateGamePlanResponse>(request);
		try {
			GenerateGamePlanRequest gp = request.getBody().getParam();
			for(GamePlanDTO plan: gp.getPlanList()){
				commonGamePlanService.pauseGamePlan(plan.getGamePlanId());
			}
		} catch (Exception e) {
			
			log.error("pauseGamePlan error:", e);
			response.getHead().setStatus(1);
		}
		
		return response;
	}
	
	@RequestMapping(value = "/continueGamePlan")
	@ResponseBody
	public Response<GenerateGamePlanResponse> continueGamePlan(
			@RequestBody @ValidRequestBody Request<GenerateGamePlanRequest> request) throws Exception{
		
		Response<GenerateGamePlanResponse> response = new Response<GenerateGamePlanResponse>(request);
		try {
			GenerateGamePlanRequest gp = request.getBody().getParam();
			for(GamePlanDTO plan: gp.getPlanList()){
				commonGamePlanService.continueGamePlan(plan.getGamePlanId());
			}
		} catch (Exception e) {
			
			log.error("pauseGamePlan error:", e);
			response.getHead().setStatus(1);
		}
		
		return response;
	}
}
