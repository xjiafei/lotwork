package com.winterframework.firefrog.game.web.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
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
import org.springframework.web.servlet.ModelAndView;

import com.winterframework.firefrog.common.httpjsonclient.IHttpJsonClient;
import com.winterframework.firefrog.game.web.controller.view.CSVView;
import com.winterframework.firefrog.game.web.controller.view.ExcelView;
import com.winterframework.firefrog.game.web.controller.view.ExportViewDataModel;
import com.winterframework.firefrog.game.web.dto.DTOConvertor4Web;
import com.winterframework.firefrog.game.web.dto.GameSpiteOrderQueryRequest;
import com.winterframework.firefrog.game.web.dto.GameSpiteOrderQueryResponse;
import com.winterframework.firefrog.game.web.dto.GameWarnOrderQueryRequest;
import com.winterframework.firefrog.game.web.dto.GameWarnOrderQueryResponse;
import com.winterframework.firefrog.game.web.dto.PageForView;
import com.winterframework.firefrog.game.web.dto.RiskOrderDownloadStruc;
import com.winterframework.firefrog.game.web.dto.RiskOrderStruc;
import com.winterframework.firefrog.game.web.dto.SpiteOrderStruc;
import com.winterframework.firefrog.game.web.dto.SpiteStrucForUI;
import com.winterframework.firefrog.game.web.util.PageUtils;
import com.winterframework.modules.page.PageRequest;
import com.winterframework.modules.spring.exetend.PropertyConfig;
import com.winterframework.modules.web.jsonresult.Pager;
import com.winterframework.modules.web.jsonresult.Response;
import com.winterframework.modules.web.jsonresult.ResultPager;
import com.winterframework.modules.web.util.RequestContext;

/** 
* @ClassName: GameWarnOrderWebController 
* @Description: 风险记录Web Controller 
* @author Alan
* @date 2013-10-16 下午3:59:26 
*  
*/
@Controller("gameWarnOrderWebController")
@RequestMapping(value = "/gameRisk")
public class GameWarnOrderWebController {

	private Logger logger = LoggerFactory.getLogger(GameWarnOrderWebController.class);

	@Resource(name = "httpJsonClientImpl")
	private IHttpJsonClient httpClient;

	@PropertyConfig(value = "url.connect")
	private String serverPath;

	@PropertyConfig(value = "url.game.queryGameWarnOrder")
	private String queryGameWarnOrderUrl;

	@PropertyConfig(value = "url.game.queryGameSpiteOrder")
	private String queryGameSpiteOrderUrl;

	@PropertyConfig(value = "url.user.userInfo")
	private String userInfoUrl;

