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
* @ClassName: ActivitySheepYearDiceDetailStruc 
* @Description: 羊年活动押大小dice detail结构
* @author david
*  
*/
public class ActivitySheepYearDiceDetailStruc implements Serializable{

	private static final long serialVersionUID = -1543757401485976506L;
    
	private String time;
	
	private String[] result;//开奖结果
	
	private String type;//开奖大小 big small
	
	private Long winMoney;//中奖金额

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String[] getResult() {
		return result;
	}

	public void setResult(String[] result) {
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
