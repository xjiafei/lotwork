package com.winterframework.firefrog.game.web.dto;

import java.io.Serializable;

public class GameSeriesConfigDTO implements Serializable {


	private static final long serialVersionUID = -3521998394384593654L;

	private Long lotteryid;
	/**撤单起始金额*/
	private Long backoutStartFee;
	/**撤单手续费比率*/
	private Long BackoutRatio;
	/**单人单期最大中奖金额*/
	private Long orderwarnMaxwins;
	/**单人单期最大中投比*/
	private Long orderwarnWinsRatop;
	/**单人最大连续中奖次数*/
	private Long orderwarnContinuousWins;
	/**单注最大中奖金额*/
	private Long orderwarnMaxslipWins;
	/**单注最大中投比*/
	private Long orderwarnSlipWinsratio;
	/**超过理论开奖时间,官方未开奖*/
	private Long issuewarnNotOpenaward;
	/***早于理论开奖时间,官方提前开奖*/
	private Long issuewarnAheadOpenaward;
	/**超过理论开奖时间,官方延迟开奖*/
	private Long issuewarnDelayOpenaward;
	/**开奖后单笔撤销时间*/
	private Long issuewarnBackoutTime;
	/**开奖后异常处理时间*/
	private Long issuewarnExceptionTime;
	private Integer status;
	private String email;
	/**截止销售前用户撤销时间*/
	private Long issuewarnUserBackoutTime;
	
	public Long getLotteryid() {
		return lotteryid;
	}
	public void setLotteryid(Long lotteryid) {
		this.lotteryid = lotteryid;
	}
	public Long getBackoutStartFee() {
		return backoutStartFee;
	}
	public void setBackoutStartFee(Long backoutStartFee) {
		this.backoutStartFee = backoutStartFee;
	}
	public Long getBackoutRatio() {
		return BackoutRatio;
	}
	public void setBackoutRatio(Long backoutRatio) {
		BackoutRatio = backoutRatio;
	}
	public Long getOrderwarnMaxwins() {
		return orderwarnMaxwins;
	}
	public void setOrderwarnMaxwins(Long orderwarnMaxwins) {
		this.orderwarnMaxwins = orderwarnMaxwins;
	}
	public Long getOrderwarnWinsRatop() {
		return orderwarnWinsRatop;
	}
	public void setOrderwarnWinsRatop(Long orderwarnWinsRatop) {
		this.orderwarnWinsRatop = orderwarnWinsRatop;
	}
	public Long getOrderwarnContinuousWins() {
		return orderwarnContinuousWins;
	}
	public void setOrderwarnContinuousWins(Long orderwarnContinuousWins) {
		this.orderwarnContinuousWins = orderwarnContinuousWins;
	}
	public Long getOrderwarnSlipWinsratio() {
		return orderwarnSlipWinsratio;
	}
	public void setOrderwarnSlipWinsratio(Long orderwarnSlipWinsratio) {
		this.orderwarnSlipWinsratio = orderwarnSlipWinsratio;
	}
	public Long getIssuewarnNotOpenaward() {
		return issuewarnNotOpenaward;
	}
	public void setIssuewarnNotOpenaward(Long issuewarnNotOpenaward) {
		this.issuewarnNotOpenaward = issuewarnNotOpenaward;
	}
	public Long getIssuewarnAheadOpenaward() {
		return issuewarnAheadOpenaward;
	}
	public void setIssuewarnAheadOpenaward(Long issuewarnAheadOpenaward) {
		this.issuewarnAheadOpenaward = issuewarnAheadOpenaward;
	}
	public Long getIssuewarnDelayOpenaward() {
		return issuewarnDelayOpenaward;
	}
	public void setIssuewarnDelayOpenaward(Long issuewarnDelayOpenaward) {
		this.issuewarnDelayOpenaward = issuewarnDelayOpenaward;
	}
	public Long getIssuewarnBackoutTime() {
		return issuewarnBackoutTime;
	}
	public void setIssuewarnBackoutTime(Long issuewarnBackoutTime) {
		this.issuewarnBackoutTime = issuewarnBackoutTime;
	}
	public Long getIssuewarnExceptionTime() {
		return issuewarnExceptionTime;
	}
	public void setIssuewarnExceptionTime(Long issuewarnExceptionTime) {
		this.issuewarnExceptionTime = issuewarnExceptionTime;
	}
	public Long getOrderwarnMaxslipWins() {
		return orderwarnMaxslipWins;
	}
	public void setOrderwarnMaxslipWins(Long orderwarnMaxslipWins) {
		this.orderwarnMaxslipWins = orderwarnMaxslipWins;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Long getIssuewarnUserBackoutTime() {
		return issuewarnUserBackoutTime;
	}
	public void setIssuewarnUserBackoutTime(Long issuewarnUserBackoutTime) {
		this.issuewarnUserBackoutTime = issuewarnUserBackoutTime;
	}
}
