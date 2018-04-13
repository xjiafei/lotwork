package com.winterframework.firefrog.game.web.controller;


import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.core.type.TypeReference;
import com.winterframework.firefrog.common.httpjsonclient.IHttpJsonClient;
import com.winterframework.firefrog.common.util.DataConverterUtil;
import com.winterframework.firefrog.common.util.DateUtils;
import com.winterframework.firefrog.game.dao.vo.ActivityAwardConfig;
import com.winterframework.firefrog.game.dao.vo.ActivityConfig;
import com.winterframework.firefrog.game.dao.vo.MMCRanking;
import com.winterframework.firefrog.game.web.controller.view.ExcelView;
import com.winterframework.firefrog.game.web.controller.view.ExportViewDataModel;
import com.winterframework.firefrog.game.web.dto.ActivityConfigRequest;
import com.winterframework.firefrog.game.web.dto.ActivityConfigResponse;
import com.winterframework.firefrog.game.web.dto.BetLimitQueryRequest;
import com.winterframework.firefrog.game.web.dto.BetLimitQueryResponse;
import com.winterframework.firefrog.game.web.dto.GameManualRecordRequest;
import com.winterframework.firefrog.game.web.dto.GameReportRequest;
import com.winterframework.firefrog.game.web.dto.GameReportStruc;
import com.winterframework.firefrog.game.web.dto.GameRiskTransactionReportResponse;
import com.winterframework.firefrog.game.web.dto.GameRiskTransactionReportStruc;
import com.winterframework.firefrog.game.web.dto.LotteryResultStruc;
import com.winterframework.firefrog.game.web.dto.LotteryResultStrucUI;
import com.winterframework.firefrog.game.web.dto.MmcRankingHistoryResponse;
import com.winterframework.firefrog.game.web.dto.MmcRankingRequest;
import com.winterframework.firefrog.game.web.dto.MmcRankingResponse;
import com.winterframework.firefrog.game.web.dto.PageForView;
import com.winterframework.firefrog.game.web.dto.QueryLotteryResultRequest;
import com.winterframework.firefrog.game.web.dto.QueryLotteryResultResponse;
import com.winterframework.firefrog.game.web.dto.QueryRedEnvelopeRequest;
import com.winterframework.firefrog.game.web.dto.QueryRedEnvelopeResponse;
import com.winterframework.firefrog.game.web.dto.RedEnvelopeStruc;
import com.winterframework.firefrog.game.web.dto.RedEnvelopeStrucUI;
import com.winterframework.firefrog.game.web.util.PageUtils;
import com.winterframework.firefrog.shortlived.sheepactivity.dao.vo.ActivitySheepBigLittle;
import com.winterframework.firefrog.shortlived.sheepactivity.dao.vo.ActivitySheepDetail;
import com.winterframework.firefrog.shortlived.sheepactivity.dao.vo.ActivitySheepHongBao;
import com.winterframework.firefrog.shortlived.sheepactivity.dao.vo.ActivitySheepWheelSurf;
import com.winterframework.firefrog.shortlived.sheepactivity.dto.ActivitySheepBigLittleResponse;
import com.winterframework.firefrog.shortlived.sheepactivity.dto.ActivitySheepDetailResponse;
import com.winterframework.firefrog.shortlived.sheepactivity.dto.ActivitySheepHongBaoResponse;
import com.winterframework.firefrog.shortlived.sheepactivity.dto.ActivitySheepHongBaoUI;
import com.winterframework.firefrog.shortlived.sheepactivity.dto.ActivitySheepWheelSurfResponse;
import com.winterframework.modules.page.PageRequest;
import com.winterframework.modules.spring.exetend.PropertyConfig;
import com.winterframework.modules.web.jsonresult.Response;
import com.winterframework.modules.web.jsonresult.ResultPager;
import com.winterframework.modules.web.util.RequestContext;

