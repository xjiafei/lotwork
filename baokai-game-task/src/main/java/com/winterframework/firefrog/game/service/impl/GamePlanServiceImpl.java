package com.winterframework.firefrog.game.service.impl;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.firefrog.common.redis.RedisClient;
import com.winterframework.firefrog.common.util.DateUtils;
import com.winterframework.firefrog.common.util.FileUtil;
import com.winterframework.firefrog.common.util.GameContext;
import com.winterframework.firefrog.common.util.ProcessResult;
import com.winterframework.firefrog.game.dao.IGameControlEventDao;
import com.winterframework.firefrog.game.dao.IGameIssueDao;
import com.winterframework.firefrog.game.dao.IGameOrderDao;
import com.winterframework.firefrog.game.dao.IGamePackageDao;
import com.winterframework.firefrog.game.dao.IGamePackageItemDao;
import com.winterframework.firefrog.game.dao.IGamePlanDao;
import com.winterframework.firefrog.game.dao.IGamePlanDetailDao;
import com.winterframework.firefrog.game.dao.vo.EnumTypeConverts;
import com.winterframework.firefrog.game.dao.vo.GameControlEvent;
import com.winterframework.firefrog.game.dao.vo.GameOrder;
import com.winterframework.firefrog.game.dao.vo.GamePackageItem;
import com.winterframework.firefrog.game.dao.vo.GamePlan;
import com.winterframework.firefrog.game.dao.vo.GamePlanDetail;
import com.winterframework.firefrog.game.entity.CancelMode;
import com.winterframework.firefrog.game.entity.FileMode;
import com.winterframework.firefrog.game.entity.GameBetType;
import com.winterframework.firefrog.game.entity.GameIssueEntity;
import com.winterframework.firefrog.game.entity.GameSeriesConfigEntity;
import com.winterframework.firefrog.game.entity.GameOrder.OrderParentType;
import com.winterframework.firefrog.game.entity.GameOrder.OrderStatus;
import com.winterframework.firefrog.game.entity.GamePackage;
import com.winterframework.firefrog.game.entity.GamePackage.GamePackageType;
import com.winterframework.firefrog.game.entity.GamePlan.GamePlanStatus;
import com.winterframework.firefrog.game.entity.GamePlan.StopMode;
import com.winterframework.firefrog.game.entity.GamePlanDetail.GamePlanDetailStatus;
import com.winterframework.firefrog.game.entity.Lottery;
import com.winterframework.firefrog.game.entity.PauseStatus;
import com.winterframework.firefrog.game.enums.Separator;
import com.winterframework.firefrog.game.exception.GameIssueISOpenAwardException;
import com.winterframework.firefrog.game.exception.GameIssueNotExistErrorException;
import com.winterframework.firefrog.game.service.ICommonGamePlanService;
import com.winterframework.firefrog.game.service.IGameFundRiskService;
import com.winterframework.firefrog.game.service.IGameIssueService;
import com.winterframework.firefrog.game.service.IGameOrderService;
import com.winterframework.firefrog.game.service.IGamePlanDetailService;
import com.winterframework.firefrog.game.service.IGameSeriesConfigService;
import com.winterframework.firefrog.game.service.revocation.IGameRevocationOrderService;
import com.winterframework.firefrog.game.service.revocation.IGameRevocationPlanService;
import com.winterframework.firefrog.game.web.dto.TORiskDTO;
import com.winterframework.firefrog.game.web.util.GameFundTypesUtils;
import com.winterframework.firefrog.user.entity.User;

/**
 * 
* @ClassName: SSCGamePlanService 
* @Description: 时时彩正常追号计划流程
* @author Richard
* @date 2013-11-18 下午5:37:06 
*
 */
@Service("gamePlanService")
@Transactional
public class GamePlanServiceImpl implements ICommonGamePlanService {

	private static final Logger log = LoggerFactory.getLogger(GamePlanServiceImpl.class);

	@Resource(name = "gameIssueServiceImpl")
	protected IGameIssueService gameIssueService;

	@Resource(name = "gamePlanDaoImpl")
	protected IGamePlanDao gamePlanDao; 	
	@Resource(name = "gameOrderServiceImpl")
	protected IGameOrderService gameOrderService;
	
	@Resource(name = "gamePlanDetailDaoImpl")
	protected IGamePlanDetailDao gamePlanDetailDao; 
	
	@Resource(name = "gamePackDaoImpl")
	protected IGamePackageDao gamePackageDao;

	@Resource(name = "gamePackageItemDao")
	protected IGamePackageItemDao gamePackageItemDao;
	
	@Resource(name = "gameIssueDaoImpl")
	protected IGameIssueDao gameIssueDao;
	
	@Resource(name ="gameRevocationPlanStatusMachineImpl")
	protected IGameRevocationPlanService gameRevocationPlanStatusMachineImpl;

	@Resource(name ="gameRevocationOrderStatusMachineImpl")
	protected IGameRevocationOrderService gameRevocationOrderStatusMachineImpl;
	@Resource(name = "gameControlEventDaoImpl")
	private IGameControlEventDao gameControlEventDao;
	@Resource(name = "gamePlanDetailServiceImpl")
	private IGamePlanDetailService gamePlanDetailService;
	@Resource(name = "gameFundRiskServiceImpl")
	private IGameFundRiskService fundRiskService;
	@Resource(name = "fileUtil")
	private FileUtil fileUtil;
	@Resource(name = "RedisClient")
	private RedisClient redisClient;
	@Resource(name = "gameOrderDaoImpl")
	private IGameOrderDao gameOrderDao;
	@Resource(name = "gameSeriesConfigServiceImpl")
	private IGameSeriesConfigService configService;
	
	@Override
	public int save(GameContext ctx, GamePlan plan) throws Exception {
		if(plan==null) return 0;
		Date curDate=DateUtils.currentDate();
		if(plan.getId()==null){
			plan.setCreateTime(curDate);
			this.gamePlanDao.insert(plan);
		}else{
			plan.setUpdateTime(curDate);
			this.gamePlanDao.update(plan);
		} 
		return 1;
	}
	@Override
	public int pause(GameContext ctx,Long planId) throws Exception {
		/**
		 * 1.暂停plan_detail
		 * 2.暂停plan
		 */
		log.info("开始：暂停追号计划信息，planId="+ planId);
		//暂停planDetail 信息 
		List<GamePlanDetail> detailList=gamePlanDetailDao.selectGamePlanDetailsByPlanId(planId);
		checkGamePlanDetail(planId,detailList);
		int pauseCount=0;
		for(GamePlanDetail detail:detailList){
			pauseCount+=gamePlanDetailService.pause(ctx,detail); 
		} 
		//暂停plan信息
		GamePlan gamePlan = gamePlanDao.getById(planId);
		gamePlan.setStatus(GamePlan.Status.PAUSE.getValue());
		gamePlan.setFinishIssue(gamePlan.getFinishIssue().intValue()-pauseCount);	//已完成奖期数-被暂停追号期数 
		this.save(ctx,gamePlan);  
		log.info("结束：暂停追号计划信息成功，planId="+ planId);
		return 1;
	}
	@Override
	public int continues(GameContext ctx,Long planId) throws Exception {
		/**
		 * 1.继续plan_detail
		 * 2.继续plan
		 */
		log.info("开始：继续执行追号计划，planId="+ planId);
		log.info("继续plan_detail planId="+ planId);
		List<GamePlanDetail> detailList=gamePlanDetailDao.selectGamePlanDetailsByPlanId(planId); 
		checkGamePlanDetail(planId,detailList); 
		//批量更新后续优化
		for(GamePlanDetail detail:detailList){
			gamePlanDetailService.continues(ctx,detail); 
		} 
		log.info("继续plan planId="+ planId);
		GamePlan gamePlan = gamePlanDao.getById(planId); 
		gamePlan.setStatus(GamePlan.Status.WAITING.getValue());
		this.save(ctx,gamePlan);  
		log.info("结束：继续执行追号计划，planId="+ planId);
		return 1;
		
	} 
	@Override
	public int finish(GameContext ctx,Long planId) throws Exception {
		GamePlan plan=this.gamePlanDao.getById(planId);
		plan.setStatus(GamePlan.Status.FINISH.getValue());
		this.save(ctx,plan);  
		return 0; 
	}
	@Override
	public int stop(GameContext ctx,Long planId) throws Exception {
		/**
		 * 与订单无关
		 * 1.终止追号明细（资金）--未执行的追号明细
		 * 2.终止追号（资金=撤单费）
		 */
		//task未曾调用，暂时先不实现		
		return 0;
	}
	 
