package com.winterframework.firefrog.game.web.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.winterframework.firefrog.common.annotation.ValidRequestBody;
import com.winterframework.firefrog.common.annotation.ValidRequestHeader;
import com.winterframework.firefrog.common.util.PageConverterUtils;
import com.winterframework.firefrog.game.dao.vo.WinsSumReport;
import com.winterframework.firefrog.game.entity.WinsReport;
import com.winterframework.firefrog.game.service.IGameWinsReportService;
import com.winterframework.firefrog.game.web.dto.DTOConvert;
import com.winterframework.firefrog.game.web.dto.OperationDetailReportStruc;
import com.winterframework.firefrog.game.web.dto.OperationReportStruc;
import com.winterframework.firefrog.game.web.dto.WinsDetailReportQueryRequest;
import com.winterframework.firefrog.game.web.dto.WinsDetailReportQueryResponse;
import com.winterframework.firefrog.game.web.dto.WinsReportQueryRequest;
import com.winterframework.firefrog.game.web.dto.WinsReportQueryResponse;
import com.winterframework.firefrog.game.web.dto.WinsSumReportQueryResponse;
import com.winterframework.modules.page.Page;
import com.winterframework.modules.page.PageRequest;
import com.winterframework.modules.web.jsonresult.Request;
import com.winterframework.modules.web.jsonresult.Response;
import com.winterframework.modules.web.jsonresult.ResultPager;

/** 
* @ClassName: GameWinsReportController 
* @Description: 运营盈亏报表Controller 
* @author Denny 
* @date 2013-10-15 上午11:48:42 
*  
*/
@Controller("gameWinsReportController")
@RequestMapping("/game")
public class GameWinsReportController {
	private Logger log = LoggerFactory.getLogger(GameWinsReportController.class);

	private static Map<Integer, String> SORT_TYPE = new HashMap<Integer, String>();
	private static Map<Integer, String> DETAIL_SORT_TYPE = new HashMap<Integer, String>();

	static {
		SORT_TYPE.put(0, "ISSUE_CODE desc,REPORT_DATE desc");
		SORT_TYPE.put(1, "totalSales");
		SORT_TYPE.put(2, "totalSales desc");
		SORT_TYPE.put(3, "totalWins");
		SORT_TYPE.put(4, "totalWins desc");
		SORT_TYPE.put(5, "totalProfit");
		SORT_TYPE.put(6, "totalProfit desc");

		DETAIL_SORT_TYPE.put(0, "BET_METHOD_CODE");
		DETAIL_SORT_TYPE.put(1, "TOTAL_SALES");
		DETAIL_SORT_TYPE.put(2, "TOTAL_SALES desc");
		DETAIL_SORT_TYPE.put(3, "TOTAL_WINS");
		DETAIL_SORT_TYPE.put(4, "TOTAL_WINS desc");
		DETAIL_SORT_TYPE.put(5, "TOTAL_PROFIT");
		DETAIL_SORT_TYPE.put(6, "TOTAL_PROFIT desc");
	}

	@Resource(name = "gameWinsReportServiceImpl")
	private IGameWinsReportService gameWinsReportService;

	/** 
	* @Title: queryWinsReport 
	* @Description: 5.5.41	单期盈亏报表查询(OMI041)
	* @param request
	* @return
	* @throws Exception
	*/
	@RequestMapping(value = "/queryWinsReport")
	@ResponseBody
	public Response<WinsReportQueryResponse> queryWinsReport(
			@RequestBody @ValidRequestBody Request<WinsReportQueryRequest> request) throws Exception {

		log.info("开始查询盈亏报表......");
		Response<WinsReportQueryResponse> response = new Response<WinsReportQueryResponse>(request);

		int sNo = request.getBody().getPager().getStartNo();
		int eNo = request.getBody().getPager().getEndNo();

		PageRequest<WinsReportQueryRequest> pr = PageConverterUtils.getRestPageRequest(sNo, eNo);
		pr.setSearchDo(request.getBody().getParam());
		pr.setSortColumns(SORT_TYPE.get(request.getBody().getParam().getSortType()));

		Page<WinsReport> page = null;
		List<WinsReport> reports = null;
		List<OperationReportStruc> strucs = new ArrayList<OperationReportStruc>();
		WinsReportQueryResponse result = new WinsReportQueryResponse();
		OperationReportStruc struc = null;
		try {
			page = gameWinsReportService.queryWinsReport(pr);
			reports = page.getResult();
			if (reports != null && reports.size() > 0) {
				for (WinsReport wr : reports) {
					struc = DTOConvert.winsReport2OperationReportStruc(wr);
					strucs.add(struc);
				}
			}
			result.setOperationReportStruc(strucs);
			response.setResult(result);
			ResultPager pager = new ResultPager();
			pager.setEndNo(page.getThisPageFirstElementNumber());
			pager.setStartNo(page.getThisPageFirstElementNumber());
			pager.setTotal(page.getTotalCount());
			response.setResultPage(pager);

		} catch (Exception e) {
			log.error("查询盈亏报表异常 ", e);
			throw e;
		}

		log.info("查询盈亏报表完成。");
		return response;
	}

