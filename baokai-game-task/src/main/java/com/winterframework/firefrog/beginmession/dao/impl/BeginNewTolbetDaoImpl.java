package com.winterframework.firefrog.beginmession.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.google.common.collect.Maps;
import com.winterframework.firefrog.beginmession.dao.BeginNewTolbetDao;
import com.winterframework.firefrog.beginmession.dao.vo.BeginNewTolbet;
import com.winterframework.firefrog.beginmession.enums.BeginMissionEnum.MissionType;

@Repository
public class BeginNewTolbetDaoImpl extends BaseBeginNewDao<BeginNewTolbet> implements BeginNewTolbetDao {

	@Override
	public List<BeginNewTolbet> getTolBetAward(Long version, Integer betDay,
			Long userId) {
		
		Map<String,Object> map =Maps.newHashMap();
		map.put("userId", userId);
		map.put("betDay", betDay);
		map.put("version", version);		
		map.put("missionType", MissionType.TOL_BET.getValue());				
		return this.sqlSessionTemplate.selectList(this.getQueryPath("getTolBetAward"), map);
	}

}
