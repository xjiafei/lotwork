package com.winterframework.firefrog.game.web.dto;

/** 
* @ClassName: GameBetBitDate 
* @Description: 投注过期提示，tpldata数据结构 
* @author david 
* @date 2013-9-27 下午3:07:50 
*  
*/
public class GameBetBitDate {
	private String expiredDate;
	private String current;

	public String getExpiredDate() {
		return expiredDate;
	}

	public void setExpiredDate(String expiredDate) {
		this.expiredDate = expiredDate;
	}

	public String getCurrent() {
		return current;
	}

	public void setCurrent(String current) {
		this.current = current;
	}

}
