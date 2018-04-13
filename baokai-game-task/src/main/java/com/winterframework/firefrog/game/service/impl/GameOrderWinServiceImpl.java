package com.winterframework.firefrog.game.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.firefrog.common.util.DateUtils;
import com.winterframework.firefrog.common.util.GameContext;
import com.winterframework.firefrog.game.dao.IGameOrderWinDao;
import com.winterframework.firefrog.game.dao.vo.GameOrder;
import com.winterframework.firefrog.game.dao.vo.GameOrderWin;
import com.winterframework.firefrog.game.dao.vo.GameSlip;
import com.winterframework.firefrog.game.entity.VOConvert4Task;
import com.winterframework.firefrog.game.exception.GameException;
import com.winterframework.firefrog.game.service.IGameOrderWinFundService;
import com.winterframework.firefrog.game.service.IGameOrderWinService;
import com.winterframework.firefrog.game.service.IGameSlipService;


/**
 * 中奖订单服务实现类
 * @ClassName
 * @Description
 * @author ibm
 * 2014年10月4日
 */
@Repository("gameOrderWinServiceImpl") 
@Transactional
public class GameOrderWinServiceImpl implements IGameOrderWinService {
	protected Logger log = LoggerFactory.getLogger(GameOrderWinServiceImpl.class);
	
	@Resource(name="gameOrderWinDaoImpl")
	private IGameOrderWinDao gameOrderWinDao; 
	@Resource(name="gameSlipServiceImpl")
	private IGameSlipService gameSlipService; 
	@Resource(name="gameOrderWinFundServcieImpl")
	private IGameOrderWinFundService gameOrderWinFundServcie; 
	
	@Override
	public int addNew(GameContext ctx, GameOrderWin orderWin)
			throws Exception {
		if(orderWin==null) return 0;
		return gameOrderWinDao.insert(orderWin); 
	}
	@Override
	public int save(GameContext ctx, GameOrderWin orderWin) throws Exception {
		if(orderWin==null) return 0;
		if(orderWin.getId()==null){
			this.gameOrderWinDao.insert(orderWin);
		}else{
			gameOrderWinDao.update(orderWin);
		}
		return 1;
	} 
	@Override
	public int remove(GameContext ctx, Long orderWinId) throws Exception {
		if(orderWinId==null) return 0;
		return gameOrderWinDao.delete(orderWinId);
	}
	@Override
	public int removeByOrderId(GameContext ctx, Long orderId) throws Exception {
		if(orderId==null) return 0;
		GameOrderWin orderWin=this.getByOrderId(ctx,orderId);
		if(orderWin==null) return 0;
		return this.remove(ctx, orderWin.getId());
	}
	@Override
	public GameOrderWin getById(GameContext ctx, Long orderWinId) throws Exception {
		return gameOrderWinDao.getById(orderWinId); 
	}
	@Override
	public GameOrderWin getByOrderId(GameContext ctx, Long orderId)
			throws Exception { 
		return gameOrderWinDao.selectGameOrderWinByOrderId(orderId);
	} 
	public int cancel(GameContext ctx,GameOrder order, GameOrderWin orderWin) throws Exception { 
		/**
		 * 1.撤销资金：派奖
		 * 2.更新中奖订单撤销信息
		 */
		if(order==null || orderWin==null) return 0;   
		if(orderWin.getStatus().intValue()==GameOrderWin.Status.DISTRIBUTE.getValue()){			
			this.gameOrderWinFundServcie.unaward(ctx, order, orderWin);
		}
		
		orderWin.setStatus(Long.valueOf(GameOrderWin.Status.CANCEL.getValue())); 
		this.gameOrderWinDao.update(orderWin); 
		return 1;	
	}  
	@Override
	public int cancelByOrder(GameContext ctx, GameOrder order) throws Exception {
		if(order==null) return 0; 
		return this.cancel(ctx,order, this.getByOrderId(ctx, order.getId()));  
	}
	
	@Override
	public int win(GameContext ctx, GameOrder order, GameOrderWin orderWin)
			throws Exception {  
		//计算
		Map<String,Long> slipSummaryMap=this.gameSlipService.getSummary(ctx, order.getId());
		if(slipSummaryMap==null){
			throw new GameException("Game slip not exists in order(orderId:"+order.getId()+")");
		}  
		Long maxSlipWinAmount=slipSummaryMap.get("maxSlipWinAmount");
		Long maxSlipWinRatio=slipSummaryMap.get("maxSlipWinRatio");
		Long totalSlipWinAmount=slipSummaryMap.get("totalSlipWinAmount");
		maxSlipWinAmount=maxSlipWinAmount==null?0L:maxSlipWinAmount;
		maxSlipWinRatio=maxSlipWinRatio==null?0L:maxSlipWinRatio;
		Long winsRatio=(totalSlipWinAmount*10000+order.getTotamount()/2)/order.getTotamount();
		if(orderWin==null){
			orderWin =VOConvert4Task.getGameOrderWinVo(order, totalSlipWinAmount);
			orderWin.setWinsRatio(winsRatio);
			orderWin.setMaxslipWins(maxSlipWinAmount);
			orderWin.setSlipWinsratio(maxSlipWinRatio);
			this.addNew(ctx, orderWin); 
		}else{
			orderWin.setWinsRatio(winsRatio);
			orderWin.setMaxslipWins(maxSlipWinAmount);
			orderWin.setSlipWinsratio(maxSlipWinRatio);
			orderWin.setCountWin(totalSlipWinAmount);
			orderWin.setCalculateWinTime(DateUtils.currentDate());
			orderWin.setStatus(Long.valueOf(GameOrderWin.Status.WAITING.getValue()));
			this.save(ctx, orderWin);
		}
		return 1;
	}
	@Override
	public int unwin(GameContext ctx, GameOrder order, GameOrderWin orderWin)
			throws Exception {
		if(orderWin==null) return 0;
		
		this.remove(ctx, orderWin.getId());
		return 1;
	}
	@Override
	public int freeze(GameContext ctx, GameOrder order, GameOrderWin orderWin)
			throws Exception {
		if(order==null || orderWin==null) return 0;
		orderWin.setStatus(Integer.valueOf(GameOrderWin.Status.FREEZE.getValue()).longValue());
		this.save(ctx, orderWin);
		return 1;
	}
	@Override
	public int freeze(GameContext ctx, GameOrder order) throws Exception {
		if(order==null ) return 0;
		GameOrderWin orderWin=this.getByOrderId(ctx, order.getId());
		return this.freeze(ctx, order,orderWin); 
	}
	
	@Override
	public int distribute(GameContext ctx, GameOrder order) throws Exception {
		if(order==null ) return 0;
		GameOrderWin orderWin=this.getByOrderId(ctx, order.getId());
		return this.freeze(ctx, order,orderWin); 
	}
	@Override
	public int distribute(GameContext ctx, GameOrder order,
			GameOrderWin orderWin) throws Exception {
		if(order==null || orderWin==null) return 0;
		this.gameOrderWinFundServcie.award(ctx, order, orderWin);
		
		orderWin.setStatus(Integer.valueOf(GameOrderWin.Status.DISTRIBUTE.getValue()).longValue());
		this.save(ctx, orderWin);
		return 1;
	}
	 
}
