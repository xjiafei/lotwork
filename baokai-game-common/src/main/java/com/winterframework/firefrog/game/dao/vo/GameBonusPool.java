 /**
 * Copyright (c) 2005-2012 winterframework.com
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
 package com.winterframework.firefrog.game.dao.vo;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import com.winterframework.orm.dal.ibatis3.BaseEntity;





/**
 * @author cms group
 * @version 1.0
 * @since 1.0
 */


public class GameBonusPool extends BaseEntity {
	
	//alias
	public static final String TABLE_ALIAS = "GameBonusPool";
	public static final String ALIAS_LOTTERYID = "lotteryid";
	public static final String ALIAS_ACTUAL_BONUS = "actualBonus";
	public static final String ALIAS_ACTUAL_BONUS_PROCESS = "actualBonusProcess";
	public static final String ALIAS_CHANGE_REASON = "changeReason";
	public static final String ALIAS_MINIMUM_BONUS = "minimumBonus";
	public static final String ALIAS_MINIMUM_BONUS_PROCESS = "minimumBonusProcess";
	public static final String ALIAS_DISTRIBUTE_1 = "distribute1";
	public static final String ALIAS_DISTRIBUTE_2 = "distribute2";
	public static final String ALIAS_DISTRIBUTE_1_PROCESS = "distribute1process";
	public static final String ALIAS_DISTRIBUTE_2_PROCESS = "distribute2process";
	public static final String ALIAS_STATUS = "status";
	
	//date formats
	
	//columns START
	private Long lotteryid;
	private Long actualBonus;
	private Long actualBonusProcess;
	private String changeReason;
	private Long minimumBonus;
	private Long minimumBonusProcess;
	private Long distribute1;
	private Long distribute2;
	private Long distribute1process;
	private Long distribute2process;
	private Long status;
	//columns END

	public GameBonusPool(){
	}

	public GameBonusPool(
		Long id
	){
		this.id = id;
	}

	public void setLotteryid(Long value) {
		this.lotteryid = value;
	}
	
	public Long getLotteryid() {
		return this.lotteryid;
	}
	public void setActualBonus(Long value) {
		this.actualBonus = value;
	}
	
	public Long getActualBonus() {
		return this.actualBonus;
	}
	public void setActualBonusProcess(Long value) {
		this.actualBonusProcess = value;
	}
	
	public Long getActualBonusProcess() {
		return this.actualBonusProcess;
	}
	public void setChangeReason(String value) {
		this.changeReason = value;
	}
	
	public String getChangeReason() {
		return this.changeReason;
	}
	public void setMinimumBonus(Long value) {
		this.minimumBonus = value;
	}
	
	public Long getMinimumBonus() {
		return this.minimumBonus;
	}
	public void setMinimumBonusProcess(Long value) {
		this.minimumBonusProcess = value;
	}
	
	public Long getMinimumBonusProcess() {
		return this.minimumBonusProcess;
	}
	public void setDistribute1(Long value) {
		this.distribute1 = value;
	}
	
	public Long getDistribute1() {
		return this.distribute1;
	}
	public void setDistribute2(Long value) {
		this.distribute2 = value;
	}
	
	public Long getDistribute2() {
		return this.distribute2;
	}
	public void setDistribute1process(Long value) {
		this.distribute1process = value;
	}
	
	public Long getDistribute1process() {
		return this.distribute1process;
	}
	public void setDistribute2process(Long value) {
		this.distribute2process = value;
	}
	
	public Long getDistribute2process() {
		return this.distribute2process;
	}
	public void setStatus(Long value) {
		this.status = value;
	}
	
	public Long getStatus() {
		return this.status;
	}
    @Override
	public String toString() {
		return new ToStringBuilder(this)
		.append("Id",getId())		
		.append("Lotteryid",getLotteryid())		
		.append("ActualBonus",getActualBonus())		
		.append("ActualBonusProcess",getActualBonusProcess())		
		.append("ChangeReason",getChangeReason())		
		.append("MinimumBonus",getMinimumBonus())		
		.append("MinimumBonusProcess",getMinimumBonusProcess())		
		.append("Distribute1",getDistribute1())		
		.append("Distribute2",getDistribute2())		
		.append("Distribute1process",getDistribute1process())		
		.append("Distribute2process",getDistribute2process())		
		.append("Status",getStatus())		
			.toString();
	}
    @Override
	public int hashCode() {
		return new HashCodeBuilder()
		.append(getId())
		.append(getLotteryid())
		.append(getActualBonus())
		.append(getActualBonusProcess())
		.append(getChangeReason())
		.append(getMinimumBonus())
		.append(getMinimumBonusProcess())
		.append(getDistribute1())
		.append(getDistribute2())
		.append(getDistribute1process())
		.append(getDistribute2process())
		.append(getStatus())
			.toHashCode();
	}
    @Override
	public boolean equals(Object obj) {
		if(obj instanceof GameBonusPool == false) return false;
		if(this == obj) return true;
		GameBonusPool other = (GameBonusPool)obj;
		return new EqualsBuilder()
		.append(getId(),other.getId())

		.append(getLotteryid(),other.getLotteryid())

		.append(getActualBonus(),other.getActualBonus())

		.append(getActualBonusProcess(),other.getActualBonusProcess())

		.append(getChangeReason(),other.getChangeReason())

		.append(getMinimumBonus(),other.getMinimumBonus())

		.append(getMinimumBonusProcess(),other.getMinimumBonusProcess())

		.append(getDistribute1(),other.getDistribute1())

		.append(getDistribute2(),other.getDistribute2())

		.append(getDistribute1process(),other.getDistribute1process())

		.append(getDistribute2process(),other.getDistribute2process())

		.append(getStatus(),other.getStatus())

			.isEquals();
	}
}

