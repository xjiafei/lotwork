package com.winterframework.firefrog.game.web.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.winterframework.firefrog.common.annotation.ValidRequestBody;
import com.winterframework.firefrog.common.redis.RedisClient;
import com.winterframework.firefrog.common.util.DataConverterUtil;
import com.winterframework.firefrog.common.util.DateUtils;
import com.winterframework.firefrog.common.util.PageConverterUtils;
import com.winterframework.firefrog.game.dao.vo.GameDrawResult;
import com.winterframework.firefrog.game.dao.vo.GameIssue;
import com.winterframework.firefrog.game.dao.vo.GameSeries;
import com.winterframework.firefrog.game.entity.GameIssueEntity;
import com.winterframework.firefrog.game.entity.GameIssueRuleEntity;
import com.winterframework.firefrog.game.exception.FutureGameIssueQueryBeginTimeBeforeStartTimeOfCurrentDayException;
import com.winterframework.firefrog.game.exception.GameIssueCommonRuleValidForPublishHasBeenExistException;
import com.winterframework.firefrog.game.exception.GameIssueNotExistErrorException;
import com.winterframework.firefrog.game.exception.GameIssueRuleEffectiveTimeAfterEndTimeException;
import com.winterframework.firefrog.game.exception.GameIssueRuleEffectiveTimeBeforeCurrentTimeException;
import com.winterframework.firefrog.game.exception.GameIssueRuleOverlapErrorException;
import com.winterframework.firefrog.game.exception.GameIssueRuleStartTimeShouldSuitableException;
import com.winterframework.firefrog.game.exception.GameIssueTemplateLastAwardTimeBeforeFirstAwardTimeException;
import com.winterframework.firefrog.game.exception.GameIssueTemplateOverlapErrorException;
import com.winterframework.firefrog.game.exception.GameIssueTemplateSalePeriodTimeShouledBiggerThenScheduleStopTimeException;
import com.winterframework.firefrog.game.exception.PastGameIssueQueryEndTimeAfterStartTimeOfCurrentDayException;
import com.winterframework.firefrog.game.exception.QueryBeginTimeAfterEndTimeException;
import com.winterframework.firefrog.game.service.IGameDrawService;
import com.winterframework.firefrog.game.service.IGameIssueRuleService;
import com.winterframework.firefrog.game.service.IGameIssueService;
import com.winterframework.firefrog.game.service.IGameSeriesConfigService;
import com.winterframework.firefrog.game.web.dto.AuditGameIssueRuleRequest;
import com.winterframework.firefrog.game.web.dto.CancelGameIssueRuleRequest;
import com.winterframework.firefrog.game.web.dto.CommonIssueRuleUpdateRequest;
import com.winterframework.firefrog.game.web.dto.CommonOrSpecialGameIssueRuleAddOrUpdateRequest;
import com.winterframework.firefrog.game.web.dto.DTOConvert;
import com.winterframework.firefrog.game.web.dto.DeleteGameIssueRequest;
import com.winterframework.firefrog.game.web.dto.DeleteGameIssueRuleRequest;
import com.winterframework.firefrog.game.web.dto.GameConfigAndAwardData;
import com.winterframework.firefrog.game.web.dto.GameIssueListQueryRequest;
import com.winterframework.firefrog.game.web.dto.GameIssueListQueryResponse;
import com.winterframework.firefrog.game.web.dto.GameIssueManualGenerateRequest;
import com.winterframework.firefrog.game.web.dto.GameIssueManualGenerateResponse;
import com.winterframework.firefrog.game.web.dto.GameIssueQueryRequest;
import com.winterframework.firefrog.game.web.dto.GameIssueQueryResponse;
import com.winterframework.firefrog.game.web.dto.GameIssueRuleQueryRequest;
import com.winterframework.firefrog.game.web.dto.GameIssueRuleQueryResponse;
import com.winterframework.firefrog.game.web.dto.GetGameIssueStrucsRequest;
import com.winterframework.firefrog.game.web.dto.HistoryLotteryAwardRequest;
import com.winterframework.firefrog.game.web.dto.HistoryLotteryAwardResponse;
import com.winterframework.firefrog.game.web.dto.IssueStruc;
import com.winterframework.firefrog.game.web.dto.PreviewIssueStruc;
import com.winterframework.firefrog.game.web.dto.QueryGameIssueRequest;
import com.winterframework.firefrog.game.web.dto.StopGameIssueRuleAddOrUpdateRequest;
import com.winterframework.firefrog.game.web.dto.TraceGameIssueListQueryRequest;
import com.winterframework.firefrog.game.web.dto.TraceGameIssueListQueryResponse;
import com.winterframework.modules.page.Page;
import com.winterframework.modules.page.PageRequest;
import com.winterframework.modules.web.jsonresult.Request;
import com.winterframework.modules.web.jsonresult.Response;
import com.winterframework.modules.web.jsonresult.ResultPager;

/**
 * @ClassName: GameOrderController
 * @Description: 游戏奖期Controller
 * @author David
 * @date 2013-7-22 上午9:37:52
 * 
 */
@Controller("gameIssueController")
@RequestMapping("/game")
public class GameIssueController {

	private Logger log = LoggerFactory.getLogger(GameIssueController.class);

