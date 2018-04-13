package com.winterframework.firefrog.game.web.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.codehaus.plexus.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.winterframework.firefrog.common.httpjsonclient.IHttpJsonClient;
import com.winterframework.firefrog.common.util.DataConverterUtil;
import com.winterframework.firefrog.common.util.DateUtils;
import com.winterframework.firefrog.game.web.controller.view.ExcelView;
import com.winterframework.firefrog.game.web.controller.view.ExportViewDataModel;
import com.winterframework.firefrog.game.web.dto.AjaxResponse;
import com.winterframework.firefrog.game.web.dto.AuditGameIssueRuleRequest;
import com.winterframework.firefrog.game.web.dto.CancelGameIssueRuleRequest;
import com.winterframework.firefrog.game.web.dto.CommonIssueRuleUpdateRequest;
import com.winterframework.firefrog.game.web.dto.CommonOrSpecialGameIssueRuleAddOrUpdateRequest;
import com.winterframework.firefrog.game.web.dto.DeleteGameIssueRequest;
import com.winterframework.firefrog.game.web.dto.DeleteGameIssueRuleRequest;
import com.winterframework.firefrog.game.web.dto.GameIssueListDownLoadRequest;
import com.winterframework.firefrog.game.web.dto.GameIssueListQueryRequest;
import com.winterframework.firefrog.game.web.dto.GameIssueListQueryResponse;
import com.winterframework.firefrog.game.web.dto.GameIssueManualGenerateRequest;
import com.winterframework.firefrog.game.web.dto.GameIssueManualGenerateResponse;
import com.winterframework.firefrog.game.web.dto.GameIssueQueryRequest;
import com.winterframework.firefrog.game.web.dto.GameIssueQueryResponse;
import com.winterframework.firefrog.game.web.dto.GameIssueRuleQueryDTO;
import com.winterframework.firefrog.game.web.dto.GameIssueRuleQueryRequest;
import com.winterframework.firefrog.game.web.dto.GameIssueRuleQueryResponse;
import com.winterframework.firefrog.game.web.dto.IssueStruc;
import com.winterframework.firefrog.game.web.dto.IssueStrucDTO;
import com.winterframework.firefrog.game.web.dto.PreviewIssueStruc;
import com.winterframework.firefrog.game.web.dto.SalesIssueStruc;
import com.winterframework.firefrog.game.web.dto.SalesIssueStrucDTO;
import com.winterframework.firefrog.game.web.dto.StopGameIssueRuleAddOrUpdateRequest;
import com.winterframework.firefrog.game.web.dto.SubUserReportRequest;
import com.winterframework.firefrog.game.web.dto.TraceGameIssueListQueryRequest;
import com.winterframework.firefrog.game.web.dto.TraceGameIssueListQueryResponse;
import com.winterframework.firefrog.game.web.util.ConvertUtil;
import com.winterframework.firefrog.game.web.util.GameAwardNameUtil4Web;
import com.winterframework.firefrog.game.web.util.PageUtils;
import com.winterframework.modules.page.PageRequest;
import com.winterframework.modules.spring.exetend.PropertyConfig;
import com.winterframework.modules.web.jsonresult.Response;
import com.winterframework.modules.web.jsonresult.ResultPager;

/**
 * 
* @ClassName: GameIssueWebController 
* @Description:奖期规则
* @author Richard
* @date 2013-9-23 下午3:52:52 
*
 */
@Controller("gameIssueRuleWebController")
@RequestMapping(value = "/gameoa")
@SuppressWarnings("unchecked")
public class GameIssueRuleWebController {

	private Logger log = LoggerFactory.getLogger(GameIssueRuleWebController.class);

	private SimpleDateFormat dateFormat;
	private SimpleDateFormat timeFormat;

	@Resource(name = "httpJsonClientImpl")
	private IHttpJsonClient httpClient;

	@PropertyConfig(value = "url.connect")
	private String serverPath;

	@PropertyConfig(value = "url.game.queryIssueRules")
	private String queryGameIssueRulesUrl;

	@PropertyConfig(value = "url.game.queryIssues")
	private String queryGameIssuesUrl;

	@PropertyConfig(value = "url.game.addOrUpdateGameIssueRule")
	private String addOrUdapteCommonOrSpecialGameIssueRuleUrl;

	@PropertyConfig(value = "url.game.addOrUdapteStopGameIssueRule")
	private String addOrUdapteStopGameIssueRuleUrl;

	@PropertyConfig(value = "url.game.cancelGameIssueRule")
	private String cancelGameIssueRuleUrl;

	@PropertyConfig(value = "url.game.auditGameIssueRule")
	private String auditGameIssueRuleUrl;

	@PropertyConfig(value = "url.game.deleteGameIssueRule")
	private String deleteGameIssueRuleUrl;

	@PropertyConfig(value = "url.game.downLoadGameIssues")
	private String downLoadGameIssuesUrl;

	@PropertyConfig(value = "url.game.manualGenerateIssues")
	private String manualGenerateIssuesUrl;

	@PropertyConfig(value = "url.game.getMaxGameIssuesByLotteryId")
	private String getMaxGameIssuesByLotteryIdUrl;

	@PropertyConfig(value = "url.game.deleteGameIssues")
	private String deleteGameIssuesUrl;

	@PropertyConfig(value = "url.game.queryMaxGameIssue")
	private String queryMaxGameIssue;

	@PropertyConfig(value = "url.game.queryTraceGameIssues")
	private String queryTraceGameIssues;

	@PropertyConfig(value = "url.game.updateCommonRuleScheduleStopTime")
	private String updateCommonRuleScheduleStopTimeUrl;

	@PropertyConfig(value = "url.game.getStartWebIssueCode")
	private String getStartWebIssueCodeUrl;
	
	@PropertyConfig(value = "url.game.updateOpenAwardTime")
	private String updateOpenAwardTime;
	
