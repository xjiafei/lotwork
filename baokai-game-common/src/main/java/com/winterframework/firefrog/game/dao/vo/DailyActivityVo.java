package com.winterframework.firefrog.game.dao.vo;

import com.winterframework.orm.dal.ibatis3.BaseEntity;

/** 
* @ClassName DailyActivityVo 
* @Description 日常活动判断数据结构，统计出活动期间内哪些天有可以领奖的投注，以及是否已领奖 
* @author  david
* @date
*  
*/
public class DailyActivityVo extends BaseEntity{

	private static final long serialVersionUID = 2280832599839842164L;

	private Integer betCount;//某天投注记录数
	
	private String betDate;//日期 格式yyyy-MM-dd
	
	private Integer rewardLogid;//领奖id

	public Integer getBetCount() {
		return betCount;
	}

	public void setBetCount(Integer betCount) {
		this.betCount = betCount;
	}

	public String getBetDate() {
		return betDate;
	}

	public void setBetDate(String betDate) {
		this.betDate = betDate;
	}

	public Integer getRewardLogid() {
		return rewardLogid;
	}

	public void setRewardLogid(Integer rewardLogid) {
		this.rewardLogid = rewardLogid;
	}
	
}
