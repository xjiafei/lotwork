package com.winterframework.firefrog.game.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.firefrog.common.util.GameContext;
import com.winterframework.firefrog.game.dao.IGameSlipDao;
import com.winterframework.firefrog.game.dao.vo.GameSlip;
import com.winterframework.firefrog.game.service.IGameSlipService;

/**
 *GamePlanDetailServiceImpl
 * @ClassName
 * @Description 注单服务类
 * @author ibm
 * 2014年9月7日
 */
@Repository("gameSlipServiceImpl") 
@Transactional
public class GameSlipServiceImpl implements IGameSlipService {
	private Logger log = LoggerFactory.getLogger(GameSlipServiceImpl.class);
	
	@Resource(name="gameSlipDaoImpl")
	private IGameSlipDao gameSlipDao;
	
	
	@Override
	public int save(GameContext ctx, GameSlip slip) throws Exception {
		if(slip==null) return 0;
		if(slip.getId()==null){
			this.gameSlipDao.insert(slip);
		}else{
			this.gameSlipDao.update(slip);
		}
		return 1;
	} 
	@Override
	public GameSlip getById(GameContext ctx, Long slipId) throws Exception {
		return gameSlipDao.getById(slipId); 
	}
	@Override
	public List<GameSlip> getByOrderId(GameContext ctx, Long orderId)
			throws Exception { 
		return gameSlipDao.querySlipByOrder(orderId);
	} 
	@Override
	public int changeStatus(Long orderId, Integer fromStatus, Integer toStatus)
			throws Exception { 
		return gameSlipDao.changeStatus(orderId,fromStatus,toStatus);
	}
}
