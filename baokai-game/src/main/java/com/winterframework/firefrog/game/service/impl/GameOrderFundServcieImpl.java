package com.winterframework.firefrog.game.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
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
import com.winterframework.firefrog.game.dao.IGameSlipDao;
import com.winterframework.firefrog.game.dao.vo.GameOrder;
import com.winterframework.firefrog.game.dao.vo.GamePlan;
import com.winterframework.firefrog.game.exception.GameRiskException;
import com.winterframework.firefrog.game.service.IGameFundRiskService;
import com.winterframework.firefrog.game.service.IGameOrderFundService;
import com.winterframework.firefrog.game.service.IGameOrderService;
import com.winterframework.firefrog.game.service.IGameReturnPointService;
import com.winterframework.firefrog.game.web.dto.TORiskDTO;
import com.winterframework.firefrog.game.web.util.GameFundTypesUtils;

/**
 * @ClassName gameOrderFundServcieImpl
 * @Description 订单与资金交互类
 * @author hugh
 * @date 2014年4月28日 下午2:27:12
 * 
 */
@Service("gameOrderFundServcieImpl")
@Transactional
public class GameOrderFundServcieImpl extends AbstractGameFundService implements
		IGameOrderFundService {

	private Logger log = LoggerFactory
			.getLogger(GameOrderFundServcieImpl.class);

	@Resource(name = "gameOrderDaoImpl")
	private IGameOrderDao gameOrderDaoImpl;

	@Resource(name = "gameFundRiskServiceImpl")
	private IGameFundRiskService gameFundRiskServiceImpl;

	@Resource(name = "gamePlanDaoImpl")
	private IGamePlanDao gamePlanDao;

	@Resource(name = "gameSlipDaoImpl")
	private IGameSlipDao gameSlipDaoImpl;
	@Resource(name = "gameReturnPointServiceImpl")
	private IGameReturnPointService gameReturnPointService;
	@Resource(name = "gameOrderServiceImpl")
	private IGameOrderService gameOrderService;

	/**
	 * @Title: orderFund
	 * @Description: 投注扣款
	 * @param orders
	 * @throws GameRiskException
	 */
	public void orderFund(Collection<GameOrder> orders)
			throws GameRiskException {
		List<TORiskDTO> toRiskDTOList = new ArrayList<TORiskDTO>();

		for (GameOrder order : orders) {
			toRiskDTOList.add(packageOrderFundTORiskDTO(order));
		}

		fundRequest(toRiskDTOList);
	}
	public void orderFund(GameContext ctx,Collection<GameOrder> orders)
			throws GameRiskException {
		List<TORiskDTO> toRiskDTOList = new ArrayList<TORiskDTO>();
		for (GameOrder order : orders) {
			toRiskDTOList.add(packageOrderFundTORiskDTO(order));
		} 
		this.addRiskDto(ctx, toRiskDTOList);
	}
	public void orderFund_bk(GameContext ctx,Collection<GameOrder> orders)
			throws GameRiskException {
		for (GameOrder order : orders) {
			if(order!=null){
				this.addRiskDtoMap(ctx,order.getId(),packageOrderFundTORiskDTO(order));
			}
		} 
	}
	protected void addRiskDtoMap(GameContext ctx, Long keyId,TORiskDTO riskDto) {
		if(riskDto==null) return;
		
		Map<Long,List<TORiskDTO>> dtoListMap=(Map<Long,List<TORiskDTO>>)ctx.get("RISKDTOLISTMAP");
		if(dtoListMap==null){
			dtoListMap=new HashMap<Long,List<TORiskDTO>>();
		}
		List<TORiskDTO> existsRiskDtoList=dtoListMap.get(keyId);
		if(existsRiskDtoList==null){
			existsRiskDtoList= new ArrayList<TORiskDTO>(); 
		}
		existsRiskDtoList.add(riskDto); 
		dtoListMap.put(keyId, existsRiskDtoList); 
		ctx.set("RISKDTOLISTMAP", dtoListMap);
	} 
	protected void addRiskDto_bk(GameContext ctx, List<TORiskDTO> riskDtoList) {
		if(riskDtoList==null || riskDtoList.size()==0) return;
		List<TORiskDTO> dtoList=(List<TORiskDTO>)ctx.get("RISKDTOLIST");
		if(dtoList==null){
			dtoList=new ArrayList<TORiskDTO>(); 
		}
		dtoList.addAll(riskDtoList);
		ctx.set("RISKDTOLIST", dtoList);
	}

	/**
	 * @Title: orderFreeze
	 * @Description: 投注冻结
	 * @param orders
	 * @throws GameRiskException
	 */
	public void orderFreeze(Collection<GameOrder> orders)
			throws GameRiskException {

		List<TORiskDTO> toRiskDTOList = new ArrayList<TORiskDTO>();

		for (GameOrder order : orders) {
			toRiskDTOList.add(packageOrderFreezeTORiskDTO(order));
		}

		fundRequest(toRiskDTOList);
	}

	/**
	 * @Title: orderCancel
	 * @Description: 投注撤销
	 * @param orders
	 * @throws GameRiskException
	 */
	public void orderCancel(Collection<GameOrder> orders)
			throws GameRiskException {

		List<TORiskDTO> toRiskDTOList = new ArrayList<TORiskDTO>();

		for (GameOrder order : orders) {
			toRiskDTOList.add(packageOrderCancelTORiskDTO(order));
		}

		fundRequest(toRiskDTOList);
	}

	/**
	 * @Title: orderFund
	 * @Description: 投注扣款
	 * @param orders
	 * @throws GameRiskException
	 */
	public void orderFund(GameOrder order) throws GameRiskException {
		orderFund(Arrays.asList(order));
	}
	public void orderFund(GameContext ctx,GameOrder order) throws GameRiskException {
		orderFund(ctx,Arrays.asList(order));
	}

	/**
	 * @Title: orderFreeze
	 * @Description: 投注冻结
	 * @param orders
	 * @throws GameRiskException
	 */
	public void orderFreeze(GameOrder order) throws GameRiskException {
		orderFreeze(Arrays.asList(order));
	}

	/**
	 * @Title: orderCancel
	 * @Description: 投注撤销
	 * @param orders
	 * @throws GameRiskException
	 */
	public void orderCancel(GameOrder order) throws GameRiskException {
		orderCancel(Arrays.asList(order));
	}

	public TORiskDTO packageOrderFundTORiskDTO(GameOrder order) {
		TORiskDTO dto = packageTORiskDTO(order);
		if (dto.getPlanCodeList() == null) {
			dto.setType(GameFundTypesUtils.GAME_BET_UNFREEZER_DETEAIL_TYPE);
		} else {
			dto.setType(GameFundTypesUtils.GAME_PLAN_UNFREEZER_DETEAIL_TYPE);
		}
		return dto;
	}

	public TORiskDTO packageOrderFreezeTORiskDTO(GameOrder order) {
		TORiskDTO dto = packageTORiskDTO(order);
		dto.setType(GameFundTypesUtils.GAME_BET_FREEZER_DETEAIL_TYPE);
		return dto;
	}

	public TORiskDTO packageOrderCancelTORiskDTO(GameOrder order) { 
		TORiskDTO dto = packageTORiskDTO(order);
		//如果追中不停，则返回本金
		dto.setType(GameFundTypesUtils.GAME_BET_RETURN_DETEAIL_TYPE);
		/*need????????
		 * if(order.getPlanId()!=null){
			GamePlan plan=gamePlanDao.getById(order.getPlanId());
			if(plan.getStopMode()==StopMode.NO_STOP.getValue()){
				dto.setType(GameFundTypesUtils.GAME_ADMIN_RETURN_TYPE);
			}
		}*/
		return dto;
	} 

	public TORiskDTO packageOrderUnFreezeTORiskDTO(GameOrder order) {
		TORiskDTO dto = packageTORiskDTO(order);
		dto.setType(GameFundTypesUtils.GAME_SYS_CANCEL_BET_UNFREEZER_DETEAIL_TYPE);
		return dto;
	}

	public TORiskDTO packageOrderFundTORiskDTO(Long orderId) {
		return packageOrderFundTORiskDTO(gameOrderDaoImpl.getById(orderId));
	}

	public TORiskDTO packageOrderFreezeTORiskDTO(Long orderId) {
		return packageOrderFreezeTORiskDTO(gameOrderDaoImpl.getById(orderId));
	}

	public TORiskDTO packageOrderCancelTORiskDTO(Long orderId) {
		return packageOrderCancelTORiskDTO(gameOrderDaoImpl.getById(orderId));
	}

	public TORiskDTO packageOrderUnFreezeTORiskDTO(Long orderId) {
		return packageOrderUnFreezeTORiskDTO(gameOrderDaoImpl.getById(orderId));
	}

	private TORiskDTO packageTORiskDTO(GameOrder order) {
		TORiskDTO dto = new TORiskDTO();
		dto.setAmount(order.getTotamount() + "");
		dto.setIssueCode(order.getIssueCode());
		dto.setLotteryid(order.getLotteryid().longValue());
		dto.setOrderCodeList(order.getOrderCode());
		GamePlan gamePlan = gamePlanDao.getGamePlanByOrderId(order.getId());
		if (gamePlan != null) {
			dto.setPlanCodeList(gamePlan.getPlanCode());
		}
		dto.setUserid(order.getUserid() + "");
		return dto;
	}

	public void updateOrderCancelStatus(Long orderId) {
		updateOrderCancelStatus(Arrays.asList(orderId));
	}

	public void updateOrderCancelStatus(GameOrder gameOrder) {
		updateOrderCancelStatus(Arrays.asList(gameOrder));

	}

	public void updateOrderCancelStatus(Collection<Long> orderIds) {
		updateOrderCancelStatus(gameOrderDaoImpl.getByIds(new ArrayList<Long>(
				orderIds)));
	}

	public void updateOrderCancelStatus(List<GameOrder> gameOrders) {
		updateOrderStatus(gameOrders, 4);
	}

	public void updateOrderFreezeStatus(List<GameOrder> gameOrders) {
		updateOrderStatus(gameOrders, 1);
	}

	public void updateOrderFreezeStatus(GameOrder gameOrder) {
		updateOrderFreezeStatus(Arrays.asList(gameOrder));
	}

	public void updateOrderStatus(List<GameOrder> gameOrders, Integer status) {
		for (GameOrder gameOrder : gameOrders) { 
			gameOrder.setStatus(status);
			gameOrder.setCancelModes(2);
			gameOrder.setCancelTime(new Date());
			try {
				gameSlipDaoImpl.updateSlipByOrderID(gameOrder.getId(), status);
			} catch (Exception e) {
				log.error("update gameslip error", e);
				throw new GameRiskException("update gameslip error");
			}
			gameOrderDaoImpl.update(gameOrder);
			
			try{
				this.gameOrderService.addOrderLog(new GameContext(), gameOrder, status.toString(), this.getClass().toString());
			}catch(Exception e){
				log.error("订单状态发生变化，操作日志记录出错。(订单ID:"+gameOrder.getId()+" status:"+status+" exception:"+e.toString());
			}
		}
	}

	// ///////////////////new///////////////////////////
	@Override
	public int freeze(GameContext ctx, GameOrder order) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int unfreeze(GameContext ctx, GameOrder order) throws Exception {
		if (order == null)
			return 0;
		TORiskDTO dto = this.toRiskDTO(order);
		dto.setType(GameFundTypesUtils.GAME_SYS_CANCEL_BET_UNFREEZER_DETEAIL_TYPE);
		this.addRiskDto(ctx, dto);
		return 1;
	}

	@Override
	public int pay(GameContext ctx, GameOrder order) throws Exception {
		if (order == null) return 0; 
		TORiskDTO dto = this.toRiskDTO(order);
		if (dto.getPlanCodeList() == null) {
			dto.setType(GameFundTypesUtils.GAME_BET_UNFREEZER_DETEAIL_TYPE);
		} else {
			dto.setType(GameFundTypesUtils.GAME_PLAN_UNFREEZER_DETEAIL_TYPE);
		} 
		this.addRiskDto(ctx, dto);
		return 1;
	}

	@Override
	public int unpay(GameContext ctx, GameOrder order) throws Exception {
		if (order == null) return 0;
		TORiskDTO dto = this.toRiskDTO(order);
		dto.setType(GameFundTypesUtils.GAME_BET_RETURN_DETEAIL_TYPE);
		this.addRiskDto(ctx, dto);
		return 1;
	} 

}