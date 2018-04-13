package com.winterframework.firefrog.game.web.dto;

import java.io.Serializable;

/**PrizeLuckyStruc
 * 抽奖返回中奖数据结构
 * @author david
 *
 */
public class PrizeLuckyStruc implements Serializable{

	private static final long serialVersionUID = -180842877049060292L;
    
	private String status;
	private Integer luckyidx;
	private String date;
	private String desc;
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}

	public Integer getLuckyidx() {
		return luckyidx;
	}
	public void setLuckyidx(Integer luckyidx) {
		this.luckyidx = luckyidx;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	
}
