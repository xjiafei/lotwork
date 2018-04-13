package com.winterframework.firefrog.game.web.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.winterframework.firefrog.common.util.DateUtils;
import com.winterframework.firefrog.game.dao.vo.GameTrendJbyl;
import com.winterframework.firefrog.game.service.IBaseOmissionService;
import com.winterframework.firefrog.game.service.gametrend.IGameTrendChartService;
import com.winterframework.firefrog.game.web.controller.view.CSVView;
import com.winterframework.firefrog.game.web.controller.view.ExcelView;
import com.winterframework.firefrog.game.web.controller.view.ExportViewDataModel;
import com.winterframework.firefrog.game.web.dto.GameTrendChartStruc;
import com.winterframework.firefrog.game.web.dto.GameTrendQueryRequest;
import com.winterframework.firefrog.game.web.dto.GameTrendReport;
import com.winterframework.firefrog.game.web.util.QueryType;
import com.winterframework.firefrog.game.web.util.WapChart;
import com.winterframework.modules.web.jsonresult.Response;
import com.winterframework.modules.web.util.RequestContext;

/**
 * @author Lex
 * @ClassName: ChartController
 * @Description: 走势图
 * @date 14-5-29 上午10:21
 */
@Controller("chartController")
@RequestMapping("/game/chart")
public class ChartController {
//	private Logger logger = Logger.getLogger(ChartController.class);
	private static final Logger logger = LoggerFactory.getLogger(ChartController.class);

	@Resource(name = "gameBet")
	private Map<String, Map> gameBet;

	@Resource(name = "baseOmissionServiceImpl")
	private IBaseOmissionService baseOmissionService;
	
	@Resource(name = "gameTrendChartServiceImpl")
	private IGameTrendChartService gameTrendChartService;

	
	@RequestMapping(value = "/{view}")
	public @ResponseBody
	Response<List<WapChart>> wapChart(@PathVariable String view) {
		Response<List<WapChart>> response = new Response<List<WapChart>>();
		Map mapBet = gameBet.get(view);
		List<WapChart> res = baseOmissionService.getWapChart(Long.valueOf(mapBet.get("lotteryId").toString()));
		response.setResult(res);
		return response;
	}
	
	
	@RequestMapping(value = "/{view}/{groupCode}")
	public String chart(@PathVariable String view, @PathVariable String groupCode, Model model) {
		Map mapBet = gameBet.get(view);
		model.addAttribute("lotteryName", mapBet.get("lotteryName"));
        model.addAttribute("view", view);
		return "/chart/" + ((Map) (gameBet.get(view)).get(groupCode)).get("view").toString();
	}

