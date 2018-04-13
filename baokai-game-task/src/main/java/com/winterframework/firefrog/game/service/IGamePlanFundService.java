package com.winterframework.firefrog.game.service;

import com.winterframework.firefrog.common.util.GameContext;
import com.winterframework.firefrog.game.dao.vo.GamePlan;
import com.winterframework.firefrog.game.dao.vo.GamePlanDetail;


/**
 * 追号资金服务接口
 * @ClassName
 * @Description
 * @author ibm
 * 2014年10月3日
 */
public interface IGamePlanFundService { 
	public int freeze(GameContext ctx,GamePlan plan,GamePlanDetail planDetail) throws Exception;
	public int unfreeze(GameContext ctx,GamePlan plan,GamePlanDetail planDetail) throws Exception;
	
}
