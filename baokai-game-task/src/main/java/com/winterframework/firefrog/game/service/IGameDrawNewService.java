package com.winterframework.firefrog.game.service;

import com.winterframework.firefrog.common.util.GameContext;
import com.winterframework.firefrog.game.dao.vo.GameControlEvent;


/**
 * 奖期开奖服务接口
 * @ClassName
 * @Description
 * @author ibm
 * 2014年10月10日
 */
public interface IGameDrawNewService {
 
	/**
	 * 开奖业务方法
	 * @param event	调度事件实体
	 * @throws Exception
	 */
	public int doBusiness(GameContext ctx,GameControlEvent event) throws Exception;
	/**
	 * 开奖业务方法
	 * @param lotteryId
	 * @param issueCode
	 * @throws Exception
	 */
	public int doBusiness(GameContext ctx,Long lotteryId, Long issueCode) throws Exception;
}
