package com.winterframework.firefrog.shortlived.gamemission.dao.entity;

import java.util.Date;

public class GameMissionUserData {

	private Long targetUserId;

	private Long targetMissionId;

	private Integer targetDiamond;

	private Date targetStartTime;

	private Date targetEndTime;
	
	private Double targetNeedPay;

	public Long getTargetUserId() {
		return targetUserId;
	}

	public void setTargetUserId(Long targetUserId) {
		this.targetUserId = targetUserId;
	}

	public Long getTargetMissionId() {
		return targetMissionId;
	}

	public void setTargetMissionId(Long targetMissionId) {
		this.targetMissionId = targetMissionId;
	}

	public Integer getTargetDiamond() {
		return targetDiamond;
	}

	public void setTargetDiamonds(Integer targetDiamond) {
		this.targetDiamond = targetDiamond;
	}

	public Date getTargetStartTime() {
		return targetStartTime;
	}

	public void setTargetStartTime(Date targetStartTime) {
		this.targetStartTime = targetStartTime;
	}

	public Date getTargetEndTime() {
		return targetEndTime;
	}

	public void setTargetEndTime(Date targetEndTime) {
		this.targetEndTime = targetEndTime;
	}

	public Double getTargetNeedPay() {
		return targetNeedPay;
	}

	public void setTargetNeedPay(Double targetNeedPay) {
		this.targetNeedPay = targetNeedPay;
	}
}
