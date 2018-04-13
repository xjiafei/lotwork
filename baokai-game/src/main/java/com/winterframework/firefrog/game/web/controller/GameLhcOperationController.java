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
import com.winterframework.firefrog.common.redis.RedisClient;
import com.winterframework.firefrog.common.util.DataConverterUtil;
import com.winterframework.firefrog.game.dao.vo.GameLhcOdds;
import com.winterframework.firefrog.game.entity.GameIssueEntity;
import com.winterframework.firefrog.game.entity.GameSeriesConfigEntity;
import com.winterframework.firefrog.game.exception.GameIssueNotExistErrorException;
import com.winterframework.firefrog.game.service.IGameDrawService;
import com.winterframework.firefrog.game.service.IGameIssueRuleService;
import com.winterframework.firefrog.game.service.IGameIssueService;
import com.winterframework.firefrog.game.service.IGameLhcOperationService;
import com.winterframework.firefrog.game.service.IGameSeriesConfigService;
import com.winterframework.firefrog.game.web.dto.GameIssueQueryRequest;
import com.winterframework.firefrog.game.web.dto.GameIssueQueryResponse;
import com.winterframework.firefrog.game.web.dto.GameLhcOddsResponse;
import com.winterframework.firefrog.game.web.dto.GameLhcTipsResponse;
import com.winterframework.firefrog.game.web.dto.GameLhcZodiacResponse;
import com.winterframework.firefrog.game.web.dto.LhcGameTips;
import com.winterframework.firefrog.game.web.dto.LhcGameZodiac;
import com.winterframework.modules.web.jsonresult.Request;
import com.winterframework.modules.web.jsonresult.Response;

/**
 * @ClassName: GameOrderController
 * @Description: 游戏奖期Controller
 * @author David
 * @date 2013-7-22 上午9:37:52
 * 
 */
@Controller("GameLhcOperationController")
@RequestMapping("/game")
public class GameLhcOperationController {

	private Logger log = LoggerFactory.getLogger(GameLhcOperationController.class);

	@Resource(name = "gameLhcOperationServiceImpl")
	private IGameLhcOperationService gameLhcOperationService;

	@Resource(name = "gameIssueRuleServiceImpl")
	private IGameIssueRuleService gameIssueRuleService;

	@Resource(name = "gameDrawServiceImpl")
	private IGameDrawService gameDrawService;

	@Resource(name = "gameSeriesConfigServiceImpl")
	private IGameSeriesConfigService configService;

	@Resource(name = "RedisClient")
	private RedisClient rc;

	private String lastUserPlayGamePR = "lastUserPlayGamePR____";

	/**
	 * @Title: queryGameOdds
	 * @Description: 賠率查詢
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryGameOdds")
	@ResponseBody
	public Response<GameLhcOddsResponse> queryGameOdds() throws Exception {

		Response<GameLhcOddsResponse> response = new Response<GameLhcOddsResponse>();
		try {
			List<GameLhcOdds> gameIssueEntity = gameLhcOperationService.getAllOdds();
			
			GameLhcOddsResponse rs = new GameLhcOddsResponse();
			rs.setGameLhcOdds(gameIssueEntity);

			response.setResult(rs);
		} catch (Exception e) {
			log.error("查询賠率错误 ", e);
			throw e;
		}
		return response;
	}
	
	
	/**
	 * @Title: queryGameZodiac
	 * @Description: 生肖號碼查詢
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryGameZodiac")
	@ResponseBody
	public Response<GameLhcZodiacResponse> queryGameZodiac() throws Exception {

		Response<GameLhcZodiacResponse> response = new Response<GameLhcZodiacResponse>();
		try {
			List<LhcGameZodiac> gameZodiacEntity = gameLhcOperationService.getAllNumber();
			
			GameLhcZodiacResponse rs = new GameLhcZodiacResponse();
			rs.setGameZodiac(gameZodiacEntity);;

			response.setResult(rs);
		} catch (Exception e) {
			log.error("查询賠率错误 ", e);
			throw e;
		}
		return response;
	}
	
	/**
	 * @Title: queryGameTips
	 * @Description: 遊戲玩法查詢
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryGameTips")
	@ResponseBody
	public Response<GameLhcTipsResponse> queryGameTips() throws Exception {

		Response<GameLhcTipsResponse> response = new Response<GameLhcTipsResponse>();
		try {
			List<LhcGameTips> gameGameTipsEntity = gameLhcOperationService.getAllGameTips();
			
			GameLhcTipsResponse rs = new GameLhcTipsResponse();
			rs.setGameTips(gameGameTipsEntity);

			response.setResult(rs);
		} catch (Exception e) {
			log.error("查询賠率错误 ", e);
			throw e;
		}
		return response;
	}
}
