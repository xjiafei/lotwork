package com.winterframework.firefrog.game.service;

import com.winterframework.firefrog.common.util.GameContext;
import com.winterframework.firefrog.game.dao.vo.GameOrder;
import com.winterframework.firefrog.game.dao.vo.GameOrderWin;


/**	
 * 中奖订单服务接口
 * @ClassName
 * @Description
 * @author ibm
 * 2014年10月4日
 */
public interface IGameOrderWinService {  
	public int addNew(GameContext ctx,GameOrderWin orderWin) throws Exception;  
	public int save(GameContext ctx,GameOrderWin orderWin) throws Exception; 
	public int remove(GameContext ctx,Long orderWinId) throws Exception;
	public int removeByOrderId(GameContext ctx,Long orderId) throws Exception;
	public GameOrderWin getById(GameContext ctx,Long orderWinId) throws Exception;  
	public GameOrderWin getByOrderId(GameContext ctx,Long orderId) throws Exception;  
	/**
	 * @param ctx
	 * @param orderId
	 * @return 真正撤销的数目
	 * @throws Exception
	 */ 
	public int cancelByOrder(GameContext ctx,GameOrder order) throws Exception;  
	
	/**
	 * 中奖服务方法--比较特殊（该实体本身就是中奖才有的结果，考虑中奖发生变化）
	 * @param ctx
	 * @param order
	 * @param orderWin
	 * @throws Exception
	 */
	public int win(GameContext ctx,GameOrder order,GameOrderWin orderWin) throws Exception;  
	/**
	 * 不中奖服务方法--比较特殊（该实体本身就是中奖才有的结果，考虑不中奖发生变化）
	 * @param ctx
	 * @param order
	 * @param orderWin
	 * @throws Exception
	 */
	public int unwin(GameContext ctx,GameOrder order,GameOrderWin orderWin) throws Exception;  
	
	/**
	 * 冻结
	 * @param ctx
	 * @param order
	 * @param orderWin
	 * @return
	 * @throws Exception
	 */
	public int freeze(GameContext ctx,GameOrder order,GameOrderWin orderWin) throws Exception;  
	public int freeze(GameContext ctx,GameOrder order) throws Exception;  
	/**
	 * 派奖
	 * @param ctx
	 * @param order
	 * @param orderWin
	 * @return
	 * @throws Exception
	 */
	public int distribute(GameContext ctx,GameOrder order,GameOrderWin orderWin) throws Exception;  
	public int distribute(GameContext ctx,GameOrder order) throws Exception;  
}
