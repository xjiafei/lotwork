package com.winterframework.firefrog.beginmession.dao.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import com.google.common.collect.Maps;
import com.winterframework.firefrog.beginmession.dao.BeginAwardDao;
import com.winterframework.firefrog.beginmession.dao.vo.BeginAward;
import com.winterframework.firefrog.beginmession.enums.BeginMissionEnum.AwardStatus;
import com.winterframework.firefrog.beginmession.enums.BeginMissionEnum.MissionType;
import com.winterframework.firefrog.beginmession.enums.BeginMissionEnum.Status;
import com.winterframework.orm.dal.ibatis3.BaseIbatis3Dao;

/**
 * 
 * @author Ami.Tsai
 *
 */
@Repository
public class BeginAwardDaoImpl extends BaseIbatis3Dao<BeginAward> implements BeginAwardDao{

	@Override
	public List<BeginAward> findByCondition(BeginAward award) {
		return this.sqlSessionTemplate.selectList(this.getQueryPath("findByCondition"), award);
	}

	@Override
	public BeginAward findFirstByCondition(BeginAward award) {
		BeginAward entity = null;
		List<BeginAward> awards= this.sqlSessionTemplate.selectList(this.getQueryPath("findByCondition"), award);
		if(awards!=null && !awards.isEmpty()){
			entity = awards.get(0);
		}
		return entity;
	}

	@Override
	public void updateAwardStatus(BeginAward award) {
		Map<String , Object> map = Maps.newHashMap();
		map.put("id", award.getId());
		map.put("status", award.getStatus());
		map.put("awardTime", award.getAwardTime());		
		if(StringUtils.isNotEmpty(award.getCancelReason())){
			map.put("cancelReason", award.getCancelReason());					
		}
		this.sqlSessionTemplate.update(this.getQueryPath("updateAwardStatus"), map);
	}

	@Override
	public void updateUnAward(Long userId,String cancelReason) {
		Map<String , Object> map = Maps.newHashMap();
		map.put("userId", userId);
		map.put("cancelStatus", AwardStatus.AwardCancel.getValue());		
		map.put("unAwardStatus", AwardStatus.UnAward.getValue());
		map.put("cancelReason", cancelReason);				
		map.put("awardType1", MissionType.FIRST_CHARGE.getValue());				
		map.put("awardType2", 0l);						
		this.sqlSessionTemplate.update(this.getQueryPath("updateUnAward"), map);
	}

	@Override
	public Long getAwardNowByMissionId(BeginAward award) {
		Map<String , Object> map = Maps.newHashMap();
		map.put("userId", award.getUserId());
		map.put("missionId", award.getMissionId());
		map.put("type1", award.getAwardType1());
		return this.sqlSessionTemplate.selectOne(this.getQueryPath("getAwardNowByMissionId"), map);
	}

	@Override
	public BeginAward getNowDailyAnsAward(Long userId) {
		Map<String , Object> map = Maps.newHashMap();
		map.put("userId", userId);
		map.put("type1", MissionType.DAY_ANS.getValue());
		return this.sqlSessionTemplate.selectOne(this.getQueryPath("getNowDailyAnsAward"), map);
	}

	@Override
	public List<BeginAward> getEffectiveAward(MissionType mission) {
		Map<String , Object> map = Maps.newHashMap();
		map.put("awardStatus", AwardStatus.UnAward.getValue());
		map.put("missionStatus", Status.VALID.getValue());
		map.put("missionType", mission.getValue());
		return this.sqlSessionTemplate.selectList(this.getQueryPath("getEffectiveAward"), map);
	}

}
