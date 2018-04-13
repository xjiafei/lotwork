/**   
* @Title: GameSeriesConfigRiskWebController.java 
* @Package com.winterframework.firefrog.game.web.controller 
* @Description: TODO(用一句话描述该文件做什么) 
* @author 你的名字   
* @date 2014-2-21 上午11:15:14 
* @version V1.0   
*/
package com.winterframework.firefrog.game.web.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.core.type.TypeReference;
import com.winterframework.firefrog.common.httpjsonclient.IHttpJsonClient;
import com.winterframework.firefrog.common.util.DataConverterUtil;
import com.winterframework.firefrog.game.web.dto.EditSeriesConfigRiskRequest;
import com.winterframework.firefrog.game.web.dto.GameExceptionAuditGameIssueInfoRequest;
import com.winterframework.firefrog.game.web.dto.GameExceptionAuditGameIssueInfoRespone;
import com.winterframework.firefrog.game.web.dto.GameExceptionAuditRequest;
import com.winterframework.firefrog.game.web.dto.GameExceptionAuditResponse;
import com.winterframework.firefrog.game.web.dto.GameExceptionAuditStruc;
import com.winterframework.firefrog.game.web.dto.GameExceptionAuditStrucDTO;
import com.winterframework.firefrog.game.web.dto.LotteryMonitorDetailRequest;
import com.winterframework.firefrog.game.web.dto.LotteryMonitorDetailResponse;
import com.winterframework.firefrog.game.web.dto.QuerySeriesConfigRiskRequest;
import com.winterframework.firefrog.game.web.dto.QuerySeriesConfigRiskResponse;
import com.winterframework.firefrog.game.web.dto.RiskOrderStruc;
import com.winterframework.firefrog.game.web.dto.SubUserReportRequest;
import com.winterframework.firefrog.game.web.util.PageUtils;
import com.winterframework.modules.page.PageRequest;
import com.winterframework.modules.spring.exetend.PropertyConfig;
import com.winterframework.modules.web.jsonresult.Response;
import com.winterframework.modules.web.jsonresult.ResultPager;

/** 
* @ClassName: GameSeriesConfigRiskWebController 
* @Description: 运营参数信息（审核模块）设置web controller
* @author Alan
* @date 2014-2-21 上午11:15:14 
*  
*/
@Controller("gameSeriesConfigRiskWebController")
@RequestMapping(value = "/gameRisk2")
public class GameSeriesConfigRiskWebController {

	private Logger logger = LoggerFactory.getLogger(GameSeriesConfigRiskWebController.class);

	@Resource(name = "httpJsonClientImpl")
	private IHttpJsonClient httpClient;

	@PropertyConfig(value = "url.connect.risk")
	private String serverPath;

	@PropertyConfig(value = "url.operations.queryGameSeriesConfigRisk")
	private String queryGameSeriesConfigRiskUrl;

	@PropertyConfig(value = "url.operations.modifyGameSeriesConfigRisk")
	private String modifyGameSeriesConfigRiskUrl;

	@PropertyConfig(value = "url.game.queryGameExceptionAuditListUrl")
	private String queryGameExceptionAuditListUrl;

	@PropertyConfig(value = "url.game.queryLotteryIssueMonitorDetail")
	private String queryLotteryIssueMonitorDetailUrl;

	@PropertyConfig(value = "url.game.getGameExceptionAuditGameIssueInfoUrl")
	private String getGameExceptionAuditGameIssueInfo;

	//用户信息页面Url
	@PropertyConfig(value = "admin.resources.path")
	private String userInfoUrl;
	//冻结用户Url
	@PropertyConfig(value = "url.user.freezeUser")
	private String freezeUserUrl;

	private SimpleDateFormat timeFormat;
	private SimpleDateFormat dateFormat;

	/**
	* @Title: querySeriesConfig 
	* @Description: 根据lotteryid(彩种ID)&status(状态)查询参数设置（审核模块）
	* @param lotteryid
	* @return Response<QuerySeriesConfigRiskResponse>
	* @throws Exception
	 */
	private Response<QuerySeriesConfigRiskResponse> querySeriesConfigRisk() throws Exception {
		Response<QuerySeriesConfigRiskResponse> response = new Response<QuerySeriesConfigRiskResponse>();

		QuerySeriesConfigRiskRequest request = new QuerySeriesConfigRiskRequest();

		logger.info("query series config start...");
		try {
			response = httpClient.invokeHttp(serverPath + queryGameSeriesConfigRiskUrl, request,
					QuerySeriesConfigRiskResponse.class);
		} catch (Exception e) {
			logger.error("query series config error...");
			throw e;
		}
		logger.info("query series config end...");

		return response;
	}