	@PropertyConfig(value = "url.game.getNextOpenAwardTime")
	private String getNextOpenAwardTime;
	
	@PropertyConfig(value = "url.game.doExtendOpenAwardTime")
	private String doExtendOpenAwardTime;
	
	
	

	private static ObjectMapper mapper = new ObjectMapper();

	private static Map<Long, Object> map = new HashMap<Long, Object>();

	static {
		mapper = new ObjectMapper();
		mapper.setSerializationInclusion(Include.NON_EMPTY);
		mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

		map.put(203001L, "设置的时间与现有特例奖期规则时间有重叠");
		map.put(203002L, "分段奖期时间有重叠项");
		map.put(203005L, "奖期修改审核新增时的生效时间应该在已经生成的奖期日期之后");
		map.put(203003L, "开始执行时间早于当前时间");
		map.put(203004L, "结束执行时间早于开始执行时间");
		map.put(203007L, "分段期间最后一期开奖时间要大于第一期开奖时间");
		map.put(203011L, "等待开奖时间不能超过开奖周期");
		map.put(203010L, " 查询未生成的奖期开始时间不得晚于当前起始时间");
		map.put(203009L, "过去奖期查询结束时间不得晚于当前起始时间");
		map.put(203006L, "不能存在两条有效待发布常规规则");
		map.put(203008L, "查询结束时间不得早于开始时间");
	}

	/**
	 * 
	* @Title: queryGameIssueRules 
	* @Description: 4.24	查询奖期规则
	* @param lotteryId
	* @param ruleId
	* @return
	* @throws Exception
	 */
	@RequestMapping("/gameIssueIndex")
	@ResponseBody
	public ModelAndView queryGameIssueRules(@RequestParam("lotteryId") Long lotteryId,
			@RequestParam("ruleId") Long ruleId) throws Exception {
		int isHaveCurrent4 = 0;
		int count = 0;
		ModelAndView view = new ModelAndView("operations/gameIssue/index");
		dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		timeFormat = new SimpleDateFormat("HH:mm:ss");
		try {

			GameIssueRuleQueryRequest params = new GameIssueRuleQueryRequest();
			params.setLotteryId(lotteryId);
			params.setRuleId(ruleId);

			Response<List<GameIssueRuleQueryResponse>> responses = httpClient.invokeHttp(serverPath
					+ queryGameIssueRulesUrl, params, new TypeReference<Response<List<GameIssueRuleQueryResponse>>>() {
			});
			Response<IssueStruc> response = httpClient.invokeHttp(serverPath + getMaxGameIssuesByLotteryIdUrl,
					lotteryId, new TypeReference<Response<IssueStruc>>() {
					});
			if (response.getBody().getResult() != null) {
				view.addObject("maxIssuesDay", response.getBody().getResult().getSaleDate());
				view.addObject("maxIssueCode", response.getBody().getResult().getWebIssueCode());
			}

			List<GameIssueRuleQueryDTO> ruleQueryDTO = new ArrayList<GameIssueRuleQueryDTO>();

			//进行中，并且当前有效
			GameIssueRuleQueryDTO currentIssueRule = null;
			for (GameIssueRuleQueryResponse gameIssueRule : responses.getBody().getResult()) {

				GameIssueRuleQueryDTO dto = new GameIssueRuleQueryDTO();

				dto.setIssueRulesName(gameIssueRule.getIssueRulesName());
				dto.setLotteryId(gameIssueRule.getLotteryId());
				if (null != gameIssueRule.getOpenAwardPeriod()) {
					dto.setOpenAwardPeriod(ConvertUtil.convertStr2Week(gameIssueRule.getOpenAwardPeriod()));
				}
				dto.setRuleStatus(convertRulesStatus(gameIssueRule.getRuleStatus(), gameIssueRule.getRuleStartTime(),
						gameIssueRule.getRuleEndTime(), gameIssueRule.getRuleType()));
				dto.setRuleType(gameIssueRule.getRuleType());
				dto.setRuleEndTime(gameIssueRule.getRuleEndTime() == 0 ? null : dateFormat.format(DataConverterUtil
						.convertLong2Date(gameIssueRule.getRuleEndTime())));
				dto.setRuleStartTime(dateFormat.format(DataConverterUtil.convertLong2Date(gameIssueRule
						.getRuleStartTime())));
				dto.setRuleId(gameIssueRule.getRuleId());
				dto.setStatus(gameIssueRule.getRuleStatus());
				dto.setStopStartTime(gameIssueRule.getStopStartTime() == null ? null : timeFormat
						.format(DataConverterUtil.convertLong2Date(gameIssueRule.getStopStartTime())));

				dto.setStopEndTime(gameIssueRule.getStopEndTime() == null ? null : timeFormat.format(DataConverterUtil
						.convertLong2Date(gameIssueRule.getStopEndTime())));

				List<SalesIssueStrucDTO> list = new ArrayList<SalesIssueStrucDTO>();

				for (SalesIssueStruc struc : gameIssueRule.getSalesIssueStrucs()) {

					SalesIssueStrucDTO issueStruc = new SalesIssueStrucDTO();
					issueStruc.setFirstAwardTime(timeFormat.format(DataConverterUtil.convertLong2Date(struc
							.getFirstAwardTime())));
					issueStruc.setLastAwardTime(timeFormat.format(DataConverterUtil.convertLong2Date(struc
							.getLastAwardTime())));
					issueStruc.setSalePeriodTime(struc.getSalePeriodTime());
					issueStruc.setSaleStartTime(timeFormat.format(DataConverterUtil.convertLong2Date(struc
							.getSaleStartTime())));
					issueStruc.setScheduleStopTime(struc.getScheduleStopTime());

					list.add(issueStruc);
				}
				dto.setSalesIssueStrucs(list);

				if (dto.getRuleEndTime() == null && gameIssueRule.getRuleStatus() != 4 && gameIssueRule.getRuleStartTime() <= Calendar.getInstance().getTimeInMillis()) {
					//进行中,要判断当前时间和
					currentIssueRule = dto;
				}
				if (dto.getRuleEndTime() == null && gameIssueRule.getRuleStatus() == 4) {
					isHaveCurrent4 = 1;
				}
				
				if(dto.getRuleEndTime() == null && gameIssueRule.getRuleStatus() != 4 && dto.getStatus() == 1){
					count++;
				}
				ruleQueryDTO.add(dto);
			}
			if(count > 1){
				isHaveCurrent4 = 1;
			}
			view.addObject("isHaveCurrent4", isHaveCurrent4);
			view.addObject("currentIssueRule", currentIssueRule);
			view.addObject("gameIssueRules", ruleQueryDTO);
			view.addObject("lotteryId", lotteryId);
			view.addObject("gameAwardName", GameAwardNameUtil4Web.lotteryName(lotteryId));

		} catch (Exception e) {

			log.error("queryGameIssueRules error:", e);
			throw e;
		}

		return view;
	}