/** 
* @ClassName: BetLimitWebController 
* @Description: 投注限制请求web controller
* @author Alan
* @date 2013-9-23 下午3:01:15 
*  
*/
@Controller("activityWebController")
@RequestMapping(value = "/gameoa")
public class ActivityWebController {

	private Logger logger = LoggerFactory.getLogger(ActivityWebController.class);

	@Resource(name = "httpJsonClientImpl")
	private IHttpJsonClient httpClient;

	@PropertyConfig(value = "url.connect")
	private String serverPath;

	@PropertyConfig(value = "url.operations.queryActivityConfig")
	private String queryActivityConfigUrl;

	@PropertyConfig(value = "url.operations.updateActivityConfig")
	private String updateActivityConfigUrl;
	
	@PropertyConfig(value = "url.operations.getActivityConfig")
	private String getActivityConfig;
	
	
	//@PropertyConfig("dailyActivityStartTime")
	private String dailyActivityStartTime;
	
	//@PropertyConfig("daily.activity.end.time")
	private String dailyActivityEndTime;
	
	@RequestMapping(value = "/queryActivityConfig")
	public String queryActivityConfig(Model model) throws Exception {
		Response<ActivityConfigResponse> response = new Response<ActivityConfigResponse>();
		ActivityConfigRequest query = new ActivityConfigRequest();
		
		query.setId(1L);
		logger.info("query queryActivityConfig start");

		try {
			Long userid = RequestContext.getCurrUser().getId();
			String username = RequestContext.getCurrUser().getUserName();
			response = httpClient.invokeHttp(serverPath + queryActivityConfigUrl, query, userid, username,
					ActivityConfigResponse.class);
		} catch (Exception e) {
			logger.error("query queryActivityConfig error");
			throw e;
		}
		logger.info("query queryActivityConfig end");
		
		if(response != null && response.getBody() !=null &&  response.getBody().getResult() != null)
		model.addAttribute("configs", response.getBody().getResult().getConfigs());
		return "/operations/activity/activity";
	
	}

	@RequestMapping(value = "/updateActivityConfig")
	public String updateActivityConfig(Model model, Long[] id ,Long[] lastNumber,Double[] multiple ,Double[] ratio) throws Exception {
	
		if(id != null){
			for (int i = 0; i < id.length; i++) {
				System.out.println(id[i]);
				System.out.println(lastNumber[i]);
				ActivityAwardConfig config = new ActivityAwardConfig();
				config.setId(id[i]);
				config.setMultiple((long)(multiple[i]*100));
				config.setRatio((long) (ratio[i]*100));
				config.setLastNumber(lastNumber[i]);
				try {
					httpClient.invokeHttp(serverPath + updateActivityConfigUrl, config,Object.class);
				} catch (Exception e) {
					logger.error("query betLimit error");
					throw e;
				}
			}
		}

		logger.info("query betLimit start");

		
		logger.info("query betLimit end");

		return queryActivityConfig(model);
	}
	
