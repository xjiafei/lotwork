package com.winterframework.firefrog.game.web.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.type.TypeReference;
import com.winterframework.firefrog.common.httpjsonclient.IHttpJsonClient;
import com.winterframework.firefrog.common.util.DateUtils;
import com.winterframework.firefrog.game.dao.vo.GamePoint;
import com.winterframework.firefrog.game.dto.UserCheckAgentRequest;
import com.winterframework.firefrog.game.dto.UserCheckStatusResult;
import com.winterframework.firefrog.game.web.WinNum.IWinNumSign;
import com.winterframework.firefrog.game.web.WinNum.WinNumSignFactory;
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
import com.winterframework.firefrog.game.web.dto.GetGameIssueStrucsRequest;
import com.winterframework.firefrog.game.web.dto.IssueStruc;
import com.winterframework.firefrog.game.web.dto.OrdersStrucDTO;
import com.winterframework.firefrog.game.web.dto.SlipsStrucDTO;
import com.winterframework.firefrog.game.web.dto.UserInfoRequest;
import com.winterframework.firefrog.game.web.util.PageUtils;
import com.winterframework.modules.page.PageRequest;
import com.winterframework.modules.spring.exetend.PropertyConfig;
import com.winterframework.modules.web.jsonresult.Pager;
import com.winterframework.modules.web.jsonresult.Response;
import com.winterframework.modules.web.jsonresult.ResultPager;
import com.winterframework.modules.web.util.RequestContext;

/** 
 * 投注管理页面controller
 * @author david 
 * @date 2013-9-25 上午10:54:34 
 */
@Controller("gameBetQueryController")
@RequestMapping(value = "/gameUserCenter")
public class GameBetQueryController {

	private Logger logger = LoggerFactory.getLogger(GameBetQueryController.class);

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

	@PropertyConfig(value = "url.game.getGameIssuesByLotteryId")
	private String getGameIssuesByLotteryId;

	@PropertyConfig(value = "pt.url")
	private String ptURL;

	@PropertyConfig(value = "url.user.checkUser")
	private String ptCheckUserURL;
	@PropertyConfig(value = "url.game.getUserInfo")
	private String queryUserInfoUrl;
	
	/** 
	* @Title: queryOrdersEnter 
	* @Description:投注查询界面入口action 
	* @return
	*/
	@RequestMapping(value = "/queryOrdersEnter")
	public String queryOrdersEnter(Model model, Long userId) throws Exception {
		//默认显示7天内所有彩种的全部订单
		logger.info("queryOrders start");
		if (userId == null)
			userId = RequestContext.getCurrUser().getId();
		Response<GameOrderQueryResponse> response = new Response<GameOrderQueryResponse>();
		PageRequest<GameOrderQueryRequest> page = new PageRequest<GameOrderQueryRequest>();

		Pager pager = new Pager();
		pager.setStartNo(0);
		pager.setEndNo(10);
		GameOrderQueryRequest request = new GameOrderQueryRequest();
		request.setEndTime(DateUtils.getEndTimeOfCurrentDate().getTime());
		request.setStartTime(DateUtils.getStartTimeOfDate(DateUtils.addDays(DateUtils.currentDate(), -6)).getTime());
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
		List<OrdersStrucDTO> dtoList = DTOConvertor4Web.orderStrucs2OrderStrucsDTO(response.getBody().getResult().getOrdersStruc());
		
		for(OrdersStrucDTO ordersStruc:dtoList){
			ordersStruc.setTotwin(formatLong(ordersStruc.getTotwin()));
		}		
		model.addAttribute("orders",
				dtoList);
		model.addAttribute("request", request);
		model.addAttribute("page", PageUtils.getPageForView(page, rp));
		model.addAttribute("time", 6);
		model.addAttribute("orderId", getPlayName(dtoList, userId));
		Response<UserCheckStatusResult> responsePtStatus = new Response<UserCheckStatusResult>();
		try {
			UserCheckAgentRequest  ptRequest = new UserCheckAgentRequest();
			ptRequest.setUserid(userId);
	
			responsePtStatus = httpClient.invokeHttp(ptURL + ptCheckUserURL, ptRequest, userId, null, UserCheckStatusResult.class);
			logger.info("UserCheckStatusResult end");
		} catch (Exception e) {
			logger.error("UserCheckStatusResult is error.", e);
		}
		if (responsePtStatus != null && responsePtStatus.getBody() != null && responsePtStatus.getBody().getResult().getStatus() == 1){
			model.addAttribute("ptStatus", responsePtStatus.getBody().getResult().getStatus());
			
		}
	
		if (response.getBody().getResult().getOrdersStruc() != null
				&& response.getBody().getResult().getOrdersStruc().size() != 0) {
			
			return "/userCenter/bettingRecords";
		} else {
			return "/userCenter/bettingRecords-nodata";
		}

	}
	
