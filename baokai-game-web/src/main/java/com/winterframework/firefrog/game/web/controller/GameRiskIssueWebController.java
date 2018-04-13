package com.winterframework.firefrog.game.web.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.core.type.TypeReference;
import com.winterframework.firefrog.common.httpjsonclient.IHttpJsonClient;
import com.winterframework.firefrog.game.web.dto.AjaxResponse;
import com.winterframework.firefrog.game.web.dto.GameExceptionAuditGameIssueInfoRequest;
import com.winterframework.firefrog.game.web.dto.GameExceptionAuditRequest;
import com.winterframework.firefrog.game.web.dto.GameExceptionAuditStrucDTO;
import com.winterframework.firefrog.game.web.dto.GameRiskIssueListQueryRequest;
import com.winterframework.firefrog.game.web.dto.GameRiskIssueListQueryResponse;
import com.winterframework.firefrog.game.web.dto.GameRiskIssueStruc;
import com.winterframework.firefrog.game.web.dto.GameRiskOperateOrdersRequest;
import com.winterframework.firefrog.game.web.dto.GameRiskOperateResponse;
import com.winterframework.firefrog.game.web.dto.GameRiskWarnUserListQueryRequest;
import com.winterframework.firefrog.game.web.dto.GameRiskWarnUserOrderListResponse;
import com.winterframework.firefrog.game.web.dto.SubUserReportRequest;
import com.winterframework.firefrog.game.web.util.PageUtils;
import com.winterframework.modules.page.PageRequest;
import com.winterframework.modules.spring.exetend.PropertyConfig;
import com.winterframework.modules.web.jsonresult.Response;
import com.winterframework.modules.web.jsonresult.ResultPager;
import com.winterframework.modules.web.util.RequestContext;

/** 
* @ClassName GameRiskIssueWebController 
* @Description 审核系统 审核管理 
* @author  hugh
* @date 2014年4月17日 上午11:23:40 
*  
*/
@Controller("gameRiskIssueWebController")
@RequestMapping(value = "/gameRisk")
public class GameRiskIssueWebController {

	private Logger logger = LoggerFactory.getLogger(GameRiskIssueWebController.class);

	@Resource(name = "httpJsonClientImpl")
	private IHttpJsonClient httpClient;

	@PropertyConfig(value = "url.connect.risk")
	private String serverPath;

	@PropertyConfig(value = "url.game.risk.queryGameRiskIssueList")
	private String queryGameRiskIssueListUrl;

	@PropertyConfig(value = "url.game.risk.queryGameRiskUserOrderList")
	private String queryGameRiskUserOrderListUrl;

	@PropertyConfig(value = "url.game.risk.auditGameRiskOrder")
	private String auditGameRiskOrderUrl;

	//用户信息页面Url
	@PropertyConfig(value = "admin.resources.path")
	private String userInfoUrl;
	//冻结用户Url
	@PropertyConfig(value = "url.user.freezeUser")
	private String freezeUserUrl;

	/**
	 * 
	* @Title: gameExceptionAudit 
	* @Description:游戏异常审核
	* @return
	* @throws Exception
	 */
	@RequestMapping("/queryGameExceptionAuditList")
	@ResponseBody
	public ModelAndView queryGameRiskIssueList(
			@ModelAttribute("monitorRequest") GameExceptionAuditRequest auditRequest,
			@ModelAttribute("page") PageRequest<SubUserReportRequest> page) throws Exception {

		ModelAndView view = new ModelAndView("risk/gameExceptionAuditIndex");

		try {
			String userAccount = RequestContext.getCurrUser().getUserName();

			if (null == page) {
				page = new PageRequest<SubUserReportRequest>();
			}

			GameRiskIssueListQueryRequest request = new GameRiskIssueListQueryRequest();
			//查询条件 默认 重庆时时彩今天待处理
			if (null == auditRequest.getStatus()) {
				request.setStatus(2);
			} else {
				request.setStatus(auditRequest.getStatus());
			}

			view.addObject("status", request.getStatus());

			if (null == auditRequest.getLotteryId()) {
				//request.setLotteryId(99101L);
				request.setLotteryId(-1L);
			} else {
				request.setLotteryId(auditRequest.getLotteryId());
			}

			view.addObject("lotteryId", request.getLotteryId());

			if (request.getLotteryId().longValue() == -1L) {
				request.setLotteryId(null);
			}

			if (null == auditRequest.getDateType()) {
				auditRequest.setDateType(1);
			}

			if (auditRequest.getDateType() == 1) { //当天
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
			} else if (auditRequest.getDateType() == 3) {

				Calendar calendar = Calendar.getInstance();
				calendar.add(Calendar.DATE, -3);
				request.setShowStartTime(calendar.getTimeInMillis());

				request.setShowEndTime(System.currentTimeMillis());
			} else if (auditRequest.getDateType() == 7) {

				Calendar calendar = Calendar.getInstance();
				calendar.add(Calendar.DATE, -6);
				request.setShowStartTime(calendar.getTimeInMillis());

				request.setShowEndTime(System.currentTimeMillis());
			}

			view.addObject("dateType", auditRequest.getDateType());

			//监控列表
			Response<GameRiskIssueListQueryResponse> response = httpClient.invokeHttp(serverPath
					+ queryGameRiskIssueListUrl, request, PageUtils.getPager(page),
					new TypeReference<Response<GameRiskIssueListQueryResponse>>() {
					});

			List<GameExceptionAuditStrucDTO> issueList = new ArrayList<GameExceptionAuditStrucDTO>();

			if (null != response && response.getBody() != null && response.getBody().getResult() != null) {

				for (GameRiskIssueStruc struc : response.getBody().getResult().getGameRiskIssueResponse()) {
					GameExceptionAuditStrucDTO dto = new GameExceptionAuditStrucDTO();

					dto.setIssueCode(struc.getIssueCode());
					dto.setLotteryid(struc.getLotteryid());
					dto.setLotteryName(struc.getLotteryName());
					dto.setOpenDrawTime(String.valueOf(struc.getOpenDrawTime()));
					dto.setConfirmDrawTime(String.valueOf(struc.getFactionDrawTime()));
					dto.setSaleDate(struc.getSaleDate());
					dto.setWebIssueCode(struc.getWebIssueCode());
					dto.setStatus(struc.getRiskStatus());
					dto.setOperator(userAccount.equals(struc.getOperator()) ? null : struc.getOperator());

					issueList.add(dto);
				}

			}

			ResultPager rp = response.getBody().getPager();

			view.addObject("issueList", issueList);
			view.addObject("page", PageUtils.getPageForView(page, rp));

		} catch (Exception e) {
			logger.error("gameExceptionAudit error:", e);
			throw e;
		}

		return view;
	}