	@RequestMapping(value = "/queryActivityHongBaoLog")
	public String queryActivityHongBaoLog(Model model,String userName ,Integer status,
			String timestart,String timeend ,
			@ModelAttribute("page") PageRequest<QueryRedEnvelopeRequest> page) throws Exception {
		Response<QueryRedEnvelopeResponse> response = new Response<QueryRedEnvelopeResponse>();
		//ActivityConfigRequest query = new ActivityConfigRequest();
		System.out.println(userName);
		System.out.println(status);
		System.out.println(timestart);
		System.out.println(timeend);
		model.addAttribute("userName", userName);
		model.addAttribute("status", status);
		model.addAttribute("timestart", timestart);
		model.addAttribute("timeend", timeend);
		if(page != null){
			System.out.println(page.getPageNo());
			System.out.println(page.getPageSize());
		}else{
			page = new PageRequest<QueryRedEnvelopeRequest>();
		}
		
		if(timestart == null){
			timestart = dailyActivityStartTime + " 00:00:00";
		}
		if(timeend == null){
			timeend = dailyActivityEndTime+ " 23:59:59";
		}

		
		//query.setId(1L);
		logger.info("query queryActivityHongBaoLog start");
		QueryRedEnvelopeRequest query  = new QueryRedEnvelopeRequest();
		query.setStartTime(DateUtils.parse(timestart,DateUtils.DATETIME_FORMAT_PATTERN));
		query.setEndTime(DateUtils.parse(timeend ,DateUtils.DATETIME_FORMAT_PATTERN));
		query.setAccount(userName);
		query.setChannel(status);
		try {
			response = httpClient.invokeHttp(serverPath + "/gameoa/queryRedEnvelope", query,PageUtils.getPager(page), 
					QueryRedEnvelopeResponse.class);
		} catch (Exception e) {
			logger.error("query queryActivityConfig error");
			throw e;
		}
		logger.info("query queryActivityHongBaoLog end");
	
		
		if(response != null && response.getBody() !=null &&  response.getBody().getResult() != null){
			model.addAttribute("struc", response.getBody().getResult().getRedEnvelopeStruc());
			model.addAttribute("bet", response.getBody().getResult().getTotalBetAmount());
			model.addAttribute("red", response.getBody().getResult().getRedEnvelopeAmount());
			ResultPager rp = response.getBody().getPager();
			PageForView pages = PageUtils.getPageForView(page, rp);
			pages.setPageNo(page.getPageNo());
			model.addAttribute("page", pages);
		}else{
			model.addAttribute("page", null);
			model.addAttribute("struc", null);
			model.addAttribute("bet", 0);
			model.addAttribute("red", 0);
		}

		return "/operations/activity/activityHongBaoLog";
	
	}
	
	@RequestMapping(value = "exportActivityHongBaoLog")
	public ModelAndView exportGameRiskTransactionReport(
			@ModelAttribute("page") PageRequest<QueryRedEnvelopeRequest> page) throws Exception {
		Response<QueryRedEnvelopeResponse> response = new Response<QueryRedEnvelopeResponse>();

		logger.info("export WinsReport start");
		try {
			QueryRedEnvelopeRequest query  = new QueryRedEnvelopeRequest();
			String timestart = dailyActivityStartTime + " 00:00:00";
			String timeend = dailyActivityEndTime+ " 23:59:59";			
			query.setStartTime(DateUtils.parse(timestart,DateUtils.DATETIME_FORMAT_PATTERN));
			query.setEndTime(DateUtils.parse(timeend ,DateUtils.DATETIME_FORMAT_PATTERN));
			page.setPageSize(100000);
			response = httpClient.invokeHttp(serverPath + "/gameoa/queryRedEnvelope", query,
					PageUtils.getPager(page), new TypeReference<Response<QueryRedEnvelopeResponse>>() {
					});
		} catch (Exception e) {
			logger.error("export WinsReport error", e);
			throw e;
		}

		if (response.getBody().getResult() != null && response.getBody().getResult().getRedEnvelopeStruc() != null) {

			List<RedEnvelopeStruc> reports = response.getBody().getResult().getRedEnvelopeStruc();
			List<RedEnvelopeStrucUI> reportUIs = new ArrayList<RedEnvelopeStrucUI>();
			if(reports != null){
				for (RedEnvelopeStruc redEnvelopeStruc : reports) {
					RedEnvelopeStrucUI ui = new RedEnvelopeStrucUI(redEnvelopeStruc);
					reportUIs.add(ui);
				}
			}
			//执行导出功能
			ExportViewDataModel viewTemplates = new ExportViewDataModel();
			String[] titles = new String[] { "时间", "用户名", "投注金额", "中奖金额", "状态", "渠道"};
			String[] columns = new String[] { "betDate", "account", "betAmount", "award", "status", "channel"};

			viewTemplates.setFileName("红包报表导出数据");
			viewTemplates.setHeader(titles);
			viewTemplates.setColumns(columns);
			viewTemplates.setDataList(reportUIs);

			Map<String, Object> model = new HashMap<String, Object>();
			model.put("dataModel", viewTemplates);

			return new ModelAndView(new ExcelView(), model);

		}

		return null;
	}
	
	
	
	
	
