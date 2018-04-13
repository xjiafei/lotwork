package com.winterframework.firefrog.schedule.beginmission;

import java.util.List;

import com.winterframework.firefrog.beginmession.dao.vo.BeginMission;
import com.winterframework.firefrog.beginmession.enums.BeginMissionEnum.MissionTask;

/**
 * 用戶活動資格到期
 * @author Ami.Tsai
 *
 */
public class BeginMissionCancelMissionTask extends BeginMissionTask{

	
	@Override
	protected void excuteJob() {
		List<BeginMission> missions = beginMissionDao.getInValidMission();
		for(BeginMission beginMission:missions){
			beginMissionService.cancelMissionJob(beginMission);					
		}
	}


	@Override
	protected MissionTask getMissionTask() {
		return MissionTask.INVALID_MISSION_STATUS_TASK;
	}
	
}
