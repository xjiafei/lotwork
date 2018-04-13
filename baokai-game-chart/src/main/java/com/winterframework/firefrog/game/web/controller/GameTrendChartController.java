package com.winterframework.firefrog.game.web.controller;

import com.winterframework.firefrog.common.annotation.ValidRequestBody;
import com.winterframework.firefrog.common.util.DateUtils;
import com.winterframework.firefrog.game.entity.GameLryl;
import com.winterframework.firefrog.game.service.IBaseOmissionService;
import com.winterframework.firefrog.game.service.IConverterService;
import com.winterframework.firefrog.game.service.IGameDrawLrylService;
import com.winterframework.firefrog.game.service.IGameDrawService;
import com.winterframework.firefrog.game.web.dto.*;
import com.winterframework.modules.web.jsonresult.Request;
import com.winterframework.modules.web.jsonresult.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/** 
* @ClassName: GameTrendChartController 
* @Description: 走势数据图表Controller 
* @author David 
* @date 2013-7-22 上午9:37:52 
*  
*/
@Controller("gameTrendChartController")
@RequestMapping("/game/chart")
public class GameTrendChartController {

	private Logger log = LoggerFactory.getLogger(GameTrendChartController.class);

	@Resource(name = "gameDrawServiceImpl")
	private IGameDrawService drawService;

	@Resource(name = "gameDrawLrylServiceImpl")
	private IGameDrawLrylService gameDrawLrylService;

	@Resource(name = "baseOmissionServiceImpl")
	private IBaseOmissionService baseOmissionService;

	@Resource(name = "converterServiceImpl")
	private IConverterService converterServiceImpl;
	@Resource(name = "gameBet")
	private Map<String, Map> gameBet;
	/**
	 * 
	* @Title: queryAssistAction 
	* @Description: 投注辅助图表查询
	* @param request
	* @return
	* @throws Exception
	 */
	@RequestMapping(value = "/queryAssistAction")
	@ResponseBody
	public Response<QueryAssistActionResponse> queryAssistAction(
			@RequestBody @ValidRequestBody Request<QueryAssistActionRequest> request) throws Exception {

		Response<QueryAssistActionResponse> response = new Response<QueryAssistActionResponse>(request);
		try {
			if (null != request.getBody()) {
				QueryAssistActionRequest actionRequest = request.getBody().getParam();
				QueryAssistActionResponse result = new QueryAssistActionResponse();
				// 查询期数
				int num = actionRequest.getCount() == null ? 20 : actionRequest.getCount();
				if(actionRequest.getLotteryid()!=99401&&actionRequest.getGameGroupCode()==33){
					actionRequest.setGameGroupCode(333);
				}
				if((actionRequest.getLotteryid().longValue()+"").startsWith("995")&& actionRequest.getGameGroupCode()==34){
					actionRequest.setGameGroupCode(344);
				}
				List<BaseChartStruc> list = baseOmissionService.queryBaseChartStruc(actionRequest.getLotteryid(),
						actionRequest.getGameGroupCode(), actionRequest.getGameSetCode(),
						actionRequest.getBetMethodCode(), num);
				result.setList(list);
				if(actionRequest.getGameGroupCode()==344){
					response.setResult(((converterServiceImpl.converter(list, actionRequest.getLotteryid(), actionRequest.getGameGroupCode(), 1))));
				}else{
				response.setResult(result);}
			}

		} catch (Exception e) {

			log.error("投注辅助图表查询错误", e);
			throw e;
		}

		return response;
	}

