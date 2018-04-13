package com.winterframework.firefrog.game.dao;

import java.util.List;

import com.winterframework.firefrog.game.dao.vo.GameWarnIssueLog;
import com.winterframework.orm.dal.ibatis3.BaseDao;

public interface IGameWarnIssueLogDao extends BaseDao<GameWarnIssueLog> {
	
	/**
	 * 
	* @Title: addGameWarnIssueLog 
	* @Description: 添加奖期异常处理日志 
	 */
	public void addGameWarnIssueLog(GameWarnIssueLog warnIssueLog) throws Exception;
	
	/**
	 * 
	* @Title: queryWarnLogByIssueCode 
	* @Description: 根据issue_code查询异常处理日志
	* @param issueCode
	* @return
	* @throws Exception
	 */
	List<GameWarnIssueLog> queryWarnLogByIssueCode(Long issueCode, Long lotteryId) throws Exception;

}
