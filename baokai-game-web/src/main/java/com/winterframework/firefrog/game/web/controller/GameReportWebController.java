package com.winterframework.firefrog.game.web.controller;

import java.util.ArrayList;
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
import com.winterframework.firefrog.game.web.controller.view.ExcelView;
import com.winterframework.firefrog.game.web.controller.view.ExportViewDataModel;
import com.winterframework.firefrog.game.web.dto.DTOConvertor4Web;
import com.winterframework.firefrog.game.web.dto.GameReportRequest;
import com.winterframework.firefrog.game.web.dto.GameReportResponse;
import com.winterframework.firefrog.game.web.dto.GameReportStruc;
import com.winterframework.firefrog.game.web.dto.GameReportStrucForUI;
import com.winterframework.firefrog.game.web.dto.WinsReportQueryRequest;
import com.winterframework.firefrog.game.web.util.PageUtils;
import com.winterframework.modules.page.PageRequest;
import com.winterframework.modules.spring.exetend.PropertyConfig;
import com.winterframework.modules.web.jsonresult.Response;
import com.winterframework.modules.web.jsonresult.ResultPager;
import com.winterframework.modules.web.util.RequestContext;

/**
 * 
* @ClassName: GameReportController 
* @Description:游戏明细报表
* @author Richard
* @date 2014-2-21 上午10:16:28 
*
 */
@RequestMapping(value = "/gameoa")
@Controller("gameReportWebController")
public class GameReportWebController {

	private Logger log = LoggerFactory.getLogger(GameReportWebController.class);

	@PropertyConfig(value = "url.connect.risk")
	private String serverPath; //Risk

	@Resource(name = "httpJsonClientImpl")
	private IHttpJsonClient httpClient;

	@PropertyConfig(value = "url.query.gameReport")
	private String queryGameReportUrl;

	@RequestMapping("/queryGameReport")
	@ResponseBody
	public Object queryGameReport(@ModelAttribute("page") PageRequest<GameReportStruc> page) throws Exception {

		log.info("gameReportWebController queryGameReport.");
		ModelAndView view = new ModelAndView("operations/winsReport/gameReportList");

		try {

			if (null == page) {
				page = new PageRequest<GameReportStruc>();
			}

			GameReportRequest request = new GameReportRequest();
			request.setUserId(0L);

			Response<GameReportResponse> response = httpClient.invokeHttp(serverPath + queryGameReportUrl, request,
					PageUtils.getPager(page), new TypeReference<Response<GameReportResponse>>() {
					});

			if (null != response && response.getBody() != null && null != response.getBody().getResult()) {

				view.addObject("reportList", response.getBody().getResult().getReportList());

				ResultPager rp = response.getBody().getPager();
				view.addObject("page", PageUtils.getPageForView(page, rp));
			}

		} catch (Exception e) {

			log.error("gameReportWebController queryGameReport error:", e);
		}
		return view;
	}

	@RequestMapping(value = "exportGameReport")
	public ModelAndView exportOrderOperations(@ModelAttribute("req") WinsReportQueryRequest request) throws Exception {
		Response<GameReportResponse> response = new Response<GameReportResponse>();

		log.info("export WinsReport start");
		try {
			Long userid = RequestContext.getCurrUser().getId();
			String username = RequestContext.getCurrUser().getUserName();
			response = httpClient.invokeHttp(serverPath + queryGameReportUrl, request, userid, username,
					new TypeReference<Response<GameReportResponse>>() {
					});
		} catch (Exception e) {
			log.error("export WinsReport error", e);
			throw e;
		}

		if (response.getBody().getResult().getReportList() != null) {

			List<GameReportStrucForUI> reports = new ArrayList<GameReportStrucForUI>();

			for (GameReportStruc struc : response.getBody().getResult().getReportList()) {
				GameReportStrucForUI report = DTOConvertor4Web.gameReportStruc2GameReportStrucForUI(struc);
				reports.add(report);
			}

			//执行导出功能
			ExportViewDataModel viewTemplates = new ExportViewDataModel();
			String[] titles = new String[] { "交易流水号", "用户名", "交易时间", "游戏类型", "追号编号", "方案编号", "摘要", "金额", "游戏", "状态" };
			String[] columns = new String[] { "tid", "userName", "tradeDate", "gameType", "planCode", "orderCode",
					"reson", "amonut", "lotteryName", "status" };

			viewTemplates.setFileName("游戏报表导出数据");
			viewTemplates.setHeader(titles);
			viewTemplates.setColumns(columns);
			viewTemplates.setDataList(reports);

			Map<String, Object> model = new HashMap<String, Object>();
			model.put("dataModel", viewTemplates);

			//return new ModelAndView(new CSVView(), model);  
			return new ModelAndView(new ExcelView(), model);

		}

		return null;
	}
}
