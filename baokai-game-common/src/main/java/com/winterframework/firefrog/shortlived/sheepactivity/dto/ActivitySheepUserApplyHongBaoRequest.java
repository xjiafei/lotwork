package com.winterframework.firefrog.shortlived.sheepactivity.dto;

import java.util.Date;

public class ActivitySheepUserApplyHongBaoRequest {
	private Long userId;
	
	private Long index;
	
	private Long award;
	
	private Date applyDate;
	
	private Date deadDate;
	
	private Long targetAmount;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getIndex() {
		return index;
	}

	public void setIndex(Long index) {
		this.index = index;
	}

	public Long getAward() {
		return award;
	}

	public void setAward(Long award) {
		this.award = award;
	}

	public Date getApplyDate() {
		return applyDate;
	}

	public void setApplyDate(Date applyDate) {
		this.applyDate = applyDate;
	}

	public Long getTargetAmount() {
		return targetAmount;
	}

	public void setTargetAmount(Long targetAmount) {
		this.targetAmount = targetAmount;
	}

	public Date getDeadDate() {
		return deadDate;
	}

	public void setDeadDate(Date deadDate) {
		this.deadDate = deadDate;
	}
}
