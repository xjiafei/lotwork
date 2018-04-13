package com.winterframework.firefrog.game.web.comtroller;

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
import com.winterframework.firefrog.common.util.PageConverterUtils;
import com.winterframework.firefrog.game.dao.IGameSeriesDao;
import com.winterframework.firefrog.game.dao.vo.GameRiskIssue;
import com.winterframework.firefrog.game.exception.PastGameIssueQueryEndTimeAfterStartTimeOfCurrentDayException;
import com.winterframework.firefrog.game.service.IGameIssueService;
import com.winterframework.firefrog.game.web.dto.GameRiskIssueListQueryRequest;
import com.winterframework.firefrog.game.web.dto.GameRiskIssueListQueryResponse;
import com.winterframework.firefrog.game.web.dto.GameRiskIssueStruc;
import com.winterframework.modules.page.Page;
import com.winterframework.modules.page.PageRequest;
import com.winterframework.modules.web.jsonresult.Request;
import com.winterframework.modules.web.jsonresult.Response;
import com.winterframework.modules.web.jsonresult.ResultPager;


/** 
* @ClassName GameIssueController 
* @Description 奖期相关 - 审核奖期 
* @author  hugh
* @date 2014年4月9日 下午3:54:08 
*  
*/
@Controller("gameRiskIssueController")
@RequestMapping("/gameRisk")
public class GameRiskIssueController {

	private Logger log = LoggerFactory.getLogger(GameRiskIssueController.class);

	@Resource(name = "gameIssueServiceImpl")
	private IGameIssueService gameIssueService;
	
	@Resource(name = "gameSeriesDaoImpl")
	private IGameSeriesDao gameSeriesDaoImpl;

	/** 
	* @Title: queryGameRiskIssue 
	* @Description: 审核奖期查询 
	* @param request
	* @return
	* @throws Exception
	*/
	@RequestMapping(value = "/queryGameRiskIssues")
	@ResponseBody
	public Response<GameRiskIssueListQueryResponse> queryGameRiskIssues(
			@RequestBody @ValidRequestBody Request<GameRiskIssueListQueryRequest> request) throws Exception {

		log.info("开始查询审核奖期列表......");
		Response<GameRiskIssueListQueryResponse> response = new Response<GameRiskIssueListQueryResponse>(request);
		int sNo = request.getBody().getPager().getStartNo();
		int eNo = request.getBody().getPager().getEndNo();

		PageRequest<GameRiskIssueListQueryRequest> pr = PageConverterUtils.getRestPageRequest(sNo, eNo);
		pr.setSearchDo(request.getBody().getParam());
		pr.setSortColumns("FACTION_DRAW_TIME desc");
		Page<GameRiskIssue> page = null;
		List<GameRiskIssue> issues = null;
		List<GameRiskIssueStruc> issueStrucs = new ArrayList<GameRiskIssueStruc>();
		GameRiskIssueListQueryResponse result = new GameRiskIssueListQueryResponse();
		GameRiskIssueStruc issueStruc = null;
		try {
			page = gameIssueService.queryRiskGameIssues(pr);
			issues = page.getResult();
			if (issues != null && issues.size() > 0) {
				for (GameRiskIssue gie : issues) {
					issueStruc = new GameRiskIssueStruc(gie);
					issueStruc.setLotteryName(gameSeriesDaoImpl.getLotteryNameByLotteryid(issueStruc.getLotteryid()));
					issueStrucs.add(issueStruc);
				}
			}
			result.setGameRiskIssueResponse(issueStrucs);
			response.setResult(result);
			ResultPager pager = new ResultPager();
			pager.setEndNo(page.getThisPageLastElementNumber());
			pager.setStartNo(page.getThisPageFirstElementNumber());
			pager.setTotal(page.getTotalCount());

			response.setResultPage(pager);

		} catch (PastGameIssueQueryEndTimeAfterStartTimeOfCurrentDayException e) {
			log.error("过去奖期查询结束时间不得晚于当前起始时间", e);
			response.getHead().setStatus(e.getStatus());
		} catch (Exception e) {
			log.error("查询奖期列表出错", e);
			throw e;
		}

		log.info("查询审核奖期列表完成。");
		return response;
	}



}
