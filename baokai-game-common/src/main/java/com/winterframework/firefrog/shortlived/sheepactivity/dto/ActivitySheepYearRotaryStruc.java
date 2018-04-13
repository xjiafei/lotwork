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
* @Description: 转盘抽奖结构
* @author david
*  
*/
public class ActivitySheepYearRotaryStruc implements Serializable{

	private static final long serialVersionUID = -1543757401485976506L;

	private String status;
	
	private String date;
	
	private Long luckyidx;//剩余亚宝次数
	
	private Long times;//当前连中次数
	
	private String username;//中奖状态：win中奖  lose不中奖
	
	private String desc;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public Long getLuckyidx() {
		return luckyidx;
	}

	public void setLuckyidx(Long luckyidx) {
		this.luckyidx = luckyidx;
	}

	public Long getTimes() {
		return times;
	}

	public void setTimes(Long times) {
		this.times = times;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}
}
