package com.winterframework.firefrog.game.web.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.winterframework.firefrog.common.httpjsonclient.IHttpJsonClient;
import com.winterframework.firefrog.common.util.DataConverterUtil;
import com.winterframework.firefrog.common.util.DateUtils;
import com.winterframework.firefrog.game.dao.vo.GameWarnEvent;
import com.winterframework.firefrog.game.web.controller.view.ExportViewDataModel;
import com.winterframework.firefrog.game.web.controller.view.PdfView;
import com.winterframework.firefrog.game.web.dto.AjaxResponse;
import com.winterframework.firefrog.game.web.dto.AuditLotteryIssueMonitorRequest;
import com.winterframework.firefrog.game.web.dto.AuditLotteryMonitorOrderRequest;
import com.winterframework.firefrog.game.web.dto.AuditLotteryMonitorOrdersRequest;
import com.winterframework.firefrog.game.web.dto.CancelOrderRequest;
import com.winterframework.firefrog.game.web.dto.GameIssueQueryRequest;
import com.winterframework.firefrog.game.web.dto.GameIssueQueryResponse;
import com.winterframework.firefrog.game.web.dto.GameOrderDetailQueryResponse;
import com.winterframework.firefrog.game.web.dto.GameOrderQueryRequest;
import com.winterframework.firefrog.game.web.dto.IssueWarnLogStruc;
import com.winterframework.firefrog.game.web.dto.IssueWarnLogStrucDTO;
import com.winterframework.firefrog.game.web.dto.LotteryIssueMonitorLogRequest;
import com.winterframework.firefrog.game.web.dto.LotteryIssueMonitorLogResponse;
import com.winterframework.firefrog.game.web.dto.LotteryIssueStruc;
import com.winterframework.firefrog.game.web.dto.LotteryIssueStrucDTO;
import com.winterframework.firefrog.game.web.dto.LotteryMonitorDetailRequest;
import com.winterframework.firefrog.game.web.dto.LotteryMonitorDetailResponse;
import com.winterframework.firefrog.game.web.dto.LotteryMonitorListRequest;
import com.winterframework.firefrog.game.web.dto.LotteryMonitorListResponse;
import com.winterframework.firefrog.game.web.dto.LotteryNoticesResponse;
import com.winterframework.firefrog.game.web.dto.OperationsAddNumberRecordRequest;
import com.winterframework.firefrog.game.web.dto.OperationsCancelCurrentPackageRequest;
import com.winterframework.firefrog.game.web.dto.OperationsCancelFollowPlanRequest;
import com.winterframework.firefrog.game.web.dto.OperationsContinueIssueRequest;
import com.winterframework.firefrog.game.web.dto.OperationsContinueIssueResponse;
import com.winterframework.firefrog.game.web.dto.OperationsModifyNumberRecordRequest;
import com.winterframework.firefrog.game.web.dto.OperationsPauseIssueRequest;
import com.winterframework.firefrog.game.web.dto.RiskOrderDetailStruc;
import com.winterframework.firefrog.game.web.dto.RiskOrderStruc;
import com.winterframework.firefrog.game.web.dto.SubUserReportRequest;
import com.winterframework.firefrog.game.web.util.PageUtils;
import com.winterframework.modules.page.PageRequest;
import com.winterframework.modules.spring.exetend.PropertyConfig;
import com.winterframework.modules.web.jsonresult.Response;
import com.winterframework.modules.web.jsonresult.ResultPager;
import com.winterframework.modules.web.util.RequestContext;

/**
 * 
* @ClassName: LotteryIssueMonitorWebController 
* @Description:彩种奖期监控 
* @author Richard
* @date 2013-10-15 下午1:33:05 
*
 */
@RequestMapping("/gameoa")
@Controller("lotteryIssueMonitorWebController")
public class LotteryIssueMonitorWebController {

	private Logger log = LoggerFactory.getLogger(LotteryIssueMonitorWebController.class);

	@Resource(name = "httpJsonClientImpl")
	private IHttpJsonClient httpClient;

	@PropertyConfig(value = "url.connect")
	private String serverPath;

	@PropertyConfig(value = "url.game.queryLotteryIssueWarnNotices")
	private String queryLotteryIssueWarnNoticesUrl;

	@PropertyConfig(value = "url.game.queryLotteryIssueWarnList")
	private String queryLotteryIssueWarnListUrl;

