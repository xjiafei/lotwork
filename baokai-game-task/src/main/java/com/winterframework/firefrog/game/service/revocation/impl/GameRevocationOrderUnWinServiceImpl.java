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
 
@Service("gameRevocationOrderUnWinServiceImpl")
@Transactional
public class GameRevocationOrderUnWinServiceImpl extends AbstractGameRevocationOrderNewService {

	private Logger log = LoggerFactory.getLogger(GameRevocationOrderUnWinServiceImpl.class);
 
	@Resource(name = "gameOrderFundServcieImpl")
	private IGameOrderFundService gameOrderFundService;  
	@Override
	protected void doFund(GameContext ctx, GameOrder order) throws Exception {
		log.info("资金处理"); 
		this.gameOrderFundService.unpay(ctx, order);
	}
	 
}
