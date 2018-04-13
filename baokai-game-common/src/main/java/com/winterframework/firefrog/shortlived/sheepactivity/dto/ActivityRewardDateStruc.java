/**   
* @Title: AssistBmBonusStruc.java 
* @Package com.winterframework.firefrog.game.web.dto 
* @Description: TODO(用一句话描述该文件做什么) 
* @author 你的名字   
* @date 2014-4-14 下午2:07:34 
* @version V1.0   
*/
package com.winterframework.firefrog.shortlived.sheepactivity.dto;

import java.io.Serializable;
import java.util.List;

/** 
* @ClassName: ActivitySheepYearRewardStruc 
* @Description: 羊年活动reward结构
* @author david
*  
*/
public class ActivityRewardDateStruc implements Serializable{

	private static final long serialVersionUID = -1543757401485976506L;

	private List<ActivitySheepYearHongBaoStep> rewards;
	
	private Boolean isVip;
	
	private Long minNum;
	
	private Long maxNum;
	
	private Integer index;//红包索引
	
	private Long total; //红包数量
	
	private Long rewardsNum;//红包金额
	
	private Long expected;//目标投注
	
	private Long deadline;//截止时间
	
	private Long nowTime;//当前时间
	
	private Long lastBet;//最后投注金额


	public List<ActivitySheepYearHongBaoStep> getRewards() {
		return rewards;
	}

	public void setRewards(List<ActivitySheepYearHongBaoStep> rewards) {
		this.rewards = rewards;
	}

	public Boolean getIsVip() {
		return isVip;
	}

	public void setIsVip(Boolean isVip) {
		this.isVip = isVip;
	}

	public Long getMinNum() {
		return minNum;
	}

	public void setMinNum(Long minNum) {
		this.minNum = minNum;
	}

	public Long getMaxNum() {
		return maxNum;
	}

	public void setMaxNum(Long maxNum) {
		this.maxNum = maxNum;
	}

	public Integer getIndex() {
		return index;
	}

	public void setIndex(Integer index) {
		this.index = index;
	}

	public Long getTotal() {
		return total;
	}

	public void setTotal(Long total) {
		this.total = total;
	}

	public Long getRewardsNum() {
		return rewardsNum;
	}

	public void setRewardsNum(Long rewardsNum) {
		this.rewardsNum = rewardsNum;
	}

	public Long getExpected() {
		return expected;
	}

	public void setExpected(Long expected) {
		this.expected = expected;
	}

	public Long getDeadline() {
		return deadline;
	}

	public void setDeadline(Long deadline) {
		this.deadline = deadline;
	}

	public Long getNowTime() {
		return nowTime;
	}

	public void setNowTime(Long nowTime) {
		this.nowTime = nowTime;
	}

	public Long getLastBet() {
		return lastBet;
	}

	public void setLastBet(Long lastBet) {
		this.lastBet = lastBet;
	}
}
