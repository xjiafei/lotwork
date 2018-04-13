package com.winterframework.firefrog.game.web.dto;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/** 
* @ClassName: BetMethodMultipleStruc 
* @Description: 投注方式限制倍数基本结构 
* @author Denny 
* @date 2013-8-21 下午3:25:31 
*  
*/
public class BetMethodMultipleStruc implements Serializable {

	private static final long serialVersionUID = -1454638571351017765L;

	/** 玩法群 */
	private Integer gameGroupCode;
	/** 玩法组 */
	private Integer gameSetCode;
	/** 投注方式 */
	private Integer betMethodCode;
	/** 限制倍数 */
	private Integer multiple;
	/** 限制倍数_对比 */
	private Integer multiple_bak;
	/** 最大中奖金额 */
	private Long maxBonus;
	/** 审核发布状态：2，进行中；3，待审核；4，待发布；5，审核未通过；6，发布未通过 */
	private Integer status;
	
	private Integer[]  multiples;
	private List<BetLimitAssistStruc> assistList;
	
	private String specialMultiple;
	private String specialMultiple_bak;
	private String specialMaxBonus;
	
	private String multipleIndex;

	//骰寶相關特殊投注限制
	private Map<String,Integer> k3hezhiMultiple = new HashMap<String, Integer>();
	public Integer getGameGroupCode() {
		return gameGroupCode;
	}

	public void setGameGroupCode(Integer gameGroupCode) {
		this.gameGroupCode = gameGroupCode;
	}

	public Integer getGameSetCode() {
		return gameSetCode;
	}

	public void setGameSetCode(Integer gameSetCode) {
		this.gameSetCode = gameSetCode;
	}

	public Integer getBetMethodCode() {
		return betMethodCode;
	}

	public void setBetMethodCode(Integer betMethodCode) {
		this.betMethodCode = betMethodCode;
	}

	public Integer getMultiple() {
		return multiple;
	}

	public void setMultiple(Integer multiple) {
		this.multiple = multiple;
	}

	public Integer getMultiple_bak() {
		return multiple_bak;
	}

	public void setMultiple_bak(Integer multiple_bak) {
		this.multiple_bak = multiple_bak;
	}

	public Long getMaxBonus() {
		return maxBonus;
	}

	public void setMaxBonus(Long maxBonus) {
		this.maxBonus = maxBonus;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer[] getMultiples() {
		return multiples;
	}

	public void setMultiples(Integer[] multiples) {
		this.multiples = multiples;
	}

	public List<BetLimitAssistStruc> getAssistList() {
		return assistList;
	}

	public void setAssistList(List<BetLimitAssistStruc> assistList) {
		this.assistList = assistList;
	}

	public String getSpecialMultiple() {
		return specialMultiple;
	}

	public void setSpecialMultiple(String specialMultiple) {
		this.specialMultiple = specialMultiple;
	}

	public String getSpecialMultiple_bak() {
		return specialMultiple_bak;
	}

	public void setSpecialMultiple_bak(String specialMultiple_bak) {
		this.specialMultiple_bak = specialMultiple_bak;
	}

	public String getMultipleIndex() {
		return multipleIndex;
	}

	public void setMultipleIndex(String multipleIndex) {
		this.multipleIndex = multipleIndex;
	}

	public String getSpecialMaxBonus() {
		return specialMaxBonus;
	}

	public void setSpecialMaxBonus(String specialMaxBonus) {
		this.specialMaxBonus = specialMaxBonus;
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
