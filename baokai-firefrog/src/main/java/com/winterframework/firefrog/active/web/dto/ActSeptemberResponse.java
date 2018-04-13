package com.winterframework.firefrog.active.web.dto;

import java.io.Serializable;

/**
 * 
 * @author Ami.Tsai
 *
 */
public class ActSeptemberResponse implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2182561574766053613L;

	
	private String isDraw;
	
	//是否有報名
	private Long hasRight;
	//下盞燈還需投注多少錢
	private Long leftMoney;
	//可點到幾盞燈
	private Integer level;
	//是否已經點燈領錢
	private Long isFinished;
	//是否VIP用戶
	private Long isVip;
	//已經點到第幾盞燈
	private Integer nowLevel;
	//接口成功
	private Long isSuccess;
	//可否領取琉璃燈
	private Long isTop;


	public String getIsDraw() {
		return isDraw;
	}


	public void setIsDraw(String isDraw) {
		this.isDraw = isDraw;
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


	public Long getHasRight() {
		return hasRight;
	}


	public void setHasRight(Long hasRight) {
		this.hasRight = hasRight;
	}


	public Long getIsVip() {
		return isVip;
	}


	public void setIsVip(Long isVip) {
		this.isVip = isVip;
	}


	public Integer getNowLevel() {
		return nowLevel;
	}


	public void setNowLevel(Integer nowLevel) {
		this.nowLevel = nowLevel;
	}


	public Long getIsSuccess() {
		return isSuccess;
	}


	public void setIsSuccess(Long isSuccess) {
		this.isSuccess = isSuccess;
	}


	public Long getIsTop() {
		return isTop;
	}


	public void setIsTop(Long isTop) {
		this.isTop = isTop;
	}


	
}
