package com.winterframework.firefrog.beginmession.dao.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.google.common.collect.Maps;
import com.winterframework.firefrog.beginmession.dao.BeginMissionDao;
import com.winterframework.firefrog.beginmession.dao.vo.BeginMission;
import com.winterframework.firefrog.beginmession.enums.BeginMissionEnum.AwardStatus;
import com.winterframework.firefrog.beginmession.enums.BeginMissionEnum.CancelReason;
import com.winterframework.firefrog.beginmession.enums.BeginMissionEnum.MissionType;
import com.winterframework.firefrog.beginmession.enums.BeginMissionEnum.Status;
import com.winterframework.firefrog.beginmession.utils.BeginMissionServiceHelper;
import com.winterframework.orm.dal.ibatis3.BaseIbatis3Dao;

/**
 * 
 * @author Ami.Tsai
 *
 */
@Repository
public class BeginMissionDaoImpl extends BaseIbatis3Dao<BeginMission> implements BeginMissionDao{

	@Override
	public BeginMission findByUserId(Long userId) {
		return this.sqlSessionTemplate.selectOne(this.getQueryPath("findByUserId"), userId);
	}

	@Override
	public Long findFirstWithdraw(Long userId, List<Long> status) {
		Map<String,Object> params =Maps.newHashMap();
		params.put("userId", userId);
		params.put("status", status);
		return this.sqlSessionTemplate.selectOne(this.getQueryPath("findFirstWithdraw"), userId);
	}

	@Override
	public Long findFirstcharge(Long userId, Long status) {
		Map<String,Object> params =Maps.newHashMap();
		params.put("userId", userId);
		params.put("status", status);
		return this.sqlSessionTemplate.selectOne(this.getQueryPath("findFirstcharge"), userId);
	}

	@Override
	public Integer updatebindCardDate(Long userId, Date bindCardDate) {
		Map<String,Object> params =Maps.newHashMap();
		params.put("userId", userId);
		params.put("bindCardDate", bindCardDate);
		return this.sqlSessionTemplate.update(this.getQueryPath("updatebindCardDate"), params);
	}

	@Override
	public Integer updateChargeAmt(Long userId, Long chargeAmt) {
		Map<String,Object> params =Maps.newHashMap();
		params.put("userId", userId);
		params.put("chargeAmt", chargeAmt);
		return this.sqlSessionTemplate.update(this.getQueryPath("updateChargeAmt"), params);
	}

	@Override
	public Integer updateWithdrawAmt(Long userId, Long withdrawAmt) {
		Map<String,Object> params =Maps.newHashMap();
		params.put("userId", userId);
		params.put("withdrawAmt", withdrawAmt);
		return this.sqlSessionTemplate.update(this.getQueryPath("updateWithdrawAmt"), params);
	}

	@Override
	public List<BeginMission> getChargeMission() {
		Map<String,Object> map = Maps.newHashMap();
		map.put("missionStatus", Status.VALID.getValue());
		map.put("awardStatus", AwardStatus.UnAward.getValue());
		map.put("awardType", MissionType.FIRST_CHARGE.getValue());
		return this.sqlSessionTemplate.selectList(this.getQueryPath("getChargeMission"), map);
	}

	@Override
	public List<BeginMission> findByCondition(BeginMission entity) {
		return this.sqlSessionTemplate.selectList(this.getQueryPath("findByCondition"), entity);
	}

	@Override
	public List<BeginMission> getDailyBetMission() {
		Map<String,Object> map = Maps.newHashMap();
		map.put("missionStatus", Status.VALID.getValue());
		map.put("awardType", MissionType.DAY_BET.getValue());
		return this.sqlSessionTemplate.selectList(this.getQueryPath("getDailyBetMission"), map);
	}

	@Override
	public List<BeginMission> getBindCardMission() {
		Map<String,Object> map = Maps.newHashMap();
		map.put("missionStatus", Status.BEVALID.getValue());
		map.put("awardType", MissionType.BIND_CARD.getValue());
		return this.sqlSessionTemplate.selectList(this.getQueryPath("getBindCardMission"), map);
	}

	@Override
	public void cancelMission(Long userId, String cancelReason) {
		Map<String,Object> map = Maps.newHashMap();
		map.put("userId", userId);
		map.put("cancelReason", cancelReason);
		map.put("status", Status.CANCEL.getValue());		
		map.put("cancelUser", BeginMissionServiceHelper.AUTO_USER);		
		map.put("cancelTime", new Date());				
		this.sqlSessionTemplate.update(this.getQueryPath("cancelMission"), map);
	}

	@Override
	public void cancelOverTimeMission() {
		Map<String,Object> map = Maps.newHashMap();
		map.put("cancelReason", CancelReason.Mission_Over_Time.getValue());
		map.put("cancelStatus",Status.INVALID.getValue());		
		map.put("validStatus", Status.VALID.getValue());
		map.put("cancelUser", BeginMissionServiceHelper.AUTO_USER);		
		map.put("cancelTime", new Date());				
		this.sqlSessionTemplate.update(this.getQueryPath("cancelOverTimeMission"), map);
		
	}

	@Override
	public void updateStatusValid(Long userId, Status status) {
		Map<String,Object> map = Maps.newHashMap();
		map.put("userId", userId);
		map.put("status", Status.VALID.getValue());
		map.put("validTime", new Date());		
		this.sqlSessionTemplate.update(this.getQueryPath("updateStatusValid"), map);
	}

	@Override
	public List<BeginMission> getInValidMission() {
		Map<String,Object> map = Maps.newHashMap();
		map.put("status1", Status.VALID.getValue());
		map.put("status2", Status.BEVALID.getValue());		
		return this.sqlSessionTemplate.selectList(this.getQueryPath("getInValidMission"), map);
	}

}
