package com.winterframework.firefrog.game.web.controller;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.winterframework.firefrog.common.httpjsonclient.IHttpJsonClient;
import com.winterframework.firefrog.common.util.DateUtils;
import com.winterframework.firefrog.game.entity.ChannelType;
import com.winterframework.firefrog.game.web.controller.view.ExcelView;
import com.winterframework.firefrog.game.web.controller.view.ExportViewDataModel;
import com.winterframework.firefrog.game.web.dto.DTOConvertor4Web;
import com.winterframework.firefrog.game.web.dto.FundTransactionStruc;
import com.winterframework.firefrog.game.web.dto.FundTransactionStrucForUI;
import com.winterframework.firefrog.game.web.dto.GamePlanOperationsDetailQueryRequest;
import com.winterframework.firefrog.game.web.dto.GamePlanOperationsDetailQueryResponse;
import com.winterframework.firefrog.game.web.dto.GamePlanOperationsRequest;
import com.winterframework.firefrog.game.web.dto.GamePlanOperationsResponse;
import com.winterframework.firefrog.game.web.dto.OrdersStruc;
import com.winterframework.firefrog.game.web.dto.OrdersStrucForUI;
import com.winterframework.firefrog.game.web.dto.PageForView;
import com.winterframework.firefrog.game.web.dto.PlansFuturesStruc;
import com.winterframework.firefrog.game.web.dto.PlansStruc;
import com.winterframework.firefrog.game.web.dto.PlansStrucForUI2;
import com.winterframework.firefrog.game.web.dto.SlipsStruc;
import com.winterframework.firefrog.game.web.dto.SlipsStrucForUI;
import com.winterframework.firefrog.game.web.util.PageUtils;
import com.winterframework.modules.page.PageRequest;
import com.winterframework.modules.spring.exetend.PropertyConfig;
import com.winterframework.modules.web.jsonresult.Pager;
import com.winterframework.modules.web.jsonresult.Response;
import com.winterframework.modules.web.jsonresult.ResultPager;
import com.winterframework.modules.web.util.RequestContext;

/** 
 * @ClassName: GamePlanOperationWebController 
 * @Description: 追号记录Web Controller
 * @author Alan
 * @date 2013-10-16 下午3:34:29 
 */
@Controller("gamePlanOperationWebController")
@RequestMapping(value = "/gameoa")
public class GamePlanOperationWebController {

	private Logger logger = LoggerFactory.getLogger(GamePlanOperationWebController.class);

	@Resource(name = "httpJsonClientImpl")
	private IHttpJsonClient httpClient;

	@PropertyConfig(value = "url.connect")
	private String serverPath;

	@PropertyConfig(value = "url.game.queryPlanOperations")
	private String queryPlanOperationsUrl;

	@PropertyConfig(value = "url.game.queryPlanOperationsDetail")
	private String queryPlanOperationsDetailUrl;

	@PropertyConfig(value = "url.game.exportPlanOperations")
	private String exportPlanOperationsUrl;

	@PropertyConfig(value = "admin.resources.path")
	private String userInfoUrl;
	
	
	/** 
	* @Title: queryPlansEnter 
	* @Description:追号记录查询界面入口
	* @return
	*/
	@RequestMapping(value = "/queryPlansEnter")
	public String queryPlansEnter() {
		return "/operations/plan/planList";
	}

