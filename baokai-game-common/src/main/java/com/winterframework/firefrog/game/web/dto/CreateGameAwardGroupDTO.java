package com.winterframework.firefrog.game.web.dto;

import java.io.Serializable;
import java.util.List;

public class CreateGameAwardGroupDTO implements Serializable {

	private static final long serialVersionUID = 8221865001211581928L;

	private Long lotteryId;
	private String awardName;
	private Integer directRet;
	private Integer threeoneRet;
	private List<AwardBonusStruc> awardBonusStrucList;
	
	public CreateGameAwardGroupDTO() {
		
	}

	public Long getLotteryId() {
		return lotteryId;
	}

	public void setLotteryId(Long lotteryId) {
		this.lotteryId = lotteryId;
	}

	public String getAwardName() {
		return awardName;
	}

	public void setAwardName(String awardName) {
		this.awardName = awardName;
	}

	public Integer getDirectRet() {
		return directRet;
	}

	public void setDirectRet(Integer directRet) {
		this.directRet = directRet;
	}

	public Integer getThreeoneRet() {
		return threeoneRet;
	}

	public void setThreeoneRet(Integer threeoneRet) {
		this.threeoneRet = threeoneRet;
	}

	public List<AwardBonusStruc> getAwardBonusStrucList() {
		return awardBonusStrucList;
	}

	public void setAwardBonusStrucList(List<AwardBonusStruc> awardBonusStrucList) {
		this.awardBonusStrucList = awardBonusStrucList;
	}
	
}
