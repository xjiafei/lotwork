package com.winterframework.firefrog.game.web.controller;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
import com.winterframework.firefrog.common.util.DateUtils;
import com.winterframework.firefrog.game.dao.vo.GameNumberConfig;
import com.winterframework.firefrog.game.entity.ChannelType;
import com.winterframework.firefrog.game.entity.GameBetDetailTotAmountEntity;
import com.winterframework.firefrog.game.util.LHCUtil;
import com.winterframework.firefrog.game.util.LhcRedisUtil;
import com.winterframework.firefrog.game.web.controller.view.ExcelView;
import com.winterframework.firefrog.game.web.controller.view.ExportViewDataModel;
import com.winterframework.firefrog.game.web.dto.DTOConvertor4Web;
import com.winterframework.firefrog.game.web.dto.FundTransactionStruc;
import com.winterframework.firefrog.game.web.dto.FundTransactionStrucForUI;
import com.winterframework.firefrog.game.web.dto.GameAwardQueryResponse;
import com.winterframework.firefrog.game.web.dto.GameBetDetailTOTAMOUNT;
import com.winterframework.firefrog.game.web.dto.GameBetDetailTotAmountDto;
import com.winterframework.firefrog.game.web.dto.GameBetDetailTotAmountResponse;
import com.winterframework.firefrog.game.web.dto.GameIssueQueryRequest;
import com.winterframework.firefrog.game.web.dto.GameIssueQueryResponse;
import com.winterframework.firefrog.game.web.dto.GameOrderDetailOperationsQueryRequest;
import com.winterframework.firefrog.game.web.dto.GameOrderDetailOperationsQueryResponse;
import com.winterframework.firefrog.game.web.dto.GameOrderOperationsQueryRequest;
import com.winterframework.firefrog.game.web.dto.GameOrderOperationsQueryResponse;
import com.winterframework.firefrog.game.web.dto.GameUserAwardQueryRequest;
import com.winterframework.firefrog.game.web.dto.GetGameIssueStrucsRequest;
import com.winterframework.firefrog.game.web.dto.IssueStruc;
import com.winterframework.firefrog.game.web.dto.OrdersStruc;
import com.winterframework.firefrog.game.web.dto.OrdersStrucForUI;
import com.winterframework.firefrog.game.web.dto.PageForView;
import com.winterframework.firefrog.game.web.dto.SlipsStruc;
import com.winterframework.firefrog.game.web.dto.SlipsStrucForUI;
import com.winterframework.firefrog.game.web.dto.UserInfoRequest;
import com.winterframework.firefrog.game.web.dto.UserInfoResponse;
import com.winterframework.firefrog.game.web.util.PageUtils;
import com.winterframework.modules.page.PageRequest;
import com.winterframework.modules.spring.exetend.PropertyConfig;
import com.winterframework.modules.web.jsonresult.Pager;
import com.winterframework.modules.web.jsonresult.Response;
import com.winterframework.modules.web.jsonresult.ResultPager;
import com.winterframework.modules.web.util.RequestContext;

/** 
* @ClassName: GameOrderOperationsWebController 
* @Description: 订单运营web controller 
* @author Alan
* @date 2013-10-16 下午2:48:50 
*  
*/
@Controller("gameOrderOperationsWebController")
@RequestMapping(value = "/gameoa")
public class GameOrderOperationsWebController {

	private static Logger logger = LoggerFactory.getLogger(GameOrderOperationsWebController.class);

	@Resource(name = "httpJsonClientImpl")
	private IHttpJsonClient httpClient;

	@PropertyConfig(value = "url.connect")
	private String serverPath;

	@PropertyConfig(value = "url.game.queryOrderOperations")
	private String queryOrderOperationsUrl;

	@PropertyConfig(value = "url.game.querySlipListByIssueCode")
	private String querySlipListByIssueCodeUrl;
	
	@PropertyConfig(value = "url.game.queryOrderOperationsDetail")
	private String queryOrderOperationsDetailUrl;

	@PropertyConfig(value = "url.game.exportOrderOperations")
	private String exportOrderOperationsUrl;
	
	@PropertyConfig(value = "url.game.queryBetDetaiByIssudeCode")
	private String queryBetDetaiByIssudeCodeUrl;
	
	@PropertyConfig(value = "admin.resources.path")
	private String userInfoUrl;

	@PropertyConfig(value = "url.game.queryGameIssue")
	private String queryGameIssue;
	