	/**
	 * @Title: queryPlanList 
	 * @Description: 访问追号记录列表页面
	 * @param page
	 * @param request
	 * @param model
	 * @param pageCount
	 * @param sortType
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryPlanList")
	public String queryPlanList(@ModelAttribute("page") PageRequest<GamePlanOperationsRequest> page,
			@ModelAttribute("req") GamePlanOperationsRequest request, Model model,
			@ModelAttribute("pageCount") String pageCount, @ModelAttribute("sortType") String sortType)
			throws Exception {
		Response<GamePlanOperationsResponse> response = new Response<GamePlanOperationsResponse>();

		// 提供來源下拉選單內容
		model.addAttribute("device", ChannelType.values());
		
		if(request.getLotteryid()==null){//首次进入页面直接返回空页面
			PageForView pv=new PageForView();
			pv.setTotalPages(25);
			pv.setPageNo(1);
			pv.setPageSize(25);
			pv.setTotalCount(0);
			model.addAttribute("page", pv);
			return "/operations/plan/planList";
		}

		//默认：全部彩种、投注时间3天，全部状态，搜索用户/方案为空值，来源为所有版本/机型，追中后续处理为全部
		Pager pager = new Pager();

		if ("".equals(pageCount)) {
			page.setPageSize(25);
		} else {
			page.setPageSize(Integer.parseInt(pageCount));
		}

		if ("".equals(sortType)) {
			request.setSortType(2);//根据追号时间倒序
		} else {
			request.setSortType(Integer.parseInt(sortType));
		}
		logger.debug("DEVICE : " + request.getDevice());
		pager = PageUtils.getPager(page);

		//查询所有追号记录分页列表
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

		logger.debug("query game plan operations start");
		try {
			Long userid = RequestContext.getCurrUser().getId();
			String username = RequestContext.getCurrUser().getUserName();
			response = httpClient.invokeHttp(serverPath + queryPlanOperationsUrl, request, pager, userid, username,
					GamePlanOperationsResponse.class);
		} catch (Exception e) {
			logger.error("query game plan operations error", e);
			throw e;
		}
		logger.debug("query game plan operations end");

		if (response.getBody() != null) {
			ResultPager rp = response.getBody().getPager();

			List<PlansStrucForUI2> psuis = new ArrayList<PlansStrucForUI2>();
			Long amountTotal = 0l;
			Long usedTotal = 0l;
			Long cancelTotal = 0l;
			Long winTotal = 0l;

			if (response.getBody().getResult().getPlanStruc() != null) {
				List<PlansStruc> planStrucs = response.getBody().getResult().getPlanStruc();
				for (PlansStruc ps : planStrucs) {
					PlansStrucForUI2 ui = DTOConvertor4Web.plansStruc2PlansStrucForUI2(ps);
					ui.setUserid(ps.getUserid());
					ui.setLotteryid(ps.getLotteryid());
					psuis.add(ui);

					amountTotal += ui.getTotamount();
					usedTotal += ui.getUsedAmount();
					cancelTotal += ui.getCanceledAmount();
					winTotal += ui.getTotalWin();
				}

				model.addAttribute("plans", psuis);
			}

			model.addAttribute("amountTotal", amountTotal);
			model.addAttribute("usedTotal", usedTotal);
			model.addAttribute("cancelTotal", cancelTotal);
			model.addAttribute("winTotal", winTotal);

			model.addAttribute("req", request);
			model.addAttribute("page", PageUtils.getPageForView(page, rp));
			model.addAttribute("totalDataCount", rp.getTotal());
			model.addAttribute("userInfoUrl", userInfoUrl);
		}

		return "/operations/plan/planList";
	}

	/**
	* @Title: exportPlanOperations 
	* @Description: 导出追号列表信息 
	* @param request
	* @param sortType
	* @return
	* @throws Exception
	 */
	@RequestMapping(value = "exportPlanOperations")
	public ModelAndView exportPlanOperations(@ModelAttribute("req") GamePlanOperationsRequest request,
			@ModelAttribute("sortType") String sortType) throws Exception {
		Response<GamePlanOperationsResponse> response = new Response<GamePlanOperationsResponse>();

		if ("".equals(sortType)) {
			request.setSortType(2);
		} else {
			request.setSortType(Integer.parseInt(sortType));
		}

		//查询所有追号记录分页列表
		if (null == request.getSelectTimeMode()) {
			request.setSelectTimeMode(3L);
			if (null == request.getStartCreateTime()) {
				request.setStartCreateTime(System.currentTimeMillis() - 1000 * 60 * 60 * 24 * 3);
			}
			if (null == request.getEndCreateTime()) {
				request.setEndCreateTime(System.currentTimeMillis());
			}
		}

		logger.info("export game plan operations start");
		try {
			Long userid = RequestContext.getCurrUser().getId();
			String username = RequestContext.getCurrUser().getUserName();
			response = httpClient.invokeHttp(serverPath + exportPlanOperationsUrl, request, userid, username,
					GamePlanOperationsResponse.class);
		} catch (Exception e) {
			logger.error("export game plan operations error", e);
			throw e;
		}

		if (response.getBody().getResult().getPlanStruc() != null) {
			List<PlansStrucForUI2> plans = new ArrayList<PlansStrucForUI2>();

			List<PlansStruc> planStrucs = response.getBody().getResult().getPlanStruc();
			for (PlansStruc ps : planStrucs) {
				PlansStrucForUI2 ui = DTOConvertor4Web.plansStruc2PlansStrucForUI2(ps);
				ui.setTotamountD(Double.valueOf(ui.getTotamount()) / 10000.0);
				ui.setUsedAmountD(Double.valueOf(ui.getUsedAmount()) / 10000.0);
				ui.setCanceledAmountD(Double.valueOf(ui.getCanceledAmount()) / 10000.0);
				ui.setTotalWinD(Double.valueOf(ui.getTotalWin()) / 10000.0);
				plans.add(ui);
			}

			//执行导出功能
			ExportViewDataModel viewTemplates = new ExportViewDataModel();

			String[] titles = new String[] { "追号编号", "彩种名称", "起始期号", "用户名", "追号时间", "追号期数", "完成期数", "取消期数", "追号总金额",
					"完成金额", "取消金额", "已获奖金", "追中即停(盈利即停)", "状态", "版本号", "来源" };
			String[] columns = new String[] { "planCode", "lotteryName", "startWebIssueCode", "account", "saleTime",
					"totalIssue", "finishIssue", "cancelIssue", "totamountD", "usedAmountD", "canceledAmountD",
					"totalWinD", "stopMode", "status", "channelVersion", "channelid" };

			viewTemplates.setFileName("追号记录导出数据");
			viewTemplates.setHeader(titles);
			viewTemplates.setColumns(columns);
			viewTemplates.setDataList(plans);

			Map<String, Object> model = new HashMap<String, Object>();
			model.put("dataModel", viewTemplates);

			return new ModelAndView(new ExcelView(), model);
		}

		return new ModelAndView();
	}

