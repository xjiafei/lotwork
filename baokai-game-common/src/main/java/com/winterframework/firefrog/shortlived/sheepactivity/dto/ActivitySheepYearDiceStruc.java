/**   
* @Title: AssistBmBonusStruc.java 
* @Package com.winterframework.firefrog.game.web.dto 
* @Description: TODO(用一句话描述该文件做什么) 
* @author 你的名字   
* @date 2014-4-14 下午2:07:34 
* @version V1.0   
*/
package com.winterframework.firefrog.shortlived.sheepactivity.dto;

import java.io.Serializable;

/** 
* @ClassName: ActivitySheepYearRewardStruc 
* @Description: 羊年活动押大小dice结构
* @author david
*  
*/
public class ActivitySheepYearDiceStruc implements Serializable{

	private static final long serialVersionUID = -1543757401485976506L;

	private String status;
	
	private String msg;
	
	private Long diceAmount;//剩余亚宝次数
	
	private Long diceContinus;//当前连中次数
	
	private String diceStatus;//中奖状态：win中奖  lose不中奖
	
	private String date;//开奖日期 mm-dd hh24:mi:ss
	
	private Integer[] result;//开奖结果
	
	private String type;//开奖大小 big small
	
	private Long winMoney;//中奖金额

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Long getDiceAmount() {
		return diceAmount;
	}

	public void setDiceAmount(Long diceAmount) {
		this.diceAmount = diceAmount;
	}

	public Long getDiceContinus() {
		return diceContinus;
	}

	public void setDiceContinus(Long diceContinus) {
		this.diceContinus = diceContinus;
	}

	public String getDiceStatus() {
		return diceStatus;
	}

	public void setDiceStatus(String diceStatus) {
		this.diceStatus = diceStatus;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public Integer[] getResult() {
		return result;
	}

	public void setResult(Integer[] result) {
		this.result = result;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Long getWinMoney() {
		return winMoney;
	}

	public void setWinMoney(Long winMoney) {
		this.winMoney = winMoney;
	}

}