	/**
	 * 
	* @Title: convertRulesStatus 
	* @Description: 转换前台需要的状态信息
	* @param status ,1 进行中，2，已删除， 3 已停止， 4 待审核
	* @param ruleStartTime
	* @param ruleEndTime
	* @return
	* @throws Exception
	 */
	private Integer convertRulesStatus(Integer status, Long ruleStartTime, Long ruleEndTime, Integer ruleType)
			throws Exception {
		//转换到前台的状态信息，1为未开始，2，未开始，待审核， 3，进行中， 4， 删除/无效, 5为已停止
		Long currentTime = System.currentTimeMillis();

		if (status == 1) {
			if (ruleType == 1) {
				return 3;
			} else if (ruleStartTime.compareTo(currentTime) > 0) {
				return 1; //已审核，未开始
			} else if (ruleStartTime.compareTo(currentTime) < 0 && ruleEndTime.compareTo(currentTime) > 0) {
				return 3; //进行中；
			} else {
				return 5;//已过期
			}
		} else if (status == 2) {

			return 4; //已删除/无效
		} else if (status == 3) {

			return 5; //已停止
		} else if (status == 4) {

			//			if(ruleEndTime != null && ruleEndTime.compareTo(currentTime) < 0){
			//				return 4; //无效
			//			}

			//常规奖期此处会产生一个问题，待审核的记录此处标记为已停止，无法审核，导致修改时有提示"不能存在两条有效待发布常规规则" 
			/*if (ruleStartTime.compareTo(currentTime) < 0) {
				return 5;
			}*/

			return 2; //为开始待审核
		} else if (status == 5) {
			if (ruleEndTime != null && ruleEndTime.compareTo(currentTime) < 0) {
				return 5;
			}
			return 0;//审核未通过
		}

		return 6;
	}

	/**
	 * 
	* @Title: queryGameIssues 
	* @Description: 4.23	查询预览生成奖期列表 
	* @param reqData
	* @return
	* @throws Exception
	 */
	@RequestMapping("/queryGameIssues")
	@ResponseBody
	public ModelAndView queryGameIssuesList(@ModelAttribute("reqData") GameIssueListQueryRequest reqData,
			@ModelAttribute("page") PageRequest<SubUserReportRequest> page, @RequestParam("lotteryId") Long lotteryId)
			throws Exception {

		ModelAndView view = new ModelAndView("operations/gameIssue/queryGameIssues");

		dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		try {

			reqData.setLotteryId(lotteryId);
			view.addObject("lotteryId", lotteryId);
			if (null == reqData.getQueryType()) {
				reqData.setQueryType(1);
			}

			view.addObject("queryType", reqData.getQueryType());

			//如果没有指定，先查3天
			if (null == reqData.getShowEndTime()) {
				Calendar cal1 = Calendar.getInstance();
				cal1.add(Calendar.DATE, -1);
				reqData.setShowEndTime(cal1.getTimeInMillis());
			}
			view.addObject("showEndTime", reqData.getShowEndTime());

			if (null == reqData.getShowStartTime()) {
				Calendar cal2 = Calendar.getInstance();
				cal2.add(Calendar.DATE, -3);
				reqData.setShowStartTime(cal2.getTimeInMillis());
			}
			view.addObject("showStartTime", reqData.getShowStartTime());

			//			Pager pager = new Pager();
			//			pager.setStartNo(page.getPageNo() == 0 ? 0 : (page.getPageNo() - 1) * page.getPageSize());
			//			pager.setEndNo(page.getPageSize() - 1 + pager.getStartNo());

			Response<GameIssueListQueryResponse> response = httpClient.invokeHttp(serverPath + queryGameIssuesUrl,
					reqData, PageUtils.getPager(page), new TypeReference<Response<GameIssueListQueryResponse>>() {
					});

			String message = "";
			if (response != null && response.getHead().getStatus() > 0) {
				try {
					log.info("返回状态码为：" + response.getHead().getStatus());
					message = map.get(response.getHead().getStatus()).toString();
				} catch (Exception e) {
					message = "系统异常，请联系管理员";
				}
			}
			view.addObject("message", message);
			
			Calendar cl = Calendar.getInstance();
			

			List<IssueStrucDTO> list = new ArrayList<IssueStrucDTO>();
			if (null != response && null != response.getBody() && null != response.getBody().getResult()
					&& null != response.getBody().getResult().getIssueStrucs()) {
				for (PreviewIssueStruc struc : response.getBody().getResult().getIssueStrucs()) {

					IssueStrucDTO dto = new IssueStrucDTO();
					dto.setSaleEndTime(dateFormat.format(DataConverterUtil.convertLong2Date(struc.getSaleEndTime())));
					dto.setSaleStartTime(dateFormat.format(DataConverterUtil.convertLong2Date(struc.getSaleStartTime())));
					dto.setWebIssueCode(struc.getWebIssueCode());
					dto.setOpenAwardTime(dateFormat.format(DataConverterUtil.convertLong2Date(struc.getOpenAwardTime())));
					dto.setDisplay(struc.getOpenAwardTime()>cl.getTimeInMillis()?true:false);
					dto.setIssueCode(struc.getIssueCode());
					dto.setId(struc.getId());
					dto.setPeriodStatus(struc.getPeriodStatus());
					list.add(dto);
				}
			}

			ResultPager rp = response.getBody() == null ? new ResultPager() : response.getBody().getPager();

			//			PageForView pv=new PageForView();
			//			if(null != response.getBody()){
			//				rp = response.getBody().getPager();
			//				pv.setTotalPages(rp.getTotal()/page.getPageSize()+1);
			//				pv.setPageNo(rp.getStartNo()/page.getPageSize()+1);
			//				pv.setPageSize(page.getPageSize());
			//				pv.setTotalCount(rp.getTotal());
			//			}

			view.addObject("reqData", reqData);
			view.addObject("issueList", list);
			view.addObject("page", PageUtils.getPageForView(page, rp));
			view.addObject("gameAwardName", GameAwardNameUtil4Web.lotteryName(lotteryId));
			/*view.addObject("showStartTime", DateUtils.format(
					DataConverterUtil.convertLong2Date(reqData.getShowStartTime()), DateUtils.DATETIME_FORMAT_PATTERN));
			view.addObject("showEndTime", DateUtils.format(
					DataConverterUtil.convertLong2Date(reqData.getShowEndTime()), DateUtils.DATETIME_FORMAT_PATTERN));*/

		} catch (Exception e) {
			log.error("queryGameIssues error:", e);
			throw e;
		}

		return view;
	}

