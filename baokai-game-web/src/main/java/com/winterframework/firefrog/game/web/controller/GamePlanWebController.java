package com.winterframework.firefrog.game.web.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

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
import com.winterframework.firefrog.common.redis.RedisClient;
import com.winterframework.firefrog.game.web.acl.IUserStruc;
import com.winterframework.firefrog.game.web.dto.AjaxResponse;
import com.winterframework.firefrog.game.web.dto.DTOConvertor4Web;
import com.winterframework.firefrog.game.web.dto.GameOrderDetailQueryRequest;
import com.winterframework.firefrog.game.web.dto.GameOrderDetailQueryResponse;
import com.winterframework.firefrog.game.web.dto.GameOrderDetailQueryResponseDTO;
import com.winterframework.firefrog.game.web.dto.GamePlanDetailQueryRequest;
import com.winterframework.firefrog.game.web.dto.GamePlanDetailQueryResponse;
import com.winterframework.firefrog.game.web.dto.GamePlanQueryRequest;
import com.winterframework.firefrog.game.web.dto.GamePlanQueryResponse;
import com.winterframework.firefrog.game.web.dto.OrdersStruc;
import com.winterframework.firefrog.game.web.dto.OrdersStrucDTO;
import com.winterframework.firefrog.game.web.dto.PageForView;
import com.winterframework.firefrog.game.web.dto.PlansFuturesStruc;
import com.winterframework.firefrog.game.web.dto.PlansStruc;
import com.winterframework.firefrog.game.web.dto.PlansStrucForUI;
import com.winterframework.firefrog.game.web.dto.ReservationCancelledRequest;
import com.winterframework.firefrog.game.web.dto.SlipsStruc;
import com.winterframework.firefrog.game.web.dto.SlipsStrucDTO;
import com.winterframework.firefrog.game.web.dto.StopGamePlanRequest;
import com.winterframework.firefrog.game.web.util.PageUtils;
import com.winterframework.modules.page.PageRequest;
import com.winterframework.modules.spring.exetend.PropertyConfig;
import com.winterframework.modules.web.jsonresult.Pager;
import com.winterframework.modules.web.jsonresult.Response;
import com.winterframework.modules.web.jsonresult.ResultPager;
import com.winterframework.modules.web.util.JsonMapper;
import com.winterframework.modules.web.util.RequestContext;

/** 
* @ClassName: GamePlanWebController 
* @Description: 追号查询Controller 
* @author Denny 
* @date 2013-9-24 下午2:11:39 
*  
*/
@RequestMapping(value = "/gameUserCenter")
@Controller("gamePlanWebController")
public class GamePlanWebController {

	private Logger logger = LoggerFactory.getLogger(GamePlanWebController.class);

	private JsonMapper jmapper = JsonMapper.nonEmptyMapper();
	@Resource(name = "httpJsonClientImpl")
	private IHttpJsonClient httpClient;

	@PropertyConfig(value = "url.connect")
	private String serverPath;

	@PropertyConfig(value = "url.game.queryPlans")
	private String queryPlansUrl;

	@PropertyConfig(value = "url.game.queryPlanDetail")
	private String queryPlanDetailUrl;
	
	@PropertyConfig(value = "url.game.queryPlanDetailGetPlayName")
	private String queryPlanDetailGetPlayName;

	@PropertyConfig(value = "url.game.stopPlan")
	private String stopPlanUrl;

	@PropertyConfig(value = "url.game.cancellReservation")
	private String cancellReservationUrl;

	@Resource(name = "RedisClient")
	private RedisClient redis;
	
	/** 
	* @Title: queryPlansEnter 
	* @Description:追号记录查询界面入口
	* @return
	*/
	@RequestMapping(value = "/queryPlansEnter")
	public String queryPlansEnter() {
		return "/userCenter/planList";
	}

