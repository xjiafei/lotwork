/**   
* @Title: GameTrendChartRegenerateEventHandler.java 
* @Package com.winterframework.firefrog.event.service.impl 
* @Description: winter-game-task.GameTrendChartRegenerateEventHandler.java 
* @author Denny  
* @date 2014-8-7 下午3:52:21 
* @version V1.0   
*/
package com.winterframework.firefrog.event.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.firefrog.event.service.IEventHandler;
import com.winterframework.firefrog.game.dao.vo.GameControlEvent;
import com.winterframework.firefrog.game.service.IGamePlanOnlyService;
 
 
/**
 * 追号任务调用事件处理类
 * @ClassName
 * @Description
 * @author ibm
 * 2015年3月21日
 */
@Service("gamePlanEventHandler")
@Transactional(rollbackFor = Exception.class)
public class GamePlanEventHandler implements IEventHandler { 
	@Resource(name = "gamePlanOnlyServiceImpl")
	private IGamePlanOnlyService gamePlanOnlyService;
	
	@Override
	public void handleEvent(GameControlEvent event) throws Exception { 
		gamePlanOnlyService.doBusiness(event);
	}

}
