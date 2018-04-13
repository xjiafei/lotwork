package com.winterframework.firefrog.game.service;

import com.winterframework.firefrog.game.dao.vo.GameWarnIssue;


/** 
* @ClassName: IGameWarnIssueService 
* @Description: 风险奖期service接口
* @author david 
* @date 2014-3-7 下午1:35:30 
*  
*/
public interface IGameWarnIssueService {

	GameWarnIssue getGameWarnIssueById(Long warnIssueId);

	void updateGameWarnIssue(GameWarnIssue gameWarnIssue);

	
}
