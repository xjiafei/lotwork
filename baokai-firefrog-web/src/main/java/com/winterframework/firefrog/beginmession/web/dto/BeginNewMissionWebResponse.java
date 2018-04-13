package com.winterframework.firefrog.beginmession.web.dto;

import java.io.Serializable;
import java.util.List;

import com.winterframework.firefrog.beginmession.dao.vo.BeginNewCharge;
import com.winterframework.firefrog.beginmession.dao.vo.BeginNewMission;

public class BeginNewMissionWebResponse implements Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8275805243424775112L;

	private BeginNewMission mission;
	
	private List<BeginNewCharge> charges;

	public BeginNewMission getMission() {
		return mission;
	}

	public void setMission(BeginNewMission mission) {
		this.mission = mission;
	}

	public List<BeginNewCharge> getCharges() {
		return charges;
	}

	public void setCharges(List<BeginNewCharge> charges) {
		this.charges = charges;
	}
	
	public BeginNewCharge getChargeIndex(int index) {
		return charges.get(index);
	}

}