	/**
	 * 
	* @Title: queryGameIssues 
	* @Description: 4.23	查询预览生成奖期列表 
	* @param reqData
	* @return
	* @throws Exception
	 */
	@RequestMapping("/downLoadGameIssues")
	@ResponseBody
	public ModelAndView downLoadGameIssues(@ModelAttribute("downData") GameIssueListDownLoadRequest downData,
			@RequestParam("lotteryId") Long lotteryId) throws Exception {
		dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		GameIssueListQueryRequest reqData = new GameIssueListQueryRequest();
		try {

			reqData.setLotteryId(lotteryId);

			reqData.setQueryType(null == downData.getQueryTypeDown() ? 1 : downData.getQueryTypeDown());

			reqData.setShowEndTime(downData.getShowEndTimeDown());
			reqData.setShowStartTime(downData.getShowStartTimeDown());
			Response<GameIssueListQueryResponse> response = httpClient.invokeHttp(serverPath + queryGameIssuesUrl,
					reqData, new TypeReference<Response<GameIssueListQueryResponse>>() {
					});

			List<IssueStrucDTO> list = new ArrayList<IssueStrucDTO>();
			if (null != response && null != response.getBody() && null != response.getBody().getResult()
					&& null != response.getBody().getResult().getIssueStrucs()) {
				for (PreviewIssueStruc struc : response.getBody().getResult().getIssueStrucs()) {

					IssueStrucDTO dto = new IssueStrucDTO();
					dto.setSaleEndTime(dateFormat.format(DataConverterUtil.convertLong2Date(struc.getSaleEndTime())));
					dto.setSaleStartTime(dateFormat.format(DataConverterUtil.convertLong2Date(struc.getSaleStartTime())));
					dto.setWebIssueCode(struc.getWebIssueCode());
					dto.setOpenAwardTime(dateFormat.format(DataConverterUtil.convertLong2Date(struc.getOpenAwardTime())));

					list.add(dto);
				}
			}
			//执行导出功能
			ExportViewDataModel viewTemplates = new ExportViewDataModel();
			String[] titles = new String[] { "期号", "开奖时间", "销售开始时间", "销售结束时间" };
			String[] columns = new String[] { "webIssueCode", "openAwardTime", "saleStartTime", "saleEndTime" };
			viewTemplates.setHeader(titles);
			viewTemplates.setColumns(columns);
			viewTemplates.setDataList(list);
			viewTemplates.setFileName("奖期记录导出数据");
			Map<String, Object> model = new HashMap<String, Object>();
			model.put("dataModel", viewTemplates);
			//return new ModelAndView(new CSVView(), model);  
			return new ModelAndView(new ExcelView(), model);

		} catch (Exception e) {
			log.error("queryGameIssues error:", e);
			throw e;
		}
	}

	@RequestMapping("/preAddOrUpdateGameIssueRule")
	@ResponseBody
	public ModelAndView preAddOrUpdateGameIssue(@RequestParam("lotteryId") Long lotteryId) throws Exception {

		ModelAndView view = new ModelAndView("operations/gameIssue/createGameIssue");

		try {

			view.addObject("lotteryId", lotteryId);
		} catch (Exception e) {

			log.error("preAddOrUpdateGameIssue error:", e);
			throw e;
		}

		return view;
	}