	@RequestMapping(value = "/queryActivityChouJianUserLog")
	public String queryActivityChouJianUserLog(Model model,String userName ,Long status,
			String timestart,String timeend ,
			@ModelAttribute("page") PageRequest<Object> page) throws Exception {
		Response<ActivityConfigResponse> response = new Response<ActivityConfigResponse>();
		//ActivityConfigRequest query = new ActivityConfigRequest();
		
		System.out.println(userName);
		System.out.println(status);
		System.out.println(timestart);
		System.out.println(timeend);
		if(page != null){
			System.out.println(page.getPageNo());
			System.out.println(page.getPageSize());
		}else{
			page = new PageRequest<Object>();
		}
		
		//query.setId(1L);
		logger.info("query queryActivityChouJianUserLog start");

		try {
			Long userid = RequestContext.getCurrUser().getId();
			String username = RequestContext.getCurrUser().getUserName();
		//	response = httpClient.invokeHttp(serverPath + queryActivityConfigUrl, query, userid, username,
		//			ActivityConfigResponse.class);
		} catch (Exception e) {
			logger.error("query queryActivityChouJianUserLog error");
			throw e;
		}
		logger.info("query queryActivityChouJianUserLog end");
		
		
		ResultPager rp = new ResultPager(10, 19, 20);
		PageForView pages = PageUtils.getPageForView(page, rp);
		pages.setPageNo(page.getPageNo());
		model.addAttribute("page", pages);
		//if(response != null && response.getBody() !=null &&  response.getBody().getResult() != null)
		//model.addAttribute("configs", response.getBody().getResult().getConfigs());
		return "/operations/activity/activityChouJianUserLog";
	
	}
	
