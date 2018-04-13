package com.winterframework.firefrog.game.web.controller;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.core.type.TypeReference;
import com.winterframework.firefrog.common.httpjsonclient.IHttpJsonClient;
import com.winterframework.firefrog.game.dao.vo.GameReportIssue;
import com.winterframework.firefrog.game.web.controller.view.ExcelView;
import com.winterframework.firefrog.game.web.controller.view.ExportViewDataModel;
import com.winterframework.firefrog.game.web.dto.GameReportRequest;
import com.winterframework.firefrog.game.web.dto.GameReportStruc;
import com.winterframework.firefrog.game.web.dto.GameRiskReportRequest;
import com.winterframework.firefrog.game.web.dto.GameRiskReportResponse;
import com.winterframework.firefrog.game.web.dto.GameRiskTransactionReportResponse;
import com.winterframework.firefrog.game.web.dto.GameRiskTransactionReportStruc;
import com.winterframework.firefrog.game.web.util.PageUtils;
import com.winterframework.modules.page.PageRequest;
import com.winterframework.modules.spring.exetend.PropertyConfig;
import com.winterframework.modules.web.jsonresult.Response;
import com.winterframework.modules.web.jsonresult.ResultPager;

/**
 * 
* @ClassName: gameRiskReportController 
* @Description:游戏交易报表
* @author hugh
* @date 2014-4-24 上午10:16:28 
*
 */
@RequestMapping(value = "/gameRisk")
@Controller("gameRiskReportController")
public class GameRiskReportController {

	private Logger log = LoggerFactory.getLogger(GameRiskReportController.class);

	@PropertyConfig(value = "url.connect.risk")
	private String serverPath; //Risk

	@Resource(name = "httpJsonClientImpl")
	private IHttpJsonClient httpClient;

	@PropertyConfig(value = "url.game.risk.queryGameTransactionReport")
	private String queryGameTransactionReportUrl;

	@PropertyConfig(value = "url.game.risk.queryGameReportIssue")
	private String queryGameReportIssueUrl;

	@RequestMapping("/gotoGameRiskTransactionReport")
	public Object gotoGameRiskTransactionReport() {
		ModelAndView view = new ModelAndView("risk/report/transactionReportList");
		return view;
	}

	@RequestMapping("/queryGameRiskTransactionReport")
	@ResponseBody
	public Object queryGameRiskTransactionReport(@ModelAttribute("req") GameReportRequest request,
			@ModelAttribute("page") int pageNo) throws Exception {

		log.info("gameReportWebController queryGameReport.");
		Response<GameRiskTransactionReportResponse> response = null;
		try {

			PageRequest<GameReportRequest> page = new PageRequest<GameReportRequest>();
			page.setPageNo(pageNo + 1);
			page.setPageSize(10);
			if (request.getUserName() != null) {
				String[] names = request.getUserName().split(",");
				if (names.length == 2) {
					request.setContainSub(Integer.valueOf(names[1]));
					request.setUserName((names[0] == null || names[0] == "" || names[0].equals("null")) ? null
							: names[0]);
				}
			}
			page.setSearchDo(request);
			response = httpClient.invokeHttp(serverPath + queryGameTransactionReportUrl, request,
					PageUtils.getPager(page), new TypeReference<Response<GameRiskTransactionReportResponse>>() {
					});

			if (null != response && response.getBody() != null && null != response.getBody().getResult()) {

				//response.getBody().getResult().getReportList());		
				//resp.setStatus(1L);
			}

		} catch (Exception e) {

			log.error("gameReportWebController queryGameReport error:", e);
		}
		return response;
	}

