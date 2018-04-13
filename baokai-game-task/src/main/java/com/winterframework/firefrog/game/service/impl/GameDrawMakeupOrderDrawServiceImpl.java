package com.winterframework.firefrog.game.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.firefrog.common.util.GameContext;
import com.winterframework.firefrog.common.util.ProcessResult;
import com.winterframework.firefrog.game.dao.vo.GameIssue;
import com.winterframework.firefrog.game.dao.vo.GameOrder;
import com.winterframework.firefrog.game.dao.vo.GamePlan;
import com.winterframework.firefrog.game.entity.GameOrder.OrderStatus;
import com.winterframework.firefrog.game.service.IGameDrawService;
import com.winterframework.firefrog.game.service.IGameIssueService;
import com.winterframework.firefrog.game.service.IGamePlanService;

 
/**
 *GameDrawMakeupOrderDrawServiceImpl
 * @ClassName
 * @Description 补充订单开奖服务
 * @author ibm
 * 2014年8月22日
 */
@Service("gameDrawMakeupOrderDrawServiceImpl")
@Transactional
public class GameDrawMakeupOrderDrawServiceImpl extends GameDrawServiceImpl {

	@Resource(name = "gameIssueServiceImpl")
	private IGameIssueService gameIssueService;
	@Resource(name = "generateGamePlanServiceImpl")
	private IGamePlanService generateGamePlanServiceImpl; 
	@Resource(name = "generateNotWinGamePlanServiceImpl")
	private IGamePlanService generateNotWinGamePlanServiceImpl; 
	@Resource(name = "gameDrawMakeupOrderDrawServiceImpl")
	private IGameDrawService gameDrawMakeupOrderDrawService;
	
	private static final Logger log = LoggerFactory.getLogger(GameDrawMakeupOrderDrawServiceImpl.class);

	void createGamePlanOrder(ProcessResult result,GameOrder gameOrder) throws Exception {
		log.debug("进入补订单流程开奖"); 
		Long planId=gameOrder.getPlanId();  
		if (planId != null) { 
			GamePlan plan =gamePlanService.getGamePlanById(planId);
			if(plan!=null){
				if (checkOrderStatus(gameOrder) || plan.getStopMode().intValue()==GamePlan.StopMode.NEVER.getValue()) {    
					Long lotteryId=gameOrder.getLotteryid();
					Long issue_code=gameOrder.getIssueCode();  
					if(gameOrder.getStatus().intValue() == OrderStatus.PRIZE.getValue()){
						generateGamePlanServiceImpl.generateGamePlan(result,lotteryId, issue_code,planId);
					}else{
						generateNotWinGamePlanServiceImpl.generateGamePlan(result,lotteryId, issue_code,planId);
					}
				}
			}
		}
	} 
	void createGamePlanOrder(GameContext ctx,ProcessResult result,GameOrder gameOrder) throws Exception {
		log.debug("进入补订单流程开奖"); 
		Long planId=gameOrder.getPlanId();  
		if (planId != null) { 
			GamePlan plan =gamePlanService.getGamePlanById(planId);
			if(plan!=null){
				if (checkOrderStatus(gameOrder) || plan.getStopMode().intValue()==GamePlan.StopMode.NEVER.getValue()) {    
					Long lotteryId=gameOrder.getLotteryid();
					Long issue_code=gameOrder.getIssueCode();  
					if(gameOrder.getStatus().intValue() == OrderStatus.PRIZE.getValue()){
						generateGamePlanServiceImpl.generateGamePlan(ctx,result,lotteryId, issue_code,planId);
					}else{
						generateNotWinGamePlanServiceImpl.generateGamePlan(ctx,result,lotteryId, issue_code,planId);
					} 
				}
			}
		}
	} 
	
	protected List<GameOrder> getDrawOrders(Long lotteryId, Long issueCode)
			throws Exception {  
		return gameOrderService.getFromPlanByIssueAndLottery(lotteryId, issueCode);
	}
	@Override
	protected void updateIssueDrawed(Long lotteryId, Long issueCode,
			GameIssue issue, GameContext ctx, String record) {
		return;
	}
	@Override
	protected void exportSlip(GameIssue issue, boolean hasOrder) {
		if(hasOrder){
			super.exportSlip(issue, hasOrder);
		}
	}
	@Override
	protected void addTrendEvent(GameIssue issue) {
		return;
	}
	/**
	 * 校验订单状态：中奖、未中奖、撤销
	 * @param gameOrder
	 * @return
	 */
	private boolean checkOrderStatus(GameOrder gameOrder) {
		return gameOrder!=null && gameOrder.getStatus().intValue() == OrderStatus.PRIZE.getValue()
				|| gameOrder.getStatus().intValue() == OrderStatus.UN_PRIZE.getValue()
				|| gameOrder.getStatus().intValue() == OrderStatus.CANCEL.getValue();
	}
 
	@Override
	boolean checkIssueStatus(GameIssue issue) throws Exception {
		//已开奖才需补开奖处理
		return issue!=null && issue.getStatus().intValue() >3;
	}
	@Override
	protected IGameDrawService getBizInterface() { 
		return gameDrawMakeupOrderDrawService;
	}

}
