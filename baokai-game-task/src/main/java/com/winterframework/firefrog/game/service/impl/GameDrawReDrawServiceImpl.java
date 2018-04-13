
package com.winterframework.firefrog.game.service.impl;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
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
import com.winterframework.firefrog.game.service.revocation.IGameRevocationIssueService;

/** 
* @ClassName: GameDrawReDrawServiceImpl 
* @Description: 重新开奖的开奖流程
* @author 你的名字 
* @date 2014-5-7 下午3:01:26 
*  
*/
@Service("gameDrawReDrawServiceImpl")
@Transactional
public class GameDrawReDrawServiceImpl extends GameDrawServiceImpl {
	@Resource(name = "gameIssueServiceImpl")
	private IGameIssueService gameIssueService; 

	@Resource(name = "generateGamePlanServiceImpl")
	private IGamePlanService generateGamePlanServiceImpl;
	@Resource(name = "generateNotWinGamePlanServiceImpl")
	private IGamePlanService generateNotWinGamePlanServiceImpl;
	@Resource(name = "gameDrawReDrawServiceImpl")
	private IGameDrawService gameDrawReDrawService;
	@Resource(name = "gameRevocationIssueToBeforeDrawServiceImpl")
	private IGameRevocationIssueService gameRevocationIssueToBeforeDrawServiceImpl;
	
	private static final Logger log = LoggerFactory.getLogger(GameDrawReDrawServiceImpl.class);
 
	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
	public void doBusiness(GameContext ctx, Long lotteryId, Long issueCode) throws Exception {
		//回撤本金，返点以及中奖金额，冻结本金（不需要修改返点表状态为撤销）
		gameRevocationIssueToBeforeDrawServiceImpl.doRevocation(lotteryId, issueCode);
		//开奖流程
		doDraw(ctx, lotteryId, issueCode, true);
	}
	/**
	* Title: createGamePlanOrder
	* Description: 重新计奖的开奖时
	* @param gameOrder 
	 * @throws Exception 
	* @see com.winterframework.firefrog.game.service.impl.GameDrawServiceImpl#createGamePlanOrder(com.winterframework.firefrog.game.dao.vo.GameOrder) 
	*/ 
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
	

	/**
	* Title: checkIssueStatus
	* Description:重新计奖的开奖不需要检查奖期信息
	* @param lotteryId
	* @param issueCode
	* @throws Exception 
	* @see com.winterframework.firefrog.game.service.impl.GameDrawServiceImpl#checkIssueStatus(java.lang.Long, java.lang.Long) 
	*/
	@Override
	boolean checkIssueStatus(GameIssue issue) throws Exception {
		//GameIssueEntity entity = gameIssueService.queryGameIssueByLotteryIdAndIssueCode(lotteryId, issueCode);
		//判断奖期状态是否为开奖号码已确认		
//		if (entity.getStatus().getValue() != 3) {
//			log.error("获取彩种【" + lotteryId + "】奖期期号【" + issueCode + "】信息不是开奖状态。");
//			throw new GameIssueStatusErrorException("获取时时彩【" + lotteryId + "】奖期期号【" + issueCode + "】信息并非处于开奖号码已确认状态");
//		}
		//重新计奖的开奖不需要检查奖期信息
		return true;
	}
	@Override
	protected IGameDrawService getBizInterface() { 
		return gameDrawReDrawService;
	}
	@Override
	protected boolean isNeedTrendTask(String preNumberRecord,
			String numberRecord) {
		//return preNumberRecord==null || !preNumberRecord.equals(numberRecord);
		return true;
	}
}
