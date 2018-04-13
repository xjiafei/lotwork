package com.winterframework.firefrog.game.entity;

import java.io.Serializable;
import java.util.Date;

/** 
* @ClassName: GameSeriesConfigEntity 
* @Description: 游戏奖期规则配置（审核模块）entity
* @author Alan
* @date 2013-9-17 下午5:38:07 
*  
*/
public class GameSeriesConfigRiskEntity implements Serializable {

	private static final long serialVersionUID = -4797922610680651282L;

	private Long id;
	private Lottery lottery;
	private Long orderwarnMaxwins;
	private Long orderwarnWinsRatio;
	private Long orderwarnContinuousWins;
	private Long orderwarnMaxslipWins;
	private Long orderwarnSlipWinsratio;
	private Date createTime;
	private Date updateTime;
	
	public GameSeriesConfigRiskEntity(){
		
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Lottery getLottery() {
		return lottery;
	}

	public void setLottery(Lottery lottery) {
		this.lottery = lottery;
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

	public Long getOrderwarnSlipWinsratio() {
		return orderwarnSlipWinsratio;
	}

	public void setOrderwarnSlipWinsratio(Long orderwarnSlipWinsratio) {
		this.orderwarnSlipWinsratio = orderwarnSlipWinsratio;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

}