	@PropertyConfig(value = "url.game.getBackGameIssuesByLotteryId")
	private String getGameIssuesByLotteryId;
	@PropertyConfig(value = "url.game.getUserInfo")
	private String queryUserInfoUrl;
	@PropertyConfig(value = "url.game.queryGameAwardRet")
	private String queryGameAwardRet;
	@Resource(name="lhcRedisUtil")
	private  LhcRedisUtil lhcRedisUtil;
	

	/**
	* @Title: queryOrderList 
	* @Description: 访问订单记录列表页面 
	* @param page
	* @param request
	* @param model
	* @param pageCount
	* @param sortType
	* @throws Exception
	 */
	@RequestMapping(value = "/queryOrderListByIssueCode")
	public String queryOrderListByIssueCode(@ModelAttribute("page") PageRequest<GameOrderOperationsQueryRequest> page,
			@ModelAttribute("req") GameOrderOperationsQueryRequest request, Model model,
			@RequestParam("lotteryId") Long lotteryId, @RequestParam("issueCode") Long issueCode) throws Exception {
		Response<GameOrderOperationsQueryResponse> response = new Response<GameOrderOperationsQueryResponse>();

		// 提供來源下拉選單內容
		model.addAttribute("device", ChannelType.values());
		
		//默认条件：全部彩种、投注时间1天，全部状态，搜索用户/方案为空值，奖金为空值（即无限制范围），来源为所有版本/机型，默认显示25行
		Pager pager = new Pager();

		page.setPageSize(25);
		request.setSortType(2); //根据投注时间倒序，时间越近在越上面

		if (null != lotteryId && lotteryId > 0) {
			request.setLotteryid(lotteryId);
		}

		if (null != issueCode && issueCode > 0) {
			request.setIssueCode(issueCode);
		}
		pager = PageUtils.getPager(page);

		//查询所有订单记录分页列表
		if (null == request.getSelectTimeMode()) {
			request.setSelectTimeMode(3L);
			if (null == request.getStartCreateTime()) {
				request.setStartCreateTime(DateUtils.getStartTimeOfDate(DateUtils.addDays(DateUtils.currentDate(), -2))
						.getTime());
			}
			if (null == request.getEndCreateTime()) {
				request.setEndCreateTime(DateUtils.getEndTimeOfCurrentDate().getTime());
			}
		}

		logger.info("query game order operations start");
		try {
			Long userid = RequestContext.getCurrUser().getId();
			String username = RequestContext.getCurrUser().getUserName();
			response = httpClient.invokeHttp(serverPath + queryOrderOperationsUrl, request, pager, userid, username,
					GameOrderOperationsQueryResponse.class);
		} catch (Exception e) {
			logger.error("query game order operations error", e);
			throw e;
		}

		ResultPager rp = response.getBody().getPager();

		List<OrdersStrucForUI> orders = new ArrayList<OrdersStrucForUI>();
		BigDecimal betTotal = new BigDecimal(0);
		BigDecimal winTotal = new BigDecimal(0);
		float ratioTotal = 0;

		if (response.getBody().getResult().getOrdersStrucs() != null) {
			List<OrdersStruc> ordersStruc = response.getBody().getResult().getOrdersStrucs();

			for (OrdersStruc os : ordersStruc) {
				OrdersStrucForUI ui = DTOConvertor4Web.ordersStruc2OrdersStrucForUI(os);
				orders.add(ui);

				betTotal = betTotal.add(new BigDecimal(ui.getTotalAmount()/10000.00));
				winTotal = winTotal.add(new BigDecimal(ui.getTotwin()/10000.00));
				ratioTotal += ui.getWinsRatio();
			}
			if (ordersStruc.size() > 0)
				ratioTotal /= ordersStruc.size();
			model.addAttribute("orders", orders);
		}
		BigDecimal scaleTotal = new BigDecimal(0);
		if(winTotal.doubleValue() != 0){
			scaleTotal = winTotal.divide(betTotal,2,RoundingMode.DOWN);
		}
		model.addAttribute("betTotal", betTotal);
		model.addAttribute("winTotal", winTotal);
		model.addAttribute("ratioTotal", ratioTotal);
		model.addAttribute("scaleTotal", scaleTotal);
		model.addAttribute("req", request);
		model.addAttribute("page", PageUtils.getPageForView(page, rp));
		model.addAttribute("totalDataCount", rp.getTotal());
		return "/operations/order/orderList";
	}
	
	
	/**
	* @Title: querySlipListByIssueCode 
	* @Description: 访问订单记录列表页面 
	* @param page
	* @param request
	* @param model
	* @param pageCount
	* @param sortType
	* @throws Exception
	 */
	@RequestMapping(value = "/querySlipListByIssueCode")
	public String querySlipListByIssueCode(@ModelAttribute("page") PageRequest<GameOrderOperationsQueryRequest> page,
			@ModelAttribute("req") GameOrderOperationsQueryRequest request, Model model,
			@RequestParam(value="lotteryId" , required=false) Long lotteryId, @RequestParam(value="issueCode", required=false) Long issueCode) throws Exception {
		Response<GameOrderOperationsQueryResponse> response = new Response<GameOrderOperationsQueryResponse>();

		// 提供來源下拉選單內容
		model.addAttribute("device", ChannelType.values());
		
		if(request.getLotteryid()==null && lotteryId==null){//首次进入页面直接返回空页面
			logger.info("request.getLotteryid()==null");
			PageForView pv=new PageForView();
			pv.setTotalPages(25);
			pv.setPageNo(1);
			pv.setPageSize(25);
			pv.setTotalCount(0);
			model.addAttribute("betTotal", 0);
			model.addAttribute("winTotal", 0);
			model.addAttribute("page", pv);
			return "/operations/order/slipList";
		}
		
		//默认条件：全部彩种、投注时间1天，全部状态，搜索用户/方案为空值，奖金为空值（即无限制范围），来源为所有版本/机型，默认显示25行
		Pager pager = new Pager();

		page.setPageSize(25);
		request.setSortType(2); //根据投注时间倒序，时间越近在越上面

		if (null != lotteryId && lotteryId > 0) {
			request.setLotteryid(lotteryId);
		}

		if (null != issueCode && issueCode > 0) {
			request.setIssueCode(issueCode);
		}
		pager = PageUtils.getPager(page);

		//查询所有订单记录分页列表
		if (null == request.getSelectTimeMode()) {
			request.setSelectTimeMode(3L);
			if (null == request.getStartCreateTime()) {
				request.setStartCreateTime(DateUtils.getStartTimeOfDate(DateUtils.addDays(DateUtils.currentDate(), -2))
						.getTime());
			}
			if (null == request.getEndCreateTime()) {
				request.setEndCreateTime(DateUtils.getEndTimeOfCurrentDate().getTime());
			}
		}

		logger.info("querySlipListByIssueCode start");
		Response<GameOrderDetailOperationsQueryResponse> slipResponse = new Response<GameOrderDetailOperationsQueryResponse>();
		try {
			Long userid = RequestContext.getCurrUser().getId();
			String username = RequestContext.getCurrUser().getUserName();
			response = httpClient.invokeHttp(serverPath + queryOrderOperationsUrl, request, pager, userid, username,
					GameOrderOperationsQueryResponse.class);
			//流程監控多一個功能，查看本期方案細單紀錄
			//若無獎期，則帶入搜索後所有的orderId查詢gameslip，最多一次查25筆
			if(request.getIssueCode()==null && issueCode==null){
				List<OrdersStruc> ordersStruc = new ArrayList<OrdersStruc>();
				ordersStruc = response.getBody().getResult().getOrdersStrucs();
				List<Long> orderIdList = new ArrayList<Long>();
				for (OrdersStruc os : ordersStruc) {
					orderIdList.add(os.getOrderId());
					request.setOrderIdList(orderIdList);
					
				}
				slipResponse = httpClient.invokeHttp(serverPath + querySlipListByIssueCodeUrl, request,pager,
						userid, username, GameOrderDetailOperationsQueryResponse.class);
			}else{
				//有帶獎期資料進來filter
				slipResponse = httpClient.invokeHttp(serverPath + querySlipListByIssueCodeUrl, request,pager,
						userid, username, GameOrderDetailOperationsQueryResponse.class);
			}			
						
		} catch (Exception e) {
			logger.error("querySlipListByIssueCode error", e);
			throw e;
		}

		ResultPager rp = response.getBody().getPager();

		List<OrdersStrucForUI> orders = new ArrayList<OrdersStrucForUI>();
		BigDecimal betTotal = new BigDecimal(0);
		BigDecimal winTotal = new BigDecimal(0);
		float ratioTotal = 0;
		
		List<OrdersStruc> ordersStruc = new ArrayList<OrdersStruc>();
		if (response.getBody().getResult().getOrdersStrucs() != null) {
			ordersStruc = response.getBody().getResult().getOrdersStrucs();
			List<SlipsStruc> slipsStrucs = slipResponse.getBody().getResult().getSlipsStruc();		
			for (OrdersStruc os : ordersStruc) {
				OrdersStrucForUI ui = DTOConvertor4Web.ordersStruc2OrdersStrucForUI(os);
				List<String> betDetailList =  new ArrayList<String>();
				String lastMetode="x";
				for(SlipsStruc slip: slipsStrucs){				
					if(ui.getOrderId().equals(slip.getOrderId())){					
						if(slip.getBetDetail().split(":")[0].contains(lastMetode)){							
							betDetailList.add(slip.getBetDetail().split(":")[1]+" ");
						}else{
							lastMetode=slip.getBetDetail().split(":")[0];
							betDetailList.add(" "+lastMetode+":"+slip.getBetDetail().split(":")[1]);
						}
					}					
			    }
				ui.setBetDetail(betDetailList);
				orders.add(ui);

				betTotal = betTotal.add(new BigDecimal(ui.getTotalAmount()/10000.00));
				winTotal = winTotal.add(new BigDecimal(ui.getTotwin()/10000.00));
				ratioTotal += ui.getWinsRatio();
			}
			if (ordersStruc.size() > 0)ratioTotal /= ordersStruc.size();
			model.addAttribute("orders", orders);
		}
		BigDecimal scaleTotal = new BigDecimal(0);
		if(winTotal.doubleValue() != 0){
			scaleTotal = winTotal.divide(betTotal,2,RoundingMode.DOWN);
		}
		model.addAttribute("betTotal", betTotal);
		model.addAttribute("winTotal", winTotal);
		model.addAttribute("ratioTotal", ratioTotal);
		model.addAttribute("scaleTotal", scaleTotal);
		model.addAttribute("req", request);
		model.addAttribute("page", PageUtils.getPageForView(page, rp));
		model.addAttribute("totalDataCount", rp.getTotal());
		return "/operations/order/slipList";
	}
	
