package com.winterframework.firefrog.game.service;

import com.winterframework.firefrog.common.util.GameContext;
import com.winterframework.firefrog.game.dao.vo.GameTrendIssue;

 
/**
 * 走势图奖期服务接口
 * @ClassName
 * @Description
 * @author ibm
 * 2014年10月23日
 */
public interface IGameTrendIssueService { 
	////////////////////////行为----begin////////////////////////// 
	public GameTrendIssue getByLotteryIdAndIssueCode(GameContext ctx,Long lotteryId,Long issueCode) throws Exception;
	public int save(GameContext ctx,GameTrendIssue trendIssue) throws Exception;
	public int remove(GameContext ctx,Long id) throws Exception;
	////////////////////////行为----end//////////////////////////  	
		
	////////////////////////业务服务----begin////////////////////////// 
	////////////////////////业务服务----end////////////////////////// 
}
