package com.winterframework.firefrog.game.web.dto;

import java.io.Serializable;
import java.util.List;

import javax.validation.constraints.NotNull;

/**
 * 
* @ClassName: CreateGameAwardGroupRequest 
* @Description: 新增用户奖金组请求 
* @author Richard
* @date 2013-8-16 上午10:31:44 
*
 */
public class CreateGameAwardGroupRequest implements Serializable {

	private static final long serialVersionUID = -1376213374001568674L;

	@NotNull
	private Long lotteryId;
	@NotNull
	private String awardName;
	@NotNull
	private Integer directRet;
	@NotNull
	private Integer threeoneRet;
	@NotNull
	private List<AwardBonusStruc> awardBonusStrucList;
	
	public CreateGameAwardGroupRequest(){
		
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