	@Override
	public GamePlan getById(Long planId) throws Exception {
		return gamePlanDao.getById(planId); 
	}
	//暂停和继续追号逻辑已经去掉
	@Override
	@Transactional(propagation=Propagation.REQUIRED, readOnly=false, rollbackFor=Exception.class)
	public void continueGamePlan(Long planId) throws Exception {
		
		log.info("继续执行追号计划信息成功，planId="+ planId);
		//根据planId 获取planDetail信息。
		//更新Plan为追号计划为进行中。
		GamePlan gamePlan = gamePlanDao.getById(planId);
		
		gamePlanDetailDao.updateGamePlanDetailContinue(planId);
		gamePlan.setStatus(GamePlan.Status.WAITING.getValue());
		this.save(new GameContext(),gamePlan);  
		log.info("继续执行追号计划信息成功，planId="+ planId);
	}

	//暂停和继续追号逻辑已经去掉
	@Override
	@Transactional(propagation=Propagation.REQUIRED, readOnly=false,rollbackFor=Exception.class)
	public void pauseGamePlan(Long planId) throws Exception {
		log.info("暂停追号计划信息，planId="+ planId);
		//暂停planDetail 信息
		GamePlan gamePlan = gamePlanDao.getById(planId);
		
		//处理等待开奖的订单
		List<GameOrder> orderList=gameOrderService.getGameOrderWaiting(planId);	//此处最好传issue_code过来
		int pauseCount=0;
		if(orderList!=null && orderList.size()>0){
			//暂停已执行的追号明细，撤销已生成的订单
			for(GameOrder order:orderList){ 
				//此处应该调用plandetail的暂停行为，可能涉及资金等
				GamePlanDetail detail=gamePlanDetailDao.getById(order.getPlanDetailId()); 
				//追号非未执行状态，则计数被有效暂停
				if(detail.getStatus().intValue()!=GamePlanDetail.Status.UN_EXEC.getValue()){ 
					pauseCount+=1;
				}
				detail.setStatus(GamePlanDetail.Status.PAUSE.getValue());
				gamePlanDetailDao.update(detail);
				order.setStatus(GameOrder.Status.CANCEL.getValue());
				/*order.setPlanId(0L);
				order.setPlanDetailId(0L);*/
				order.setCancelModes(-1);
				gameOrderService.updateGameOrder(order);
			} 
			//暂停未执行的追号明细
			gamePlanDetailDao.updateGamePlanDetailPause(planId);
		}
		//暂停plan信息
		Map<String,Long> summaryMap=this.gamePlanDetailService.getSummary(gamePlan.getId());
		if(summaryMap!=null){
			gamePlan.setFinishIssue(summaryMap.get("finishedCount").intValue());	//已完成奖期数-被暂停追号期数
			gamePlan.setSoldAmount(summaryMap.get("soldAmount"));
			gamePlan.setCancelIssue(summaryMap.get("canceledCount").intValue());
			gamePlan.setCanceledAmount(summaryMap.get("canceledAmount"));
			
		}else{
			log.info("获取plandetail信息出错，planId="+ planId);
		}
		gamePlan.setStatus(GamePlan.Status.PAUSE.getValue());
		this.save(new GameContext(),gamePlan);  
		log.info("暂停追号计划信息成功，planId="+ planId);
	}

	@Override
	public boolean checkHasNextPlanOrder(Long orderId) throws Exception {
		
		GamePlan plan = gamePlanDao.getGamePlanByOrderId(orderId);
		if (null == plan) {
			throw new Exception("generateGamePlan,参数orderId=" + orderId + ",不是追号订单");
		}

		List<GameOrder> gameOrderList = gameOrderService.getNextIssueGameOrder(orderId);
		if (gameOrderList != null && !gameOrderList.isEmpty()) {
			return true;
		}
		return false;
	}

	@Override
	public boolean checkIsExceuteGamePlan(Long gameOrderId) throws Exception {
		
		GamePlan plan = gamePlanDao.getGamePlanByOrderId(gameOrderId);
		if(null == plan){
			return false;
		}
		
		return true;
	}
	
	/**
	 * 
	* @Title: getNextIssue 
	* @Description: 根据彩种及奖期信息，获取奖期顺序的下一期。
	* @param lotteryId
	* @param currentIssueCode
	* @return
	 */
	protected GameIssueEntity getNextIssue(Long lotteryId,Long currentIssueCode) throws Exception{
		
		GameIssueEntity nextIssue = gameIssueDao.getNextGameIssueByIssueAndLotteryId(lotteryId, currentIssueCode);
		
		if(null == nextIssue){
			throw new GameIssueNotExistErrorException();
		}
		return nextIssue;
	}
	