	@Resource(name = "gameIssueServiceImpl")
	private IGameIssueService gameIssueService;

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
	 * 4.15 奖期查询
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryGameIssue")
	@ResponseBody
	public Response<GameIssueQueryResponse> queryGameIssue(
			@RequestBody @ValidRequestBody Request<GameIssueQueryRequest> request) throws Exception {

		Response<GameIssueQueryResponse> response = new Response<GameIssueQueryResponse>(request);
		try {
			GameIssueEntity gameIssueEntity = gameIssueService.queryGameIssue(request.getBody().getParam()
					.getLotteryId(), request.getBody().getParam().getIssueCode());
			//获取最新一期已开奖的奖期
			GameIssueEntity lastGameIssueEntity = gameIssueService.getLastDrawGameIssue(request.getBody().getParam()
					.getLotteryId());
			GameIssueQueryResponse gameIssueQueryResponse = new GameIssueQueryResponse();
			gameIssueQueryResponse.setIssueCode(gameIssueEntity.getIssueCode());
			gameIssueQueryResponse.setLastWebIssueCode(lastGameIssueEntity.getWebIssueCode());
			gameIssueQueryResponse.setNumberRecord(lastGameIssueEntity.getNumberRecord());
			gameIssueQueryResponse.setLotteryId(gameIssueEntity.getLottery().getLotteryId());
			gameIssueQueryResponse.setSaleStartTime(DataConverterUtil.convertDate2Long(gameIssueEntity
					.getSaleStartTime()));
			gameIssueQueryResponse.setSaleEndTime(DataConverterUtil.convertDate2Long(gameIssueEntity.getSaleEndTime()));
			gameIssueQueryResponse.setStatus(gameIssueEntity.getStatus().getValue());
			gameIssueQueryResponse.setWebIssueCode(gameIssueEntity.getWebIssueCode());
			gameIssueQueryResponse.setPasuseStatus(gameIssueEntity.getPauseStatus().getValue());
			gameIssueQueryResponse.setAwardStruc(gameIssueEntity.getAwardStruct());
			gameIssueQueryResponse.setOpenDrawTime(DataConverterUtil.convertDate2Long(gameIssueEntity.getOpenDrawTime()));
			gameIssueQueryResponse.setLastIssueCode(lastGameIssueEntity.getIssueCode());
			response.setResult(gameIssueQueryResponse);
		} catch (GameIssueNotExistErrorException e) {
			log.error("查询当前奖期错误", e);
			response.getHead().setStatus(e.getStatus());
		} catch (Exception e) {
			log.error("查询当前奖期错误 ", e);
			throw e;
		}
		return response;
	}

