package com.winterframework.firefrog.game.web.comtroller;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.winterframework.firefrog.common.util.PageConverterUtils;
import com.winterframework.firefrog.game.dao.vo.GameReportIssue;
import com.winterframework.firefrog.game.service.IGameReportIssueService;
import com.winterframework.firefrog.game.service.ITransactionRecordService;
import com.winterframework.firefrog.game.web.dto.GameReportRequest;
import com.winterframework.firefrog.game.web.dto.GameReportResponse;
import com.winterframework.firefrog.game.web.dto.GameReportStruc;
import com.winterframework.firefrog.game.web.dto.GameRiskReportRequest;
import com.winterframework.firefrog.game.web.dto.GameRiskReportResponse;
import com.winterframework.firefrog.game.web.dto.GameRiskResponse;
import com.winterframework.firefrog.game.web.dto.GameRiskTransactionReportResponse;
import com.winterframework.firefrog.game.web.dto.GameRiskTransactionReportStruc;
import com.winterframework.modules.page.Page;
import com.winterframework.modules.page.PageRequest;
import com.winterframework.modules.web.jsonresult.Request;
import com.winterframework.modules.web.jsonresult.Response;
import com.winterframework.modules.web.jsonresult.ResultPager;

/**
 * 
* @ClassName: GameReportController 
* @Description: 游戏报表 
* @author Richard
* @date 2014-2-21 上午10:34:57 
*
 */
@RequestMapping(value = "/gameRisk")
@Controller("gameReportController")
public class GameReportController {
	
	private static Logger log = LoggerFactory.getLogger(GameReportController.class);
	
	@Resource(name = "gameTransactionRecordServiceImpl")
	private ITransactionRecordService gameTransactionRecordServiceImpl;
	
	@Resource(name = "gameReportIssueServiceImpl")
	private IGameReportIssueService gameReportIssueServiceImpl;
	
	/**
	 * 
	* @Title: queryGameReport 
	* @Description: 游戏报表查询
	* @param request
	* @return
	* @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/queryGameReport")
	public Response<GameReportResponse> queryGameReport(@RequestBody Request<GameReportRequest> request) throws Exception{
		
		log.info("queryGameReport controller...");
		
		Response<GameReportResponse> response = new Response<GameReportResponse>(request);
		int sNo = request.getBody().getPager().getStartNo();
		int eNo = request.getBody().getPager().getEndNo();
		
		PageRequest<GameReportRequest> pageRequest = PageConverterUtils.getRestPageRequest(sNo, eNo);
		pageRequest.setSearchDo(request.getBody().getParam());
		pageRequest.setSortColumns("CREATE_TIME desc");
		Page<GameReportStruc> page = null;
		GameReportResponse result = new GameReportResponse();
		try {
			
			page = gameTransactionRecordServiceImpl.gameReport(pageRequest);
			result.setReportList(page.getResult());
			
			response.setResult(result);
			ResultPager pager = new ResultPager();
			pager.setEndNo(page.getThisPageFirstElementNumber());
			pager.setStartNo(page.getThisPageFirstElementNumber());
			pager.setTotal(page.getTotalCount());
			
			response.setResultPage(pager);
			
		} catch (Exception e) {
			log.error("queryGameReport error:",e);
		}
		
		return response;
	}
	
	/**
	 * 
	* @Title: queryGameRiskTransactionReport 
	* @Description: 游戏交易流水报表查询
	* @param request
	* @return
	* @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/queryGameRiskTransactionReport")
	public Response<GameRiskTransactionReportResponse> queryGameRiskTransactionReport(@RequestBody Request<GameReportRequest> request) throws Exception{
		
		log.info("queryGameReport controller...");
		
		Response<GameRiskTransactionReportResponse> response = new Response<GameRiskTransactionReportResponse>(request);
		int sNo = request.getBody().getPager().getStartNo();
		int eNo = request.getBody().getPager().getEndNo();
		
		PageRequest<GameReportRequest> pageRequest = PageConverterUtils.getRestPageRequest(sNo, eNo);
		pageRequest.setSearchDo(request.getBody().getParam());
		pageRequest.setSortColumns("CREATE_TIME desc");
		Page<GameRiskTransactionReportStruc> page = null;
		GameRiskTransactionReportResponse result = new GameRiskTransactionReportResponse();
		try {
			
			page = gameTransactionRecordServiceImpl.queryTransactionReport(pageRequest);
			result.setReportList(page.getResult());
			
			response.setResult(result);
			ResultPager pager = new ResultPager();
			pager.setEndNo(page.getThisPageFirstElementNumber());
			pager.setStartNo(page.getThisPageFirstElementNumber());
			pager.setTotal(page.getTotalCount());
			
			response.setResultPage(pager);
			
		} catch (Exception e) {
			log.error("queryGameReport error:",e);
		}
		
		return response;
	}

	/**
	 * 
	* @Title: queryGameReportIssue 
	* @Description: 游戏营收报表查询
	* @param request
	* @return
	* @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/queryGameReportIssue")
	public Response<GameRiskReportResponse> queryGameReportIssue(@RequestBody Request<GameRiskReportRequest> request) throws Exception{
		
		log.info("queryGameReport controller...");
		
		Response<GameRiskReportResponse> response = new Response<GameRiskReportResponse>(request);
		int sNo = request.getBody().getPager().getStartNo();
		int eNo = request.getBody().getPager().getEndNo();
		
		PageRequest<GameRiskReportRequest> pageRequest = PageConverterUtils.getRestPageRequest(sNo, eNo);
		pageRequest.setSearchDo(request.getBody().getParam());
		pageRequest.setSortColumns("UPDATE_TIME desc");
		Page<GameReportIssue> page = null;
		GameRiskReportResponse result = new GameRiskReportResponse();
		try {
			
			page = gameReportIssueServiceImpl.queryGameReportIssues(pageRequest);
			result.setReports(page.getResult());
			
			response.setResult(result);
			ResultPager pager = new ResultPager();
			pager.setEndNo(page.getThisPageFirstElementNumber());
			pager.setStartNo(page.getThisPageFirstElementNumber());
			pager.setTotal(page.getTotalCount());
			
			response.setResultPage(pager);
			
		} catch (Exception e) {
			log.error("queryGameReport error:",e);
		}
		
		return response;
	}
	
	/**
	 * 
	* @Title: createGameReportIssue 
	* @Description: 生成游戏营收报表
	* @param request
	* @return
	* @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/createGameReportIssue")
	public Response<GameRiskResponse> createGameReportIssue(@RequestBody Request<GameRiskReportRequest> request) throws Exception{
		
		Response<GameRiskResponse> response = new Response<GameRiskResponse>(request);
		GameRiskReportRequest req = request.getBody().getParam();
		GameRiskResponse res = new GameRiskResponse();
		try {
			
			gameReportIssueServiceImpl.createGameReport(req.getLotteryId(),req.getIssueCode());
			res.setResultStatus(0L);
			
		} catch (Exception e) {
			res.setResultStatus(1L);
			res.setExceptionMessage(e.getMessage());
			log.error("queryGameReport error:",e);
		}
		response.setResult(res);
		return response;
	}
}
