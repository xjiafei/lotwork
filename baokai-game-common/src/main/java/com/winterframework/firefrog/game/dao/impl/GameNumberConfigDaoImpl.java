package com.winterframework.firefrog.game.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.winterframework.firefrog.game.dao.IGameNumberConfigDao;
import com.winterframework.firefrog.game.dao.vo.GameNumberConfig;
import com.winterframework.orm.dal.ibatis3.BaseIbatis3Dao;

@Repository("gameNumberConfigDaoImpl")
public class GameNumberConfigDaoImpl extends BaseIbatis3Dao<GameNumberConfig> implements
		IGameNumberConfigDao {

	@Override
	public List<GameNumberConfig> getByEffDate() {
		return this.sqlSessionTemplate.selectList(getQueryPath("getByEffDate"));
	}
	
	@Override
	public List<GameNumberConfig> getByYearLaterDate(Date date){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("yearLater", date);
		return this.sqlSessionTemplate.selectList(getQueryPath("getByYearLaterDate"), map);
	}

}
