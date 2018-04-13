package com.winterframework.firefrog.game.web.controller;

import java.util.ArrayList;
import java.util.Date;
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

import com.winterframework.firefrog.common.httpjsonclient.IHttpJsonClient;
import com.winterframework.firefrog.game.dto.UserCheckAgentRequest;
import com.winterframework.firefrog.game.dto.UserCheckStatusResult;
import com.winterframework.firefrog.game.web.dto.ChainUserStruc;
import com.winterframework.firefrog.game.web.dto.CurrentUserCenterReportReponse;
import com.winterframework.firefrog.game.web.dto.CurrentUserCenterReportRequest;
import com.winterframework.firefrog.game.web.dto.SubUserReportRequest;
import com.winterframework.firefrog.game.web.dto.SubUserReportResponse;
import com.winterframework.firefrog.game.web.dto.UserCenterReportComplexReponse;
import com.winterframework.firefrog.game.web.dto.UserCenterReportComplexRequest;
import com.winterframework.firefrog.game.web.dto.UserCenterReportStruc;
import com.winterframework.firefrog.game.web.util.PageUtils;
import com.winterframework.modules.page.Page;
import com.winterframework.modules.page.PageRequest;
import com.winterframework.modules.spring.exetend.PropertyConfig;
import com.winterframework.modules.web.jsonresult.Response;
import com.winterframework.modules.web.jsonresult.ResultPager;
import com.winterframework.modules.web.util.RequestContext;

/** 
* @ClassName: GameUserCenterController 
* @Description: 用户中心报表controller 
* @author david 
* @date 2013-9-24 下午7:23:24 
*  
*/
@Controller("gameUserCenterController")
@RequestMapping(value = "/gameUserCenter")
public class GameUserCenterController {

	private Logger logger = LoggerFactory.getLogger(GameUserCenterController.class);
	@Resource(name = "httpJsonClientImpl")
	private IHttpJsonClient httpClient;

	@PropertyConfig(value = "url.connect")
	private String serverPath;
	
	@PropertyConfig(value = "pt.url")
	private String ptURL;

	@PropertyConfig(value = "url.user.checkAgent")
	private String ptCheckURL;

	@PropertyConfig(value = "url.game.queryCurrentUserReport")
	private String queryCurrentUserReport;

	@PropertyConfig(value = "url.game.querySubUserReport")
	private String querySubUserReport;

	@PropertyConfig(value = "url.game.queryUserReportByComplexCondition")
	private String queryUserReportByComplexCondition;
	
	@PropertyConfig(value = "url.wg.api.server.path")
	private String wgURL;
	
	@PropertyConfig(value = "url.wanguo.checkUser")
	private String wgCheckURL;
	