	/**
	 * 
	* @Title: queryPlans 
	* @Description: 查询追号记录
	* @param request
	* @return
	* @throws Exception
	 */
	@RequestMapping(value = "/queryPlans")
	public String queryPlans(@ModelAttribute("page") PageRequest<GamePlanQueryRequest> page, Integer time,
			@ModelAttribute("request") GamePlanQueryRequest request, Model model) throws Exception {
		logger.info("queryPlans start");
		Long userId = RequestContext.getCurrUser().getId();
		String userAccount = RequestContext.getCurrUser().getUserName();
		Response<GamePlanQueryResponse> response = new Response<GamePlanQueryResponse>();
		Pager pager = PageUtils.getPager(page);

		try {
			if (request.getStartTime() == null) {
				Calendar c = Calendar.getInstance();
				c.setTime(new Date());
				c.set(Calendar.HOUR_OF_DAY, 0);
				c.set(Calendar.WEEK_OF_YEAR, c.get(Calendar.WEEK_OF_YEAR) - 1);
				request.setStartTime(c.getTimeInMillis());
			}
			response = httpClient.invokeHttp(serverPath + queryPlansUrl, request, pager, userId, userAccount,
					GamePlanQueryResponse.class);
			logger.info("queryPlans end");
		} catch (Exception e) {
			logger.error("queryPlans is error.", e);
			throw e;
		}
		ResultPager rp = response.getBody().getPager();
		PageForView pv = PageUtils.getPageForView(page, rp);

		List<PlansStruc> plansStruc = response.getBody().getResult().getPlansStruc();
		List<PlansStrucForUI> plans = new ArrayList<PlansStrucForUI>();
		PlansStrucForUI struc = new PlansStrucForUI();
		for (PlansStruc ps : plansStruc) {
			struc = DTOConvertor4Web.plansStruc2PlansStrucForUI(ps);
			plans.add(struc);
		}
		model.addAttribute("plans", plans);
		model.addAttribute("request", request);
		model.addAttribute("page", pv);
		model.addAttribute("time", time);
		model.addAttribute("orderId",getPlayName(plans,userId));
		return "/userCenter/planList";

	}
	
	/**
	 * requirement #435 建议在“投注记录"、“追号记录”列表中增加一列“玩法”，不然则需点击“查看”按钮才能查询到具体玩法
	 * @Title: getPlayName 
	 * @Description:取得遊戲玩法
	 * @return
	 */
	private List<Object> getPlayName(List<PlansStrucForUI> plans,Long userId) {
		Response<GamePlanDetailQueryResponse> response = new Response<GamePlanDetailQueryResponse>();
		GamePlanDetailQueryRequest request = new GamePlanDetailQueryRequest();
		List<Object> tmpList = new ArrayList<Object>();
		
		for(PlansStrucForUI p : plans){
			Map<String, String> idMapper = new HashMap<String, String>();
			String playString = "";
			Set<String> reloadSet = new HashSet<String>();
			try {
				request.setPlanid(p.getPlanid());
				request.setPlanCode(p.getPlanCode());
				
				response = httpClient.invokeHttp(serverPath + queryPlanDetailGetPlayName, request, userId, null,
						GamePlanDetailQueryResponse.class);
				
				if (response.getBody() != null && response.getBody().getResult() != null) {
					List<SlipsStruc> planSlipsStruc = response.getBody().getResult().getPlanSlipsStrucs();
					PlansStruc ps = response.getBody().getResult().getPlansStruc();
					List<SlipsStrucDTO> planSlipsStrucs = new ArrayList<SlipsStrucDTO>();
					if (planSlipsStruc != null && planSlipsStruc.size() > 0) {
						for (SlipsStruc s : planSlipsStruc) {
							SlipsStrucDTO slipsStrucDTO = DTOConvertor4Web.slipStruc2SlipStrucDTO(s, ps.getLotteryid());
							///北京快乐8只需要玩法群趣味
							if (ps.getLotteryid().longValue() == 99201L && s.getGameGroupCode().longValue() == 18) {
								slipsStrucDTO.setGamePlayName("趣味型");
							}
							//planSlipsStrucs.add(slipsStrucDTO);
							if(reloadSet.contains(slipsStrucDTO.getGamePlayName())){
								continue;
							}else{
								reloadSet.add(slipsStrucDTO.getGamePlayName());
								playString += slipsStrucDTO.getGamePlayName()+", ";
							}
						}
						reloadSet.clear();
						idMapper.put("playName", playString.substring(0, playString.length()-2));
						idMapper.put("orderId", p.getPlanid().toString());
						tmpList.add(idMapper);
					}
				}else if (response.getBody() == null || response.getBody().getResult() == null) {
					idMapper.put("playName", "");
					idMapper.put("orderId", p.getPlanid().toString());
					tmpList.add(idMapper);
				}
				
				
			} catch (Exception e) {
				// TODO: handle exception
				logger.error("getPlayName is error.", e);
				idMapper.put("orderId", p.getPlanid().toString());
				idMapper.put("playName", "");
				tmpList.add(idMapper);
			}
		}
		
		return tmpList;
	}
	
	

