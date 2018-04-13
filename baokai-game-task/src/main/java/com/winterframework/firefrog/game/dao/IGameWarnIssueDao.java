package com.winterframework.firefrog.game.dao;

import java.util.List;

import com.winterframework.firefrog.game.dao.vo.GameWarnIssue;
import com.winterframework.orm.dal.ibatis3.BaseDao;

public interface IGameWarnIssueDao extends BaseDao<GameWarnIssue> {

	public GameWarnIssue queryGameWarnIssueByLotteryIdAndIssueCode(
			Long lotteryId, Long issueCode) throws Exception;

	public int updateGameWarnIssue(GameWarnIssue warn) throws Exception;

}
