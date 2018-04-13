package com.winterframework.firefrog.game.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.firefrog.common.util.GameContext;
import com.winterframework.firefrog.game.dao.IGameOrderDao;
import com.winterframework.firefrog.game.dao.IGamePlanDao;
import com.winterframework.firefrog.game.dao.IGameReturnPointDao;
import com.winterframework.firefrog.game.dao.vo.GameOrder;
import com.winterframework.firefrog.game.dao.vo.GamePlan;
import com.winterframework.firefrog.game.dao.vo.GameRetPoint;
import com.winterframework.firefrog.game.exception.GameException;
import com.winterframework.firefrog.game.exception.GameRiskException;
import com.winterframework.firefrog.game.service.IGameFundRiskService;
import com.winterframework.firefrog.game.service.IGameReturnPointFundService;
import com.winterframework.firefrog.game.web.dto.TORiskDTO;
import com.winterframework.firefrog.game.web.util.GameFundTypesUtils;

/** 
* @ClassName GameReturnPointFundServcieImpl 
* @Description 返点资金操作类 
* @author  hugh
* @date 2014年4月22日 下午2:46:23 
*  
*/
@Service("gameReturnPointFundServcieImpl")
@Transactional
public class GameReturnPointFundServcieImpl extends AbstractGameFundService implements IGameReturnPointFundService {

	private Logger log = LoggerFactory.getLogger(GameReturnPointFundServcieImpl.class);

	@Resource(name = "gameOrderDaoImpl")
	private IGameOrderDao gameOrderDaoImpl;

	@Resource(name = "gameReturnPointDaoImpl")
	private IGameReturnPointDao gameReturnPointDaoImpl;

	@Resource(name = "gameFundRiskServiceImpl")
	private IGameFundRiskService gameFundRiskServiceImpl;

	@Resource(name = "gamePlanDaoImpl")
	private IGamePlanDao gamePlanDao;
	
	/** 
	* @Title: returnPointFundUpdateRetsStatus 
	* @Description: 派发资金返点  并更新返点表 
	* @param orderId
	* @return
	* @throws GameRiskException
	*/
	public boolean returnPointFundUpdateRetsStatus(Long orderId) throws GameRiskException{
		Map<Long, GameOrder> orders = new HashMap<Long, GameOrder>();	
		orders.put(orderId,gameOrderDaoImpl.getById(orderId));
		return returnPointFundUpdateRetsStatus(orders, getReturnPoints(orders));
	}

	public boolean returnPointFundUpdateRetsStatus(Map<Long, GameOrder> orders ,Map<Long, GameRetPoint> rets) throws GameRiskException{
		if(returnPointFund( orders , rets)){
			for (Long orderId : rets.keySet()) {
				GameRetPoint ret = rets.get(orderId);
				ret.setStatus(2);
				gameReturnPointDaoImpl.update(ret);
			}			
		}
		return true;
	}
	
	/** 
	* @Title: getReturnPoints 
	* @Description: 获取返点记录
	* @param orders
	* @return
	* @throws GameRiskException
	*/
	public Map<Long, GameRetPoint> getReturnPoints(Map<Long, GameOrder> orders) throws GameRiskException{
		
		Map<Long, GameRetPoint> rets = new HashMap<Long, GameRetPoint>();
		for (Long orderId : orders.keySet()) {
			rets.put(orderId, gameReturnPointDaoImpl.getGameRetPointByGameOrderId(orderId));
		}
		return rets;
	}
	
	/**
	* @Title: returnPointFund
	* @Description:资金返点
	* @param orders
	* @param rets
	* @return
	* @throws GameRiskException 
	* @see com.com.winterframework.firefrog.game.service.IGameReturnPointFundService#returnPointFund(java.util.Map, java.util.Map) 
	*/
	public boolean returnPointFund(Map<Long, GameOrder> orders ,Map<Long, GameRetPoint> rets) throws GameRiskException {
		List<TORiskDTO> toRiskDTOList = new ArrayList<TORiskDTO>();
		
		for (Long orderId : orders.keySet()) {
			GameRetPoint ret = gameReturnPointDaoImpl.getGameRetPointByGameOrderId(orderId);	
			toRiskDTOList.add(packageFundTORiskDTO(orders.get(orderId),ret));
		}
		
		fundRequest(toRiskDTOList);
		return true;
	}
	public TORiskDTO packageFundTORiskDTO(GameOrder order ,GameRetPoint ret){
		TORiskDTO dto = packageTORiskDTO(order);
		dto.setAmount(ret.getRetPointChain());
		dto.setUserid(ret.getRetUserChain());
		dto.setType(GameFundTypesUtils.GAME_RET_UNFREEZER_DETEAIL_TYPE);		
		return dto;
	}
	public TORiskDTO packageTORiskDTO(GameOrder order){
		TORiskDTO dto = new TORiskDTO();		
		dto.setIssueCode(order.getIssueCode());
		dto.setLotteryid(order.getLotteryid().longValue());
		dto.setOrderCodeList(order.getOrderCode());
		GamePlan gamePlan = gamePlanDao.getGamePlanByOrderId(order.getId());
		if(gamePlan!=null){
			dto.setPlanCodeList(gamePlan.getPlanCode());
		}					
		return dto;
	}
	
	@Override
	public int freeze(GameContext ctx, GameOrder order, GameRetPoint retPoint)
			throws GameException {
		// TODO Auto-generated method stub
		return 0;
	}
	
	@Override
	public int distribute(GameContext ctx, GameOrder order,
			GameRetPoint retPoint) throws GameException {
		if(order==null || retPoint==null) return 0; 
		TORiskDTO riskDto=toRiskDTO(order);
		riskDto.setAmount(retPoint.getRetPointChain());
		riskDto.setUserid(retPoint.getRetUserChain());
		riskDto.setType(GameFundTypesUtils.GAME_RET_UNFREEZER_DETEAIL_TYPE);
		addRiskDto(ctx, riskDto);
		return 1;
	}
	
	@Override
	public int cancel(GameContext ctx, GameOrder order, GameRetPoint retPoint)
			throws GameException {
		if(order==null || retPoint==null) return 0;
		TORiskDTO dto = this.toRiskDTO(order); 
		dto.setAmount(retPoint.getRetPointChain());
		dto.setUserid(retPoint.getRetUserChain());		
		dto.setType(GameFundTypesUtils.GAME_CANCEL_RET_DETEAIL_TYPE);	
		this.addRiskDto(ctx, dto);
		return 1;
	} 
}
