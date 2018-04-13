package com.winterframework.firefrog.game.web.dto;

import java.io.Serializable;
import java.util.List;

/**DaliyPrizeStruc
 * 日常抽奖活动
 * @author david
 *
 */
public class PrizeStruc implements Serializable{

	private static final long serialVersionUID = -4423573402263652146L;
	
	private Integer days; //活动天数
	
	private Integer money;//每次活动奖励
	
	private Integer thisDay;//当前天
	
	List<PrizeDaliyStruc> prizes;

	public Integer getDays() {
		return days;
	}

	public void setDays(Integer days) {
		this.days = days;
	}

	public Integer getMoney() {
		return money;
	}

	public void setMoney(Integer money) {
		this.money = money;
	}

	public Integer getThisDay() {
		return thisDay;
	}

	public void setThisDay(Integer thisDay) {
		this.thisDay = thisDay;
	}

	public List<PrizeDaliyStruc> getPrizes() {
		return prizes;
	}

	public void setPrizes(List<PrizeDaliyStruc> prizes) {
		this.prizes = prizes;
	}
	
}
