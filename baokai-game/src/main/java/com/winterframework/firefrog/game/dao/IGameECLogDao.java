package com.winterframework.firefrog.game.dao;

import java.util.Map;

import com.winterframework.firefrog.game.dao.vo.GameECLog;
import com.winterframework.orm.dal.ibatis3.BaseDao;

public interface IGameECLogDao extends BaseDao<GameECLog> {

	/**
	 * 
	* @Title: addGameWarnIssueLog 
	* @Description: 增加开奖中心日志 
	 */
	public long addGameECLog(GameECLog gameECLog) throws Exception;
	
	public void upGameECLog(Map gamelog) throws Exception;
	
	}
