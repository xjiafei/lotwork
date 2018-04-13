package com.winterframework.firefrog.active.web.dto;

import java.io.Serializable;

/** 
* @ClassName ActRedEnvelopeResponse 
* @Description 
* @author  Ray
* @date 2016年07月15日 上午11:52:37 
*  
*/
public class ActAuguestResponse implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2108726806220372577L;

	
	//0:失敗　1:成功
	private Long result;
	
	//紅包 0: 游戏未开始  1：游戏进行中  2： 游戏预告期 (8月23日之前) -1:異常
	//奧運 -1:不在活動區間 0:未報名 1:已報名或不需報
	private Long status;
	
	//紅包 參加資格 0: 無參加資格 1:有參加資格
	private Long qualification;
	//剩餘紅包數
	private Long rest;
	
	//紅包金額
	private Long redEnvelope;
	
	//需投注總額
	private Long betsTotal=0l;
	
	//已投注金額
	private Long betAmount=0l;
	
	/**
	 * 用戶仍需投注金額
	 */
	private Long leftAmount;
	
	/**
	 * 用戶今日遊戲是否完成
	 * 完成 : 1
	 * 沒完成 : 0
	 */
	private Long isFinished;
	
	/**
	 * 當日遊戲是否結束
	 * 結束 : 1
	 * 沒結束 : 0
	 */
	private Long todayFinished;
	/**
	 * 今日活動開始時間
	 */
	private String todayStartTime;
	/**
	 * 今日活動結束時間
	 */
	private String todayEndTime;
	/**
	 * 現在時間
	 */
	private String nowTime;
	
	//充值金額
	private Long chargeAmount=0l;
	
	//返利比例
	private Long RebateRatio=0l;
	
	//加獎比例
	private Long plusRatio=0l;
	
	//金牌數
	private Long medals =0l;
	
	//獎金總額
	private String prize = "0";
	
	//錯誤訊息
	private String errorMsg ;

	public Long getResult() {
		return result;
	}

	public void setResult(Long result) {
		this.result = result;
	}

	public String getPrize() {
		return prize;
	}

	public void setPrize(String prize) {
		this.prize = prize;
	}

	public Long getChargeAmount() {
		return chargeAmount;
	}

	public void setChargeAmount(Long chargeAmount) {
		this.chargeAmount = chargeAmount;
	}

	public Long getRebateRatio() {
		return RebateRatio;
	}

	public void setRebateRatio(Long rebateRatio) {
		RebateRatio = rebateRatio;
	}

	public Long getPlusRatio() {
		return plusRatio;
	}

	public void setPlusRatio(Long plusRatio) {
		this.plusRatio = plusRatio;
	}

	public Long getMedals() {
		return medals;
	}

	public void setMedals(Long medals) {
		this.medals = medals;
	}

	public Long getStatus() {
		return status;
	}

	public void setStatus(Long status) {
		this.status = status;
	}

	public Long getRest() {
		return rest;
	}

	public void setRest(Long rest) {
		this.rest = rest;
	}

	public Long getRedEnvelope() {
		return redEnvelope;
	}

	public void setRedEnvelope(Long redEnvelope) {
		this.redEnvelope = redEnvelope;
	}

	public Long getBetsTotal() {
		return betsTotal;
	}

	public void setBetsTotal(Long betsTotal) {
		this.betsTotal = betsTotal;
	}

	public Long getBetAmount() {
		return betAmount;
	}

	public void setBetAmount(Long betAmount) {
		this.betAmount = betAmount;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public Long getQualification() {
		return qualification;
	}

	public void setQualification(Long qualification) {
		this.qualification = qualification;
	}

	public Long getLeftAmount() {
		return leftAmount;
	}

	public void setLeftAmount(Long leftAmount) {
		this.leftAmount = leftAmount;
	}

	public Long getIsFinished() {
		return isFinished;
	}

	public void setIsFinished(Long isFinished) {
		this.isFinished = isFinished;
	}

	public Long getTodayFinished() {
		return todayFinished;
	}

	public void setTodayFinished(Long todayFinished) {
		this.todayFinished = todayFinished;
	}

	public String getTodayStartTime() {
		return todayStartTime;
	}

	public void setTodayStartTime(String todayStartTime) {
		this.todayStartTime = todayStartTime;
	}

	public String getTodayEndTime() {
		return todayEndTime;
	}

	public void setTodayEndTime(String todayEndTime) {
		this.todayEndTime = todayEndTime;
	}

	public String getNowTime() {
		return nowTime;
	}

	public void setNowTime(String nowTime) {
		this.nowTime = nowTime;
	}
	
    
}
