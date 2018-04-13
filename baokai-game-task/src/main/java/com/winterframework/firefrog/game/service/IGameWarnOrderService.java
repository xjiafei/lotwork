package com.winterframework.firefrog.game.service;

import com.winterframework.firefrog.common.util.GameContext;
import com.winterframework.firefrog.game.dao.vo.GameWarnOrder;

 
/**
 * 风险订单服务接口
 * @ClassName
 * @Description
 * @author ibm
 * 2014年10月16日
 */
public interface IGameWarnOrderService {
	 
	public int save(GameContext ctx,GameWarnOrder warnOrder) throws Exception; 
}