	@PropertyConfig(value = "url.game.queryLotteryIssueMonitorDetail")
	private String queryLotteryIssueMonitorDetailUrl;

	@PropertyConfig(value = "url.game.queryLotteryIssueLog")
	private String queryLotteryIssueLogUrl;

	@PropertyConfig(value = "url.game.auditIssue")
	private String auditIssueUrl;

	@PropertyConfig(value = "url.game.auditOrder")
	private String auditOrderUrl;

	@PropertyConfig(value = "url.game.auditOrders")
	private String auditOrdersUrl;

	@PropertyConfig(value = "url.game.addNumberRecord")
	private String addNumberRecordUrl;

	@PropertyConfig(value = "url.game.modifyNumberRecord")
	private String modifyNumberRecordUrl;

	@PropertyConfig(value = "url.game.pauseIssue")
	private String pauseIssueUrl;

	@PropertyConfig(value = "url.game.reTry")
	private String reTry;

	@PropertyConfig(value = "url.game.continueIssue")
	private String continueIssueUrl;

	@PropertyConfig(value = "url.game.cancelCurrentPackage")
	private String cancelCurrentPackageUrl;

	@PropertyConfig(value = "url.game.cancelFollowPlan")
	private String cancelFollowPlanUrl;

	@PropertyConfig(value = "url.game.getCurrentGameIssue")
	private String currentGameIssue;

	@PropertyConfig(value = "url.game.queryGameOrdersWithSlip")
	private String queryGameOrdersWithSlipUrl;

	//用户信息页面Url
	@PropertyConfig(value = "url.user.userInfo")
	private String userInfoUrl;
	//冻结用户Url
	@PropertyConfig(value = "url.user.freezeUser")
	private String freezeUserUrl;

	@PropertyConfig(value = "url.game.cancelOrder")
	private String cancelOrder;

	private SimpleDateFormat timeFormat;
	private SimpleDateFormat dateFormat;

	private static ObjectMapper mapper = new ObjectMapper();

	static {
		mapper = new ObjectMapper();
		mapper.setSerializationInclusion(Include.NON_EMPTY);
		mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
	}

	/**
	 * 
	* @Title: lotteryNotiecs 
	* @Description: 5.5.30	彩种奖期监控通知(OMI030)
	* @return
	* @throws Exception
	 */
	@RequestMapping("/lotteryIssueWarnNotices")
	@ResponseBody
	public Object lotteryIssueWarnNotices() throws Exception {

		AjaxResponse view = new AjaxResponse();

		try {

			Response<LotteryNoticesResponse> response = httpClient.invokeHttp(serverPath
					+ queryLotteryIssueWarnNoticesUrl, new TypeReference<Response<LotteryNoticesResponse>>() {
			});

			String str = mapper.writeValueAsString(response.getBody().getResult().getList());
			view.setStatus(1l);
			view.setMessage(str);

		} catch (Exception e) {

			log.error("lotteryIssueWarnNotices error", e);
			view.setStatus(2l);
			view.setMessage("lotteryIssueWarnNotices error!");
		}

		return view;
	}

