package com.winterframework.firefrog.game.service.impl;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.firefrog.common.util.GameContext;
import com.winterframework.firefrog.common.util.ProcessResult;
import com.winterframework.firefrog.game.dao.IGameOrderDao;
import com.winterframework.firefrog.game.dao.IGamePlanDao;
import com.winterframework.firefrog.game.dao.vo.GameOrder;
import com.winterframework.firefrog.game.dao.vo.GamePlan;
import com.winterframework.firefrog.game.dao.vo.GamePlanDetail;
import com.winterframework.firefrog.game.entity.GameIssueEntity;
import com.winterframework.firefrog.game.entity.GamePlan.StopMode;
import com.winterframework.firefrog.game.service.IGameIssueEndPlanService;

/**
 * 处理由销售截止产生的追号信息，例如：当前期为第3期，追号计划由第4期开始。
* @ClassName: SaleEndGamePlanServiceImpl 
* @Description: 销售截止时的追号计划
* @author Richard
* @date 2014-5-8 上午9:11:48 
*
 */
@Service("saleEndGamePlanServiceImpl")
@Transactional(rollbackFor=Exception.class)
public class SaleEndGamePlanServiceImpl extends GamePlanServiceImpl implements IGameIssueEndPlanService {

	private static final Logger log = LoggerFactory.getLogger(SaleEndGamePlanServiceImpl.class);

	@Resource(name = "gameOrderDaoImpl")
	private IGameOrderDao orderDao; 
	@Resource(name = "gamePlanDaoImpl")
	private IGamePlanDao gamePlanDao;  
	
	@Override
	public void generatePlanWhenStart(GameContext ctx, ProcessResult result,
			Long lotteryId, Long issueCode) throws Exception {
		/**
		 * 1.找出本奖期是追号第一期且未追号的追号明细 
		 */ 
		log.info("处理奖期销售开始时产生的追号计划信息，lotteryId=" + lotteryId + ",issueCode =" + issueCode);   
		
		//#7598 Fh4.0 后台 - 投注内容空白 :  修正第一筆追號細項被撤銷後下一筆追號訂單無法自動生成的問題 Start		
		//List<GamePlan> planList=gamePlanDao.getByLotteryIdAndStartIssueCode(lotteryId,issueCode);
		//List<GamePlan> planList=gamePlanDao.getGamePlanByLotteryIdAndIssueCodeAndNoOrder(lotteryId,issueCode);
		List<GamePlan> planList=gamePlanDao.getGamePlanByLotteryIdAndIssueCode(lotteryId,issueCode);
		
		//#7598 Fh4.0 后台 - 投注内容空白 :  修正第一筆追號細項被撤銷後下一筆追號訂單無法自動生成的問題 End
		
		if(planList!=null && planList.size()>0){
			for(GamePlan plan:planList){  
				if(plan.getStatus().intValue()==GamePlan.Status.INIT.getValue() || plan.getStatus().intValue()==GamePlan.Status.WAITING.getValue()){
					//第一期追号 && 追中即停 未中奖（跳期追号）
					boolean isNeed=false;
					//若追中即停  判断是否有中奖订单（未开奖不管，开奖逻辑会追） --追号上一期未中奖（不包括未开奖）  则追
					if(plan.getStopMode()==StopMode.WIN_STOP.getValue()){
						GamePlanDetail lastDetail=gamePlanDetailDao.getLastIssueGamePlanDetailWithoutCancel(plan.getId(), issueCode);
						 if(lastDetail!=null){
							GameOrder order=gameOrderService.selectGameOrderByPlanIdAndIssueCode(plan.getId(), lastDetail.getIssueCode()); //中奖了 那该订单就不会生成，故前期必定未中奖
							if(order!=null && order.getStatus()==GameOrder.Status.UN_WIN.getValue()){
								isNeed=true;
							}
						 }
					}
					if(isNeed || gamePlanDao.hasNoOrder(plan.getId())){
						//1.找出本奖期是追号第一期且未追号的追号明细
						GamePlanDetail detail =gamePlanDetailDao.getGamePlanDetailByPlanIdAndIssueCode(plan.getId(),issueCode);
						if(detail!=null && detail.getStatus().intValue()==GamePlanDetail.Status.UN_EXEC.getValue()){
							generateGamePlan(result,detail,null); 
						}
					}
				}
			}
		}
	}
	@Override
	public void generatePlanWhenEnd(GameContext ctx, ProcessResult result,
			Long lotteryId, Long issueCode) throws Exception {
		/**
		 * 1.找出追号不停止且本奖期的下一期未追号 
		 */ 
		log.info("处理奖期销售截止时产生的追号计划信息，lotteryId=" + lotteryId + ",issueCode =" + issueCode);   
		List<GamePlan> planList=gamePlanDao.getGamePlanByLotteryIdAndIssueCode(lotteryId, issueCode);
		if(planList!=null && planList.size()>0){
			for(GamePlan plan:planList){  
				//if(plan.getStatus().intValue()==GamePlan.Status.INIT.getValue() || plan.getStatus().intValue()==GamePlan.Status.WAITING.getValue()){
					if(plan.getStopMode().intValue()==StopMode.NO_STOP.getValue()){
						GameIssueEntity  nextAvailableIssue=this.getNextIssueIsNotCancal(lotteryId, issueCode, plan.getId());
						if(nextAvailableIssue!=null){
							GamePlanDetail detail =gamePlanDetailDao.getGamePlanDetailByPlanIdAndIssueCode(plan.getId(),nextAvailableIssue.getIssueCode());
							if(detail!=null && detail.getStatus().intValue()==GamePlanDetail.Status.UN_EXEC.getValue()){
								generateGamePlan(result,detail,issueCode); 
							}  
						}
					}
				//}
			}
		}
		
	}
	//@Override
	public void generateGamePlan_bk(ProcessResult result,Long lotteryId, Long issueCode, Long planId) throws Exception {

		log.error("奖期销售截止时产生的追号信息不在此方法中处理。");
		throw new IllegalArgumentException("奖期销售截止时产生的追号信息不在此方法中处理。");
	}