	@RequestMapping(value = "/queryActivityChouJianLog")
	public String queryActivityChouJianLog(Model model,String userName ,Long status,
			String timestart,String timeend ,
			@ModelAttribute("page") PageRequest<Object> page) throws Exception {
		Response<ActivityConfigResponse> response = new Response<ActivityConfigResponse>();
		//ActivityConfigRequest query = new ActivityConfigRequest();
		System.out.println(userName);
		System.out.println(status);
		System.out.println(timestart);
		System.out.println(timeend);
		if(page != null){
			System.out.println(page.getPageNo());
			System.out.println(page.getPageSize());
		}else{
			page = new PageRequest<Object>();
		}
		//query.setId(1L);
		logger.info("query queryActivityChouJianLog start");

		try {
			Long userid = RequestContext.getCurrUser().getId();
			String username = RequestContext.getCurrUser().getUserName();
		//	response = httpClient.invokeHttp(serverPath + queryActivityConfigUrl, query, userid, username,
		//			ActivityConfigResponse.class);
		} catch (Exception e) {
			logger.error("query queryActivityChouJianLog error");
			throw e;
		}
		logger.info("query queryActivityChouJianLog end");
		ResultPager rp = new ResultPager(10, 19, 20);
		PageForView pages = PageUtils.getPageForView(page, rp);
		pages.setPageNo(page.getPageNo());
		model.addAttribute("page", pages);
		//if(response != null && response.getBody() !=null &&  response.getBody().getResult() != null)
		//model.addAttribute("configs", response.getBody().getResult().getConfigs());
		return "/operations/activity/activityChouJianLog";
	
	}
	
	
	@RequestMapping(value = "exportActivityChouJianLog")
	public ModelAndView exportActivityChouJianLog(
			@ModelAttribute("page") PageRequest<QueryRedEnvelopeRequest> page) throws Exception {

		Response<QueryLotteryResultResponse> response = new Response<QueryLotteryResultResponse>();
		logger.info("export exportActivityChouJianLog start");
		try {
			logger.info("query queryActivityHongBaoLog start");
			QueryLotteryResultRequest query  = new QueryLotteryResultRequest();	
			String timestart = dailyActivityStartTime + " 00:00:00";
			String timeend = dailyActivityEndTime+ " 23:59:59";			
			query.setStartTime(DateUtils.parse(timestart,DateUtils.DATETIME_FORMAT_PATTERN));
			query.setEndTime(DateUtils.parse(timeend ,DateUtils.DATETIME_FORMAT_PATTERN));
			page.setPageSize(100000);
			response = httpClient.invokeHttp(serverPath + "/gameoa/queryLotteryResult", query,PageUtils.getPager(page), 
					QueryLotteryResultResponse.class);
		} catch (Exception e) {
			logger.error("export WinsReport error", e);
			throw e;
		}

		if (response.getBody().getResult() != null && response.getBody().getResult().getList() != null) {

			List<LotteryResultStruc> reports = response.getBody().getResult().getList();
			List<LotteryResultStrucUI> reportUIs = new ArrayList<LotteryResultStrucUI>();
			if(reports != null){
				for (LotteryResultStruc redEnvelopeStruc : reports) {
					LotteryResultStrucUI ui = new LotteryResultStrucUI(redEnvelopeStruc);
					reportUIs.add(ui);
				}
			}
			//执行导出功能
			ExportViewDataModel viewTemplates = new ExportViewDataModel();
			String[] titles = new String[] {  "用户名","时间", "奖项",  "渠道"};
			String[] columns = new String[] { "account","rewardTime",  "rewardName",  "channel"};

			viewTemplates.setFileName("用户抽奖报表导出数据");
			viewTemplates.setHeader(titles);
			viewTemplates.setColumns(columns);
			viewTemplates.setDataList(reportUIs);

			Map<String, Object> model = new HashMap<String, Object>();
			model.put("dataModel", viewTemplates);

			return new ModelAndView(new ExcelView(), model);

		}

		return null;
	}
	
	
	@RequestMapping(value = "/exportActivityYaDaXiaoLog")
	public ModelAndView exportActivityYaDaXiaoLog(Long pageStatus,
			@ModelAttribute("page") PageRequest<ActivitySheepBigLittle> page) throws Exception {
		Response<ActivitySheepBigLittleResponse> response = new Response<ActivitySheepBigLittleResponse>();

		Response<ActivitySheepDetailResponse> responseDetail = new Response<ActivitySheepDetailResponse>();
		if (page == null) {
			page = new PageRequest<ActivitySheepBigLittle>();
			page.setPageSize(Integer.MAX_VALUE);
		}
		ActivitySheepDetail requestDetail = new ActivitySheepDetail();
		try {
			if (pageStatus == null) {
				response = httpClient.invokeHttp(serverPath + "/gameoa/querySheepBigLittle",
						PageUtils.getPager(page), ActivitySheepBigLittleResponse.class);
			} else {

				
				requestDetail.setStatus(pageStatus);
				requestDetail.setActivityId(4l);
				responseDetail = httpClient.invokeHttp(serverPath + "/gameoa/querySheepDetail", requestDetail,
						PageUtils.getPager(page), ActivitySheepDetailResponse.class);
			}
		} catch (Exception e) {
			logger.error("query queryActivityYaDaXiaoLog error");
			throw e;
		}
		ExportViewDataModel viewTemplates = new ExportViewDataModel();
		if (response != null && response.getBody() != null && response.getBody().getResult() != null) {
			List<ActivitySheepBigLittle> list = response.getBody().getResult().getList();
			String[] titles = new String[] { "用户名", "剩余次数", "当前连胜次数", "最高连胜次数","累计充值金额", "来源"};
			String[] columns = new String[] { "userName", "lastNum", "nextWinNumNow", "maxNextWinNum", "allAwardUi", "channelStr"};
			viewTemplates.setHeader(titles);
			viewTemplates.setColumns(columns);
			viewTemplates.setFileName("押大小报表导出数据");
			viewTemplates.setDataList(list);

			Map<String, Object> model = new HashMap<String, Object>();
			model.put("dataModel", viewTemplates);

			return new ModelAndView(new ExcelView(), model);
			

		} else if (responseDetail != null && responseDetail.getBody() != null
				&& responseDetail.getBody().getResult() != null) {
			List<ActivitySheepDetail> list = responseDetail.getBody().getResult().getList();
			if(pageStatus == 3){
				String[] titles = new String[] { "用户名", "押宝时间", "结果", "奖金","来源"};
				String[] columns = new String[] { "userName", "activityTimeStr", "result", "awardUi", "channelStr"};
				viewTemplates.setHeader(titles);
				viewTemplates.setColumns(columns);
			}
			if(pageStatus == 4){
				String[] titles = new String[] { "用户名", "押宝时间", "结果", "奖金","来源","审核人","审核时间"};
				String[] columns = new String[] { "userName", "activityTimeStr", "result", "awardUi", "channelStr","verifyName","verifyTimeStr"};
				viewTemplates.setHeader(titles);
				viewTemplates.setColumns(columns);
			}
			
			if(pageStatus == 5){
				String[] titles = new String[] { "用户名", "押宝时间", "结果", "奖金","来源","原因","审核人","审核时间"};
				String[] columns = new String[] { "userName", "activityTimeStr", "result", "awardUi", "channelStr","verifyReason","verifyName","verifyTimeStr"};
				viewTemplates.setHeader(titles);
				viewTemplates.setColumns(columns);
			}
			
			viewTemplates.setFileName("押大小报表导出数据");
			viewTemplates.setDataList(list);

			Map<String, Object> model = new HashMap<String, Object>();
			model.put("dataModel", viewTemplates);

			return new ModelAndView(new ExcelView(), model);
		}
		
		return null;
	}
	
