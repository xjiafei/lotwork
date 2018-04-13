package com.winterframework.firefrog.acl.web.interceptor;

import java.io.Serializable;
import java.util.List;

import com.winterframework.firefrog.acl.web.dto.LoginGameGroup;
import com.winterframework.firefrog.acl.web.dto.QQStruc;
import com.winterframework.firefrog.acl.web.dto.QuStrucResponse;
import com.winterframework.modules.web.util.IUser;

public class UserStrucResponse implements  IUser , Serializable {

	private static final long serialVersionUID = 8523501884440880379L;
	private String account;
	private Long id;
	private Integer userLvl;
	private Integer vipLvl;
	private String userChain;
	private String passwd;
	private Integer passwdLvl;
	private Integer isFreeze;
	private String withdrawPasswd;
	private String cipher;

	private Long freezer;
	private Long freezeDate;
	private String freezeMemo;
	private Integer sex;
	private String email;
	private Integer emailActived;
	private String cellphone;
	private Long birthday;
	private List<QQStruc> qqStruc;
	private List<QuStrucResponse> quStruc;
	private Long parentId;
	private Long registerDate;
	private Long registerIp;
	private Long lastLoginDate;
	private Integer teamACount;
	private Integer teamUCount;
	private String vipCellphone;
	private Long freezeMethod;
	private Integer agentlimit;
	private String freezeAccount;
	private String registerAddress;
	private Long questionStructureActiveDate;
	private List<LoginGameGroup> loginGameGroups;
	private Long isAllZero;
	private Long availBal;
	private Long freezeBal;
	private Long teamBal;
	private Integer lhcStatus;
	

	public Long getIsAllZero() {
		return isAllZero;
	}

	public void setIsAllZero(Long isAllZero) {
		this.isAllZero = isAllZero;
	}

	public Long getTeamBal() {
		return teamBal;
	}

	public void setTeamBal(Long teamBal) {
		this.teamBal = teamBal;
	}

	public Long getQuestionStructureActiveDate() {
		return questionStructureActiveDate;
	}

	public List<LoginGameGroup> getLoginGameGroups() {
		return loginGameGroups;
	}

	public void setLoginGameGroups(List<LoginGameGroup> loginGameGroups) {
		this.loginGameGroups = loginGameGroups;
	}

	public void setQuestionStructureActiveDate(Long questionStructureActiveDate) {
		this.questionStructureActiveDate = questionStructureActiveDate;
	}

	public UserStrucResponse() {

	}

	public Integer getVipLvl() {
		return vipLvl;
	}

	public void setVipLvl(Integer vipLvl) {
		this.vipLvl = vipLvl;
	}

	public String getRegisterAddress() {
		return registerAddress;
	}

	public void setRegisterAddress(String registerAddress) {
		this.registerAddress = registerAddress;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getUserLvl() {
		return userLvl;
	}

	public void setUserLvl(Integer userLvl) {
		this.userLvl = userLvl;
	}

	public String getUserChain() {
		return userChain;
	}

	public void setUserChain(String userChain) {
		this.userChain = userChain;
	}

	public String getPasswd() {
		return passwd;
	}

	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}

	public Integer getPasswdLvl() {
		return passwdLvl;
	}

	public void setPasswdLvl(Integer passwdLvl) {
		this.passwdLvl = passwdLvl;
	}

	public Integer getIsFreeze() {
		return isFreeze;
	}

	public void setIsFreeze(Integer isFreeze) {
		this.isFreeze = isFreeze;
	}

	public String getWithdrawPasswd() {
		return withdrawPasswd;
	}

	public void setWithdrawPasswd(String withdrawPasswd) {
		this.withdrawPasswd = withdrawPasswd;
	}

	public String getCipher() {
		return cipher;
	}

	public void setCipher(String cipher) {
		this.cipher = cipher;
	}

	

	public Long getAvailBal() {
		return availBal;
	}

	public void setAvailBal(Long availBal) {
		this.availBal = availBal;
	}

	public Long getFreezeBal() {
		return freezeBal;
	}

	public void setFreezeBal(Long freezeBal) {
		this.freezeBal = freezeBal;
	}

	public Long getFreezer() {
		return freezer;
	}

	public void setFreezer(Long freezer) {
		this.freezer = freezer;
	}

	public Long getFreezeDate() {
		return freezeDate;
	}

	public void setFreezeDate(Long freezeDate) {
		this.freezeDate = freezeDate;
	}

	public String getFreezeMemo() {
		return freezeMemo;
	}

	public void setFreezeMemo(String freezeMemo) {
		this.freezeMemo = freezeMemo;
	}

	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Integer getEmailActived() {
		return emailActived;
	}

	public void setEmailActived(Integer emailActived) {
		this.emailActived = emailActived;
	}

	public String getCellphone() {
		return cellphone;
	}

	public void setCellphone(String cellphone) {
		this.cellphone = cellphone;
	}

	public Long getBirthday() {
		return birthday;
	}

	public void setBirthday(Long birthday) {
		this.birthday = birthday;
	}

	public List<QQStruc> getQqStruc() {
		return qqStruc;
	}

	public void setQqStruc(List<QQStruc> qqStruc) {
		this.qqStruc = qqStruc;
	}

	public List<QuStrucResponse> getQuStruc() {
		return quStruc;
	}

	public void setQuStruc(List<QuStrucResponse> quStruc) {
		this.quStruc = quStruc;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public Long getRegisterDate() {
		return registerDate;
	}

	public void setRegisterDate(Long registerDate) {
		this.registerDate = registerDate;
	}

	public Long getRegisterIp() {
		return registerIp;
	}

	public void setRegisterIp(Long registerIp) {
		this.registerIp = registerIp;
	}

	public Long getLastLoginDate() {
		return lastLoginDate;
	}

	public void setLastLoginDate(Long lastLoginDate) {
		this.lastLoginDate = lastLoginDate;
	}

	public Integer getTeamACount() {
		return teamACount;
	}

	public void setTeamACount(Integer teamACount) {
		this.teamACount = teamACount;
	}

	public Integer getTeamUCount() {
		return teamUCount;
	}

	public void setTeamUCount(Integer teamUCount) {
		this.teamUCount = teamUCount;
	}

	public String getVipCellphone() {
		return vipCellphone;
	}

	public void setVipCellphone(String vipCellphone) {
		this.vipCellphone = vipCellphone;
	}

	public Long getFreezeMethod() {
		return freezeMethod;
	}

	public Integer getAgentlimit() {
		return agentlimit;
	}

	public void setAgentlimit(Integer agentlimit) {
		this.agentlimit = agentlimit;
	}

	public void setFreezeMethod(Long freezeMethod) {
		this.freezeMethod = freezeMethod;
	}

	public String getFreezeAccount() {
		return freezeAccount;
	}

	public void setFreezeAccount(String freezeAccount) {
		this.freezeAccount = freezeAccount;
	}

	@Override
	public String getUserName() {
		// TODO Auto-generated method stub
		return this.getAccount();
	}

	@Override
	public boolean IsBlocked() {
		// TODO Auto-generated method stub
		return this.IsBlocked();
	}

	public Integer getLhcStatus() {
		return lhcStatus;
	}

	public void setLhcStatus(Integer lhcStatus) {
		this.lhcStatus = lhcStatus;
	}

}
