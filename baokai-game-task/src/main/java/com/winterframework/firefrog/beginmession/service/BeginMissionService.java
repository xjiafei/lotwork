package com.winterframework.firefrog.beginmession.service;

import com.winterframework.firefrog.beginmession.dao.vo.BeginMission;
import com.winterframework.firefrog.beginmession.enums.BeginMissionEnum.MissionTask;
import com.winterframework.firefrog.beginmession.enums.BeginMissionEnum.TaskStatus;


public interface BeginMissionService {
	
	public void chargeAwardJob(BeginMission mission);
	
	public void tolbetAwardJob(BeginMission mission);
	
	public void dailyBetAwardJob(BeginMission mission);
	
	public void bindCardAwardJob(BeginMission mission);
	
	public void cancelMissionJob(BeginMission mission);

	Long creatMissionTaskLog(MissionTask missionTask);

	void updateMissionTaskLog(Long taskId, TaskStatus status);
}
