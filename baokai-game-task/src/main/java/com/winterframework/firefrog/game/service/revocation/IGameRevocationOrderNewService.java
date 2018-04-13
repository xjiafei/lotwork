package com.winterframework.firefrog.game.service.revocation;

import com.winterframework.firefrog.common.util.GameContext;
import com.winterframework.firefrog.game.dao.vo.GameOrder;


/**
 * 订单撤销服务类
 * @ClassName
 * @Description
 * @author ibm
 * 2014年10月3日
 */
public interface IGameRevocationOrderNewService {
	/**
	 * 撤销
	 * @param ctx
	 * @param gameOrder
	 * @return 真正撤销的数目
	 * @throws Exception
	 */
	public int doRevocation(GameContext ctx,GameOrder gameOrder) throws Exception;
}
