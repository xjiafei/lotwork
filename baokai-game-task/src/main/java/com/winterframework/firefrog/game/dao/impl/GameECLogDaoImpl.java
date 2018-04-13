package com.winterframework.firefrog.game.dao.impl;

import java.util.Map;

import org.springframework.stereotype.Repository;

import com.winterframework.firefrog.game.dao.IGameECLogDao;
import com.winterframework.firefrog.game.dao.vo.GameECLog;
import com.winterframework.orm.dal.ibatis3.BaseIbatis3Dao;

/**
 * 
* @ClassName: GameWarnIssueLogDaoImpl 
* @Description: 警告奖期日志Dao实现类
* @author Richard
* @date 2013-12-27 下午4:13:52 
*
 */
@Repository("gameECLogDaoImpl")
public class GameECLogDaoImpl extends BaseIbatis3Dao<GameECLog> implements IGameECLogDao {

	@Override
	public long addGameECLog(GameECLog gameECLog) throws Exception {
		this.insert(gameECLog);
		return gameECLog.getId();
	}
	
	@Override
	public void upGameECLog(Map<?,?> gamelog) throws Exception {
		sqlSessionTemplate.update("upGameECLog", gamelog);
	}

}