	/**
	 * @Title: queryOrders
	 * @Description: 4.23 查询预览生成奖期列表
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryGameIssues")
	@ResponseBody
	public Response<GameIssueListQueryResponse> queryGameIssues(
			@RequestBody @ValidRequestBody Request<GameIssueListQueryRequest> request) throws Exception {

		log.debug("开始查询奖期列表......");
		Response<GameIssueListQueryResponse> response = new Response<GameIssueListQueryResponse>(request);
		int sNo = request.getBody().getPager().getStartNo();
		int eNo = request.getBody().getPager().getEndNo();

		PageRequest<GameIssueListQueryRequest> pr = PageConverterUtils.getRestPageRequest(sNo, eNo);
		pr.setSearchDo(request.getBody().getParam());
		pr.setSortColumns("SALE_START_TIME asc");
		Page<GameIssueEntity> page = null;
		List<GameIssueEntity> issues = null;
		List<PreviewIssueStruc> issueStrucs = new ArrayList<PreviewIssueStruc>();
		GameIssueListQueryResponse result = new GameIssueListQueryResponse();
		PreviewIssueStruc issueStruc = null;
		Date takeOffTime = null;
		try {
			//查詢彩種下架時間
			List<GameSeries> gameSeriesList = configService.getAllGameSeries(request.getBody().getParam().getLotteryId(), null);
			for(GameSeries gameSeries : gameSeriesList){
				takeOffTime = gameSeries.getTakeOffTime();
			}
			page = gameIssueService.queryGameIssues(pr,takeOffTime);
			issues = page.getResult();
			if (issues != null && issues.size() > 0) {
				for (GameIssueEntity gie : issues) {
					issueStruc = DTOConvert.gameIssueEntity2IssueStruc(gie);
					issueStrucs.add(issueStruc);
				}
			}
			result.setIssueStrucs(issueStrucs);
			response.setResult(result);
			ResultPager pager = new ResultPager();
			pager.setEndNo(page.getThisPageLastElementNumber());
			pager.setStartNo(page.getThisPageFirstElementNumber());
			pager.setTotal(page.getTotalCount());

			response.setResultPage(pager);

		} catch (PastGameIssueQueryEndTimeAfterStartTimeOfCurrentDayException e) {
			log.error("过去奖期查询结束时间不得晚于当前起始时间", e);
			response.getHead().setStatus(e.getStatus());
		} catch (FutureGameIssueQueryBeginTimeBeforeStartTimeOfCurrentDayException e) {
			log.error("未来奖期查询开始时间不得早于当前起始时间", e);
			response.getHead().setStatus(e.getStatus());
		} catch (QueryBeginTimeAfterEndTimeException e) {
			log.error("查询结束时间不得早于开始时间", e);
			response.getHead().setStatus(e.getStatus());
		} catch (Exception e) {
			log.error("查询奖期列表出错", e);
			throw e;
		}

		log.debug("查询奖期列表完成。");
		return response;
	}

	/**
	 * @Title: queryOrders
	 * @Description: 4.23 查询预览生成奖期列表
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/downLoadGameIssues")
	@ResponseBody
	public Response<GameIssueListQueryResponse> downLoadGameIssues(
			@RequestBody @ValidRequestBody Request<GameIssueListQueryRequest> request) throws Exception {

		log.debug("开始查询奖期列表......");
		Response<GameIssueListQueryResponse> response = new Response<GameIssueListQueryResponse>(request);
		List<GameIssueEntity> issues = null;
		List<PreviewIssueStruc> issueStrucs = new ArrayList<PreviewIssueStruc>();
		GameIssueListQueryResponse result = new GameIssueListQueryResponse();
		PreviewIssueStruc issueStruc = null;
		Date takeOffTime = null;
		try {
			//查詢彩種下架時間
			List<GameSeries> gameSeriesList = configService.getAllGameSeries(request.getBody().getParam().getLotteryId(), null);
			for(GameSeries gameSeries : gameSeriesList){
				takeOffTime = gameSeries.getTakeOffTime();
			}
			issues = gameIssueService.queryGameIssues(request.getBody().getParam(),takeOffTime);
			if (issues != null && issues.size() > 0) {
				for (GameIssueEntity gie : issues) {
					issueStruc = DTOConvert.gameIssueEntity2IssueStruc(gie);
					issueStrucs.add(issueStruc);
				}
			}
			result.setIssueStrucs(issueStrucs);
			response.setResult(result);
		} catch (PastGameIssueQueryEndTimeAfterStartTimeOfCurrentDayException e) {
			log.error("过去奖期查询结束时间不得晚于当前起始时间", e);
			response.getHead().setStatus(e.getStatus());
		} catch (FutureGameIssueQueryBeginTimeBeforeStartTimeOfCurrentDayException e) {
			log.error("未来奖期查询开始时间不得早于当前起始时间", e);
			response.getHead().setStatus(e.getStatus());
		} catch (QueryBeginTimeAfterEndTimeException e) {
			log.error("查询结束时间不得早于开始时间", e);
			response.getHead().setStatus(e.getStatus());
		} catch (Exception e) {
			log.error("查询奖期列表出错", e);
			throw e;
		}

		log.debug("查询奖期列表完成。");
		return response;
	}

	/**
	 * @Title: queryGameIssueRules
	 * @Description: 4.24 查询奖期规则
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryGameIssueRules")
	@ResponseBody
	public Response<List<GameIssueRuleQueryResponse>> queryGameIssueRules(
			@RequestBody @ValidRequestBody Request<GameIssueRuleQueryRequest> request) throws Exception {

		Response<List<GameIssueRuleQueryResponse>> response = new Response<List<GameIssueRuleQueryResponse>>(request);
		try {
			List<GameIssueRuleEntity> gameIssueRuleEntities = new ArrayList<GameIssueRuleEntity>();
			GameIssueRuleQueryRequest query = request.getBody().getParam();
			if (null != query.getRuleStatus() && query.getRuleStatus() > 0) {
				// 用于回显修改
				gameIssueRuleEntities.add(gameIssueService.queryGameIssueRuleById(query.getLotteryId(),
						query.getRuleId(), query.getRuleStatus()));
			} else {
				gameIssueRuleEntities = gameIssueService.queryGameIssueRules(request.getBody().getParam()
						.getLotteryId(), request.getBody().getParam().getRuleId());
			}
			List<GameIssueRuleQueryResponse> gameIssueRuleQueryResponse = new ArrayList<GameIssueRuleQueryResponse>();
			for (GameIssueRuleEntity rule : gameIssueRuleEntities) {
				GameIssueRuleQueryResponse queryResponse = DTOConvert.ruleEntity2GameIssueRuleQueryResponse(rule);
				if (queryResponse.getRuleStatus() == 4) {
					if (rule.getUpdateTime() != null) {
						if (!DateUtils.format(rule.getUpdateTime(), DateUtils.DATE_FORMAT_PATTERN).equals(
								DateUtils.format(DateUtils.currentDate(), DateUtils.DATE_FORMAT_PATTERN))) {
							queryResponse.setRuleStatus(2);
						}
					} else {
						if (!DateUtils.format(rule.getCreateTime(), DateUtils.DATE_FORMAT_PATTERN).equals(
								DateUtils.format(DateUtils.currentDate(), DateUtils.DATE_FORMAT_PATTERN))) {
							queryResponse.setRuleStatus(2);
						}
					}
				}
				gameIssueRuleQueryResponse.add(queryResponse);
			}
			response.setResult(gameIssueRuleQueryResponse);
		} catch (Exception e) {
			log.error("查询奖期规则错误 ", e);
			throw e;
		}
		return response;
	}

	/**
	 * @Title: addOrUdapteCommonOrSpecialGameIssueRule
	 * @Description: 4.25 新增/修改特例(常规)奖期生成规则
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/updateCommonRuleScheduleStopTime")
	@ResponseBody
	public Object updateCommonRuleScheduleStopTime(
			@RequestBody @ValidRequestBody Request<CommonIssueRuleUpdateRequest> request) throws Exception {
		Response response = new Response(request);
		try {
			gameIssueService.updateCommonRuleScheduleStopTime(request.getBody().getParam().getLotteryId(), request
					.getBody().getParam().getScheduleStopTime());
		} catch (Exception e) {
			log.error("修改等待开奖时间失败", e);
			throw e;
		}
		return response;
	}

	/**
	 * @Title: addOrUdapteCommonOrSpecialGameIssueRule
	 * @Description: 4.25 新增/修改特例(常规)奖期生成规则
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/addOrUdapteCommonOrSpecialGameIssueRule")
	@ResponseBody
	public Object addOrUdapteCommonOrSpecialGameIssueRule(
			@RequestBody @ValidRequestBody Request<CommonOrSpecialGameIssueRuleAddOrUpdateRequest> request)
			throws Exception {
		@SuppressWarnings("rawtypes")
		Response response = new Response(request);
		GameIssueRuleEntity gameIssueRuleEntity = DTOConvert
				.specialGameIssueRuleAddOrUpdateRequest2GameIssueRuleEntity(request);
		try {
			gameIssueRuleService.addOrUpdateCommenOrSpecialGameIssueRule(gameIssueRuleEntity, request.getBody()
					.getParam().getOperationType());
		} catch (GameIssueRuleOverlapErrorException e) {
			log.error("修改更新奖期规则时间段重叠错误", e);
			response.getHead().setStatus(e.getStatus());
		} catch (GameIssueTemplateOverlapErrorException e) {
			log.error("修改更新奖期规则时间段重叠错误", e);
			response.getHead().setStatus(e.getStatus());
		} catch (GameIssueRuleStartTimeShouldSuitableException e) {
			log.error("此规则的生效时间应在当前起始时间" + GameIssueRuleEntity.AUDIT_LIMIT_DAY + "天之后", e);
			response.getHead().setStatus(e.getStatus());
		} catch (GameIssueRuleEffectiveTimeBeforeCurrentTimeException e) {
			log.error("开始执行时间早于当前时间", e);
			response.getHead().setStatus(e.getStatus());
		} catch (GameIssueRuleEffectiveTimeAfterEndTimeException e) {
			log.error("结束执行时间早于开始执行时间", e);
			response.getHead().setStatus(e.getStatus());
		} catch (GameIssueTemplateLastAwardTimeBeforeFirstAwardTimeException e) {
			log.error("分段期间最后一期开奖时间要大于第一期开奖时间", e);
			response.getHead().setStatus(e.getStatus());
		} catch (GameIssueTemplateSalePeriodTimeShouledBiggerThenScheduleStopTimeException e) {
			log.error("等待开奖时间不能超过开奖周期", e);
			response.getHead().setStatus(e.getStatus());
		} catch (GameIssueCommonRuleValidForPublishHasBeenExistException e) {
			log.error("不能存在两条有效待发布常规规则", e);
			response.getHead().setStatus(e.getStatus());
		} catch (Exception e) {
			log.error("修改更新奖期规则错误", e);
			throw e;
		}
		return response;
	}

	/**
	 * @Title: addOrUdapteStopGameIssueRule
	 * @Description: 4.26 新增/修改休市奖期规则
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/addOrUdapteStopGameIssueRule")
	@ResponseBody
	public Object addOrUdapteStopGameIssueRule(
			@RequestBody @ValidRequestBody Request<StopGameIssueRuleAddOrUpdateRequest> request) throws Exception {
		@SuppressWarnings("rawtypes")
		Response response = new Response(request);
		GameIssueRuleEntity gameIssueRuleEntity = DTOConvert
				.stopGameIssueRuleAddOrUpdateRequest2GameIssueRuleEntity(request);
		try {
			gameIssueRuleService.addOrUpdateStopGameIssueRule(gameIssueRuleEntity, request.getBody().getParam()
					.getOperationType());
		} catch (GameIssueRuleOverlapErrorException e) {
			log.error("修改更新奖期规则时间段重叠错误", e);
			response.getHead().setStatus(e.getStatus());
		} catch (GameIssueRuleStartTimeShouldSuitableException e) {
			log.error("此规则的生效时间应在当前起始时间" + GameIssueRuleEntity.AUDIT_LIMIT_DAY + "天之后", e);
			response.getHead().setStatus(e.getStatus());
		} catch (Exception e) {
			log.error("修改更新奖期规则错误", e);
			throw e;
		}
		return response;
	}

	/**
	 * @Title: cancelGameIssueRule
	 * @Description: 4.27 停止（取消）特例奖期生成规则
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/cancelGameIssueRule")
	@ResponseBody
	public Object cancelGameIssueRule(@RequestBody @ValidRequestBody Request<CancelGameIssueRuleRequest> request)
			throws Exception {
		@SuppressWarnings("rawtypes")
		Response response = new Response(request);
		try {
			gameIssueRuleService.cancelGameIssueRule(request.getBody().getParam().getLotteryId(), request.getBody()
					.getParam().getRuleId());
		} catch (GameIssueRuleStartTimeShouldSuitableException e) {
			log.error("此规则的生效时间应在当前起始时间" + GameIssueRuleEntity.AUDIT_LIMIT_DAY + "天之后", e);
			response.getHead().setStatus(e.getStatus());
		} catch (Exception e) {
			log.error("取消奖期规则错误", e);
			throw e;
		}
		return response;
	}

	/**
	 * @Title: auditGameIssueRule
	 * @Description: 4.28 审核生成奖期规则
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/auditGameIssueRule")
	@ResponseBody
	public Object auditGameIssueRule(@RequestBody @ValidRequestBody Request<AuditGameIssueRuleRequest> request)
			throws Exception {
		@SuppressWarnings("rawtypes")
		Response response = new Response(request);
		try {
			gameIssueRuleService.auditGameIssueRule(request.getBody().getParam().getLotteryId(), request.getBody()
					.getParam().getRuleId(), request.getBody().getParam().getCheckType(), request.getBody().getParam()
					.getCheckResult());
		} catch (GameIssueRuleStartTimeShouldSuitableException e) {
			log.error("此规则的生效时间应在当前起始时间" + GameIssueRuleEntity.AUDIT_LIMIT_DAY + "天之后", e);
			response.getHead().setStatus(e.getStatus());
		} catch (Exception e) {
			log.error("删除奖期规则错误", e);
			throw e;
		}
		return response;
	}

	/**
	 * @Title: deleteGameIssueRule
	 * @Description: 4.29 删除奖期规则
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteGameIssueRule")
	@ResponseBody
	public Object deleteGameIssueRule(@RequestBody @ValidRequestBody Request<DeleteGameIssueRuleRequest> request)
			throws Exception {
		@SuppressWarnings("rawtypes")
		Response response = new Response(request);
		try {
			gameIssueRuleService.deleteGameIssueRule(request.getBody().getParam().getLotteryId(), request.getBody()
					.getParam().getRuleId());
		} catch (GameIssueRuleStartTimeShouldSuitableException e) {
			log.error("此规则的生效时间应在当前起始时间" + GameIssueRuleEntity.AUDIT_LIMIT_DAY + "天之后", e);
			response.getHead().setStatus(e.getStatus());
		} catch (Exception e) {
			log.error("删除奖期规则错误", e);
			throw e;
		}
		return response;
	}

	/**
	 * 
	 * @Title: queryGameIssue
	 * @Description: 根据奖期ID及状态获取单个奖期信息
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryGameIssueByRule")
	@ResponseBody
	public Response<GameIssueRuleQueryResponse> queryGameIssueByRule(
			@RequestBody @ValidRequestBody Request<QueryGameIssueRequest> request) throws Exception {

		Response<GameIssueRuleQueryResponse> response = new Response<GameIssueRuleQueryResponse>(request);
		try {

			QueryGameIssueRequest queryRequest = request.getBody().getParam();
			GameIssueRuleEntity gameIssueRuleEntities = gameIssueService.queryGameIssueRuleById(
					queryRequest.getLotteryId(), queryRequest.getRuleId(), queryRequest.getStatus());
			GameIssueRuleQueryResponse queryResponse = DTOConvert
					.ruleEntity2GameIssueRuleQueryResponse(gameIssueRuleEntities);

			response.setResult(queryResponse);

		} catch (Exception e) {
			log.error("queryGameIssueByRule error:", e);
			throw e;
		}

		return response;
	}

	/**
	 * @Title: getGameIssuesByLotteryId
	 * @Description: 根据彩种信息获取奖期列表
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getGameIssuesByLotteryId")
	@ResponseBody
	public Response<List<IssueStruc>> getGameIssuesByLotteryId(
			@RequestBody @ValidRequestBody Request<GetGameIssueStrucsRequest> request) throws Exception {

		Response<List<IssueStruc>> response = new Response<List<IssueStruc>>(request);
		try {
			List<GameIssueEntity> gameIssueEntities = gameIssueService.getGameIssuesByLotteryId(request.getBody()
					.getParam().getLotteryId());
			List<IssueStruc> issueStrucs = new ArrayList<IssueStruc>();
			for (GameIssueEntity issue : gameIssueEntities) {
				IssueStruc issueStruc = new IssueStruc();
				issueStruc.setIssueCode(issue.getIssueCode());
				issueStruc.setWebIssueCode(issue.getWebIssueCode());
				issueStrucs.add(issueStruc);
			}
			response.setResult(issueStrucs);
		} catch (Exception e) {
			log.error("通过彩种id查询奖期列表出错 ", e);
			throw e;
		}
		return response;
	}
	
	
	/**
	 * @Title: getGameIssuesByLotteryId
	 * @Description: 后台查询根据彩种信息获取奖期列表
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getBackGameIssuesByLotteryId")
	@ResponseBody
	public Response<List<IssueStruc>> getBackGameIssuesByLotteryId(
			@RequestBody @ValidRequestBody Request<GetGameIssueStrucsRequest> request) throws Exception {

		Response<List<IssueStruc>> response = new Response<List<IssueStruc>>(request);
		try {
			List<GameIssueEntity> gameIssueEntities = gameIssueService.getBackGameIssuesByLotteryId(request.getBody()
					.getParam().getLotteryId());
			List<IssueStruc> issueStrucs = new ArrayList<IssueStruc>();
			for (GameIssueEntity issue : gameIssueEntities) {
				IssueStruc issueStruc = new IssueStruc();
				issueStruc.setIssueCode(issue.getIssueCode());
				issueStruc.setWebIssueCode(issue.getWebIssueCode());
				issueStrucs.add(issueStruc);
			}
			response.setResult(issueStrucs);
		} catch (Exception e) {
			log.error("通过彩种id查询奖期列表出错 ", e);
			throw e;
		}
		return response;
	}


	/**
	 * 根據 request 取得某彩種當前獎期及前後各10筆的獎期資料。
	 * @param request 獎期資料結構體
	 * @return 第一筆為當期獎期，2~11為前10筆獎期，12~21為後10筆獎期。
	 * @throws Exception
	 */
	@RequestMapping(value = "/getGameIssuesForLockData")
	@ResponseBody
	public Response<List<IssueStruc>> getGameIssuesForLockData(
			@RequestBody @ValidRequestBody Request<GetGameIssueStrucsRequest> request) throws Exception {

		Response<List<IssueStruc>> response = new Response<List<IssueStruc>>(request);
		try {
			List<GameIssueEntity> gameIssueEntities = gameIssueService.getGameIssueForLockData(request.getBody()
					.getParam().getLotteryId());
			List<IssueStruc> issueStrucs = new ArrayList<IssueStruc>();
			for (GameIssueEntity issue : gameIssueEntities) {
				IssueStruc issueStruc = new IssueStruc();
				issueStruc.setIssueCode(issue.getIssueCode());
				issueStruc.setWebIssueCode(issue.getWebIssueCode());
				issueStrucs.add(issueStruc);
			}
			response.setResult(issueStrucs);
		} catch (Exception e) {
			log.error("通过彩种id查询奖期列表出错 ", e);
			throw e;
		}
		return response;
	}

