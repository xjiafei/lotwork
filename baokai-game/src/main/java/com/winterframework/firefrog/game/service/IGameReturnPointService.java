package com.winterframework.firefrog.game.service;

import com.winterframework.firefrog.common.util.GameContext;
import com.winterframework.firefrog.game.dao.vo.GameOrder;
import com.winterframework.firefrog.game.dao.vo.GameRetPoint;


/**
 * 返点服务接口
 * @ClassName
 * @Description
 * @author ibm
 * 2014年10月4日
 */
public interface IGameReturnPointService {  
	public int save(GameContext ctx,GameRetPoint retPoint) throws Exception;  
	public GameRetPoint getById(GameContext ctx,Long retPointId) throws Exception;  
	public GameRetPoint getByOrderId(GameContext ctx,Long orderId) throws Exception;  
	/**
	 * 重置
	 * @param ctx
	 * @param slip
	 * @return
	 * @throws Exception
	 */
	int reset(GameContext ctx,GameOrder order,GameRetPoint retPoint) throws Exception;
	int resetByOrder(GameContext ctx,GameOrder order) throws Exception;
	/**
	 * 冻结
	 * @param ctx
	 * @param retPoint
	 * @return
	 * @throws Exception
	 */
	public int freeze(GameContext ctx,GameOrder order,GameRetPoint retPoint) throws Exception; 
	public int freeze(GameContext ctx,GameOrder order) throws Exception; 
	/**
	 * 派发
	 * @param ctx
	 * @param retPoint
	 * @return
	 * @throws Exception
	 */
	public int distribute(GameContext ctx,GameOrder order,GameRetPoint retPoint) throws Exception;
	public int distribute(GameContext ctx,GameOrder order) throws Exception;
	/**
	 * @param ctx
	 * @param orderId
	 * @return 真正撤销的数目
	 * @throws Exception
	 */ 
	public int cancel(GameContext ctx,GameOrder order,GameRetPoint retPoint) throws Exception; 
	int cancel(GameContext ctx,GameOrder order) throws Exception; 
	
}