	/**
	 * 
	* @Title: preEditGameIssue 
	* @Description:修改常规奖期及特例奖期 
	* @return
	 */
	@RequestMapping("/preEditCommonGameIssue")
	@ResponseBody
	public ModelAndView preEditCommonGameIssue(@RequestParam("lotteryId") Long lotteryId,
			@RequestParam("ruleType") Integer ruleType, @RequestParam("ruleId") Long ruleId,
			@RequestParam("ruleStatus") Integer ruleStatus) throws Exception {

		ModelAndView view = new ModelAndView("operations/gameIssue/editGameIssue");

		try {

			GameIssueRuleQueryRequest request = new GameIssueRuleQueryRequest();
			request.setLotteryId(lotteryId);
			request.setRuleId(ruleId);
			request.setRuleStatus(ruleStatus);

			Response<List<GameIssueRuleQueryResponse>> response = httpClient.invokeHttp(serverPath
					+ queryGameIssueRulesUrl, request, new TypeReference<Response<List<GameIssueRuleQueryResponse>>>() {
			});

			GameIssueRuleQueryResponse gameIssue = new GameIssueRuleQueryResponse();
			if (null != response.getBody().getResult()) {
				gameIssue = response.getBody().getResult().get(0); //只有一个
			}

			view.addObject("gameIssue", gameIssue);
			view.addObject("ruleType", ruleType);
			view.addObject("lotteryId", lotteryId);
			view.addObject("ruleStatus", ruleStatus);
			view.addObject("ruleId", ruleId);
			if (null != gameIssue && gameIssue.getSalesIssueStrucs().size() > 0) {
				String str = mapper.writeValueAsString(gameIssue.getSalesIssueStrucs());
				view.addObject("salesIssueStrucs", str);
			}

		} catch (Exception e) {
			log.error("preEditCommonGameIssue error:", e);
			throw e;
		}

		return view;
	}

	/**
	 * 
	* @Title: checkUpapteCommonOrSpecialGameIssueRule 
	* @Description: 4.25	修改特例(常规)奖期生成规则,判断是否在追号期类
	* @param request
	* @return
	* @throws Exception
	 */
	@RequestMapping("/checkUpapteCommonOrSpecialGameIssueRuleTime")
	@ResponseBody
	public Object checkUpapteCommonOrSpecialGameIssueRuleTime(@ModelAttribute("reqData") String reqData)
			throws Exception {
		AjaxResponse resp = new AjaxResponse();
		CommonOrSpecialGameIssueRuleAddOrUpdateRequest request = mapper.readValue(reqData,
				new TypeReference<CommonOrSpecialGameIssueRuleAddOrUpdateRequest>() {
				});
		Response<Long> resMax = httpClient.invokeHttp(serverPath + queryMaxGameIssue, request.getLotteryId(),
				Long.class);
		int maxGameIssue = resMax.getBody().getResult().intValue();
		TraceGameIssueListQueryRequest traceGameIssueListQueryRequest = new TraceGameIssueListQueryRequest();
		traceGameIssueListQueryRequest.setLotteryId(request.getLotteryId());
		traceGameIssueListQueryRequest.setMaxCountIssue(maxGameIssue);
		Response<TraceGameIssueListQueryResponse> resTraceGameIssue = httpClient.invokeHttp(serverPath
				+ queryTraceGameIssues, traceGameIssueListQueryRequest, TraceGameIssueListQueryResponse.class);
		if (resTraceGameIssue != null && resTraceGameIssue.getBody().getResult().getIssueStrucs() != null) {
			List<PreviewIssueStruc> previewIssueStrucs = resTraceGameIssue.getBody().getResult().getIssueStrucs();
			if (previewIssueStrucs.size() > 0) {
				if (DataConverterUtil.convertLong2Date(request.getRuleStartTime()).before(
						DataConverterUtil.convertLong2Date(previewIssueStrucs.get(previewIssueStrucs.size() - 1)
								.getSaleEndTime()))) {
					resp.setStatus(0l);
					resp.setMaxIssuesDay(DateUtils.format(DataConverterUtil.convertLong2Date(previewIssueStrucs.get(
							previewIssueStrucs.size() - 1).getSaleEndTime()), DateUtils.DATETIME_FORMAT_PATTERN));
					return resp;
				}
			}
		}
		resp.setStatus(1l);
		return resp;
	}

	/**
	 * 
	* @Title: udapteCommonRuleScheduleStopTime 
	* @Description: 4.25	修改常规奖期的等待开奖时间
	* @param request
	* @return
	* @throws Exception
	 */
	@RequestMapping("/updateCommonRuleScheduleStopTime")
	@ResponseBody
	public Object updateCommonRuleScheduleStopTime(@ModelAttribute("reqData") String reqData) throws Exception {
		AjaxResponse resp = new AjaxResponse();
		try {
			CommonIssueRuleUpdateRequest request = mapper.readValue(reqData,
					new TypeReference<CommonIssueRuleUpdateRequest>() {
					});
			Response<Object> response = httpClient.invokeHttpWithoutResultType(serverPath
					+ updateCommonRuleScheduleStopTimeUrl, request);
			String message = "";
			if (response.getHead().getStatus() > 0) {
				try {
					message = map.get(response.getHead().getStatus()).toString();
				} catch (Exception e) {
					message = "系统异常，请联系管理员";
				}

				resp.setStatus(0l);
				resp.setMessage(message);
				return resp;
			}
			resp.setStatus(1l);
			resp.setMessage("success");
		} catch (Exception e) {

			log.error("udapteCommonRuleScheduleStopTime error:", e);
			resp.setStatus(0l);
			resp.setMessage("系统请求超时，请联系管理员");
		}
		return resp;
	}

	/**
	 * 
	* @Title: addOrUdapteCommonOrSpecialGameIssueRule 
	* @Description: 4.25	新增/修改特例(常规)奖期生成规则
	* @param request
	* @return
	* @throws Exception
	 */
	@RequestMapping("/addOrUdapteCommonOrSpecialGameIssueRule")
	@ResponseBody
	public Object addOrUdapteCommonOrSpecialGameIssueRule(@ModelAttribute("reqData") String reqData) throws Exception {

		AjaxResponse resp = new AjaxResponse();

		try {

			CommonOrSpecialGameIssueRuleAddOrUpdateRequest request = mapper.readValue(reqData,
					new TypeReference<CommonOrSpecialGameIssueRuleAddOrUpdateRequest>() {
					});

			Response<Object> response = httpClient.invokeHttpWithoutResultType(serverPath
					+ addOrUdapteCommonOrSpecialGameIssueRuleUrl, request);

			String message = "";
			if (response.getHead().getStatus() > 0) {
				try {
					message = map.get(response.getHead().getStatus()).toString();
				} catch (Exception e) {
					message = "系统异常，请联系管理员";
				}

				resp.setStatus(2l);
				resp.setMessage(message);
				return resp;
			}

			resp.setStatus(1l);
			resp.setMessage("success");

		} catch (JsonParseException je) {
			log.error("addOrUdapteCommonOrSpecialGameIssueRule error:", je);
			resp.setStatus(2l);
			resp.setMessage("请正确填写奖期规则信息。");
		} catch (Exception e) {

			log.error("addOrUdapteCommonOrSpecialGameIssueRule error:", e);
			resp.setStatus(2l);
			resp.setMessage("系统请求超时，请联系管理员");
		}
		return resp;
	}

