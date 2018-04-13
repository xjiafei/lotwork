package com.winterframework.firefrog.beginmession.web.dto;

import java.io.Serializable;
import java.util.List;

import com.winterframework.firefrog.beginmession.dao.vo.BeginNewDaybet;
import com.winterframework.firefrog.beginmession.dao.vo.BeginNewDayques;
import com.winterframework.firefrog.beginmession.dao.vo.BeginNewMission;
import com.winterframework.firefrog.beginmession.dao.vo.BeginNewTolbet;

public class BeginNewDailyMissionResponse implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6299320249071700039L;

	private BeginNewMission beginNewMission;

	private List<BeginNewTolbet> beginNewTolbets;
	
	private List<BeginNewDayques> beginNewDayqueses;
	
	private List<BeginNewDaybet> beginNewDaybets;
	

	public List<BeginNewTolbet> getBeginNewTolbets() {
		return beginNewTolbets;
	}

	public void setBeginNewTolbets(List<BeginNewTolbet> beginNewTolbets) {
		this.beginNewTolbets = beginNewTolbets;
	}

	public List<BeginNewDayques> getBeginNewDayqueses() {
		return beginNewDayqueses;
	}

	public void setBeginNewDayqueses(List<BeginNewDayques> beginNewDayqueses) {
		this.beginNewDayqueses = beginNewDayqueses;
	}

	public List<BeginNewDaybet> getBeginNewDaybets() {
		return beginNewDaybets;
	}

	public void setBeginNewDaybets(List<BeginNewDaybet> beginNewDaybets) {
		this.beginNewDaybets = beginNewDaybets;
	}

	public BeginNewMission getBeginNewMission() {
		return beginNewMission;
	}

	public void setBeginNewMission(BeginNewMission beginNewMission) {
		this.beginNewMission = beginNewMission;
	}
	
	
	
}
