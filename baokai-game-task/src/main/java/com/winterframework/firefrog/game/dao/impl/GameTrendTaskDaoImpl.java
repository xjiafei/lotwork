package com.winterframework.firefrog.game.dao.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.winterframework.firefrog.game.dao.IGameTrendTaskDao;
import com.winterframework.firefrog.game.dao.vo.GameTrendTask;
import com.winterframework.orm.dal.ibatis3.BaseIbatis3Dao;

/**
 * 走势图任务DAO实现类
 * @ClassName
 * @Description
 * @author ibm
 * 2014年10月12日
 */
@Repository("gameTrendTaskDaoImpl")
public class GameTrendTaskDaoImpl extends BaseIbatis3Dao<GameTrendTask> implements IGameTrendTaskDao {
	@Override
	public GameTrendTask getByLotteryIdAndIssueCode(Long lotteryId,
			Long issueCode) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("lotteryId", lotteryId);
		map.put("issueCode", issueCode);
		return this.sqlSessionTemplate.selectOne(getQueryPath("getByLotteryIdAndIssueCode"), map);
	}
}
