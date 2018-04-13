package com.winterframework.firefrog.game.web.dto;

import java.io.Serializable;

/**DaliyPrizeStruc
 * 日常抽奖活动数据结构
 * @author david
 *
 */
public class PrizeDaliyStruc implements Serializable{

	private static final long serialVersionUID = -4423573402263652146L;
	
	private Integer year;
	
	private Integer month;
	
	private Integer day;
	
	private Integer money;
	
//	$status = 'expired'; // 已过期
//	$status = 'accepted'; // 已领取
//	$status = 'lottery'; // 投注领奖
//	$status = 'enabled'; // 当前可领取
//	'status' => 'comming' // 尚未开启
	private String status;

	public Integer getDay() {
		return day;
	}

	public void setDay(Integer day) {
		this.day = day;
	}

	public Integer getMoney() {
		return money;
	}

	public void setMoney(Integer money) {
		this.money = money;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public Integer getMonth() {
		return month;
	}

	public void setMonth(Integer month) {
		this.month = month;
	}
}
