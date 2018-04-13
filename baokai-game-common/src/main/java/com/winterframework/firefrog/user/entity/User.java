package com.winterframework.firefrog.user.entity;

import java.util.List;

import com.winterframework.orm.dal.ibatis3.BaseEntity;

public class User extends BaseEntity {

	private static final long serialVersionUID = 2722188025205115773L;

	private User parent;

	private boolean isFreeze;
	private Long freezeId;
	private UserProfile userProfile;
	private FreezeLog freezeLog;
	private UserFreezeInfo userFreeze;

	private LoginLog lastLoginLog;
	private Integer userLevel;
	private Integer vipLvl;
	private List<LoginLog> loginHistory;
	private Integer awardRetStatus;	//奖金返点模式状态（未开启、已开启）
	private Integer superPairStatus;	//超级对子状态（未开启、已开启）
	private Integer lhcStatus;
	private String nickName;
	private String headImg;
	
	public Integer getUserLevel() {
		return userLevel;
	}

	public void setUserLevel(Integer userLevel) {
		this.userLevel = userLevel;
	}

	public User getParent() {
		return parent;
	}

	public void setParent(User parent) {
		this.parent = parent;
	}

	public UserProfile getUserProfile() {
		return userProfile;
	}

	public void setUserProfile(UserProfile userProfile) {
		this.userProfile = userProfile;
	}

	public Integer getVipLvl() {
		return vipLvl;
	}

	public void setVipLvl(Integer vipLvl) {
		this.vipLvl = vipLvl;
	}

	public UserFreezeInfo getUserFreeze() {
		return userFreeze;
	}

	public void setUserFreeze(UserFreezeInfo userFreeze) {
		this.userFreeze = userFreeze;
	}

	public LoginLog getLastLoginLog() {
		return lastLoginLog;
	}

	public void setLastLoginLog(LoginLog lastLoginLog) {
		this.lastLoginLog = lastLoginLog;
	}

	public List<LoginLog> getLoginHistory() {
		return loginHistory;
	}

	public void setLoginHistory(List<LoginLog> loginHistory) {
		this.loginHistory = loginHistory;
	}

	public boolean isFreeze() {
		return isFreeze;
	}

	public void setFreeze(boolean isFreeze) {
		this.isFreeze = isFreeze;
	}

	public Long getFreezeId() {
		return freezeId;
	}

	public void setFreezeId(Long freezeId) {
		this.freezeId = freezeId;
	}

	public FreezeLog getFreezeLog() {
		return freezeLog;
	}

	public void setFreezeLog(FreezeLog freezeLog) {
		this.freezeLog = freezeLog;
	}

	public Integer getAwardRetStatus() {
		return awardRetStatus;
	}

	public void setAwardRetStatus(Integer awardRetStatus) {
		this.awardRetStatus = awardRetStatus;
	}

	public Integer getSuperPairStatus() {
		return superPairStatus;
	}

	public void setSuperPairStatus(Integer superPairStatus) {
		this.superPairStatus = superPairStatus;
	}

	public Integer getLhcStatus() {
		return lhcStatus;
	}

	public void setLhcStatus(Integer lhcStatus) {
		this.lhcStatus = lhcStatus;
	}
	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getHeadImg() {
		return headImg;
	}

	public void setHeadImg(String headImg) {
		this.headImg = headImg;
	}
	
}
