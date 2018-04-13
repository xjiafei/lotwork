package com.winterframework.firefrog.game.service.impl;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.firefrog.common.util.DateUtils;
import com.winterframework.firefrog.common.util.GameContext;
import com.winterframework.firefrog.common.util.ProcessResult;
import com.winterframework.firefrog.game.dao.IGameOrderDao;
import com.winterframework.firefrog.game.dao.IGameOrderWinDao;
import com.winterframework.firefrog.game.dao.vo.GamePlan;
import com.winterframework.firefrog.game.dao.vo.GamePlanDetail;
import com.winterframework.firefrog.game.entity.GameIssueEntity;
import com.winterframework.firefrog.game.entity.GameIssueStatus;
import com.winterframework.firefrog.game.entity.GamePlan.GamePlanStatus;
import com.winterframework.firefrog.game.service.revocation.IGameRevocationOrderService;

/**
 *GenerateNotWinGamePlanServiceImpl
 * @ClassName
 * @Description 异常（非中奖）追号处理
 * @author ibm
 * 2014年8月25日
 */
@Service("generateNotWinGamePlanServiceImpl")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, readOnly = false)
public class GenerateNotWinGamePlanServiceImpl extends GenerateGamePlanServiceImpl {
	private static final Logger log = Logger.getLogger(GenerateNotWinGamePlanServiceImpl.class);
 
	@Resource(name = "gameRevocationOrderStatusMachineImpl")
	private IGameRevocationOrderService gameRevocationOrderService;

	@Resource(name = "gameOrderDaoImpl")
	private IGameOrderDao orderDao;
	
	@Resource(name = "gameOrderWinDaoImpl")
	private IGameOrderWinDao gameOrderWinDaoImpl;
	
