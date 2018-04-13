package com.winterframework.firefrog.game.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
import com.winterframework.firefrog.game.dao.IGamePackageDao;
import com.winterframework.firefrog.game.dao.vo.GameOrder;
import com.winterframework.firefrog.game.dao.vo.GameOrderWin;
import com.winterframework.firefrog.game.dao.vo.GamePackage;
import com.winterframework.firefrog.game.dao.vo.GamePlan;
import com.winterframework.firefrog.game.dao.vo.GamePlanDetail;
import com.winterframework.firefrog.game.entity.GameIssueEntity;
import com.winterframework.firefrog.game.entity.GameIssueStatus;
import com.winterframework.firefrog.game.entity.GamePlan.GamePlanStatus;
import com.winterframework.firefrog.game.entity.GamePlanDetail.GamePlanDetailStatus;
import com.winterframework.firefrog.game.service.IGameDrawService;
import com.winterframework.firefrog.game.service.IGameFundRiskService;
import com.winterframework.firefrog.game.service.IGamePlanDetailService;
import com.winterframework.firefrog.game.service.IGamePlanService;
import com.winterframework.firefrog.game.service.revocation.IGameRevocationOrderService;
import com.winterframework.firefrog.game.web.dto.TORiskDTO;
import com.winterframework.firefrog.game.web.util.GameFundTypesUtils;

/**
 * 处理正常的追号计划信息。包含补开流程，主要是审核无法判断是否补开。
* @ClassName: GenerateGamePlanServiceImpl 
* @Description: 异常追号计划
* @author Richard
* @date 2014-5-8 上午9:04:12 
*
 */

@Service("generateGamePlanServiceImpl")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, readOnly = false)
public class GenerateGamePlanServiceImpl extends GamePlanServiceImpl implements IGamePlanService {

	private static final Logger log = Logger.getLogger(GenerateGamePlanServiceImpl.class);

	@Resource(name = "gameDrawRepairDrawServiceImpl")
	private IGameDrawService drawService;

	@Resource(name = "gameRevocationOrderStatusMachineImpl")
	private IGameRevocationOrderService gameRevocationOrderService;

	@Resource(name = "gameOrderDaoImpl")
	private IGameOrderDao orderDao;
	
	@Resource(name = "gameOrderWinDaoImpl")
	private IGameOrderWinDao gameOrderWinDaoImpl;
	
	@Resource(name = "gameFundRiskServiceImpl")
	protected IGameFundRiskService gameFundRiskServiceImpl;
	
	@Resource(name = "gamePackDaoImpl")
	private IGamePackageDao gamePackDaoImpl;
	
	@Resource(name = "gamePlanDetailServiceImpl")
	protected IGamePlanDetailService gamePlanDetailService;
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

		/*//判断下一是否已执行追号产生下一期的单
		boolean isPlanOrderNextIssueGenerated = checkPlanOrderNextIssueGenerated(planId, nextIssue.getIssueCode());
		Set<Long> cancelIssueCode = new HashSet<Long>(); 
		//如果无需继续追号，但是追号已经生成则要撤销N期
			if(isPlanOrderNextIssueGenerated){ 
				revocationGamePlanFromNextIssue(plan, nextIssue.getIssueCode(),cancelIssueCode); 
			}
		//当前的追号计划是否执行
		boolean isExceGamePlan = checkIsExceuteGamePlan(plan,nextIssue.getIssueCode()); 
		
		//如果还需要继续追号，而且追号已经生成则不撤销N期也不再追号------即只有还需要继续追号，而且未生成追号的才继续追号
		if (isExceGamePlan ) {*/
		//判断下一是否已执行追号产生下一期的单
	    boolean isPlanOrderNextIssueGenerated = checkPlanOrderNextIssueGenerated(planId, nextIssue.getIssueCode());
	    Set<Long> cancelIssueCode = new HashSet<Long>(); 
	    //当前的追号计划是否执行
	    boolean isExceGamePlan = checkIsExceuteGamePlan(plan,nextIssue.getIssueCode()); 
	    /////如果无需追号，则要撤销后续N期，但是前面判断是否需要追号的时候已经处理了这些
	    //如果需要继续追号  且 已经生成追号明细
	    /*不撤销后续N期--20141103
	     * if(isExceGamePlan && isPlanOrderNextIssueGenerated){ 
	      revocationGamePlanFromNextIssue(plan, nextIssue.getIssueCode(),cancelIssueCode); 
	    }*/
	    //如果还需要继续追号，而且追号已经生成则不撤销N期也不再追号------即只有还需要继续追号，而且未生成追号的才继续追号
	    //前面回滚追号未追	if (isExceGamePlan && !isPlanOrderNextIssueGenerated) { 
	    if (isExceGamePlan) { 
			//更新追号计划明细为未执行（条件为原来非已执行的追号计划明细）
			//1.更新追号计划为进行中
			plan.setStatus(GamePlanStatus.WAITING.getValue());
			gamePlanDao.update(plan);	
			
			//生成下一期的追号。
			createNextIssueGamePlan(result,lotteryId, nextIssue.getIssueCode(), planId,issueCode);
			
			/*开奖处理补充了补订单开奖的逻辑
			 * //确定是否补开，补开到某期，这期要处于开奖前的状态：已生成奖期，开始销售，截至销售，暂停，
			boolean isDraw = nextIssue.getStatus().getValue() > GameIssueStatus.ACK_DRAW_RESULT.getValue();

			if (isDraw) {
				//补开流程			
				repairGamePlan(nextIssue, lotteryId, planId);
			}*/
		}

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

