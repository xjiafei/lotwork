package com.winterframework.firefrog.game.web.dto;

import java.util.List;

import com.winterframework.firefrog.game.web.util.GameAwardNameUtil;

public class BetLimitMethodCode {

	/** 投注方式 */
	private Integer betMethodCode;
	/** 投注方式名称 */
	private String betMethodName;
	/** 限制倍数 */
	private Integer multiple;
	/** 限制倍数(修改前数据) */
	private Integer multiple_bak;
	/** 最大中奖金额 */
	private Long maxBonus;
	
	private List<BetLimitAssistStruc> assistList;
	
	private String[] specialMultiple;
	
	private String[] specialMultiple_bak;
	
	private String[] specialMaxBonus;
	
	public Integer getBetMethodCode() {
		return betMethodCode;
	}
	public void setBetMethodCode(Integer betMethodCode) {
		this.betMethodCode = betMethodCode;
	}
	public String getBetMethodName() {
//		betMethodName = GameAwardNameUtil.methodCodeName(betMethodCode);
		return betMethodName;
	}
	public void setBetMethodName(String betMethodName) {
		this.betMethodName = betMethodName;
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
	public List<BetLimitAssistStruc> getAssistList() {
		return assistList;
	}
	public void setAssistList(List<BetLimitAssistStruc> assistList) {
		this.assistList = assistList;
	}
	public String[] getSpecialMultiple() {
		return specialMultiple;
	}
	public void setSpecialMultiple(String[] specialMultiple) {
		this.specialMultiple = specialMultiple;
	}
	public String[] getSpecialMultiple_bak() {
		return specialMultiple_bak;
	}
	public void setSpecialMultiple_bak(String[] specialMultiple_bak) {
		this.specialMultiple_bak = specialMultiple_bak;
	}
	public String[] getSpecialMaxBonus() {
		return specialMaxBonus;
	}
	public void setSpecialMaxBonus(String[] specialMaxBonus) {
		this.specialMaxBonus = specialMaxBonus;
	}

}