	@RequestMapping(value = "exportGameRiskTransactionReport")
	public ModelAndView exportGameRiskTransactionReport(@ModelAttribute("req") GameReportRequest request,
			@ModelAttribute("page") PageRequest<GameReportStruc> page) throws Exception {
		Response<GameRiskTransactionReportResponse> response = new Response<GameRiskTransactionReportResponse>();

		log.info("export WinsReport start");
		try {
			request.setStartTime(new Date());
			Calendar calendar = Calendar.getInstance();
			calendar.add(Calendar.DATE, -30);
			request.setEndTime(calendar.getTime());
			page.setPageSize(100000);
			response = httpClient.invokeHttp(serverPath + queryGameTransactionReportUrl, request,
					PageUtils.getPager(page), new TypeReference<Response<GameRiskTransactionReportResponse>>() {
					});
		} catch (Exception e) {
			log.error("export WinsReport error", e);
			throw e;
		}

		if (response.getBody().getResult() != null && response.getBody().getResult().getReportList() != null) {

			List<GameRiskTransactionReportStruc> reports = response.getBody().getResult().getReportList();
			for (GameRiskTransactionReportStruc gameRiskTransactionReportStruc : reports) {

				BigDecimal amout = new BigDecimal(gameRiskTransactionReportStruc.getAmonut());
				BigDecimal c = amout.divide(new BigDecimal(10000), 2, BigDecimal.ROUND_HALF_EVEN);
				gameRiskTransactionReportStruc.setAmountD(c.toString());
			}
			//执行导出功能
			ExportViewDataModel viewTemplates = new ExportViewDataModel();
			String[] titles = new String[] { "交易流水号", "用户名", "交易时间", "游戏类型", "追号编号", "方案编号", "摘要", "金额", "游戏", "状态" };
			String[] columns = new String[] { "tid", "userName", "tradeDate", "gameType", "planCode", "orderCode",
					"reson", "amountD", "lotteryName", "status" };

			viewTemplates.setFileName("游戏报表导出数据");
			viewTemplates.setHeader(titles);
			viewTemplates.setColumns(columns);
			viewTemplates.setDataList(reports);

			Map<String, Object> model = new HashMap<String, Object>();
			model.put("dataModel", viewTemplates);

			return new ModelAndView(new ExcelView(), model);

		}

		return null;
	}

	@RequestMapping("/queryGameRiskIncomeReport")
	@ResponseBody
	public Object queryGameRiskIncomeReport(@ModelAttribute("request") GameRiskReportRequest request,
			@ModelAttribute("page") PageRequest<GameReportIssue> page) throws Exception {

		log.info("gameReportWebController queryGameReport.");
		ModelAndView view = new ModelAndView("risk/report/incomeReportList");

		try {

			if (null == page) {
				page = new PageRequest<GameReportIssue>();
			}
			if (request.getDateType() != null) {
				if (request.getDateType() == 1) { //当天				
					Calendar currentDate = new GregorianCalendar();
					currentDate.set(Calendar.HOUR_OF_DAY, 0);
					currentDate.set(Calendar.MINUTE, 0);
					currentDate.set(Calendar.SECOND, 0);
					request.setShowStartTime(currentDate.getTimeInMillis());
					Calendar currentDate1 = new GregorianCalendar();
					currentDate1.set(Calendar.HOUR_OF_DAY, 23);
					currentDate1.set(Calendar.MINUTE, 59);
					currentDate1.set(Calendar.SECOND, 59);
					request.setShowEndTime(currentDate1.getTimeInMillis());
				} else if (request.getDateType() == 3) {
					Calendar calendar = Calendar.getInstance();
					calendar.add(Calendar.DATE, -3);
					request.setShowStartTime(calendar.getTimeInMillis());
					request.setShowEndTime(System.currentTimeMillis());
				} else if (request.getDateType() == 7) {
					Calendar calendar = Calendar.getInstance();
					calendar.add(Calendar.DATE, -6);
					request.setShowStartTime(calendar.getTimeInMillis());
					request.setShowEndTime(System.currentTimeMillis());
				}
			}

			Response<GameRiskReportResponse> response = httpClient.invokeHttp(serverPath + queryGameReportIssueUrl,
					request, PageUtils.getPager(page), new TypeReference<Response<GameRiskReportResponse>>() {
					});

			if (null != response && response.getBody() != null && null != response.getBody().getResult()) {

				view.addObject("reports", response.getBody().getResult().getReports());
				view.addObject("request", request);
				ResultPager rp = response.getBody().getPager();
				view.addObject("page", PageUtils.getPageForView(page, rp));
			}

		} catch (Exception e) {

			log.error("gameReportWebController queryGameReport error:", e);
		}
		return view;
	}

}
