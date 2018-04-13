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
import com.winterframework.firefrog.game.service.gametrend.IGameTrendChartService;

/** 
* @ClassName: GameTrendChartRegenerateEventHandler 
* @Description: 走势图数据重新生成(历史奖期开奖号码)
* @author Denny 
* @date 2014-8-7 下午3:52:21 
*  
*/
@Service("gameTrendChartRegenerateEventHandler")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, readOnly = false)
public class GameTrendChartRegenerateEventHandler implements IEventHandler {

	@Resource(name = "gameTrendChartServiceImpl")
	private IGameTrendChartService gameTrendChartService;
	
	@Override
	public void handleEvent(GameControlEvent event) throws Exception {
		gameTrendChartService.generateTrendData(event);
	}

}