	/**
	* @Title: downLoadWarnOrderList 
	* @Description: 下载风险方案列表页面
	* @param page
	* @param request
	* @param model
	* @param pageCount
	* @throws Exception
	 */
	@RequestMapping(value = "/downLoadWarnOrderList")
	public ModelAndView downLoadWarnOrderList(
			@ModelAttribute("queryWarnOrderListRequest") GameWarnOrderQueryRequest request) throws Exception {
		Response<GameWarnOrderQueryResponse> response = new Response<GameWarnOrderQueryResponse>();

		Pager pager = new Pager();

		pager.setStartNo(1);
		pager.setEndNo(Integer.MAX_VALUE);
		if (null == request.getSelectTimeMode()) { //当天
			request.setSelectTimeMode(1L);
			Calendar currentDate = new GregorianCalendar();
			currentDate.set(Calendar.HOUR_OF_DAY, 0);
			currentDate.set(Calendar.MINUTE, 0);
			currentDate.set(Calendar.SECOND, 0);
			request.setStartCreateTime(currentDate.getTimeInMillis());
			Calendar currentDate1 = new GregorianCalendar();
			currentDate1.set(Calendar.HOUR_OF_DAY, 23);
			currentDate1.set(Calendar.MINUTE, 59);
			currentDate1.set(Calendar.SECOND, 59);
			request.setEndCreateTime(currentDate1.getTimeInMillis());
		}

		boolean statusFlag = false;

		if (request.getStatus() != null && request.getStatus().intValue() == -1) {
			request.setStatus(null);
			statusFlag = true;
		}

		logger.info("query warn order operations list start");
		try {
			Long userid = RequestContext.getCurrUser().getId();
			String username = RequestContext.getCurrUser().getUserName();
			response = httpClient.invokeHttp(serverPath + queryGameWarnOrderUrl, request, pager, userid, username,
					GameWarnOrderQueryResponse.class);
		} catch (Exception e) {
			logger.error("query warn order operations list error", e);
			throw e;
		}
		logger.info("query warn order operations list end");

		ResultPager rp = response.getBody().getPager();
		if (response.getBody().getResult() != null) {
			List<RiskOrderDownloadStruc> downloadStruc = new ArrayList<RiskOrderDownloadStruc>();
			List<RiskOrderStruc> RiskOrderStrucs = response.getBody().getResult().getRiskOrderStruc();
			for(RiskOrderStruc riskOrderStruc:RiskOrderStrucs){
				RiskOrderDownloadStruc struc = new RiskOrderDownloadStruc();
				struc.setAccount(riskOrderStruc.getAccount());
				struc.setLotteryName(riskOrderStruc.getLotteryName());
				struc.setWebIssueCode(riskOrderStruc.getWebIssueCode());
				struc.setCountWins(riskOrderStruc.getCountWins()/10000.00);
				struc.setIssueWinsRatio(riskOrderStruc.getIssueWinsRatio()/10000.00);
				struc.setOrderwarnContinuousWins(riskOrderStruc.getOrderwarnContinuousWins());
				struc.setContinuousWinsTimes(riskOrderStruc.getContinuousWinsTimes());
				downloadStruc.add(struc);
			}
			//model.addAttribute("config", response.getBody().getResult().getRiskConfigStruc());
			//执行导出功能
			ExportViewDataModel viewTemplates = new ExportViewDataModel();

			String[] titles = new String[] { "用户名", "彩种名称", "期号", "单期奖金", "单期中/投比", "连续中奖期数", "连续中奖次数" };
			String[] columns = new String[] { "account", "lotteryName", "webIssueCode", "countWins", "issueWinsRatio",
					"orderwarnContinuousWins", "continuousWinsTimes" };

			viewTemplates.setFileName("异常记录导出数据");
			viewTemplates.setHeader(titles);
			viewTemplates.setColumns(columns);
			viewTemplates.setDataList(downloadStruc);

			Map<String, Object> model = new HashMap<String, Object>();
			model.put("dataModel", viewTemplates);

			return new ModelAndView(new ExcelView(), model);
		} else {
			return new ModelAndView();
		}
	}

	/**
	* @Title: queryWarnOrderList 
	* @Description: 查询风险方案列表页面
	* @param page
	* @param request
	* @param model
	* @param pageCount
	* @throws Exception
	 */
	@RequestMapping(value = "/queryWarnOrderList")
	public String queryWarnOrderList(@ModelAttribute("page") PageRequest<GameSpiteOrderQueryRequest> page,
			@ModelAttribute("queryWarnOrderListRequest") GameWarnOrderQueryRequest request, Model model,
			@ModelAttribute("pageCount") String pageCount) throws Exception {
		Response<GameWarnOrderQueryResponse> response = new Response<GameWarnOrderQueryResponse>();

		Pager pager = new Pager();

		if ("".equals(pageCount)) {
			page.setPageSize(5);
		} else {
			page.setPageSize(Integer.parseInt(pageCount));
		}

		pager = PageUtils.getPager(page);

		//查询所有追号记录分页列表
		//		if(null == request.getSelectTimeMode()){
		//			if (null == request.getStartCreateTime()) {
		//				request.setStartCreateTime(System.currentTimeMillis());
		//			}
		//			if (null == request.getEndCreateTime()) {
		//				request.setEndCreateTime(System.currentTimeMillis());
		//			}
		//		}

		//		if(null == request.getSelectTimeMode()){
		//			request.setSelectTimeMode(1L); 
		//		}

		if (null == request.getSelectTimeMode()) { //当天
			request.setSelectTimeMode(1L);
			Calendar currentDate = new GregorianCalendar();
			currentDate.set(Calendar.HOUR_OF_DAY, 0);
			currentDate.set(Calendar.MINUTE, 0);
			currentDate.set(Calendar.SECOND, 0);
			request.setStartCreateTime(currentDate.getTimeInMillis());
			Calendar currentDate1 = new GregorianCalendar();
			currentDate1.set(Calendar.HOUR_OF_DAY, 23);
			currentDate1.set(Calendar.MINUTE, 59);
			currentDate1.set(Calendar.SECOND, 59);
			request.setEndCreateTime(currentDate1.getTimeInMillis());
		}

		boolean statusFlag = false;

		if (request.getStatus() != null && request.getStatus().intValue() == -1) {
			request.setStatus(null);
			statusFlag = true;
		}

		logger.info("query warn order operations list start");
		try {
			Long userid = RequestContext.getCurrUser().getId();
			String username = RequestContext.getCurrUser().getUserName();
			response = httpClient.invokeHttp(serverPath + queryGameWarnOrderUrl, request, pager, userid, username,
					GameWarnOrderQueryResponse.class);
		} catch (Exception e) {
			logger.error("query warn order operations list error", e);
			throw e;
		}
		logger.info("query warn order operations list end");

		ResultPager rp = response.getBody().getPager();

		List<RiskOrderStruc> RiskOrderStrucs = null;
		if (response.getBody().getResult() != null) {
			RiskOrderStrucs = response.getBody().getResult().getRiskOrderStruc();
			model.addAttribute("config", response.getBody().getResult().getRiskConfigStruc());
		}
		if (statusFlag) {
			request.setStatus(-1);
		}

		model.addAttribute("risks", RiskOrderStrucs);
		model.addAttribute("req", request);
		if (rp != null) {
			model.addAttribute("page", PageUtils.getPageForView(page, rp));
		} else {
			PageForView pageView = new PageForView();
			pageView.setPageNo(page.getPageNo());
			pageView.setPageSize(page.getPageSize());
			pageView.setTotalCount(0);
			pageView.setTotalPages(0);
			model.addAttribute("page", pageView);
		}

		model.addAttribute("userInfoUrl", userInfoUrl);

		return "/risk/warnList";
	}

