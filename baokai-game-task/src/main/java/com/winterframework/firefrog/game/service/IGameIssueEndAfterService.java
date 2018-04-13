package com.winterframework.firefrog.game.service;

import com.winterframework.firefrog.game.dao.vo.GameControlEvent;
 
/**
 * 奖期销售结束后补服务接口
 * @ClassName
 * @Description
 * @author ibm
 * 2015年1月16日
 */
public interface IGameIssueEndAfterService { 
	////////////////////////行为----begin////////////////////////// 
	
	////////////////////////行为----end//////////////////////////  	
		
	////////////////////////业务服务----begin////////////////////////// 
	void doBusiness(GameControlEvent event) throws Exception; 
	////////////////////////业务服务----end////////////////////////// 
}
