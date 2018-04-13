package com.winterframework.firefrog.game.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.firefrog.common.util.GameContext;
import com.winterframework.firefrog.game.dao.IGameOrderDao;
import com.winterframework.firefrog.game.dao.IGameOrderWinDao;
import com.winterframework.firefrog.game.dao.IGamePlanDao;
import com.winterframework.firefrog.game.dao.vo.GameOrder;
import com.winterframework.firefrog.game.dao.vo.GameOrderWin;
import com.winterframework.firefrog.game.dao.vo.GamePlan;
import com.winterframework.firefrog.game.exception.GameRiskException;
import com.winterframework.firefrog.game.service.ICommonGamePlanService;
import com.winterframework.firefrog.game.service.IGameFundRiskService;
import com.winterframework.firefrog.game.service.IGameOrderWinFundService;
import com.winterframework.firefrog.game.service.IGameOrderWinService;
import com.winterframework.firefrog.game.web.dto.TORiskDTO;
import com.winterframework.firefrog.game.web.util.GameFundTypesUtils;

/** 
* @ClassName GameOrderWinFundServcieImpl 
* @Description 派奖操作 
* @author  hugh
* @date 2014年4月22日 下午2:27:12 
*  
*/
@Service("gameOrderWinFundServcieImpl")
@Transactional
public class GameOrderWinFundServcieImpl extends AbstractGameFundService implements IGameOrderWinFundService {

	private Logger log = LoggerFactory.getLogger(GameOrderWinFundServcieImpl.class);

	@Resource(name = "gameOrderWinDaoImpl")
	private IGameOrderWinDao gameOrderWinDaoImpl;

	@Resource(name = "gameOrderDaoImpl")
	private IGameOrderDao gameOrderDaoImpl;

	@Resource(name = "gameFundRiskServiceImpl")
	private IGameFundRiskService gameFundRiskServiceImpl;

	@Resource(name = "gamePlanDaoImpl")
	private IGamePlanDao gamePlanDao;
//	@Resource(name = "gamePlanService")
//	private ICommonGamePlanService gamePlanService;
	@Resource(name = "gameOrderWinServiceImpl")
	private IGameOrderWinService gameOrderWinService;
	
	

	public boolean orderWinFundAndUpdateWinStatus(GameOrder order) throws GameRiskException {
		GameOrderWin orderWin = gameOrderWinDaoImpl.selectGameOrderWinByOrderId(order.getId());
		orderWin.setStatus(1L);
		gameOrderWinDaoImpl.update(orderWin);
		return orderWinFund(order, orderWin);
	}
	/**
	* @Title: distributeFund
	* @Description:派奖 (1个单)
	* @param order
	* @param orderWin
	* @return 
	* @see com.com.winterframework.firefrog.game.service.IGameOrderWinFundService#distributeFund(com.winterframework.firefrog.game.dao.vo.GameOrder, com.winterframework.firefrog.game.dao.vo.GameOrderWin) 
	*/
	@Override
	public boolean orderWinFund(GameOrder order, GameOrderWin orderWin) throws GameRiskException {

		List<TORiskDTO> toRiskDTOList = new ArrayList<TORiskDTO>();
		log.debug("派奖, 用户  =" + orderWin.getUserid() + ", 资金=" + orderWin.getCountWin()+",  鑽石金額="+orderWin.getDiamondCountWin());
		toRiskDTOList.add(packageFundTORiskDTO(orderWin));
		fundRequest(toRiskDTOList);
		return true;
	}
	
	public TORiskDTO packageFundTORiskDTO(GameOrderWin orderWin) {
		TORiskDTO dto = packageTORiskDTO(orderWin);
		dto.setType(GameFundTypesUtils.GAME_DISTRIBUTE_DETEAIL_DETEAIL_TYPE);
		return dto;
	}
	
	private TORiskDTO packageTORiskDTO(GameOrderWin orderWin) {
		TORiskDTO dto = new TORiskDTO();
		dto.setAmount((orderWin.getCountWin()+orderWin.getDiamondCountWin()) + "");
		dto.setIssueCode(orderWin.getIssueCode());
		dto.setLotteryid(orderWin.getLotteryid().longValue());
		if (orderWin.getOrderCode() == null) {
			dto.setOrderCodeList(gameOrderDaoImpl.getById(orderWin.getOrderid()).getOrderCode());
		} else {
			dto.setOrderCodeList(orderWin.getOrderCode());
		}

		GamePlan gamePlan = gamePlanDao.getGamePlanByOrderId(orderWin.getOrderid());
		if (gamePlan != null) {
			dto.setPlanCodeList(gamePlan.getPlanCode());
		}
		dto.setUserid(orderWin.getUserid() + "");
		return dto;
	}
	@Override
	public int award(GameContext ctx, GameOrder order,GameOrderWin orderWin) throws Exception {
		if(order==null || orderWin==null) return 0; 
		TORiskDTO dto=this.toRiskDTO(order);
		dto.setAmount(orderWin.getCountWin() + ""); 
		dto.setType(GameFundTypesUtils.GAME_DISTRIBUTE_DETEAIL_DETEAIL_TYPE);
		addRiskDto(ctx, dto);
		return 1;
	}
	@Override
	public int unaward(GameContext ctx,GameOrder order,GameOrderWin orderWin) throws Exception {
		if(order==null || orderWin==null) return 0; 
		TORiskDTO dto=this.toRiskDTO(order);
		dto.setAmount(orderWin.getCountWin() + ""); 
		dto.setType(GameFundTypesUtils.GAME_CANCEL_AWARD_DETEAIL_TYPE);
		addRiskDto(ctx, dto);
		return 1;
	}
	 
}