	/**
	 * 
	* @Title: queryCurrentUserReport 
	* @Description: 查询当前用户报表信息
	* @param request
	* @return
	* @throws Exception
	 */
	@RequestMapping(value = "/queryCurrentUserReport")
	public String queryCurrentUserReport(Model model) throws Exception {
		logger.info("queryCurrentUserReport start");
		Long userId = RequestContext.getCurrUser().getId();
		String account = RequestContext.getCurrUser().getUserName();
		CurrentUserCenterReportRequest queryDTO = new CurrentUserCenterReportRequest();
		Response<CurrentUserCenterReportReponse> response = new Response<CurrentUserCenterReportReponse>();
		try {
			queryDTO.setUserId(userId);
			response = httpClient.invokeHttp(serverPath + queryCurrentUserReport, queryDTO, userId, account,
					CurrentUserCenterReportReponse.class);
			logger.info("queryCurrentUserReport end");
		} catch (Exception e) {
			logger.error("queryCurrentUserReport is error.", e);
			throw e;
		}
		
		Response<UserCheckStatusResult> responsePtStatus = new Response<UserCheckStatusResult>();
		Response<UserCheckStatusResult> responseWgStatus = new Response<UserCheckStatusResult>();
		try {
			UserCheckAgentRequest  ptRequest = new UserCheckAgentRequest();
			ptRequest.setUserid(userId);
	
			responsePtStatus = httpClient.invokeHttp(ptURL + ptCheckURL, ptRequest, userId, null, UserCheckStatusResult.class);
			responseWgStatus = httpClient.invokeHttp(wgURL + wgCheckURL, ptRequest, userId, null, UserCheckStatusResult.class);
			logger.info("UserCheckStatusResult end");
		} catch (Exception e) {
			logger.error("UserCheckStatusResult is error.", e);
		}
		try{
			if (responsePtStatus != null &&  responsePtStatus.getBody() != null &&  responsePtStatus.getBody().getResult().getStatus() == 1){
				model.addAttribute("ptStatus", responsePtStatus.getBody().getResult().getStatus());
			}
			if (responseWgStatus != null &&  responseWgStatus.getBody() != null &&  responseWgStatus.getBody().getResult().getStatus() == 1){
				model.addAttribute("wgStatus", responseWgStatus.getBody().getResult().getStatus());
			}
			
			model.addAttribute("query", queryDTO);
			model.addAttribute("cucrr", response.getBody().getResult());
			if (response.getBody().getResult() == null
					|| response.getBody().getResult().getUserCenterReportStrucs() == null) {
				return "/userCenter/userReport-nodata";
			} else {
				return "/userCenter/currentUserReport";
			}
		}catch(Exception e){
			logger.error(" return userReport nodata when result is null.", e);
			return "/userCenter/userReport-nodata";
		}

	}

	/** 
	* @Title: querySubUserReport 
	* @Description: 查看下级报表 
	* @param userId
	* @param model
	* @return
	* @throws Exception
	*/
	@RequestMapping(value = "/querySubUserReport")
	public String querySubUserReport(@RequestParam("userId") Long userId,@RequestParam(required = false)  String account,
			@RequestParam("lotteryId") Long lotteryId, @RequestParam("betTypeCode") String betTypeCode, @RequestParam("queryTime") Long queryTime,
			@ModelAttribute("page") PageRequest<SubUserReportRequest> page, Model model,UserCenterReportComplexRequest request) throws Exception {
		SubUserReportRequest queryDTO = new SubUserReportRequest();
		Response<SubUserReportResponse> response = new Response<SubUserReportResponse>();
		request.setLotteryId(lotteryId);
		request.setQueryTime(queryTime);
		request.setAccount(account);
		//2016.05.10 DavidWu Changed #7332 4.0前台代理报表查询 新增查询功能 玩法 分類 Start
		request.setBetTypeCode(betTypeCode);
		//2016.05.10 DavidWu Changed #7332 4.0前台代理报表查询 新增查询功能 玩法 分類 End
		try {
			Long currentId = RequestContext.getCurrUser().getId();
			queryDTO.setCurUserId(currentId);
			queryDTO.setUserId(userId);
			queryDTO.setQueryTime(queryTime);
			queryDTO.setLotteryId(lotteryId);
			queryDTO.setAccount(account);
			//2016.05.10 DavidWu Changed #7332 4.0前台代理报表查询 新增查询功能 玩法 分類 Start
			queryDTO.setBetTypeCode(betTypeCode);
			//2016.05.10 DavidWu Changed #7332 4.0前台代理报表查询 新增查询功能 玩法 分類 End
			response = httpClient.invokeHttp(serverPath + querySubUserReport, queryDTO, PageUtils.getPager(page),
					SubUserReportResponse.class);
			logger.info("querySubUserReport end");
		} catch (Exception e) {
			logger.error("querySubUserReport is error.", e);
			throw e;
		}
		List<ChainUserStruc> chainUser = response.getBody().getResult().getChainUsers();
		List<ChainUserStruc> chainUserProcess = new ArrayList<ChainUserStruc>();
		boolean addNext = false;
		for (ChainUserStruc chainUserStruc : chainUser) {
			if (chainUserStruc.getUserId().longValue() == RequestContext.getCurrUser().getId() || addNext) {
				chainUserProcess.add(chainUserStruc);
				addNext = true;
			}
		}
		ResultPager rp = response.getBody().getPager();
		model.addAttribute("cucrr", response.getBody().getResult());
		model.addAttribute("chainUserProcess", chainUserProcess);
		model.addAttribute("chainUserProcessIndex", chainUserProcess.size()-1);
		model.addAttribute("query", queryDTO);
		model.addAttribute("request", request);
		model.addAttribute("page", PageUtils.getPageForView(page, rp));
		return "/userCenter/subUserReport";
	}
	
