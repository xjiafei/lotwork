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
import com.winterframework.firefrog.game.dao.vo.GamePlan;
import com.winterframework.firefrog.game.dao.vo.GamePlanDetail;
import com.winterframework.firefrog.game.entity.GameIssueEntity;
import com.winterframework.firefrog.game.entity.GamePlan.GamePlanStatus;
import com.winterframework.firefrog.game.entity.PauseStatus;
import com.winterframework.firefrog.game.service.IGamePlanService;
import com.winterframework.firefrog.game.service.revocation.IGameRevocationOrderService;

/** 
* @ClassName: NormalGamePlanServiceImpl 
* @Description: 正常开奖实现
* @author 你的名字 
* @date 2014-5-14 上午11:49:23 
*  
*/
@Service("normalGamePlanServiceImpl")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, readOnly = false)

public class NormalGamePlanServiceImpl extends GamePlanServiceImpl implements IGamePlanService {

	private static final Logger log = Logger.getLogger(NormalGamePlanServiceImpl.class);

	@Resource(name = "gameRevocationOrderStatusMachineImpl")
	private IGameRevocationOrderService gameRevocationOrderService;

	@Resource(name = "gameOrderDaoImpl")
	private IGameOrderDao orderDao;

	@Override
	public void generateGamePlan(ProcessResult result,Long lotteryId, Long issueCode, Long planId) throws Exception {

		/****************************************
		 * 1.接口中传入当前奖期信息，追号服务自动获取下一期奖期信息。
		 * 2.如果下一期已经开奖了，进行追号补开。
		 ****************************************/
		log.info("处理正常追号计划，lotteryId=" + lotteryId + ",issueCode=" + issueCode + ",planId=" + planId);

		/*******************************************
		 * V1: handle plan when draw 
		 * A:正常追号（奖期的顺序下一期）pre-conditions:
		 *  1.追下一期，
		 *  2.下一期没有追号
		 *  3.下一期没有开奖
		 *******************************************/

		GameIssueEntity nextIssue = this.getNextIssueIsNotCancal(lotteryId, issueCode,planId);
		
		GamePlan plan = getGamePlanById(planId);
		if (null == nextIssue) {
			//如果根据PlanId及奖期获取不到下一期时，属于正常终止。更新追号为终止状态。
			log.info("处理异常追号计划,根据PlanId=" + planId + "。issueCode=" + issueCode + ", 获取不到下一期，属于正常终止，更新追号状态为终止状态。");
			plan.setStatus(GamePlan.Status.FINISH.getValue());
			plan.setUpdateTime(DateUtils.currentDate());
			gamePlanDao.update(plan);

			return;
		}
		
		log.debug("nextIssue=" +  nextIssue.getId() + ",planId=" + planId);
		
		
		//下一奖期暂停状态：非撤销  & 追号状态：进行中
		if (nextIssue.getPauseStatus().getValue()!=PauseStatus.CANCAL.getValue()  && plan.getStatus() == GamePlanStatus.WAITING.getValue()) {
			//正常追号
			createNextIssueGamePlan(result,lotteryId, nextIssue.getIssueCode(), planId,issueCode);
		}
	}
	@Override
	public void generateGamePlan(GameContext ctx,ProcessResult result,Long lotteryId, Long issueCode, Long planId) throws Exception {

		/****************************************
		 * 1.接口中传入当前奖期信息，追号服务自动获取下一期奖期信息。
		 * 2.如果下一期已经开奖了，进行追号补开。
		 ****************************************/
		log.info("处理正常追号计划，lotteryId=" + lotteryId + ",issueCode=" + issueCode + ",planId=" + planId);

		/*******************************************
		 * V1: handle plan when draw 
		 * A:正常追号（奖期的顺序下一期）pre-conditions:
		 *  1.追下一期，
		 *  2.下一期没有追号
		 *  3.下一期没有开奖
		 *******************************************/

		GameIssueEntity nextIssue = this.getNextIssueIsNotCancal(lotteryId, issueCode,planId);
		
		GamePlan plan = getGamePlanById(planId);
		if (null == nextIssue) {
			//如果根据PlanId及奖期获取不到下一期时，属于正常终止。更新追号为终止状态。
			log.info("处理异常追号计划,根据PlanId=" + planId + "。issueCode=" + issueCode + ", 获取不到下一期，属于正常终止，更新追号状态为终止状态。");
			plan.setStatus(GamePlan.Status.FINISH.getValue());
			plan.setUpdateTime(DateUtils.currentDate());
			gamePlanDao.update(plan);

			return;
		}
		
		log.info("nextIssue=" +  nextIssue.getId() + ",planId=" + planId);
		
		/*//只要detail未执行则追号
		 * if(plan.getStatus().intValue()==GamePlan.Status.STOP.getValue()){
			log.info("追号计划已经终止->planId:"+plan.getId());
			return;
		}*/
		//下一奖期暂停状态：非撤销  & 追号状态：进行中
		if (nextIssue.getPauseStatus().getValue()!=PauseStatus.CANCAL.getValue()) {
			//正常追号
			createNextIssueGamePlan(ctx,result,lotteryId, nextIssue.getIssueCode(), planId,issueCode);
		}
	}
 
	private void createNextIssueGamePlan(ProcessResult result,Long lotteryId, Long issueCode, Long planId, Long lastIssueCode) throws Exception {

		GamePlan plan = getGamePlanById(planId);
	
		if (checkIsExceuteGamePlan(plan,issueCode)) {

			GamePlanDetail detail = gamePlanDetailDao.queryGamePlanDetailByPlanID(planId, issueCode);

			if(detail!=null){
				log.debug("detail.getStatus() = " +  detail.getStatus() +
						"detail.getIssueCode() = " + detail.getIssueCode());
			}
			
			if (null != detail) {
				generateGamePlan(result,detail,lastIssueCode);
			}
		}

	}
	private void createNextIssueGamePlan(GameContext ctx,ProcessResult result,Long lotteryId, Long issueCode, Long planId, Long lastIssueCode) throws Exception {

		GamePlan plan = getGamePlanById(planId);
	
		if (checkIsExceuteGamePlan(ctx,plan,issueCode,lastIssueCode)) {

			GamePlanDetail detail = gamePlanDetailDao.queryGamePlanDetailByPlanID(planId, issueCode);

			if(detail!=null){
				log.info("detail.getStatus() = " +  detail.getStatus() +
						"detail.getIssueCode() = " + detail.getIssueCode());
			}
			
			if (null != detail) {
				generateGamePlan(ctx,result,detail,lastIssueCode);
			}
		}

	}

	@Override
	public void generateGamePlan(ProcessResult result,Long lotteryId, Long issueCode) throws Exception {
		//not support
	}
}