	/**
	* @Title: queryOrderList 
	* @Description: 访问订单记录列表页面 
	* @param page
	* @param request
	* @param model
	* @param pageCount
	* @param sortType
	* @throws Exception
	 */
	@RequestMapping(value = "/queryOrderList")
	public String queryOrderList(@ModelAttribute("page") PageRequest<GameOrderOperationsQueryRequest> page,
			@ModelAttribute("req") GameOrderOperationsQueryRequest request, Model model,
			@ModelAttribute("pageCount") String pageCount, @ModelAttribute("sortType") String sortType)
			throws Exception {
		Response<GameOrderOperationsQueryResponse> response = new Response<GameOrderOperationsQueryResponse>();
		logger.info("queryOrderList lottery id : " + request.getLotteryid());
		
		// 提供來源下拉選單內容
		model.addAttribute("device", ChannelType.values());
		
		if(request.getLotteryid()==null){//首次进入页面直接返回空页面
			logger.info("request.getLotteryid()==null");
			PageForView pv=new PageForView();
			pv.setTotalPages(25);
			pv.setPageNo(1);
			pv.setPageSize(25);
			pv.setTotalCount(0);
			model.addAttribute("betTotal", 0);
			model.addAttribute("winTotal", 0);
			model.addAttribute("page", pv);
			return "/operations/order/orderList";
		}
		//默认条件：全部彩种、投注时间1天，全部状态，搜索用户/方案为空值，奖金为空值（即无限制范围），来源为所有版本/机型，默认显示25行
		Pager pager = new Pager(0,25);

		if ("".equals(pageCount)) {
			page.setPageSize(25);
		} else {
			page.setPageSize(Integer.parseInt(pageCount));
		}

		if ("".equals(sortType)) {
			request.setSortType(2); //根据投注时间倒序，时间越近在越上面
		} else {
			request.setSortType(Integer.parseInt(sortType));
		}
		pager = PageUtils.getPager(page);

		//查询所有订单记录分页列表
		if (null == request.getSelectTimeMode()) {
			request.setSelectTimeMode(1L);
			if (null == request.getStartCreateTime()) {
				request.setStartCreateTime(DateUtils.getStartTimeOfCurrentDate().getTime());
			}
			if (null == request.getEndCreateTime()) {
				request.setEndCreateTime(DateUtils.getEndTimeOfCurrentDate().getTime());
			}
		}

		logger.info("query game order operations start");
		try {
			Long userid = RequestContext.getCurrUser().getId();
			String username = RequestContext.getCurrUser().getUserName();
			response = httpClient.invokeHttp(serverPath + queryOrderOperationsUrl, request, pager, userid, username,
					GameOrderOperationsQueryResponse.class);
			
			if (response == null){
				throw new Exception();
			}
				
		} catch (Exception e) {
			logger.error("query game order operations error", e);
			model.addAttribute("ConnectFail", 1);
			model.addAttribute("betTotal", 0);
			model.addAttribute("winTotal", 0);
			model.addAttribute("ratioTotal", 0);
			model.addAttribute("scaleTotal", 0);
			model.addAttribute("req", request);
			ResultPager rp2 = new ResultPager ();
			model.addAttribute("page", PageUtils.getPageForView(page, rp2));
			model.addAttribute("totalDataCount", 0);
			model.addAttribute("userInfoUrl", userInfoUrl);
			return "/operations/order/orderList";
		}

		ResultPager rp = response.getBody().getPager();

		List<OrdersStrucForUI> orders = new ArrayList<OrdersStrucForUI>();
		BigDecimal betTotal = new BigDecimal(0);
		BigDecimal winTotal = new BigDecimal(0);
		float ratioTotal = 0;
		model.addAttribute("orders", orders);
		if (response.getBody().getResult().getOrdersStrucs() != null) {
			List<OrdersStruc> ordersStruc = response.getBody().getResult().getOrdersStrucs();

			for (OrdersStruc os : ordersStruc) {
				OrdersStrucForUI ui = DTOConvertor4Web.ordersStruc2OrdersStrucForUI(os);
				ui.setUserid(os.getUserid());
				orders.add(ui);

				betTotal = betTotal.add(new BigDecimal(ui.getTotalAmount()/10000.00));
				winTotal = winTotal.add(new BigDecimal((ui.getTotwin()+ui.getTotDiamondWin())/10000.00));
				ratioTotal += ui.getWinsRatio();
			}
			if (ordersStruc.size() > 0)
				ratioTotal /= ordersStruc.size();
			model.addAttribute("orders", orders);
		}
		BigDecimal scaleTotal = new BigDecimal(0);
		if(winTotal.doubleValue() != 0){
			scaleTotal = winTotal.divide(betTotal,2,RoundingMode.DOWN);
		}
		model.addAttribute("betTotal", betTotal);
		model.addAttribute("winTotal", winTotal);
		model.addAttribute("ratioTotal", ratioTotal);
		model.addAttribute("scaleTotal", scaleTotal);
		model.addAttribute("req", request);
	
		model.addAttribute("page", PageUtils.getPageForView(page, rp));
		model.addAttribute("totalDataCount", rp.getTotal());
		model.addAttribute("userInfoUrl", userInfoUrl);
	
		return "/operations/order/orderList";
	}

