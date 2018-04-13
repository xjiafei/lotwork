package com.winterframework.firefrog.game.web.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.winterframework.firefrog.common.annotation.ValidRequestBody;
import com.winterframework.firefrog.common.util.PageConverterUtils;
import com.winterframework.firefrog.game.dao.vo.GamePlan;
import com.winterframework.firefrog.game.entity.GameOrderOperationsEntity;
import com.winterframework.firefrog.game.entity.GamePlanDetail;
import com.winterframework.firefrog.game.entity.GamePlanOperationsEntity;
import com.winterframework.firefrog.game.entity.GameSlipOperationsEntity;
import com.winterframework.firefrog.game.service.IGameOrderService;
import com.winterframework.firefrog.game.service.IGamePlanOperationsService;
import com.winterframework.firefrog.game.service.IGamePlanService;
import com.winterframework.firefrog.game.service.ITransactionRecordService;
import com.winterframework.firefrog.game.web.dto.DTOConvert;
import com.winterframework.firefrog.game.web.dto.FundTransactionStruc;
import com.winterframework.firefrog.game.web.dto.GamePlanOperationsDetailQueryRequest;
import com.winterframework.firefrog.game.web.dto.GamePlanOperationsDetailQueryResponse;
import com.winterframework.firefrog.game.web.dto.GamePlanOperationsQueryDTO;
import com.winterframework.firefrog.game.web.dto.GamePlanOperationsRequest;
import com.winterframework.firefrog.game.web.dto.GamePlanOperationsResponse;
import com.winterframework.firefrog.game.web.dto.OrdersStruc;
import com.winterframework.firefrog.game.web.dto.PlansFuturesStruc;
import com.winterframework.firefrog.game.web.dto.PlansStruc;
import com.winterframework.firefrog.game.web.dto.SlipsStruc;
import com.winterframework.modules.page.Page;
import com.winterframework.modules.page.PageRequest;
import com.winterframework.modules.web.jsonresult.Request;
import com.winterframework.modules.web.jsonresult.Response;
import com.winterframework.modules.web.jsonresult.ResultPager;

/** 
* @ClassName: GamePlanOperationsController 
* @Description: 追号记录运营Controller 
* @author Alan
* @date 2013-10-14 下午3:30:54 
*  
*/
@Controller("gamePlanOperationsController")
@RequestMapping("/game")
public class GamePlanOperationsController {

	private Logger logger = LoggerFactory.getLogger(GamePlanOperationsController.class);

	@Resource(name = "gamePlanOperationsServiceImpl")
	private IGamePlanOperationsService gamePlanOperationsService;

	@Resource(name = "gameOrderServiceImpl")
	private IGameOrderService gameOrderService;

	@Resource(name = "gamePlanServiceImpl")
	private IGamePlanService gamePlanService;

	@Resource(name = "gameTransactionRecordServiceImpl")
	private ITransactionRecordService gameTransactionRecordService;

