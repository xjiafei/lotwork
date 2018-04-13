package com.winterframework.firefrog.game.dao.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.winterframework.firefrog.game.dao.IGameTrendIssueDao;
import com.winterframework.firefrog.game.dao.vo.GameTrendIssue;
import com.winterframework.orm.dal.ibatis3.BaseIbatis3Dao;
 
/**
 * 开奖号码走势图奖期DAO实现类
 * @ClassName
 * @Description
 * @author ibm
 * 2014年10月23日
 */
@Repository("gameTrendIssueDaoImpl")
public class GameTrendIssueDaoImpl extends BaseIbatis3Dao<GameTrendIssue> implements IGameTrendIssueDao {
	@Override
	public GameTrendIssue getByLotteryIdAndIssueCode(Long lotteryId , Long issueCode) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("lotteryId", lotteryId);
		map.put("issueCode", issueCode);
		return this.sqlSessionTemplate.selectOne(getQueryPath("getByLotteryIdAndIssueCode"), map);
	} 
}
