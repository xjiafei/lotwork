package com.winterframework.firefrog.game.web.dto;

import java.io.Serializable;
import java.util.Date;

public class DailyActivityRewardStruc implements Serializable{

	private static final long serialVersionUID = -254861760419529196L;

	private Integer betCount;
	
	private String date;
	
	private Boolean hadGet;

	public Integer getBetCount() {
		return betCount;
	}

	public void setBetCount(Integer betCount) {
		this.betCount = betCount;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public Boolean getHadGet() {
		return hadGet;
	}

	public void setHadGet(Boolean hadGet) {
		this.hadGet = hadGet;
	}
}