	/**
	 * @Title: getGameIssuesForLockData
	 * @Description: 根据彩种信息获取奖期列表
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/manualGenerateIssues")
	@ResponseBody
	public Response<GameIssueManualGenerateResponse> manualGenerateIssues(
			@RequestBody Request<GameIssueManualGenerateRequest> request) throws Exception {

		Response<GameIssueManualGenerateResponse> response = new Response<GameIssueManualGenerateResponse>(request);
		Date takeOffTime = null;
		try {
			//查詢彩種下架時間
			List<GameSeries> gameSeriesList = configService.getAllGameSeries(request.getBody().getParam().getLotteryId(), null);
			for(GameSeries gameSeries : gameSeriesList){
				takeOffTime = gameSeries.getTakeOffTime();
			}
			GameIssueManualGenerateResponse generateRes = gameIssueService.manualGenerateIssues(request.getBody()
					.getParam(),takeOffTime);
			response.setResult(generateRes);
		} catch (Exception e) {
			log.error("手动生成奖期错误", e);
			throw e;
		}
		return response;
	}

	/**
	 * @Title: getGameIssuesForLockData
	 * @Description: 根据彩种信息获取奖期列表
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getStartWebIssueCode")
	@ResponseBody
	public Response<String> getStartWebIssueCode(@RequestBody Request<GameIssueManualGenerateRequest> request)
			throws Exception {

		Response<String> response = new Response<String>(request);
		try {
			String startWebIssueCode = gameIssueService.getStartWebIssueCode(request.getBody().getParam()
					.getLotteryId(), request.getBody().getParam().getShowStartTime());
			response.setResult(startWebIssueCode);

		} catch (Exception e) {
			log.error("生成开始奖期号错误", e);
			throw e;
		}
		return response;
	}

	/**
	 * @Title: getMaxGameIssuesByLotteryId
	 * @Description: 获取后的奖期
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getMaxGameIssuesByLotteryId")
	@ResponseBody
	public Response<IssueStruc> getMaxGameIssuesByLotteryId(@RequestBody Request<Long> request) throws Exception {

		Response<IssueStruc> response = new Response<IssueStruc>(request);
		try {
			GameIssue gameIssue = gameIssueService.getMaxGameIssuesByLotteryId(request.getBody().getParam());

			if (gameIssue != null) {
				IssueStruc issueStruc = new IssueStruc();
				issueStruc.setIssueCode(gameIssue.getIssueCode());
				issueStruc.setWebIssueCode(gameIssue.getWebIssueCode());
				issueStruc.setSaleDate(DateUtils.format(gameIssue.getSaleEndTime(), DateUtils.DATE_FORMAT_PATTERN));
				response.setResult(issueStruc);
			}

		} catch (Exception e) {
			log.error("通过彩种id查询奖期列表出错 ", e);
			throw e;
		}
		return response;
	}

	/**
	 * @Title: queryTraceGameIssues
	 * @Description: 获取彩种可追号奖期列表
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryTraceGameIssues")
	@ResponseBody
	public Response<TraceGameIssueListQueryResponse> queryTraceGameIssues(
			@RequestBody @ValidRequestBody Request<TraceGameIssueListQueryRequest> request) throws Exception {

		log.debug("开始查询奖期列表......");
		Response<TraceGameIssueListQueryResponse> response = new Response<TraceGameIssueListQueryResponse>(request);

		List<GameIssueEntity> issues = null;
		List<PreviewIssueStruc> issueStrucs = new ArrayList<PreviewIssueStruc>();
		TraceGameIssueListQueryResponse result = new TraceGameIssueListQueryResponse();
		PreviewIssueStruc issueStruc = null;
		try {
			issues = gameIssueService.queryTraceGameIssues(request.getBody().getParam().getLotteryId(), request
					.getBody().getParam().getMaxCountIssue());
			if (issues != null && issues.size() > 0) {
				for (GameIssueEntity gie : issues) {
					issueStruc = DTOConvert.gameIssueEntity2IssueStruc(gie);
					issueStrucs.add(issueStruc);
				}
			}
			result.setIssueStrucs(issueStrucs);
			response.setResult(result);
		} catch (Exception e) {
			log.error("查询奖期列表出错", e);
			throw e;
		}

		log.debug("查询奖期列表完成。");
		return response;
	}

	/**
	 * @Title: deleteGameIssues
	 * @Description: 删除奖期失败
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteGameIssues")
	@ResponseBody
	public Response<GameIssueManualGenerateResponse> deleteGameIssues(
			@RequestBody Request<DeleteGameIssueRequest> request) throws Exception {
		Response<GameIssueManualGenerateResponse> response = new Response<GameIssueManualGenerateResponse>();
		GameIssueManualGenerateResponse result = new GameIssueManualGenerateResponse();
		try {
			DeleteGameIssueRequest deleteGameIssueRequest = request.getBody().getParam();
			result = gameIssueService.manualDeleteIssues(deleteGameIssueRequest.getType(),
					deleteGameIssueRequest.getStart(), deleteGameIssueRequest.getEnd(),
					deleteGameIssueRequest.getLotteryId());
		} catch (Exception e) {
			log.error("查询奖期列表出错", e);
			throw e;
		}
		response.setResult(result);
		return response;
	}

	/**
	 * @Title: indexInit
	 * @Description: 获取首页上次用户游戏
	 * @param gametype
	 * @return// 动态配置大部分参数将在cookie中获取，部分参数在测试中暂时写死数据
	 * @throws Exception
	 */
	@RequestMapping(value = "/indexInit")
	@ResponseBody
	public Object indexInit(@RequestParam("uname") String uname, @RequestParam("userId") String userId)
			throws Exception {

		long lotteryId = 99101;

		RedisClient rc = new RedisClient();

		String lid = rc.get(lastUserPlayGamePR + userId);

		if (null != lid) {
			lotteryId = new Long(lid);
		}
		/**
		 * 根据用户ID获取redis游戏
		 */

		// 获取当前奖期信息

		GameIssueQueryResponse gameIssue = new GameIssueQueryResponse();

		GameIssueEntity gameIssueEntity = gameIssueService.queryGameIssue(lotteryId, null);
		GameDrawResult gameDrawResult = gameDrawService.getDrawResuleByLotteryIdAndIssueCode(lotteryId, null);

		gameIssue.setIssueCode(gameIssueEntity.getIssueCode());
		if (gameDrawResult != null) {
			gameIssue.setLastWebIssueCode(gameDrawResult.getWebIssueCode());
			gameIssue.setNumberRecord(gameDrawResult.getNumberRecord());
		}
		gameIssue.setLotteryId(gameIssueEntity.getLottery().getLotteryId());
		gameIssue.setSaleStartTime(DataConverterUtil.convertDate2Long(gameIssueEntity.getSaleStartTime()));
		gameIssue.setSaleEndTime(DataConverterUtil.convertDate2Long(gameIssueEntity.getSaleEndTime()));
		gameIssue.setStatus(gameIssueEntity.getStatus().getValue());
		gameIssue.setWebIssueCode(gameIssueEntity.getWebIssueCode());

		GameConfigAndAwardData gd = new GameConfigAndAwardData();

		// 将开奖号码用逗号分隔
		char[] repeatChars = gameIssue.getNumberRecord().toCharArray();
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < repeatChars.length; i++) {
			sb.append(repeatChars[i]);
			if (i != repeatChars.length - 1) {
				sb.append(",");
			}
		}
		gd.setLastballs(sb.toString());// 获取上期开奖号码
		gd.setLastnumber(gameIssue.getLastWebIssueCode());// 获取上期奖期
		gd.setNowstoptime(DateUtils.format(DataConverterUtil.convertLong2Date(gameIssue.getSaleEndTime()),
				DateUtils.DATETIME_JSVIEW_FORMAT_PATTERN));// 当前期投注结束时间
		gd.setNowtime(DateUtils.format(new Date(), DateUtils.DATETIME_JSVIEW_FORMAT_PATTERN));// 当前服务器时间
		gd.setNumber(gameIssue.getWebIssueCode());// 当前web期号
		gd.setIssueCode(gameIssue.getIssueCode());// 当前期号
		gd.setResulttime(DateUtils.format(DataConverterUtil.convertLong2Date(gameIssue.getSaleStartTime()),
				DateUtils.DATETIME_JSVIEW_FORMAT_PATTERN));// 当前期预计开始时间
		gd.setUsername(uname);// 当前用户名称
		return gd;
	}
	
	
	
	/**
	 * @Title: getGameIssueNumberRecord
	 * @Description: 根据彩种信息获取奖期列表
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getGameIssueNumberRecord")
	@ResponseBody
	public Response<List<IssueStruc>> getGameIssueNumberRecord(@RequestBody Request<Long> request)
			throws Exception {

		Response<List<IssueStruc>> response = new Response<List<IssueStruc>>(request);
		List<IssueStruc> Issuelist = new ArrayList<IssueStruc>();
		
		try {
			List<GameIssue> list = gameIssueService.getGameIssueNumberRecord(request.getBody().getParam());
			for(GameIssue gameIssue:list){
				IssueStruc struc = new IssueStruc();
				struc.setWebIssueCode(gameIssue.getWebIssueCode());
				struc.setNumberRecord(gameIssue.getNumberRecord());
				Issuelist.add(struc);
			}
			response.setResult(Issuelist);

		} catch (Exception e) {
			log.error("获取奖期错误", e);
			throw e;
		}
		return response;
	}
	
	
	/**
	 * @Title: updateOpenAwardTime
	 * @Description: 更新開獎時間
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateOpenAwardTime")
	@ResponseBody
	public Response<GameIssueQueryResponse> updateOpenAwardTime(@RequestBody Request<GameIssueQueryRequest> request) throws Exception {
		Response<GameIssueQueryResponse> response = new Response<GameIssueQueryResponse>();
		log.debug("updateOpenAwardTime......");
		GameIssueQueryResponse gameIssueResponse = new GameIssueQueryResponse();
		try {
			
			GameIssueQueryRequest gameIssuseRequert =  (GameIssueQueryRequest)request.getBody().getParam();
			
			gameIssueResponse = gameIssueService.updateOpenAwardTime(gameIssuseRequert);
			
			response.setResult(gameIssueResponse);
		} catch (Exception e) {
			log.error("updateOpenAwardTime error ", e);
			throw e;
		}

		log.debug("updateOpenAwardTime end");
		return response;
	}
	
	/**
	 * @Title: getNextOpenAwardTime
	 * @Description: 取得下一期開獎時間
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getNextOpenAwardTime")
	@ResponseBody
	public Response<GameIssueQueryResponse> getNextOpenAwardTime(@RequestBody Request<GameIssueQueryRequest> request) throws Exception {
		Response<GameIssueQueryResponse> response = new Response<GameIssueQueryResponse>();
		log.debug("getNextOpenAwardTime......");
		GameIssueQueryResponse gameIssueResponse = new GameIssueQueryResponse();
		try {
			
			GameIssueQueryRequest gameIssuseRequert =  (GameIssueQueryRequest)request.getBody().getParam();
			gameIssueResponse = gameIssueService.getNextOpenAwardTime(gameIssuseRequert);
			response.setResult(gameIssueResponse);
		} catch (Exception e) {
			log.error("getNextOpenAwardTime error ", e);
			throw e;
		}

		log.debug("getNextOpenAwardTime end");
		return response;
	}
	
	/**
	 * @Title: getNextOpenAwardTime
	 * @Description: 取得下一期開獎時間
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/doExtendOpenAwardTime")
	@ResponseBody
	public Response<GameIssueQueryResponse> doExtendOpenAwardTime(@RequestBody Request<GameIssueQueryRequest> request) throws Exception {
		Response<GameIssueQueryResponse> response = new Response<GameIssueQueryResponse>();
		log.debug("doExtendOpenAwardTime......");
		GameIssueQueryResponse gameIssueResponse = new GameIssueQueryResponse();
		try {
			
			GameIssueQueryRequest gameIssuseRequert =  (GameIssueQueryRequest)request.getBody().getParam();
			gameIssueResponse = gameIssueService.doExtendOpenAwardTime(gameIssuseRequert);
			response.setResult(gameIssueResponse);
		} catch (Exception e) {
			log.error("doExtendOpenAwardTime error ", e);
			throw e;
		}

		log.debug("doExtendOpenAwardTime end");
		return response;
	}

	/**
	 * 查詢彩種歷史開獎資料
	 */
	@ResponseBody
	@RequestMapping(value = "/historyLotteryAward")
	public Response<HistoryLotteryAwardResponse> historyLotteryAward(@RequestBody Request<HistoryLotteryAwardRequest> request) {
		HistoryLotteryAwardRequest hlaReq = request.getBody().getParam();
		log.debug("Start historyLotteryAward:{}", hlaReq.toString());
		try {
			Response<HistoryLotteryAwardResponse> response = new Response<HistoryLotteryAwardResponse>();
			HistoryLotteryAwardResponse hlaResp = new HistoryLotteryAwardResponse();
			hlaResp = gameIssueService.getHistoryLotteryAward(hlaReq);
			response.setResult(hlaResp);
			return response;
		} catch(Exception e) {
			log.error("historyLotteryAward get GameIssue error.{}", e.getMessage(), e);
		}
		return null;
	}
}
