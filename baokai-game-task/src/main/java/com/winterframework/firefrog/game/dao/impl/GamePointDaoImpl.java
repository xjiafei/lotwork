package com.winterframework.firefrog.game.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.winterframework.firefrog.game.dao.IGamePointDao;
import com.winterframework.firefrog.game.dao.vo.GamePoint;
import com.winterframework.orm.dal.ibatis3.BaseIbatis3Dao;

@Repository("gamePointDaoImpl")
public class GamePointDaoImpl extends BaseIbatis3Dao<GamePoint> implements IGamePointDao {

	@Override
	public void saveGamePoints(List<GamePoint> pointsList) throws Exception {
		this.insert(pointsList);
	}

	@Override
	public List<GamePoint> getGamePointsBySlipId(Long slipId, List<String> point) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("slipId", slipId);
		paramMap.put("point", point);
		return this.sqlSessionTemplate.selectList(getQueryPath("getBySlipId"), paramMap);
	}

	/**
	* Title: insertGamePointSlipId
	* Description:
	* @param planDetailId
	* @param slipId
	* @throws Exception 
	* @see com.winterframework.firefrog.game.dao.IGamePointDao#insertGamePointSlipId(java.lang.Long, java.lang.Long) 
	*/
	@Override
	public void insertGamePointSlipId(Long planDetailId, Long pkgDetailId,Long slipId, String betDetail) throws Exception {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("planDetailId", planDetailId);
		paramMap.put("pkgDetailId", pkgDetailId);
		paramMap.put("slipId", slipId);
		paramMap.put("betDetail", betDetail);
		this.sqlSessionTemplate.update("insertGamePointSlipId", paramMap);

	}

}
