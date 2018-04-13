package com.winterframework.firefrog.beginmession.dao.vo;

import java.util.Date;

import com.winterframework.orm.dal.ibatis3.BaseEntity;

public class BeginAwardLog extends BaseEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 961215662035400475L;

	private Long userId;

	private Long awardId;

	private Long awardLotteryId;
	
	private Long prize;

	private Date awardTime;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getAwardId() {
		return awardId;
	}

	public void setAwardId(Long awardId) {
		this.awardId = awardId;
	}

	public Long getPrize() {
		return prize;
	}

	public void setPrize(Long prize) {
		this.prize = prize;
	}

	public Date getAwardTime() {
		return awardTime;
	}

	public void setAwardTime(Date awardTime) {
		this.awardTime = awardTime;
	}

	public Long getAwardLotteryId() {
		return awardLotteryId;
	}

	public void setAwardLotteryId(Long awardLotteryId) {
		this.awardLotteryId = awardLotteryId;
	}

}
