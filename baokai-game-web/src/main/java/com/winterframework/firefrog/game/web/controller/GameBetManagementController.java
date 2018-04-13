package com.winterframework.firefrog.game.web.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.winterframework.firefrog.common.httpjsonclient.IHttpJsonClient;
import com.winterframework.firefrog.common.util.DateUtils;
import com.winterframework.firefrog.game.util.SuperPairUtil;
import com.winterframework.firefrog.game.web.dto.AjaxResponse;
import com.winterframework.firefrog.game.web.dto.CancelOrderRequest;
import com.winterframework.firefrog.game.web.dto.DTOConvertor4Web;
import com.winterframework.firefrog.game.web.dto.GameIssueQueryRequest;
import com.winterframework.firefrog.game.web.dto.GameIssueQueryResponse;
import com.winterframework.firefrog.game.web.dto.GameOrderDetailQueryRequest;
import com.winterframework.firefrog.game.web.dto.GameOrderDetailQueryResponse;
import com.winterframework.firefrog.game.web.dto.GameOrderDetailQueryResponseDTO;
import com.winterframework.firefrog.game.web.dto.GameOrderQueryRequest;
import com.winterframework.firefrog.game.web.dto.GameOrderQueryResponse;
import com.winterframework.firefrog.game.web.dto.SlipsStrucDTO;
import com.winterframework.firefrog.game.web.util.PageUtils;
import com.winterframework.modules.page.PageRequest;
import com.winterframework.modules.spring.exetend.PropertyConfig;
import com.winterframework.modules.web.jsonresult.Pager;
import com.winterframework.modules.web.jsonresult.Response;
import com.winterframework.modules.web.jsonresult.ResultPager;
import com.winterframework.modules.web.util.RequestContext;

/** 
* @ClassName: GameBetManagementController 
* @Description:投注查询页面controller 同投注管理页面
* @author david 
* @date 2013-9-25 上午10:54:34 
*  
*/
@Controller("gameBetManagementController")
@RequestMapping(value = "/gameUserCenter")
public class GameBetManagementController {

	private Logger logger = LoggerFactory.getLogger(GameBetManagementController.class);

	@Resource(name = "httpJsonClientImpl")
	private IHttpJsonClient httpClient;

	@PropertyConfig(value = "url.connect")
	private String serverPath;

	@PropertyConfig(value = "url.game.queryOrders")
	private String queryOrders;

	@PropertyConfig(value = "url.game.queryOrderDetail")
	private String queryOrderDetail;

	@PropertyConfig(value = "url.game.cancelOrder")
	private String cancelOrder;

	@PropertyConfig(value = "url.game.queryGameIssue")
	private String queryGameIssue;

	/** 
	* @Title: queryOrdersEnter 
	* @Description:投注查询界面入口action 
	* @return
	 * @throws Exception 
	*/
	@RequestMapping(value = "/queryOrdersManagementEnter")
	public String queryOrdersEnter(Model model) throws Exception {
		logger.info("queryOrders start");
		Long userId = RequestContext.getCurrUser().getId();
		Response<GameOrderQueryResponse> response = new Response<GameOrderQueryResponse>();
		PageRequest<GameOrderQueryRequest> page = new PageRequest<GameOrderQueryRequest>();

		Pager pager = new Pager();
		pager.setStartNo(0);
		pager.setEndNo(10);
		GameOrderQueryRequest request = new GameOrderQueryRequest();
		request.setEndTime(DateUtils.getEndTimeOfCurrentDate().getTime());
		request.setStartTime(DateUtils.getStartTimeOfDate(DateUtils.addDays(DateUtils.currentDate(), -6)).getTime());
		request.setStatus(0);
		request.setLotteryId(0L);
		request.setParentType(0);

		page.setSearchDo(request);

		try {
			response = httpClient.invokeHttp(serverPath + queryOrders, request, pager, userId, null,
					GameOrderQueryResponse.class);
			logger.info("queryOrders end");
		} catch (Exception e) {
			logger.error("queryOrders is error.", e);
			throw e;
		}
		ResultPager rp = response.getBody().getPager();
		model.addAttribute("orders",
				DTOConvertor4Web.orderStrucs2OrderStrucsDTO(response.getBody().getResult().getOrdersStruc()));
		model.addAttribute("request", request);
		model.addAttribute("page", PageUtils.getPageForView(page, rp));
		if (!response.getBody().getResult().getOrdersStruc().isEmpty()
				&& response.getBody().getResult().getOrdersStruc().size() != 0) {
			return "/userCenter/proxy-customer-management-bets";
		} else {
			return "/userCenter/proxy-customer-management-bets-nodata";
		}
	}

