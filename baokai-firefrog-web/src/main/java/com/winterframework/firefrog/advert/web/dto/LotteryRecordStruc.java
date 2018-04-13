package com.winterframework.firefrog.advert.web.dto;

import java.io.Serializable;

public class LotteryRecordStruc implements Serializable{

	private static final long serialVersionUID = 7565663294657611576L;
    
	private String account;
	
	private Long totalLotteryCount;//总抽奖次数
	
	private Long unUseCount; //未使用次数
	
	private Long useCount;//已抽次数
	
	private Long awardCount;//中奖次数

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public Long getTotalLotteryCount() {
		return totalLotteryCount;
	}

	public void setTotalLotteryCount(Long totalLotteryCount) {
		this.totalLotteryCount = totalLotteryCount;
	}

	public Long getUnUseCount() {
		return unUseCount;
	}

	public void setUnUseCount(Long unUseCount) {
		this.unUseCount = unUseCount;
	}

	public Long getUseCount() {
		return useCount;
	}

	public void setUseCount(Long useCount) {
		this.useCount = useCount;
	}

	public Long getAwardCount() {
		return awardCount;
	}

	public void setAwardCount(Long awardCount) {
		this.awardCount = awardCount;
	}
}