	/** 
	* @Title: queryWinsSumReport 
	* @Description: 单期盈亏报表合计查询
	* @param request
	* @return
	* @throws Exception
	*/
	@RequestMapping(value = "/queryWinsSumReport")
	@ResponseBody
	public Response<WinsSumReportQueryResponse> queryWinsSumReport(
			@RequestBody @ValidRequestBody Request<WinsReportQueryRequest> request) throws Exception {

		log.info("开始查询盈亏报表合计......");
		Response<WinsSumReportQueryResponse> response = new Response<WinsSumReportQueryResponse>(request);

		Long lotteryid = request.getBody().getParam().getLotteryid();
		Long startTime = request.getBody().getParam().getStartCreateTime();
		Long endTime = request.getBody().getParam().getEndCreateTime();

		WinsSumReportQueryResponse result = null;
		WinsSumReport winsSumReport = null;
		try {
			winsSumReport = gameWinsReportService.queryWinsSumReport(lotteryid, startTime, endTime);

			if (winsSumReport != null) {
				result = DTOConvert.winsSumReport2WinsSumReportQueryResponse(winsSumReport);
			}

			response.setResult(result);
		} catch (Exception e) {
			log.error("查询盈亏报表合计异常 ", e);
			throw e;
		}

		log.info("查询盈亏报表合计完成。");
		return response;
	}

	/** 
	* @Title: queryWinsDetailReport 
	* @Description: 5.5.47	单期盈亏明细报表查询(OMI047)
	* @param request
	* @return
	* @throws Exception
	*/
	@RequestMapping(value = "/queryWinsDetailReport")
	@ResponseBody
	public Response<WinsDetailReportQueryResponse> queryWinsDetailReport(
			@RequestBody @ValidRequestBody Request<WinsDetailReportQueryRequest> request) throws Exception {

		log.info("开始查询单期盈亏明细报表......");
		Response<WinsDetailReportQueryResponse> response = new Response<WinsDetailReportQueryResponse>(request);

		Long lotteryid = request.getBody().getParam().getLotteryid();
		Long issueCode = request.getBody().getParam().getIssueCode();
		String sortColumns = DETAIL_SORT_TYPE.get(request.getBody().getParam().getSortType());

		List<WinsReport> reports = null;
		List<OperationDetailReportStruc> strucs = new ArrayList<OperationDetailReportStruc>();
		WinsDetailReportQueryResponse result = new WinsDetailReportQueryResponse(); 
		try {
			reports = gameWinsReportService.queryWinsDetailReport(lotteryid, issueCode, sortColumns);
			if (reports != null && reports.size() > 0) {
				for (WinsReport wr : reports) { 
					strucs.add(DTOConvert.winsReport2OperationDetailReportStruc(wr));
				}
			}

			WinsReport report = reports.get(0);
			result.setIssueCode(report.getIssueCode());
			result.setLotteryid(report.getLotteryid());
			result.setLotteryName(report.getLotteryName());
			result.setWebIssueCode(report.getWebIssueCode());
			result.setReportDate(report.getReportDate().getTime());
			result.setOperationDetailReportStruc(strucs);

			response.setResult(result);

		} catch (Exception e) {
			log.error("查询单期盈亏明细报表异常 ", e);
			throw e;
		}

		log.info("查询单期盈亏明细报表完成。");
		return response;
	}
	
	/** 
	* @Title: queryWinsDetailSumReport 
	* @Description: 单期盈亏明细报表合计查询
	*/
	@RequestMapping(value = "/queryWinsDetailSumReport")
	@ResponseBody
	public Response<WinsSumReportQueryResponse> queryWinsDetailSumReport(
			@RequestBody @ValidRequestBody Request<WinsDetailReportQueryRequest> request) throws Exception {

		log.info("开始查询盈亏明细报表合计......");
		Response<WinsSumReportQueryResponse> response = new Response<WinsSumReportQueryResponse>(request);

		Long lotteryid = request.getBody().getParam().getLotteryid();
		Long issueCode = request.getBody().getParam().getIssueCode();

		WinsSumReportQueryResponse result = null;
		WinsSumReport winsSumReport = null;
		try {
			winsSumReport = gameWinsReportService.queryWinsDetailSumReport(lotteryid, issueCode);

			if (winsSumReport != null) {
				result = DTOConvert.winsSumReport2WinsSumReportQueryResponse(winsSumReport);
			}

			response.setResult(result);
		} catch (Exception e) {
			log.error("查询盈亏明细报表合计异常 ", e);
			throw e;
		}

		log.info("查询盈亏明细报表合计完成。");
		return response;
	}

	/**
	 * 
	* @Title: queryWinsReportForExport 
	* @Description: 查询单期盈亏报表(用于导出excel)
	* @param request
	* @return
	* @throws Exception
	 */
	@RequestMapping(value = "/queryWinsReportForExport")
	@ResponseBody
	public Response<WinsReportQueryResponse> queryWinsReportForExport(
			@RequestBody @ValidRequestHeader @ValidRequestBody Request<WinsReportQueryRequest> request) throws Exception {

		log.info("query wins report for export start...");
		Response<WinsReportQueryResponse> response = new Response<WinsReportQueryResponse>(request);

		String sortColumns = SORT_TYPE.get(request.getBody().getParam().getSortType());
		Long startTime = request.getBody().getParam().getStartCreateTime();
		Long endTime = request.getBody().getParam().getEndCreateTime();
		Long lotteryid = request.getBody().getParam().getLotteryid();

		List<WinsReport> reports = null;
		List<OperationReportStruc> strucs = new ArrayList<OperationReportStruc>();
		WinsReportQueryResponse result = new WinsReportQueryResponse();
		OperationReportStruc struc = null;
		try {
			reports = gameWinsReportService.queryWinsReportForExport(lotteryid, startTime, endTime, sortColumns);
			if (reports != null && reports.size() > 0) {
				for (WinsReport wr : reports) {
					struc = DTOConvert.winsReport2OperationReportStruc(wr);
					strucs.add(struc);
				}
			}
			result.setOperationReportStruc(strucs);
			
			response.setResult(result);
		} catch (Exception e) {
			log.error("query wins report for export error", e);
			throw e;
		}

		log.info("query wins report for export end...");
		return response;
	}
}
