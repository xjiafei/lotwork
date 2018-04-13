package com.winterframework.firefrog.game.service.revocation.impl;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.firefrog.common.util.DateUtils;
import com.winterframework.firefrog.common.util.GameContext;
import com.winterframework.firefrog.game.dao.vo.GameOrder;
import com.winterframework.firefrog.game.dao.vo.GamePlan;
import com.winterframework.firefrog.game.dao.vo.GamePlanDetail;
import com.winterframework.firefrog.game.entity.CancelMode;
import com.winterframework.firefrog.game.service.ICommonGamePlanService;
import com.winterframework.firefrog.game.service.IGameOrderService;
import com.winterframework.firefrog.game.service.IGamePlanFundService;
 
/**
 * 追号明细撤销处理服务类_已执行
 * @ClassName
 * @Description
 * @author ibm
 * 2014年10月4日
 */
@Service("gameRevocationPlanExecServiceImpl")
@Transactional
public class GameRevocationPlanExecServiceImpl extends AbstractGameRevocationPlanNewService {

	private Logger log = LoggerFactory.getLogger(GameRevocationPlanExecServiceImpl.class);
  
	@Resource(name = "gamePlanFundServiceImpl")
	private IGamePlanFundService gamePlanFundService;
	@Resource(name = "gamePlanService")
	private ICommonGamePlanService gamePlanService;
	@Resource(name = "gameOrderServiceImpl")
	private IGameOrderService gameOrderService;
	 
	@Override
	protected void doFund(GameContext ctx, GamePlan plan, GamePlanDetail detail)
			throws Exception {
		log.info("资金处理");  
		//已经执行，则资金处理交给order
		//this.gamePlanFundService.unfreeze(ctx, plan,detail);
	}
	@Override
	protected void doOrder(GameContext ctx, GamePlan plan, GamePlanDetail detail)
			throws Exception { 
		log.info("订单处理");
		super.doOrder(ctx, plan, detail);
		
		GameOrder order=this.gameOrderService.getOrderByPlanDetailId(detail.getId());
		if(order==null){
			throw new Exception("Game order not exists.");
		}
		this.gameOrderService.cancel(ctx, order);
	}
	@Override
	protected void doPlan(GameContext ctx, GamePlan gamePlan, GamePlanDetail detail)
			throws Exception {  
		GamePlan plan=this.gamePlanService.getById(detail.getPlanid()); 
		if(plan!=null){ 
			Long amount=detail.getTotamount();
			plan.setFinishIssue(plan.getFinishIssue()-1);
			plan.setCancelIssue(plan.getCancelIssue()==null ? 1 : plan.getCancelIssue() + 1);
			plan.setSoldAmount(plan.getSoldAmount()==null ? 0L: (plan.getSoldAmount() - amount)); 
			plan.setCanceledAmount(plan.getCanceledAmount()==null ?0L:(plan.getCanceledAmount()+amount));  
			this.gamePlanService.updateGamePlan(plan);
		}
	}
}