	/**
	 * 
	* @Title: queryLotteryIssueWarnList 
	* @Description: 5.5.31	彩种奖期监控列表(OMI031) 
	* @return
	* @throws Exception
	 */
	@RequestMapping("/queryLotteryIssueWarn")
	@ResponseBody
	public ModelAndView queryLotteryIssueWarnList(
			@ModelAttribute("monitorRequest") LotteryMonitorListRequest monitorRequest,
			@ModelAttribute("page") PageRequest<SubUserReportRequest> page) throws Exception {

		ModelAndView view = new ModelAndView("operations/issueMonitor/issueMonitorIndex");
		timeFormat = new SimpleDateFormat("HH:mm:ss");
		dateFormat = new SimpleDateFormat("yyyy-MM-ss HH:mm:ss");
		try {

			if (null == page) {
				page = new PageRequest<SubUserReportRequest>();
			}

			//查询时也要刷新通知
			Response<LotteryNoticesResponse> response = httpClient.invokeHttp(serverPath
					+ queryLotteryIssueWarnNoticesUrl, new TypeReference<Response<LotteryNoticesResponse>>() {
			});

			String str = "";
			if (null != response.getBody().getResult() && null != response.getBody().getResult().getList()) {
				str = mapper.writeValueAsString(response.getBody().getResult().getList());
			}
			view.addObject("result", str);

			//查询条件
			if (null == monitorRequest.getIssueType()) {
				monitorRequest.setIssueType(0);
			}

			view.addObject("issueType", monitorRequest.getIssueType());

			if (null == monitorRequest.getLotteryId()) {
				monitorRequest.setLotteryId(99101L);
			}

			view.addObject("lotteryId", monitorRequest.getLotteryId());

			if (null == monitorRequest.getDateType()) {
				monitorRequest.setDateType(1);
			}

			if (monitorRequest.getDateType() == 1) { //当天

				monitorRequest.setStartIssueTime(System.currentTimeMillis());
				//				monitorRequest.setEndIssueTime(System.currentTimeMillis());
				Calendar calendar = Calendar.getInstance();
				calendar.add(Calendar.DATE, 1);
				monitorRequest.setEndIssueTime(calendar.getTimeInMillis());
			} else if (monitorRequest.getDateType() == 3) {

				Calendar calendar = Calendar.getInstance();
				calendar.add(Calendar.DATE, -3);
				monitorRequest.setStartIssueTime(calendar.getTimeInMillis());

				monitorRequest.setEndIssueTime(System.currentTimeMillis());
			} else if (monitorRequest.getDateType() == 7) {

				Calendar calendar = Calendar.getInstance();
				calendar.add(Calendar.DATE, -6);
				monitorRequest.setStartIssueTime(calendar.getTimeInMillis());

				monitorRequest.setEndIssueTime(System.currentTimeMillis());
			}

			view.addObject("dateType", monitorRequest.getDateType());

			//监控列表
			Response<LotteryMonitorListResponse> response2 = httpClient.invokeHttp(serverPath
					+ queryLotteryIssueWarnListUrl, monitorRequest, PageUtils.getPager(page),
					new TypeReference<Response<LotteryMonitorListResponse>>() {
					});

			List<LotteryIssueStrucDTO> issueList = new ArrayList<LotteryIssueStrucDTO>();

			if (null != response2 && response2.getBody() != null && response2.getBody().getResult() != null) {

				for (LotteryIssueStruc struc : response2.getBody().getResult().getList()) {

					LotteryIssueStrucDTO dto = new LotteryIssueStrucDTO();

					dto.setConfirmDrawTime(struc.getConfirmDrawTime() > 0 ? timeFormat.format(DataConverterUtil
							.convertLong2Date(struc.getConfirmDrawTime())) : null);
					dto.setIssueCode(struc.getIssueCode());
					dto.setLotteryid(struc.getLotteryid());
					dto.setLotteryName(struc.getLotteryName());
					dto.setNumberRecord(struc.getNumberRecord());
					dto.setOpenDrawTime(struc.getOpenDrawTime() > 0 ? timeFormat.format(DataConverterUtil
							.convertLong2Date(struc.getOpenDrawTime())) : null);
					dto.setRecivceDrawTime(struc.getRecivceDrawTime() > 0 ? timeFormat.format(DataConverterUtil
							.convertLong2Date(struc.getRecivceDrawTime())) : null);
					dto.setPeriodStatus(struc.getPeriodStatus());
					dto.setSaleDate(struc.getSaleDate());
					dto.setSalePeriod(struc.getSalePeriod());
					dto.setWarnDescStr(struc.getWarnDescStr());
					dto.setWebIssueCode(struc.getWebIssueCode());
					dto.setPauseStatus(struc.getPauseStatus());

					issueList.add(dto);
				}

			}

			ResultPager rp = response2.getBody().getPager();

			view.addObject("issueList", issueList);
			view.addObject("page", PageUtils.getPageForView(page, rp));

		} catch (Exception e) {
			log.error("queryLotteryIssueWarnList error:", e);
			throw e;
		}

		return view;
	}

