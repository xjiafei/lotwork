package com.winterframework.firefrog.game.web.dto;

import java.io.Serializable;

/**
 * 
* @ClassName: QuerySeriesConfigRequest 
* @Description: 查询运营参数（审核模块）响应
* @author Alan
* @date 2013-9-17 下午5:16:31 
*
 */
public class QuerySeriesConfigRiskResponse implements Serializable{

	private static final long serialVersionUID = -6865547369203751693L;
	
	/**单人单期最大中奖金额*/
	private Long orderwarnMaxwins;
	/**单人单期最大中投比*/
	private Long orderwarnWinsRatio;
	/**单人最大连续中奖次数*/
	private Long orderwarnContinuousWins;
	/**单注最大中投比*/
	private Long orderwarnSlipWinsRatio;
	/**单注最大中奖金额*/
	private Long orderwarnMaxslipWins;
	
	public QuerySeriesConfigRiskResponse(){
		
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

	public Long getOrderwarnSlipWinsRatio() {
		return orderwarnSlipWinsRatio;
	}

	public void setOrderwarnSlipWinsRatio(Long orderwarnSlipWinsRatio) {
		this.orderwarnSlipWinsRatio = orderwarnSlipWinsRatio;
	}

	public Long getOrderwarnMaxslipWins() {
		return orderwarnMaxslipWins;
	}

	public void setOrderwarnMaxslipWins(Long orderwarnMaxslipWins) {
		this.orderwarnMaxslipWins = orderwarnMaxslipWins;
	}

	
}