	@RequestMapping(value = "/exportActivityZpLog")
	public ModelAndView exportActivityZpLog(Long pageStatus,
			@ModelAttribute("page") PageRequest<ActivitySheepBigLittle> page) throws Exception {
		Response<ActivitySheepWheelSurfResponse> response = new Response<ActivitySheepWheelSurfResponse>();

		Response<ActivitySheepDetailResponse> responseDetail = new Response<ActivitySheepDetailResponse>();

		if (page == null) {
			page = new PageRequest<ActivitySheepBigLittle>();
			page.setPageSize(Integer.MAX_VALUE);
		}
		ActivitySheepDetail requestDetail = new ActivitySheepDetail();
		try {
			if (pageStatus == null) {
				response = httpClient.invokeHttp(serverPath + "/gameoa/querySheepWheelSurf",
						PageUtils.getPager(page), ActivitySheepWheelSurfResponse.class);
			} else {
				requestDetail.setStatus(pageStatus);
				requestDetail.setActivityId(5l);
				responseDetail = httpClient.invokeHttp(serverPath + "/gameoa/querySheepDetail", requestDetail,
						PageUtils.getPager(page), ActivitySheepDetailResponse.class);
			}
		} catch (Exception e) {
			logger.error("query queryActivityYaDaXiaoLog error");
			throw e;
		}
		ExportViewDataModel viewTemplates = new ExportViewDataModel();
		if (response != null && response.getBody() != null && response.getBody().getResult() != null) {
			List<ActivitySheepWheelSurf> list = response.getBody().getResult().getList();
			String[] titles = new String[] { "用户名", "剩余次数", "累计充值金额", "累计中奖金额","来源"};
			String[] columns = new String[] { "userName", "lastNum", "allRechargeUi", "allAwardUi", "channelStr"};
			viewTemplates.setHeader(titles);
			viewTemplates.setColumns(columns);
			viewTemplates.setFileName("转盘报表导出数据");
			viewTemplates.setDataList(list);

			Map<String, Object> model = new HashMap<String, Object>();
			model.put("dataModel", viewTemplates);

			return new ModelAndView(new ExcelView(), model);
			
		} else if (responseDetail != null && responseDetail.getBody() != null
				&& responseDetail.getBody().getResult() != null) {
			List<ActivitySheepDetail> list = responseDetail.getBody().getResult().getList();
			if(pageStatus == 3){
				String[] titles = new String[] { "用户名", "押宝时间", "结果", "奖金","来源"};
				String[] columns = new String[] { "userName", "activityTimeStr", "result", "awardUi", "channelStr"};
				viewTemplates.setHeader(titles);
				viewTemplates.setColumns(columns);
			}
			if(pageStatus == 4){
				String[] titles = new String[] { "用户名", "押宝时间", "结果", "奖金","来源","审核人","审核时间"};
				String[] columns = new String[] { "userName", "activityTimeStr", "result", "awardUi", "channelStr","verifyName","verifyTimeStr"};
				viewTemplates.setHeader(titles);
				viewTemplates.setColumns(columns);
			}
			
			if(pageStatus == 5){
				String[] titles = new String[] { "用户名", "押宝时间", "结果", "奖金","来源","原因","审核人","审核时间"};
				String[] columns = new String[] { "userName", "activityTimeStr", "result", "awardUi", "channelStr","verifyReason","verifyName","verifyTimeStr"};
				viewTemplates.setHeader(titles);
				viewTemplates.setColumns(columns);
			}
			
			viewTemplates.setFileName("转盘报表导出数据");
			viewTemplates.setDataList(list);

			Map<String, Object> model = new HashMap<String, Object>();
			model.put("dataModel", viewTemplates);

			return new ModelAndView(new ExcelView(), model);
		}
		return null;
	}
	
