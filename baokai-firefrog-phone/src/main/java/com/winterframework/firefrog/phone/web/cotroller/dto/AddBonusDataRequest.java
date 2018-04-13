package com.winterframework.firefrog.phone.web.cotroller.dto;

import java.io.Serializable;

public class AddBonusDataRequest implements Serializable {

	private static final long serialVersionUID = 5182886917886577792L;

	private Integer awardGroupId;//	md5(资金密码)
	
	private Integer lotteryId;

	public Integer getAwardGroupId() {
		return awardGroupId;
	}

	public void setAwardGroupId(Integer awardGroupId) {
		this.awardGroupId = awardGroupId;
	}

	public Integer getLotteryId() {
		return lotteryId;
	}

	public void setLotteryId(Integer lotteryId) {
		this.lotteryId = lotteryId;
	}
	
}