	/**
	* @Title: querySpiteOrderList 
	* @Description: 查询恶意记录列表页面
	* @param page
	* @param request
	* @param model
	* @param pageCount
	* @throws Exception
	 */
	@RequestMapping(value = "/querySpiteOrderList")
	public String querySpiteOrderList(@ModelAttribute("page") PageRequest<GameSpiteOrderQueryRequest> page,
			@ModelAttribute("req") GameSpiteOrderQueryRequest request, Model model,
			@ModelAttribute("pageCount") String pageCount) throws Exception {
		Response<GameSpiteOrderQueryResponse> response = new Response<GameSpiteOrderQueryResponse>();

		Pager pager = new Pager();

		if ("".equals(pageCount)) {
			page.setPageSize(5);
		} else {
			page.setPageSize(Integer.parseInt(pageCount));
		}

		pager = PageUtils.getPager(page);

		//查询所有追号记录分页列表
		if (null == request.getSelectTimeMode()) {
			if (null == request.getStartCreateTime()) {
				Calendar currentDate = new GregorianCalendar();
				currentDate.set(Calendar.HOUR_OF_DAY, 0);
				currentDate.set(Calendar.MINUTE, 0);
				currentDate.set(Calendar.SECOND, 0);
				request.setStartCreateTime(currentDate.getTimeInMillis());
			}
			if (null == request.getEndCreateTime()) {
				Calendar currentDate1 = new GregorianCalendar();
				currentDate1.set(Calendar.HOUR_OF_DAY, 23);
				currentDate1.set(Calendar.MINUTE, 59);
				currentDate1.set(Calendar.SECOND, 59);
				request.setEndCreateTime(currentDate1.getTimeInMillis());
			}
		}

		logger.info("query game spite orders start");
		try {
			Long userid = RequestContext.getCurrUser().getId();
			String username = RequestContext.getCurrUser().getUserName();
			response = httpClient.invokeHttp(serverPath + queryGameSpiteOrderUrl, request, pager, userid, username,
					GameSpiteOrderQueryResponse.class);
		} catch (Exception e) {
			logger.error("query game spite orders error");
			throw e;
		}
		logger.info("query game spite orders end");

		ResultPager rp = response.getBody().getPager();

		List<SpiteStrucForUI> spiteUIs = new ArrayList<SpiteStrucForUI>();

		List<SpiteOrderStruc> spiteOrderStrucs = response.getBody().getResult().getSpiteOrderStrucs();
		for (SpiteOrderStruc ps : spiteOrderStrucs) {
			SpiteStrucForUI ui = DTOConvertor4Web.SpiteOrderStruc2SpiteStrucForUI(ps);
			spiteUIs.add(ui);
		}

		model.addAttribute("spites", spiteUIs);
		model.addAttribute("req", request);
		model.addAttribute("page", PageUtils.getPageForView(page, rp));

		return "/risk/spiteList";
	}

}
