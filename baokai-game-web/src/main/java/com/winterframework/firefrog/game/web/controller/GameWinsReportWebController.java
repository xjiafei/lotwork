package com.winterframework.firefrog.game.web.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.winterframework.firefrog.common.httpjsonclient.IHttpJsonClient;
import com.winterframework.firefrog.common.util.DataConverterUtil;
import com.winterframework.firefrog.common.util.DateUtils;
import com.winterframework.firefrog.game.web.controller.view.ExcelView;
import com.winterframework.firefrog.game.web.controller.view.ExportViewDataModel;
import com.winterframework.firefrog.game.web.dto.DTOConvert;
import com.winterframework.firefrog.game.web.dto.DTOConvertor4Web;
import com.winterframework.firefrog.game.web.dto.OperationDetailReportStruc;
import com.winterframework.firefrog.game.web.dto.OperationReportStruc;
import com.winterframework.firefrog.game.web.dto.OperationReportStrucForUI;
import com.winterframework.firefrog.game.web.dto.PageForView;
import com.winterframework.firefrog.game.web.dto.SlipsStruc;
import com.winterframework.firefrog.game.web.dto.SlipsStrucDTO;
import com.winterframework.firefrog.game.web.dto.WinsDetailReportQueryRequest;
import com.winterframework.firefrog.game.web.dto.WinsDetailReportQueryResponse;
import com.winterframework.firefrog.game.web.dto.WinsReportQueryRequest;
import com.winterframework.firefrog.game.web.dto.WinsReportQueryResponse;
import com.winterframework.firefrog.game.web.dto.WinsSumReportQueryWebResponse;
import com.winterframework.firefrog.game.web.util.GameAwardNameUtil;
import com.winterframework.firefrog.game.web.util.PageUtils;
import com.winterframework.modules.page.PageRequest;
import com.winterframework.modules.spring.exetend.PropertyConfig;
import com.winterframework.modules.web.jsonresult.Pager;
import com.winterframework.modules.web.jsonresult.Response;
import com.winterframework.modules.web.jsonresult.ResultPager;
import com.winterframework.modules.web.util.RequestContext;

/** 
* @ClassName: GameWinsReportWebController 
* @Description: 运营盈亏报表查询Controller 
* @author Denny 
* @date 2013-10-16 下午4:26:07 
*  
*/
@RequestMapping(value = "/gameoa")
@Controller("gameWinsReportWebController")
public class GameWinsReportWebController {

	private Logger logger = LoggerFactory.getLogger(GameBetQueryController.class);

	@Resource(name = "httpJsonClientImpl")
	private IHttpJsonClient httpClient;

	@PropertyConfig(value = "url.connect")
	private String serverPath;

	@PropertyConfig(value = "url.game.queryWinsReport")
	private String queryWinsReportUrl;

	@PropertyConfig(value = "url.game.queryWinsSumReport")
	private String queryWinsSumReportUrl;

	@PropertyConfig(value = "url.game.queryWinsDetailReport")
	private String queryWinsDetailReportUrl;
	
	@PropertyConfig(value = "url.game.queryWinsDetailSumReport")
	private String queryWinsDetailSumReportUrl;

	@PropertyConfig(value = "url.game.queryWinsReportForExport")
	private String queryWinsReportForExport;

	/**
	 * 
	* @Title: queryWinsReport
	* @Description: 查询单期盈亏报表
	* @param request
	* @return
	* @throws Exception
	 */
	@RequestMapping(value = "/queryWinsReport")
	public String queryWinsReport(@ModelAttribute("page") PageRequest<WinsReportQueryRequest> page,
			@ModelAttribute("req") WinsReportQueryRequest request, Model model,
			@ModelAttribute("pageCount") String pageCount) throws Exception {
		logger.info("queryWinsReport start");
		Response<WinsReportQueryResponse> response = new Response<WinsReportQueryResponse>();
		List<OperationReportStrucForUI> reports = new ArrayList<OperationReportStrucForUI>();

		Response<WinsSumReportQueryWebResponse> response2 = new Response<WinsSumReportQueryWebResponse>();
		if(request.getLotteryid()==null && request.getStartCreateTime()==null && request.getEndCreateTime()==null){//首次进入页面直接返回空页面
			PageForView pv=new PageForView();
			pv.setTotalPages(25);
			pv.setPageNo(1);
			pv.setPageSize(25);
			pv.setTotalCount(0);
			model.addAttribute("reports", reports);
			model.addAttribute("winsSum", null);
			model.addAttribute("page", pv);
			return "/operations/winsReport/winsReportList";
		}
		if ("".equals(pageCount)) {
			page.setPageSize(25);
		} else {
			page.setPageSize(Integer.parseInt(pageCount));
		}
		Pager pager = PageUtils.getPager(page);

		if(null == request.getSelectTimeMode()){
			if (null == request.getStartCreateTime()) { 
				request.setStartCreateTime(DateUtils.getStartTimeOfDate(DateUtils.currentDate()).getTime());
			}
			if (null == request.getEndCreateTime()) { 
				request.setEndCreateTime(DateUtils.getEndTimeOfCurrentDate().getTime());
			}
		} 
		
		if (null == request.getSortType()) {
			request.setSortType(0);
		}
		try {
			response = httpClient.invokeHttp(serverPath + queryWinsReportUrl, request, pager,
					WinsReportQueryResponse.class);
			response2 = httpClient.invokeHttp(serverPath + queryWinsSumReportUrl, request,
					WinsSumReportQueryWebResponse.class);

			logger.info("queryWinsReport end");
		} catch (Exception e) {
			logger.error("queryWinsReport is error.", e);
			throw e;
		}

		ResultPager rp = response.getBody().getPager() == null ? new ResultPager() : response.getBody().getPager();
		PageForView pv = PageUtils.getPageForView(page, rp);

		
		if (response.getBody().getResult() != null) {
			for (OperationReportStruc struc : response.getBody().getResult().getOperationReportStruc()) {
				OperationReportStrucForUI report = DTOConvertor4Web.operationReportStruc2OperationReportStrucForUI(struc);
				reports.add(report);
			}
		}

		WinsSumReportQueryWebResponse winsSum = response2.getBody().getResult();

		if (winsSum != null) {
			winsSum.setStartTime(DateUtils.format(DataConverterUtil.convertLong2Date(request.getStartCreateTime()),
					DateUtils.DATETIME_FORMAT_PATTERN));
			winsSum.setEndTime(DateUtils.format(DataConverterUtil.convertLong2Date(request.getEndCreateTime()),
					DateUtils.DATETIME_FORMAT_PATTERN));
		}

		model.addAttribute("winsSum", winsSum);
		model.addAttribute("reports", reports);
		model.addAttribute("req", request);
		model.addAttribute("page", pv);

		return "/operations/winsReport/winsReportList";
	}

