package com.winterframework.firefrog.beginmession.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.google.common.base.Optional;
import com.google.common.collect.Maps;
import com.winterframework.firefrog.beginmession.dao.BeginNewChargeDao;
import com.winterframework.firefrog.beginmession.dao.vo.BeginNewCharge;
import com.winterframework.firefrog.beginmession.enums.BeginMissionEnum.AwardStatus;
import com.winterframework.firefrog.beginmession.enums.BeginMissionEnum.MissionType;
import com.winterframework.firefrog.beginmession.enums.BeginMissionEnum.Status;

@Repository
public class BeginNewChargeDaoImpl extends BaseBeginNewDao<BeginNewCharge> implements
		BeginNewChargeDao {

	@Override
	public List<BeginNewCharge> findMaxVersionAndRowNum() {
		return this.sqlSessionTemplate.selectList(this.getQueryPath("findMaxVersionAndRowNum"));
	}

	@Override
	public Optional<List<BeginNewCharge>> findByCondition2(BeginNewCharge entity) {
		List<BeginNewCharge> charges =  this.sqlSessionTemplate.selectList(this.getQueryPath("findByCondition2"),entity);
		return Optional.fromNullable(charges);
	}

	@Override
	public Optional<List<BeginNewCharge>> getByMultipleGet(Long userId,
			Long version,String YesOrNo) {
		Map<String,Object> map = Maps.newHashMap();
		map.put("multiGet", YesOrNo);
		map.put("version", version);	
		map.put("userId", userId);	
		map.put("awardStatus", AwardStatus.UnAward.getValue())	;	
		map.put("missionType", MissionType.FIRST_CHARGE.getValue())	;	
		map.put("missionStatus", Status.VALID.getValue())	;	
		List<BeginNewCharge> charges =  this.sqlSessionTemplate.selectList(this.getQueryPath("getByMultipleGet"),map);
		return Optional.fromNullable(charges);
	}

	
	@Override
	public Long getMaxChargeDate(Long userId) {
		Map<String,Object> map = Maps.newHashMap();
		map.put("userId", userId);	
		map.put("missionType", MissionType.FIRST_CHARGE.getValue());
		return this.sqlSessionTemplate.selectOne(this.getQueryPath("getMaxChargeDate"),map);
	}
}
