package com.winterframework.firefrog.phone.web.begin.entity;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.winterframework.firefrog.phone.web.begin.vo.BeginNewCharge;
import com.winterframework.firefrog.phone.web.begin.vo.BeginNewDaybet;
import com.winterframework.firefrog.phone.web.begin.vo.BeginNewDayques;
import com.winterframework.firefrog.phone.web.begin.vo.BeginNewMission;
import com.winterframework.firefrog.phone.web.begin.vo.BeginNewTolbet;

public class CountDown implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1098871676459093797L;

	private Long isbinded;
	
	private String bankDays;
	
	private String gameDays;

	public Long getIsbinded() {
		return isbinded;
	}

	public void setIsbinded(Long isbinded) {
		this.isbinded = isbinded;
	}

	public String getBankDays() {
		return bankDays;
	}

	public void setBankDays(String bankDays) {
		this.bankDays = bankDays;
	}

	public String getGameDays() {
		return gameDays;
	}

	public void setGameDays(String gameDays) {
		this.gameDays = gameDays;
	}
	
	
}
