 package com.winterframework.firefrog.game.entity;

import java.util.*;

import com.winterframework.orm.dal.ibatis3.BaseEntity;

public class GameRiskConfig extends BaseEntity {
	
	private static final long serialVersionUID = 7949312016661523161L;
	
	//alias
	public static final String TABLE_ALIAS = "GameRiskConfig";
	public static final String ALIAS_ORDERWARN_MAXWINS = "单人单期最大中奖金额";
	public static final String ALIAS_ORDERWARN_WINS_RATIO = "单人单期最大中投比";
	public static final String ALIAS_ORDERWARN_CONTINUOUS_WINS = "单人最大连续中奖次数";
	public static final String ALIAS_ORDERWARN_MAXSLIP_WINS = "单注最大中奖金额";
	public static final String ALIAS_ORDERWARN_SLIP_WINSRATIO = "单注最大中投比";
	public static final String ALIAS_CREATE_TIME = "创建时间";
	public static final String ALIAS_UPDATE_TIME = "更新时间";
	public static final String ALIAS_LOTTERY_ID = "更新时间";
	//date formats
	
	//columns START
	private Long lotteryId;
	private Long orderwarnMaxwins;
	private Long orderwarnWinsRatio;
	private Long orderwarnContinuousWins;
	private Long orderwarnMaxslipWins;
	private Long orderwarnSlipWinsratio;
	private Date createTime;
	private Date updateTime;
	//columns END

	public GameRiskConfig(){
	}

	public void setOrderwarnMaxwins(Long value) {
		this.orderwarnMaxwins = value;
	}
	
	public Long getOrderwarnMaxwins() {
		return this.orderwarnMaxwins;
	}
	public void setOrderwarnWinsRatio(Long value) {
		this.orderwarnWinsRatio = value;
	}
	
	public Long getOrderwarnWinsRatio() {
		return this.orderwarnWinsRatio;
	}
	public void setOrderwarnContinuousWins(Long value) {
		this.orderwarnContinuousWins = value;
	}
	
	public Long getOrderwarnContinuousWins() {
		return this.orderwarnContinuousWins;
	}
	public void setOrderwarnMaxslipWins(Long value) {
		this.orderwarnMaxslipWins = value;
	}
	
	public Long getOrderwarnMaxslipWins() {
		return this.orderwarnMaxslipWins;
	}
	public void setOrderwarnSlipWinsratio(Long value) {
		this.orderwarnSlipWinsratio = value;
	}
	
	public Long getOrderwarnSlipWinsratio() {
		return this.orderwarnSlipWinsratio;
	}
	public void setCreateTime(Date value) {
		this.createTime = value;
	}
	
	public Object getCreateTime() {
		return this.createTime;
	}
	public void setUpdateTime(Date value) {
		this.updateTime = value;
	}
	
	public Object getUpdateTime() {
		return this.updateTime;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((createTime == null) ? 0 : createTime.hashCode());
		result = prime * result + ((orderwarnContinuousWins == null) ? 0 : orderwarnContinuousWins.hashCode());
		result = prime * result + ((orderwarnMaxslipWins == null) ? 0 : orderwarnMaxslipWins.hashCode());
		result = prime * result + ((orderwarnMaxwins == null) ? 0 : orderwarnMaxwins.hashCode());
		result = prime * result + ((orderwarnSlipWinsratio == null) ? 0 : orderwarnSlipWinsratio.hashCode());
		result = prime * result + ((orderwarnWinsRatio == null) ? 0 : orderwarnWinsRatio.hashCode());
		result = prime * result + ((updateTime == null) ? 0 : updateTime.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		GameRiskConfig other = (GameRiskConfig) obj;
		if (createTime == null) {
			if (other.createTime != null)
				return false;
		} else if (!createTime.equals(other.createTime))
			return false;
		if (orderwarnContinuousWins == null) {
			if (other.orderwarnContinuousWins != null)
				return false;
		} else if (!orderwarnContinuousWins.equals(other.orderwarnContinuousWins))
			return false;
		if (orderwarnMaxslipWins == null) {
			if (other.orderwarnMaxslipWins != null)
				return false;
		} else if (!orderwarnMaxslipWins.equals(other.orderwarnMaxslipWins))
			return false;
		if (orderwarnMaxwins == null) {
			if (other.orderwarnMaxwins != null)
				return false;
		} else if (!orderwarnMaxwins.equals(other.orderwarnMaxwins))
			return false;
		if (orderwarnSlipWinsratio == null) {
			if (other.orderwarnSlipWinsratio != null)
				return false;
		} else if (!orderwarnSlipWinsratio.equals(other.orderwarnSlipWinsratio))
			return false;
		if (orderwarnWinsRatio == null) {
			if (other.orderwarnWinsRatio != null)
				return false;
		} else if (!orderwarnWinsRatio.equals(other.orderwarnWinsRatio))
			return false;
		if (updateTime == null) {
			if (other.updateTime != null)
				return false;
		} else if (!updateTime.equals(other.updateTime))
			return false;
		return true;
	}

	public Long getLotteryId() {
		return lotteryId;
	}

	public void setLotteryId(Long lotteryId) {
		this.lotteryId = lotteryId;
	}
	
	
}

