/**
 * Copyright (c) 2005-2012 winterframework.com
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.winterframework.firefrog.user.dao.vo;

import java.util.Date;

import com.winterframework.orm.dal.ibatis3.BaseEntity;

/**
 * @author cms group
 * @version 1.0
 * @since 1.0
 */

public class UserCustomer extends BaseEntity {

	private static final long serialVersionUID = -6622041217973952498L;
	// alias
	public static final String TABLE_ALIAS = "UserCustomer";
	public static final String ALIAS_ACCOUNT = "用户名 6-16位";
	public static final String ALIAS_PASSWD = "密码，因为是md5加密，所以长度为32";
	public static final String ALIAS_PASSWD_LVL = "密码级别";
	public static final String ALIAS_WITHDRAW_PASSWD = "安全密码，md5加密，长度32";
	public static final String ALIAS_CIPHER = "暗号";
	public static final String ALIAS_SEX = "性别 0：男 1：女";
	public static final String ALIAS_EMAIL = "邮箱";
	public static final String ALIAS_EMAIL_ACTIVED = "邮箱是否激活";
	public static final String ALIAS_CELLPHONE = "手机";
	public static final String ALIAS_BIRTHDAY = "生日";
	public static final String ALIAS_QQ_STRUC = "[{\"qq\":1,\"nickName\":\"aaaa\"},{\"qq\":1,\"nickName\":\"aaaa\"}]";
	public static final String ALIAS_IS_FREEZE = "是否冻结 1：冻结 0：未冻结";
	public static final String ALIAS_USER_LVL = "-1: user 0:top agent 1-10:common agent";
	public static final String ALIAS_QU_STRUC = "[{\"qu\":1,\"ans\":\"aaaa\"},{\"qu\":1,\"ans\":\"aaaa\"}]";
	public static final String ALIAS_WITHDRAW_PASSWD_ACTIVE_DATE = "安全密码生效时间";
	public static final String ALIAS_QUESTION_STRUCTURE_ACTIVE_DATE = "安全问题激活日期";
	public static final String ALIAS_REGISTER_IP = "注册时候的ip";
	public static final String ALIAS_REGISTER_DATE = "注册日期";
	public static final String ALIAS_PARENT_ID = "上级id";
	public static final String ALIAS_USER_CHAIN = "/12/13/233/333 代理/结尾，普通用户不用斜线结尾";
	public static final String ALIAS_LAST_LOGIN_DATE = "最后登录日期";
	public static final String ALIAS_TERM_A_COUNT = "团队用户数量";
	public static final String ALIAS_FREEZE_DATE = "冻结日期";
	public static final String ALIAS_FREEZER = "冻结人";
	public static final String ALIAS_VIP_CELLPHONE = "VIP电话";
	public static final String ALIAS_TERM_U_ACCOUNT = "团队代理数量";
	public static final String ALIAS_AGENT_LIMIT = "总代开户配额";
	public static final String ALIAS_FREEZE_METHOD = "冻结方式";
	public static final String ALIAS_LAST_LOGIN_IP = "最后登录ip";
	public static final String ALIAS_FREEZE_MEMO = "冻结原因";
	public static final String ALIAS_AWARD_RET_STATUS = "奖金返点模式状态";

	// date formats
	public static final String FORMAT_FREEZE_DATE = DATE_TIME_FORMAT;

	// 用户中心错误码
	public static final Long LOGIN_FAIL_REASON_USER_NOT_EXIST = 101004L;// 用户不存在
	public static final Long LOGIN_FAIL_REASON_PWD_ERROR = 101001L;// 密码错误
	public static final Long LOGIN_FAIL_REASON_USER_FREEZE = 102004L;// 用户冻结
	public static final Long LOGIN_FAIL_REASON_USER_DUPLICATE = 101002L;// 用户名重复
	public static final Long USER_EMAIL_EXSITS = 102008L;// 邮箱已存在

	public static final int FREEZE_YES = 1;//已冻结
	public static final int FREEZE_NO = 0;//未冻结
	public static final String SOURCE = "4.0"; //用户来源

	// columns START
	private String account;
	private String passwd;
	private Integer passwdLvl;
	private String withdrawPasswd;
	private String cipher;
	private Integer sex;
	private String email;
	private Integer emailActived;
	private String cellphone;
	private Date birthday;
	private String qqStruc;
	private Integer isFreeze;
	private Integer userLvl;
	private String quStruc;
	private Date withdrawPasswdActiveDate;
	private Date questionStructureActiveDate;
	private Long registerIp;
	private Date registerDate;
	private Long parentId;
	private String userChain;
	private Date lastLoginDate;
	private Long termAcount;
	private Date freezeDate;
	private Long freezer;
	private String vipCellphone;
	private Long termUaccount;
	private Long agentLimit;
	private Long freezeMethod;
	private Long lastLoginIp;
	private String freezeMemo;
	private String freezeAccount;
	private Long freezeId;
	private Long urlId;
	private String referer;
	private Long vipLvl;
	private Long sumBal;
	private Long bal;
	private String source;
	
