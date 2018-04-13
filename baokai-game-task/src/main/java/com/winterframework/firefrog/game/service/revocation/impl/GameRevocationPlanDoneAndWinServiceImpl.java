package com.winterframework.firefrog.game.service.revocation.impl;

import com.winterframework.firefrog.game.service.IGamePlanDetailService;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.firefrog.game.dao.IGameOrderWinDao;
import com.winterframework.firefrog.game.dao.vo.GameOrder;
import com.winterframework.firefrog.game.dao.vo.GameOrderWin;
import com.winterframework.firefrog.game.dao.vo.GamePlan;
import com.winterframework.firefrog.game.dao.vo.GamePlanDetail;
import com.winterframework.firefrog.game.exception.GameRevocationException;
import com.winterframework.firefrog.game.web.dto.TORiskDTO;

@Service("gameRevocationPlanDoneAndWinServiceImpl")
@Transactional
public class GameRevocationPlanDoneAndWinServiceImpl extends AbstractGameRevocationPlanService { 
	private Logger log = LoggerFactory.getLogger(GameRevocationPlanDoneAndWinServiceImpl.class);

	@Resource(name = "gameOrderWinDaoImpl")
	private IGameOrderWinDao gameOrderWinDaoImpl;
	@Resource(name = "gamePlanDetailServiceImpl")
	private IGamePlanDetailService gamePlanDetailService;
	

	/**
	* @Title: doRevocation
	* @Description:执行了追号 且中奖 --》撤销追号
	* @param plan
	* @param detail
	* @param order
	* @return 
	* @see com.winterframework.firefrog.game.service.revocation.impl.AbstractGameRevocationPlanService#doRevocation(com.winterframework.firefrog.game.dao.vo.GamePlan, com.winterframework.firefrog.game.dao.vo.GamePlanDetail, com.winterframework.firefrog.game.dao.vo.GameOrder) 
	*/ 
	public List<TORiskDTO> doRevocation_unfinish(GamePlan plan, GamePlanDetail detail, GameOrder order, boolean isAskPlan)
			throws Exception {
		super.doRevocation(plan, detail, order, isAskPlan);
		//更新plan销售金额 detail状态
		updateGamePlanRevocation(plan, detail, order, isAskPlan);
		plan = gamePlanService.getGamePlanById(plan.getId());
		GameOrderWin win = gameOrderWinDaoImpl.selectGameOrderWinByOrderId(order.getId());
		//调回滚plan中奖金额
		try {
			plan.setWinAmount(plan.getWinAmount() - win.getCountWin());
			gamePlanService.updateGamePlan(plan);
		} catch (Exception e) {
			log.error("计划更新中奖金额出错", e);
			throw new GameRevocationException("计划更新中奖金额出错");
		}
		
		////（是否追中即停 且 中奖）？恢复后续追号(有执行则恢复，未执行则追一起） 并通知下一期补开奖
		/*if(plan!=null && detail!=null && plan.getStopMode().intValue()==2 && order.getStatus().intValue()==2){
			//获取后续追号 
			List<GamePlanDetail> detailList =gamePlanDetailService.getGamePlanDetailFollowedByPlanAndIssue(detail.getPlanid(), detail.getIssueCode());
			if(detailList!=null){
				GameContext ctx=new GameContext();
				for(GamePlanDetail planDetail:detailList){
					gamePlanDetailService.reset(ctx,planDetail);
				}
			}
		}*/
		//追号不执行退款，转由订单执行退款，所以不需传递资金数据
		return null;
	}
	/**
	* @Title: doRevocation
	* @Description:执行了追号 且中奖 --》撤销追号
	* @param plan
	* @param detail
	* @param order
	* @return 
	* @see com.winterframework.firefrog.game.service.revocation.impl.AbstractGameRevocationPlanService#doRevocation(com.winterframework.firefrog.game.dao.vo.GamePlan, com.winterframework.firefrog.game.dao.vo.GamePlanDetail, com.winterframework.firefrog.game.dao.vo.GameOrder) 
	*/ 
	public List<TORiskDTO> doRevocation(GamePlan plan, GamePlanDetail detail, GameOrder order, boolean isAskPlan)
			throws Exception {
		super.doRevocation(plan, detail, order, isAskPlan);
		//更新plan销售金额 detail状态
		updateGamePlanRevocation(plan, detail, order, isAskPlan);
		plan = gamePlanService.getGamePlanById(plan.getId());
		GameOrderWin win = gameOrderWinDaoImpl.selectGameOrderWinByOrderId(order.getId());
		//调回滚plan中奖金额
		try {
			plan.setWinAmount(plan.getWinAmount() - win.getCountWin());
			gamePlanService.updateGamePlan(plan);
		} catch (Exception e) {
			log.error("计划更新中奖金额出错", e);
			throw new GameRevocationException("计划更新中奖金额出错");
		} 
		//追号不执行退款，转由订单执行退款，所以不需传递资金数据
		return null;
	}

}
