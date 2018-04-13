package com.winterframework.firefrog.game.web.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * 开奖结果Struc
 * @ClassName
 * @Description
 * @author ibm
 * 2014年10月15日
 */

public class LhcGameOddsDetail implements Serializable {
	/** 彩种 */
	private String name;
	private Double odds;
	private String specialFlag;
	/**
	 * 六合彩奖金识别
	 */
	private String lhcCode;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Double getOdds() {
		return odds;
	}
	public void setOdds(Double odds) {
		this.odds = odds;
	}
	public String getSpecialFlag() {
		return specialFlag;
	}
	public void setSpecialFlag(String specialFlag) {
		this.specialFlag = specialFlag;
	}
	/**
	 * 取得六合彩奖金识别
	 */
	public String getLhcCode() {
		return lhcCode;
	}
	/**
	 * 設定六合彩奖金识别
	 */
	public void setLhcCode(String lhcCode) {
		this.lhcCode = lhcCode;
	}
	
}