	/**
	 * 
	* @Title: queryOrders 
	* @Description: 查询订单
	* @param request
	* @return
	* @throws Exception
	 */
	@RequestMapping(value = "/queryOrdersManagement")
	public String queryOrders(@ModelAttribute("page") PageRequest<GameOrderQueryRequest> page,
			@ModelAttribute("request") GameOrderQueryRequest request, Model model, Long userId) throws Exception {
		logger.info("queryOrders start");
		if (userId == null) {
			userId = RequestContext.getCurrUser().getId();
		}
		Response<GameOrderQueryResponse> response = new Response<GameOrderQueryResponse>();
		try {
			request.setSysUserId(RequestContext.getCurrUser().getId());
			response = httpClient.invokeHttp(serverPath + queryOrders, request, PageUtils.getPager(page), userId, null,
					GameOrderQueryResponse.class);
			logger.info("queryOrders end");
		} catch (Exception e) {
			logger.error("queryOrders is error.", e);
			throw e;
		}
		ResultPager rp = response.getBody().getPager();
		model.addAttribute("orders",
				DTOConvertor4Web.orderStrucs2OrderStrucsDTO(response.getBody().getResult().getOrdersStruc()));
		model.addAttribute("request", request);
		model.addAttribute("userId", userId);
		model.addAttribute("page", PageUtils.getPageForView(page, rp));
		if (!response.getBody().getResult().getOrdersStruc().isEmpty()
				&& response.getBody().getResult().getOrdersStruc().size() != 0) {
			return "/userCenter/proxy-customer-management-bets";
		} else {
			return "/userCenter/proxy-customer-management-bets-nodata";
		}

	}