	//@Override
	public void generateGamePlan_bk(ProcessResult result,Long lotteryId, Long issueCode) throws Exception {
		log.debug("处理奖期销售截止时产生的追号计划信息，lotteryId=" + lotteryId + ",issueCode =" + issueCode);  
		 
		/**
		 * 1.找出本奖期是追号第一期且未追号的追号明细
		 * 2.找出追号不停止且本奖期的下一期未追号
		 * 以上两分支可能同时执行（追中不停止且是第一期销售截止）
		 */ 
		List<GamePlan> planList=gamePlanDao.getGamePlanByLotteryIdAndIssueCode(lotteryId, issueCode);
		if(planList!=null && planList.size()>0){
			for(GamePlan plan:planList){  
				if(plan.getStatus().intValue()==GamePlan.Status.INIT.getValue() || plan.getStatus().intValue()==GamePlan.Status.WAITING.getValue()){
					//1.找出本奖期是追号第一期且未追号的追号明细
					GamePlanDetail detail=checkFirstPlanIssue(plan.getId(),issueCode);
					if(detail!=null && detail.getStatus().intValue()==GamePlanDetail.Status.UN_EXEC.getValue()){
						generateGamePlan(result,detail,null); 
					}
					//2.找出追号不停止且本奖期的下一期未追号
					if(plan.getStopMode().intValue()==StopMode.NO_STOP.getValue()){
						GameIssueEntity  nextAvailableIssue=this.getNextIssueIsNotCancal(lotteryId, issueCode, plan.getId());
						if(nextAvailableIssue!=null){
							detail =gamePlanDetailDao.getGamePlanDetailByPlanIdAndIssueCode(plan.getId(),nextAvailableIssue.getIssueCode());
							if(detail!=null && detail.getStatus().intValue()==GamePlanDetail.Status.UN_EXEC.getValue()){
								generateGamePlan(result,detail,issueCode); 
							}  
						}
					}
				}
			}
		}
	}
	//@Override
	public void generateGamePlan_bk(GameContext ctx, ProcessResult result,
			Long lotteryId, Long issueCode, Long planId) throws Exception {
		// TODO Auto-generated method stub
		
	}

	/** 
	* @Title: checkGamePlanIsNeedExecute 
	* @Description: 校验该追号机会是否应当执行  
	* @param aimList
	* @param issueCode
	* @return 计划的上一期是正常执行或者计划没有停止&计划的第一期就是本期 返回true，否则返回false
	* Modified By Gary:
	 * 销售截止追号：
	 * 1.追号计划追中不停止，则每一期销售截止都追
	 * 2.追号计划第一期奖期(前台投注的时候已经追号生成订单了，故此处没作用）
	 */ 
	@Deprecated
	public boolean checkGamePlanIsNeedExecute(Long planId, Long issueCode) throws Exception {    
		//根据追号计划id获取追号细节列表
		List<GamePlanDetail> aimList = gamePlanDetailDao.selectGamePlanDetailsByPlanId(planId);
		
		if(aimList!=null && aimList.size()>0){
			//根据奖期排序
			Collections.sort(aimList, new Comparator<GamePlanDetail>() {
				@Override
				public int compare(GamePlanDetail o1, GamePlanDetail o2) {
					return o1.getIssueCode().compareTo(o2.getIssueCode());
				}

			});
			if(aimList.get(0)!=null && aimList.get(0).getIssueCode()!=null && aimList.get(0).getIssueCode().equals(issueCode)){
				return true;
			}
		}
			
		  
		return false;
	}
	
	/**
	 * 校验奖期是否是该追号计划的第一期奖期，是则返回这一期的追号明细，否则返回null
	 * @param planId
	 * @param issueCode
	 * @return
	 * @throws Exception
	 */
	@Deprecated
	protected GamePlanDetail checkFirstPlanIssue(Long planId,Long issueCode) throws Exception {  
		//根据追号计划id获取追号细节列表
		List<GamePlanDetail> detailList = gamePlanDetailDao.selectGamePlanDetailsByPlanId(planId); 
		if(detailList!=null && detailList.size()>0){
			//根据奖期排序
			Collections.sort(detailList, new Comparator<GamePlanDetail>() {
				@Override
				public int compare(GamePlanDetail o1, GamePlanDetail o2) {
					return o1.getIssueCode().compareTo(o2.getIssueCode());
				}

			});
			if(detailList.get(0)!=null && detailList.get(0).getIssueCode()!=null && detailList.get(0).getIssueCode().equals(issueCode)){
				return detailList.get(0);
			}
		} 
		return null;
	}
}
