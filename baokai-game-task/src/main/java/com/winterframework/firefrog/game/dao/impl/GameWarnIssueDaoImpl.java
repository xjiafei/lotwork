package com.winterframework.firefrog.game.dao.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.winterframework.firefrog.game.dao.IGameWarnIssueDao;
import com.winterframework.firefrog.game.dao.vo.GameWarnIssue;
import com.winterframework.orm.dal.ibatis3.BaseIbatis3Dao;

/**
 * 
 * @ClassName: GameWarnIssueDaoImpl
 * @Description: 警告奖期DAO实现类
 * @author Richard
 * @date 2013-12-27 下午4:13:09
 *
 */
@Repository("gameWarnIssueDaoImpl")
public class GameWarnIssueDaoImpl extends BaseIbatis3Dao<GameWarnIssue>
		implements IGameWarnIssueDao {

	@Override
	public GameWarnIssue queryGameWarnIssueByLotteryIdAndIssueCode(
			Long lotteryId, Long issueCode) throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("lotteryId", lotteryId);
		map.put("issueCode", issueCode);

		GameWarnIssue issue = this.sqlSessionTemplate.selectOne(
				"queryGameWarnIssueByLotteryIdAndIssueCode", map);
		return issue;
	}

	@Override
	public int updateGameWarnIssue(GameWarnIssue warn) throws Exception {
		return update(warn);
	}

}