	/**
	 * 
	* @Title: queryLotteryIssueWarnDetail 
	* @Description: 5.5.32	彩种奖期监控详情(OMI032)
	* @return
	* @throws Exception
	 */
	@RequestMapping("/queryLotteryIssueWarnDetail")
	@ResponseBody
	public ModelAndView queryLotteryIssueWarnDetail(@RequestParam("lotteryId") Long lotteryId,
			@RequestParam("issueCode") Long issueCode, @ModelAttribute("page") PageRequest<SubUserReportRequest> page)
			throws Exception {

		ModelAndView view = new ModelAndView("operations/issueMonitor/issueMonitorDetail");

		try {

			if (null == page) {
				page = new PageRequest<SubUserReportRequest>();
			}

			LotteryMonitorDetailRequest request = new LotteryMonitorDetailRequest();
			request.setIssueCode(issueCode);
			request.setLotteryId(lotteryId);

			LotteryMonitorDetailResponse detailResponse = new LotteryMonitorDetailResponse();

			GameIssueQueryRequest gameIssueQueryRequest = new GameIssueQueryRequest();
			gameIssueQueryRequest.setLotteryId(lotteryId);
			gameIssueQueryRequest.setIssueCode(issueCode);
			Response<GameIssueQueryResponse> gameIssueQueryResponse = httpClient.invokeHttp(serverPath
					+ currentGameIssue, gameIssueQueryRequest, GameIssueQueryResponse.class);

			Response<LotteryMonitorDetailResponse> response = httpClient.invokeHttp(serverPath
					+ queryLotteryIssueMonitorDetailUrl, request, PageUtils.getPager(page),
					new TypeReference<Response<LotteryMonitorDetailResponse>>() {
					});

			if (null != response.getBody() && null != response.getBody().getResult()) {
				detailResponse = response.getBody().getResult();

				List<RiskOrderStruc> list = detailResponse.getRiskOrderList();

				int orderCount = 0;
				int pass = 0;
				int unPass = 0;
				int userCount = 0;
				if (null != list) {

					userCount = list.size();
					for (RiskOrderStruc struc : list) {

						if (null != struc.getRiskOrderDetailStrucs()) {
							orderCount += struc.getRiskOrderDetailStrucs().size();

							for (RiskOrderDetailStruc detailStruc : struc.getRiskOrderDetailStrucs()) {
								if (detailStruc.getStatus() == 1) {
									pass++;
								}
								if (detailStruc.getStatus() == 2) {
									unPass++;
								}
							}
						}

					}
				}

				view.addObject("userCount", userCount);
				view.addObject("orderCount", orderCount);
				view.addObject("pass", pass);
				view.addObject("unPass", unPass);
			}

			ResultPager rp = response.getBody().getPager();

			view.addObject("page", PageUtils.getPageForView(page, rp));
			view.addObject("lotteryId", lotteryId);
			view.addObject("issueCode", issueCode);
			view.addObject("detail", detailResponse);
			view.addObject("userInfoUrl", userInfoUrl);
			view.addObject("freezeUserUrl", freezeUserUrl);
			view.addObject("canUpdate",
					DataConverterUtil.convertDate2Long(DateUtils.currentDate()) > gameIssueQueryResponse.getBody()
							.getResult().getSaleEndTime());
			view.addObject(
					"saleEndTime",
					DateUtils.format(
							DataConverterUtil.convertLong2Date(gameIssueQueryResponse.getBody().getResult()
									.getSaleEndTime()), DateUtils.DATETIME_FORMAT_PATTERN));
			view.addObject("gameIssue", gameIssueQueryResponse.getBody().getResult());

		} catch (Exception e) {
			log.error("queryLotteryIssueWarnDetail error:", e);
			throw e;
		}
		return view;
	}

	/**
	 * 
	* @Title: downLoadGameOrders 
	* @Description: 下载方案记录
	* @param request
	* @return
	* @throws Exception
	 */
	@RequestMapping("/downLoadGameOrders")
	@ResponseBody
	public ModelAndView downLoadGameOrders(@ModelAttribute("request") GameOrderQueryRequest request) throws Exception {
		try {
			Response<List<GameOrderDetailQueryResponse>> response = httpClient.invokeHttp(serverPath
					+ queryGameOrdersWithSlipUrl, request,
					new TypeReference<Response<List<GameOrderDetailQueryResponse>>>() {
					});
			if (response.getBody().getResult() != null && response.getBody().getResult().size() > 0) {
				ExportViewDataModel viewTemplates = new ExportViewDataModel();
				viewTemplates.setFileName("");
				viewTemplates.setHeader(new String[] { response.getBody().getResult().get(0).getOrdersStruc()
						.getWebIssueCode() });
				viewTemplates.setColumns(new String[] { RequestContext.getCurrUser().getUserName() });
				viewTemplates.setDataList(response.getBody().getResult());
				Map<String, Object> model = new HashMap<String, Object>();
				model.put("dataModel", viewTemplates);
				return new ModelAndView(new PdfView(), model);
			}
		} catch (Exception e) {
			log.error("downLoadGameOrders error");
			throw e;
		}
		return null;
	}

