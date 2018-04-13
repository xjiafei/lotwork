package com.winterframework.firefrog.phone.web.cotroller.dto;

import java.io.Serializable;

public class GameWinDto implements Serializable {
 
	/**
	 * 
	 */
	private static final long serialVersionUID = 640020099233046136L;
	private String lotteryName;//彩种名称
	private String userName;//	用户名称
	private Double winMoney;	//中奖金额
	
	public String getLotteryName() {
		return lotteryName;
	}
	public void setLotteryName(String lotteryName) {
		this.lotteryName = lotteryName;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public Double getWinMoney() {
		return winMoney;
	}
	public void setWinMoney(Double winMoney) {
		this.winMoney = winMoney;
	}
	
}
