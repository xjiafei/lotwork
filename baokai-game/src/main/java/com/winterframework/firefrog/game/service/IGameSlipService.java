package com.winterframework.firefrog.game.service;

import java.util.List;
import java.util.Map;

import com.winterframework.firefrog.common.util.GameContext;
import com.winterframework.firefrog.game.dao.vo.GameOrder;
import com.winterframework.firefrog.game.dao.vo.GameSlip;


/**
 *IGamePlanDetailService
 * @ClassName
 * @Description 注单接口
 * @author ibm
 * 2014年9月7日
 */
public interface IGameSlipService { 
	////////////////////////行为----begin////////////////////////// 
	public int save(GameContext ctx,GameSlip slip) throws Exception;  
	public GameSlip getById(GameContext ctx,Long slipId) throws Exception;  
	public List<GameSlip> getByOrderId(GameContext ctx,Long orderId) throws Exception;  
	
	/**
	 * 重置(业务中不存在单个注单的撤销，因此不提供单个撤销的行为)
	 * @param ctx
	 * @param planDetail
	 * @throws Exception
	 */
	int reset(GameContext ctx,GameSlip slip) throws Exception;
	int resetByOrder(GameContext ctx,GameOrder order) throws Exception;
	/**
	 * @param ctx
	 * @param slip
	 * @return 真正撤销的数目
	 * @throws Exception
	 */ 
	public int cancel(GameContext ctx,GameSlip slip) throws Exception;
	public int cancelByOrder(GameContext ctx,GameOrder order) throws Exception;    
	/**
	 * 开奖
	 * @param ctx
	 * @param slip
	 * @return	是否中奖
	 * @throws Exception
	 */
	public boolean draw(GameContext ctx,GameOrder order,GameSlip slip) throws Exception;
	////////////////////////行为----end//////////////////////////
	////////////////////////业务服务----begin////////////////////////// 
	boolean isWin(GameContext ctx, GameOrder order, GameSlip slip) throws Exception;
	/**
	 * 获取中大奖统计（一、二等奖）
	 * @param ctx
	 * @param slip
	 * @return
	 * @throws Exception
	 */
	public Map<Long,Long> getBigAwardCount(GameContext ctx,GameSlip slip) throws Exception;
	 
	/**
	 * 获取统计信息
	 * @param ctx
	 * @param orderId
	 * @return
	 */
	Map<String, Long> getSummary(GameContext ctx, Long orderId) throws Exception;
	
	/**
	 * 改变状态
	 * @param orderId
	 * @param fromStatus
	 * @param toStatus
	 * @return
	 * @throws Exception
	 */
	int changeStatus(Long orderId,Integer fromStatus,Integer toStatus) throws Exception;

	////////////////////////业务服务----end//////////////////////////
}
