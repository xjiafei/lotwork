package com.winterframework.firefrog.game.service.revocation.impl;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.firefrog.common.util.GameContext;
import com.winterframework.firefrog.game.dao.vo.GameOrder;
import com.winterframework.firefrog.game.service.IGameOrderFundService;
import com.winterframework.firefrog.game.service.IGameOrderWinService;
import com.winterframework.firefrog.game.service.IGameWarnService;
 
@Service("gameRevocationOrderExcepServiceImpl")
@Transactional
public class GameRevocationOrderExcepServiceImpl extends AbstractGameRevocationOrderNewService {

	private Logger log = LoggerFactory.getLogger(GameRevocationOrderExcepServiceImpl.class);
 
	@Resource(name = "gameOrderFundServcieImpl")
	private IGameOrderFundService gameOrderFundService; 
	@Resource(name = "gameWarnServiceImpl")
	private IGameWarnService gameWarnService;
	@Resource(name = "gameOrderWinServiceImpl")
	private IGameOrderWinService gameOrderWinService; 
	
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
}