	@ResponseBody
	@RequestMapping(value = "/{view}/{groupCode}/data", method = RequestMethod.GET)
	public GameTrendChartStruc chartData(@PathVariable String view, @PathVariable String groupCode,
			@RequestParam String periodsType, @RequestParam String gameType, @RequestParam String gameMethod,
			@RequestParam(value = "periodsNum[endTime]", required = false) String endTime,
			@RequestParam(value = "periodsNum[startTime]", required = false) String startTime,
			@RequestParam(value = "periodsNum", required = false) Integer periodsNum, HttpServletRequest request,
			Model model) throws Exception {
		model.addAttribute("view", view);
		model.addAttribute("groupCode", groupCode);
		Map mapBet = gameBet.get(view);
		int type = 0;
		if ("periods".equals(periodsType)) {
			type = 1;
		} else if ("day".equals(periodsType) || "time".equals(periodsType)) {
			type = 2;
		}

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Long start = null;
		Long end = null;
		if (startTime != null && endTime != null) {
			start = sdf.parse(startTime).getTime();
			end = sdf.parse(endTime).getTime();
		}
		if (periodsType.equals("day")) {
			if (periodsNum.intValue() == 1) {
				start = DateUtils.getStartTimeOfCurrentDate().getTime();//取得當天 00:00:00的時間
			}
			if (periodsNum.intValue() == 2) {
				start = DateUtils.getStartTimeOfDate(DateUtils.getYesterday()).getTime();//取得昨天 00:00:00的時間
			}
			if (periodsNum.intValue() == 5) {
				start = DateUtils.getStartTimeOfDate(DateUtils.addDays(new Date(), -4)).getTime();//近 5 天資料
			}
			end = DateUtils.currentDate().getTime();
		}
		@SuppressWarnings("unchecked")
		Object isGeneral = ((Map<String, Object>) mapBet.get(groupCode)).get("isGeneral");
		Long lotteryId = Long.valueOf(mapBet.get("lotteryId").toString());
		
		QueryType queryType =  gameTrendChartService.checkQueryTypeByLotteryid(lotteryId);
		if(QueryType.IMMEDIATE_QUERY.equals(queryType)){
			Long startTimeMillis = System.currentTimeMillis();
			@SuppressWarnings("unchecked")
			String groupCodeStr = ((Map<String, Object>) mapBet.get(groupCode)).get("code").toString();
			//直接寫死100筆資料
			periodsNum = 100;
			List<GameTrendJbyl> gameTrendJbyls = gameTrendChartService.generateMMCTrendChartData(
													RequestContext.getCurrUser().getId(), 
													lotteryId, 
													isGeneral == null ? null : Integer.valueOf(isGeneral.toString()), 
													Long.valueOf(groupCodeStr),
													periodsNum, start, end);
			GameTrendChartStruc rtr = gameTrendChartService.queryOmissionValue(
										gameTrendJbyls, 
										lotteryId, 
										isGeneral == null ? null : Integer.valueOf(isGeneral.toString()), 
										Integer.valueOf(groupCodeStr));
			Long endTimeMillis = System.currentTimeMillis();
			logger.info("account : {}, 查詢  : {}, groupCode : {}, 耗時 : {} 毫秒", 
					RequestContext.getCurrUser().getUserName(),
					view,
					groupCode,
					(endTimeMillis-startTimeMillis));
			rtr.setLotteryCode(view);
			return rtr;
		} else {
			GameTrendChartStruc rtr= baseOmissionService.queryTrendCharts(Long.valueOf(mapBet.get("lotteryId").toString()), Integer
					.valueOf(((Map) mapBet.get(groupCode)).get("code").toString()), type, isGeneral == null ? null
					: Integer.valueOf(isGeneral.toString()), periodsNum, start, end);
			rtr.setLotteryCode(view);
			return rtr;
		}
	}

