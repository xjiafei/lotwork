package com.winterframework.firefrog.game.web.dto;

import java.util.Map;

/** 
* @ClassName: GameMethodLimit 
* @Description: 游戏玩法限制
* @author Alan
* @date 2013-10-9 下午2:57:50 
*  
*/
public class GameLimit {

	//最大可选倍数(-1表示无限制)
	private Long maxmultiple;
	//用户奖金组
	private Long usergroupmoney;

	private Integer[] maxmultiples;
	
	private String[] specialMultiple;//11選五特殊倍數
	
	private Map<String,Integer> k3hezhiMultiple;//骰寶和值倍數
	
	public Long getMaxmultiple() {
		return maxmultiple;
	}

	public void setMaxmultiple(Long maxmultiple) {
		this.maxmultiple = maxmultiple;
	}

	public Long getUsergroupmoney() {
		return usergroupmoney;
	}

	public void setUsergroupmoney(Long usergroupmoney) {
		this.usergroupmoney = usergroupmoney;
	}

	public Integer[] getMaxmultiples() {
		return maxmultiples;
	}

	public void setMaxmultiples(Integer[] maxmultiples) {
		this.maxmultiples = maxmultiples;
	}

	public String[] getSpecialMultiple() {
		return specialMultiple;
	}

	public void setSpecialMultiple(String[] specialMultiple) {
		this.specialMultiple = specialMultiple;
	}

	/**
	 * @return the k3hezhiMultiple
	 */
	public Map<String, Integer> getK3hezhiMultiple() {
		return k3hezhiMultiple;
	}

	/**
	 * @param k3hezhiMultiple the k3hezhiMultiple to set
	 */
	public void setK3hezhiMultiple(Map<String, Integer> k3hezhiMultiple) {
		this.k3hezhiMultiple = k3hezhiMultiple;
	}

	
	
}
