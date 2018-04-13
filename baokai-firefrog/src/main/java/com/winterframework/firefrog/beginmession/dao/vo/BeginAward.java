package com.winterframework.firefrog.beginmession.dao.vo;


import java.util.Date;

import com.winterframework.orm.dal.ibatis3.BaseEntity;


public class BeginAward extends BaseEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8097469654487687465L;

	private Long userId;

	private Long awardType1;

	private Long awardType2;

	private Long missionId;

	private Date createTime;
	
	private Date awardTime;

	private Long status;
	
	private String cancelReason;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getAwardType1() {
		return awardType1;
	}

	public void setAwardType1(Long awardType1) {
		this.awardType1 = awardType1;
	}

	public Long getAwardType2() {
		return awardType2;
	}

	public void setAwardType2(Long awardType2) {
		this.awardType2 = awardType2;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Long getStatus() {
		return status;
	}

	public void setStatus(Long status) {
		this.status = status;
	}

	public Long getMissionId() {
		return missionId;
	}

	public void setMissionId(Long missionId) {
		this.missionId = missionId;
	}

	public Date getAwardTime() {
		return awardTime;
	}

	public void setAwardTime(Date awardTime) {
		this.awardTime = awardTime;
	}

	public String getCancelReason() {
		return cancelReason;
	}

	public void setCancelReason(String cancelReason) {
		this.cancelReason = cancelReason;
	}

}