	/**
	* @Title: toSeriesConfig 
	* @Description: 访问参数设置页面
	* @param lotteryid
	* @param model
	* @throws Exception
	 */
	@RequestMapping(value = "toSeriesConfigRisk")
	public String toSeriesConfigRisk(Model model) throws Exception {
		Response<QuerySeriesConfigRiskResponse> response = querySeriesConfigRisk();

		model.addAttribute("seriesConfigRisk", response.getBody().getResult());

		return "/risk/seriesConfigRiskSetting";
	}

	/**
	* @Title: toModifySeriesConfig 
	* @Description: 访问参数设置修改页面
	* @param lotteryid
	* @param model
	* @throws Exception
	 */
	@RequestMapping(value = "toModifySeriesConfigRisk")
	public String toModifySeriesConfigRisk(Model model) throws Exception {
		Response<QuerySeriesConfigRiskResponse> response = querySeriesConfigRisk();

		model.addAttribute("seriesConfigRisk", response.getBody().getResult());
		model.addAttribute("pageType", "modify");

		return "/risk/seriesConfigRiskSetting";
	}

	/**
	* @Title: modifySeriesConfig 
	* @Description: 修改参数信息
	* @param modifyForm
	* @param model
	* @return
	* @throws Exception
	 */
	@RequestMapping(value = "modifySeriesConfigRisk")
	public String modifySeriesConfigRisk(@ModelAttribute("modifyForm") EditSeriesConfigRiskRequest modifyForm,
			Model model) throws Exception {
		logger.info("modify series config start...");
		try {
			httpClient.invokeHttp(serverPath + modifyGameSeriesConfigRiskUrl, modifyForm, Object.class);
		} catch (Exception e) {
			logger.error("modify series config error...");
			throw e;
		}
		logger.info("modify series config end...");

		Response<QuerySeriesConfigRiskResponse> response = querySeriesConfigRisk();

		model.addAttribute("seriesConfigRisk", response.getBody().getResult());

		return "redirect:/gameRisk/toSeriesConfigRisk";
	}