	private Integer awardRetStatus;	
	private Integer superPairStatus;	//超级对子开关
	private Integer lhcStatus;

	private String nickName;
	private String headImg;

	// columns END

	public Long getFreezeId() {
		return freezeId;
	}

	public Long getUrlId() {
		return urlId;
	}

	public void setUrlId(Long urlId) {
		this.urlId = urlId;
	}

	public String getReferer() {
		return referer;
	}

	public void setReferer(String referer) {
		this.referer = referer;
	}

	public Long getVipLvl() {
		return vipLvl;
	}

	public void setVipLvl(Long vipLvl) {
		this.vipLvl = vipLvl;
	}

	public Long getSumBal() {
		return sumBal;
	}

	public void setSumBal(Long sumBal) {
		this.sumBal = sumBal;
	}

	public Long getBal() {
		return bal;
	}

	public void setBal(Long bal) {
		this.bal = bal;
	}

	public UserCustomer() {
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
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

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public String getQqStruc() {
		return qqStruc;
	}

	public void setQqStruc(String qqStruc) {
		this.qqStruc = qqStruc;
	}

	public Integer getIsFreeze() {
		return isFreeze;
	}

	public void setIsFreeze(Integer isFreeze) {
		this.isFreeze = isFreeze;
	}

	public Integer getUserLvl() {
		return userLvl;
	}

	public void setUserLvl(Integer userLvl) {
		this.userLvl = userLvl;
	}

	public String getQuStruc() {
		return quStruc;
	}

	public void setQuStruc(String quStruc) {
		this.quStruc = quStruc;
	}

	public Date getWithdrawPasswdActiveDate() {
		return withdrawPasswdActiveDate;
	}

	public void setWithdrawPasswdActiveDate(Date withdrawPasswdActiveDate) {
		this.withdrawPasswdActiveDate = withdrawPasswdActiveDate;
	}

	public Date getQuestionStructureActiveDate() {
		return questionStructureActiveDate;
	}

	public void setQuestionStructureActiveDate(Date questionStructureActiveDate) {
		this.questionStructureActiveDate = questionStructureActiveDate;
	}

	public Long getRegisterIp() {
		return registerIp;
	}

	public void setRegisterIp(Long registerIp) {
		this.registerIp = registerIp;
	}

	public Date getRegisterDate() {
		return registerDate;
	}

	public void setRegisterDate(Date registerDate) {
		this.registerDate = registerDate;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public String getUserChain() {
		return userChain;
	}

	public void setUserChain(String userChain) {
		this.userChain = userChain;
	}

	public Date getLastLoginDate() {
		return lastLoginDate;
	}

	public void setLastLoginDate(Date lastLoginDate) {
		this.lastLoginDate = lastLoginDate;
	}

	public Long getTermAcount() {
		return termAcount;
	}

	public void setTermAcount(Long termAcount) {
		this.termAcount = termAcount;
	}

	public Date getFreezeDate() {
		return freezeDate;
	}

	public void setFreezeDate(Date freezeDate) {
		this.freezeDate = freezeDate;
	}

	public Long getFreezer() {
		return freezer;
	}

	public void setFreezer(Long freezer) {
		this.freezer = freezer;
	}

	public String getVipCellphone() {
		return vipCellphone;
	}

	public void setVipCellphone(String vipCellphone) {
		this.vipCellphone = vipCellphone;
	}

	public Long getTermUaccount() {
		return termUaccount;
	}

	public void setTermUaccount(Long termUaccount) {
		this.termUaccount = termUaccount;
	}

	public Long getAgentLimit() {
		return agentLimit;
	}

	public void setAgentLimit(Long agentLimit) {
		this.agentLimit = agentLimit;
	}

	public Long getFreezeMethod() {
		return freezeMethod;
	}

	public void setFreezeMethod(Long freezeMethod) {
		this.freezeMethod = freezeMethod;
	}

	public Long getLastLoginIp() {
		return lastLoginIp;
	}

	public void setLastLoginIp(Long lastLoginIp) {
		this.lastLoginIp = lastLoginIp;
	}

	public String getFreezeMemo() {
		return freezeMemo;
	}

	public void setFreezeMemo(String freezeMemo) {
		this.freezeMemo = freezeMemo;
	}

	public static String getAliasPasswdLvl() {
		return ALIAS_PASSWD_LVL;
	}

	public static String getAliasQqStruc() {
		return ALIAS_QQ_STRUC;
	}

	public static String getAliasQuStruc() {
		return ALIAS_QU_STRUC;
	}

	public static String getAliasQuestionStructureActiveDate() {
		return ALIAS_QUESTION_STRUCTURE_ACTIVE_DATE;
	}

	public static String getAliasRegisterDate() {
		return ALIAS_REGISTER_DATE;
	}

	public String getFreezeAccount() {
		return freezeAccount;
	}

	public void setFreezeAccount(String freezeAccount) {
		this.freezeAccount = freezeAccount;
	}

	public void setFreezeId(Long freezeId) {
		this.freezeId = freezeId;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
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
