package com.winterframework.firefrog.beginmession.dao.vo;

import java.util.Date;

import com.winterframework.orm.dal.ibatis3.BaseEntity;

/**
 * 
 * @author Ami.Tsai
 *
 */
public class BeginAwardLottery extends BaseEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = -172619704928636599L;

	private Long userId;

	private Long lotteryType1;

	private Long lotteryType2;

	private Long missionId;

	private Date createTime;

	private Long status;

	private Date awardTime;
	
	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getLotteryType1() {
		return lotteryType1;
	}

	public void setLotteryType1(Long lotteryType1) {
		this.lotteryType1 = lotteryType1;
	}

	public Long getLotteryType2() {
		return lotteryType2;
	}

	public void setLotteryType2(Long lotteryType2) {
		this.lotteryType2 = lotteryType2;
	}

	public Long getMissionId() {
		return missionId;
	}

	public void setMissionId(Long missionId) {
		this.missionId = missionId;
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

	public Date getAwardTime() {
		return awardTime;
	}

	public void setAwardTime(Date awardTime) {
		this.awardTime = awardTime;
	}

}
