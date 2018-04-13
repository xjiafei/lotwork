package com.winterframework.firefrog.game.web.dto;

import java.io.Serializable;

public class GameSeriesConfigRiskDTO implements Serializable {

	private static final long serialVersionUID = -1007693491364370184L;
	
	private Long lotteryid;
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
	public Long getLotteryid() {
		return lotteryid;
	}
	public void setLotteryid(Long lotteryid) {
		this.lotteryid = lotteryid;
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
	public Long getOrderwarnMaxslipWins() {
		return orderwarnMaxslipWins;
	}
	public void setOrderwarnMaxslipWins(Long orderwarnMaxslipWins) {
		this.orderwarnMaxslipWins = orderwarnMaxslipWins;
	}
	public Long getOrderwarnSlipWinsratio() {
		return orderwarnSlipWinsratio;
	}
	public void setOrderwarnSlipWinsratio(Long orderwarnSlipWinsratio) {
		this.orderwarnSlipWinsratio = orderwarnSlipWinsratio;
	}
	
}
