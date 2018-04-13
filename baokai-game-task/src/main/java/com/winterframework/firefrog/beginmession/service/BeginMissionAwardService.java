package com.winterframework.firefrog.beginmession.service;

import java.util.List;

import com.google.common.collect.Lists;
import com.winterframework.firefrog.beginmession.dao.vo.BeginAward;
import com.winterframework.firefrog.beginmession.dao.vo.BeginMission;
import com.winterframework.firefrog.beginmession.dao.vo.BeginNewCharge;
import com.winterframework.firefrog.beginmession.dao.vo.BeginNewMission;
import com.winterframework.firefrog.beginmession.enums.BeginMissionEnum.MissionType;
import com.winterframework.firefrog.game.dao.vo.GameOrder;


public interface BeginMissionAwardService {
	
	public final static long CALCUTE_UNIT = 10000L;
	
	public final static List<Integer> ORDER_STATUSES = Lists.newArrayList(GameOrder.Status.UN_WIN.getValue(),GameOrder.Status.WIN.getValue());
	
	static final String ACTIVITY_REASON_KEY = "PM-PGXX-3";
	
	Boolean charegAward(BeginMission mission , BeginNewCharge charge,BeginAward award);

	void toAward(Long userId, MissionType type, Long missionId, Long amount);
	
	void bindCardAward(Long userId,BeginNewMission newMission);
}