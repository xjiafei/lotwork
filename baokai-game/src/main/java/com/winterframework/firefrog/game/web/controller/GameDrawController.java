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
import com.winterframework.firefrog.game.dao.vo.GameTrendJbyl;
import com.winterframework.firefrog.game.entity.GameHelpEntity;
import com.winterframework.firefrog.game.entity.GameLryl;
import com.winterframework.firefrog.game.service.IGameDrawLrylService;
import com.winterframework.firefrog.game.service.IGameDrawService;
import com.winterframework.firefrog.game.service.IGameTrendService;
import com.winterframework.firefrog.game.web.dto.ActionResponse;
import com.winterframework.firefrog.game.web.dto.BetChartStrucKl8;
import com.winterframework.firefrog.game.web.dto.DTOConvertor4Web;
import com.winterframework.firefrog.game.web.dto.GameColdAndHotNumbersQueryRequest;
import com.winterframework.firefrog.game.web.dto.GameLrylQueryResponse;
import com.winterframework.firefrog.game.web.dto.GameMissingValueQueryRequest;
import com.winterframework.firefrog.game.web.dto.QueryActionRequest;
import com.winterframework.firefrog.game.web.dto.QueryActionResponse;
import com.winterframework.firefrog.game.web.dto.QueryAssistActionRequest;
import com.winterframework.modules.web.jsonresult.Request;
import com.winterframework.modules.web.jsonresult.Response;

/** 
* @ClassName: GameDrawController 
* @Description: 开奖结果相关统计Controller 
* @author Denny 
* @date 2013-7-22 上午10:00:00 
*  
*/
@Controller("gameDrawController")
@RequestMapping("/game")
public class GameDrawController {

	private Logger log = LoggerFactory.getLogger(GameDrawController.class);

	@Resource(name = "gameDrawServiceImpl")
	private IGameDrawService gameDrawServiceImpl;

	@Resource(name = "gameDrawLrylServiceImpl")
	private IGameDrawLrylService gameDrawLrylService;

	@Resource(name = "gameTrendAssistServiceImpl")
	private IGameTrendService gameTrendAssistServiceImpl;

	/** 
	* @Title: queryColdAndHotNumbers 
	* @Description: 4.12 选球号码冷热查询
	* @param request
	* @return
	* @throws Exception
	*/
	@RequestMapping(value = "/queryColdAndHotNumbers")
	@ResponseBody
	public Response<GameLrylQueryResponse> queryColdAndHotNumbers(
			@RequestBody @ValidRequestBody Request<GameColdAndHotNumbersQueryRequest> request) throws Exception {

		log.info("开始查询冷热号码......");
		Response<GameLrylQueryResponse> response = new Response<GameLrylQueryResponse>(request);
		GameLrylQueryResponse glqResponse = new GameLrylQueryResponse();
		GameColdAndHotNumbersQueryRequest reqParam = request.getBody().getParam();
		long lotteryId = reqParam.getLotteryId();
		int gameGroupCode = reqParam.getGameGroupCode();
		int gameSetCode = reqParam.getGameSetCode();
		int betMethodCode = reqParam.getBetMethodCode();
		int countIssue = reqParam.getCountIssue();
		try {
			List<GameLryl> coldAndHotNUmbersResult = gameDrawLrylService.queryColdAndHotNumbers(lotteryId,
					gameGroupCode, gameSetCode, betMethodCode, countIssue);
			glqResponse.setGameLrylNumbers(coldAndHotNUmbersResult);

			response.setResult(glqResponse);
		} catch (Exception e) {
			log.error("查询冷热号码出现异常：", e);
			throw e;
		}

		log.info("查询冷热号码完成。");
		return response;
	}