	/**
	* @Title: exportOrderOperations 
	* @Description: 导出订单记录数据
	* @param request
	* @param sortType
	* @throws Exception
	 */
	@RequestMapping(value = "exportOrderOperations")
	public ModelAndView exportOrderOperations(@ModelAttribute("req") GameOrderOperationsQueryRequest request,
			@ModelAttribute("sortType") String sortType) throws Exception {
		Response<GameOrderOperationsQueryResponse> response = new Response<GameOrderOperationsQueryResponse>();

		if ("".equals(sortType)) {
			request.setSortType(2);
		} else {
			request.setSortType(Integer.parseInt(sortType));
		}

		//查询所有订单列表
		if (null == request.getSelectTimeMode()) {
			request.setSelectTimeMode(3L);

			if (null == request.getStartCreateTime()) {
				request.setStartCreateTime(System.currentTimeMillis() - 1000 * 60 * 60 * 24 * 3);
			}
			if (null == request.getEndCreateTime()) {
				request.setEndCreateTime(System.currentTimeMillis());
			}
		}

		logger.info("export game order operations start");
		try {
			Long userid = RequestContext.getCurrUser().getId();
			String username = RequestContext.getCurrUser().getUserName();
			response = httpClient.invokeHttp(serverPath + exportOrderOperationsUrl, request, userid, username,
					GameOrderOperationsQueryResponse.class);
		} catch (Exception e) {
			logger.error("export game order operations error", e);
			throw e;
		}

		if (response.getBody().getResult().getOrdersStrucs() != null) {
			List<OrdersStrucForUI> orders = new ArrayList<OrdersStrucForUI>();

			List<OrdersStruc> ordersStruc = response.getBody().getResult().getOrdersStrucs();
			for (OrdersStruc os : ordersStruc) {
				OrdersStrucForUI ui = DTOConvertor4Web.ordersStruc2OrdersStrucForUI(os);
				ui.setTotalAmountD(Double.valueOf(ui.getTotalAmount()) / 10000.0);
				ui.setTotwinD(Double.valueOf(ui.getTotwin()+ui.getTotDiamondWin()) / 10000.0);// 匯出報表 增加顯示鑽石加獎獎金
				orders.add(ui);
			}

			//执行导出功能
			ExportViewDataModel viewTemplates = new ExportViewDataModel();

			String[] titles = new String[] { "方案编号", "彩种名称", "期号", "用户名", "投注时间", "投注金额(¥)", "奖金(¥)", "方案中投比", "状态",
					"开奖号码", "是否为追号单", "版本号", "来源" };
			String[] columns = new String[] { "orderCode", "lotteryName", "webIssueCode", "account", "saleTime",
					"totalAmountD", "totwinD", "winsRatio", "status", "numberRecord", "parentType", "channelVersion",
					"channelId" };

			viewTemplates.setFileName("订单记录导出数据");

			viewTemplates.setHeader(titles);
			viewTemplates.setColumns(columns);
			viewTemplates.setDataList(orders);

			Map<String, Object> model = new HashMap<String, Object>();
			model.put("dataModel", viewTemplates);

			//return new ModelAndView(new CSVView(), model);
			return new ModelAndView(new ExcelView(), model);
		}

		return new ModelAndView();
	}