	/** 
	* @Title: queryOrderDetail 
	* @Description: 查询订单详情
	* @param userId
	* @param request
	* @param model
	* @return
	* @throws Exception
	*/
	@RequestMapping(value = "/queryOrderDetailManagement")
	public String queryOrderDetail(@ModelAttribute("request") GameOrderDetailQueryRequest request, Model model,
			Long userId) throws Exception {
		logger.info("queryOrderDetail start");
		Response<GameOrderDetailQueryResponse> response = new Response<GameOrderDetailQueryResponse>();
		Response<GameIssueQueryResponse> responseGameIssue = new Response<GameIssueQueryResponse>();
		try {
			request.setSysUserId(RequestContext.getCurrUser().getId());
			response = httpClient.invokeHttp(serverPath + queryOrderDetail, request, userId, null,
					GameOrderDetailQueryResponse.class);
			logger.info("queryOrderDetail end");
		} catch (Exception e) {
			logger.error("queryOrderDetail is error.", e);
			throw e;
		}
		logger.info("queryGameIssue start");
		GameIssueQueryRequest gameIssueQueryRequest = new GameIssueQueryRequest();
		if (response.getBody() != null && response.getBody().getResult() != null
				&& response.getBody().getResult().getOrdersStruc() != null) {
			gameIssueQueryRequest.setLotteryId(response.getBody().getResult().getOrdersStruc().getLotteryid());
			gameIssueQueryRequest.setIssueCode(response.getBody().getResult().getOrdersStruc().getIssueCode());
		}
		try {
			responseGameIssue = httpClient.invokeHttp(serverPath + queryGameIssue, gameIssueQueryRequest,
					GameIssueQueryResponse.class);
			logger.info("queryGameIssue end");
		} catch (Exception e) {
			logger.error("queryGameIssue is error.", e);
			throw e;
		}
		if (response.getBody() != null && response.getBody().getResult() != null) {
			GameOrderDetailQueryResponseDTO dto = DTOConvertor4Web
					.gameOrderDetailQueryResponse2GameOrderDetailQueryResponseDTO(response.getBody().getResult());
			String resultNumber = dto.getOrdersStruc().getNumberRecord();
			if (dto.getOrdersStruc().getLotteryid() == 99201 && resultNumber != null) {
				String[] numberStr = resultNumber.split(",");
				List<Integer> numberList = new ArrayList<Integer>();
				for (String str : numberStr) {
					numberList.add(Integer.valueOf(str));
				}
				dto.getOrdersStruc()
						.setNumberRecord(resultNumber + " (" + DTOConvertor4Web.getKl8Tip(numberList) + ")");
			}
			if(dto.getSlipsStruc()!=null){
				for(SlipsStrucDTO dtoo:dto.getSlipsStruc()){
					if(Integer.valueOf("2").equals(dtoo.getAwardMode())){
						model.addAttribute("awardRetStatus",1);
						//break;
					}
					
					String betTypeCode=dtoo.getGameGroupCode()+"_"+dtoo.getGameSetCode()+"_"+dtoo.getBetMethodCode();
					if(GameBetQueryController.isZuxuan(betTypeCode)){
						dtoo.setGroupAward(0L);
						dtoo.setGroupAwardDown(0L);
						dtoo.setRetAward(0L);
						//dtoo.setRetPoint(0L);
					}else{
						dtoo.setRetAward(formatLong(dtoo.getRetAward()));
					}
					dtoo.setAward(formatLong(dtoo.getAward()));
				}
				
			}
			if (RequestContext.getCurrUser().getId() != request.getUserId()){
				dto.getOrdersStruc().setCanCancel(false);
			}
			dto.getOrdersStruc().setTotwin(formatLong(dto.getOrdersStruc().getTotwin()));
			model.addAttribute("orderDetail", dto);
			model.addAttribute("request", request);
			if (responseGameIssue.getBody() == null) {
				model.addAttribute("gameIssue", new GameIssueQueryResponse());
			} else {
				model.addAttribute("gameIssue", responseGameIssue.getBody().getResult());
			}
		} else {
			return this.queryOrders(new PageRequest<GameOrderQueryRequest>(), new GameOrderQueryRequest(), model,
					userId);
		}
		return "/userCenter/proxy-customer-management-bets-info";
	}

	private Long formatLong(Long aaa){ 
		if(aaa==null) return null;
		return NumberUtils.toLong(String.valueOf(aaa/100)+"00"); 
	}
	/** 
	* @Title: cancelOrder 
	* @Description: 撤销订单
	* @param request
	* @param model
	* @throws Exception
	*/
	@RequestMapping(value = "/cancelOrderManagement")
	@ResponseBody
	public Object cancelOrder(@ModelAttribute("request") CancelOrderRequest request, Model model) throws Exception {
		logger.info("cancelOrder start");
		Long userId = RequestContext.getCurrUser().getId();
		request.setCancelTime(new Date().getTime());
		AjaxResponse resp = new AjaxResponse();
		@SuppressWarnings("rawtypes")
		Response response = new Response();
		try {
			response = httpClient.invokeHttp(serverPath + cancelOrder, request, userId, null, Object.class);
			logger.info("cancelOrder end");
			if (response.getHead().getStatus() == 0) {
				resp.setStatus(1l);
				resp.setMessage("success");
			} else if (response.getHead().getStatus() == 202007L) {
				resp.setStatus(2l);
				resp.setMessage("方案撤销失败，订单状态错误!");
			} else if (response.getHead().getStatus() == 201005L) {
				resp.setStatus(2l);
				resp.setMessage("方案撤销失败，该奖期已截止销售!");
			} else if (response.getHead().getStatus() == 999999L) {
				resp.setStatus(2l);
				resp.setMessage("方案撤销失败，该用户无权限撤销其他人的订单!");
			} else {
				resp.setStatus(2l);
				resp.setMessage("方案撤销失败，请检查网络或重试！");
			}
		} catch (Exception e) {
			logger.error("cancelOrder is error.", e);
			resp.setStatus(2l);
			resp.setMessage("cancelOrder errors");
			return resp;
		}
		return resp;
	}

}
