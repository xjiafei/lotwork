package com.winterframework.firefrog.active.entity;

import java.io.Serializable;

public class ActivitySeptEntity implements Serializable{

	private static final long serialVersionUID = -3495961322713611062L;
	
	
	//用戶ID
	private Long userId;
	
	

	//是否有報名
	private Long hasRight;
	//下盞燈還需投注多少錢
	private Long leftMoney;
	//可點到幾盞燈
	private Integer level;
	//是否已經點燈領錢
	private Long isFinished;
	//現在第幾層
	private Integer nowLevel;
	//可不可以領琉璃燈
	private Long isTop;
	
	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}
	
	public Long getHasRight() {
		return hasRight;
	}

	public void setHasRight(Long hasRight) {
		this.hasRight = hasRight;
	}

	public Long getLeftMoney() {
		return leftMoney;
	}

	public void setLeftMoney(Long leftMoney) {
		this.leftMoney = leftMoney;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public Long getIsFinished() {
		return isFinished;
	}

	public void setIsFinished(Long isFinished) {
		this.isFinished = isFinished;
	}

	public Integer getNowLevel() {
		return nowLevel;
	}

	public void setNowLevel(Integer nowLevel) {
		this.nowLevel = nowLevel;
	}

	public Long getIsTop() {
		return isTop;
	}

	public void setIsTop(Long isTop) {
		this.isTop = isTop;
	}

}