	/**
	 * 
	* @Title: queryLotteryIssueWarnLog 
	* @Description: 5.5.34	彩种奖期监控日志(OMI034) 
	* @param request
	* @return
	* @throws Exception
	 */
	@RequestMapping("/queryLotteryIssueWarnLog")
	@ResponseBody
	public ModelAndView queryLotteryIssueWarnLog(@ModelAttribute("request") LotteryIssueMonitorLogRequest request,
			@ModelAttribute("page") PageRequest<SubUserReportRequest> page) throws Exception {

		ModelAndView view = new ModelAndView("operations/issueMonitor/issueMonitorLog");
		dateFormat = new SimpleDateFormat("yyyy-MM-ss HH:mm:ss");
		try {

			if (null == page) {
				page = new PageRequest<SubUserReportRequest>();
			}

			if (null == request.getLotteryid()) {
				request.setLotteryid(0l);
			}
			view.addObject("lotteryId", request.getLotteryid());

			if (null == request.getWarnType()) {
				request.setWarnType(0);
			}

			view.addObject("warnType", request.getWarnType());

			if (null == request.getDateType()) {
				request.setDateType(0);
			}

			if (request.getDateType() == 1) { //当天

				request.setStartCreateTime(System.currentTimeMillis());
				request.setEndCreateTime(System.currentTimeMillis());
			} else if (request.getDateType() == 3) {
				request.setStartCreateTime(DateUtils.addDays(new Date(), -2).getTime());

				request.setEndCreateTime(DateUtils.addDays(new Date(), 1).getTime());
			} else if (request.getDateType() == 7) {
				request.setStartCreateTime(DateUtils.addDays(new Date(), -6).getTime());

				request.setEndCreateTime(DateUtils.addDays(new Date(), 1).getTime());
			}

			view.addObject("dateType", request.getDateType());

			Response<LotteryIssueMonitorLogResponse> response = httpClient.invokeHttp(serverPath
					+ queryLotteryIssueLogUrl, request, PageUtils.getPager(page),
					new TypeReference<Response<LotteryIssueMonitorLogResponse>>() {
					});

			List<IssueWarnLogStrucDTO> result = new ArrayList<IssueWarnLogStrucDTO>();

			if (null != response.getBody().getResult()) {
				for (IssueWarnLogStruc struc : response.getBody().getResult().getList()) {

					IssueWarnLogStrucDTO dto = new IssueWarnLogStrucDTO();
					if (null != struc.getCreateTime()) {
						dto.setCreateTime(DateUtils.format(DataConverterUtil.convertLong2Date(struc.getCreateTime()),
								DateUtils.DATETIME_FORMAT_PATTERN));
					}
					dto.setDisposeInfo(struc.getDisposeInfo());
					dto.setDisposeMemo(struc.getDisposeMemo());
					dto.setDisposeUser(struc.getDisposeUser());
					dto.setDoingMemo(struc.getDoingMemo());
					dto.setLotteryName(struc.getLotteryName());
					dto.setStatus(struc.getStatus() == 1 ? "待处理" : "已处理");
					dto.setWarnDes(struc.getWarnDes());
					dto.setWarnTypeName(struc.getWarnTypeName());
					dto.setWebIssueCode(struc.getWebIssueCode());

					result.add(dto);
				}
				ResultPager rp = response.getBody().getPager();
				view.addObject("page", PageUtils.getPageForView(page, rp));
			} else {
				ResultPager rp = new ResultPager(0, 0, 0);
				view.addObject("page", PageUtils.getPageForView(page, rp));
			}

			view.addObject("logList", result);

		} catch (Exception e) {
			log.error("queryLotteryIssueWarnLog error:", e);
			throw e;
		}
		return view;
	}

	@RequestMapping("/auditIssue")
	@ResponseBody
	public Object auditIssue(@RequestParam("lotteryId") Long lotteryId, @RequestParam("issueCode") Long issueCode)
			throws Exception {

		AjaxResponse resp = new AjaxResponse();

		try {

			AuditLotteryIssueMonitorRequest requestData = new AuditLotteryIssueMonitorRequest();
			requestData.setIssueCode(issueCode);
			requestData.setLotteryId(lotteryId);
			httpClient.invokeHttpWithoutResultType(serverPath + auditIssueUrl, requestData);

			resp.setMessage("success");
			resp.setStatus(1l);

		} catch (Exception e) {
			log.error("auditIssue", e);
			resp.setMessage("auditIssue error");
			resp.setStatus(2l);
		}

		return resp;
	}