	/** 
	* @Title: querySubUserReportTestajax 
	* @Description: 查看下级报表 
	* @param userId
	* @param model
	* @return
	* @throws Exception
	*/
	@RequestMapping(value = "/querySubUserReportAjax")
	@ResponseBody
	public List<UserCenterReportStruc> querySubUserReportAjax(@RequestParam("userId") Long userId,@RequestParam(required = false)  String account,
			@RequestParam("lotteryId") Long lotteryId, @RequestParam("betTypeCode") String betTypeCode, @RequestParam("queryTime") Long queryTime,
			@RequestParam("pageNo") Integer pageNo,@RequestParam("pageSize") Integer pageSize, UserCenterReportComplexRequest request) throws Exception {
		logger.info("querySubUserReport start");
		SubUserReportRequest queryDTO = new SubUserReportRequest();
		PageRequest<SubUserReportRequest> page = new PageRequest<SubUserReportRequest>();
		Response<SubUserReportResponse> response = new Response<SubUserReportResponse>();
		request.setLotteryId(lotteryId);
		request.setQueryTime(queryTime);
		request.setAccount(account);
		//2016.05.10 DavidWu Changed #7332 4.0前台代理报表查询 新增查询功能 玩法 分類 Start
		request.setBetTypeCode(betTypeCode);
		//2016.05.10 DavidWu Changed #7332 4.0前台代理报表查询 新增查询功能 玩法 分類 End
		page.setPageNo(pageNo);
		page.setPageSize(pageSize);
		try {
			Long currentId = RequestContext.getCurrUser().getId();
			queryDTO.setCurUserId(currentId);
			queryDTO.setUserId(userId);
			queryDTO.setQueryTime(queryTime);
			queryDTO.setLotteryId(lotteryId);
			queryDTO.setAccount(account);
			//2016.05.10 DavidWu Changed #7332 4.0前台代理报表查询 新增查询功能 玩法 分類 Start
			queryDTO.setBetTypeCode(betTypeCode);
			//2016.05.10 DavidWu Changed #7332 4.0前台代理报表查询 新增查询功能 玩法 分類 End
			response = httpClient.invokeHttp(serverPath + querySubUserReport, queryDTO, PageUtils.getPager(page),
					SubUserReportResponse.class);
			logger.info("querySubUserReport end");
		} catch (Exception e) {
			logger.error("querySubUserReport is error.", e);
			throw e;
		}
		return response.getBody().getResult().getUserCenterReportStrucs();
	}
	
