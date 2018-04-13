package com.winterframework.firefrog.fund.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.winterframework.firefrog.fund.dao.IGameGoldDetailDao;
import com.winterframework.firefrog.fund.dao.vo.GameGoldDetailVO;
import com.winterframework.orm.dal.ibatis3.BaseIbatis3Dao;

@Repository("gameGoldDetailDaoImpl")
public class GameGoldDetailDaoImpl  extends BaseIbatis3Dao<GameGoldDetailVO> implements IGameGoldDetailDao{

	@Override
	public List<GameGoldDetailVO> getGameGoldDetails(GameGoldDetailVO vo) {
		Map<String, Object> filters = new HashMap<String, Object>();
		filters.put("account", vo.getAccount());
		filters.put("useMoney", vo.getUseMoney());
		filters.put("endUseMoney", vo.getEndUseMoney());
		filters.put("sumUseMoney", vo.getSumUseMoney());
		filters.put("endSumUseMoney", vo.getEndSumUseMoney());
		filters.put("isFreeze", vo.getIsFreeze());
		return sqlSessionTemplate.selectList(getQueryPath("getGameGoldDetails"),filters);
	}

}