	@RequestMapping("/auditOrder")
	@ResponseBody
	public Object auditOrder(@RequestParam("orderCode") String orderCode, @RequestParam("status") Integer status)
			throws Exception {

		AjaxResponse resp = new AjaxResponse();

		try {

			AuditLotteryMonitorOrderRequest request = new AuditLotteryMonitorOrderRequest();
			request.setOrderCode(orderCode);
			request.setStatus(status);
			httpClient.invokeHttpWithoutResultType(serverPath + auditOrderUrl, request);

			resp.setMessage("success");
			resp.setStatus(1l);

		} catch (Exception e) {
			log.error("auditOrder error:", e);
			resp.setMessage("AuditOrder error");
			resp.setStatus(2l);
		}

		return resp;
	}

	/** 
	* @Title: auditOrders 
	* @Description: 批量审核订单 
	* @param orderCodes 已逗号隔开的审核订单列表
	* @return
	* @throws Exception
	*/
	@RequestMapping("/auditOrders")
	@ResponseBody
	public Object auditOrders(@RequestParam("orderCodes") String orderCodes) throws Exception {

		AjaxResponse resp = new AjaxResponse();

		try {

			AuditLotteryMonitorOrdersRequest request = new AuditLotteryMonitorOrdersRequest();
			request.setOrderCodes(orderCodes);
			httpClient.invokeHttpWithoutResultType(serverPath + auditOrdersUrl, request);

			resp.setMessage("success");
			resp.setStatus(1l);

		} catch (Exception e) {
			log.error("auditOrders error:", e);
			resp.setMessage("AuditOrders error");
			resp.setStatus(2l);
		}

		return resp;
	}

	@RequestMapping("/addNumberRecord")
	@ResponseBody
	public Object addNumberRecord(@RequestBody OperationsAddNumberRecordRequest json) throws Exception {
		AjaxResponse resp = new AjaxResponse();

		try {
			Long userId = RequestContext.getCurrUser().getId();
			String userName = RequestContext.getCurrUser().getUserName();
			Response<?> response = httpClient.invokeHttp(serverPath + addNumberRecordUrl, json, userId, userName,
					Object.class);

			Long status = response.getHead().getStatus();
			if (status.longValue() == 0L) {
				resp.setMessage("输入开奖号码成功");
				resp.setStatus(0l);
			} else if (status.longValue() == 1L) {
				resp.setMessage("输入开奖号码失败");
				resp.setStatus(1l);
			} else if (status.longValue() == 201005L) {
				resp.setMessage("奖期状态不是结束销售状态");
				resp.setStatus(3l);
			} else {
				if (status.longValue() == 3L) {
					resp.setMessage("奖期状态不是结束销售状态，不能输入开奖号码");
				} else {
					resp.setMessage("开奖号码已存在");
				}
				resp.setStatus(2l);
			}

		} catch (Exception e) {
			log.error("addNumberRecord error:", e);
			resp.setMessage("输入开奖号码出现错误");
			resp.setStatus(2l);
		}

		return resp;
	}

	@RequestMapping("/modifyNumberRecord")
	@ResponseBody
	public Object modifyNumberRecord(@RequestBody OperationsModifyNumberRecordRequest json) throws Exception {
		AjaxResponse resp = new AjaxResponse();

		try {
			Long userId = RequestContext.getCurrUser().getId();
			String userName = RequestContext.getCurrUser().getUserName();
			Response<?> response = httpClient.invokeHttp(serverPath + modifyNumberRecordUrl, json, userId, userName,
					Object.class);

			Long status = response.getHead().getStatus();
			if (status.longValue() == 0L) {
				resp.setMessage("输入官方开奖号码成功,开始重新记奖");
				resp.setStatus(0l);
			} else if (status.longValue() == 201005L) {
				resp.setMessage("奖期状态不是结束销售状态，不能输入开奖号码");
				resp.setStatus(3L);
			} else {
				resp.setMessage("输入官方开奖号码失败");
				resp.setStatus(1l);
			}

		} catch (Exception e) {
			log.error("modifyNumberRecord error:", e);
			resp.setMessage("输入官方开奖号码出现错误");
			resp.setStatus(2l);
		}

		return resp;
	}