		/*//判断下一是否已执行追号产生下一期的单
		boolean isPlanOrderNextIssueGenerated = checkPlanOrderNextIssueGenerated(planId, nextIssue.getIssueCode());
		Set<Long> cancelIssueCode = new HashSet<Long>(); 
		//如果无需继续追号，但是追号已经生成则要撤销N期
			if(isPlanOrderNextIssueGenerated){ 
				revocationGamePlanFromNextIssue(plan, nextIssue.getIssueCode(),cancelIssueCode); 
			}
		//当前的追号计划是否执行
		boolean isExceGamePlan = checkIsExceuteGamePlan(plan,nextIssue.getIssueCode()); 
		
		//如果还需要继续追号，而且追号已经生成则不撤销N期也不再追号------即只有还需要继续追号，而且未生成追号的才继续追号
		if (isExceGamePlan ) {*/
		//判断下一是否已执行追号产生下一期的单
	    boolean isPlanOrderNextIssueGenerated = checkPlanOrderNextIssueGenerated(planId, nextIssue.getIssueCode());
	    Set<Long> cancelIssueCode = new HashSet<Long>(); 
	    //当前的追号计划是否执行
	    boolean isExceGamePlan = checkIsExceuteGamePlan(ctx,plan,nextIssue.getIssueCode(),issueCode); 
	    /////如果无需追号，则要撤销后续N期，但是前面判断是否需要追号的时候已经处理了这些
	    //如果需要继续追号  且 已经生成追号明细
	    /*不撤销后续N期--20141103
	     * if(isExceGamePlan && isPlanOrderNextIssueGenerated){ 
	      revocationGamePlanFromNextIssue(plan, nextIssue.getIssueCode(),cancelIssueCode); 
	    }*/
	    //如果还需要继续追号，而且追号已经生成则不撤销N期也不再追号------即只有还需要继续追号，而且未生成追号的才继续追号
	    //前面回滚追号未追	if (isExceGamePlan && !isPlanOrderNextIssueGenerated) { 
	    if (isExceGamePlan) { 
			//更新追号计划明细为未执行（条件为原来非已执行的追号计划明细）
			//1.更新追号计划为进行中
			plan.setStatus(GamePlanStatus.WAITING.getValue());
			gamePlanDao.update(plan);	
			
			//生成下一期的追号。
			createNextIssueGamePlan(ctx,result,lotteryId, nextIssue.getIssueCode(), planId,issueCode);
			
			/*开奖处理补充了补订单开奖的逻辑
			 * //确定是否补开，补开到某期，这期要处于开奖前的状态：已生成奖期，开始销售，截至销售，暂停，
			boolean isDraw = nextIssue.getStatus().getValue() > GameIssueStatus.ACK_DRAW_RESULT.getValue();

			if (isDraw) {
				//补开流程			
				repairGamePlan(nextIssue, lotteryId, planId);
			}*/
		}

	}

	/***
	 * 
	* @Title: checkNextIssueGamePlanDetailRepair 
	* @Description: 判断追号计划明细奖期是否已执行产生下一期的单，如果已执行产生下一期的单，返回true。
	* @param planId
	* @param issueCode
	* @return
	 */
	protected boolean checkPlanOrderNextIssueGenerated(Long planId, Long issueCode) {

		log.info("【checkPlanOrderNextIssueGenerated】判断追号计划明细奖期是否为已执行,planId= " + planId + ", issueCode =" + issueCode);
		GamePlanDetail detail = gamePlanDetailDao.queryGamePlanDetailByPlanIDWithOutStatus(planId, issueCode);

		if (detail== null || detail.getStatus() == GamePlanDetail.Status.UN_EXEC.getValue()) {
			return false;
		}else{
			return true;
		}
		
	}

	/**
	 * B:追号&补开流程
	 *  1.追下一期，
	 *  2.下一期没有追号
	 *  3.下一期已开奖
	* @Title: repairGamePlan 
	* @Description: 
	* @param nextIssue
	* @param lotteryId
	* @param planId
	* @throws Exception
	 */
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, readOnly = false)
	private void repairGamePlan(GameIssueEntity nextIssue, Long lotteryId, Long planId) throws Exception {

		log.info("处理正常追号计划，lotteryId=" + lotteryId + ",issueCode=" + nextIssue.getIssueCode() + ",planId=" + planId
				+ ",追号已处理完成，开始调用补开流程");

		try {
			Long orderId = gamePlanDao.getOrderIdbyPlanId(planId, nextIssue.getIssueCode());
			if (null != orderId) {
				drawService.doBusiness(lotteryId, nextIssue.getIssueCode(), orderId);
			}

		} catch (Exception e) {
			log.error("repairGamePlan get OrderId error:", e);
			throw e;
		}
	}

	/**
	 * 
	* @Title: updateGamePlanByRepair 
	* @Description: 更新追号计划为执行中，追号计划明细为未执行(条件为非已执行追号明细) 
	* @param planId
	* @param issueCode
	 * @param cancelIssueCode 
	* @throws Exception
	 */
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, readOnly = false)
	private void updateGamePlanByRepair(Long planId, Long issueCode, Set<Long> cancelIssueCode) throws Exception {

		//1.更新追号计划为进行中
		GamePlan plan = getGamePlanById(planId);
		plan.setStatus(GamePlan.Status.WAITING.getValue());
		plan.setUpdateTime(DateUtils.currentDate());
		gamePlanDao.update(plan);		
	
	}

	/**
	* @Title: cancalGamePlanFromNextIssue 
	* @Description:  此方法只做撤销N期（置状态：撤销/撤销订单、撤销追号明细、资金审核），不做其余处理
	* @param planId
	* @param nextIssueCode
	* @throws Exception
	 */
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, readOnly = false)
	protected void revocationGamePlanFromNextIssue_unfinish(GamePlan plan, Long currentIssueCode,Set<Long> cancelIssueCode) throws Exception {
		log.info("检查是否撤销已追订单信息。planId=" + plan.getId() + ", issueCode=" + currentIssueCode); 
		int cancelTime = 0 ;
		List<TORiskDTO> betAmountFreezerList = new ArrayList<TORiskDTO>();
		/*****************************
		 * 撤销该追号计划后续奖期已产生订单信息，
		 ****************************/
		List<GameOrder> orderList =orderDao.getOrderFollowedByPlanIdAndIssueCode(plan.getId(), currentIssueCode);

		if(orderList!=null){
			//先将后续已产生的订单信息进行撤销；
			for (GameOrder order : orderList) { 
				int status = order.getStatus().intValue();
				if(status==4) continue;
				if(order.getCancelModes()!=null && order.getCancelModes().intValue()==1) continue;
			 
				order.setCancelModes(-1);
				gameRevocationOrderService.doRevocationNotAskPlanAskRisk(null,order);
				/*order.setPlanId(0L);
				order.setPlanDetailId(0L);*/
				gameOrderService.updateGameOrder(order);
				
				if(status == 2){
					GameOrderWin win = gameOrderWinDaoImpl.selectGameOrderWinByOrderId(order.getId());
					plan.setWinAmount(plan.getWinAmount() - win.getCountWin());
				} 
				if(status != 4){
					TORiskDTO dto = new TORiskDTO();
					dto.setAmount(order.getTotamount()+"");
					dto.setIssueCode(order.getIssueCode());
					dto.setLotteryid(plan.getLotteryid());
					dto.setOrderCodeList(order.getOrderCode());
					dto.setPlanCodeList(plan.getPlanCode());	
					dto.setUserid(order.getUserid()+"");				
					dto.setType(GameFundTypesUtils.GAME_PLAN_FREEZER_DETEAIL_TYPE);
					betAmountFreezerList.add(dto);
				} 
			}	
		}
		//gamePlanDao.update(plan);
		
		//如果存在 系统追中及停或者满足金额后 且 未生成订单的追号明细 且撤销状态的。需要变为可执行；		
		List<GamePlanDetail> detailList = gamePlanDetailDao.getFollowIssueGamePlanDetailWithOutCancel(plan.getId(), currentIssueCode);
		if(null != detailList && !detailList.isEmpty()){ 
			for(GamePlanDetail detail : detailList){ 
				cancelTime++;
				detail.setStatus(GamePlanDetailStatus.CANCEL.getValue());
				detail.setCancelUser("");				
				gamePlanDetailDao.update(detail);
			} 
		}
		plan.setCancelIssue(plan.getCancelIssue()==null?0:(plan.getCancelIssue() - cancelTime));  
		if(!betAmountFreezerList.isEmpty()){
			gameFundRiskServiceImpl.betAmountFreezer(betAmountFreezerList);
		}		
		gamePlanDao.update(plan);
	}
	/**
	 * C:回滚追号N期
	* @Title: cancalGamePlanFromNextIssue 
	* @Description:  
	* @param planId
	* @param nextIssueCode
	* @throws Exception
	 */
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, readOnly = false)
	protected void revocationGamePlanFromNextIssue(GamePlan plan, Long currentIssueCode,Set<Long> cancelIssueCode) throws Exception {
		log.info("检查是否撤销已追订单信息。planId=" + plan.getId() + ", issueCode=" + currentIssueCode);
		boolean isNotFinished = false;
		int cancelTime = 0 ;
		List<TORiskDTO> betAmountFreezerList = new ArrayList<TORiskDTO>();
		/*****************************
		 * 撤销该追号计划后续奖期已产生订单信息，
		 ****************************/
		List<GameOrder> orderList = orderDao.getOrderByCancalGamePlanDetail(plan.getId(), currentIssueCode);
		int subFinishedCount=0;
		Long subSoldAmount=0L;
		if(orderList!=null){
			//先将后续已产生的订单信息进行撤销；
			for (GameOrder order : orderList) {
				int status = order.getStatus().intValue();
				isNotFinished = true;
				//cancelIssueCode.add(order.getIssueCode());
				if(order.getStatus().intValue() == 4){
					cancelTime++;
				}
				order.setCancelModes(-1);
				gameRevocationOrderService.doRevocationNotAskPlanAskRisk(null,order);
				/*order.setPlanId(0L);
				order.setPlanDetailId(0L);*/
				gameOrderService.updateGameOrder(order);
				
				if(status == 2){
					GameOrderWin win = gameOrderWinDaoImpl.selectGameOrderWinByOrderId(order.getId());
					plan.setWinAmount(plan.getWinAmount() - win.getCountWin());
				}else if(status == 5){
					plan.setStatus(GamePlan.Status.WAITING.getValue());  
				}
				if(status != 4){
					TORiskDTO dto = new TORiskDTO();
					dto.setAmount(order.getTotamount()+"");
					dto.setIssueCode(order.getIssueCode());
					dto.setLotteryid(plan.getLotteryid());
					dto.setOrderCodeList(order.getOrderCode());
					dto.setPlanCodeList(plan.getPlanCode());	
					dto.setUserid(order.getUserid()+"");				
					dto.setType(GameFundTypesUtils.GAME_PLAN_FREEZER_DETEAIL_TYPE);
					betAmountFreezerList.add(dto);
				}
				
				GamePlanDetail detail=this.gamePlanDetailDao.getGamePlanDetailByPlanIdAndIssueCode(plan.getId(), order.getIssueCode());
				if(detail.getStatus().intValue()!=GamePlanDetail.Status.UN_EXEC.getValue()){
					subFinishedCount++;
					subSoldAmount+=detail.getTotamount();
					detail.setStatus(GamePlanDetail.Status.UN_EXEC.getValue());
					detail.setRumTime(null);
					detail.setCancelUser("");	
					this.gamePlanDetailDao.update(detail);
				} 
				//gamePlanDetailDao.updateGamePlanDetailWithOutStatus(plan.getId(), order.getIssueCode(), GamePlanDetail.Status.UN_EXEC.getValue(),"");
				
			}	
		}
		//gamePlanDao.update(plan);
		
		//如果存在 系统追中及停或者满足金额后 且 未生成订单的追号明细 且撤销状态的。需要变为可执行；		
		List<GamePlanDetail> detailList = gamePlanDetailDao.getGamePlanDetailByPlanIdAndIssueCodes(plan.getId(), currentIssueCode);
		if(null != detailList && !detailList.isEmpty()){
			isNotFinished = true;	
			for(GamePlanDetail detail : detailList){
				if(detail.getStatus().intValue() == 2){
					
					TORiskDTO dto = new TORiskDTO();
					dto.setAmount(detail.getTotamount()+"");
					dto.setIssueCode(detail.getIssueCode());
					dto.setLotteryid(plan.getLotteryid());
					dto.setOrderCodeList("");
					dto.setPlanCodeList(plan.getPlanCode());	
					if(plan.getUserId() !=null){
						dto.setUserid(plan.getUserId()+"");
					}else{
						GamePackage gamePackage = gamePackDaoImpl.getById(plan.getPackageId());
						dto.setUserid(gamePackage.getUserid()+"");
					}
					dto.setType(GameFundTypesUtils.GAME_PLAN_FREEZER_DETEAIL_TYPE);
					betAmountFreezerList.add(dto);
					cancelTime++;
				}
				if(detail.getStatus().intValue()!=GamePlanDetail.Status.UN_EXEC.getValue()){
					subFinishedCount++;
					subSoldAmount+=detail.getTotamount();
					detail.setStatus(GamePlanDetail.Status.UN_EXEC.getValue());
					detail.setRumTime(null);
					detail.setCancelUser("");	
					this.gamePlanDetailDao.update(detail);
				}  
			}
			
		}
		plan.setCancelIssue((plan.getCancelIssue()==null || (plan.getCancelIssue() - cancelTime)<0)?0:(plan.getCancelIssue() - cancelTime)); 
		plan.setFinishIssue((plan.getFinishIssue()==null || (plan.getFinishIssue() - subFinishedCount)<0)?0:(plan.getFinishIssue() - subFinishedCount)); 
		plan.setSoldAmount((plan.getSoldAmount()==null || (plan.getSoldAmount()-subSoldAmount)<0)?0L:(plan.getSoldAmount()-subSoldAmount));
		if(isNotFinished && plan.getStatus().intValue() == GamePlanStatus.FINISH.getValue()){
			isNotFinished = true;
			plan.setStatus(GamePlanStatus.WAITING.getValue()); 
		}
		if(!betAmountFreezerList.isEmpty()){
			gameFundRiskServiceImpl.betAmountFreezer(betAmountFreezerList);
		}		
		try{
			Map<String,Long> summaryMap=this.gamePlanDetailService.getSummary(plan.getId()); 
			if(summaryMap!=null){
				plan.setFinishIssue(summaryMap.get("finishedCount").intValue());	//已完成奖期数-被暂停追号期数
				plan.setSoldAmount(summaryMap.get("soldAmount"));
				plan.setCancelIssue(summaryMap.get("canceledCount").intValue());
				plan.setCanceledAmount(summaryMap.get("canceledAmount"));
			}else{
				log.info("获取plandetail信息出错，planId="+ plan.getId());
			} 
		}catch(Exception e){
			log.error("获取plan销售金额出错",e);
		}
		plan.setUpdateTime(DateUtils.currentDate());
		gamePlanDao.update(plan);
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

		GamePlan plan = getGamePlanById(planId);
		
		if (checkIsExceuteGamePlan(plan,issueCode)) {

			GamePlanDetail detail = gamePlanDetailDao.queryGamePlanDetailByPlanID(planId, issueCode);

			if (null != detail) {
				generateGamePlan(result,detail,lastIssueCode);
			}
		}

	}
	private void createNextIssueGamePlan(GameContext ctx,ProcessResult result,Long lotteryId, Long issueCode, Long planId, Long lastIssueCode) throws Exception {

		GamePlan plan = getGamePlanById(planId);
		
		if (checkIsExceuteGamePlan(ctx,plan,issueCode,lastIssueCode)) {

			GamePlanDetail detail = gamePlanDetailDao.queryGamePlanDetailByPlanID(planId, issueCode);

			if (null != detail) {
				generateGamePlan(ctx,result,detail,lastIssueCode);
			}
		}

	}

	@Override
	public void generateGamePlan(ProcessResult result,Long lotteryId, Long issueCode) throws Exception {

		log.error("异常追号不在此方法中处理");
		throw new IllegalArgumentException("异常追号不在此方法中处理。请检查");
	}
}
