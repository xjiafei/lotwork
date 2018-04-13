package com.winterframework.firefrog.game.web.dto;

import java.io.Serializable;

/** 
 * 彩种奖期基本结构 
 * @author david 
 * @date 2013-8-21 下午5:42:54 
 */
public class IssueStruc implements Serializable {

	private static final long serialVersionUID = -8848174089315020130L;

	/**彩種ID*/
	private Long lotteryId;
	/**彩種名稱*/
	private String lotteryName;
	/**獎期*/
	private Long issueCode;
	/**web獎期*/
	private String webIssueCode;
	/**销售日期  20130717*/
	private String saleDate;
	/**销售时间段 00:30:00-00:35:00*/
	private String sale_period;
	/**理论开奖时间*/
	private Long openDrawTime;
	/**開獎號碼*/
	private String numberRecord;
	/**獎期過程狀態；0:待销售(P1) 1:销售中(P2) 2:待开奖(P3) 3:计奖中(P4) 4:验奖中(P5) 5:派奖中(P6) 6:待结束(P7) 7:待对账(P8)*/
	private Integer periodStatus;
	private Long confirmDrawTime;
	private String warnDescStr;

	/**
	 * 取得彩種ID。
	 * @return
	 */
	public Long getLotteryId() {
		return lotteryId;
	}

	/**
	 * 設定彩種ID。
	 * @param lotteryId
	 */
	public void setLotteryId(Long lotteryId) {
		this.lotteryId = lotteryId;
	}

	/**
	 * 取得彩種名稱。
	 * @return
	 */
	public String getLotteryName() {
		return lotteryName;
	}

	/**
	 * 設定彩種名稱。
	 * @param lotteryName
	 */
	public void setLotteryName(String lotteryName) {
		this.lotteryName = lotteryName;
	}

	/**
	 * 取得獎期。
	 * @return
	 */
	public Long getIssueCode() {
		return issueCode;
	}

	/**
	 * 設定獎期。
	 * @param issueCode
	 */
	public void setIssueCode(Long issueCode) {
		this.issueCode = issueCode;
	}

	/**
	 * 取得web獎期。
	 * @return
	 */
	public String getWebIssueCode() {
		return webIssueCode;
	}

	/**
	 * 設定web獎期。
	 * @param webIssueCode
	 */
	public void setWebIssueCode(String webIssueCode) {
		this.webIssueCode = webIssueCode;
	}

	/**
	 * 取得销售日期。
	 * @return
	 */
	public String getSaleDate() {
		return saleDate;
	}

	/**
	 * 設定销售日期。
	 * @param saleDate
	 */
	public void setSaleDate(String saleDate) {
		this.saleDate = saleDate;
	}

	/**
	 * 取得销售时间段。
	 * @return
	 */
	public String getSale_period() {
		return sale_period;
	}

	/**
	 * 設定销售时间段。
	 * @param sale_period
	 */
	public void setSale_period(String sale_period) {
		this.sale_period = sale_period;
	}

	/**
	 * 取得理论开奖时间。
	 * @return
	 */
	public Long getOpenDrawTime() {
		return openDrawTime;
	}

	/**
	 * 設定理论开奖时间。
	 * @param openDrawTime
	 */
	public void setOpenDrawTime(Long openDrawTime) {
		this.openDrawTime = openDrawTime;
	}

	/**
	 * 取得開獎號碼。
	 * @return
	 */
	public String getNumberRecord() {
		return numberRecord;
	}

	/**
	 * 設定開獎號碼。
	 * @param numberRecord
	 */
	public void setNumberRecord(String numberRecord) {
		this.numberRecord = numberRecord;
	}

	/**
	 * 取得獎期過程狀態。
	 * @return 0:待销售(P1) 1:销售中(P2) 2:待开奖(P3) 3:计奖中(P4) 4:验奖中(P5) 5:派奖中(P6) 6:待结束(P7) 7:待对账(P8)
	 */
	public Integer getPeriodStatus() {
		return periodStatus;
	}

	/**
	 * 設定獎期過程狀態。
	 * @param periodStatus 0:待销售(P1) 1:销售中(P2) 2:待开奖(P3) 3:计奖中(P4) 4:验奖中(P5) 5:派奖中(P6) 6:待结束(P7) 7:待对账(P8)
	 */
	public void setPeriodStatus(Integer periodStatus) {
		this.periodStatus = periodStatus;
	}

	public Long getConfirmDrawTime() {
		return confirmDrawTime;
	}

	public void setConfirmDrawTime(Long confirmDrawTime) {
		this.confirmDrawTime = confirmDrawTime;
	}

	public String getWarnDescStr() {
		return warnDescStr;
	}

	public void setWarnDescStr(String warnDescStr) {
		this.warnDescStr = warnDescStr;
	}
}
