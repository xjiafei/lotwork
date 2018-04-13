package com.winterframework.firefrog.active.dao.vo;

import java.util.Date;

import com.winterframework.orm.dal.ibatis3.BaseEntity;

/**
 * 活動得獎名單
 * @author Pogi.Lin
 */
public class ActivityWinningLog extends BaseEntity {

	private static final long serialVersionUID = -147846672115446547L;
	
	// id 繼承自 BaseEntity，不實作。
	/**活動ID*/
	private Long activityId;
	/**用戶帳號*/
	private String account;
	/**活動獎品ID*/
	private Long activityPrizeId;
	/**資金異動流水號*/
	private String fundChangeLogSN;
	/**是否中獎；0:未中獎、1:中獎*/
	private Long winningPrize;
	/**應發獎金，非0資料(代表獎金)可轉入 FUND_CHANGE_LOG*/
	private Long changeAmt;
	/**實發獎金*/
	private Long approverAmt;
	// gmtCreated 繼承自 BsaeEntity，不實作。
	/**審批人帳號*/
	private String approver;
	/**審批時間*/
	private Date gmtAppr;
	/**審批註解*/
	private String approverMemo;
	/**用戶ID*/
	private Long userId;
	/**活動周數*/
	private Long activityWeek;
	
	/**
	 * 取得活動ID。
	 * @return 
	 */
	public Long getActivityId() {
		return activityId;
	}
	/**
	 * 設定活動ID。
	 * @param activityId 
	 */
	public void setActivityId(Long activityId) {
		this.activityId = activityId;
	}
	/**
	 * 取得用戶帳號。
	 * @return 
	 */
	public String getAccount() {
		return account;
	}
	/**
	 * 設定用戶帳號。
	 * @param account 
	 */
	public void setAccount(String account) {
		this.account = account;
	}
	/**
	 * 取得活動獎品ID。
	 * @return 
	 */
	public Long getActivityPrizeId() {
		return activityPrizeId;
	}
	/**
	 * 設定活動獎品ID。
	 * @param activityPrizeId 
	 */
	public void setActivityPrizeId(Long activityPrizeId) {
		this.activityPrizeId = activityPrizeId;
	}
	/**
	 * 取得資金異動流水號。
	 * @return 
	 */
	public String getFundChangeLogSN() {
		return fundChangeLogSN;
	}
	/**
	 * 設定資金異動流水號。
	 * @param fundChangeLogSN
	 */
	public void setFundChangeLogSN(String fundChangeLogSN) {
		this.fundChangeLogSN = fundChangeLogSN;
	}
	/**
	 * 取得是否中獎。
	 * @return 0:未中獎、1:中獎
	 */
	public Long getWinningPrize() {
		return winningPrize;
	}
	/**
	 * 設定是否中獎。
	 * @param winningPrize 0:未中獎、1:中獎
	 */
	public void setWinningPrize(Long winningPrize) {
		this.winningPrize = winningPrize;
	}
	/**
	 * 取得應發獎金。<br>
	 * 非0資料(代表獎金)可轉入 FUND_CHANGE_LOG。
	 * @return 
	 */
	public Long getChangeAmt() {
		return changeAmt;
	}
	/**
	 * 設定應發獎金。<br>
	 * 非0資料(代表獎金)可轉入 FUND_CHANGE_LOG。
	 * @param changeAmt 
	 */
	public void setChangeAmt(Long changeAmt) {
		this.changeAmt = changeAmt;
	}
	/**
	 * 取得實發獎金。
	 * @return 
	 */
	public Long getApproverAmt() {
		return approverAmt;
	}
	/**
	 * 設定實發獎金。
	 * @param approverAmt 
	 */
	public void setApproverAmt(Long approverAmt) {
		this.approverAmt = approverAmt;
	}
	/**
	 * 取得審批人帳號。
	 * @return 
	 */
	public String getApprover() {
		return approver;
	}
	/**
	 * 設定審批人帳號。
	 * @param approver 
	 */
	public void setApprover(String approver) {
		this.approver = approver;
	}
	/**
	 * 取得審批時間。
	 * @return 
	 */
	public Date getGmtAppr() {
		return gmtAppr;
	}
	/**
	 * 設定審批時間。
	 * @param gmtAppr 
	 */
	public void setGmtAppr(Date gmtAppr) {
		this.gmtAppr = gmtAppr;
	}
	/**
	 * 取得審批註解。
	 * @return 
	 */
	public String getApproverMemo() {
		return approverMemo;
	}
	/**
	 * 設定審批註解。
	 * @param approverMemo 
	 */
	public void setApproverMemo(String approverMemo) {
		this.approverMemo = approverMemo;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public Long getActivityWeek() {
		return activityWeek;
	}
	public void setActivityWeek(Long activityWeek) {
		this.activityWeek = activityWeek;
	}
	
}