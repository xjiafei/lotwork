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
import com.winterframework.firefrog.game.service.IGameOrderFundMakeupService;
 
/**
 * 奖期销售结束后补调用事件处理类
 * @ClassName
 * @Description
 * @author ibm
 * 2015年01月16日
 */
@Service("gameOrderFundEventHandler")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, readOnly = false)
public class GameOrderFundEventHandler implements IEventHandler { 
	@Resource(name = "gameOrderFundMakeupServiceImpl")
	private IGameOrderFundMakeupService gameOrderFundMakeupService;
	
	@Override
	public void handleEvent(GameControlEvent event) throws Exception { 
		this.gameOrderFundMakeupService.doBusiness(event);
	}

}