	/**
	 * 
	* @Title: getGamePlanNextIssue 
	* @Description: 根据追号计划Id及奖期，获取计划的下一期奖期信息。 
	* @param currentIssuCode
	* @param planId
	* @return
	 */
	protected GameIssueEntity getGamePlanNextIssue(Long currentIssuCode, Long planId) throws Exception{
		
		GameIssueEntity nextIssue = gameIssueDao.getNextGameIssueByPlanIdAndIssueCode(currentIssuCode, planId);
		return nextIssue;
	}

	
	/**
	 * 
	* @Title: checkIsExceuteGamePlan 
	* @Description: 检查并更新追号状态。
	* @param plan
	* @param cancelIssueCode 后续撤销的开始期号
	* @return
	* @throws Exception
	 */
	protected boolean checkIsExceuteGamePlan(com.winterframework.firefrog.game.dao.vo.GamePlan plan ,Long cancelIssueCode) throws Exception {
		log.info("更新追号计划表中奖信息。");

		boolean b = false;

		if (null == plan) {
			log.error("获取追号计划信息失败, plan 不能为null");
			return b;
		}

		//20140212 add 追号计划为暂停状态时，不进行追号
		if (plan.getStatus().intValue() == GamePlanStatus.PAUSE.getValue()) {

			log.info("该追号计划为暂停暂停，planId=" + plan.getId());
			return b;
		}

		Long totalWinAmount = plan.getWinAmount() == null ? 0L : plan.getWinAmount();
		Long stopAmount = plan.getStopParms() == null ? 0L : plan.getStopParms();
		plan.setWinAmount(totalWinAmount.longValue());
		//停止方式 0.不停止， 1.按累计盈利停止， 2.中奖即停。
		log.info("plan.getStopMode() = " + plan.getStopMode());
		if (totalWinAmount.longValue() > 0 && plan.getStopMode() == StopMode.WIN_STOP.getValue()) {
			//中奖即停
			log.info("追号计划的停止模式为中奖即停，将追号计划停止。");		
			terminatePlan(plan, cancelIssueCode); 
		} else if (plan.getStopMode() == StopMode.STOP_BY_BENIFIT.getValue()) {
			//按累计盈利停止
			if (totalWinAmount.compareTo(stopAmount) >= 0) { 
				log.info("追号计划按累计盈利停止，中奖金额已超过停止参数，将追号计划停止。");
				terminatePlan(plan, cancelIssueCode); 
			} else {
				b = true;
			}
		} else if (plan.getStopMode() == StopMode.NO_STOP.getValue() || totalWinAmount.intValue() == 0) {//20140211 edit 更新中奖即停不执行追号计划问题。
			b = true;
		}

		return b;
	}
	protected boolean checkIsExceuteGamePlan(GameContext ctx,com.winterframework.firefrog.game.dao.vo.GamePlan plan ,Long cancelIssueCode,Long launchIssueCode) throws Exception {
		log.info("更新追号计划表中奖信息。");

		boolean b = false;

		if (null == plan) {
			log.error("获取追号计划信息失败, plan 不能为null");
			return b;
		}

		//20140212 add 追号计划为暂停状态时，不进行追号
		if (plan.getStatus().intValue() == GamePlanStatus.PAUSE.getValue()) {

			log.info("该追号计划为暂停暂停，planId=" + plan.getId());
			return b;
		}

		Long totalWinAmount = plan.getWinAmount() == null ? 0L : plan.getWinAmount();
		Long stopAmount = plan.getStopParms() == null ? 0L : plan.getStopParms();
		plan.setWinAmount(totalWinAmount.longValue());
		Long soldAmount = plan.getSoldAmount() == null ? 0L : plan.getSoldAmount();
		//停止方式 0.不停止， 1.按累计盈利停止， 2.中奖即停。
		log.info("plan.getStopMode() = " + plan.getStopMode());
		if (totalWinAmount.longValue() > 0 && plan.getStopMode() == StopMode.WIN_STOP.getValue()) {
			//中奖即停
			log.info("追号计划的停止模式为中奖即停，将追号计划停止。");		
			terminatePlan(ctx,plan, cancelIssueCode,launchIssueCode); 
		} else if (plan.getStopMode() == StopMode.STOP_BY_BENIFIT.getValue()) {
			//按累计盈利停止--盈利金额=中奖金额-投注本金
			if (totalWinAmount.compareTo(stopAmount+soldAmount) >= 0) {
				log.info("追号计划按累计盈利停止，中奖金额已超过停止参数，将追号计划停止。");
				terminatePlan(ctx,plan, cancelIssueCode,launchIssueCode); 
			} else {
				b = true;
			}
		} else if (plan.getStopMode() == StopMode.NO_STOP.getValue() || totalWinAmount.intValue() == 0) {//20140211 edit 更新中奖即停不执行追号计划问题。
			b = true;
		}

		return b;
	}

	private void terminatePlan(
			com.winterframework.firefrog.game.dao.vo.GamePlan plan,
			Long cancelIssueCode) throws Exception {
		revocationPlanDetail(plan,cancelIssueCode); 
		plan.setStatus(GamePlan.Status.STOP.getValue());//已终止
		plan.setCancelTime(new Date());
		plan.setCancelModes(CancelMode.SYSTEM.getValue()); //系统终止。 
		plan.setCancelIssue(plan.getTotalIssue()-plan.getFinishIssue());
		plan.setCanceledAmount(getCancelTotalAmount(plan));
		this.save(new GameContext(),plan);  
	}
	private void terminatePlan(GameContext ctx,
			com.winterframework.firefrog.game.dao.vo.GamePlan plan,
			Long cancelIssueCode,Long launchIssueCode) throws Exception {
		revocationPlanDetail(ctx,plan,cancelIssueCode,launchIssueCode); 
		plan.setStatus(GamePlan.Status.STOP.getValue());//已终止
		plan.setCancelTime(new Date());
		plan.setCancelModes(CancelMode.SYSTEM.getValue()); //系统终止。 
		plan.setCancelIssue(plan.getTotalIssue()-plan.getFinishIssue());
		plan.setCanceledAmount(getCancelTotalAmount(plan));
		this.save(new GameContext(),plan);  
	}
	
	private Long getCancelTotalAmount(GamePlan plan) throws Exception{
		Long cancelAmout = 0L;
		List<GamePlanDetail> list = getGamePlanDetailByStatus(plan.getId(), GamePlanDetailStatus.CANCEL.getValue());
		if(null != list){
			
			for(GamePlanDetail detail : list){
				cancelAmout += detail.getTotamount();
			}
		}
		return cancelAmout;
	}
	
	private List<GamePlanDetail> getGamePlanDetailByStatus(Long planId, Integer status) throws Exception{
		GamePlanDetail entity = new GamePlanDetail();
		entity.setStatus(status);
		entity.setPlanid(planId);
		
		List<GamePlanDetail> detailList = gamePlanDetailDao.getAllByEntity(entity);
		return detailList;
	}
	
	
	protected void revocationPlanDetail(GamePlan plan,Long cancelIssueCode) throws Exception{
		log.info("撤销追号信息，返还投注金额。");
		plan.setStatus(GamePlan.Status.INIT.getValue());
		plan.setUpdateTime(DateUtils.currentDate());
		List<GamePlanDetail> detailList =gamePlanDetailDao.getFollowIssueGamePlanDetailWithOutCancel(plan.getId(), cancelIssueCode);
		log.info("撤销追号信息1 plan:"+plan.getId()+",issue:"+cancelIssueCode +",couint:"+detailList.size());
		if(detailList != null  && detailList.size()!= 0){ 
			for(GamePlanDetail detail : detailList){
				detail.setCancelUser("-1");
				GameOrder order = gameOrderService.getOrderByPlanDetailId(detail.getId());
				
				if(order != null){
					log.info("调用撤销订单，级联撤销追号");	
					order.setCancelModes(-1);
					//此处是停止追号逻辑（追中即停、盈利即停等），后续追号直接撤销，无需通知可开奖下一期重开奖,此调用待改进   //不再追号
					gameRevocationOrderStatusMachineImpl.doRevocationNotAskPlanAskRisk(null,order);
				}else{
					log.info("调用撤销追号");			
					gameRevocationPlanStatusMachineImpl.doRevocationAndAskRisk(plan, detail, order);
					
					//System.out.println(detail);
				}
		
			}  
			log.info("更新追号计划明细状态为已terminate状态");
			gamePlanDetailDao.updateFollowIssueGamePlanDetailWithOutCancelToCancel(plan.getId(), cancelIssueCode);
		}				
		plan.setStatus(GamePlanStatus.STOP.getValue());		
	}
	protected void revocationPlanDetail(GameContext ctx,GamePlan plan,Long cancelIssueCode,Long launchIssueCode) throws Exception{
		log.info("撤销追号信息，返还投注金额。");
		plan.setStatus(GamePlan.Status.INIT.getValue());
		plan.setUpdateTime(DateUtils.currentDate());
		
		List<GamePlanDetail> detailList =gamePlanDetailDao.getFollowIssueGamePlanDetailWithOutCancel(plan.getId(), cancelIssueCode);
		log.info("撤销追号信息2 plan:"+plan.getId()+",issue:"+cancelIssueCode +",couint:"+detailList.size());
		if(detailList != null && detailList.size()!= 0){ 
			for(GamePlanDetail detail : detailList){
				detail.setCancelUser("-1");
				GameOrder order = gameOrderService.getOrderByPlanDetailId(detail.getId());
				
				if(order != null){
					log.info("调用撤销订单，级联撤销追号");	
					order.setCancelModes(-1);
					//此处是停止追号逻辑（追中即停、盈利即停等），后续追号直接撤销，无需通知可开奖下一期重开奖,此调用待改进   //不再追号
					gameRevocationOrderStatusMachineImpl.doRevocationNotAskPlanAskRisk(ctx,null,order);
				}else{
					log.info("调用撤销追号");		
					//订单未生成，则此时的order为撤销发起者订单
					order=this.gameOrderService.selectGameOrderByPlanIdAndIssueCode(plan.getId(), launchIssueCode);
					order.setStatus(0);//该状态会在处理类选择上启左右，故设置0
					gameRevocationPlanStatusMachineImpl.doRevocationAndAskRisk(ctx,plan, detail, order);
					
					//System.out.println(detail);
				}
		
			}  
			log.info("更新追号计划明细状态为已terminate状态");
			gamePlanDetailDao.updateFollowIssueGamePlanDetailWithOutCancelToCancel(plan.getId(), cancelIssueCode);
		}				
		plan.setStatus(GamePlanStatus.STOP.getValue());		
	}

