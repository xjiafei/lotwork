package com.winterframework.firefrog.game.web.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.winterframework.firefrog.common.annotation.ValidRequestBody;
import com.winterframework.firefrog.game.dao.vo.GameSeries;
import com.winterframework.firefrog.game.service.IGameLotteryService;
import com.winterframework.firefrog.game.web.dto.DTOConvert;
import com.winterframework.firefrog.game.web.dto.LotteryListQueryRequest;
import com.winterframework.firefrog.game.web.dto.LotteryListQueryResponse;
import com.winterframework.firefrog.game.web.dto.LotteryListStruc;
import com.winterframework.modules.web.jsonresult.Request;
import com.winterframework.modules.web.jsonresult.Response;

/** 
* @ClassName: GameLotteryController 
* @Description: 彩种相关操作Controller 
* @author Denny 
* @date 2013-9-30 上午11:40:26 
*  
*/
@Controller("gameLotteryController")
@RequestMapping("/game")
public class GameLotteryController {
	
	private Logger log = LoggerFactory.getLogger(GameLotteryController.class);
	
	@Resource(name = "gameLotteryServiceImpl")
	private IGameLotteryService gameLotteryService;	
	
	/** 
	* @Title: queryLotteryList 
	* @Description: 5.3.17 彩种列表查询
	* @param request
	* @return
	* @throws Exception
	*/
	@RequestMapping(value = "/queryLotteryList")
	@ResponseBody
	public Response<LotteryListQueryResponse> queryLotteryList(
			@RequestBody @ValidRequestBody Request<LotteryListQueryRequest> request) throws Exception {

		log.info("开始查询彩种列表......");
		Response<LotteryListQueryResponse> response = new Response<LotteryListQueryResponse>(request);

		long lotteryid = request.getBody().getParam().getLotteryid();
		int status = request.getBody().getParam().getStatus();

		LotteryListQueryResponse lotteryListQueryResponse = new LotteryListQueryResponse();
		List<LotteryListStruc> lotteryList = new ArrayList<LotteryListStruc>();
		LotteryListStruc lotteryListStruc = null;
		try {
			List<GameSeries> gameSeriesList = gameLotteryService.queryLotteryList(lotteryid, status);
			for (GameSeries gs : gameSeriesList) {
				lotteryListStruc = DTOConvert.gameSeries2LotteryListStruc(gs);
				lotteryList.add(lotteryListStruc);
			}
			lotteryListQueryResponse.setLotteryListStruc(lotteryList);
			response.setResult(lotteryListQueryResponse);
		} catch (Exception e) {
			log.error("查询彩种列表异常 ", e);
			throw e;
		}

		log.info("查询彩种列表完成。");
		return response;
	}

}