	/** 
	* @Title: queryTrendCharts 
	* @Description: 查询走势表
	* @param request
	* @return
	* @throws Exception
	*/
	@RequestMapping(value = "/queryTrendCharts")
	@ResponseBody
	public Response<QueryTrendChartResponse> queryTrendCharts(
			@RequestBody @ValidRequestBody Request<QueryTrendChartRequest> request) throws Exception {
		log.info("开始查询走势表......");
		Response<QueryTrendChartResponse> response = new Response<QueryTrendChartResponse>(request);

		return response;
	}
	@RequestMapping(value = "/queryChart")
	@ResponseBody
	public Response<QueryChartResponse> queryChart(
			@RequestBody @ValidRequestBody Request<QueryChartRequest> request) throws Exception {
		log.info("开始查询走势表......");
		Long lotteryId=request.getBody().getParam().getLotteryId();
		String gameMethod=request.getBody().getParam().getGameMethod();
		if(gameMethod==null ||gameMethod.equals(""))return null;
		Map lotteryMap=gameBet.get(lotteryAlias.get(lotteryId));
		if(lotteryMap==null ||lotteryMap.size()==0)return null;
		if(lotteryMap.get(gameMethod)==null)return null;
		Integer groupCode=Integer.valueOf(((Map) lotteryMap.get(gameMethod)).get("code").toString());
		Integer isGeneral=((Map) lotteryMap.get(gameMethod)).get("isGeneral")==null?null:Integer.valueOf(((Map) lotteryMap.get(gameMethod)).get("isGeneral").toString());
		Integer type=request.getBody().getParam().getType();
		Integer periodsNum=request.getBody().getParam().getPeriodsNum();
		//Map mapBet = gameBet.get(view);
		Long startDate = null;
		Long endDate = null;
		if (type==2) {
			startDate = DateUtils.getStartTimeOfDate(DateUtils.addDays(new Date(), -1*(periodsNum-1))).getTime();//近 5 天資料
			endDate = DateUtils.currentDate().getTime();
		}
		Response<QueryChartResponse> response = new Response<QueryChartResponse>(request);
		QueryChartResponse  bizRes=new QueryChartResponse();
		GameTrendChartStruc rtr= baseOmissionService.queryTrendCharts(lotteryId, groupCode, type, isGeneral==null?null:isGeneral, periodsNum, startDate, endDate);
		bizRes.setTrendChartStruc(rtr);
		response.setResult(bizRes);
		return response;
	}
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
		if (lotteryId != 99401 && gameGroupCode == 33) {
			gameGroupCode = 333;
		}
		if ((lotteryId+"").startsWith("995") && gameGroupCode == 34) {
			gameGroupCode = 344;
		}
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
		if (lotteryId != 99401 && gameGroupCode == 33) {
			gameGroupCode = 333;
		}
		if ((lotteryId+"").startsWith("995")&& gameGroupCode == 34) {
			gameGroupCode = 344;
		}
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
	protected static final Map<Long,String> lotteryAlias= new HashMap<Long,String>();
	static {
		lotteryAlias.put(99101L, "cqssc");
		lotteryAlias.put(99102L, "jxssc");
		lotteryAlias.put(99105L, "hljssc");
		lotteryAlias.put(99103L, "xjssc");
		lotteryAlias.put(99104L, "tjssc");
		lotteryAlias.put(99106L, "llssc");
		lotteryAlias.put(99112L, "slmmc");
		lotteryAlias.put(99111L, "jlffc");
		lotteryAlias.put(99114L, "txffc");
		lotteryAlias.put(99107L, "shssl");
		lotteryAlias.put(99108L, "d3");
		lotteryAlias.put(99201L, "kl8");
		lotteryAlias.put(99301L, "sd115");
		lotteryAlias.put(99302L, "jx115");
		lotteryAlias.put(99303L, "gd115");
		//lotteryAlias.put(99101L, "cq115");
		lotteryAlias.put(99305L, "ll115");
		lotteryAlias.put(99307L, "js115");
		lotteryAlias.put(99306L, "sl115");
		lotteryAlias.put(99109L, "p5");
		lotteryAlias.put(99401L, "ssq");
		lotteryAlias.put(99501L, "jsk3");
		lotteryAlias.put(99502L, "ahk3");
		lotteryAlias.put(99601L, "jsdice");
		lotteryAlias.put(99602L, "jldice1");
		lotteryAlias.put(99603L, "jldice2");
	}
}
