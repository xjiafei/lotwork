package com.winterframework.firefrog.user.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.winterframework.firefrog.user.dao.ILevelRecycleDao;
import com.winterframework.firefrog.user.entity.LevelRecycle;
import com.winterframework.firefrog.user.entity.LevelRecycleDTO;
import com.winterframework.orm.dal.ibatis3.BaseIbatis3Dao;

@Repository("levelRecycleDaoImpl")
public class LevelRecycleDaoImpl extends BaseIbatis3Dao<LevelRecycle> implements
		ILevelRecycleDao {

	@Override
	public List<LevelRecycle> queryLevelRecycleHistory(LevelRecycleDTO levelRecycleDTO)
			throws Exception {
		return sqlSessionTemplate.selectList("queryLevelRecycleHistory", levelRecycleDTO);
	}

	@Override
	public void applyLevelRecycle(LevelRecycleDTO levelRecycleDTO)
			throws Exception {
		sqlSessionTemplate.insert("applyLevelRecycle", levelRecycleDTO);

	}

	@Override
	public void updateRecycleStatus(LevelRecycleDTO levelRecycleDTO) throws Exception{
		sqlSessionTemplate.update("updateRecycleStatus", levelRecycleDTO);
		
	}
}
