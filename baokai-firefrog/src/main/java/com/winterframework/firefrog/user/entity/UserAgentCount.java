package com.winterframework.firefrog.user.entity;

import java.util.Date;

/** 
* @ClassName: UserAgentCount 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author 你的名字 
* @date 2013-12-27 上午11:32:22 
*  
*/
public class UserAgentCount {

	private Date day;
	private Long time;
	private Long withDraw;
	private Long charge;
	private Long bet;
	private Long reward;
	private Long newUser;
	private Long userId;
	
	//用于查询使用
	private String startTime;
	private String endTime;
	private Long type;
	
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
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public Long getType() {
		return type;
	}
	public void setType(Long type) {
		this.type = type;
	}
}