	/** 
	* @Title: queryMissingValue 
	* @Description: 4.13 选球号码遗漏查询
	* @param request
	* @return
	* @throws Exception
	*/
	@RequestMapping(value = "/queryMissingValue")
	@ResponseBody
	public Response<GameLrylQueryResponse> queryMissingValue(
			@RequestBody @ValidRequestBody Request<GameMissingValueQueryRequest> request) throws Exception {

		log.info("开始查询遗漏号码......");
		Response<GameLrylQueryResponse> response = new Response<GameLrylQueryResponse>(request);
		GameLrylQueryResponse glqResponse = new GameLrylQueryResponse();
		GameMissingValueQueryRequest reqParam = request.getBody().getParam();
		long lotteryId = reqParam.getLotteryId();
		int gameGroupCode = reqParam.getGameGroupCode();
		int gameSetCode = reqParam.getGameSetCode();
		int betMethodCode = reqParam.getBetMethodCode();
		int queryType = reqParam.getType();
		try {

			List<GameLryl> missingValueResult = gameDrawLrylService.queryMissingValue(lotteryId, gameGroupCode,
					gameSetCode, betMethodCode, queryType);
			glqResponse.setGameLrylNumbers(missingValueResult);

			response.setResult(glqResponse);
		} catch (Exception e) {
			log.error("查询遗漏号码出现异常：", e);
			throw e;
		}

		log.info("查询遗漏号码完成。");
		return response;
	}

	/**
	 * 
	* @Title: queryActionSet 
	* @Description: 4.16 投注方式查询
	* @param request
	* @return
	* @throws Exception
	 */
	@RequestMapping(value = "/queryAcionSet")
	@ResponseBody
	public Response<QueryActionResponse> queryActionSet(
			@RequestBody @ValidRequestBody Request<QueryActionRequest> request) throws Exception {

		Response<QueryActionResponse> response = new Response<QueryActionResponse>(request);

		try {

			if (null != request.getBody()) {

				QueryActionRequest action = request.getBody().getParam();
				QueryActionResponse result = new QueryActionResponse();

				List<GameHelpEntity> list = gameDrawServiceImpl.queryGameHelpByLotteryId(action.getLotteryId());

				List<ActionResponse> actionList = new ArrayList<ActionResponse>();

				for (GameHelpEntity entity : list) {

					ActionResponse actionResponse = new ActionResponse();
					actionResponse.setBetMethodCode(entity.getBetMethodCode());
					actionResponse.setGameExamplesDes(entity.getGameExamplesDes());
					actionResponse.setGameGroupCode(entity.getGameGroupCode());
					actionResponse.setGamePromptDes(entity.getGamePromptDes());
					actionResponse.setGameSetCode(entity.getGameSetCode());

					actionList.add(actionResponse);
				}
				result.setList(actionList);

				response.setResult(result);
			}

		} catch (Exception e) {

			log.error("投注方式查询错误", e);
			throw e;
		}

		return response;
	}

	/**
	 * 
	* @Title: queryKl8AssistAction 
	* @Description: 4.41 投注辅助图表查询
	* @param request
	* @return
	* @throws Exception
	 */
	@RequestMapping(value = "/queryKl8AssistAction")
	@ResponseBody
	public Response<List<BetChartStrucKl8>> queryKl8AssistAction(
			@RequestBody @ValidRequestBody Request<QueryAssistActionRequest> request) throws Exception {

		Response<List<BetChartStrucKl8>> response = new Response<List<BetChartStrucKl8>>(request);

		try {

			if (null != request.getBody()) {

				QueryAssistActionRequest actionRequest = request.getBody().getParam();

				List<GameTrendJbyl> list = gameTrendAssistServiceImpl.getTrendByBetMethod(actionRequest.getLotteryid(),
						actionRequest.getGameGroupCode(), null,
						null, actionRequest.getCount());
				List<BetChartStrucKl8> resultList = new ArrayList<BetChartStrucKl8>();
				for (GameTrendJbyl gameTrendJbyl : list) {
					resultList.add(DTOConvertor4Web.transforGameJbylTrend2BetChartStrucKl8(gameTrendJbyl));
				}
				response.setResult(resultList);
			}

		} catch (Exception e) {

			log.error("投注辅助图表查询错误", e);
			throw e;
		}

		return response;
	}

}