	/**
	* @Title: queryOrderDetail 
	* @Description: 查询订单详情 
	* @param queryOrderDetailRequest
	* @param model
	* @return
	* @throws Exception
	 */
	@RequestMapping(value = "queryOrderDetail")
	public String queryOrderDetail(
			@ModelAttribute("queryOrderDetailRequest") GameOrderDetailOperationsQueryRequest queryOrderDetailRequest,
			Model model) throws Exception { 
		UserInfoRequest userInfoReq=new UserInfoRequest();
		GameUserAwardQueryRequest userAwardReq = new GameUserAwardQueryRequest();
		userInfoReq.setUserId(RequestContext.getCurrUser().getId());
		Response<UserInfoResponse> userInfoRes = httpClient.invokeHttp(serverPath + queryUserInfoUrl, userInfoReq,UserInfoResponse.class); 
		model.addAttribute("awardRetStatus",null==userInfoRes.getBody().getResult().getAwardRetStatus()?1:userInfoRes.getBody().getResult().getAwardRetStatus());
		Response<GameOrderDetailOperationsQueryResponse> response = new Response<GameOrderDetailOperationsQueryResponse>();
		Response<GameIssueQueryResponse> responseGameIssue = new Response<GameIssueQueryResponse>();
		logger.info("query game order operations detail start");
		try {
			Long userid = RequestContext.getCurrUser().getId();
			String username = RequestContext.getCurrUser().getUserName();
			response = httpClient.invokeHttp(serverPath + queryOrderOperationsDetailUrl, queryOrderDetailRequest,
					userid, username, GameOrderDetailOperationsQueryResponse.class);
			if(response == null ||response.getBody() == null){
				model.addAttribute("orderID",queryOrderDetailRequest.getOrderCode());
				model.addAttribute("error",true);
				return "/operations/order/orderDetail";
			}
		} catch (Exception e) {
			logger.error("query game order operations detail error", e);
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

		if (response != null) {
			OrdersStruc ordersStruc = response.getBody().getResult().getOrdersStruc();
			List<SlipsStruc> slipsStrucs = response.getBody().getResult().getSlipsStruc();
			OrdersStrucForUI ordersStrucForUI = DTOConvertor4Web.ordersStruc2OrdersStrucForUI(ordersStruc);
			try {
				List<GameNumberConfig> gameNumberConfig = lhcRedisUtil.findThisYearNumberConfig(DateUtils.parse(ordersStrucForUI.getSaleTime(),DateUtils.DATETIME_FORMAT_PATTERN));
				if(ordersStrucForUI.getNumberRecordList()!= null && ordersStrucForUI.getNumberRecordList().size()>0){
					Set<String> shengXiao=LHCUtil.tranferShenXiao(gameNumberConfig , ordersStrucForUI.getNumberRecordList());
					ordersStrucForUI.setShenXiaoList(shengXiao);
				}
			} catch (Exception e) {
				logger.error("LHC 開獎號碼轉換生肖 error" +e );
			}
			List<SlipsStrucForUI> slipsStrucForUI = new ArrayList<SlipsStrucForUI>();
			//查詢用戶組、總代返點
			userAwardReq.setUserId(response.getBody().getResult().getOrdersStruc().getUserid());
			userAwardReq.setLotteryId(response.getBody().getResult().getOrdersStruc().getLotteryid());
			logger.info("用戶組="+response.getBody().getResult().getOrdersStruc().getUserid());
			logger.info("總代返點="+response.getBody().getResult().getOrdersStruc().getLotteryid());
			Response<GameAwardQueryResponse> userAwardRes = httpClient.invokeHttp(serverPath + queryGameAwardRet, userAwardReq,GameAwardQueryResponse.class);
			
			
			for (SlipsStruc ss : slipsStrucs) {
				SlipsStrucForUI ui = DTOConvertor4Web.slipsStruc2SlipsStrucForUI(ss,ordersStruc.getLotteryid());
				///北京快乐8只需要玩法群趣味
				if (ordersStruc.getLotteryid().longValue() == 99201L && ss.getGameGroupCode().longValue() == 18) {
					ui.setGamePlayName("趣味型");
				}
				slipsStrucForUI.add(ui);
			}
			List<FundTransactionStruc> fundTransactionStrucs = response.getBody().getResult()
					.getFundTransactionStrucs();
			List<FundTransactionStrucForUI> fundTransactionStrucForUIs = new ArrayList<FundTransactionStrucForUI>();
			if (fundTransactionStrucs!=null&&!fundTransactionStrucs.isEmpty()) {
				for (FundTransactionStruc s : fundTransactionStrucs) {
					FundTransactionStrucForUI ui = DTOConvertor4Web.fundTransactionStruc2FundTransactionStrucForUI(s);
					fundTransactionStrucForUIs.add(ui);
				}
			}
			model.addAttribute("order", ordersStrucForUI);
			model.addAttribute("numberRecordList", ordersStrucForUI.getNumberRecordList());
			model.addAttribute("numberRecordColorList", ordersStrucForUI.getNumberRecordColorList());
			model.addAttribute("numberRecordShenXiaoList", ordersStrucForUI.getShenXiaoList());
			model.addAttribute("slips", slipsStrucForUI);
			model.addAttribute("funds", fundTransactionStrucForUIs);
			model.addAttribute("userAwardName", userAwardRes.getBody().getResult().getAwardGroupName());
			model.addAttribute("userDirectRet", userAwardRes.getBody().getResult().getDirectRet());
			model.addAttribute("userThreeoneRet", userAwardRes.getBody().getResult().getThreeoneRet());
			if(userAwardRes.getBody().getResult().getLotteryId()==99701){
				model.addAttribute("lhcColorRet", userAwardRes.getBody().getResult().getLhcColor());
				model.addAttribute("lhcYearRet", userAwardRes.getBody().getResult().getLhcYear());
				model.addAttribute("lhcFlatcodeRet", userAwardRes.getBody().getResult().getLhcFlatcode());
				model.addAttribute("lhcHalfwaveRet", userAwardRes.getBody().getResult().getLhcHalfwave());
				model.addAttribute("lhcOneyearRet", userAwardRes.getBody().getResult().getLhcOneyear());
				model.addAttribute("lhcNotinRet", userAwardRes.getBody().getResult().getLhcNotin());
				model.addAttribute("lhcContinuein23Ret", userAwardRes.getBody().getResult().getLhcContinuein23());
				model.addAttribute("lhcContinuein4Ret", userAwardRes.getBody().getResult().getLhcContinuein4());
				model.addAttribute("lhcContinuein5Ret", userAwardRes.getBody().getResult().getLhcContinuein5());
				model.addAttribute("lhcContinuenotin23Ret", userAwardRes.getBody().getResult().getLhcContinuenotin23());
				model.addAttribute("lhcContinuenotin4Ret", userAwardRes.getBody().getResult().getLhcContinuenotin4());
				model.addAttribute("lhcContinuenotin5Ret", userAwardRes.getBody().getResult().getLhcContinuenotin5());
				model.addAttribute("lhcContinuecodeRet", userAwardRes.getBody().getResult().getLhcContinuecode());
			}
			if(userAwardRes.getBody().getResult().getLotteryId()==99601 || userAwardRes.getBody().getResult().getLotteryId()==99602 || userAwardRes.getBody().getResult().getLotteryId()==99603){
				model.addAttribute("sbThreeoneRet", userAwardRes.getBody().getResult().getSbThreeoneRet());
			}
			
			if (responseGameIssue.getBody() == null) {
				model.addAttribute("gameIssue", new GameIssueQueryResponse());
			} else {
				model.addAttribute("gameIssue", responseGameIssue.getBody().getResult());
			}
			model.addAttribute("status", ordersStruc.getStatus());
		}
		return "/operations/order/orderDetail";
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
	* @Title: queryUserGameAward 
	* @Description: 4.18	奖金组奖金查询 
	* @param lotteryId
	* @param awardGroupId
	* @param status
	* @return
	* @throws Exception
	 */
	@RequestMapping("/queryBetDetaiByIssudeCode")
	@ResponseBody
	public Object queryBetDetaiByIssudeCode(@RequestParam("issueCode") Long issueCode,@RequestParam("lotteryId") Long lotteryId)
			throws Exception {
		logger.info("queryBetDetaiByIssudeCOde start :"+issueCode);
		GameBetDetailTOTAMOUNT op = new GameBetDetailTOTAMOUNT();
		try {
			GameOrderOperationsQueryRequest requestData = new GameOrderOperationsQueryRequest();
			requestData.setIssueCode(issueCode);
			requestData.setLotteryid(lotteryId);
			Response<GameBetDetailTotAmountResponse> response = httpClient.invokeHttp(serverPath + queryBetDetaiByIssudeCodeUrl,
					requestData, GameBetDetailTotAmountResponse.class);
			Map<String, List<GameBetDetailTotAmountDto>> dtos = new HashMap<String, List<GameBetDetailTotAmountDto>>();
			if (null != response.getBody() && null != response.getBody().getResult()) {
				GameBetDetailTotAmountResponse betDetailTotAmount = response.getBody().getResult();
				dtos = convertStruc2DTO(betDetailTotAmount.getGameBetDetailTotAmountStruc());
			}
			op.setStatus("ok");
			StringBuffer htmlBuffer = new StringBuffer();
		
			htmlBuffer.append("<table class='table table-border'>")
					  .append("<thead><tr><th>獎期:<"+issueCode+">玩法群</th><th>详细玩法</th><th>金額（元）</th></tr></thead><tbody>");
			for(String key : dtos.keySet()){
				List<GameBetDetailTotAmountDto> dtolist = dtos.get(key);
				htmlBuffer.append("<tr>");
				htmlBuffer.append("<td rowspan='" + dtolist.size() + "'>" + dtolist.get(0).getMethodCodeTitle()
								+ "</td>");
				
				for (int i = 0; i < dtolist.size(); i++) {
					GameBetDetailTotAmountDto awards = dtolist.get(i);

					htmlBuffer.append("<td rowspan=''>"
							+ awards.getBetDeatil() + "</td>");
					htmlBuffer.append("<td rowspan=''>"
										+ awards.getTotAmount() + "</td>");
					htmlBuffer.append("</tr>");
				}
			}	
			htmlBuffer.append("</tbody>");
			op.setHtml(htmlBuffer.toString()); 

		} catch (Exception e) {
			logger.error("查询玩法分布错误"+e.getMessage());
			op.setStatus("failure");
			op.setHtml("查询玩法分布错误");
		}
		return op;
	}


	private  Map<String, List<GameBetDetailTotAmountDto>> convertStruc2DTO(List<GameBetDetailTotAmountEntity> gameBetDetailTotAmountEntity) {	
		//HashMap<String,Object> resultMap= new HashMap<String,Object>();
		GameBetDetailTotAmountDto betDetailTotAmountDto = null;
		Map<String, List<GameBetDetailTotAmountDto>> results= new HashMap<String, List<GameBetDetailTotAmountDto>>();		
		for(GameBetDetailTotAmountEntity betDetail:gameBetDetailTotAmountEntity){
	
			String betDetailKey =betDetail.getBetDetail().split("\\*\\*")[0];
			String methodCodeTitle =betDetail.getBetDetail().split("\\*\\*")[1];
			String betDetailMethod =betDetail.getBetDetail().split("\\*\\*")[2];
			betDetailTotAmountDto=new GameBetDetailTotAmountDto();
			betDetailTotAmountDto.setBetDeatil(betDetailMethod);
			betDetailTotAmountDto.setTotAmount(betDetail.getTotAmount());
			betDetailTotAmountDto.setMethodCodeTitle(methodCodeTitle);
			
			if(results.containsKey(betDetailKey)){
				results.get(betDetailKey).add(betDetailTotAmountDto);
			} else {
				List<GameBetDetailTotAmountDto> list = new ArrayList<GameBetDetailTotAmountDto>();
				list.add(betDetailTotAmountDto);
				results.put(betDetailKey, list);
				
			}
		}
		return results;
	}
	
}
