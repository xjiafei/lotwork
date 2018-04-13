package com.winterframework.firefrog.phone.web.cotroller.dto;

import java.io.Serializable;

public class FrontResponse implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5889354041819283732L;
	private Long userid; //	用户id
	private String username;//	用户名
	private String nickname;//	用户昵称
	private String language;//	用户指定语言
	private Long userrank;//	用户星级
	private Long frozentype;//	冻结类型
	private Long isfrozen;//	是否冻结账户
	private Long istester;//	是否为测试账号
	private String token;//	登陆令牌，用来保持会话
	private Long agentType; //	用户类型
	private Boolean needSetSecurityPass; //	是否设定资金密码
	private String openlink;//	链结开户权限
	private Long isvip;//	是否为vip用户
	private Boolean needSetSafeQuest;
	private Integer unread;
	private String source; //如果是3.0 则是老用户
	private Boolean isTrain;
	public Boolean getIsTrain() {
		return isTrain;
	}
	public void setIsTrain(Boolean isTrain) {
		this.isTrain = isTrain;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public Integer getUnread() {
		return unread;
	}
	public void setUnread(Integer unread) {
		this.unread = unread;
	}
	public Boolean getNeedSetSafeQuest() {
		return needSetSafeQuest;
	}
	public void setNeedSetSafeQuest(Boolean needSetSafeQuest) {
		this.needSetSafeQuest = needSetSafeQuest;
	}
	public Long getUserid() {
		return userid;
	}
	public void setUserid(Long userid) {
		this.userid = userid;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	public Long getUserrank() {
		return userrank;
	}
	public void setUserrank(Long userrank) {
		this.userrank = userrank;
	}
	public Long getFrozentype() {
		return frozentype;
	}
	public void setFrozentype(Long frozentype) {
		this.frozentype = frozentype;
	}
	public Long getIsfrozen() {
		return isfrozen;
	}
	public void setIsfrozen(Long isfrozen) {
		this.isfrozen = isfrozen;
	}
	public Long getIstester() {
		return istester;
	}
	public void setIstester(Long istester) {
		this.istester = istester;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public Long getAgentType() {
		return agentType;
	}
	public void setAgentType(Long agentType) {
		this.agentType = agentType;
	}
	public Boolean getNeedSetSecurityPass() {
		return needSetSecurityPass;
	}
	public void setNeedSetSecurityPass(Boolean needSetSecurityPass) {
		this.needSetSecurityPass = needSetSecurityPass;
	}
	public String getOpenlink() {
		return openlink;
	}
	public void setOpenlink(String openlink) {
		this.openlink = openlink;
	}
	public Long getIsvip() {
		return isvip;
	}
	public void setIsvip(Long isvip) {
		this.isvip = isvip;
	}
	
	
}