	/**
	 * 
	* @Title: addOrUdapteStopGameIssueRule 
	* @Description: 4.26	新增/修改休市奖期规则 
	* @param request
	* @return
	* @throws Exception
	 */
	@RequestMapping("/addOrUdapteStopGameIssueRule")
	@ResponseBody
	public Object addOrUdapteStopGameIssueRule(@ModelAttribute("reqData") String reqData) throws Exception {

		AjaxResponse resp = new AjaxResponse();

		try {

			StopGameIssueRuleAddOrUpdateRequest request = mapper.readValue(reqData,
					new TypeReference<StopGameIssueRuleAddOrUpdateRequest>() {
					});

			Response<Object> response = httpClient.invokeHttpWithoutResultType(serverPath
					+ addOrUdapteStopGameIssueRuleUrl, request);

			String message = "";
			if (response.getHead().getStatus() > 0) {
				try {
					message = map.get(response.getHead().getStatus()).toString();
				} catch (Exception e) {
					message = "系统异常，请联系管理员";
				}

				resp.setStatus(2l);
				resp.setMessage(message);
				return resp;
			}

			resp.setStatus(1l);
			resp.setMessage("success");

		} catch (JsonParseException je) {
			log.error("addOrUdapteCommonOrSpecialGameIssueRule error:", je);
			resp.setStatus(2l);
			resp.setMessage("请正确填写奖期规则信息。");
		} catch (Exception e) {

			log.error("addOrUdapteStopGameIssueRule error:", e);
			resp.setStatus(2l);
			resp.setMessage("异常请求超时，请联系管理员");
		}

		return resp;
	}

	/**
	 * 
	* @Title: cancelGameIssueRule 
	* @Description: 4.27	停止（取消）特例奖期生成规则 
	* @param lotteryId
	* @param ruleId
	* @return
	* @throws Exception
	 */
	@RequestMapping("/cancelGameIssueRule")
	@ResponseBody
	public Object cancelGameIssueRule(@ModelAttribute("calGameIssue") String calGameIssue) throws Exception {

		AjaxResponse resp = new AjaxResponse();

		try {

			CancelGameIssueRuleRequest request = mapper.readValue(calGameIssue,
					new TypeReference<CancelGameIssueRuleRequest>() {
					});

			Response<Object> response = httpClient.invokeHttpWithoutResultType(serverPath + cancelGameIssueRuleUrl,
					request);

			String message = "";
			if (response.getHead().getStatus() > 0) {
				try {
					message = map.get(response.getHead().getStatus()).toString();
				} catch (Exception e) {
					message = "系统异常，请联系管理员";
				}

				resp.setStatus(2l);
				resp.setMessage(message);
				return resp;
			}

			resp.setStatus(1l);
			resp.setMessage("success");
		} catch (JsonParseException je) {
			log.error("addOrUdapteCommonOrSpecialGameIssueRule error:", je);
			resp.setStatus(2l);
			resp.setMessage("请正确填写奖期规则信息。");
		} catch (Exception e) {

			log.error("cancelGameIssueRule error", e);
			resp.setStatus(2l);
			resp.setMessage("异常请求超时，请联系管理员");
		}
		return resp;
	}

	/**
	 * 
	* @Title: auditGameIssueRule 
	* @Description: 4.28	审核生成奖期规则
	* @param lotteryId
	* @param ruleId
	* @param checkType
	* @return
	* @throws Exception
	 */
	@RequestMapping("/auditGameIssueRule")
	@ResponseBody
	public Object auditGameIssueRule(@RequestBody @ModelAttribute("auditGameIssue") String auditGameIssue)
			throws Exception {

		AjaxResponse resp = new AjaxResponse();
		try {

			AuditGameIssueRuleRequest request = mapper.readValue(auditGameIssue,
					new TypeReference<AuditGameIssueRuleRequest>() {
					});

			Response<Object> response = httpClient.invokeHttpWithoutResultType(serverPath + auditGameIssueRuleUrl,
					request);

			String message = "";
			if (response.getHead().getStatus() > 0) {
				try {
					message = map.get(response.getHead().getStatus()).toString();
				} catch (Exception e) {
					message = "系统异常，请联系管理员";
				}

				resp.setStatus(2l);
				resp.setMessage(message);
				return resp;
			}

			resp.setStatus(1l);
			resp.setMessage("success");

		} catch (JsonParseException je) {
			log.error("addOrUdapteCommonOrSpecialGameIssueRule error:", je);
			resp.setStatus(2l);
			resp.setMessage("请正确填写奖期规则信息。");
		} catch (Exception e) {

			log.error("auditGameIssueRule error:", e);

			resp.setStatus(2l);
			resp.setMessage("异常请求超时，请联系管理员");
		}

		return resp;
	}

