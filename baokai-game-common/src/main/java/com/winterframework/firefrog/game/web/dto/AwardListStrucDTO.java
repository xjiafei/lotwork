package com.winterframework.firefrog.game.web.dto;

import java.io.Serializable;

public class AwardListStrucDTO implements Serializable {

	private static final long serialVersionUID = 2417723644779126986L;

	public AwardListStrucDTO() {
		
	}
	
	private Long lotteryId;
	private Long awardGroupId;
	private String awardName;
	private Integer status;
	private String createTime;
	private String updateTime;
	private Integer sysAwardGroup;
	private Integer directRet;
	private Integer threeoneRet;

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
	public String getAwardName() {
		return awardName;
	}
	public void setAwardName(String awardName) {
		this.awardName = awardName;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	
	public Integer getSysAwardGroup() {
		return sysAwardGroup;
	}
	public void setSysAwardGroup(Integer sysAwardGroup) {
		this.sysAwardGroup = sysAwardGroup;
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
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}
	
}