	/**
	 * 
	* @Title: queryPlanOperations 
	* @Description: 追号记录运营分页列表 
	* @param request
	* @return
	* @throws Exception
	 */
	@RequestMapping(value = "/queryPlanOperations")
	@ResponseBody
	public Response<GamePlanOperationsResponse> queryPlanOperations(
			@RequestBody @ValidRequestBody Request<GamePlanOperationsRequest> request) throws Exception {
		logger.info("query GamePlan Operations start...");
		
		logger.info("device : " + request.getBody().getParam().getDevice());

		Response<GamePlanOperationsResponse> response = new Response<GamePlanOperationsResponse>();

		Long userid = request.getHead().getUserId();
		int sNo = request.getBody().getPager().getStartNo();
		int eNo = request.getBody().getPager().getEndNo();

		GamePlanOperationsQueryDTO queryDTO = new GamePlanOperationsQueryDTO();
		queryDTO.setUserid(userid);
		queryDTO.setQueryRequest(request.getBody().getParam());

		PageRequest<GamePlanOperationsQueryDTO> pr = PageConverterUtils.getRestPageRequest(sNo, eNo);
		pr.setSearchDo(queryDTO);
		pr.setSortColumns(DTOConvert.convert2PlanSortType(queryDTO.getQueryRequest().getSortType()));

		Page<GamePlanOperationsEntity> page = null;
		List<GamePlanOperationsEntity> plans = null;
		List<PlansStruc> plansStrucs = new ArrayList<PlansStruc>();
		GamePlanOperationsResponse result = new GamePlanOperationsResponse();
		PlansStruc plansStruc = null;
		try {
			logger.info("------------------------------------------------");
			page = gamePlanOperationsService.queryGamePlanOperations(pr);
			plans = page.getResult();
			if (plans != null && plans.size() > 0) {
				for (GamePlanOperationsEntity gp : plans) {
					plansStruc = DTOConvert.gamePlanOperationsEntity2PlansStruc(gp);
					plansStrucs.add(plansStruc);
				}
			}
			result.setPlanStruc(plansStrucs);
			response.setResult(result);
			ResultPager pager = new ResultPager();
			pager.setEndNo(page.getThisPageFirstElementNumber());
			pager.setStartNo(page.getThisPageFirstElementNumber());
			pager.setTotal(page.getTotalCount());
			response.setResultPage(pager);

		} catch (Exception e) {
			logger.error("query game plan operations error", e);
			throw e;
		}

		logger.info("query game plan operations end...");
		return response;
	}

	/**
	 * 
	* @Title: queryPlanOperationsListForExport 
	* @Description: 追号记录列表(用于导出excel)
	* @param request
	* @return
	* @throws Exception
	 */
	@RequestMapping(value = "/queryPlanOperationsListForExport")
	@ResponseBody
	public Response<GamePlanOperationsResponse> queryPlanOperationsListForExport(
			@RequestBody @ValidRequestBody Request<GamePlanOperationsRequest> request) throws Exception {
		logger.info("query GamePlan Operations for export start...");

		Response<GamePlanOperationsResponse> response = new Response<GamePlanOperationsResponse>();

		Long userid = request.getHead().getUserId();

		GamePlanOperationsQueryDTO queryDTO = new GamePlanOperationsQueryDTO();
		queryDTO.setUserid(userid);
		queryDTO.setQueryRequest(request.getBody().getParam());

		List<GamePlanOperationsEntity> plans = null;
		List<PlansStruc> plansStrucs = new ArrayList<PlansStruc>();
		GamePlanOperationsResponse result = new GamePlanOperationsResponse();
		PlansStruc plansStruc = null;
		try {
			plans = gamePlanOperationsService.queryGamePlanOperationsList(queryDTO);
			if (plans != null && plans.size() > 0) {
				for (GamePlanOperationsEntity gp : plans) {
					plansStruc = DTOConvert.gamePlanOperationsEntity2PlansStruc(gp);
					plansStrucs.add(plansStruc);
				}
			}
			result.setPlanStruc(plansStrucs);

			response.setResult(result);
		} catch (Exception e) {
			logger.error("query game plan operations for export error", e);
			throw e;
		}

		logger.info("query game plan operations for export end...");
		return response;
	}