	@RequestMapping("/deleteGameIssues")
	@ResponseBody
	public Object deleteGameIssues(@RequestBody @ModelAttribute("deleteGameIssuesStr") String deleteGameIssuesStr) {
		AjaxResponse resp = new AjaxResponse();
		try {
			DeleteGameIssueRequest request = mapper.readValue(deleteGameIssuesStr,
					new TypeReference<DeleteGameIssueRequest>() {
					});
			request.setType(1);
			if (DateUtils.parse(request.getStart(), DateUtils.DATE_FORMAT_PATTERN).after(
					DateUtils.parse(request.getEnd(), DateUtils.DATE_FORMAT_PATTERN))) {
				resp.setStatus(0l);
				resp.setMessage("开始时间不能大于结束时间");
				return resp;
			} else if (DateUtils.parse(request.getStart(), DateUtils.DATE_FORMAT_PATTERN).before(
					DateUtils.currentDate())) {
				resp.setStatus(0l);
				resp.setMessage("开始时间必须大于当天");
				return resp;
			}

			Response<Long> resMax = httpClient.invokeHttp(serverPath + queryMaxGameIssue, request.getLotteryId(),
					Long.class);
			int maxGameIssue = resMax.getBody().getResult().intValue();
			TraceGameIssueListQueryRequest traceGameIssueListQueryRequest = new TraceGameIssueListQueryRequest();
			traceGameIssueListQueryRequest.setLotteryId(request.getLotteryId());
			traceGameIssueListQueryRequest.setMaxCountIssue(maxGameIssue);
			Response<TraceGameIssueListQueryResponse> resTraceGameIssue = httpClient.invokeHttp(serverPath
					+ queryTraceGameIssues, traceGameIssueListQueryRequest, TraceGameIssueListQueryResponse.class);
			if (resTraceGameIssue != null && resTraceGameIssue.getBody().getResult().getIssueStrucs() != null) {
				List<PreviewIssueStruc> previewIssueStrucs = resTraceGameIssue.getBody().getResult().getIssueStrucs();

				if (DateUtils.parse(request.getStart(), DateUtils.DATE_FORMAT_PATTERN).before(
						DataConverterUtil.convertLong2Date(previewIssueStrucs.get(previewIssueStrucs.size() - 1)
								.getSaleEndTime()))) {
					resp.setStatus(0l);
					resp.setMessage("删除的奖期必须在该彩种配置的最大可追号奖期:"
							+ previewIssueStrucs.get(previewIssueStrucs.size() - 1).getWebIssueCode() + "之后");
					return resp;
				}
			}

			Response<GameIssueManualGenerateResponse> response = httpClient.invokeHttp(
					serverPath + deleteGameIssuesUrl, request,
					new TypeReference<Response<GameIssueManualGenerateResponse>>() {
					});
			GameIssueManualGenerateResponse queryResponse = response.getBody().getResult();
			if (queryResponse.getMessage() == null) {
				resp.setStatus(1l);
				String days = queryResponse.getGenerateDays() != 0 ? "共计" + queryResponse.getGenerateDays() + "天 " : "";
				resp.setMessage(queryResponse.getStartDate() + " 到  " + queryResponse.getEndDate() + "的奖期已经删除完毕 "
						+ days + queryResponse.getGenerateIssues() + "个奖期");
			} else {
				resp.setStatus(0l);
				resp.setMessage(queryResponse.getMessage());
			}
		} catch (Exception e) {
			resp.setStatus(0l);
			resp.setMessage("删除奖期错误");
		}
		return resp;
	}

	@RequestMapping("/getStartWebIssueCode")
	@ResponseBody
	public Object getStartWebIssueCode(@RequestBody @ModelAttribute("startWebIssueCodeStr") String startWebIssueCodeStr) {
		AjaxResponse resp = new AjaxResponse();
		try {
			GameIssueManualGenerateRequest request = mapper.readValue(startWebIssueCodeStr,
					new TypeReference<GameIssueManualGenerateRequest>() {
					});
			Response<String> responseMaxIssue = httpClient.invokeHttp(serverPath + getStartWebIssueCodeUrl, request,
					new TypeReference<Response<String>>() {
					});
			resp.setMaxIssueCode(responseMaxIssue.getBody().getResult());
			resp.setStatus(1l);
		} catch (Exception e) {
			resp.setStatus(0l);
			resp.setMessage("生成开始期号错误");
		}
		return resp;
	}

	/**
	 * 手動生成獎期
	 * @param manualGenerateStr
	 * @return
	 */
	@RequestMapping("/manualGenerateIssues")
	@ResponseBody
	public Object manualGenerateIssues(@RequestBody @ModelAttribute("manualGenerateStr") String manualGenerateStr) {

		AjaxResponse resp = new AjaxResponse();
		try {
			GameIssueManualGenerateRequest request = mapper.readValue(manualGenerateStr,
					new TypeReference<GameIssueManualGenerateRequest>() {
					});
			Response<IssueStruc> responseMaxIssue = httpClient.invokeHttp(serverPath + getMaxGameIssuesByLotteryIdUrl,
					request.getLotteryId(), new TypeReference<Response<IssueStruc>>() {
					});
			if (responseMaxIssue.getBody().getResult() != null) {
				resp.setMaxIssuesDay(responseMaxIssue.getBody().getResult().getSaleDate());
				resp.setMaxIssueCode(responseMaxIssue.getBody().getResult().getWebIssueCode());
			}

			Response<GameIssueManualGenerateResponse> response = httpClient.invokeHttp(serverPath
					+ manualGenerateIssuesUrl, request, new TypeReference<Response<GameIssueManualGenerateResponse>>() {
			});
			GameIssueManualGenerateResponse queryResponse = response.getBody().getResult();
			if (queryResponse.getMessage() == null) {
				resp.setStatus(1l);
				resp.setMessage(queryResponse.getStartDate() + " 到  " + queryResponse.getEndDate() + " 的奖期已经生成完毕，共计"
						+ queryResponse.getGenerateDays() + "天 " + queryResponse.getGenerateIssues() + "个奖期");
			} else {
				resp.setStatus(0l);
				resp.setMessage(queryResponse.getMessage());
			}

		} catch (Exception e) {
			log.error("手工生成奖期错误", e);
			resp.setStatus(0l);
			resp.setMessage("手动生成奖期失败");
		}
		return resp;
	}

