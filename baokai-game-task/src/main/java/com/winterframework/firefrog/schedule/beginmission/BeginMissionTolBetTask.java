package com.winterframework.firefrog.schedule.beginmission;

import java.util.List;

import com.winterframework.firefrog.beginmession.dao.vo.BeginMission;
import com.winterframework.firefrog.beginmession.enums.BeginMissionEnum.MissionTask;
import com.winterframework.firefrog.beginmession.enums.BeginMissionEnum.Status;

/**
 * 累積投注派獎
 * @author Ami.Tsai
 *
 */
public class BeginMissionTolBetTask extends BeginMissionTask{


	@Override
	protected void excuteJob() {
		BeginMission entity = new BeginMission();
		entity.setStatus(Status.VALID.getValue());
		List<BeginMission> missions = beginMissionDao.findByCondition(entity);
		if(missions!=null){
			for(BeginMission mission:missions){
				beginMissionService.tolbetAwardJob(mission);				
			}
		}
	}


	@Override
	protected MissionTask getMissionTask() {
		return MissionTask.TOL_BET_AWARD_TASK;
	}


}
