package com.winterframework.firefrog.active.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * @author David.Wu
 *
 */
public class ActivityAuguest implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3495961322713611062L;
	
	/**
	 * 活動ID
	 */
	private Long activityId;
	
	/**
	 * 用戶ID
	 */
	private Long userId;
	/**
	 * 用戶帳號
	 */
	private String userAccunt;
	
	/**
	 * 3.0 或是 4.0 用戶
	 */
	private String userSource;
	/**
	 * 活動設定檔類別
	 */
	private String configType;
	/**
	 * 活動代碼
	 */
	private String activityCode;
	
	/**
	 * 使用者等級
	 */
	private Integer userLv;
	
	/**
	 * 用戶中獎金額
	 */
	private Long prize;
	
	/**
	 * 用戶投注金額
	 */
	private Long userAmount;
	
	/**
	 * 用戶仍需投注金額
	 */
	private Long leftAmount;
	
	/**
	 * 用戶需要投注总金额
	 */
	private Long prizeAmount;
	
	/**
	 * 用戶今日遊戲是否完成
	 * 完成 : true
	 * 沒完成 : false
	 */
	private boolean isFinished;
	
	/**
	 * 當日遊戲是否結束
	 * 結束 : true
	 * 沒結束 : false
	 */
	private boolean todayFinished;
	
	/**
	 * 遊戲當前狀態
	 * 0: 游戏未开始  1：游戏进行中  2： 游戏预告期 (8月23日之前)
	 */
	private Long status;
	
	/**
	 * 用戶是否有参加资格
	 */
	private boolean qualification;
	
	/**
	 * 红包剩余数量
	 */
	private Long surplusPrize;
	
	/**
	 * 獎金上限
	 */
	private Long rand_max;
	/**
	 * 獎金下限
	 */
	private Long rand_min;
	
	/**
	 * 今日大獎數量
	 */
	private Long awardsCunt;
	
	/**
	 * 設定紅包數量
	 */
	private Long quantity;
	
	/**
	 * 中獎類別
	 */
	private String resultType;
	
	/**
	 * 活動開始時間
	 */
	private Date activityStartTime;
	
	/**
	 * 活動結束時間
	 */
	private Date activityEndTime;
	
	/**
	 * 今日活動開始時間
	 */
	private Date todayStartTime;
	/**
	 * 今日活動結束時間
	 */
	private Date todayEndTime;
	
	/**
	 * 現在時間
	 */
	private Date nowTime;
	
	/**
	 * LOG 紀錄
	 */
	private String memo;
	
	/**
	 * 每日活動開始時間
	 */
	private Long dayStart;
	
	/**
	 * 每日活動結束時間
	 */
	private Long dayEnd;
	
	/**
	 * 大獎機率最大值
	 */
	private Long rateRandMax;
	/**
	 * 大獎機率最小值
	 */
	private Long rateRandMin;
	
	/**
	 * 用戶今日中獎時間
	 */
	private Date awardTime;

	public final Long getActivityId() {
		return activityId;
	}

	public final void setActivityId(Long activityId) {
		this.activityId = activityId;
	}

	public final Long getUserId() {
		return userId;
	}

	public final void setUserId(Long userId) {
		this.userId = userId;
	}

	public final String getUserAccunt() {
		return userAccunt;
	}

	public final void setUserAccunt(String userAccunt) {
		this.userAccunt = userAccunt;
	}

	public final String getConfigType() {
		return configType;
	}

	public final void setConfigType(String configType) {
		this.configType = configType;
	}

	public final String getActivityCode() {
		return activityCode;
	}

	public final void setActivityCode(String activityCode) {
		this.activityCode = activityCode;
	}

	public final Integer getUserLv() {
		return userLv;
	}

	public final void setUserLv(Integer userLv) {
		this.userLv = userLv;
	}

	public final Long getPrize() {
		return prize;
	}

	public final void setPrize(Long prize) {
		this.prize = prize;
	}

	public final Long getUserAmount() {
		return userAmount;
	}

	public final void setUserAmount(Long userAmount) {
		this.userAmount = userAmount;
	}

	public Long getLeftAmount() {
		return leftAmount;
	}

	public void setLeftAmount(Long leftAmount) {
		this.leftAmount = leftAmount;
	}

	public Long getPrizeAmount() {
		return prizeAmount;
	}

	public void setPrizeAmount(Long prizeAmount) {
		this.prizeAmount = prizeAmount;
	}

	public final boolean isFinished() {
		return isFinished;
	}

	public final void setFinished(boolean isFinished) {
		this.isFinished = isFinished;
	}

	public final boolean isTodayFinished() {
		return todayFinished;
	}

	public final void setTodayFinished(boolean todayFinished) {
		this.todayFinished = todayFinished;
	}

	public final Long getStatus() {
		return status;
	}

	public final void setStatus(Long status) {
		this.status = status;
	}

	public final boolean isQualification() {
		return qualification;
	}

	public final void setQualification(boolean qualification) {
		this.qualification = qualification;
	}

	public final Long getSurplusPrize() {
		return surplusPrize;
	}

	public final void setSurplusPrize(Long surplusPrize) {
		this.surplusPrize = surplusPrize;
	}

	public Long getRand_max() {
		return rand_max;
	}

	public void setRand_max(Long rand_max) {
		this.rand_max = rand_max;
	}

	public Long getRand_min() {
		return rand_min;
	}

	public void setRand_min(Long rand_min) {
		this.rand_min = rand_min;
	}

	public Long getAwardsCunt() {
		return awardsCunt;
	}

	public void setAwardsCunt(Long awardsCunt) {
		this.awardsCunt = awardsCunt;
	}

	public Long getQuantity() {
		return quantity;
	}

	public void setQuantity(Long quantity) {
		this.quantity = quantity;
	}

	public String getResultType() {
		return resultType;
	}

	public void setResultType(String resultType) {
		this.resultType = resultType;
	}

	public final Date getActivityStartTime() {
		return activityStartTime;
	}

	public final void setActivityStartTime(Date activityStartTime) {
		this.activityStartTime = activityStartTime;
	}

	public final Date getActivityEndTime() {
		return activityEndTime;
	}

	public final void setActivityEndTime(Date activityEndTime) {
		this.activityEndTime = activityEndTime;
	}

	public final Date getTodayStartTime() {
		return todayStartTime;
	}

	public final void setTodayStartTime(Date todayStartTime) {
		this.todayStartTime = todayStartTime;
	}

	public final Date getTodayEndTime() {
		return todayEndTime;
	}

	public final void setTodayEndTime(Date todayEndTime) {
		this.todayEndTime = todayEndTime;
	}

	public String getUserSource() {
		return userSource;
	}

	public void setUserSource(String userSource) {
		this.userSource = userSource;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public Date getNowTime() {
		return nowTime;
	}

	public void setNowTime(Date nowTime) {
		this.nowTime = nowTime;
	}

	public Long getDayStart() {
		return dayStart;
	}

	public void setDayStart(Long dayStart) {
		this.dayStart = dayStart;
	}

	public Long getDayEnd() {
		return dayEnd;
	}

	public void setDayEnd(Long dayEnd) {
		this.dayEnd = dayEnd;
	}

	public Long getRateRandMax() {
		return rateRandMax;
	}

	public void setRateRandMax(Long rateRandMax) {
		this.rateRandMax = rateRandMax;
	}

	public Long getRateRandMin() {
		return rateRandMin;
	}

	public void setRateRandMin(Long rateRandMin) {
		this.rateRandMin = rateRandMin;
	}

	public Date getAwardTime() {
		return awardTime;
	}

	public void setAwardTime(Date awardTime) {
		this.awardTime = awardTime;
	}
	

}
