package com.winterframework.firefrog.game.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.firefrog.game.dao.IGameOrderDao;
import com.winterframework.firefrog.game.dao.IGamePlanOperationsDao;
import com.winterframework.firefrog.game.dao.IGameSlipDao;
import com.winterframework.firefrog.game.entity.GameOrderOperationsEntity;
import com.winterframework.firefrog.game.entity.GamePlanOperationsEntity;
import com.winterframework.firefrog.game.entity.GameSlipOperationsEntity;
import com.winterframework.firefrog.game.service.IGamePlanOperationsService;
import com.winterframework.firefrog.game.web.dto.GamePlanOperationsQueryDTO;
import com.winterframework.modules.page.Page;
import com.winterframework.modules.page.PageRequest;

@Service("gamePlanOperationsServiceImpl")
@Transactional(rollbackFor = Exception.class)
public class GamePlanOperationsSerivceImpl implements IGamePlanOperationsService {

	@Resource(name = "gamePlanOperationsDaoImpl")
	private IGamePlanOperationsDao gamePlanOperationsDao;
	
	@Resource(name = "gameSlipDaoImpl")
	private IGameSlipDao gameSlipDao;
	
	@Resource(name = "gameOrderDaoImpl")
	private IGameOrderDao gameOrderDao;
	
	@Override
	public Page<GamePlanOperationsEntity> queryGamePlanOperations(PageRequest<GamePlanOperationsQueryDTO> queryDTO) throws Exception {
		Page<GamePlanOperationsEntity> pageResult = gamePlanOperationsDao.getGamePlanByPage(queryDTO);
		return pageResult;
	}

	@Override
	public List<GamePlanOperationsEntity> queryGamePlanOperationsList(GamePlanOperationsQueryDTO queryDTO) throws Exception {
		List<GamePlanOperationsEntity> list = gamePlanOperationsDao.getGamePlanList(queryDTO);
		return list;
	}

	@Override
	public GamePlanOperationsEntity queryGamePlanOperationsDetail(Long planid) throws Exception {
		GamePlanOperationsEntity entity  = gamePlanOperationsDao.queryGamePlanOperationsDetail(planid);
		return entity;
	}

	@Override
	public List<GameSlipOperationsEntity> querySlipOperationsListByPlanID(Long planid) throws Exception {
		List<GameSlipOperationsEntity> slipList = gameSlipDao.querySlipOperationsListByPlanID(planid);
		return slipList;
	}

	@Override
	public List<GameOrderOperationsEntity> queryOrderOperationsListByPlanID(Long planid) throws Exception {
		List<GameOrderOperationsEntity> orderList = gameOrderDao.queryOrderOperationsListByPlanID(planid);
		return orderList;
	}

}
