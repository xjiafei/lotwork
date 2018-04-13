package com.winterframework.firefrog.shortlived.sheepactivity.dto;

import java.util.List;

import com.winterframework.firefrog.shortlived.sheepactivity.dao.vo.ActivitySheepDetail;

public class ActivitySheepUserDiceResponse {
	
	private Long lastDiceTime;//剩余押宝次数
	
	private Long diceContinus;//连中次数
	
	private List<ActivitySheepDetail> list ;

	public Long getLastDiceTime() {
		return lastDiceTime;
	}

	public void setLastDiceTime(Long lastDiceTime) {
		this.lastDiceTime = lastDiceTime;
	}

	public Long getDiceContinus() {
		return diceContinus;
	}

	public void setDiceContinus(Long diceContinus) {
		this.diceContinus = diceContinus;
	}

	public List<ActivitySheepDetail> getList() {
		return list;
	}

	public void setList(List<ActivitySheepDetail> list) {
		this.list = list;
	}
}
