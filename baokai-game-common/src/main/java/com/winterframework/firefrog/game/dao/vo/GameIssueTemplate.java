package com.winterframework.firefrog.game.dao.vo;

import java.util.Date;

import com.winterframework.orm.dal.ibatis3.BaseEntity;

/**
 * @author cms group
 * @version 1.0
 * @since 1.0
 */

public class GameIssueTemplate extends BaseEntity {

	private static final long serialVersionUID = 3119482279027043810L;
	private Long ruleid;
	private Date saleStartTime;
	private Long salePeriodTime;
	private Long scheduleStopTime;
	private Date firstAwardTime;
	private Date lastAwardTime;
	private Date createTime;
	private Date updateTime;
	private Integer saleTimeSn;

	public GameIssueTemplate() {
	}

	public GameIssueTemplate(Long id) {
		this.id = id;
	}

	public Long getRuleid() {
		return ruleid;
	}

	public void setRuleid(Long ruleid) {
		this.ruleid = ruleid;
	}

	public Date getSaleStartTime() {
		return saleStartTime;
	}

	public void setSaleStartTime(Date saleStartTime) {
		this.saleStartTime = saleStartTime;
	}

	public Long getSalePeriodTime() {
		return salePeriodTime;
	}

	public void setSalePeriodTime(Long salePeriodTime) {
		this.salePeriodTime = salePeriodTime;
	}

	public Long getScheduleStopTime() {
		return scheduleStopTime;
	}

	public void setScheduleStopTime(Long scheduleStopTime) {
		this.scheduleStopTime = scheduleStopTime;
	}

	public Date getFirstAwardTime() {
		return firstAwardTime;
	}

	public void setFirstAwardTime(Date firstAwardTime) {
		this.firstAwardTime = firstAwardTime;
	}

	public Date getLastAwardTime() {
		return lastAwardTime;
	}

	public void setLastAwardTime(Date lastAwardTime) {
		this.lastAwardTime = lastAwardTime;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Integer getSaleTimeSn() {
		return saleTimeSn;
	}

	public void setSaleTimeSn(Integer saleTimeSn) {
		this.saleTimeSn = saleTimeSn;
	}

}
