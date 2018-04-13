package com.winterframework.firefrog.user.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.winterframework.firefrog.fund.entity.UserFund;
import com.winterframework.orm.dal.ibatis3.BaseEntity;

public class User extends BaseEntity implements BaseUser {

	private static final long serialVersionUID = 2722188025205115773L;

	private User parent;

	private boolean isFreeze;
	private Long freezeId;
	private UserProfile userProfile;
	private UserFund fund;
	private FreezeLog freezeLog;
	private UserFreezeInfo userFreeze;
    private Long agentlimit;
	private LoginLog lastLoginLog;
	private Integer userLevel;
	private Integer vipLvl;
	private List<LoginLog> loginHistory;
	private Integer awardRetStatus;	//奖金返点模式状态（未开启、已开启）
	private Integer superPairStatus;	//超级对子模式状态（未开启、已开启）
	private Long appealNewFunc;
	private Integer lhcStatus;	//六合彩模式状态（未开启、已开启）

	/**
	 * 指定IP白名單 IP清單若為空字串表示使用者不是指定IP白名單成員
	 */
	private String whiteListIpList;

	public Long getAgentlimit() {
		return agentlimit;
	}

	public void setAgentlimit(Long agentlimit) {
		this.agentlimit = agentlimit;
	}

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

	public UserFund getFund() {
		return fund;
	}

	public void setFund(UserFund fund) {
		this.fund = fund;
	}

	@Override
	public String getAccount() {
		if (this.getUserProfile() == null) {
			return "-1";
		} else {
			return this.getUserProfile().getAccount();
		}
	}

	@Override
	public void setAccount(String account) {
		if(this.getUserProfile()==null){
			this.setUserProfile(new UserProfile());
		}
		this.getUserProfile().setAccount(account);
	}

	@Override
	@JsonIgnore 
	public boolean isFrontUser() {
		// TODO Auto-generated method stub
		return true;
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

	public Long getAppealNewFunc() {
		return appealNewFunc;
	}

	public void setAppealNewFunc(Long appealNewFunc) {
		this.appealNewFunc = appealNewFunc;
	}

	public String getWhiteListIpList() {
		return whiteListIpList;
	}

	public void setWhiteListIpList(String whiteListIpList) {
		this.whiteListIpList = whiteListIpList;
	}
	
}
