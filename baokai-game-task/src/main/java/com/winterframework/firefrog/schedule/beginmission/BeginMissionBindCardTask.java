package com.winterframework.firefrog.schedule.beginmission;

import java.util.List;

import com.winterframework.firefrog.beginmession.dao.vo.BeginMission;
import com.winterframework.firefrog.beginmession.enums.BeginMissionEnum.MissionTask;

/**
 * 綁卡獎勵
 * @author Ami.Tsai
 *
 */
public class BeginMissionBindCardTask extends BeginMissionTask{

	
	@Override
	protected void excuteJob() {
		List<BeginMission> missions = beginMissionDao.getBindCardMission();
		if(missions!=null){
			for(BeginMission mission:missions){
				beginMissionService.bindCardAwardJob(mission);				
			}
		}

	}


	@Override
	protected MissionTask getMissionTask() {
		return MissionTask.BIND_CARD_AWARD_TASK;
	}
}
