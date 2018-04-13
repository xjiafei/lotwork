package com.winterframework.firefrog.phone.web.cotroller.dto;

import java.io.Serializable;
import java.util.Date;

public class AgentRpt implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1239467840055714977L;
	private Date day;
	private Long time;
	private Long withDraw;
	private Long charge;
	private Long bet;
	private Long reward;
	private Long newUser;
	private Long userId;
	public Date getDay() {
		return day;
	}
	public void setDay(Date day) {
		this.day = day;
	}
	public Long getTime() {
		return time;
	}
	public void setTime(Long time) {
		this.time = time;
	}
	public Long getWithDraw() {
		return withDraw;
	}
	public void setWithDraw(Long withDraw) {
		this.withDraw = withDraw;
	}
	public Long getCharge() {
		return charge;
	}
	public void setCharge(Long charge) {
		this.charge = charge;
	}
	public Long getBet() {
		return bet;
	}
	public void setBet(Long bet) {
		this.bet = bet;
	}
	public Long getReward() {
		return reward;
	}
	public void setReward(Long reward) {
		this.reward = reward;
	}
	public Long getNewUser() {
		return newUser;
	}
	public void setNewUser(Long newUser) {
		this.newUser = newUser;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	
}
