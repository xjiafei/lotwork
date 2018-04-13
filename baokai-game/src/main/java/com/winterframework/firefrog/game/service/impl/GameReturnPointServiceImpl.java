package com.winterframework.firefrog.game.service.impl;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.firefrog.common.util.GameContext;
import com.winterframework.firefrog.game.dao.IGameReturnPointDao;
import com.winterframework.firefrog.game.dao.vo.GameOrder;
import com.winterframework.firefrog.game.dao.vo.GameRetPoint;
import com.winterframework.firefrog.game.dao.vo.GameSlip;
import com.winterframework.firefrog.game.service.IGameReturnPointFundService;
import com.winterframework.firefrog.game.service.IGameReturnPointService;

 
/**
 * 返点服务实现类
 * @ClassName
 * @Description
 * @author ibm
 * 2014年10月4日
 */
@Repository("gameReturnPointServiceImpl") 
@Transactional
public class GameReturnPointServiceImpl implements IGameReturnPointService {
	protected Logger log = LoggerFactory.getLogger(GameReturnPointServiceImpl.class);
	
	@Resource(name="gameReturnPointDaoImpl")
	private IGameReturnPointDao gameReturnPointDao; 
	@Resource(name="gameReturnPointFundServcieImpl")
	private IGameReturnPointFundService gameReturnPointFundServcie; 
	
	
	@Override
	public int save(GameContext ctx, GameRetPoint retPoint) throws Exception {
		int count=0;
		if(retPoint==null) return count;
		if(retPoint.getId()==null){
			count= this.gameReturnPointDao.insert(retPoint);
		}else{
			count=gameReturnPointDao.update(retPoint);			
		}
		return count;
	}
	@Override
	public GameRetPoint getById(GameContext ctx, Long returnPointId) throws Exception {
		return gameReturnPointDao.getById(returnPointId); 
	}
	@Override
	public GameRetPoint getByOrderId(GameContext ctx, Long orderId)
			throws Exception { 
		return gameReturnPointDao.getGameRetPointByGameOrderId(orderId);
	} 
	
	@Override
	public int reset(GameContext ctx,GameOrder order,GameRetPoint retPoint) throws Exception {
		if(order==null || retPoint==null) return 0;
		
		int status=retPoint.getStatus();
		if(status==GameRetPoint.Status.FREEZE.getValue()) return 0;
		
		this.cancel(ctx, order, retPoint);
		
		retPoint.setStatus(GameRetPoint.Status.FREEZE.getValue());
		this.save(ctx, retPoint);
		return 1;
	}
	
	@Override
	public int resetByOrder(GameContext ctx, GameOrder order) throws Exception {
		if(order==null) return 0;
		GameRetPoint retPoint=this.getByOrderId(ctx, order.getId());
		this.reset(ctx, order, retPoint);
		return 1;
	}
	
	@Override
	public int freeze(GameContext ctx,GameOrder order, GameRetPoint retPoint) throws Exception {
		if(retPoint==null) return 0; 
		this.gameReturnPointFundServcie.freeze(ctx, order, retPoint);
		
		retPoint.setStatus(GameRetPoint.Status.FREEZE.getValue());
		this.gameReturnPointDao.update(retPoint);
		return 1;
	}
	@Override
	public int freeze(GameContext ctx, GameOrder order) throws Exception {
		if(order==null) return 0;  
		GameRetPoint retPoint=this.getByOrderId(ctx, order.getId());
		return this.freeze(ctx, order, retPoint);
	}
	@Override
	public int distribute(GameContext ctx, GameOrder order,GameRetPoint retPoint) throws Exception {
		/**
		 * 1.调用资金服务--ctx中返回RISKDTOLIST
		 * 2.更新返点信息
		 */
		if(retPoint==null) return 0;  
		this.gameReturnPointFundServcie.distribute(ctx, order, retPoint);
		
		retPoint.setStatus(GameRetPoint.Status.DISTRIBUTE.getValue());
		this.gameReturnPointDao.update(retPoint); 
		return 1;
	}
	@Override
	public int distribute(GameContext ctx, GameOrder order) throws Exception {
		if(order==null) return 0; 
		GameRetPoint retPoint=this.getByOrderId(ctx, order.getId());
		return this.distribute(ctx, order, retPoint);
	}
	@Override
	public int cancel(GameContext ctx, GameOrder order,GameRetPoint retPoint) throws Exception { 
		if(retPoint==null) return 0; 
		
		int status=retPoint.getStatus();
		if(status==GameRetPoint.Status.CANCEL.getValue()) return 0;
		
		if(status==GameRetPoint.Status.DISTRIBUTE.getValue()){			
			this.gameReturnPointFundServcie.cancel(ctx, order, retPoint);
		} 
		
		retPoint.setStatus(GameRetPoint.Status.CANCEL.getValue());
		this.gameReturnPointDao.update(retPoint); 
		return 1;
	}  
	@Override
	public int cancel(GameContext ctx, GameOrder order) throws Exception {
		if(order==null) return 0; 
		GameRetPoint retPoint=this.getByOrderId(ctx, order.getId());
		return this.cancel(ctx, order, retPoint); 
	}
	
}
