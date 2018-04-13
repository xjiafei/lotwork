package com.winterframework.firefrog.game.entity;

import java.util.Date;

/**
 * @author cms group
 * @version 1.0
 * @since 1.0
 */

public class GameIssueTemplateEntity {

	private Long id;
	private GameIssueRuleEntity rule;
	private Date saleStartTime;
	private Long salePeriodTime;
	private Long scheduleStopTime;
	private Date firstAwardTime;
	private Date lastAwardTime;
	private Date createTime;
	private Date updateTime;
	private Integer saleTimeSn;

	public GameIssueTemplateEntity() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public GameIssueRuleEntity getRule() {
		return rule;
	}

	public void setRule(GameIssueRuleEntity rule) {
		this.rule = rule;
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
