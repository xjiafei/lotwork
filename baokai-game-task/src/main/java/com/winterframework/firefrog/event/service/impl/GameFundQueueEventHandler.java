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
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.firefrog.event.service.IEventHandler;
import com.winterframework.firefrog.game.dao.vo.GameControlEvent;
import com.winterframework.firefrog.game.service.IGameFundQueueService;
 
/**
 * 资金队列调用时间处理类
 * @ClassName
 * @Description
 * @author ibm
 * 2014年12月16日
 */
@Service("gameFundQueueEventHandler")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, readOnly = false)
public class GameFundQueueEventHandler implements IEventHandler {

	@Resource(name = "gameFundQueueServiceImpl")
	private IGameFundQueueService gameFundQueueService;
	
	@Override
	public void handleEvent(GameControlEvent event) throws Exception {
		//已取消该任务
		//this.gameFundQueueService.doRequestNew(event);
	}

}
