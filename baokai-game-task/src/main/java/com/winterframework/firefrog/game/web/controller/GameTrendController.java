package com.winterframework.firefrog.game.web.controller;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.winterframework.firefrog.common.annotation.ValidRequestBody;
import com.winterframework.firefrog.game.dao.vo.GameTrendJbyl;
import com.winterframework.firefrog.game.service.api.IGameTrendService;
import com.winterframework.firefrog.game.web.dto.GameTrendJbylResponse;
import com.winterframework.firefrog.game.web.dto.GameTrendQueryRequest;
import com.winterframework.modules.web.jsonresult.Request;
import com.winterframework.modules.web.jsonresult.Response;

 
/**
 * 走势图API
 * @ClassName
 * @Description
 * @author ibm
 * 2014年10月17日
 */
@Controller("GameTrendController")
@RequestMapping("/gameTask")
public class GameTrendController {

	private static final Logger log = LoggerFactory.getLogger(GameTrendController.class);

	@Resource(name = "gameTrendServiceImpl")
	private IGameTrendService gameTrendService;

 
	/**
	 * 秒秒彩走势图数据接口
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryTrendJbylMmc")
	@ResponseBody
	public Response<List<GameTrendJbyl>> queryTrendJbylMmc(
			@RequestBody @ValidRequestBody Request<GameTrendQueryRequest> request) throws Exception {
		Long userId = 312L;//request.getHead().getUserId();
		Long lotteryId=request.getBody().getParam().getLotteryId();
		Integer gameGroupCode=request.getBody().getParam().getGameGroupCode();
		Integer type=request.getBody().getParam().getType();
		Integer top=request.getBody().getParam().getIssue();
		Long startDate=request.getBody().getParam().getStartDate();
		Long endDate=request.getBody().getParam().getEndDate();
		Date startTime=startDate==null?null:new Date(startDate);
		Date endTime=endDate==null?null:new Date(endDate); 
		
		Response<List<GameTrendJbyl>> response = new Response<List<GameTrendJbyl>>(request);  
		List<GameTrendJbyl> trendJbylList =null;
		try{
			trendJbylList = this.gameTrendService.getTrendJbyl(lotteryId,userId,gameGroupCode,type,startTime,endTime,top);  
			response.getHead().setStatus(1L);
		}catch (Exception e) {
			response.getHead().setStatus(0L);  
			log.error("走势图获取异常",e);
		}

		response.setResult(trendJbylList);
		return response;
	}

}