	@RequestMapping(value = "/queryUserReportByComplexCondition")
	public String queryUserReport(@ModelAttribute("page") PageRequest<SubUserReportRequest> page,
			UserCenterReportComplexRequest request, Model model) throws Exception {
		
		Long userId = RequestContext.getCurrUser().getId();//session
		String account = RequestContext.getCurrUser().getUserName();//session
		Long lotteryId = request.getLotteryId();
		Long queryTime = request.getQueryTime();
		Date dt = new Date();
		long milsec = dt.getTime()-86400000;
		String accountInput= request.getAccount();//輸入帳戶
		String betTypeCode = request.getBetTypeCode(); 
		String crowdId = request.getCrowdId();
		String groupId = request.getGroupId();
		String setId = request.getSetId();
		String methodId = request.getMethodId();
		
		logger.info("=userId:"+userId);
		logger.info("=account:"+account);
		logger.info("=lotteryId:"+lotteryId);
		logger.info("=queryTime:"+queryTime);
		logger.info("=milsec:"+milsec);
		logger.info("=accountInput:"+accountInput);
		logger.info("=betTypeCode:"+betTypeCode);
		logger.info("=crowdId:"+crowdId);
		logger.info("=groupId:"+groupId);
		logger.info("=setId:"+setId);
		logger.info("=methodId:"+methodId);
		
//		if(("".equals(accountInput) && lotteryId == 0 && queryTime== null) || 
//			(accountInput.equals(account) && lotteryId == 0 && queryTime== null) || 
//			("".equals(accountInput) && lotteryId == 0 && queryTime >= milsec) ||
//			(accountInput.equals(account) && lotteryId == 0 && queryTime >= milsec)){
//			
//			logger.info("account = ''"); 
//			
//			CurrentUserCenterReportRequest queryDTO = new CurrentUserCenterReportRequest();
//			Response<CurrentUserCenterReportReponse> response = new Response<CurrentUserCenterReportReponse>();
//			try {
//				queryDTO.setUserId(userId);
//				response = httpClient.invokeHttp(serverPath + queryCurrentUserReport, queryDTO, userId, accountInput,
//						CurrentUserCenterReportReponse.class);
//				logger.info("queryCurrentUserReport end");
//			} catch (Exception e) {
//				logger.error("queryCurrentUserReport is error.", e);
//				throw e;
//			}
//			
//			Response<UserCheckStatusResult> responsePtStatus = new Response<UserCheckStatusResult>();
//			try {
//				UserCheckAgentRequest  ptRequest = new UserCheckAgentRequest();
//				ptRequest.setUserid(userId);
//		
//				responsePtStatus = httpClient.invokeHttp(ptURL + ptCheckURL, ptRequest, userId, null, UserCheckStatusResult.class);
//				logger.info("UserCheckStatusResult end");
//			} catch (Exception e) {
//				logger.error("UserCheckStatusResult is error.", e);
//			}
//			if (responsePtStatus != null &&  responsePtStatus.getBody() != null &&  responsePtStatus.getBody().getResult().getStatus() == 1){
//				model.addAttribute("ptStatus", responsePtStatus.getBody().getResult().getStatus());
//			}
//						
//			model.addAttribute("cucrr", response.getBody().getResult());
//			model.addAttribute("query", queryDTO);
//			if (response.getBody().getResult() == null
//					|| response.getBody().getResult().getUserCenterReportStrucs() == null) {
//				return "/userCenter/userReport-nodata";
//			} else {
//				return "/userCenter/currentUserReport";
//			}
//		}else{
			logger.info("account != ''"); 
			
			//request.setAccount(accountInput);
			Response<Page<UserCenterReportStruc>> response = new Response<Page<UserCenterReportStruc>>();
			try {
				response = httpClient.invokeHttp(serverPath + queryUserReportByComplexCondition, request,
						PageUtils.getPager(page), userId, null, UserCenterReportComplexReponse.class);
				logger.info("queryUserReportByComplexCondition end");
			} catch (Exception e) {
				logger.error("queryUserReportByComplexCondition is error.", e);
				throw e;
			}
			Response<UserCheckStatusResult> responsePtStatus = new Response<UserCheckStatusResult>();
			try {
				UserCheckAgentRequest  ptRequest = new UserCheckAgentRequest();
				ptRequest.setUserid(userId);
		
				responsePtStatus = httpClient.invokeHttp(ptURL + ptCheckURL, ptRequest, userId, null, UserCheckStatusResult.class);
				logger.info("UserCheckStatusResult end");
			} catch (Exception e) {
				logger.error("UserCheckStatusResult is error.", e);
			}
			
	
			ResultPager rp = response.getBody().getPager();
	
			model.addAttribute("cucrr", response.getBody().getResult());
			model.addAttribute("request", request);
			model.addAttribute("page", PageUtils.getPageForView(page, rp));
			return "/userCenter/userReport";
//		}

	}

}
