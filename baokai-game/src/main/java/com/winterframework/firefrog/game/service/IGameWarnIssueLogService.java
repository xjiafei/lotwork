package com.winterframework.firefrog.game.service;

import com.winterframework.firefrog.game.dao.vo.GameWarnIssueLog;

/** 
* @ClassName: IGameWarnIssueLogService 
* @Description: 风险奖期日志service
* @author Alan
* @date 2013-11-18 下午1:55:17 
*  
*/
public interface IGameWarnIssueLogService {

	/**
	 * 
	* @Title: addWarnIssueLog 
	* @Description: 添加异常奖期处理日志 
	* @param log
	* @throws Exception
	 */
	public void addWarnIssueLog(GameWarnIssueLog log) throws Exception;
	
	/**
	 * 
	* @Title: queryWarnByIssueCode 
	* @Description: 根据奖期code查询异常日志
	* @return GameWarnIssueLog
	* @throws Exception
	 */
	public GameWarnIssueLog queryWarnByIssueCode(Long warnIssueId) throws Exception;
	
}
