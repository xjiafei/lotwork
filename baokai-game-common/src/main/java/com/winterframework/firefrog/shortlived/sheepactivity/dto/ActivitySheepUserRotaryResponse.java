package com.winterframework.firefrog.shortlived.sheepactivity.dto;

import java.util.List;

import com.winterframework.firefrog.shortlived.sheepactivity.dao.vo.ActivitySheepDetail;

public class ActivitySheepUserRotaryResponse {
	
	private Long lastDiceTime;//剩余押宝次数
	
	private List<ActivitySheepDetail> myList ;
	
	private List<ActivitySheepDetail> allList ;

	public Long getLastDiceTime() {
		return lastDiceTime;
	}

	public void setLastDiceTime(Long lastDiceTime) {
		this.lastDiceTime = lastDiceTime;
	}

	public List<ActivitySheepDetail> getMyList() {
		return myList;
	}

	public void setMyList(List<ActivitySheepDetail> myList) {
		this.myList = myList;
	}

	public List<ActivitySheepDetail> getAllList() {
		return allList;
	}

	public void setAllList(List<ActivitySheepDetail> allList) {
		this.allList = allList;
	}


}