	@RequestMapping(value = "exportActivitySheepHongBao")
	public ModelAndView exportActivitySheepHongBao(Long pageStatus,
			@ModelAttribute("page") PageRequest<ActivitySheepHongBao> page) throws Exception {
		Response<ActivitySheepHongBaoResponse> response = new Response<ActivitySheepHongBaoResponse>();

		logger.info("export WinsReport start");
		try {
			ActivitySheepHongBao query  = new ActivitySheepHongBao();
			//query.setVerifyStatus(pageStatus);
			page.setPageSize(100000);
			if(pageStatus!=null && pageStatus == 0){
				pageStatus = null;
			}
			query.setVerifyStatus(pageStatus);
			response = httpClient.invokeHttp(serverPath + "/gameoa/querySheepHongBao", query,PageUtils.getPager(page), 
					new TypeReference<Response<ActivitySheepHongBaoResponse>>() {
			});
		} catch (Exception e) {
			logger.error("export WinsReport error", e);
			throw e;
		}

		if (response.getBody().getResult() != null && response.getBody().getResult().getList() != null) {

			List<ActivitySheepHongBao> reports = response.getBody().getResult().getList();
			List<ActivitySheepHongBaoUI> reportUIs = new ArrayList<ActivitySheepHongBaoUI>();
			if(reports != null){
				for (ActivitySheepHongBao redEnvelopeStruc : reports) {
					ActivitySheepHongBaoUI ui = new ActivitySheepHongBaoUI(redEnvelopeStruc);
					reportUIs.add(ui);
				}
			}
			
			//执行导出功能
			ExportViewDataModel viewTemplates = new ExportViewDataModel();
			
			if(pageStatus == null || pageStatus ==0){
				String[] titles = new String[] { "报名时间","用户名", "奖金", "类型", "目标金额","累积金额", "状态", "渠道"};
				String[] columns = new String[] { "signTime","userName", "award", "awardType", "targetAward", "allAward","status", "channel"};
				viewTemplates.setHeader(titles);
				viewTemplates.setColumns(columns);
			}else if ( pageStatus ==1){
				String[] titles = new String[] { "用户名", "奖金", "类型", "目标金额","累积金额", "领取时间","报名时间","达标时间", "渠道"};
				String[] columns = new String[] { "userName", "award", "awardType", "targetAward", "allAward","getTime","signTime","reachTime", "channel"};
				viewTemplates.setHeader(titles);
				viewTemplates.setColumns(columns);
			}else if ( pageStatus ==2){
				String[] titles = new String[] { "用户名", "奖金", "类型", "目标金额","累积金额", "领取时间","报名时间","达标时间","审核人","审核时间", "渠道"};
				String[] columns = new String[] { "userName", "award", "awardType", "targetAward", "allAward","getTime","signTime","reachTime","verifyName","verifyTime", "channel"};
				viewTemplates.setHeader(titles);
				viewTemplates.setColumns(columns);
			}else if ( pageStatus ==3){
				String[] titles = new String[] { "用户名", "奖金", "类型", "目标金额","累积金额", "领取时间","报名时间","达标时间","审核人","审核时间","审核原因", "渠道"};
				String[] columns = new String[] { "userName", "award", "awardType", "targetAward", "allAward","getTime","signTime","reachTime","verifyName","verifyTime", "verifyReason","channel"};
				viewTemplates.setHeader(titles);
				viewTemplates.setColumns(columns);
			}
			viewTemplates.setFileName("红包报表导出数据");
			viewTemplates.setDataList(reportUIs);

			Map<String, Object> model = new HashMap<String, Object>();
			model.put("dataModel", viewTemplates);

			return new ModelAndView(new ExcelView(), model);

		}

		return null;
	}
	