	/**
	 * 
	* @Title: queryPlanOperationsDetail 
	* @Description: 追号记录运营详情
	* @param request
	* @return
	* @throws Exception
	 */
	@RequestMapping(value = "/queryPlanOperationsDetail")
	@ResponseBody
	public Response<GamePlanOperationsDetailQueryResponse> queryPlanOperationsDetail(
			@RequestBody @ValidRequestBody Request<GamePlanOperationsDetailQueryRequest> request) throws Exception {
		logger.info("query plan operations detail start...");
		Response<GamePlanOperationsDetailQueryResponse> response = new Response<GamePlanOperationsDetailQueryResponse>();
		GamePlanOperationsDetailQueryResponse result = new GamePlanOperationsDetailQueryResponse();

		Long planid = request.getBody().getParam().getPlanid();
		String planCode = request.getBody().getParam().getPlanCode();

		PlansStruc plansStruc = null;
		List<SlipsStruc> slipsStrucs = new ArrayList<SlipsStruc>();
		List<OrdersStruc> ordersStrucs = new ArrayList<OrdersStruc>();
		List<PlansFuturesStruc> futuresStrucs = new ArrayList<PlansFuturesStruc>();
		// 调用资金接口>查询资金交易记录

		try {
		
			if (planid == null) {
				GamePlan gamePlan = gamePlanService.queryPlanByCode(planCode);
				if(gamePlan == null){
					return response;
				}
				planid = gamePlan.getId();
			}
			plansStruc = DTOConvert.gamePlanOperationsEntity2PlansStruc(gamePlanOperationsService
					.queryGamePlanOperationsDetail(planid));
			List<FundTransactionStruc> fundTransactionStrucs = gameTransactionRecordService
					.queryTransactionRecordPlanCode(planCode,plansStruc.getUserid());
			List<GameSlipOperationsEntity> slipEntityList = gamePlanOperationsService
					.querySlipOperationsListByPlanID(planid);
			if (null == slipEntityList || slipEntityList.size() == 0) {
				slipEntityList = new ArrayList<GameSlipOperationsEntity>();
			} else {
				for (GameSlipOperationsEntity slipEntity : slipEntityList) {
					if (slipEntity != null && slipEntity.getSlipid() != null) { 
						//slipEntity.setRetAward(formatLong(slipEntity.getMoneyMode().intValue(),slipEntity.getRetAward()));
						// 临时解决方案
						SlipsStruc slipsStruc = DTOConvert.gameSlipOperationsEntity2SlipStruc(slipEntity);
						slipsStrucs.add(slipsStruc);
					}
				}
			}

			List<GameOrderOperationsEntity> orderEntityList = gameOrderService.queryOrderOperationsListByPlanID(planid);
			if (null == orderEntityList || orderEntityList.size() == 0) {
				orderEntityList = new ArrayList<GameOrderOperationsEntity>();
			} else {
				for (GameOrderOperationsEntity orderEntity : orderEntityList) {
					if (orderEntity != null && orderEntity.getOrderId() != null) {
						// 临时解决方案
						OrdersStruc ordersStruc = DTOConvert.gameOrderOperationsEntity2OrdersStruc(orderEntity);
						ordersStrucs.add(ordersStruc);
					}
				}
			}

			List<GamePlanDetail> futuresList = gamePlanService.queryPlanDetailsByPlanId(planid);
			if (null == futuresList || futuresList.size() == 0) {
				futuresList = new ArrayList<GamePlanDetail>();
			} else {
				for (GamePlanDetail futureEntity : futuresList) {
					if (futureEntity != null && futureEntity.getId() != null) {
						// 临时解决方案
						PlansFuturesStruc futuresStruc = DTOConvert.gamePlanDetail2PlansFuturesStruc(futureEntity);
						futuresStrucs.add(futuresStruc);
					}
				}
			}

			result.setPlansStruc(plansStruc);
			result.setSlipsStrucs(slipsStrucs);
			result.setOrdersStrucs(ordersStrucs);
			result.setPlansFuturesStrucs(futuresStrucs);
			result.setFundTransactionStrucs(fundTransactionStrucs);

			response.setResult(result);
		} catch (Exception e) {
			logger.error("query plan operations detail error", e);
			throw e;
		}
		logger.info("query plan operations detail end...");

		return response;
	}
	private Long formatLong(int moneyModel,Long aaa){
		//System.out.println(" Long formatLong(Long moneyModel,Long aaa)============"+aaa);
		if(aaa==null) return null;
		if(1==moneyModel){
		    aaa= NumberUtils.toLong(String.valueOf(aaa/100)+"00");
		}else if(3==moneyModel){
		    aaa= NumberUtils.toLong(String.valueOf(aaa/100)+"00");
		}else{
			aaa= NumberUtils.toLong(String.valueOf(aaa/100)+"00");
		}
		//System.out.println(" Long formatLong(Long moneyModel,Long aaa)2============"+aaa);

		return aaa;
	}
}
