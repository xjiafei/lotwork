package com.winterframework.firefrog.game.service.revocation.impl;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.firefrog.common.util.GameContext;
import com.winterframework.firefrog.game.dao.vo.GameOrder;
import com.winterframework.firefrog.game.dao.vo.GameOrderWin;
import com.winterframework.firefrog.game.dao.vo.GamePlan;
import com.winterframework.firefrog.game.service.ICommonGamePlanService;
import com.winterframework.firefrog.game.service.IGameOrderFundService;
import com.winterframework.firefrog.game.service.IGameOrderWinFundService;
import com.winterframework.firefrog.game.service.IGameOrderWinService;
import com.winterframework.firefrog.game.service.IGameWarnService;
 
@Service("gameRevocationOrderWinServiceImpl")
@Transactional
public class GameRevocationOrderWinServiceImpl extends AbstractGameRevocationOrderNewService {

	private Logger log = LoggerFactory.getLogger(GameRevocationOrderWinServiceImpl.class);
 
	@Resource(name = "gameOrderFundServcieImpl")
	private IGameOrderFundService gameOrderFundService; 
	@Resource(name = "gameWarnServiceImpl")
	private IGameWarnService gameWarnService;
	@Resource(name = "gameOrderWinFundServcieImpl")
	private IGameOrderWinFundService gameOrderWinFundServcie; 
	@Resource(name = "gameOrderWinServiceImpl")
	private IGameOrderWinService gameOrderWinService; 
	@Resource(name = "gamePlanService")
	private ICommonGamePlanService gamePlanService; 
	
	@Override
	protected void doFund(GameContext ctx, GameOrder order) throws Exception {
		log.info("资金处理"); 
		this.gameOrderFundService.unpay(ctx, order); 		
	}
	@Override
	protected void doOrderAssist(GameContext ctx, GameOrder order)
			throws Exception { 
		super.doOrderAssist(ctx, order);
		
		log.info("撤销中奖订单");
		this.gameOrderWinService.cancelByOrder(ctx, order); 
		
		log.info("撤销风险订单");
		this.gameWarnService.cancelWarnOrder(order.getId());
	}
	
	@Override
	protected void doWriteback(GameContext ctx, GameOrder order)
			throws Exception {  
		if(order==null) return;
		super.doWriteback(ctx, order); 
		//中奖金额有变的情况也只有是中奖订单被撤销（更新追号中奖信息 ？？？？？？？？？？？？？？？
		GamePlan plan=gamePlanService.getGamePlanById(order.getPlanId());
		GameOrderWin orderWin=gameOrderWinService.getByOrderId(ctx, order.getId()); 
		plan.setWinAmount(plan.getWinAmount() - orderWin.getCountWin()); 	 
		gamePlanService.save(ctx,plan);
	}
}
