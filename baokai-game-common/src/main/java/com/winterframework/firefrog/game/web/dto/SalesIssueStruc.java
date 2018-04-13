package com.winterframework.firefrog.game.web.dto;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

/** 
* @ClassName: SalesIssueStruc 
* @Description:销售奖期时间段基本结构
* @author david 
* @date 2013-8-21 下午5:42:26 
*  
*/
public class SalesIssueStruc implements Serializable {

	private static final long serialVersionUID = -8848174089315020130L;

	private Long saleStartTime;

    private Integer salePeriodTime;
	@NotNull
    private Integer scheduleStopTime;
	@NotNull
    private Long firstAwardTime;
	@NotNull
    private Long lastAwardTime;
	
	private Long id;
	private Integer saleTimeSn;
	
	public Long getSaleStartTime() {
		return saleStartTime;
	}
	public void setSaleStartTime(Long saleStartTime) {
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
	public Long getFirstAwardTime() {
		return firstAwardTime;
	}
	public void setFirstAwardTime(Long firstAwardTime) {
		this.firstAwardTime = firstAwardTime;
	}
	public Long getLastAwardTime() {
		return lastAwardTime;
	}
	public void setLastAwardTime(Long lastAwardTime) {
		this.lastAwardTime = lastAwardTime;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Integer getSaleTimeSn() {
		return saleTimeSn;
	}
	public void setSaleTimeSn(Integer saleTimeSn) {
		this.saleTimeSn = saleTimeSn;
	}
}
