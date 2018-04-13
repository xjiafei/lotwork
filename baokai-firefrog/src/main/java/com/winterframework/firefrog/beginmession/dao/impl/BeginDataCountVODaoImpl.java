package com.winterframework.firefrog.beginmession.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.google.common.collect.Maps;
import com.winterframework.firefrog.beginmession.dao.BeginDataCountVODao;
import com.winterframework.firefrog.beginmession.dao.vo.BeginDataCountVO;
import com.winterframework.firefrog.beginmession.enums.BeginMissionEnum.AwardStatus;
import com.winterframework.firefrog.beginmession.enums.BeginMissionEnum.MissionType;
import com.winterframework.firefrog.fund.entity.FundChargeOrder;
import com.winterframework.firefrog.fund.entity.FundWithdrawOrder;
import com.winterframework.orm.dal.ibatis3.BaseIbatis3Dao;

@Repository
public class BeginDataCountVODaoImpl extends BaseIbatis3Dao<BeginDataCountVO> implements BeginDataCountVODao{

	@Override
	public Long getMissionCount(Date startTime, Date endTime) {
		Map<String,Object> map = Maps.newHashMap();
		map.put("startTime", startTime);
		map.put("endTime", endTime);		
		return this.sqlSessionTemplate.selectOne(this.getQueryPath("getMissionCount"), map);
	}

	@Override
	public Long getCharegCount(Date startTime, Date endTime) {
		Map<String,Object> map = Maps.newHashMap();
		map.put("startTime", startTime);
		map.put("endTime", endTime);		
		map.put("chargeStatus", FundChargeOrder.Status.SUCCESS.getValue());				
		return this.sqlSessionTemplate.selectOne(this.getQueryPath("getCharegCount"), map);
	}

	@Override
	public Long getWithdrawCount(Date startTime, Date endTime) {
		Map<String,Object> map = Maps.newHashMap();
		map.put("startTime", startTime);
		map.put("endTime", endTime);		
		List<Long> status = new ArrayList<Long>();
		status.add(FundWithdrawOrder.WithdrawStauts.SUCCESS.getValue());
		status.add(FundWithdrawOrder.WithdrawStauts.PART.getValue());		
		map.put("withDrawStatuses", status);				
		return this.sqlSessionTemplate.selectOne(this.getQueryPath("getWithdrawCount"), map);
	}

	@Override
	public Long getBindCardAward(Date startTime, Date endTime) {
		Map<String,Object> map = Maps.newHashMap();
		map.put("startTime", startTime);
		map.put("endTime", endTime);		
		map.put("cardType", MissionType.BIND_CARD.getValue());
		map.put("status", AwardStatus.AwardSucess.getValue());
		return this.sqlSessionTemplate.selectOne(this.getQueryPath("getBindCardAward"), map);
	}

	@Override
	public Double getTotalCharegAmt(Date startTime, Date endTime) {
		Map<String,Object> map = Maps.newHashMap();
		map.put("startTime", startTime);
		map.put("endTime", endTime);		
		return this.sqlSessionTemplate.selectOne(this.getQueryPath("getTotalCharegAmt"), map);
	}

	@Override
	public Long getAwardLotteryCount(Date startTime, Date endTime) {
		Map<String,Object> map = Maps.newHashMap();
		map.put("startTime", startTime);
		map.put("endTime", endTime);		
		map.put("awardStatus", AwardStatus.AwardSucess.getValue());				
		return this.sqlSessionTemplate.selectOne(this.getQueryPath("getAwardLotteryCount"), map);
	}

	@Override
	public Long getAwardTotalAmt(Date startTime, Date endTime) {
		Map<String,Object> map = Maps.newHashMap();
		map.put("startTime", startTime);
		map.put("endTime", endTime);		
		map.put("missionType", MissionType.EGG.getValue());		
		map.put("awardStatus", AwardStatus.AwardSucess.getValue());				
		return this.sqlSessionTemplate.selectOne(this.getQueryPath("getAwardLotteryCount"), map);
	}

	@Override
	public Long getAwardLotteryTime(Date startTime, Date endTime) {
		Map<String,Object> map = Maps.newHashMap();
		map.put("startTime", startTime);
		map.put("endTime", endTime);
		map.put("status", AwardStatus.AwardSucess.getValue());				
		return this.sqlSessionTemplate.selectOne(this.getQueryPath("getAwardLotteryTime"), map);
	}

	@Override
	public Double getTotalOrderAmt(Date startTime, Date endTime) {
		Map<String,Object> map = Maps.newHashMap();
		map.put("startTime", startTime);
		map.put("endTime", endTime);		
		map.put("orderStatus", AwardStatus.AwardSucess.getValue());				
		return this.sqlSessionTemplate.selectOne("getTotalOrderAmt", map);
	}




}
