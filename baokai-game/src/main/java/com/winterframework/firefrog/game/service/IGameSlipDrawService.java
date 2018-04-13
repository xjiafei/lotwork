package com.winterframework.firefrog.game.service;

import com.winterframework.firefrog.common.util.GameContext;
import com.winterframework.firefrog.game.dao.vo.GameOrder;
import com.winterframework.firefrog.game.dao.vo.GameSlip;


/**
 * 注单开奖服务接口
 * @ClassName
 * @Description
 * @author ibm
 * 2014年10月10日
 */
public interface IGameSlipDrawService {  
	/**
	 * 开奖业务
	 * @param ctx
	 * @param slip
	 * @return	是否中奖
	 * @throws Exception
	 */
	public boolean doBusiness(GameContext ctx,GameOrder order,GameSlip slip) throws Exception; 
	boolean isWin(GameContext ctx, GameOrder order, GameSlip slip) throws Exception;
}
