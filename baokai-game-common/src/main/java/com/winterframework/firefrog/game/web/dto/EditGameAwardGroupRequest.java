package com.winterframework.firefrog.game.web.dto;

import java.io.Serializable;
import java.util.List;

import javax.validation.constraints.NotNull;
/**
 * 
* @ClassName: EditGameAwardGroupRequest 
* @Description: 奖金组修改请求
* @author Richard
* @date 2013-8-16 上午11:07:53 
*
 */
public class EditGameAwardGroupRequest implements Serializable {

	private static final long serialVersionUID = -505345610907952712L;
	
	@NotNull
	private Long lotteryId;
	@NotNull
	private String awardId;
	@NotNull
	private Integer directRet;
	@NotNull
	private Integer threeoneRet;
	@NotNull
	private Integer updateType;
	@NotNull
	private List<AwardBonusStruc> awardBonusStrucList;
	
	public EditGameAwardGroupRequest() {
		
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
