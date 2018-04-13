package com.winterframework.firefrog.game.service;

import com.winterframework.firefrog.common.util.GameContext;
import com.winterframework.firefrog.game.dao.vo.GameOrder;


/**
 * 订单开奖服务接口
 * @ClassName
 * @Description
 * @author ibm
 * 2014年10月10日
 */
public interface IGameOrderDrawService { 
	public int doBusiness(GameContext ctx,GameOrder order) throws Exception; 
	boolean isWin(GameContext ctx, GameOrder order) throws Exception;
}
