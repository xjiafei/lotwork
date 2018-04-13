package com.winterframework.firefrog.game.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.winterframework.firefrog.game.dao.IGameLevelRecycleDao;
import com.winterframework.firefrog.user.dao.vo.LevelRecycle;
import com.winterframework.firefrog.user.entity.LevelRecycleDTO;
import com.winterframework.orm.dal.ibatis3.BaseIbatis3Dao;

@Repository("gameLevelRecycleDaoImpl")
public class GameLevelRecycleDaoImpl extends BaseIbatis3Dao<LevelRecycle> implements
		IGameLevelRecycleDao {

	@Override
	public List<LevelRecycle> queryLevelRecycleHistory(LevelRecycleDTO levelRecycleDTO)
			throws Exception {
		return sqlSessionTemplate.selectList("queryLevelRecycleHistory", levelRecycleDTO);
	}	

}
