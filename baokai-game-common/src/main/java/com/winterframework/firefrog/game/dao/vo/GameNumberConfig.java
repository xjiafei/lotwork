package com.winterframework.firefrog.game.dao.vo;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.winterframework.orm.dal.ibatis3.BaseEntity;

/**
 * 六合彩生肖數字對應檔。
 * @author Pogi.Lin
 */
public class GameNumberConfig extends BaseEntity{

	private static final long serialVersionUID = -7340510554553164267L;

	/**彩種ID*/
	private Long lotteryid;
	/**生肖*/
	private String numType;
	/**生肖號球(逗號分隔)*/
	private String gameNumber;
	/**生肖開始日期*/
	private Date startTime;
	/**生肖結束日期*/
	private Date endTime;
	/**是否為本命年；Y:是、N:否*/
	private String specialFlag;

	/**
	 * 取得彩種ID。
	 * @return
	 */
	public Long getLotteryid() {
		return lotteryid;
	}

	/**
	 * 設定彩種ID。
	 * @param lotteryid
	 */
	public void setLotteryid(Long lotteryid) {
		this.lotteryid = lotteryid;
	}

	/**
	 * 取得生肖。
	 * @return
	 */
	public String getNumType() {
		return numType;
	}

	/**
	 * 設定生肖。
	 * @param numType
	 */
	public void setNumType(String numType) {
		this.numType = numType;
	}

	/**
	 * 取得生肖號球(逗號分隔)。
	 * @return
	 */
	public String getGameNumber() {
		return gameNumber;
	}

	/**
	 * 設定生肖號球(逗號分隔)。
	 * @param gameNumber
	 */
	public void setGameNumber(String gameNumber) {
		this.gameNumber = gameNumber;
	}

	/**
	 * 取得生肖開始日期。
	 * @return
	 */
	public Date getStartTime() {
		return startTime;
	}

	/**
	 * 設定生肖開始日期。
	 * @param startTime
	 */
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	/**
	 * 取得生肖結束日期。
	 * @return
	 */
	public Date getEndTime() {
		return endTime;
	}

	/**
	 * 設定生肖結束日期。
	 * @param endTime
	 */
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	/**
	 * 取得是否為本命年。
	 * @return Y:是、N:否
	 */
	public String getSpecialFlag() {
		return specialFlag;
	}

	/**
	 * 設定是否為本命年。
	 * @param specialFlag Y:是、N:否
	 */
	public void setSpecialFlag(String specialFlag) {
		this.specialFlag = specialFlag;
	}
	
}
