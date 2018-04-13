package com.winterframework.firefrog.game.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Points {
	/**號球或生肖*/
	private String point;
	private Long mult;	
	private Long singleBet;
	/**投注前盈虧值*/
	@JsonIgnore
	private Long single;
	private Long retValue;
	
	private Integer currPhase;
	/**本次投注調整後投注金額*/
	private Long afterMoney;
	/**本次投注原始投注金額*/
	private Long beforeMoney;

	/**
	 * 取得號球或生肖。
	 * @return
	 */
	public String getPoint() {
		return point;
	}

	public Long getRetValue() {
		return retValue;
	}

	public void setRetValue(Long retValue) {
		this.retValue = retValue;
	}

	public Long getSingleBet() {
		return singleBet;
	}

	public void setSingleBet(Long singleBet) {
		this.singleBet = singleBet;
	}

	/**
	 * 設定號球或生肖。
	 * @param point
	 */
	public void setPoint(String point) {
		this.point = point;
	}

	public Long getMult() {
		return mult;
	}

	public void setMult(Long mult) {
		this.mult = mult;
	}

	
	/**
	 * 取得投注前盈虧值。
	 * @return
	 */
	public Long getSingle() {
		return single;
	}
	/**
	 * 設定投注前盈虧值。
	 * @param single
	 */
	public void setSingle(Long single) {
		this.single = single;
	}

	public Integer getCurrPhase() {
		return currPhase;
	}

	public void setCurrPhase(Integer currPhase) {
		this.currPhase = currPhase;
	}
	/**
	 * 設定本次投注調整後投注金額。
	 * @return
	 */
	public Long getAfterMoney() {
		return afterMoney;
	}
	/**
	 * 取得本次投注調整後投注金額。
	 * @param afterMoney
	 */
	public void setAfterMoney(Long afterMoney) {
		this.afterMoney = afterMoney;
	}
	/**
	 * 取得本次投注原始投注金額。
	 * @return
	 */
	public Long getBeforeMoney() {
		return beforeMoney;
	}
	/**
	 * 設定本次投注原始投注金額
	 * @param beforeMoney
	 */
	public void setBeforeMoney(Long beforeMoney) {
		this.beforeMoney = beforeMoney;
	}

}
