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
import com.winterframework.firefrog.game.exception.IssueIsNotOpenAwardException;
import com.winterframework.firefrog.game.service.IGameDrawService;
import com.winterframework.firefrog.game.service.IGameIssueService;
import com.winterframework.firefrog.game.service.IGamePlanService;

/** 
* @ClassName: GameDrawNormalDrawServiceImpl 
* @Description: 正常开奖流程
* @author 你的名字 
* @date 2014-5-6 下午5:52:37 
*  
*/
@Service("gameDrawNormalDrawServiceImpl")
@Transactional
public class GameDrawNormalDrawServiceImpl extends GameDrawServiceImpl {

	private static final Logger log = LoggerFactory.getLogger(GameDrawNormalDrawServiceImpl.class);
	@Resource(name = "gameIssueServiceImpl")
	private IGameIssueService gameIssueService;
	@Resource(name = "normalGamePlanServiceImpl")
	private IGamePlanService normalGamePlanServiceImpl; 
	@Resource(name = "normalNotWinGamePlanServiceImpl")
	private IGamePlanService normalNotWinGamePlanServiceImpl; 
	@Resource(name = "gameDrawNormalDrawServiceImpl")
	private IGameDrawService gameDrawNormalDrawService;
	
	void createGamePlanOrder(ProcessResult result,GameOrder gameOrder) throws Exception {
		
		log.debug("进入正常流程开奖"); 
		Long planId=gameOrder.getPlanId();  
		if (planId != null) { 
			GamePlan plan =gamePlanService.getGamePlanById(planId);
			if(plan!=null){
				if (checkOrderStatus(gameOrder) || plan.getStopMode().intValue()==GamePlan.StopMode.NEVER.getValue()) {   
					Long lotteryId=gameOrder.getLotteryid();
					Long issue_code=gameOrder.getIssueCode();  
					if(gameOrder.getStatus().intValue() == OrderStatus.PRIZE.getValue()){
						normalGamePlanServiceImpl.generateGamePlan(result,lotteryId, issue_code,planId);
					}else{
						normalNotWinGamePlanServiceImpl.generateGamePlan(result,lotteryId, issue_code,planId);
					}  
				}
			}
		}
	}
	
	void createGamePlanOrder(GameContext ctx,ProcessResult result,GameOrder gameOrder) throws Exception {
		
		log.debug("进入正常流程开奖"); 
		Long planId=gameOrder.getPlanId();  
		if (planId != null) { 
			GamePlan plan =gamePlanService.getGamePlanById(planId);
			if(plan!=null){
				if (checkOrderStatus(gameOrder) || plan.getStopMode().intValue()==GamePlan.StopMode.NEVER.getValue()) {   
					Long lotteryId=gameOrder.getLotteryid();
					Long issue_code=gameOrder.getIssueCode();  
					if(gameOrder.getStatus().intValue() == OrderStatus.PRIZE.getValue()){
						normalGamePlanServiceImpl.generateGamePlan(ctx,result,lotteryId, issue_code,planId);
					}else{
						normalNotWinGamePlanServiceImpl.generateGamePlan(ctx,result,lotteryId, issue_code,planId);
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
		//判断奖期状态是否为开奖号码已确认		
		if (issue.getStatus().intValue() != 3) {
			log.error("3.获取时时彩【" + issue.getLotteryid() + "】奖期期号【" + issue.getIssueCode() + "】信息并非处于开奖号码已确认状态。");
			throw new IssueIsNotOpenAwardException("获取时时彩【" + issue.getLotteryid() + "】奖期期号【" + issue.getIssueCode() + "】信息并非处于开奖号码已确认状态");
		}
//		//判断奖期是否已暂停或已撤销
		if(issue.getPauseStatus().intValue() !=1){
			log.error("获取时时彩【" + issue.getLotteryid() + "】奖期期号【" + issue.getIssueCode() + "】已暂停或已撤销。");
			throw new GameIssueStatusErrorException();
		}
		return true;
	};
	
	@Override
	protected IGameDrawService getBizInterface() { 
		return gameDrawNormalDrawService;
	}
	@Override
	protected boolean isNeedTrendTask(String preNumberRecord,
			String numberRecord) {
		//return preNumberRecord==null || !preNumberRecord.equals(numberRecord);
		return true;
	}
}