	@RequestMapping(value = "/queryRankingIsOpen")
	@ResponseBody
	public Boolean queryRankingIsOpen() throws Exception {
		Response<ActivityConfig> response = new Response<ActivityConfig>();
		ActivityConfigRequest query = new ActivityConfigRequest();
		
		query.setId(152l);
		logger.info("query queryRankingIsOpen start");
		Date nowDate = new Date();
		try {
			response = httpClient.invokeHttp(serverPath + getActivityConfig, query,
					ActivityConfigResponse.class);
			if(nowDate.before(response.getBody().getResult().getBeginTime()) ){
				logger.info("秒秒風雲榜活動日期未開始");
				return false;
			}
			if(nowDate.after(response.getBody().getResult().getEndTime()) ){
				logger.info("秒秒風雲榜活動日期已結束");
				return false;
			}
			
		} catch (Exception e) {
			logger.error("query queryRankingIsOpen error");
			throw e;
		}
		logger.info("query queryRankingIsOpen end");
		
		return false;
	
	}
	
	
	@RequestMapping(value = "/queryRanking")
	@ResponseBody
	public Response<MmcRankingResponse> queryRanking() throws Exception {
		Response<MmcRankingResponse> response = new Response<MmcRankingResponse>();
		MmcRankingRequest query = new MmcRankingRequest();
		
		query.setAccount(RequestContext.getCurrUser().getUserName());
		logger.info("query queryRanking start");
		
		try {
			if(!queryRankingIsOpen()){
				MmcRankingResponse noOpenResponse = new MmcRankingResponse();
				noOpenResponse.setStart(false);
				response.setResult(noOpenResponse);
				return response;
			}
			response = httpClient.invokeHttp(serverPath + getActivityConfig, query,
					MmcRankingResponse.class);
			response.getBody().getResult().setStart(true);
			
		} catch (Exception e) {
			logger.error("query queryRanking error");
			throw e;
		}
		logger.info("query queryRanking end");
		
		return response;
	
	}
	
	@RequestMapping(value = "/queryHistory")
	@ResponseBody
	public Response<MmcRankingHistoryResponse> queryHistory() throws Exception {
		Response<MmcRankingHistoryResponse> response = new Response<MmcRankingHistoryResponse>();
		MmcRankingRequest query = new MmcRankingRequest();
		
		query.setAccount(RequestContext.getCurrUser().getUserName());
		logger.info("query queryRankingHistory start");
		
		try {
			if(!queryRankingIsOpen()){
				return null;
			}
			response = httpClient.invokeHttp(serverPath + getActivityConfig, query,
					MmcRankingHistoryResponse.class);
		} catch (Exception e) {
			logger.error("query queryRankingHistory error");
			throw e;
		}
		logger.info("query queryRankingHistory end");
		
		return response;
	
	}
}
