package com.winterframework.firefrog.game.service.revocation.impl;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.firefrog.common.util.DateUtils;
import com.winterframework.firefrog.common.util.GameContext;
import com.winterframework.firefrog.game.dao.vo.GameOrder;
import com.winterframework.firefrog.game.service.IGameOrderFundService;
import com.winterframework.firefrog.game.service.IGameOrderService;
import com.winterframework.firefrog.game.service.IGameReturnPointService;
import com.winterframework.firefrog.game.service.IGameSlipService;
import com.winterframework.firefrog.game.service.revocation.IGameRevocationOrderNewService;
 
@Service("gameRevocationOrderWaitingServiceImpl")
@Transactional
public class GameRevocationOrderWaitingServiceImpl extends AbstractGameRevocationOrderNewService {

	private Logger log = LoggerFactory.getLogger(GameRevocationOrderWaitingServiceImpl.class);
 
	@Resource(name = "gameSlipServiceImpl")
	private IGameSlipService gameSlipService;
	@Resource(name = "gameOrderFundServcieImpl")
	private IGameOrderFundService gameOrderFundService;
	@Resource(name = "gameReturnPointServiceImpl")
	private IGameReturnPointService gameReturnPointService; 
	
	@Override
	protected void doFund(GameContext ctx, GameOrder order) throws Exception {
		log.info("资金处理"); 
		this.gameOrderFundService.unfreeze(ctx, order); 
	} 
}
