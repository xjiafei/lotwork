package com.winterframework.firefrog.game.service.impl;

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
import com.winterframework.firefrog.game.exception.GameIssueStatusErrorException;
import com.winterframework.firefrog.game.service.IGameDrawService;
import com.winterframework.firefrog.game.service.IGameIssueService;
import com.winterframework.firefrog.game.service.IGamePlanService;

/** 
* @ClassName: GameDrawInputManualDrawServiceImpl 
* @Description:	补开开奖号码的开奖实现
* @author 你的名字 
* @date 2014-5-7 下午2:19:51 
*  
*/
@Service("gameDrawRepairDrawServiceImpl")
@Transactional
public class GameDrawRepairDrawServiceImpl extends GameDrawServiceImpl {

	@Resource(name = "gameIssueServiceImpl")
	private IGameIssueService gameIssueService;
	@Resource(name = "generateGamePlanServiceImpl")
	private IGamePlanService generateGamePlanServiceImpl;
	@Resource(name = "generateNotWinGamePlanServiceImpl")
	private IGamePlanService generateNotWinGamePlanServiceImpl;
	@Resource(name = "gameDrawRepairDrawServiceImpl")
	private IGameDrawService gameDrawRepairDrawService;
	
	private static final Logger log = LoggerFactory.getLogger(GameDrawRepairDrawServiceImpl.class); 
	@Override
	void createGamePlanOrder(ProcessResult result,GameOrder gameOrder) throws Exception { 
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
	@Override
	void createGamePlanOrder(GameContext ctx,ProcessResult result,GameOrder gameOrder) throws Exception { 
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
	

	boolean checkIssueStatus(GameIssue issue) throws Exception {
		//GameIssueEntity entity = gameIssueService.queryGameIssueByLotteryIdAndIssueCode(lotteryId, issueCode);
		//判断奖期状态为已经开奖
		if (issue.getStatus().intValue() < 3) {
			log.error("3.获取时时彩【" + issue.getLotteryid() + "】奖期期号【" + issue.getIssueCode() + "】信息并非处于开奖号码已确认状态。");
			throw new GameIssueStatusErrorException("获取时时彩【" + issue.getLotteryid() + "】奖期期号【" + issue.getIssueCode() + "】信息并非处于开奖号码已确认状态");
		}
		return true;
	};
	@Override
	protected IGameDrawService getBizInterface() { 
		return gameDrawRepairDrawService;
	}
}
