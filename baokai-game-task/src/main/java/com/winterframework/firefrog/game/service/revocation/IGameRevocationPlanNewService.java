package com.winterframework.firefrog.game.service.revocation;

import com.winterframework.firefrog.common.util.GameContext;
import com.winterframework.firefrog.game.dao.vo.GamePlanDetail;


/**
 * 追号撤销服务接口
 * @ClassName
 * @Description
 * @author ibm
 * 2014年10月4日
 */
public interface IGameRevocationPlanNewService { 
	/**
	 * 撤销
	 * @param ctx
	 * @param gamePlanDetail
	 * @return 真正撤销的数目
	 * @throws Exception
	 */
	public int doRevocation(GameContext ctx,GamePlanDetail gamePlanDetail) throws Exception;

}