	/**
	 * 
	* @Title: queryPlanDetail 
	* @Description: 查询追号详情
	* @param request
	* @return
	* @throws Exception
	 */
	@RequestMapping(value = "/queryPlanDetail")
	public String queryPlanDetail(@RequestParam(required = false) Long planid,
			@RequestParam(required = false) String planCode, Model model) throws Exception {
		logger.info("queryPlanDetail start");
		Response<GamePlanDetailQueryResponse> response = new Response<GamePlanDetailQueryResponse>();
		GamePlanDetailQueryRequest request = new GamePlanDetailQueryRequest();
		request.setPlanid(planid);
		request.setPlanCode(planCode);
		Long userId = RequestContext.getCurrUser().getId();
		try {

			response = httpClient.invokeHttp(serverPath + queryPlanDetailUrl, request, userId, null,
					GamePlanDetailQueryResponse.class);
			logger.info("queryOrderDetail end");
		} catch (Exception e) {
			logger.error("queryOrderDetail is error.", e);
			throw e;
		}

		if (response.getBody() != null && response.getBody().getResult() != null) {

			PlansStruc ps = response.getBody().getResult().getPlansStruc();
			PlansStrucForUI plansStruc = DTOConvertor4Web.plansStruc2PlansStrucForUI(ps);
			List<OrdersStruc> ordersStrucList = response.getBody().getResult().getOrdersStrucs();
			List<PlansFuturesStruc> plansFuturesStrucList = response.getBody().getResult().getPlansFuturesStrucs();
			List<SlipsStruc> ss = response.getBody().getResult().getSlipsStruc();
			List<SlipsStruc> planSlipsStruc = response.getBody().getResult().getPlanSlipsStrucs();
			List<SlipsStrucDTO> slipsStrucs = new ArrayList<SlipsStrucDTO>();
			List<SlipsStrucDTO> planSlipsStrucs = new ArrayList<SlipsStrucDTO>();
			if (ss != null && ss.size() > 0) {
				for (SlipsStruc s : ss) {
					slipsStrucs.add(DTOConvertor4Web.slipStruc2SlipStrucDTO(s, ps.getLotteryid()));
				}
			}

			if (planSlipsStruc != null && planSlipsStruc.size() > 0) {
				for (SlipsStruc s : planSlipsStruc) {
					SlipsStrucDTO slipsStrucDTO = DTOConvertor4Web.slipStruc2SlipStrucDTO(s, ps.getLotteryid());
					///北京快乐8只需要玩法群趣味
					if (ps.getLotteryid().longValue() == 99201L && s.getGameGroupCode().longValue() == 18) {
						slipsStrucDTO.setGamePlayName("趣味型");
					}
					planSlipsStrucs.add(slipsStrucDTO);
				}
			}

			// 将订单与预约数据组合起来，用于页面显示
			Map<String, PlansFuturesStruc> plansFuturesMap = new TreeMap<String, PlansFuturesStruc>(
					new Comparator<String>() {
						public int compare(String obj1, String obj2) {
							// 按key升序排序
							return obj1.compareTo(obj2);
						}
					});

			for (PlansFuturesStruc p : plansFuturesStrucList) {
				/*	if (p.getStatus() == 1) {
				plansFuturesMap.put(p.getWebIssueCode(), null);
				} else {
				plansFuturesMap.put(p.getWebIssueCode(), p);
				}*/
				p.setStatus(p.getStatus() == 2 ? 4 : 0);
				if(p.getWebIssueCode()!=null){
					plansFuturesMap.put(p.getWebIssueCode(), p);					
				}
			}

			for (String key : plansFuturesMap.keySet()) {
				for (OrdersStruc o : ordersStrucList) {
					if (o.getWebIssueCode()!=null && o.getWebIssueCode().equals(key)) {
						if (o.getWebIssueCode()!=null && o.getWebIssueCode().equals(key)) {
							plansFuturesMap.get(key).setStatus(o.getStatus());
							plansFuturesMap.get(key).setTotwin(o.getTotwin());
							plansFuturesMap.get(key).setPlanDetailsId(o.getOrderId());
							plansFuturesMap.get(key).setTotamount(o.getTotamount());
							plansFuturesMap.get(key).setNumberRecord(o.getNumberRecord());
							plansFuturesMap.get(key).setPlanCode(o.getOrderCode());
							plansFuturesMap.get(key).setIsOrder(true);
						}
					}
				}
			}

			model.addAttribute("plansStruc", plansStruc);
			model.addAttribute("slipsStrucs", slipsStrucs);
			model.addAttribute("planSlipsStrucs", planSlipsStrucs);
			model.addAttribute("plansFutures", plansFuturesMap.values());
			model.addAttribute("request", request);
		}
		if (response.getBody() == null || response.getBody().getResult() == null) {
			return this.queryPlans(new PageRequest<GamePlanQueryRequest>(), 7, new GamePlanQueryRequest(), model);
		}
		return "/userCenter/queryPlanDetail";
	}

