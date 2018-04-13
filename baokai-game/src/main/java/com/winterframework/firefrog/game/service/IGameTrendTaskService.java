package com.winterframework.firefrog.game.service;

import com.winterframework.firefrog.common.util.GameContext;
import com.winterframework.firefrog.game.dao.vo.GameTrendTask;

/**
 * 走势图任务服务接口
 * @ClassName
 * @Description
 * @author ibm
 * 2014年10月12日
 */
public interface IGameTrendTaskService { 
	////////////////////////行为----begin////////////////////////// 
	public GameTrendTask getById(GameContext ctx,Long id) throws Exception;    
	public int save(GameContext ctx,GameTrendTask trendTask) throws Exception;  
	////////////////////////行为----end//////////////////////////  	
}
