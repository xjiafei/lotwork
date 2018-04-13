package com.winterframework.firefrog.game.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.firefrog.common.util.GameContext;
import com.winterframework.firefrog.game.dao.IGameWarnOrderDao;
import com.winterframework.firefrog.game.dao.vo.GameWarnOrder;
import com.winterframework.firefrog.game.service.IGameWarnOrderService;

@Service("gameWarnOrderServiceImpl")
@Transactional(rollbackFor = Exception.class)
public class GameWarnOrderServiceImpl implements IGameWarnOrderService {
	
	@Resource(name = "gameWarnOrderDaoImpl")
	private IGameWarnOrderDao gameWarnOrderDao;
 
	@Override
	public int save(GameContext ctx, GameWarnOrder warnOrder) throws Exception {
		if(warnOrder.getId()==null){			
			this.gameWarnOrderDao.insert(warnOrder);
		}else{
			this.gameWarnOrderDao.update(warnOrder);
		}
		return 1;
	}

}
