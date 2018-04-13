package com.winterframework.firefrog.phone.web.cotroller.dto;

import java.io.Serializable;
import java.util.List;

public class UserStrucResponse implements Serializable {

	private static final long serialVersionUID = 8523501884440880379L;
	private String source;
	private String account;
	private Long id;
	private Integer userLvl; //-1 不能开户，
	private Integer vipLvl;
	private String userChain;
	private String serialNumber;
	private String passwd;
	private Integer passwdLvl;
	private Integer isFreeze;
	private String withdrawPasswd;
	private String cipher;
	private Long isAllZero=1L; //为0时不能开户

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
	private Long teamACount;
	private Long teamUCount;
	private String vipCellphone;
	private Long freezeMethod;
	private Long agentlimit;
	private String freezeAccount;
	private String registerAddress;
	private Long questionStructureActiveDate;
	private List<LoginGameGroup> loginGameGroups;
	private RegisterLoginConfigDto loginCtrl;
	private List<UserStrucResponse> subUsers;
	

	
	public String getSource() {
		return source;
	}


	public void setSource(String source) {
		this.source = source;
	}


	public Long getIsAllZero() {
		return isAllZero;
	}


	public void setIsAllZero(Long isAllZero) {
		this.isAllZero = isAllZero;
	}


	public List<UserStrucResponse> getSubUsers() {
		return subUsers;
	}


	public void setSubUsers(List<UserStrucResponse> subUsers) {
		this.subUsers = subUsers;
	}


	public RegisterLoginConfigDto getLoginCtrl() {
		return loginCtrl;
	}


	public void setLoginCtrl(RegisterLoginConfigDto loginCtrl) {
		this.loginCtrl = loginCtrl;
	}

	private Long availBal;
	private Long canWithdrawBal;
	private Long freezeBal;
	private Long teamBal;
	

	public Long getCanWithdrawBal() {
		return canWithdrawBal;
	}


	public void setCanWithdrawBal(Long canWithdrawBal) {
		this.canWithdrawBal = canWithdrawBal;
	}


	public Long getTeamBal() {
		return teamBal;
	}
	

	public String getSerialNumber() {
		return serialNumber;
	}


	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
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

	

	public Long getTeamACount() {
		return teamACount;
	}


	public void setTeamACount(Long teamACount) {
		this.teamACount = teamACount;
	}


	public Long getTeamUCount() {
		return teamUCount;
	}


	public void setTeamUCount(Long teamUCount) {
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

	public Long getAgentlimit() {
		return agentlimit;
	}

	public void setAgentlimit(Long agentlimit) {
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


}