	@RequestMapping(value = "/{view}/{groupCode}/reportDownload/data")
	public ModelAndView exportChart(@PathVariable String view, @PathVariable String groupCode,
			@RequestParam String periodsType,
			@RequestParam(value = "periodsNum[endTime]", required = false) String endTime,
			@RequestParam(value = "periodsNum[startTime]", required = false) String startTime,
			@RequestParam(value = "periodsNum", required = false) Integer periodsNum, int radio_doc_type, HttpServletRequest request) throws Exception {

		GameTrendQueryRequest gameTrendQueryRequest = new GameTrendQueryRequest();
		
		Map mapBet = gameBet.get(view);
		int type = 1;
		if ("periods".equals(periodsType)) {
			type = 1;
		} else if ("day".equals(periodsType) || "time".equals(periodsType)) {
			type = 2;
		}
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Long start = null;
		Long end = null;
		if (StringUtils.isNotEmpty(startTime) && StringUtils.isNotEmpty(endTime)) {
			start = sdf.parse(startTime).getTime();
			end = sdf.parse(endTime).getTime();
		}
		if (periodsType.equals("day")) {
			if (periodsNum.intValue() == 1) {
				start = DateUtils.getStartTimeOfCurrentDate().getTime();
			}
			if (periodsNum.intValue() == 2) {
				start = DateUtils.getStartTimeOfDate(DateUtils.getYesterday()).getTime();
			}
			if (periodsNum.intValue() == 5) {
				start = DateUtils.getStartTimeOfDate(DateUtils.addDays(new Date(), -4)).getTime();
			}
			end = DateUtils.currentDate().getTime();
		} else if (periodsNum == null) {
			periodsNum = 30;
		}
		
		Integer isGeneral = ((Map) mapBet.get(groupCode)).get("isGeneral") == null ? null : Integer.valueOf(((Map) mapBet.get(groupCode)).get("isGeneral").toString());
		
		gameTrendQueryRequest.setType(type);
		gameTrendQueryRequest.setStartDate(start);
		gameTrendQueryRequest.setEndDate(end);
		gameTrendQueryRequest.setIsGeneral(isGeneral);
		gameTrendQueryRequest.setLotteryId(Long.valueOf(mapBet.get("lotteryId").toString()));
		gameTrendQueryRequest.setGameGroupCode(Integer
				.valueOf(((Map) mapBet.get(groupCode)).get("code").toString()));
		gameTrendQueryRequest.setIssue(periodsNum);
		
		List<GameTrendReport> gameTrendReportList = null;
		
		try {
			QueryType queryType =  gameTrendChartService.checkQueryTypeByLotteryid(gameTrendQueryRequest.getLotteryId());
			if(QueryType.IMMEDIATE_QUERY.equals(queryType)){
				Long startTimeMillis = System.currentTimeMillis();
				@SuppressWarnings("unchecked")
				String groupCodeStr = ((Map<String, Object>) mapBet.get(groupCode)).get("code").toString();
				//直接寫死100筆資料
				periodsNum = 100;
				List<GameTrendJbyl> gameTrendJbyls = gameTrendChartService.generateMMCTrendChartData(
														RequestContext.getCurrUser().getId(), 
														gameTrendQueryRequest.getLotteryId(), 
														isGeneral == null ? null : Integer.valueOf(isGeneral.toString()), 
														Long.valueOf(groupCodeStr),
														periodsNum, start, end);
				gameTrendReportList = new ArrayList<GameTrendReport>();
				Set<Long> issueCodes = new HashSet<Long>();
				if (null != gameTrendJbyls && gameTrendJbyls.size() > 0) {
					for (GameTrendJbyl gdr : gameTrendJbyls) {
						if(issueCodes.contains(gdr.getIssueCode())){
							//不重複做
						} else {
							GameTrendReport report = new GameTrendReport();
							report.setNumberRecord(gdr.getLotteryid() == 99401l ? gdr.getValue().replace("+", ",") : gdr.getNumberRecord());
							report.setWebIssueCode(gdr.getIssueCode().toString());
							gameTrendReportList.add(report);
							issueCodes.add(gdr.getIssueCode());
						}
					}
					if(gameTrendQueryRequest.getLotteryId().equals(99112L)){
						for(GameTrendReport report : gameTrendReportList){
							report.setWebIssueCode("/");
						}
					}
				}
				
				Long endTimeMillis = System.currentTimeMillis();
				logger.info("account : {}, 報表  : {}, groupCode : {}, 耗時 : {} 毫秒", 
						RequestContext.getCurrUser().getUserName(),
						view,
						groupCode,
						(endTimeMillis-startTimeMillis));
				
			} else {
				gameTrendReportList = baseOmissionService.queryTrendReport(gameTrendQueryRequest);
			}
		} catch (Exception e) {
			logger.error("导出开奖结果出错！");
			e.printStackTrace();
		}
		
		//执行导出功能
		ExportViewDataModel viewTemplates = new ExportViewDataModel();
		String[] titles = new String[] { "奖期", "开奖号码" };
		String[] columns = new String[] { "webIssueCode", "numberRecord"};

		viewTemplates.setFileName(DateUtils.format(new Date(), DateUtils.DATE_FORMAT_PATTERN));
		viewTemplates.setHeader(titles);
		viewTemplates.setColumns(columns);
		viewTemplates.setDataList(gameTrendReportList);

		Map<String, Object> model = new HashMap<String, Object>();
		model.put("dataModel", viewTemplates);
		if (radio_doc_type == 1) {
			return new ModelAndView(new ExcelView(), model);
		} else {
			return new ModelAndView(new CSVView(), model);
		}

	}

}
