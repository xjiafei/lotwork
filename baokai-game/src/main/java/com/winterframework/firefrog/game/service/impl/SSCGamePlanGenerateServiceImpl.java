package com.winterframework.firefrog.game.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.firefrog.common.util.DateUtils;
import com.winterframework.firefrog.game.dao.IGamePlanDao;
import com.winterframework.firefrog.game.dao.IGamePlanDetailDao;
import com.winterframework.firefrog.game.dao.IGameSlipDao;
import com.winterframework.firefrog.game.dao.vo.GamePlan;
import com.winterframework.firefrog.game.dao.vo.GamePlanDetail;
import com.winterframework.firefrog.game.entity.EventStatus;
import com.winterframework.firefrog.game.entity.GameIssueEntity;
import com.winterframework.firefrog.game.entity.GameOrder;
import com.winterframework.firefrog.game.entity.GameOrder.OrderParentType;
import com.winterframework.firefrog.game.entity.Lottery;
import com.winterframework.firefrog.game.entity.PauseStatus;
import com.winterframework.firefrog.game.service.IGameOrderService;
import com.winterframework.firefrog.game.service.IGamePlanGenerateService;
import com.winterframework.firefrog.user.entity.User;

/**
 * 
* @ClassName: SSCGamePlanGenerateServiceImpl 
* @Description: 自动执行【时时彩】追号计划
* @author Richard
* @date 2013-10-31 下午1:52:40 
*
 */
@Service("sscGamePlanGenerateServiceImpl")
@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
@Deprecated
public class SSCGamePlanGenerateServiceImpl implements IGamePlanGenerateService {

	private Logger log = LoggerFactory.getLogger(SSCGamePlanGenerateServiceImpl.class);
	
	@Resource(name = "gamePlanDaoImpl")
	private IGamePlanDao gamePlanDao;
	
	@Resource(name = "gamePlanDetailDaoImpl")
	private IGamePlanDetailDao detailDao;
	
	@Resource(name = "gameOrderServiceImpl")
	private IGameOrderService gameOrderSerive;
	
	@Resource(name = "gameSlipDaoImpl")
	private IGameSlipDao gameSlipDao;
	
	
	/**
	 *  这里处理的只是每一期的追号信息，处理流程为：生成订单信息->解冻计划资金->派发冻结返点->更新追号计划状态。
	* Title: gamePlanGenerate
	* Description:
	* @param gameIssue
	* @throws Exception 
	* @see com.winterframework.firefrog.game.service.IGamePlanGenerateService#gamePlanGenerate(com.winterframework.firefrog.game.entity.GameIssueEntity)
	 */
	@Deprecated
	@Override
	public void gamePlanGenerate(GameIssueEntity gameIssue) throws Exception {
		
		//1.如果奖期为暂停，则返回
		if(gameIssue.getPauseStatus() == PauseStatus.PAUSE){
			log.info("彩种【"+ gameIssue.getLottery().getLotteryId() + "】，期号为【"+ gameIssue.getWebIssueCode() +"】的奖期状态为暂停，自动执行追号计划暂时退出。");
			return ;
		}
		
		//2.为锁定状态，则返回
		if(gameIssue.getEventStatus() == EventStatus.LOCK){
			log.info("彩种【"+ gameIssue.getLottery().getLotteryId() + "】，期号为【"+ gameIssue.getWebIssueCode() +"】已被锁定，自动执行追号计划暂时退出。");
			return;
		}
		
		/**
		 * 奖期状态 0:已生成(M1) 1:开始销售(M2) 2:结束销售(M3) 3:开奖号码确认(M4) 4:计奖完成(M5) 5:验奖完成(M6) 6:派奖完成(M7) 7:奖期结束(M8) 8:对账结束(M9)
		 */
		if(gameIssue.getStatus().getValue() < 6){
			log.info("彩种【"+ gameIssue.getLottery().getLotteryId() + "】，期号为【"+ gameIssue.getWebIssueCode() +"】的奖期状态不是派奖完成，自动执行追号计划暂时退出。");
			return;			
		}
		
		/**
		 * 奖期过程状态  0:待销售(P1) 1:销售中(P2) 2:待开奖(P3) 3:计奖中(P4) 4:验奖中(P5) 5:派奖中(P6) 6:待结束(P7) 7:待对账(P8)
		 */
		if(gameIssue.getPeriodStatus().getValue() < 6){
			log.info("彩种【"+ gameIssue.getLottery().getLotteryId()  + "】，期号为【"+ gameIssue.getWebIssueCode() +"】的奖期状态不是待结束，自动执行追号计划暂时退出。");
			return;		
		}
		
		//获取追号信息，这里是返回当前彩种未结束追号的追号计划信息。
		GamePlan entity = new  GamePlan();
		entity.setLotteryid(gameIssue.getLottery().getLotteryId());
		entity.setStatus(GamePlan.Status.WAITING.getValue()); //1为进行中
		entity.setUpdateTime(DateUtils.currentDate());
		/**获取本彩种正在进行追号计划的追号信息*/
		List<GamePlan> gamePlanList = gamePlanDao.getAllByEntity(entity);
		
		if(gamePlanList.isEmpty()){
			log.info("彩种【"+ gameIssue.getLottery().getLotteryId()  + "】，期号为【"+ gameIssue.getWebIssueCode() +"】无追号计划信息，自动执行追号计划暂时退出。");
			return;
		}
		
		for(GamePlan plan : gamePlanList){
			
			//当前奖期的追号明细
			GamePlanDetail detail = detailDao.getGamePlanDetailByPlanIdAndIssueCode(plan.getId(), gameIssue.getIssueCode());
			
//			Assert.assertNotNull("无追号明细信息", detail);
			if(null == detail){
				log.info("彩种【"+ plan.getLotteryid() + "】，期号【"+gameIssue.getIssueCode()+"】无明细信息！，");
				continue;
			}

			if(detail.getStatus() > 0){
				log.info("彩种【"+ plan.getLotteryid() + "】，期号【"+detail.getIssueCode()+"】不是未执行状态，");
				continue;
			}
			
			//生成订单信息
			GameOrder order = new GameOrder();
			
			User user = new User();
//			user.setId(plan.getUserid());
			
//			order.setUser(user);
//			order.setIssueCode(detail.getIssueCode());
			
			Lottery lottery = new Lottery();
			lottery.setLotteryId(plan.getLotteryid());
			order.setLottery(lottery);
			
			order.setTotalAmount(detail.getTotamount());
//			order.setStatus(OrderStatus.waiting);
			order.setSaleTime(new Date());
//			order.setCancelModes(OrderCancelModes.defaults);
			order.setParentType(OrderParentType.PLAN);
//			order.setParentId(plan.getId());
			
			gameOrderSerive.saveGameOrder(order);
			
			//更新追号计划状态。
			plan.setFinishIssue(plan.getFinishIssue() + 1); //完成期数
			BigDecimal _soldAmount = new BigDecimal(plan.getSoldAmount());
			BigDecimal totalAmount = _soldAmount.add(new BigDecimal(detail.getTotamount()));
			plan.setSoldAmount(totalAmount.longValue());
			
			gamePlanDao.update(plan);
		}
		
	}

}
