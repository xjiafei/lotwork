package com.winterframework.firefrog.game.web.dto;

import java.io.Serializable;
import java.util.List;

/**
 * 
* @ClassName: GameAwardGroupQueryResponse 
* @Description:奖金组查询响应 
* @author Richard
* @date 2013-8-16 上午9:46:33 
*
 */
public class GameAwardGroupQueryResponse implements Serializable {

	private static final long serialVersionUID = -4497963894498193878L;

	private List<AwardListStruc> awardListStruc;
	private Long lotteryId;
	private Long awardGroupId;
	private String awardGroupName;
	private Integer directRet;
	private Integer threeoneRet;
	private Integer directLimitRet;
	private Integer threeLimitRet;
	private String lotteryName;
	
	public GameAwardGroupQueryResponse(){
		
	}

	public List<AwardListStruc> getAwardListStruc() {
		return awardListStruc;
	}

	public void setAwardListStruc(List<AwardListStruc> awardListStruc) {
		this.awardListStruc = awardListStruc;
	}

	public Long getLotteryId() {
		return lotteryId;
	}

	public void setLotteryId(Long lotteryId) {
		this.lotteryId = lotteryId;
	}

	public Long getAwardGroupId() {
		return awardGroupId;
	}

	public void setAwardGroupId(Long awardGroupId) {
		this.awardGroupId = awardGroupId;
	}

	public String getAwardGroupName() {
		return awardGroupName;
	}

	public void setAwardGroupName(String awardGroupName) {
		this.awardGroupName = awardGroupName;
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

	public Integer getDirectLimitRet() {
		return directLimitRet;
	}

	public void setDirectLimitRet(Integer directLimitRet) {
		this.directLimitRet = directLimitRet;
	}

	public Integer getThreeLimitRet() {
		return threeLimitRet;
	}

	public void setThreeLimitRet(Integer threeLimitRet) {
		this.threeLimitRet = threeLimitRet;
	}

	public String getLotteryName() {
		return lotteryName;
	}

	public void setLotteryName(String lotteryName) {
		this.lotteryName = lotteryName;
	}

}