	/** 
	* @Title: stopPlan
	* @Description: 终止追号
	* @param request
	* @param model
	* @throws Exception
	*/
	@RequestMapping(value = "/stopPlan")
	@ResponseBody
	public Object stopPlan(@RequestParam("planId") Long planId, @RequestParam("userType") Integer userType,
			@ModelAttribute("request") StopGamePlanRequest request, Model model) throws Exception {
		logger.info("stopPlan start");
		AjaxResponse resp = new AjaxResponse();
		@SuppressWarnings("rawtypes")
		Response response = new Response();
		//redis 加鎖
		boolean bo = redis.acquireLock(planId.toString(), 5);
		if(bo){
			request.setPlanId(planId);
			request.setUserType(userType);
			request.setCancelTime(new Date().getTime());
			Long userId = RequestContext.getCurrUser().getId();
			try {
				userId = RequestContext.getCurrUser().getId();
				//判斷是否是管理員撤單
				String date = jmapper.toJson(RequestContext.getCurrUser());
				String userChain = jmapper.fromJson(date, IUserStruc.class).getUserChain();
				if(userChain != null){
					if(userType == 2){
						throw new RuntimeException();
					}
				}
				response = httpClient.invokeHttp(serverPath + stopPlanUrl, request, userId, null, Object.class);
				//redis 解鎖
				redis.releaseLock(planId.toString());
				logger.info("stopPlan end");
				if (response.getHead().getStatus() == 0) {
					resp.setStatus(1l);
					resp.setMessage("计划终止成功。");
				} else if (response.getHead().getStatus() == 200513L) {
					resp.setStatus(3l);
					resp.setMessage("开奖中，计划终止失败!");
				} else if (response.getHead().getStatus() == 202007L) {
					resp.setStatus(2l);
					resp.setMessage("计划终止失败，计划状态状态错误!");
				} else if (response.getHead().getStatus() == 206001L) {
					resp.setStatus(2l);
					resp.setMessage("连接资金系统异常!");
				} else {
					resp.setStatus(2l);
					resp.setMessage("计划终止失败，请检查网络或重试！");
				}
			} catch (Exception e) {
				logger.error("stopPlan is error.", e);
				//redis 解鎖
				redis.releaseLock(planId.toString());
				resp.setStatus(2l);
				resp.setMessage("计划终止失败，请检查网络或重试！");
				return resp;
			}
		}else{
			logger.error("stopPlan is execute.");
			resp.setStatus(2l);
			resp.setMessage("计划终止失败，请检查网络或重试！");
			return resp;
		}
		

		return resp;
	}

	/** 
	* @Title: reservationCalled
	* @Description: 追号预约撤销
	* @param request
	* @param model
	* @throws Exception
	*/
	@RequestMapping(value = "/reservationCalled")
	@ResponseBody
	public Object reservationCalled(@RequestParam("planId") Long planId, @RequestParam("issueCode") Long issueCode, @RequestParam("lotteryId") Long lotteryId,
			@RequestParam("userType") Integer userType,@ModelAttribute("request") ReservationCancelledRequest request, Model model) throws Exception {
		logger.info("reservationCalled start");
		request.setPlanId(planId);
		request.setIssueCode(issueCode);
		request.setUserType(userType);//用户撤销
		if(lotteryId!=null){
			request.setLotteryId(lotteryId);
		}

		AjaxResponse resp = new AjaxResponse();
		Long userId = RequestContext.getCurrUser().getId();
		
		String key = "cancelPlanDetailOrderForUser_"+planId;
		if (!redis.acquireLock(key, 1000)) {
			logger.info("提交过于频繁：planId=" + planId + " ,issueCode="+issueCode);
			resp.setStatus(2l);
			resp.setMessage("预约撤销失败，请检查网络或重试！");
		} else {
			@SuppressWarnings("rawtypes")
			Response response = new Response();
			try {
				userId = RequestContext.getCurrUser().getId();
				//判斷是否是管理員撤單
				String date = jmapper.toJson(RequestContext.getCurrUser());
				String userChain = jmapper.fromJson(date, IUserStruc.class).getUserChain();
				if(userChain != null){
					if(userType == 2){
						throw new RuntimeException();
					}
				}
				response = httpClient.invokeHttp(serverPath + cancellReservationUrl, request, userId, null, Object.class);
				logger.info("reservationCalled end");
				if (response.getHead().getStatus() == 0) {
					resp.setStatus(1l);
					resp.setMessage("预约撤销成功。");
				} else if (response.getHead().getStatus() == 200513L) {
					resp.setStatus(3l);
					resp.setMessage("开奖中，计划终止失败!");
				} else if (response.getHead().getStatus() == 206001L) {
					resp.setStatus(2l);
					resp.setMessage("连接资金系统异常!");
				} else if (response.getHead().getStatus() == 111) {
					resp.setStatus(0l);
					resp.setMessage("该追号的奖期过了销售截止时间!");
				} else {
					resp.setStatus(2l);
					resp.setMessage("预约撤销失败，请检查网络或重试！");
				}
			} catch (Exception e) {
				logger.error("reservationCalled is error.", e);
				resp.setStatus(2l);
				resp.setMessage("预约撤销失败，请检查网络或重试！");
				return resp;
			}
		}
		return resp;
	}

}
