package com.winterframework.firefrog.game.service;

import com.winterframework.firefrog.common.util.GameContext;
import com.winterframework.firefrog.game.dao.vo.GameOrderLog;
 
/**
 * 订单操作日志服务接口
 * @ClassName
 * @Description
 * @author ibm
 * 2014年10月10日
 */
public interface IGameOrderLogService {

	/**
	 * 新增
	 * @param orderLog
	 * @return
	 * @throws Exception
	 */
	public int save(GameContext ctx,GameOrderLog orderLog) throws Exception; 
}