	/**
	 * 
	* @Title: updateGamePlanDetail 
	* @Description: 更新追号明细信息及追号计划状态
	* @param result
	* @param plan
	 */
	protected Integer updateGamePlanDetail(Long planId, Long issueCode, Integer status,String cancelUser,Integer needStatus) throws Exception {

		log.info("更新追号明细信息， gamePlan.Id = " + planId + ", issueCode = " + issueCode);
		return gamePlanDetailDao.updateGamePlanDetailByPlanID(planId, issueCode, status, cancelUser,needStatus);
	}

	/**
	 * 
	* @Title: updateGamePlan 
	* @Description: 更新追号计划信息。
	* @param plan
	* @param detail
	 */
	protected void updateGamePlan(com.winterframework.firefrog.game.dao.vo.GamePlan plan,
			com.winterframework.firefrog.game.dao.vo.GamePlanDetail detail) {

		log.info("更新追号计划信息， planId =" + plan.getId() + ", planDetail.id=" + detail.getId());
		//在这里只是进行追号，能进入这里表明其追号计划未完成，
		//当前的已完成的奖期数。
		Integer finishedIssue = (plan.getFinishIssue() == null ? 0 : plan.getFinishIssue()) + 1; 
		log.info("更新追号计划信息， planId =" + plan.getId() + ", planDetail.id=" + detail.getId() + ", finishedIssue="
				+ finishedIssue); 
		//总期数
		Integer totalIssue = plan.getTotalIssue();
		log.info("更新追号计划信息， planId =" + plan.getId() + ", planDetail.id=" + detail.getId() + ", totalIssue="
				+ totalIssue);
		//销售完成金额
		BigDecimal soldAmount = new BigDecimal(plan.getSoldAmount() == null ? 0 : plan.getSoldAmount());

		log.info("更新追号计划信息， planId =" + plan.getId() + ", planDetail.id=" + detail.getId() + ",soldAmount ="
				+ soldAmount);
		plan.setFinishIssue(finishedIssue);
		BigDecimal total = new BigDecimal(detail.getTotamount()).add(soldAmount);
		plan.setSoldAmount(total.longValue());  
		//取消参数
		Integer cancelIssue = plan.getCancelIssue() == null ? 0 : plan.getCancelIssue();
		//如果追号明细曾经追过，现在重追（通过是否撤销时间为空判断）则撤销奖期和撤销金额要还原
		if(detail.getCancelTime()!=null){
			plan.setCancelIssue((plan.getCancelIssue()==null ||(cancelIssue-1)<0)?0:(cancelIssue-1));
			plan.setCanceledAmount((plan.getCanceledAmount()==null || (plan.getCanceledAmount()-detail.getTotamount())<0)?0:plan.getCanceledAmount()-detail.getTotamount());	//撤销金额总是单期追号的整数倍，所以不考虑负数
		}
		if (totalIssue - cancelIssue == finishedIssue) {
			//更新追号计划已完成。
			if(plan.getStatus().intValue()==GamePlan.Status.INIT.getValue() || plan.getStatus().intValue()==GamePlan.Status.WAITING.getValue()){
				plan.setStatus(GamePlan.Status.FINISH.getValue()); //已结束				
			}
			log.info("更新追号计划为已结束状态，gamePlan.id" + plan.getId());
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
		log.debug("更新追号计划信息， planId =" + plan.getId() + ", planDetail.id=" + detail.getId() + ",更新成功");
	}
	
	/**
	 * 执行订单追号计划，有任务项目进行调用。
	* Title: generateGamePlan
	* Description:
	* @param planId
	* @param issueCode
	* @throws Exception 
	* @see com.winterframework.firefrog.game.service.IGamePlanService#generateGamePlan(java.lang.Long, java.lang.Long)
	 */
	protected void generateGamePlan(ProcessResult result,com.winterframework.firefrog.game.dao.vo.GamePlanDetail detail,Long lastIssueCode) throws Exception {
		
		//未执行状态或者系统撤销的才追号 
		if(detail.getStatus()==GamePlanDetail.Status.UN_EXEC.getValue() || (detail.getStatus()==GamePlanDetail.Status.CANCEL.getValue() && detail.getCancelUser().equals("-1"))){
			  
			//將追號明細更改為待執行
			Integer updateCount =0;
			updateCount = updateGamePlanDetail(detail.getPlanid(), detail.getIssueCode(), GamePlanDetail.Status.WAIT_EXEC.getValue() ,null,GamePlanDetail.Status.UN_EXEC.getValue());
			if(updateCount == 0){
				log.error("生成追號注單失敗，變態已變更 planDetailId:"+detail.getId());
				return;
			}
			
			boolean isNeedFreeze=false;
			//系统撤销的追号再次追号时组要重新冻结
			if(detail.getStatus()==GamePlanDetail.Status.CANCEL.getValue() && detail.getCancelUser().equals("-1")){
				isNeedFreeze=true;
			}
			log.info("进入生成追号计划方法。game_plan_detail.id=" + detail.getId());
			//1.获取追号信息
			com.winterframework.firefrog.game.dao.vo.GamePlan gamePlanVo = gamePlanDao.getById(detail.getPlanid());
	
			if (null == gamePlanVo) {
				log.error("无法获取gamePlan 信息， planId = " + detail.getPlanid());
				return;
			}
	
			log.info("开始生成订单信息。 gamePlanId =" + gamePlanVo.getId() 
					+ ", WinAmount =" + gamePlanVo.getWinAmount()
					+ ", gamePlanDetailId =" + detail.getId()
					+ ", issue_code = " + detail.getIssueCode());
			//3.生成订单
			/**
			 * 1.判断是否曾经生成订单，是则只更新,同时写入log;否则生成订单
			 */ 
			GameOrder order=this.gameOrderService.getOrderByPlanDetailId(detail.getId());
			com.winterframework.firefrog.game.entity.GameOrder orderStatus;
			if(order==null){
				orderStatus = createGameOrderEntity(gamePlanVo, detail,lastIssueCode,isNeedFreeze);
				if(orderStatus == null){
					log.error("生成追號注單失敗，gamePlanDetailId= " + detail.getPlanid() + ", gamePlandDetail_issueCode = "
							+ detail.getIssueCode());
					return;
				}
			}else if(order.getStatus().intValue()==GameOrder.Status.CANCEL.getValue()){ 
				/*//改进撤销流程未测试
				GameContext ctx=new GameContext();
				this.gameOrderService.reset(ctx, order); */
				this.gameOrderService.reset_tmp(new GameContext(), order);   
			}
	
			log.info("开始更新追号明细信息，gamePlanDetailId= " + detail.getPlanid() + ", gamePlandDetail_issueCode = "
					+ detail.getIssueCode());
			//5.更新追号明细信息。
			updateGamePlanDetail(detail.getPlanid(), detail.getIssueCode(), GamePlanDetail.Status.EXEC.getValue() ,null,GamePlanDetail.Status.WAIT_EXEC.getValue()); 
			log.info("开始生成追号信息，gamePlanId=" + gamePlanVo.getId() 
					+ ", WinAmount =" + gamePlanVo.getWinAmount()
					+ ", gamePlanDetailId =" + detail.getId());
			//4.更新追号信息
			updateGamePlan(gamePlanVo, detail);
			  
			//6.有追号生成订单的奖期列表返回 以待进行补单开奖
			if(result!=null){
				String lotteryId=String.valueOf(gamePlanVo.getLotteryid()); 
				List<Long> issueCodeList=(List<Long>)result.getFromRetParaMap(lotteryId);
				if(issueCodeList==null){
					issueCodeList=new ArrayList<Long>();
					issueCodeList.add(detail.getIssueCode());
				} else if(!issueCodeList.contains(detail.getIssueCode())){
					issueCodeList.add(detail.getIssueCode()); 
				}
				result.setToRetParaMap(lotteryId, issueCodeList);
			}   
		}
	}
	protected void generateGamePlan(GameContext ctx,ProcessResult result,com.winterframework.firefrog.game.dao.vo.GamePlanDetail detail,Long lastIssueCode) throws Exception {
		
		//未执行状态或者系统撤销的才追号 
		if(detail.getStatus()==GamePlanDetail.Status.UN_EXEC.getValue() || (detail.getStatus()==GamePlanDetail.Status.CANCEL.getValue() && detail.getCancelUser().equals("-1"))){
			  
			//將追號明細更改為待執行
			Integer updateCount =0;
			updateCount = updateGamePlanDetail(detail.getPlanid(), detail.getIssueCode(), GamePlanDetail.Status.WAIT_EXEC.getValue() ,null,GamePlanDetail.Status.UN_EXEC.getValue());
			if(updateCount == 0){
				log.error("生成追號注單失敗，變態已變更 planDetailId:"+detail.getId());
				return;
			}
			
			boolean isNeedFreeze=false;
			//系统撤销的追号再次追号时组要重新冻结
			if(detail.getStatus()==GamePlanDetail.Status.CANCEL.getValue() && detail.getCancelUser().equals("-1")){
				isNeedFreeze=true;
			}
			log.info("进入生成追号计划方法。game_plan_detail.id=" + detail.getId());
			//1.获取追号信息
			com.winterframework.firefrog.game.dao.vo.GamePlan gamePlanVo = gamePlanDao.getById(detail.getPlanid());
	
			if (null == gamePlanVo) {
				log.error("无法获取gamePlan 信息， planId = " + detail.getPlanid());
				return;
			}
	
			log.info("开始生成订单信息。 gamePlanId =" + gamePlanVo.getId() 
					+ ", WinAmount =" + gamePlanVo.getWinAmount()
					+ ", gamePlanDetailId =" + detail.getId()
					+ ", issue_code = " + detail.getIssueCode());
			//3.生成订单
			/**
			 * 1.判断是否曾经生成订单，是则只更新,同时写入log;否则生成订单
			 */ 
			GameOrder order=this.gameOrderService.getOrderByPlanDetailId(detail.getId());
			if(order==null){
				createGameOrderEntity(ctx,gamePlanVo, detail,lastIssueCode,isNeedFreeze);			
			}else if(order.getStatus().intValue()==GameOrder.Status.CANCEL.getValue()){ 
				/*//改进撤销流程未测试
				GameContext ctx=new GameContext();
				this.gameOrderService.reset(ctx, order);  */
				this.gameOrderService.reset_tmp(ctx, order);   
			}
	
			log.info("开始更新追号明细信息，gamePlanDetailId= " + detail.getPlanid() + ", gamePlandDetail_issueCode = "
					+ detail.getIssueCode());
			//5.更新追号明细信息。
			updateGamePlanDetail(detail.getPlanid(), detail.getIssueCode(), GamePlanDetail.Status.EXEC.getValue() ,null,GamePlanDetail.Status.WAIT_EXEC.getValue()); 
			log.info("开始生成追号信息，gamePlanId=" + gamePlanVo.getId() 
					+ ", WinAmount =" + gamePlanVo.getWinAmount()
					+ ", gamePlanDetailId =" + detail.getId());
			//4.更新追号信息
			updateGamePlan(gamePlanVo, detail);
			  
			//6.有追号生成订单的奖期列表返回 以待进行补单开奖
			if(result!=null){
				String lotteryId=String.valueOf(gamePlanVo.getLotteryid()); 
				List<Long> issueCodeList=(List<Long>)result.getFromRetParaMap(lotteryId);
				if(issueCodeList==null){
					issueCodeList=new ArrayList<Long>();
					issueCodeList.add(detail.getIssueCode());
				} else if(!issueCodeList.contains(detail.getIssueCode())){
					issueCodeList.add(detail.getIssueCode()); 
				}
				result.setToRetParaMap(lotteryId, issueCodeList);
			}   
		}
	}
	 
	public void addMakeupOrderDrawEvent(Long lotteryId, Long issueCode) throws Exception{
		GameControlEvent event = new GameControlEvent();
		
		GameIssueEntity entity = gameIssueService.queryGameIssueByLotteryIdAndIssueCode(lotteryId, issueCode);
		event.setCreateTime(new Date());
		event.setEndIssueCode(issueCode);
		event.setEnentType(10L);	//10  补订单开奖
		event.setLotteryid(lotteryId); 
		event.setSaleEndTime(entity.getSaleEndTime());
		event.setSaleStartTime(entity.getSaleStartTime());
		event.setStartIssueCode(issueCode);
		event.setStatus(0L);
		event.setParams("lotteryId:"+lotteryId+";"+"issueCode:"+issueCode);
		
		gameControlEventDao.insert(event);
	}
	
	@Override
	public void addMakeupOrderDrawEvent(Long lotteryId, List<Long> issueCodeList)
			throws Exception {
		if(issueCodeList!=null && issueCodeList.size()>0){ 
			for (Long ret_issueCode : issueCodeList) { 
				this.addMakeupOrderDrawEvent(lotteryId, ret_issueCode);
			} 
		}
		
	}
	/**
	 * 
	* @Title: createGameOrder 
	* @Description: 根据追号和明细信息生成订单信息。 
	* @param gamePlanVo
	* @param detail
	* @return
	* @throws Exception
	 */
	protected com.winterframework.firefrog.game.entity.GameOrder createGameOrderEntity(
			com.winterframework.firefrog.game.dao.vo.GamePlan plan,
			com.winterframework.firefrog.game.dao.vo.GamePlanDetail detail,Long lastIssueCode,boolean isNeedFreeze) throws Exception {

		com.winterframework.firefrog.game.entity.GameOrder order = new com.winterframework.firefrog.game.entity.GameOrder();
		//生成Game_order数据
		User user = new User();

		com.winterframework.firefrog.game.dao.vo.GamePackage gamePackageVo = gamePackageDao
				.getById(plan.getPackageId());
		user.setId(gamePackageVo.getUserid());
		GamePackage packageEntity = new GamePackage();
		packageEntity.setUser(user);
		packageEntity.setFileMode(EnumTypeConverts.converFileMode(gamePackageVo.getFileMode()));
		order.setFileMode(EnumTypeConverts.converFileMode(gamePackageVo.getFileMode()));
		order.setGamePackage(packageEntity);
		GameIssueEntity issue=new GameIssueEntity(detail.getIssueCode());
		issue.setWebIssueCode(detail.getWebIssueCode());
		order.setGameIssue(issue);
		order.setTotalAmount(packageEntity.getPackageAmount());
		Lottery lottery = new Lottery();
		lottery.setLotteryId(plan.getLotteryid());
		order.setLottery(lottery);

		order.setTotalAmount(detail.getTotamount());
		order.setStatus(OrderStatus.WAITING);
		order.setSaleTime(new Date());
		order.setCancelModes(CancelMode.DEFAULTS);
		order.setParentType(OrderParentType.PLAN);
		order.setParentid(gamePackageVo.getId());
		
		order.setGamePlanId(plan.getId());
		order.setGamePlanDetailId(detail.getId());
		order.setLastIssueCode(lastIssueCode);
		order.setGamePlanId(plan.getId());
		order.setGamePlanDetailId(detail.getId());
		order.setCancelFee(detail.getCancelFee());
		
		//根据PlanId 先获取gamePackage(VO) 
		//获取第一期的方案记录信息，
		com.winterframework.firefrog.game.dao.vo.GamePackage packe = gamePackageDao.queryGamePackageByPlanId(
				plan.getId(), lottery.getLotteryId(), plan.getStartIsuueCode());
		order.setAwardGroupId(packe.getAwardId());
		log.info("追号计划生成订单，gamePlanId = " + plan.getId() + ", planDetailId =" + detail.getId() + ",packId="
				+ packe.getId());

		//原方案金额不一定合适现在的方案金额。可能存在按倍速追加
		//		order.setPackageAmount(packe.getPackageAmount());

		List<com.winterframework.firefrog.game.entity.GameSlip> gameOrderDetail = new ArrayList<com.winterframework.firefrog.game.entity.GameSlip>();
		//根据GamePackage 获取gamePackageItem 信息
		List<GamePackageItem> itemList = gamePackageItemDao.getPackageItemListByPackageId(packe.getId());

		//开始生成OrderDetail 信息;
		for (GamePackageItem item : itemList) {
			log.info("追号计划生成订单，gamePlanId = " + plan.getId() + ", planDetailId =" + detail.getId() + ",packItemId="
					+ item.getId());
			String content = item.getBetDetail(); 
//			if(order.getFileMode().getValue() == FileMode.FILE.getValue()){
				if (item.getFileMode() == 1) {
					// 文件模式将文件内容读取出来，读取方式直接读取有误问题？
					File file = new File(content); 
					try {
						content = FileUtils.readFileToString(file);
					} catch (IOException e) {
						log.error("读取gamePackageItem投注内容错误，itemId="+item.getId()+e);
						throw new RuntimeException("读取gamePackageItem投注内容错误");
					} 
				} 
//			}
			item.setBetDetail(content);
			gameOrderDetail.add(convertGamePlanRequest2GameOrderDetail(item, order,detail));
		}
		order.setSlipList(gameOrderDetail);
		
		//新增注單前再次檢查追號是不是已被撤銷
		GamePlanDetail detailStatus =gamePlanDetailDao.getById(detail.getId());
		if(detailStatus.getStatus() == GamePlanDetail.Status.CANCEL.getValue() || redisClient.exists("cancelPlanDetail"+detailStatus.getId())){
			log.error("追号计划生成订单失敗，gamePlanId = " + plan.getId() + ", planDetailId =" + detail.getId() + ", 追號單已被撤銷");
			return null;
		}
		Long orderId = gameOrderService.saveGameOrder(order, false, GamePackageType.PLAN, packe.getId(), itemList,isNeedFreeze);
		order.setId(orderId);
		log.info("追号计划生成订单成功，gamePlanId = " + plan.getId() + ", planDetailId =" + detail.getId() + ", 生成的GameOrderId="
				+ orderId);
		return order;
	}
	
	protected com.winterframework.firefrog.game.entity.GameOrder createGameOrderEntity(GameContext ctx,
			com.winterframework.firefrog.game.dao.vo.GamePlan plan,
			com.winterframework.firefrog.game.dao.vo.GamePlanDetail detail,Long lastIssueCode,boolean isNeedFreeze) throws Exception {

		com.winterframework.firefrog.game.entity.GameOrder order = new com.winterframework.firefrog.game.entity.GameOrder();
		//生成Game_order数据
		User user = new User();

		com.winterframework.firefrog.game.dao.vo.GamePackage gamePackageVo = gamePackageDao
				.getById(plan.getPackageId());
		user.setId(gamePackageVo.getUserid());
		GamePackage packageEntity = new GamePackage();
		packageEntity.setUser(user);
		packageEntity.setFileMode(EnumTypeConverts.converFileMode(gamePackageVo.getFileMode()));
		order.setFileMode(EnumTypeConverts.converFileMode(gamePackageVo.getFileMode()));
		order.setGamePackage(packageEntity);
		GameIssueEntity issue=new GameIssueEntity(detail.getIssueCode());
		issue.setWebIssueCode(detail.getWebIssueCode());
		order.setGameIssue(issue);
		order.setTotalAmount(packageEntity.getPackageAmount());
		Lottery lottery = new Lottery();
		lottery.setLotteryId(plan.getLotteryid());
		order.setLottery(lottery);

		order.setTotalAmount(detail.getTotamount());
		order.setStatus(OrderStatus.WAITING);
		order.setSaleTime(new Date());
		order.setCancelModes(CancelMode.DEFAULTS);
		order.setParentType(OrderParentType.PLAN);
		order.setParentid(gamePackageVo.getId());
		
		order.setGamePlanId(plan.getId());
		order.setGamePlanDetailId(detail.getId());
		order.setLastIssueCode(lastIssueCode);
		order.setGamePlanId(plan.getId());
		order.setGamePlanDetailId(detail.getId());
		order.setCancelFee(detail.getCancelFee());
		order.setAwardGroupId(gamePackageVo.getAwardId());
		//根据PlanId 先获取gamePackage(VO) 
		//获取第一期的方案记录信息，
		com.winterframework.firefrog.game.dao.vo.GamePackage packe = gamePackageDao.queryGamePackageByPlanId(
				plan.getId(), lottery.getLotteryId(), plan.getStartIsuueCode());

		log.info("追号计划生成订单，gamePlanId = " + plan.getId() + ", planDetailId =" + detail.getId() + ",packId="
				+ packe.getId());

		//原方案金额不一定合适现在的方案金额。可能存在按倍速追加
		//		order.setPackageAmount(packe.getPackageAmount());

		List<com.winterframework.firefrog.game.entity.GameSlip> gameOrderDetail = new ArrayList<com.winterframework.firefrog.game.entity.GameSlip>();
		//根据GamePackage 获取gamePackageItem 信息
		List<GamePackageItem> itemList = gamePackageItemDao.getPackageItemListByPackageId(packe.getId());

		//开始生成OrderDetail 信息;
		for (GamePackageItem item : itemList) {
			log.info("追号计划生成订单，gamePlanId = " + plan.getId() + ", planDetailId =" + detail.getId() + ",packItemId="
					+ item.getId());
			String content = item.getBetDetail(); 
//			if(order.getFileMode().getValue() == FileMode.FILE.getValue()){ 
				if (item.getFileMode() == 1) {
					// 文件模式将文件内容读取出来，读取方式直接读取有误问题？
					File file = new File(content); 
					try {
						content = FileUtils.readFileToString(file);
					} catch (IOException e) {
						log.error("读取gamePackageItem投注内容错误，itemId="+item.getId()+e);
						throw new RuntimeException("读取gamePackageItem投注内容错误");
					} 
				} 
//			}
			item.setBetDetail(content);
			gameOrderDetail.add(convertGamePlanRequest2GameOrderDetail(item, order,detail));
		}
		order.setSlipList(gameOrderDetail);

		Long orderId = gameOrderService.saveGameOrder(ctx,order, false, GamePackageType.PLAN, packe.getId(), itemList,isNeedFreeze);

		order.setId(orderId);
		log.info("追号计划生成订单成功，gamePlanId = " + plan.getId() + ", planDetailId =" + detail.getId() + ", 生成的GameOrderId="
				+ orderId);
		return order;
	}
	
	

	public com.winterframework.firefrog.game.entity.GameSlip convertGamePlanRequest2GameOrderDetail(
			GamePackageItem detailStruc, com.winterframework.firefrog.game.entity.GameOrder order,GamePlanDetail planDetail) throws Exception {

		com.winterframework.firefrog.game.entity.GameSlip detail = new com.winterframework.firefrog.game.entity.GameSlip();
		//设置奖期
		GameIssueEntity issue = new GameIssueEntity();
		issue.setIssueCode(order.getGameIssue().getIssueCode());
		detail.setIssueCode(issue);
		//初始化圆角模式
		detail.setMoneyMode(EnumTypeConverts.convertMoneyMode(detailStruc.getMoneyMode()));
		detail.setBetDetail(detailStruc.getBetDetail());
		//初始化玩法信息
		GameBetType betType = new GameBetType();
		String betTypeCode = detailStruc.getBetTypeCode();
		String[] betArr = betTypeCode.split("_");
		betType.setGameGroupCode(Integer.valueOf(betArr[0]));
		betType.setGameSetCode(Integer.valueOf(betArr[1]));
		Integer integer = (betArr[2] == null || betArr[2].equals("null")) ? null : Integer.valueOf(betArr[2]);
		betType.setBetMethodCode(integer);
		detail.setGameBetType(betType);

		detail.setTotalBet(Long.valueOf(detailStruc.getTotbets()));
		Long totalAomunt = detailStruc.getTotamount();
		//if (1L == detailStruc.getMoneyMode()) {
		 //追号也没必要在初、10了吧	
		if(order.getLottery().getLotteryId()==99601L||order.getLottery().getLotteryId()==99602L||order.getLottery().getLotteryId()==99603L){
			totalAomunt=Long.valueOf(detailStruc.getTotamount()*planDetail.getMutiple().intValue());
		}else{
			totalAomunt=Long.valueOf(detailStruc.getTotamount()/detailStruc.getMultiple().intValue()*planDetail.getMutiple().intValue());			
		}
		//} else if (2L == detailStruc.getMoneyMode()) {
			//totalAomunt = Long.valueOf(detailStruc.getTotamount()*planDetail.getMutiple().intValue()) / 10;
		//}
		detail.setTotalAmount(totalAomunt);
		detail.setMultiple(planDetail.getMutiple().intValue());

		//初始化注单状态
		detail.setGameSlipStatus(EnumTypeConverts.convertGameSlipStatus(1));
		detail.setCrateTime(new Date()); 
		detail.setFileMode(detailStruc.getFileMode()); 
		detail.setPlanDetailId(planDetail.getId());
		detail.setAwardMode(detailStruc.getAwardMode());
		//String[] retPoints=detailStruc.getRetPointChain().split(Separator.comma);
		//detail.setRetPoint(Long.valueOf(retPoints[retPoints.length-1]));
		if(detailStruc.getRetPoint()!=null)
		detail.setRetPoint(Long.valueOf(detailStruc.getRetPoint()));
		detail.setRetAward(detailStruc.getRetAward());
		detail.setPackageItemId(detailStruc.getId());
		detail.setGameOrder(order);
		return detail;
	}
	

	@Override
	public void updateGamePlanRevocation(Long planId, Long issueCode,boolean isAskGamePlan ,String user) throws Exception {
		
		log.info("updateGamePlanRevocation planId =" + planId + ", issueCode=" + issueCode + ", isAskGamePlan= "+ isAskGamePlan);
		/**********************************
		 * 1.针对于原来下期已产生追号单
		 * 更新追号计划撤销的信息。不包含中奖金额的扣减，
		 * 中奖金额扣减由hugh调用updateGamePlan进行更新。
		 **********************************/
		GamePlan plan = getGamePlanById(planId);
		
		GamePlanDetail detail = gamePlanDetailDao.selectGamePlanDetailsByPlanIdAndIssueCode(planId, issueCode);
		if(null == detail){
			log.error("获取GamePlanDetail失败,planId="+ planId +",issueCode =" + issueCode);
			throw new RuntimeException("获取GamePlanDetail失败，planId="+ planId +",issueCode =" + issueCode);
		}
		
		//追号已执行则撤销时更新完成奖期和已售金额
		if(detail.getStatus().intValue() != GamePlanDetail.Status.UN_EXEC.getValue()){
			plan.setFinishIssue((plan.getFinishIssue()==null || (plan.getFinishIssue()-1)<0)?0:(plan.getFinishIssue()-1));
			plan.setSoldAmount((plan.getSoldAmount()==null || (plan.getSoldAmount() - detail.getTotamount())<0) ? plan.getSoldAmount() : (plan.getSoldAmount() - detail.getTotamount()));
		}
//		plan.setStatus(GamePlanStatus.STOP.getValue());
		plan.setCancelTime(new Date());
		plan.setCancelModes(CancelMode.SYSTEM.getValue());
		plan.setCancelIssue(plan.getCancelIssue()==null ? 1 : plan.getCancelIssue() + 1);
		Long canceledAmount = plan.getCanceledAmount()==null ? detail.getTotamount() : plan.getCanceledAmount() + detail.getTotamount();
		plan.setCanceledAmount(canceledAmount); 
		detail.setCancelUser(user);
		//isAskGamePlan 是否要进行追号。
		if(isAskGamePlan){
			if(detail.getStatus()==GamePlanDetailStatus.EXEC.getValue()){
				plan.setSoldAmount(plan.getSoldAmount()-detail.getTotamount()>0 ?plan.getSoldAmount()-detail.getTotamount() : 0 );
			}			
			detail.setStatus(GamePlanDetail.Status.CANCEL.getValue());
		}else{
			if(detail.getStatus().intValue() != GamePlanDetail.Status.UN_EXEC.getValue()){
				detail.setStatus(GamePlanDetail.Status.UN_EXEC.getValue());
				plan.setFinishIssue(plan.getFinishIssue()-1);
				plan.setSoldAmount((plan.getSoldAmount()==null ||  (plan.getSoldAmount() - detail.getTotamount()<0))?0:( plan.getSoldAmount() - detail.getTotamount()));
			}
		}
		detail.setCancelTime(new Date());
		gamePlanDetailDao.update(detail);
		
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
		gamePlanDao.update(plan);
		
	}
	
	
	@Override
	public GamePlan getGamePlanById(Long id) throws Exception {
		return gamePlanDao.getById(id);
	}
	@Override
	public GamePlan getByPackageId(Long packageId) throws Exception {
		return gamePlanDao.getByPackageId(packageId);
	}

	@Override
	public void updateGamePlan(GamePlan plan) throws Exception {
		gamePlanDao.update(plan);
	}

	@Override
	public GamePlan getGamePlanByOrderId(Long orderId) throws Exception {
		return gamePlanDao.getGamePlanByOrderId(orderId);
	}
	
	/**
	 * 
	* @Title: checkNextIssueIsCancal 
	* @Description: 检查下一期是否已撤销。
	* @param lotteryId
	* @param issueCode
	* @return
	 */
	protected GameIssueEntity getNextIssueIsNotCancal(Long lotteryId, Long issueCode, Long planId) throws Exception{ 
		GameIssueEntity nextIssue = getGamePlanNextIssue(issueCode,planId);
		if(nextIssue!=null){
			GamePlanDetail detail = gamePlanDetailDao.queryGamePlanDetailByPlanIDWithOutStatus(planId, nextIssue.getIssueCode());
			
			if(detail!=null){
				log.debug("detail.getStatus() = " +  detail.getStatus() +
						"detail.getIssueCode() = " + detail.getIssueCode());
			} 
			//奖期已撤销或者追号明细已被用户撤销
			if (nextIssue.getPauseStatus() == PauseStatus.CANCAL || (detail!=null && !"-1".equals(detail.getCancelUser()) && (detail.getStatus()==GamePlanDetailStatus.CANCEL.getValue() 
					&& detail.getStatus()==GamePlanDetailStatus.WAIT_CANCEL.getValue() && detail.getStatus()==GamePlanDetailStatus.CANCEL_ERROR.getValue()) )) {
				log.info("处理正常追号计划，lotteryId=" + lotteryId + ",issueCode=" + nextIssue.getIssueCode() + ",本期奖期"
						+ nextIssue.getIssueCode() + "已处于撤销状态，直接执行下一期追号计划信息");
	
				return getNextIssueIsNotCancal(lotteryId, nextIssue.getIssueCode(),planId);
	
			}
		}

		return nextIssue;
	}
	
	/**
	 * 校验追号明细数据
	 * @param planId
	 * @param detailList
	 * @throws Exception
	 */
	private void checkGamePlanDetail(Long planId,List<GamePlanDetail> detailList) throws Exception{
		if(detailList==null || detailList.size()<=0){
			log.info("追号明细数据异常，planId="+ planId);
			throw new Exception("追号明细数据异常，planId="+ planId);
		}
	}
	
	
	/**
	 * 撤銷異常追號單
	 * status=6
	 * @param planId
	 * @param issueCode
	 * @param userId
	 * @param userType
	 * @throws Exception
	 */
	@Override
	public void reservationCalled() throws Exception { 
		
		List<GamePlanDetail> gamePlanDetailList = gamePlanDetailDao
				.getGamePlanDetailNeedCancelList();
		
		if(gamePlanDetailList == null){
			return;
		}
		
		for(GamePlanDetail gamePlanDetail:gamePlanDetailList ){
			com.winterframework.firefrog.game.dao.vo.GamePlan gamePlan = gamePlanDao
					.getPlanById(gamePlanDetail.getPlanid());
			int count = gamePlanDetailDao
					.updateGamePlanDetailByIdCancel(
							gamePlanDetail.getId(),  gamePlan.getUserId() + "");
			
			Map<String,Long> summaryMap=gamePlanDetailService.getSummary(gamePlan.getId());
			if(summaryMap!=null){
				Long soldAmount=summaryMap.get("soldAmount");
				Long finishedCount=summaryMap.get("finishedCount");
				Long canceledAmount=summaryMap.get("canceledAmount"); 
				Long canceledCount=summaryMap.get("canceledCount"); 
				
				gamePlan.setCanceledAmount(canceledAmount);
				gamePlan.setCancelIssue(canceledCount.intValue()); 
				gamePlan.setFinishIssue(finishedCount.intValue());
				gamePlan.setSoldAmount(soldAmount);
				if(canceledCount.intValue()+finishedCount.intValue()==gamePlan.getTotalIssue().intValue()){
					gamePlan.setStatus(com.winterframework.firefrog.game.dao.vo.GamePlan.Status.FINISH
							.getValue());
				}
				gamePlan.setUpdateTime(DateUtils.currentDate()); 
				gamePlanDao.update(gamePlan);
			}
			
			if (count > 0) {
				GameOrder order = gameOrderDao.getOrderByPlanIdAndIssueCode(gamePlan.getId(),
						gamePlanDetail.getIssueCode());
				// 2.解冻当前的金额
				List<TORiskDTO> toRiskDTOList = new ArrayList<TORiskDTO>();
				// 投注DTO生成
				TORiskDTO unFreezerPlanDTO = new TORiskDTO();
				unFreezerPlanDTO.setAmount(gamePlanDetail.getTotamount() + "");
				unFreezerPlanDTO.setIssueCode(gamePlanDetail.getIssueCode());
				unFreezerPlanDTO.setLotteryid(gamePlan.getLotteryid());
				unFreezerPlanDTO.setPlanCodeList(gamePlan.getPlanCode());
				if (order != null) {
					unFreezerPlanDTO.setOrderCodeList(order.getOrderCode());
				}
				unFreezerPlanDTO
						.setType(GameFundTypesUtils.GAME_PLAN_RESERVE_UNFREEZEN_DETEAIL_TYPE);
				unFreezerPlanDTO.setUserid(gamePlan.getUserId() + "");
				toRiskDTOList.add(unFreezerPlanDTO);

				Double amount = 0.00D;
				GameSeriesConfigEntity gse = configService
						.getSeriesConfigByLotteryId(gamePlan.getLotteryid());
				if (gse == null) {
					amount = 0.00D;
				} else {
					if (gamePlanDetail.getTotamount().longValue() <= gse
							.getBackoutStartFee().longValue()) {
						amount = 0.00D;
					} else {
						DecimalFormat df = new DecimalFormat(".00");
						// 后台撤单时获取撤单手续费，因为金额和撤单率 都*10000 因此需要除以一个10000
						amount = Double.valueOf(df.format(gamePlanDetail
								.getTotamount()
								* gse.getBackoutRatio()
								/ 10000.00));
					}
				}

				if (amount > 0) {
					// 撤单手续费DTO生成
					TORiskDTO cancelFeeDTO = new TORiskDTO();
					cancelFeeDTO.setAmount(amount.longValue() + "");
					cancelFeeDTO.setIssueCode(gamePlanDetail.getIssueCode());
					cancelFeeDTO.setLotteryid(gamePlan.getLotteryid());
					cancelFeeDTO.setPlanCodeList(gamePlan.getPlanCode());
					if (order != null) {
						cancelFeeDTO.setOrderCodeList(order.getOrderCode());
					}
					cancelFeeDTO
							.setType(GameFundTypesUtils.GAME_CANCEL_FEE_DETEAIL_TYPE);
					cancelFeeDTO.setUserid(gamePlan.getUserId() + "");
					toRiskDTOList.add(cancelFeeDTO);
				}
				

				// 调用风控资金冻结接口
				fundRiskService.cancelFee(toRiskDTOList);
			}
		}
	}
}