	/**
	 * requirement #435 建议在“投注记录"、“追号记录”列表中增加一列“玩法”，不然则需点击“查看”按钮才能查询到具体玩法
	 * @Title: getPlayName 
	 * @Description:取得遊戲玩法
	 * @return
	 */
	private List<Object> getPlayName(List<OrdersStrucDTO> dtoList,Long userId) {
				//add by Hassan 20150610
				//requirement #435 建议在“投注记录"、“追号记录”列表中增加一列“玩法”，不然则需点击“查看”按钮才能查询到具体玩法。
				GameOrderDetailQueryRequest resquest = new GameOrderDetailQueryRequest();
				Response<GameOrderDetailQueryResponse> response = new Response<GameOrderDetailQueryResponse>();
				
				List<Object> tmpList = new ArrayList<Object>();
				Set<String> reloadSet = new HashSet<String>();
				for(OrdersStrucDTO m : dtoList){
					Map<String, String> idMapper = new HashMap<String, String>();
					String playString = "";
					try {
						
						resquest.setOrderId(m.getOrderId());
						response = httpClient.invokeHttp(serverPath + queryOrderDetail, resquest, userId, null,
								GameOrderDetailQueryResponse.class);
						if (response.getBody() != null && response.getBody().getResult() != null
								&& response.getBody().getResult().getOrdersStruc() != null) {
							if (response.getBody().getResult().getOrdersStruc().getUserid().longValue() != userId) {
								idMapper.put("orderId", m.getOrderId().toString());
								idMapper.put("playName", "");
								tmpList.add(idMapper);
								continue;
							}
						}
						GameOrderDetailQueryResponseDTO dto = DTOConvertor4Web
								.gameOrderDetailQueryResponse2GameOrderDetailQueryResponseDTO(response.getBody().getResult());
						
						for(SlipsStrucDTO d : dto.getSlipsStruc()){
							if(reloadSet.contains(d.getGamePlayName())){
								continue;
							}else{
								reloadSet.add(d.getGamePlayName());
								playString += d.getGamePlayName()+", ";
							}
						}
						reloadSet.clear();
						idMapper.put("playName", playString.substring(0, playString.length()-2));
						idMapper.put("orderId", m.getOrderId().toString());
						tmpList.add(idMapper);
					} catch (Exception e) {
						// 
						logger.error("getPlayName is error.", e);
						idMapper.put("orderId", m.getOrderId().toString());
						idMapper.put("playName", "");
						tmpList.add(idMapper);
					}						
				}

		return tmpList;
	}

	@RequestMapping(value = "/getGameIssuesByLotteryId")
	@ResponseBody
	public Object getGameIssuesBylotteryId(@ModelAttribute("request") GetGameIssueStrucsRequest request)
			throws Exception {
		logger.info("getGameIssuesBylotteryId start");
		Response<List<IssueStruc>> response = new Response<List<IssueStruc>>();
		try {
			response = httpClient.invokeHttp(serverPath + getGameIssuesByLotteryId, request,
					new TypeReference<Response<List<IssueStruc>>>() {
					});
			logger.info("getGameIssuesBylotteryId end");
		} catch (Exception e) {
			logger.error("getGameIssuesBylotteryId is error.", e);
			throw e;
		}
		//加只有issuecode的简化结构体返回
		return response.getBody().getResult();
	}

