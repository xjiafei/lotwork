package com.winterframework.firefrog.beginmession.service;

import com.winterframework.firefrog.beginmession.web.dto.BeginNewMissionRequest;
import com.winterframework.firefrog.beginmession.web.dto.BeginNewMissionResponse;

public interface BeginNewMissionService {

	public void insertNewMission(BeginNewMissionRequest dto) throws Exception;
	
	public BeginNewMissionResponse selectNewMissionMaxVersion() throws Exception ;
}