	/**
	 * 
	* @Title: gameExceptionAudit 
	* @Description:游戏异常审核
	* @return
	* @throws Exception
	 */
	@RequestMapping("/queryGameExceptionAuditList")
	@ResponseBody
	public ModelAndView gameExceptionAudit(@ModelAttribute("monitorRequest") GameExceptionAuditRequest auditRequest,
			@ModelAttribute("page") PageRequest<SubUserReportRequest> page) throws Exception {

		ModelAndView view = new ModelAndView("risk/gameExceptionAuditIndex");
		timeFormat = new SimpleDateFormat("HH:mm:ss");
		dateFormat = new SimpleDateFormat("yyyy-MM-ss HH:mm:ss");
		try {

			if (null == page) {
				page = new PageRequest<SubUserReportRequest>();
			}

			//查询条件 默认 重庆时时彩今天待处理
			if (null == auditRequest.getStatus()) {
				auditRequest.setStatus(1);
			}

			view.addObject("status", auditRequest.getStatus());

			if (null == auditRequest.getLotteryId()) {
				auditRequest.setLotteryId(99101L);
			}

			view.addObject("lotteryId", auditRequest.getLotteryId());

			if (null == auditRequest.getDateType()) {
				auditRequest.setDateType(1);
			}

			if (auditRequest.getDateType() == 1) { //当天

				auditRequest.setStartIssueTime(System.currentTimeMillis());
				//				monitorRequest.setEndIssueTime(System.currentTimeMillis());
				Calendar calendar = Calendar.getInstance();
				calendar.add(Calendar.DATE, 1);
				auditRequest.setEndIssueTime(calendar.getTimeInMillis());
			} else if (auditRequest.getDateType() == 3) {

				Calendar calendar = Calendar.getInstance();
				calendar.add(Calendar.DATE, -3);
				auditRequest.setStartIssueTime(calendar.getTimeInMillis());

				auditRequest.setEndIssueTime(System.currentTimeMillis());
			} else if (auditRequest.getDateType() == 7) {

				Calendar calendar = Calendar.getInstance();
				calendar.add(Calendar.DATE, -6);
				auditRequest.setStartIssueTime(calendar.getTimeInMillis());

				auditRequest.setEndIssueTime(System.currentTimeMillis());
			}

			view.addObject("dateType", auditRequest.getDateType());

			//监控列表
			Response<GameExceptionAuditResponse> response2 = httpClient.invokeHttp(serverPath
					+ queryGameExceptionAuditListUrl, auditRequest, PageUtils.getPager(page),
					new TypeReference<Response<GameExceptionAuditResponse>>() {
					});

			List<GameExceptionAuditStrucDTO> issueList = new ArrayList<GameExceptionAuditStrucDTO>();

			if (null != response2 && response2.getBody() != null && response2.getBody().getResult() != null) {

				for (GameExceptionAuditStruc struc : response2.getBody().getResult().getList()) {
					GameExceptionAuditStrucDTO dto = new GameExceptionAuditStrucDTO();

					dto.setConfirmDrawTime(struc.getConfirmDrawTime() > 0 ? timeFormat.format(DataConverterUtil
							.convertLong2Date(struc.getConfirmDrawTime())) : null);
					dto.setIssueCode(struc.getIssueCode());
					dto.setLotteryid(struc.getLotteryid());
					dto.setLotteryName(struc.getLotteryName());
					dto.setNumberRecord(struc.getNumberRecord());
					dto.setOpenDrawTime(struc.getOpenDrawTime() > 0 ? timeFormat.format(DataConverterUtil
							.convertLong2Date(struc.getOpenDrawTime())) : null);
					dto.setSaleDate(struc.getSaleDate());
					dto.setWebIssueCode(struc.getWebIssueCode());
					dto.setStatus(struc.getStatus());

					issueList.add(dto);
				}

			}

			ResultPager rp = response2.getBody().getPager();

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
	* @Description: 5.5.32	彩种奖期监控详情(OMI032)
	* @return
	* @throws Exception
	 */
	@RequestMapping("/queryGameExceptionAuditDetail")
	@ResponseBody
	public ModelAndView queryLotteryIssueWarnDetail(@RequestParam("lotteryId") Long lotteryId,
			@RequestParam("issueCode") Long issueCode, @ModelAttribute("page") PageRequest<SubUserReportRequest> page)
			throws Exception {

		ModelAndView view = new ModelAndView("risk/orderList");

		try {

			if (null == page) {
				page = new PageRequest<SubUserReportRequest>();
			}

			LotteryMonitorDetailRequest request = new LotteryMonitorDetailRequest();
			request.setIssueCode(issueCode);
			request.setLotteryId(lotteryId);

			LotteryMonitorDetailResponse detailResponse = new LotteryMonitorDetailResponse();

			Response<LotteryMonitorDetailResponse> response = httpClient.invokeHttp(serverPath
					+ queryLotteryIssueMonitorDetailUrl, request, PageUtils.getPager(page),
					new TypeReference<Response<LotteryMonitorDetailResponse>>() {
					});

			GameExceptionAuditGameIssueInfoRequest infoRequest = new GameExceptionAuditGameIssueInfoRequest();
			infoRequest.setIssueCode(issueCode);
			infoRequest.setLotteryId(lotteryId);
			Response<GameExceptionAuditGameIssueInfoRespone> info = httpClient.invokeHttp(serverPath
					+ getGameExceptionAuditGameIssueInfo, infoRequest, GameExceptionAuditGameIssueInfoRespone.class);

			//			if(null != response.getBody() && null != response.getBody().getResult()){
			//				detailResponse = response.getBody().getResult();
			//				
			//				List<RiskOrderStruc> list = detailResponse.getRiskOrderList();
			//				
			//				int orderCount = 0;
			//				int pass = 0;
			//				int unPass = 0; 
			//				int userCount = 0;
			//				if(null != list){
			//					
			//					userCount = list.size();
			//					for(RiskOrderStruc struc : list){
			//						
			//						if(null != struc.getRiskOrderDetailStrucs()){
			//							orderCount += struc.getRiskOrderDetailStrucs().size();
			//							
			//							for(RiskOrderDetailStruc detailStruc : struc.getRiskOrderDetailStrucs()){
			//								if( detailStruc.getStatus() == 1){
			//									pass++;
			//								}
			//								if(detailStruc.getStatus() == 2){
			//									unPass++;
			//								}
			//							}
			//						}
			//						
			//					}
			//				}
			//				
			//				view.addObject("userCount", userCount);
			//				view.addObject("orderCount", orderCount);
			//				view.addObject("pass", pass);
			//				view.addObject("unPass", unPass);
			//			}

			int orderCount = 0;
			int pass = 0;

			if (null != response.getBody() && null != response.getBody().getResult()) {
				detailResponse = response.getBody().getResult();
				List<RiskOrderStruc> list = detailResponse.getRiskOrderList();
				List<RiskOrderStruc> auditlist = detailResponse.getRiskOrderAuditedList();
				if (!list.isEmpty()) {
					orderCount += list.size();
				}
				if (!auditlist.isEmpty()) {
					orderCount += auditlist.size();
					pass += auditlist.size();
				}
			}

			view.addObject("orderCount", orderCount);
			view.addObject("pass", pass);
			ResultPager rp = response.getBody().getPager();

			view.addObject("page", PageUtils.getPageForView(page, rp));
			view.addObject("lotteryId", lotteryId);
			view.addObject("issueCode", issueCode);
			view.addObject("detail", detailResponse);
			view.addObject("userInfoUrl", userInfoUrl);
			view.addObject("freezeUserUrl", freezeUserUrl);
			view.addObject("issue", info.getBody().getResult());

		} catch (Exception e) {
			logger.error("queryLotteryIssueWarnDetail error:", e);
			throw e;
		}
		return view;
	}

}
