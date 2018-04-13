package com.winterframework.firefrog.beginmession.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.google.common.collect.Maps;
import com.winterframework.firefrog.beginmession.dao.BeginNewDaybetDao;
import com.winterframework.firefrog.beginmession.dao.vo.BeginNewDaybet;
import com.winterframework.firefrog.beginmession.enums.BeginMissionEnum.MissionType;

@Repository
public class BeginNewDaybetDaoImpl extends BaseBeginNewDao<BeginNewDaybet> implements
BeginNewDaybetDao {

	@Override
	public List<BeginNewDaybet> getDayBetAward(Long userId, Long betAmount,
			Long version) {
		Map<String,Object> map = Maps.newHashMap();
		map.put("userId", userId);
		map.put("betAmount", betAmount);
		map.put("version", version);
		map.put("missionType", MissionType.DAY_BET.getValue());
		return this.sqlSessionTemplate.selectList(this.getQueryPath("getDayBetAward"), map);
	}

}
