package com.winterframework.firefrog.beginmession.service;

import com.winterframework.firefrog.beginmession.web.dto.BeginNewDailyMissionRequest;
import com.winterframework.firefrog.beginmession.web.dto.BeginNewDailyMissionResponse;

public interface BeginNewDailyMissionService {
	
	public void insertDailyMission(BeginNewDailyMissionRequest dto) throws Exception;
	
	public BeginNewDailyMissionResponse selectNewMissionMaxVersion() throws Exception;
	
}