	/**
	 * 
	* @Title: queryOrders 
	* @Description: 查询订单
	* @param request
	* @return
	* @throws Exception
	 */
	@RequestMapping(value = "/queryOrders")
	public String queryOrders(@ModelAttribute("page") PageRequest<GameOrderQueryRequest> page, Integer time,
			@ModelAttribute("request") GameOrderQueryRequest request, Model model, Long userId) throws Exception {
		logger.info("queryOrders start");
		if (userId == null) {
			userId = RequestContext.getCurrUser().getId();
		} else {
			model.addAttribute("userId", userId);
		}
		Response<GameOrderQueryResponse> response = new Response<GameOrderQueryResponse>();
		try {
			response = httpClient.invokeHttp(serverPath + queryOrders, request, PageUtils.getPager(page), userId, null,
					GameOrderQueryResponse.class);
			logger.info("queryOrders end");
		} catch (Exception e) {
			logger.error("queryOrders is error.", e);
			throw e;
		}

		Response<UserCheckStatusResult> responsePtStatus = new Response<UserCheckStatusResult>();
		try {
			UserCheckAgentRequest  ptRequest = new UserCheckAgentRequest();
			ptRequest.setUserid(userId);
	
			responsePtStatus = httpClient.invokeHttp(ptURL + ptCheckUserURL, ptRequest, userId, null, UserCheckStatusResult.class);
			logger.info("UserCheckStatusResult end");
		} catch (Exception e) {
			logger.error("UserCheckStatusResult is error.", e);
		}
		if (responsePtStatus != null && responsePtStatus.getBody() != null && responsePtStatus.getBody().getResult().getStatus() == 1){
			model.addAttribute("ptStatus", responsePtStatus.getBody().getResult().getStatus());
		}
		
		ResultPager rp = response.getBody().getPager();
		List<OrdersStrucDTO> dtoList = DTOConvertor4Web.orderStrucs2OrderStrucsDTO(response.getBody().getResult().getOrdersStruc());
		
		if(null!=dtoList){
			for(OrdersStrucDTO dto:dtoList){
				dto.setTotwin(formatLong(dto.getTotwin()));
			}
		}
		model.addAttribute("orders",
				dtoList);
		model.addAttribute("request", request);
		model.addAttribute("page", PageUtils.getPageForView(page, rp));
		model.addAttribute("time", time);
		model.addAttribute("orderId", getPlayName(dtoList, userId));
		if (response.getBody().getResult().getOrdersStruc() != null
				&& response.getBody().getResult().getOrdersStruc().size() != 0) {
			return "/userCenter/bettingRecords";
		} else {
			return "/userCenter/bettingRecords-nodata";
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
	@RequestMapping(value = "/queryOrderDetail")
	public String queryOrderDetail(@ModelAttribute("request") GameOrderDetailQueryRequest request, Model model)
			throws Exception {
		logger.info("queryOrderDetail start");
		UserInfoRequest userInfoReq=new UserInfoRequest();
		userInfoReq.setUserId(RequestContext.getCurrUser().getId());

		//Response<UserInfoResponse> userInfoRes = httpClient.invokeHttp(serverPath + queryUserInfoUrl, userInfoReq,UserInfoResponse.class);
		//model.addAttribute("awardRetStatus",null==userInfoRes.getBody().getResult().getAwardRetStatus()?1:userInfoRes.getBody().getResult().getAwardRetStatus());
		
		Response<GameOrderDetailQueryResponse> response = new Response<GameOrderDetailQueryResponse>();

		Response<GameIssueQueryResponse> responseGameIssue = new Response<GameIssueQueryResponse>();

		Long userId = request.getUserId() == null ? RequestContext.getCurrUser().getId() : request.getUserId();
		try {
			response = httpClient.invokeHttp(serverPath + queryOrderDetail, request, userId, null,
					GameOrderDetailQueryResponse.class);
			if (response.getBody() != null && response.getBody().getResult() != null
					&& response.getBody().getResult().getOrdersStruc() != null) {
				if (response.getBody().getResult().getOrdersStruc().getUserid().longValue() != userId) {
					return null;
				}
			}
			logger.info("queryOrderDetail end");
		} catch (Exception e) {
			logger.error("queryOrderDetail is error.", e);
			return "/userCenter/bettingRecordDetail";
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
			OrdersStrucDTO orderStruc=dto.getOrdersStruc();
			orderStruc.setTotwin(formatLong(orderStruc.getTotwin()));
			orderStruc.setDiamondMultiple(orderStruc.getDiamondMultiple()/10);
			for(SlipsStrucDTO slipStruc:dto.getSlipsStruc()){
				String betTypeCode=slipStruc.getGameGroupCode()+"_"+slipStruc.getGameSetCode()+"_"+slipStruc.getBetMethodCode();
				if(isZuxuan(betTypeCode)){
					slipStruc.setGroupAwardDown(0L);
					slipStruc.setGroupAward(0L);
					slipStruc.setRetAward(0L);
					//slipStruc.setRetPoint(0L);
				}else{
					slipStruc.setRetAward(formatLong(slipStruc.getRetAward()));
				}
				slipStruc.setAward(formatLong(slipStruc.getAward()+slipStruc.getDiamondWin()));
			}
			model.addAttribute("orderDetail", dto);
			model.addAttribute("request", request);
			model.addAttribute("lotteryName", dto.getOrdersStruc().getLotteryName());
			if (responseGameIssue.getBody() == null) {
				model.addAttribute("gameIssue", new GameIssueQueryResponse());
			} else {
				model.addAttribute("gameIssue", responseGameIssue.getBody().getResult());
			}
			if(dto.getSlipsStruc()!=null){
				for(SlipsStrucDTO dtoo:dto.getSlipsStruc()){
					if(Integer.valueOf("2").equals(dtoo.getAwardMode())){
						model.addAttribute("awardRetStatus",1);
						//break;
					}
				}
				
			}
			//添加中奖号码标红,不为异常状态
			if (dto.getOrdersStruc().getStatus() != 5) {
				try {
					IWinNumSign winNumSign = WinNumSignFactory.getSignNumInstance(dto.getOrdersStruc().getLotteryid(),
							dto.getOrdersStruc().getNumberRecordList());
					winNumSign.signSlips(dto.getSlipsStruc());
					Long lotteryId = dto.getOrdersStruc().getLotteryid();
					if (lotteryId == 99108 || lotteryId == 99109 || lotteryId == 99401) {
						List<String> numberResult = new ArrayList<String>();
						List<SlipsStrucDTO> slips = dto.getSlipsStruc();
						for (SlipsStrucDTO slip : slips) {
							if (slip.getStatus() == 2) {
								if (lotteryId == 99108) {
									numberResult = dto.getOrdersStruc().getNumberRecordList().subList(0, 3);

								} else if (lotteryId == 99109) {
									if (slip.getGameGroupCode() == 30) {
										numberResult = dto.getOrdersStruc().getNumberRecordList().subList(3, 5);
									} else if (slip.getGameGroupCode() == 31) {
										numberResult = dto.getOrdersStruc().getNumberRecordList();
									} else {
										numberResult = dto.getOrdersStruc().getNumberRecordList().subList(0, 3);
									}
								} else {
									numberResult = dto.getOrdersStruc().getNumberRecordList();
								}
								signGamePoint(slip, numberResult);
							}
							
						}

					}
				} catch (Exception e) {
					logger.error("标记中奖号码异常", e);
				}
			}
		}

		if (response.getBody() == null || response.getBody().getResult() == null) {
			//return "redirect:/gameUserCenter/queryOrdersEnter?time=7";
			return this.queryOrdersEnter(model, null);
		}
		return "/userCenter/bettingRecordDetail";
	}
	private Long formatLong(Long aaa){ 
		if(aaa==null) return null;
		return NumberUtils.toLong(String.valueOf(aaa/100)+"00"); 
	}
	public static boolean isZuxuan(String betTypeCode){
		//String abc=".zuxuan.hezhi","zuxuan.baodan","zuxuan.hunhezuxuan";
		if(StringUtils.contains(betTypeCode, "11_33") || StringUtils.contains(betTypeCode, "11_37") || StringUtils.contains(betTypeCode, "11_39")){
			if(StringUtils.contains(betTypeCode, "12") || StringUtils.contains(betTypeCode, "13") || StringUtils.contains(betTypeCode, "33"))
			return true;
		}
		return false;
	}
	
	private void signGamePoint(SlipsStrucDTO slip, List<String> numberResults) {
		List<GamePoint> GamePoints = slip.getGamePoints();
		String numberResultStr = "";
		for (String numberResult : numberResults) {
			numberResultStr += numberResult;
		}
		if (GamePoints != null) {
			for (GamePoint gamePoint : GamePoints) {
				String point = gamePoint.getPoint();
				String pointView = "";
				List<String> results = Arrays.asList(point.split(","));
				for (String result : results) {
					String resultBak = result.replace("+", "");
					if (resultBak.equals(numberResultStr)) {
						pointView = pointView + "<span class='color-red'>" + result + "</span>";
						gamePoint.setIsSignRed(1);
					} else if (resultBak.length() != numberResultStr.length()) {
						if (resultBak.equals(numberResultStr.substring(numberResultStr.length() - resultBak.length(),
								numberResultStr.length()))) {
							pointView = pointView + "<span class='color-red'>" + result + "</span>";
							gamePoint.setIsSignRed(1);
						} else {
							pointView = pointView + result;
						}
					} else {
						pointView = pointView + result;
					}
					pointView = pointView + ",";
				}
				gamePoint.setPointView(pointView.substring(0, pointView.length() - 1));
			}
		}
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
		Long userId = RequestContext.getCurrUser().getId();
		logger.info("cancelOrder start");
		request.setUserId(userId);
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
			resp.setMessage("方案撤销失败!");
			return resp;
		}
		return resp;
	}	
}
