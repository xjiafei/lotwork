package com.winterframework.firefrog.user.entity;

import java.util.Date;

import com.winterframework.orm.dal.ibatis3.BaseEntity;

public class GameAwardUserGroupVo extends BaseEntity {

	private static final long serialVersionUID = 4391360208964183516L;

	private Long id;
	private Long lotteryId;
	private Long directRet;
	private Long threeoneRet;
	private Date createTime;
	private Date updateTime;
	private Long userId;
	private Long setType;
	private Long status;
	private Long betType;
	private Long sysGroupAwardId;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getLotteryId() {
		return lotteryId;
	}
	public void setLotteryId(Long lotteryId) {
		this.lotteryId = lotteryId;
	}
	public Long getDirectRet() {
		return directRet;
	}
	public void setDirectRet(Long directRet) {
		this.directRet = directRet;
	}
	public Long getThreeoneRet() {
		return threeoneRet;
	}
	public void setThreeoneRet(Long threeoneRet) {
		this.threeoneRet = threeoneRet;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public Long getSetType() {
		return setType;
	}
	public void setSetType(Long setType) {
		this.setType = setType;
	}
	public Long getStatus() {
		return status;
	}
	public void setStatus(Long status) {
		this.status = status;
	}
	public Long getBetType() {
		return betType;
	}
	public void setBetType(Long betType) {
		this.betType = betType;
	}
	public Long getSysGroupAwardId() {
		return sysGroupAwardId;
	}
	public void setSysGroupAwardId(Long sysGroupAwardId) {
		this.sysGroupAwardId = sysGroupAwardId;
	}
	
	

}
