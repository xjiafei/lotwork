package com.winterframework.firefrog.game.web.dto;

import java.io.Serializable;

import javax.validation.constraints.NotNull;
/**
 * 
* @ClassName: EditSeriesConfigRequest 
* @Description: 修改运营参数（审核模块）
* @author Alan
* @date 2013-9-17 下午5:24:49 
*
 */
public class EditSeriesConfigRiskRequest implements Serializable{
	
	private static final long serialVersionUID = 7477128355644615871L;
	
	private Long lotteryId;
	
	//@NotNull
	private Long orderwarnMaxwins;
	/**单人单期最大中投比*/
	//@NotNull
	private Long orderwarnWinsRatio;
	/**单人最大连续中奖次数*/
	//@NotNull
	private Long orderwarnContinuousWins;
	/**单注最大中奖金额*/
	//@NotNull
	private Long orderwarnMaxslipWins;
	/**单注最大中投比*/
	//@NotNull
	private Long orderwarnSlipWinsRatio;
	
	public EditSeriesConfigRiskRequest(){
		
	}

	public Long getOrderwarnMaxwins() {
		return orderwarnMaxwins;
	}

	public void setOrderwarnMaxwins(Long orderwarnMaxwins) {
		this.orderwarnMaxwins = orderwarnMaxwins;
	}

	public Long getOrderwarnWinsRatio() {
		return orderwarnWinsRatio;
	}

	public void setOrderwarnWinsRatio(Long orderwarnWinsRatio) {
		this.orderwarnWinsRatio = orderwarnWinsRatio;
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

	public Long getOrderwarnSlipWinsRatio() {
		return orderwarnSlipWinsRatio;
	}

	public void setOrderwarnSlipWinsRatio(Long orderwarnSlipWinsRatio) {
		this.orderwarnSlipWinsRatio = orderwarnSlipWinsRatio;
	}

	public Long getLotteryId() {
		return lotteryId;
	}

	public void setLotteryId(Long lotteryId) {
		this.lotteryId = lotteryId;
	}


}
