package com.winterframework.firefrog.game.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.firefrog.game.dao.IGameWarnOrderDao;
import com.winterframework.firefrog.game.dao.vo.GameWarnOrder;
import com.winterframework.firefrog.game.dao.vo.RiskOrders;
import com.winterframework.firefrog.game.entity.GameSpiteOperationsEntity;
import com.winterframework.firefrog.game.service.IGameWarnOrderService;
import com.winterframework.firefrog.game.web.dto.GameSpiteOrderQueryRequestDTO;
import com.winterframework.firefrog.game.web.dto.GameWarnOrderQueryDTO;
import com.winterframework.modules.page.Page;
import com.winterframework.modules.page.PageRequest;

@Service("gameWarnOrderServiceImpl")
@Transactional(rollbackFor = Exception.class)
public class GameWarnOrderServiceImpl implements IGameWarnOrderService {
	
	@Resource(name = "gameWarnOrderDaoImpl")
	private IGameWarnOrderDao gameWarnOrderDao;

	@Override
	public Page<RiskOrders> queryGameWarnOrders(PageRequest<GameWarnOrderQueryDTO> pr) throws Exception {
		Page<RiskOrders> result = gameWarnOrderDao.queryGameWarnOrderByPage(pr);
		return result;
	}

	@Override
	public Page<GameSpiteOperationsEntity> queryGameSpiteOrders(PageRequest<GameSpiteOrderQueryRequestDTO> pr) throws Exception {
		Page<GameSpiteOperationsEntity> result = gameWarnOrderDao.queryGameSpiteOrders(pr);
		return result;
	}
	
	@Override
	public List<GameWarnOrder> getUndealGameWarnOrderByPlanId(Long planId) throws Exception {
		
		return  gameWarnOrderDao.getUndealGameWarnOrderByPlanId(planId);
	}

}
