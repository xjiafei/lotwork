package com.winterframework.firefrog.beginmession.web.dto;

import java.io.Serializable;
import java.util.List;

import com.winterframework.firefrog.beginmession.dao.vo.BeginNewCharge;
import com.winterframework.firefrog.beginmession.dao.vo.BeginNewMission;

public class BeginNewMissionWebRequest implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3995411514383559023L;
	
	private BeginNewMission mission;
	
	private List<BeginNewCharge> charges;
	
	private String userName;

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

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	

}
