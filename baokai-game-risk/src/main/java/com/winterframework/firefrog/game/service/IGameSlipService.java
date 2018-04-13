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
