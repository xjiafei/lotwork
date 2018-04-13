package com.winterframework.firefrog.game.web.dto;

import java.io.Serializable;

/**PrizeLuckyStruc
 * 我的抽奖结果列表
 * @author david
 *
 */
public class MyLuckStruc implements Serializable{

	private static final long serialVersionUID = -180842877049060292L;
    
	private String date;
	private String awardName;
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getAwardName() {
		return awardName;
	}
	public void setAwardName(String awardName) {
		this.awardName = awardName;
	}
	
}
