package com.winterframework.firefrog.game.web.dto;

import java.io.Serializable;
import java.util.List;

public class EditGameAwardGroupDTO implements Serializable {

	private static final long serialVersionUID = -2018986984674777193L;

	private Long lotteryId;
	private String awardId;
	private Integer directRet;
	private Integer threeoneRet;
	private Integer updateType;
	private List<AwardBonusStruc> awardBonusStrucList;
	
	public EditGameAwardGroupDTO() {
		
	}

	public Long getLotteryId() {
		return lotteryId;
	}

	public void setLotteryId(Long lotteryId) {
		this.lotteryId = lotteryId;
	}

	public String getAwardId() {
		return awardId;
	}

	public void setAwardId(String awardId) {
		this.awardId = awardId;
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

	public Integer getUpdateType() {
		return updateType;
	}

	public void setUpdateType(Integer updateType) {
		this.updateType = updateType;
	}

	public List<AwardBonusStruc> getAwardBonusStrucList() {
		return awardBonusStrucList;
	}

	public void setAwardBonusStrucList(List<AwardBonusStruc> awardBonusStrucList) {
		this.awardBonusStrucList = awardBonusStrucList;
	}
	
}