	@RequestMapping("/pauseIssue")
	@ResponseBody
	public Object pauseIssue(@RequestBody OperationsPauseIssueRequest json) throws Exception {
		AjaxResponse resp = new AjaxResponse();

		try {
			Long userId = RequestContext.getCurrUser().getId();
			String userName = RequestContext.getCurrUser().getUserName();
			Response<?> response = httpClient.invokeHttp(serverPath + pauseIssueUrl, json, userId, userName,
					Object.class);

			Long status = response.getHead().getStatus();
			if (status.longValue() == 0L) {
				resp.setMessage("暂停奖期成功");
				resp.setStatus(0l);
			} else {
				resp.setMessage("暂停奖期失败");
				resp.setStatus(1l);
			}

		} catch (Exception e) {
			log.error("pauseIssue error:", e);
			resp.setMessage("暂停奖期出现错误");
			resp.setStatus(2l);
		}

		return resp;
	}

	@RequestMapping("/continueIssue")
	@ResponseBody
	public Object continueIssue(@RequestBody OperationsContinueIssueRequest json) throws Exception {
		AjaxResponse resp = new AjaxResponse();

		try {
			Long userId = RequestContext.getCurrUser().getId();
			String userName = RequestContext.getCurrUser().getUserName();
			Response<OperationsContinueIssueResponse> response = httpClient.invokeHttp(serverPath + continueIssueUrl,
					json, userId, userName, OperationsContinueIssueResponse.class);

			Long status = response.getHead().getStatus();
			if (status.longValue() == 0L) {
				resp.setMessage("继续奖期成功");
				resp.setStatus(0l);
			} else if (status == 1L) {
				resp.setMessage("继续奖期失败");
				resp.setStatus(1l);
			} /* 经确认，继续派奖不需要判断之前是否存在暂停奖期。
				else if (status==2L) {
				//	0001481: 游戏后台 暂缓派奖 -> 继续派奖操作无效
				OperationsContinueIssueResponse op = response.getBody().getResult();
				resp.setMessage("存在早期暂停奖期，奖期为【"+ op.getIssueCode() + "】继续奖期失败");
				resp.setStatus(1l);
				}*/

		} catch (Exception e) {
			log.error("continueIssue error:", e);
			resp.setMessage("继续奖期出现错误");
			resp.setStatus(2l);
		}

		return resp;
	}

	@RequestMapping("/cancelCurrentPackage")
	@ResponseBody
	public Object cancelCurrentPackage(@RequestBody OperationsCancelCurrentPackageRequest json) throws Exception {
		AjaxResponse resp = new AjaxResponse();

		try {
			Long userId = RequestContext.getCurrUser().getId();
			String userName = RequestContext.getCurrUser().getUserName();
			Response<?> response = httpClient.invokeHttp(serverPath + cancelCurrentPackageUrl, json, userId, userName,
					Object.class);

			Long status = response.getHead().getStatus();
			if (status.longValue() == 0L) {
				resp.setMessage("撤销本期方案任务开始执行");
				resp.setStatus(0l);
			} else if (status.longValue() == 201005L) {
				resp.setMessage("可撤销时间结束");
				resp.setStatus(3L);
			} else if (status == 206001L) {
				resp.setStatus(2l);
				resp.setMessage("连接资金系统异常!");
			} else if (status.longValue() == 301001L) {
				resp.setMessage("可撤销时间结束");
				resp.setStatus(3L);
			} else {
				resp.setMessage("撤销本期方案失败");
				resp.setStatus(1l);
			}

		} catch (Exception e) {
			log.error("cancelCurrentPackage error:", e);
			resp.setMessage("撤销本期方案出现错误");
			resp.setStatus(2l);
		}

		return resp;
	}