	@Override
	public void generateGamePlan(ProcessResult result,Long lotteryId, Long issueCode, Long planId) throws Exception {

		/**********************************************
		 * 1.接口中传入当前奖期信息，追号服务自动获取追号订单的下一期奖期信息。
		 * 2.如果下一期已经开奖了，进行追号补开。
		 **********************************************/
		log.info("处理异常追号计划，lotteryId=" + lotteryId + ",issueCode=" + issueCode + ",planId=" + planId);

		/*************************************************************
		 * V1: handle plan when draw 
		 * A:正常追号（奖期的顺序下一期）pre-conditions:
		 *  1.追下一期，
		 *  2.下一期没有追号
		 *  3.下一期没有开奖

		 * V2: handle plan when end sale:生成本期的追号订单 前提条件：
		 *  1.本期追号订单没有生成（防止重复生成订单）
		 *  2.计划没有停止&计划的上一期是正常执行或者计划没有停止&计划的第一期就是本期
		 *  
		 * V3: handle plan when redraw, revocation, input draw number
		 * A:追号order（计划的下一期）pre-conditions:
		 *  1.追下一期，
		 *  2.下一期没有追号
		 *  3.下一期没有开奖
		 * B:追号order&补开流程(计划的下一期）pre-conditions:
		 *  1.追下一期，
		 *  2.下一期没有追号
		 *  3.下一期已开奖
		 * C:只做撤销追号N期，不追下一期  （计划的下一期）pre-conditions:
		 *  1.下期已产生追号订单信息
		 *  2.撤销后判断计划,不需要再追
		 * D:先撤销追号N，再追下一期&补开  //to-do（计划的下一期）pre-conditions:
		 *  1.下期已经产生追号订单
		 *  2.撤销后判断计划,需要再追
		 ****************************************************************/

		//获取计划的下一期
		GameIssueEntity nextIssue = this.getNextIssueIsNotCancal(lotteryId, issueCode, planId);

		GamePlan plan = getGamePlanById(planId);
		if (null == nextIssue) {
			//如果根据PlanId及奖期获取不到下一期时，属于正常终止。更新追号为终止状态。
			log.info("处理异常追号计划,根据PlanId=" + planId + "。issueCode=" + issueCode + ", 获取不到下一期，属于正常终止，更新追号状态为终止状态。");
			plan.setStatus(GamePlan.Status.FINISH.getValue());
			plan.setUpdateTime(DateUtils.currentDate());
			gamePlanDao.update(plan);

			return;
		}

		//如果奖期是已生成状态，则退出
		if (nextIssue.getStatus() == GameIssueStatus.CREATE) {
			log.info("处理异常追号计划,奖期未开始销售。issueCode=" + nextIssue.getIssueCode());
			return;
		}

		log.info("处理异常追号计划，lotteryId=" + lotteryId + ",issueCode=" + issueCode + ",planId=" + planId + ", 获取下一奖期："
				+ nextIssue.getIssueCode());

		/*不撤销后续N期--20141103
		 * //判断下一是否已执行追号产生下一期的单
		boolean isPlanOrderNextIssueGenerated = checkPlanOrderNextIssueGenerated(planId, nextIssue.getIssueCode());
		
		//如果追号未生成则也无需撤销N期
		if (isPlanOrderNextIssueGenerated) {
			Set<Long> cancelIssueCode = new HashSet<Long>();
			//先撤销后续追号N期 。
			revocationGamePlanFromNextIssue(plan, nextIssue.getIssueCode(),cancelIssueCode);
		} */
		////此处必须追号，但是如果追号已经生成则不再追号
		////前面回滚追号未追
		//if(!isPlanOrderNextIssueGenerated){
			//更新追号计划明细为未执行（条件为原来非已执行的追号计划明细）
			//1.更新追号计划为进行中
			plan.setStatus(GamePlanStatus.WAITING.getValue());
			gamePlanDao.update(plan);	
			
			//生成下一期的追号。
			createNextIssueGamePlan(result,lotteryId, nextIssue.getIssueCode(), planId,issueCode);
		//}
	} 
	@Override
	public void generateGamePlan(GameContext ctx,ProcessResult result,Long lotteryId, Long issueCode, Long planId) throws Exception {

		/**********************************************
		 * 1.接口中传入当前奖期信息，追号服务自动获取追号订单的下一期奖期信息。
		 * 2.如果下一期已经开奖了，进行追号补开。
		 **********************************************/
		log.info("处理异常追号计划，lotteryId=" + lotteryId + ",issueCode=" + issueCode + ",planId=" + planId);

		/*************************************************************
		 * V1: handle plan when draw 
		 * A:正常追号（奖期的顺序下一期）pre-conditions:
		 *  1.追下一期，
		 *  2.下一期没有追号
		 *  3.下一期没有开奖

		 * V2: handle plan when end sale:生成本期的追号订单 前提条件：
		 *  1.本期追号订单没有生成（防止重复生成订单）
		 *  2.计划没有停止&计划的上一期是正常执行或者计划没有停止&计划的第一期就是本期
		 *  
		 * V3: handle plan when redraw, revocation, input draw number
		 * A:追号order（计划的下一期）pre-conditions:
		 *  1.追下一期，
		 *  2.下一期没有追号
		 *  3.下一期没有开奖
		 * B:追号order&补开流程(计划的下一期）pre-conditions:
		 *  1.追下一期，
		 *  2.下一期没有追号
		 *  3.下一期已开奖
		 * C:只做撤销追号N期，不追下一期  （计划的下一期）pre-conditions:
		 *  1.下期已产生追号订单信息
		 *  2.撤销后判断计划,不需要再追
		 * D:先撤销追号N，再追下一期&补开  //to-do（计划的下一期）pre-conditions:
		 *  1.下期已经产生追号订单
		 *  2.撤销后判断计划,需要再追
		 ****************************************************************/

		//获取计划的下一期
		GameIssueEntity nextIssue = this.getNextIssueIsNotCancal(lotteryId, issueCode, planId);

		GamePlan plan = getGamePlanById(planId);
		if (null == nextIssue) {
			//如果根据PlanId及奖期获取不到下一期时，属于正常终止。更新追号为终止状态。
			log.info("处理异常追号计划,根据PlanId=" + planId + "。issueCode=" + issueCode + ", 获取不到下一期，属于正常终止，更新追号状态为终止状态。");
			plan.setStatus(GamePlan.Status.FINISH.getValue());
			plan.setUpdateTime(DateUtils.currentDate());
			gamePlanDao.update(plan);

			return;
		}
		/*//只要detail未执行则追号
		 * if(plan.getStatus().intValue()==GamePlan.Status.STOP.getValue()){
			log.info("追号计划已经终止->planId:"+plan.getId());
			return;
		}*/

		//如果奖期是已生成状态，则退出
		if (nextIssue.getStatus() == GameIssueStatus.CREATE) {
			log.info("处理异常追号计划,奖期未开始销售。issueCode=" + nextIssue.getIssueCode());
			return;
		}

		log.info("处理异常追号计划，lotteryId=" + lotteryId + ",issueCode=" + issueCode + ",planId=" + planId + ", 获取下一奖期："
				+ nextIssue.getIssueCode());

		/*不撤销后续N期--20141103
		 * //判断下一是否已执行追号产生下一期的单
		boolean isPlanOrderNextIssueGenerated = checkPlanOrderNextIssueGenerated(planId, nextIssue.getIssueCode());
		
		//如果追号未生成则也无需撤销N期
		if (isPlanOrderNextIssueGenerated) {
			Set<Long> cancelIssueCode = new HashSet<Long>();
			//先撤销后续追号N期 。
			revocationGamePlanFromNextIssue(plan, nextIssue.getIssueCode(),cancelIssueCode);
		} */
		////此处必须追号，但是如果追号已经生成则不再追号
		////前面回滚追号未追
		//if(!isPlanOrderNextIssueGenerated){
			//更新追号计划明细为未执行（条件为原来非已执行的追号计划明细）
			//1.更新追号计划为进行中
			plan.setStatus(GamePlanStatus.WAITING.getValue());
			gamePlanDao.update(plan);	
			
			//生成下一期的追号。
			createNextIssueGamePlan(ctx,result,lotteryId, nextIssue.getIssueCode(), planId,issueCode);
		//}
	} 
	/**
	 * 
	* @Title: createNextIssueGamePlan 
	* @Description: 创建下一奖期追号计划。
	* @param lotteryId
	* @param issueCode
	* @param planId
	* @throws Exception
	 */
	private void createNextIssueGamePlan(ProcessResult result,Long lotteryId, Long issueCode, Long planId, Long lastIssueCode) throws Exception {
		GamePlanDetail detail = gamePlanDetailDao.queryGamePlanDetailByPlanID(planId, issueCode);

		if (null != detail) {
			generateGamePlan(result,detail,lastIssueCode);
		} 
	}
	private void createNextIssueGamePlan(GameContext ctx,ProcessResult result,Long lotteryId, Long issueCode, Long planId, Long lastIssueCode) throws Exception {
		GamePlanDetail detail = gamePlanDetailDao.queryGamePlanDetailByPlanID(planId, issueCode);

		if (null != detail) {
			generateGamePlan(ctx,result,detail,lastIssueCode);
		} 
	}
 
}