	/**
	* @Title: queryPlanDetail 
	* @Description: 查看追号详情信息 
	* @param queryPlanDetailRequest
	* @param model
	* @throws Exception
	 */
	@RequestMapping(value = "/queryPlanDetail")
	public String queryPlanDetail(
			@ModelAttribute("queryPlanDetailRequest") GamePlanOperationsDetailQueryRequest queryPlanDetailRequest,
			Model model) throws Exception {
		Response<GamePlanOperationsDetailQueryResponse> response = new Response<GamePlanOperationsDetailQueryResponse>();

		logger.info("query game plan operations detail start");
		try {
			Long userid = RequestContext.getCurrUser().getId();
			String username = RequestContext.getCurrUser().getUserName();
			response = httpClient.invokeHttp(serverPath + queryPlanOperationsDetailUrl, queryPlanDetailRequest, userid,
					username, GamePlanOperationsDetailQueryResponse.class);
			if(response.getBody() == null){
				model.addAttribute("orderID",queryPlanDetailRequest.getPlanCode());
				model.addAttribute("error",true);
				return "/operations/order/orderDetail";
			}
		} catch (Exception e) {
			logger.error("query game plan operations detail error");
			throw e;
		}
		logger.info("query game plan operations detail end");

		PlansStruc plansStruc = response.getBody().getResult().getPlansStruc();
		List<SlipsStruc> slipsStrucs = response.getBody().getResult().getSlipsStrucs();
		List<OrdersStruc> ordersStrucs = response.getBody().getResult().getOrdersStrucs();
		List<PlansFuturesStruc> plansFuturesStrucs = response.getBody().getResult().getPlansFuturesStrucs();
		List<FundTransactionStruc> fundTransactionStrucs = response.getBody().getResult().getFundTransactionStrucs();

		List<FundTransactionStrucForUI> fundTransactionStrucForUIs = new ArrayList<FundTransactionStrucForUI>();
		if (fundTransactionStrucs!=null&&!fundTransactionStrucs.isEmpty()) {
			for (FundTransactionStruc s : fundTransactionStrucs) {
				FundTransactionStrucForUI ui = DTOConvertor4Web.fundTransactionStruc2FundTransactionStrucForUI(s);
				fundTransactionStrucForUIs.add(ui);
			}
		}

		PlansStrucForUI2 plan = DTOConvertor4Web.plansStruc2PlansStrucForUI2(plansStruc);

		List<SlipsStrucForUI> slipsUIs = new ArrayList<SlipsStrucForUI>();
		for (SlipsStruc ss : slipsStrucs) {
			SlipsStrucForUI ui = DTOConvertor4Web.slipsStruc2SlipsStrucForUI(ss, plansStruc.getLotteryid());
			slipsUIs.add(ui);
		}

		List<OrdersStrucForUI> orderUIs = new ArrayList<OrdersStrucForUI>();
		for (OrdersStruc os : ordersStrucs) {
			OrdersStrucForUI ordersStrucForUI = DTOConvertor4Web.ordersStruc2OrdersStrucForUI(os);
			orderUIs.add(ordersStrucForUI);
		}

		// 将订单与预约数据组合起来，用于页面显示
		Map<String, PlansFuturesStruc> plansFuturesMap = new TreeMap<String, PlansFuturesStruc>(
				new Comparator<String>() {
					public int compare(String obj1, String obj2) {
						if(obj1==null) obj1="";
						if(obj2==null) obj2="";
						// 按key升序排序
						return obj1.compareTo(obj2);
					}
				});

		for (PlansFuturesStruc p : plansFuturesStrucs) {
			p.setStatus(new Integer(2).equals(p.getStatus()) ? 4 : 0);
			plansFuturesMap.put(p.getWebIssueCode(), p);
		}
		
		Boolean canStop=true;

		for (String key : plansFuturesMap.keySet()) {
			for (OrdersStruc o : ordersStrucs) {
				if(o.getStatus()==5){
					canStop=false;
				}
				if (o.getWebIssueCode()!=null && o.getWebIssueCode().equals(key)) {
					if (o.getWebIssueCode().equals(key)) {
						plansFuturesMap.get(key).setStatus(o.getStatus());
						plansFuturesMap.get(key).setTotwin(o.getTotwin());
						plansFuturesMap.get(key).setPlanDetailsId(o.getOrderId());
						plansFuturesMap.get(key).setTotamount(o.getTotamount());
						plansFuturesMap.get(key).setNumberRecord(o.getNumberRecord());
						plansFuturesMap.get(key).setPlanCode(o.getOrderCode());
					}
				}
			}
		}

		model.addAttribute("plan", plan);
		model.addAttribute("slips", slipsUIs);
		model.addAttribute("plansFutures", plansFuturesMap.values());
		model.addAttribute("funds", fundTransactionStrucForUIs);
		model.addAttribute("canStop", canStop);

		return "/operations/plan/planDetail";
	}
}