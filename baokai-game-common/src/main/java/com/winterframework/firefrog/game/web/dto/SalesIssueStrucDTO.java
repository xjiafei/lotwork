package com.winterframework.firefrog.game.web.dto;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

public class SalesIssueStrucDTO implements Serializable {

	private static final long serialVersionUID = -8848174089315020130L;

	@NotNull
	private String saleStartTime;
	@NotNull
    private Integer salePeriodTime;
	@NotNull
    private Integer scheduleStopTime;
	@NotNull
    private String firstAwardTime;
	@NotNull
    private String lastAwardTime;
	public String getSaleStartTime() {
		return saleStartTime;
	}
	public void setSaleStartTime(String saleStartTime) {
		this.saleStartTime = saleStartTime;
	}
	public Integer getSalePeriodTime() {
		return salePeriodTime;
	}
	public void setSalePeriodTime(Integer salePeriodTime) {
		this.salePeriodTime = salePeriodTime;
	}
	public Integer getScheduleStopTime() {
		return scheduleStopTime;
	}
	public void setScheduleStopTime(Integer scheduleStopTime) {
		this.scheduleStopTime = scheduleStopTime;
	}
	public String getFirstAwardTime() {
		return firstAwardTime;
	}
	public void setFirstAwardTime(String firstAwardTime) {
		this.firstAwardTime = firstAwardTime;
	}
	public String getLastAwardTime() {
		return lastAwardTime;
	}
	public void setLastAwardTime(String lastAwardTime) {
		this.lastAwardTime = lastAwardTime;
	}
	
	
	
}
