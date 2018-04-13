package com.winterframework.firefrog.schedule.beginmission;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.winterframework.firefrog.beginmession.dao.BeginMissionDao;
import com.winterframework.firefrog.beginmession.enums.BeginMissionEnum.MissionTask;
import com.winterframework.firefrog.beginmession.enums.BeginMissionEnum.TaskStatus;
import com.winterframework.firefrog.beginmession.service.BeginMissionService;

public abstract class BeginMissionTask {
	
	protected static final Logger log = LoggerFactory.getLogger(BeginMissionChargeTask.class);
	
	protected BeginMissionService beginMissionService; 
	
	protected BeginMissionDao beginMissionDao;
	
	public void execute(){
		log.info(this.getClass().getName()+" to start");
		Long taskId = beginMissionService.creatMissionTaskLog(getMissionTask());
		try {
			excuteJob();		
			beginMissionService.updateMissionTaskLog(taskId,TaskStatus.FINISH);
		} catch (Exception e) {
			log.error("beginMissionTask error", e.fillInStackTrace());
			beginMissionService.updateMissionTaskLog(taskId,TaskStatus.ERROR);
		}
		log.info(this.getClass().getName()+" to end");
	}
	
	abstract protected void excuteJob();
	
	abstract protected MissionTask getMissionTask();


	public BeginMissionService getBeginMissionService() {
		return beginMissionService;
	}

	public void setBeginMissionService(BeginMissionService beginMissionService) {
		this.beginMissionService = beginMissionService;
	}

	public BeginMissionDao getBeginMissionDao() {
		return beginMissionDao;
	}

	public void setBeginMissionDao(BeginMissionDao beginMissionDao) {
		this.beginMissionDao = beginMissionDao;
	}
}
