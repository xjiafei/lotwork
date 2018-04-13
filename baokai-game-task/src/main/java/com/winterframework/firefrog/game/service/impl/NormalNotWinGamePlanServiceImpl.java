package com.winterframework.firefrog.game.service.impl;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.firefrog.common.util.DateUtils;
import com.winterframework.firefrog.common.util.GameContext;
import com.winterframework.firefrog.common.util.ProcessResult;
import com.winterframework.firefrog.game.dao.vo.GamePlan;
import com.winterframework.firefrog.game.dao.vo.GamePlanDetail;
import com.winterframework.firefrog.game.entity.GameIssueEntity;
import com.winterframework.firefrog.game.entity.GamePlan.GamePlanStatus;
import com.winterframework.firefrog.game.entity.PauseStatus;

/**
 *NormalNotWinGamePlanServiceImpl
 * @ClassName
 * @Description 正常开奖未中奖追号处理
 * @author ibm
 * 2014年8月21日
 */
@Service("normalNotWinGamePlanServiceImpl")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, readOnly = false)
public class NormalNotWinGamePlanServiceImpl extends NormalGamePlanServiceImpl {
	private static final Logger log = Logger.getLogger(NormalGamePlanServiceImpl.class);
 
	@Override
	public void generateGamePlan(ProcessResult result,Long lotteryId, Long issueCode, Long planId)
			throws Exception {  
		log.info("处理正常追号计划，lotteryId=" + lotteryId + ",issueCode=" + issueCode + ",planId=" + planId); 

		GameIssueEntity nextIssue = this.getNextIssueIsNotCancal(lotteryId, issueCode,planId);
		GamePlan plan = getGamePlanById(planId); 

		if (checkStatus(nextIssue, plan)) {
			//正常追号  
			log.debug("nextIssue=" +  nextIssue.getId() + ",planId=" + planId); 
			
			GamePlanDetail detail = gamePlanDetailDao.queryGamePlanDetailByPlanID(planId, nextIssue.getIssueCode());
			if(detail!=null){
				log.debug("detail.getStatus() = " +  detail.getStatus() +"detail.getIssueCode() = " + detail.getIssueCode());
				generateGamePlan(result,detail,issueCode); 
			} 
		}else if(null==nextIssue){
			//如果根据PlanId及奖期获取不到下一期时，属于正常终止。更新追号为终止状态。
			log.info("处理异常追号计划,根据PlanId=" + planId + "。issueCode=" + issueCode + ", 获取不到下一期，属于正常终止，更新追号状态为终止状态。");
			
			plan.setStatus(GamePlan.Status.FINISH.getValue());
			plan.setUpdateTime(DateUtils.currentDate());
			gamePlanDao.update(plan); 
		} 
	}
	@Override
	public void generateGamePlan(GameContext ctx,ProcessResult result,Long lotteryId, Long issueCode, Long planId)
			throws Exception {  
		log.info("处理正常追号计划，lotteryId=" + lotteryId + ",issueCode=" + issueCode + ",planId=" + planId); 

		GameIssueEntity nextIssue = this.getNextIssueIsNotCancal(lotteryId, issueCode,planId);
		log.info("处理正常追号计划，getNextIssueIsNotCancal");
		GamePlan plan = getGamePlanById(planId); 
		log.info("处理正常追号计划，getGamePlanById"); 

		if (checkStatus(nextIssue, plan)) {
			//正常追号  
			log.info("nextIssue=" +  nextIssue.getId() + ",planId=" + planId); 
			
			GamePlanDetail detail = gamePlanDetailDao.queryGamePlanDetailByPlanID(planId, nextIssue.getIssueCode());
			if(detail!=null){
				log.info("detail.getStatus() = " +  detail.getStatus() +"detail.getIssueCode() = " + detail.getIssueCode());
				generateGamePlan(ctx,result,detail,issueCode); 
			} 
		}else if(null==nextIssue){
			//如果根据PlanId及奖期获取不到下一期时，属于正常终止。更新追号为终止状态。
			log.info("处理异常追号计划,根据PlanId=" + planId + "。issueCode=" + issueCode + ", 获取不到下一期，属于正常终止，更新追号状态为终止状态。");
			
			plan.setStatus(GamePlan.Status.FINISH.getValue());
			plan.setUpdateTime(DateUtils.currentDate());
			gamePlanDao.update(plan); 
		} 
	}

	/**校验方案暂停状态:非撤销 & 追号状态：进行中
	 * @param nextIssue
	 * @param plan
	 * @return
	 */
	private boolean checkStatus(GameIssueEntity nextIssue, GamePlan plan) {
		return null!=plan && null != nextIssue && nextIssue.getPauseStatus().getValue() != PauseStatus.CANCAL.getValue()  
				&& plan.getStatus() != GamePlan.Status.PAUSE.getValue();
	}
}