	@RequestMapping("/cancelFollowPlan")
	@ResponseBody
	public Object cancelFollowPlan(@RequestBody OperationsCancelFollowPlanRequest json) throws Exception {
		AjaxResponse resp = new AjaxResponse();

		try {
			Long userId = RequestContext.getCurrUser().getId();
			String userName = RequestContext.getCurrUser().getUserName();

			GameIssueQueryRequest gameIssueQueryRequest = new GameIssueQueryRequest();
			gameIssueQueryRequest.setLotteryId(json.getLotteryid());
			Response<GameIssueQueryResponse> gameIssueQueryResponse = httpClient.invokeHttp(serverPath
					+ currentGameIssue, gameIssueQueryRequest, GameIssueQueryResponse.class);

			Long currentIssueCode = Long.valueOf(gameIssueQueryResponse.getBody().getResult().getWebIssueCode()
					.replaceAll("-", ""));
			Long cancelBeginCode = Long.valueOf(json.getStartIssueCode().replaceAll("-", ""));

			if (cancelBeginCode.longValue() < currentIssueCode.longValue()) {
				resp.setMessage("撤销后续追号失败,撤销开始奖期期号需要在当前销售期之后！");
				resp.setStatus(1l);
				return resp;
			}
			Response<?> response = httpClient.invokeHttp(serverPath + cancelFollowPlanUrl, json, userId, userName,
					Object.class);

			Long status = response.getHead().getStatus();
			if (status.longValue() == 0L) {
				resp.setMessage("撤销后续追号任务开始执行");
				resp.setStatus(0l);
			} else if (status == 206001L) {
				resp.setStatus(2l);
				resp.setMessage("连接资金系统异常!");
			} else {
				resp.setMessage("撤销后续追号失败");
				resp.setStatus(1l);
			}

		} catch (Exception e) {
			log.error("cancelFollowPlan error:", e);
			resp.setMessage("撤销后续追号出现错误");
			resp.setStatus(2l);
		}

		return resp;
	}

	/** 
	* @Title: cancelOrder 
	* @Description: 撤销订单
	* @param request
	* @param model
	* @throws Exception
	*/
	@RequestMapping(value = "/cancelOrder")
	@ResponseBody
	public Object cancelOrder(@ModelAttribute("request") CancelOrderRequest request, Model model) throws Exception {
		log.info("cancelOrder start");
		Long userId = RequestContext.getCurrUser().getId();
		request.setCancelTime(new Date().getTime());
		AjaxResponse resp = new AjaxResponse();
		@SuppressWarnings("rawtypes")
		Response response = new Response();
		try {
			response = httpClient.invokeHttp(serverPath + cancelOrder, request, userId, null, Object.class);
			log.info("cancelOrder end");
			if (response.getHead().getStatus() == 0) {
				resp.setStatus(1l);
				resp.setMessage("success");
			} else if (response.getHead().getStatus() == 202007L) {
				resp.setStatus(2l);
				resp.setMessage("方案撤销失败，订单状态错误!");
			} else if (response.getHead().getStatus() == 201005L) {
				resp.setStatus(2l);
				resp.setMessage("方案撤销失败，该奖期已截止销售!");
			} else if (response.getHead().getStatus() == 206001L) {
				resp.setStatus(2l);
				resp.setMessage("连接资金系统异常!");
			} else if (response.getHead().getStatus() == 301001L) {
				resp.setStatus(2l);
				resp.setMessage("方案撤销失败，该奖期已截止销售!");
			} else {
				resp.setStatus(2l);
				resp.setMessage("方案撤销失败，请检查网络或重试！");
			}
		} catch (Exception e) {
			log.error("cancelOrder is error.", e);
			resp.setStatus(2l);
			resp.setMessage("方案撤销失败，请检查网络或重试！");
			return resp;
		}
		return resp;
	}

	@RequestMapping("/reTry")
	@ResponseBody
	public Object reTry(@ModelAttribute("request") OperationsPauseIssueRequest json) throws Exception {
		AjaxResponse resp = new AjaxResponse();

		try {
			Long userId = RequestContext.getCurrUser().getId();
			String userName = RequestContext.getCurrUser().getUserName();

			Long lotteryid = json.getLotteryid();
			Long issueCode = json.getIssueCode();

			System.out.println(lotteryid);
			System.out.println(issueCode);
			Response<?> response = httpClient.invokeHttp(serverPath + reTry, json, userId, userName, Object.class);

			Long status = response.getHead().getStatus();
			if (status.longValue() == 0L) {
				resp.setMessage("重做成功");
				resp.setStatus(0l);
			} else {
				resp.setMessage("重做失败");
				resp.setStatus(1l);
			}

		} catch (Exception e) {
			log.error("重做 error:", e);
			resp.setMessage("重做出现错误");
			resp.setStatus(2l);
		}

		return resp;
	}
}
