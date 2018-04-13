package com.winterframework.firefrog.user.entity;

import java.util.Date;
import java.util.List;

public class UserProfile {

	private String account;
	private Integer userLvl;

	private String password;

	private Integer passwordLevel;
	private String serialNumber;

	private String withdrawPwd;

	private String cipher;

	private Integer sex;

	private String email;

	private Integer emailActived;

	private String phone;

	private Date birthday;

	private List<QQInfo> qq;

	private List<QAInfo> qa;

	private String vipPhone;

	private Long registerIP;

	private Date registerDate;

	private Date withdrawPwdActiveDate;

	private Date qaActiveDate;

	private String userChain;
	private Long termACount;
	private Long termUCount;
	private String source;
	private String device;
	private String nickname;
	private String headImg;
	private Date nickUpdateTime;

	
	public String getDevice() {
		return device;
	}

	public void setDevice(String device) {
		this.device = device;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		
		this.account = account;
	}

	public String getPassword() {
		return password;
	}

	public Integer getUserLvl() {
		return userLvl;
	}

	public String getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	public void setUserLvl(Integer userLvl) {
		this.userLvl = userLvl;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Integer getPasswordLevel() {
		return passwordLevel;
	}

	public void setPasswordLevel(Integer passwordLevel) {
		this.passwordLevel = passwordLevel;
	}

	public String getWithdrawPwd() {
		return withdrawPwd;
	}

	public void setWithdrawPwd(String withdrawPwd) {
		this.withdrawPwd = withdrawPwd;
	}

	public String getCipher() {
		return cipher;
	}

	public void setCipher(String cipher) {
		this.cipher = cipher;
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

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public List<QQInfo> getQq() {
		return qq;
	}

	public void setQq(List<QQInfo> qq) {
		this.qq = qq;
	}

	public List<QAInfo> getQa() {
		return qa;
	}

	public void setQa(List<QAInfo> qa) {
		this.qa = qa;
	}

	public String getVipPhone() {
		return vipPhone;
	}

	public void setVipPhone(String vipPhone) {
		this.vipPhone = vipPhone;
	}

	public Long getRegisterIP() {
		return registerIP;
	}

	public void setRegisterIP(Long registerIP) {
		this.registerIP = registerIP;
	}

	public Date getRegisterDate() {
		return registerDate;
	}

	public void setRegisterDate(Date registerDate) {
		this.registerDate = registerDate;
	}

	public Date getWithdrawPwdActiveDate() {
		return withdrawPwdActiveDate;
	}

	public void setWithdrawPwdActiveDate(Date withdrawPwdActiveDate) {
		this.withdrawPwdActiveDate = withdrawPwdActiveDate;
	}

	public Date getQaActiveDate() {
		return qaActiveDate;
	}

	public void setQaActiveDate(Date qaActiveDate) {
		this.qaActiveDate = qaActiveDate;
	}

	public String getUserChain() {
		return userChain;
	}

	public void setUserChain(String userChain) {
		this.userChain = userChain;
	}

	public Long getTermACount() {
		return termACount;
	}

	public void setTermACount(Long termACount) {
		this.termACount = termACount;
	}

	public Long getTermUCount() {
		return termUCount;
	}

	public void setTermUCount(Long termUCount) {
		this.termUCount = termUCount;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getHeadImg() {
		return headImg;
	}

	public void setHeadImg(String headImg) {
		this.headImg = headImg;
	}

	public Date getNickUpdateTime() {
		return nickUpdateTime;
	}

	public void setNickUpdateTime(Date nickUpdateTime) {
		this.nickUpdateTime = nickUpdateTime;
	}
	
}
