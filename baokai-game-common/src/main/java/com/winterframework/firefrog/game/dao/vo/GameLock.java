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
 * 封鎖變價設定
 * @author floy
 * @version 1.0
 * @since 1.0
 */
public class GameLock extends BaseEntity {
	
	private static final long serialVersionUID = 1L;
	
	/**彩種ID*/
	private Long gameId;
	/**封鎖值；對應3D、P3、六合彩(特碼)*/
	private Long upVal;
	/**老的封鎖值*/
	private Long upValProcess;
	/**封鎖值2；對應P5、六合彩(正特碼一肖)*/
	private Long upVal2;
	/**老的封鎖值2*/
	private Long upValProcess2;
	/**狀態；1：待審核、2：審核通過、3：審核不通過、4：已發佈*/
	private Long status;
	/**紅球封鎖值；對應雙色球*/
	private Long redSlipVal;
	/**老的紅球封鎖值*/
	private Long redSlipValProcess;
	/**藍球封鎖值；對應雙色球*/
	private Long blueSlipVal;
	/**老的藍球封鎖值*/
	private Long blueSlipValProcess;
	/**封鎖值3；對應六合彩(其他玩法)*/
	private Long upVal3;
	/**老的封鎖值3*/
	private Long upValProcess3;

	public GameLock(){
	}

	public GameLock(Long id){
		this.id = id;
	}

	/**
	 * 設定彩種ID。
	 * @param value
	 */
	public void setGameId(Long value) {
		this.gameId = value;
	}
	
	/**
	 * 取得彩種ID。
	 * @return
	 */
	public Long getGameId() {
		return this.gameId;
	}
	
	/**
	 * 設定封鎖值；對應3D、P3、六合彩(特碼)。
	 * @param value
	 */
	public void setUpVal(Long value) {
		this.upVal = value;
	}
	
	/**
	 * 取得封鎖值；對應3D、P3、六合彩(特碼)。
	 * @return
	 */
	public Long getUpVal() {
		return this.upVal;
	}
	
	/**
	 * 設定老的封鎖值。
	 * @param value
	 */
	public void setUpValProcess(Long value) {
		this.upValProcess = value;
	}
	
	/**
	 * 取得老的封鎖值。
	 * @return
	 */
	public Long getUpValProcess() {
		return this.upValProcess;
	}
	
	/**
	 * 設定狀態。
	 * @param value 1：待審核、2：審核通過、3：審核不通過、4：已發佈
	 */
	public void setStatus(Long value) {
		this.status = value;
	}
	
	/**
	 * 取得狀態。
	 * @return 1：待審核、2：審核通過、3：審核不通過、4：已發佈
	 */
	public Long getStatus() {
		return this.status;
	}
	
	/**
	 * 取得封鎖值2；對應P5、六合彩(正特碼一肖)。
	 * @return
	 */
    public Long getUpVal2() {
		return upVal2;
	}

    /**
     * 設定封鎖值2；對應P5、六合彩(正特碼一肖)。
     * @param upVal2
     */
	public void setUpVal2(Long upVal2) {
		this.upVal2 = upVal2;
	}

	/**
	 * 取得老的封鎖值2。
	 * @return
	 */
	public Long getUpValProcess2() {
		return upValProcess2;
	}

	/**
	 * 設定老的封鎖值2。
	 * @param upValProcess2
	 */
	public void setUpValProcess2(Long upValProcess2) {
		this.upValProcess2 = upValProcess2;
	}

	/**
	 * 取得紅球封鎖值；對應雙色球，
	 * @return
	 */
	public Long getRedSlipVal() {
		return redSlipVal;
	}

	/**
	 * 設定紅球封鎖值；對應雙色球。
	 * @param redSlipVal
	 */
	public void setRedSlipVal(Long redSlipVal) {
		this.redSlipVal = redSlipVal;
	}

	/**
	 * 取得老的紅球封鎖值。
	 * @return
	 */
	public Long getRedSlipValProcess() {
		return redSlipValProcess;
	}

	/**
	 * 設定老的紅球封鎖值。
	 * @param redSlipValProcess
	 */
	public void setRedSlipValProcess(Long redSlipValProcess) {
		this.redSlipValProcess = redSlipValProcess;
	}

	/**
	 * 取得藍球封鎖值；對應雙色球。
	 * @return
	 */
	public Long getBlueSlipVal() {
		return blueSlipVal;
	}

	/**
	 * 設定藍球封鎖值；對應雙色球。
	 * @param blueSlipVal
	 */
	public void setBlueSlipVal(Long blueSlipVal) {
		this.blueSlipVal = blueSlipVal;
	}

	/**
	 * 取得老的藍球封鎖值。
	 * @return
	 */
	public Long getBlueSlipValProcess() {
		return blueSlipValProcess;
	}

	/**
	 * 設定老的藍球封鎖值。
	 * @param blueSlipValProcess
	 */
	public void setBlueSlipValProcess(Long blueSlipValProcess) {
		this.blueSlipValProcess = blueSlipValProcess;
	}
	
	/**
	 * 取得封鎖值3；對應六合彩(其他玩法)。
	 * @return 
	 */
	public Long getUpVal3() {
		return upVal3;
	}

	/**
	 * 設定封鎖值3；對應六合彩(其他玩法)。
	 * @param upVal3 
	 */
	public void setUpVal3(Long upVal3) {
		this.upVal3 = upVal3;
	}

	/**
	 * 取得老的封鎖值3。
	 * @return 
	 */
	public Long getUpValProcess3() {
		return upValProcess3;
	}

	/**
	 * 設定老的封鎖值3。
	 * @param upValProcess3 
	 */
	public void setUpValProcess3(Long upValProcess3) {
		this.upValProcess3 = upValProcess3;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this)
		.append("Id",getId())		
		.append("GameId",getGameId())		
		.append("UpVal",getUpVal())		
		.append("UpValProcess",getUpValProcess())		
		.append("Status",getStatus())		
			.toString();
	}
    @Override
	public int hashCode() {
		return new HashCodeBuilder()
		.append(getId())
		.append(getGameId())
		.append(getUpVal())
		.append(getUpValProcess())
		.append(getStatus())
			.toHashCode();
	}
    @Override
	public boolean equals(Object obj) {
		if(obj instanceof GameLock == false) return false;
		if(this == obj) return true;
		GameLock other = (GameLock)obj;
		return new EqualsBuilder()
		.append(getId(),other.getId())

		.append(getGameId(),other.getGameId())

		.append(getUpVal(),other.getUpVal())

		.append(getUpValProcess(),other.getUpValProcess())

		.append(getStatus(),other.getStatus())

			.isEquals();
	}
}