	@RequestMapping("/preManualGameIssues")
	@ResponseBody
	public ModelAndView preManualGameIssues(@RequestParam("lotteryId") Long lotteryId) {
		ModelAndView view = new ModelAndView("operations/gameIssue/manualGameIssue");
		try {

			Response<IssueStruc> response = httpClient.invokeHttp(serverPath + getMaxGameIssuesByLotteryIdUrl,
					lotteryId, new TypeReference<Response<IssueStruc>>() {
					});
			if (response.getBody().getResult() != null) {
				view.addObject("maxIssuesDay", response.getBody().getResult().getSaleDate());
				view.addObject("maxIssueCode", response.getBody().getResult().getWebIssueCode());
			}
		} catch (Exception e) {
			log.error("query max gameIssues error", e);
		}
		view.addObject("lotteryId", lotteryId);
		view.addObject("lotteryName", GameAwardNameUtil4Web.lotteryName(lotteryId));
		view.addObject("tomoDay",
				DateUtils.format(DateUtils.addDays(DateUtils.currentDate(), 1), DateUtils.DATE_FORMAT_PATTERN));
		return view;
	}

	@RequestMapping("/preDeleteGameIssues")
	@ResponseBody
	public ModelAndView preDeleteGameIssues(@RequestParam("lotteryId") Long lotteryId) {
		ModelAndView view = new ModelAndView("operations/gameIssue/deleteGameIssue");
		view.addObject("lotteryId", lotteryId);
		view.addObject("lotteryName", GameAwardNameUtil4Web.lotteryName(lotteryId));
		view.addObject("tomoDay",
				DateUtils.format(DateUtils.addDays(DateUtils.currentDate(), 1), DateUtils.DATE_FORMAT_PATTERN));
		return view;
	}

	/**
	 * 
	* @Title: deleteGameIssueRule 
	* @Description: 4.29	删除奖期规则 
	* @param lotteryId
	* @param ruleId
	* @return
	* @throws Exception
	 */
	@RequestMapping("/delGameIssueRule")
	@ResponseBody
	public Object deleteGameIssueRule(@RequestBody @ModelAttribute("delGameIssue") String delGameIssue)
			throws Exception {

		AjaxResponse resp = new AjaxResponse();

		try {

			DeleteGameIssueRuleRequest request = mapper.readValue(delGameIssue,
					new TypeReference<DeleteGameIssueRuleRequest>() {
					});

			Response<Object> response = httpClient.invokeHttpWithoutResultType(serverPath + deleteGameIssueRuleUrl,
					request);

			String message = "";
			if (response.getHead().getStatus() > 0) {
				try {
					message = map.get(response.getHead().getStatus()).toString();
				} catch (Exception e) {
					message = "系统异常，请联系管理员";
				}

				resp.setStatus(2l);
				resp.setMessage(message);
				return resp;
			}

			resp.setStatus(1l);
			resp.setMessage("success");

		} catch (Exception e) {

			log.error("deleteGameIssueRule error:", e);
			resp.setStatus(2l);
			resp.setMessage("异常请求超时，请联系管理员");
		}

		return resp;
	}
	
	@RequestMapping("/updateOpenAwardTime")
	@ResponseBody
	public Object updateOpenAwardTime(@RequestParam("webIssueCode") String webIssueCode
			,@RequestParam("openAwardTime") String openAwardTime,@RequestParam("lotteryId") Long lotteryId
			,@RequestParam("issueCode") Long issueCode,@RequestParam("id") Long id){
		Response<GameIssueQueryResponse>  resp =null;
		try {
			dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			
			
			GameIssueQueryRequest request = new GameIssueQueryRequest();
			request.setWebIssueCode(webIssueCode);
			request.setOpenAwardTime(StringUtils.isNotBlank(openAwardTime)?
					dateFormat.parse(openAwardTime):null);
			request.setLotteryId(lotteryId);
			request.setIssueCode(issueCode);
			request.setId(id);
			resp = httpClient.invokeHttp(serverPath
					+ updateOpenAwardTime, request, GameIssueQueryResponse.class);
						
			resp.getBody().getResult();
			

		
		}catch (Exception e) {
			//resp.setMessage("异常请求超时，请联系管理员");
			log.error("updateOpenAwardTime error:", e);

		}
		return resp.getBody().getResult();
	
	}
	
	
	@RequestMapping("/getNextOpenAwardTime")
	@ResponseBody
	public Object getNextOpenAwardTime(@RequestParam("issueCode") Long issueCode
			,@RequestParam("lotteryId") Long lotteryId){
		Response<GameIssueQueryResponse>  resp =null;
		try {
			GameIssueQueryRequest request = new GameIssueQueryRequest();
			request.setIssueCode(issueCode);
			request.setLotteryId(lotteryId);
			resp = httpClient.invokeHttp(serverPath
					+ getNextOpenAwardTime, request, GameIssueQueryResponse.class);
						
			resp.getBody().getResult();
			

		
		}catch (Exception e) {
			log.error("getNextOpenAwardTime error:", e);

		}
		return resp.getBody().getResult();
	
	}
	
	@RequestMapping("/doExtendOpenAwardTime")
	@ResponseBody
	public Object doExtendOpenAwardTime(@RequestParam("id") Long id
			,@RequestParam("nextId") Long nextId,@RequestParam("lotteryId") Long lotteryId){
		Response<GameIssueQueryResponse>  resp =null;
		try {
			GameIssueQueryRequest request = new GameIssueQueryRequest();
			request.setId(id);
			request.setNextId(nextId);
			request.setLotteryId(lotteryId);
			resp = httpClient.invokeHttp(serverPath
					+ doExtendOpenAwardTime, request, GameIssueQueryResponse.class);
						
			resp.getBody().getResult();
			

		
		}catch (Exception e) {
			log.error("doExtendOpenAwardTime error:", e);

		}
		return resp.getBody().getResult();
	
	}
	
	

}
