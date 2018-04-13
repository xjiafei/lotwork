package com.winterframework.firefrog.game.web.dto;

import java.io.Serializable;
import java.util.List;

import javax.validation.constraints.NotNull;

public class GamePlanRequest implements Serializable {

	private static final long serialVersionUID = 6464314985005368296L;
	
	//当前奖期，卡点投注后台拿到的当前奖期会有误差，直接由web传递过来
	private Long currentIssueCode;

	@NotNull
	private Long lotteryid;
	@NotNull
	private Long startIssueCode;
	@NotNull
	private Long totalIssue;
	@NotNull
	private List<BetDetailStruc> betDetailsStruc;
	
	private List<GamePlanBetOriginDataStruc>  betOriginDataStruc;
	private Long userip;
	private Long serverip;
	@NotNull
	private Long saleTime;
	@NotNull
	private Long planAmount;
	@NotNull
	private Integer stopMode;
	@NotNull
	private Long stopParms;
	private String optionParms;
	//封锁变价时是否是第一次提交0：不是，1：是
	private Integer isFirstSubmit;
	
	private Integer channelId;
	private String channelVersion;
	
	//用户id
	private Long userId;
	private Long activityType = 0l;
	public Integer getChannelId() {
		return channelId;
	}

	public void setChannelId(Integer channelId) {
		this.channelId = channelId;
	}

	public String getChannelVersion() {
		return channelVersion;
	}

	public void setChannelVersion(String channelVersion) {
		this.channelVersion = channelVersion;
	}

	public GamePlanRequest() {

	}

	public Long getLotteryid() {
		return lotteryid;
	}

	public void setLotteryid(Long lotteryid) {
		this.lotteryid = lotteryid;
	}

	public List<BetDetailStruc> getBetDetailsStruc() {
		return betDetailsStruc;
	}

	public void setBetDetailsStruc(List<BetDetailStruc> betDetailsStruc) {
		this.betDetailsStruc = betDetailsStruc;
	}

	public Long getUserip() {
		return userip;
	}

	public void setUserip(Long userip) {
		this.userip = userip;
	}

	public Long getServerip() {
		return serverip;
	}

	public void setServerip(Long serverip) {
		this.serverip = serverip;
	}

	public Long getSaleTime() {
		return saleTime;
	}

	public void setSaleTime(Long saleTime) {
		this.saleTime = saleTime;
	}

	public Long getPlanAmount() {
		return planAmount;
	}

	public void setPlanAmount(Long planAmount) {
		this.planAmount = planAmount;
	}

	public Long getStartIssueCode() {
		return startIssueCode;
	}

	public void setStartIssueCode(Long startIssueCode) {
		this.startIssueCode = startIssueCode;
	}

	public Long getTotalIssue() {
		return totalIssue;
	}

	public void setTotalIssue(Long totalIssue) {
		this.totalIssue = totalIssue;
	}

	public Integer getStopMode() {
		return stopMode;
	}

	public void setStopMode(Integer stopMode) {
		this.stopMode = stopMode;
	}

	public Long getStopParms() {
		return stopParms;
	}

	public void setStopParms(Long stopParms) {
		this.stopParms = stopParms;
	}

	public String getOptionParms() {
		return optionParms;
	}

	public void setOptionParms(String optionParms) {
		this.optionParms = optionParms;
	}

	public List<GamePlanBetOriginDataStruc> getBetOriginDataStruc() {
		return betOriginDataStruc;
	}

	public void setBetOriginDataStruc(List<GamePlanBetOriginDataStruc> betOriginDataStruc) {
		this.betOriginDataStruc = betOriginDataStruc;
	}

	public Integer getIsFirstSubmit() {
		return isFirstSubmit;
	}

	public void setIsFirstSubmit(Integer isFirstSubmit) {
		this.isFirstSubmit = isFirstSubmit;
	}

	public Long getCurrentIssueCode() {
		return currentIssueCode;
	}

	public void setCurrentIssueCode(Long currentIssueCode) {
		this.currentIssueCode = currentIssueCode;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getActivityType() {
		return activityType;
	}

	public void setActivityType(Long activityType) {
		this.activityType = activityType;
	}
}
