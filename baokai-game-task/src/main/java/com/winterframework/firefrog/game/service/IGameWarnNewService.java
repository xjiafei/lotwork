package com.winterframework.firefrog.game.service;

import com.winterframework.firefrog.common.util.GameContext;
import com.winterframework.firefrog.game.dao.vo.GameOrder;

/**
 * 风险审核服务接口
 * @ClassName
 * @Description
 * @author ibm
 * 2014年10月13日
 */
public interface IGameWarnNewService {
 
	/**
	 * 风险审核业务
	 * @param ctx
	 * @param order
	 * @return
	 * @throws Exception
	 */
	int doBusiness(GameContext ctx,GameOrder order) throws Exception;	  
}
