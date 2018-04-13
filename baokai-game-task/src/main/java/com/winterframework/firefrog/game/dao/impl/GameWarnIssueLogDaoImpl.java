package com.winterframework.firefrog.game.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.winterframework.firefrog.game.dao.IGameWarnIssueLogDao;
import com.winterframework.firefrog.game.dao.vo.GameWarnIssueLog;
import com.winterframework.orm.dal.ibatis3.BaseIbatis3Dao;

/**
 * 
* @ClassName: GameWarnIssueLogDaoImpl 
* @Description: 警告奖期日志Dao实现类
* @author Richard
* @date 2013-12-27 下午4:13:52 
*
 */
@Repository("gameWarnIssueLogDaoImpl")
public class GameWarnIssueLogDaoImpl extends BaseIbatis3Dao<GameWarnIssueLog> implements IGameWarnIssueLogDao {

	@Override
	public void addGameWarnIssueLog(GameWarnIssueLog warnIssueLog) throws Exception {
		this.insert(warnIssueLog);
	}

	@Override
	public List<GameWarnIssueLog> queryWarnLogByIssueCode(Long issueCode, Long lotteryId) throws Exception {
		
		Map<String, Object> map = new HashMap<String, Object>();		
		map.put("issueCode",issueCode);
		map.put("lotteryId", lotteryId);		
		return this.sqlSessionTemplate.selectList("queryWarnLogByLotteryIssue", map);
	}

}
