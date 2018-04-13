package com.winterframework.firefrog.schedule.beginmission;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.winterframework.firefrog.beginmession.dao.vo.BeginMission;
import com.winterframework.firefrog.beginmession.enums.BeginMissionEnum.MissionTask;

/**
 * 
 * @author Ami.Tsai
 * 充值派獎
 */
public class BeginMissionChargeTask extends BeginMissionTask{

	public static final Logger log = LoggerFactory.getLogger(BeginMissionChargeTask.class);
	
	@Override
	protected void excuteJob() {
		List<BeginMission> missions = beginMissionDao.getChargeMission();
		
		for(BeginMission mission:missions){
			beginMissionService.chargeAwardJob(mission);			
		}
	}

	@Override
	protected MissionTask getMissionTask() {
		return MissionTask.CHAREG_AWARD_TASK;
	}

}