	/**
	 * 
	* @Title: queryLotteryIssueWarnDetail 
	* @Description: 5.5.32	审核模块用户订单列表
	* @return
	* @throws Exception
	 */
	@RequestMapping("/queryGameExceptionAuditDetail")
	@ResponseBody
	public ModelAndView queryLotteryIssueWarnDetail(@RequestParam("lotteryId") Long lotteryId,
			@RequestParam("issueCode") Long issueCode, @RequestParam("status") Integer status,
			@ModelAttribute("page") PageRequest<SubUserReportRequest> page) throws Exception {

		ModelAndView view = new ModelAndView("risk/orderList");
		Long userId = RequestContext.getCurrUser().getId();
		String userAccount = RequestContext.getCurrUser().getUserName();

		try {

			if (null == page) {
				page = new PageRequest<SubUserReportRequest>();
			}

			GameRiskWarnUserListQueryRequest request = new GameRiskWarnUserListQueryRequest();
			request.setIssueCode(issueCode);
			request.setLotteryId(lotteryId);
			request.setStatus(status);
			if (status == null || status.intValue() == 1) {
				view.addObject("status", 1);
			} else {
				view.addObject("status", 2);
			}

			GameRiskWarnUserOrderListResponse detailResponse = new GameRiskWarnUserOrderListResponse();

			Response<GameRiskWarnUserOrderListResponse> response = httpClient.invokeHttp(serverPath
					+ queryGameRiskUserOrderListUrl, request, PageUtils.getPager(page), userId, userAccount,
					new TypeReference<Response<GameRiskWarnUserOrderListResponse>>() {
					});

			GameExceptionAuditGameIssueInfoRequest infoRequest = new GameExceptionAuditGameIssueInfoRequest();
			infoRequest.setIssueCode(issueCode);
			infoRequest.setLotteryId(lotteryId);

			if (null != response.getBody() && null != response.getBody().getResult()) {
				detailResponse = response.getBody().getResult();

				view.addObject("issue", detailResponse.getRiskIssueStruc());
				view.addObject("orderCount", detailResponse.getRiskIssueStruc().getRiskWarnOrderNumber());
				view.addObject("pass", detailResponse.getRiskIssueStruc().getRiskDealedWarnOrderNumber());
				view.addObject("config", detailResponse.getRiskConfigStruc());
				view.addObject("detail", detailResponse.getWarnUserOrderStrucs());
				view.addObject("userInfoUrl", userInfoUrl);
				view.addObject("freezeUserUrl", freezeUserUrl);
			}

			view.addObject("lotteryId", lotteryId);
			view.addObject("issueCode", issueCode);
			ResultPager rp = response.getBody().getPager();
			view.addObject("page", PageUtils.getPageForView(page, rp));

		} catch (Exception e) {
			logger.error("queryLotteryIssueWarnDetail error:", e);
			throw e;
		}
		return view;
	}

	@RequestMapping("/auditGameRiskOrder")
	@ResponseBody
	public Object auditGameRiskOrder(@RequestParam("ids") String ids, @RequestParam("status") Long status,
			@RequestParam("disposeMemo") String disposeMemo) throws Exception {

		AjaxResponse view = new AjaxResponse();

		//Long userId = RequestContext.getCurrUser().getId();
		String userName = RequestContext.getCurrUser().getUserName();

		GameRiskOperateOrdersRequest request = new GameRiskOperateOrdersRequest();
		List<Long> orderIds = new ArrayList<Long>();
		String[] idList = ids.split(",");
		for (String string : idList) {
			orderIds.add(Long.parseLong(string));
		}

		request.setOrderIds(orderIds);

		request.setStatus(status);
		if (status.longValue() == 1L) {
			request.setDisposeInfo("风险订单审核通过");
		} else {
			request.setDisposeInfo("风险订单审核不通过");
		}
		request.setDisposeMemo(disposeMemo);
		request.setDisposeUser(userName);

		try {

			//Response<GameRiskOperateResponse> response = 
			httpClient.invokeHttp(serverPath + auditGameRiskOrderUrl, request,
					new TypeReference<Response<GameRiskOperateResponse>>() {
					});

			view.setStatus(1l);
			view.setMessage("成功");

		} catch (Exception e) {

			view.setStatus(2l);
			view.setMessage("lotteryIssueWarnNotices error!");
		}

		return view;

	}

}