	/**
	 * 
	* @Title: queryWinsDetailReport
	* @Description: 查询单期盈亏明细报表
	* @param request
	* @return
	* @throws Exception
	 */
	@RequestMapping(value = "/queryWinsDetailReport")
	public String queryWinsDetailReport(@RequestParam("lotteryid") Long lotteryid,
			@RequestParam("issueCode") Long issueCode, @ModelAttribute("req") WinsDetailReportQueryRequest request,
			Model model, @ModelAttribute("sortType") String sortType) throws Exception {
		logger.info("queryWinsDetailReport start");
		Response<WinsDetailReportQueryResponse> response = new Response<WinsDetailReportQueryResponse>();
		Response<WinsSumReportQueryWebResponse> response2 = new Response<WinsSumReportQueryWebResponse>();

		request.setLotteryid(lotteryid);
		request.setIssueCode(issueCode);
		if ("".equals(sortType)) {
			request.setSortType(0);
		} else {
			request.setSortType(Integer.parseInt(sortType));
		}

		try {
			response = httpClient.invokeHttp(serverPath + queryWinsDetailReportUrl, request,
					WinsDetailReportQueryResponse.class);
			response2 = httpClient.invokeHttp(serverPath + queryWinsDetailSumReportUrl, request,
					WinsSumReportQueryWebResponse.class);
			logger.info("queryWinsDetailReport end");
		} catch (Exception e) {
			logger.error("queryWinsDetailReport is error.", e);
			throw e;
		}

		WinsDetailReportQueryResponse result = response.getBody().getResult();
		
		if(result!=null){
			List<OperationDetailReportStruc> reports = result.getOperationDetailReportStruc();
			List<OperationDetailReportStruc> reportStrucList=new ArrayList<OperationDetailReportStruc>();
			if(reports!=null && reports.size()>0){
				for(OperationDetailReportStruc reportStruc:reports){ 
						reportStrucList.add(DTOConvertor4Web.winsReport2winsReportForUI(reportStruc));   
				}
			}
			WinsSumReportQueryWebResponse winsSum = response2.getBody().getResult();

			model.addAttribute("reports", reportStrucList);
			model.addAttribute("req", request);

			model.addAttribute("lotteryid", result.getLotteryid());
			model.addAttribute("lotteryName", result.getLotteryName());
			model.addAttribute("issueCode", result.getIssueCode());
			model.addAttribute("webIssueCode", result.getWebIssueCode());
			model.addAttribute("reportDate", DateUtils.format(DataConverterUtil.convertLong2Date(result.getReportDate()),
					DateUtils.DATE_FORMAT_PATTERN));
			
			model.addAttribute("winsSum", winsSum);
		}
		
		

		return "/operations/winsReport/winsDetailReportList";
	}

	@RequestMapping(value = "exportWinsReport")
	public ModelAndView exportOrderOperations(@ModelAttribute("req") WinsReportQueryRequest request)
			throws Exception {
		Response<WinsReportQueryResponse> response = new Response<WinsReportQueryResponse>();

		if (null == request.getSortType()) {
			request.setSortType(0);
		} 

		logger.info("export WinsReport start");
		try {
			Long userid = RequestContext.getCurrUser().getId();
			String username = RequestContext.getCurrUser().getUserName();
			response = httpClient.invokeHttp(serverPath + queryWinsReportForExport, request, userid, username, WinsReportQueryResponse.class);
		} catch (Exception e) {
			logger.error("export WinsReport error", e);
			throw e;
		}

		if (response.getBody().getResult().getOperationReportStruc() != null) {
			List<OperationReportStrucForUI> reports = new ArrayList<OperationReportStrucForUI>();

			for (OperationReportStruc struc : response.getBody().getResult().getOperationReportStruc()) {
				OperationReportStrucForUI report = DTOConvertor4Web.operationReportStruc2OperationReportStrucForUI(struc);
				reports.add(report);
			}

			//执行导出功能
			ExportViewDataModel viewTemplates = new ExportViewDataModel();
			String[] titles = new String[] {"日期", "彩种名称", "期号", "销售总额", "撤单手续费",  "返点总额", "返奖总额", 
					"盈亏值"};  
			String[] columns = new String[] {"reportDate", "lotteryName", "webIssueCode", "totalSales", "totalCancel", "totalPoints","totalWins","totalProfit"};  
			
			viewTemplates.setFileName("单期盈亏报表导出数据");
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
