package com.winterframework.firefrog.game.dao.vo;

import java.util.Date;

import com.winterframework.orm.dal.ibatis3.BaseEntity;

public class GameDiamondBettype extends BaseEntity{

	private static final long serialVersionUID = -1967439279592055315L;

	//alias
	public static final String TABLE_ALIAS = "鑽石加獎玩法說明";
	public static final String ALIAS_LOTTERYID = "彩种";
	public static final String ALIAS_DIAMOND_LV = "鑽石等級";
	public static final String ALIAS_MULTIPLE = "倍率";
	public static final String ALIAS_THEORY_BONUS = "理論獎金";
	public static final String ALIAS_PROBABILITY = "機率";
	
	//columns START
	private Long lotteryId;
	private Long diamondLv;
	private Long multiple;
	private Long theoryBonus;
	private Long probability;
	//columns END
	
	
	public Long getLotteryId() {
		return lotteryId;
	}
	public void setLotteryId(Long lotteryId) {
		this.lotteryId = lotteryId;
	}
	public Long getDiamondLv() {
		return diamondLv;
	}
	public void setDiamondLv(Long diamondLv) {
		this.diamondLv = diamondLv;
	}
	public Long getMultiple() {
		return multiple;
	}
	public void setMultiple(Long multiple) {
		this.multiple = multiple;
	}
	public Long getTheoryBonus() {
		return theoryBonus;
	}
	public void setTheoryBonus(Long theoryBonus) {
		this.theoryBonus = theoryBonus;
	}
	public Long getProbability() {
		return probability;
	}
	public void setProbability(Long probability) {
		this.probability = probability;
	}
}
