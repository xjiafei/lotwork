package com.winterframework.firefrog.user.entity;

import java.util.Date;

import com.winterframework.orm.dal.ibatis3.BaseEntity;

public class UserCustomerRequestVo extends BaseEntity {

	private static final long serialVersionUID = 4391360208964183516L;

	private Long id;
	private String ptAccount;// PT帳號
	private String ptAccountBlurry;
	private Long ffId;// FF系統UserId
	private String ptPasswd;// PT密碼
	private String ffAccount;// FF系統帳號
	private String ffAccountBlurry;
	private Date gmtRegister;// 建立日期
	private Long ffParentId;
	private Long status;// 狀態
	private String gameList;// 遊戲列表
	private Long availBal;// 可用金額
	private Long subId;
	private Date gmtLogin;// 登入時間
	private Date gmtUpdate;// 修改時間
	private Long registerIP;// 註冊 IP
	private Long ptBalMin;// PT餘額最大值
	private Long ptBalMax;// PT餘額最小值
	private String orderStr;// 排序
	private Long agentType;
	private Integer userLvl;
	private Integer active;
	private String gmtRegisterStart;// 建立日期起
	private String gmtRegisterEnd;// 建立日期迄
	private Date gmtLoginStart;// 登入時間起
	private Date gmtLoginEnd;// 登入時間迄
	private Integer isInclude;
	private Integer startNo;// 起頁
	private Integer endNo;// 迄頁
	private Integer subusercount; // 下級數量
	private Long isRet;// 返點,紅利
	private Long balance;// PT balance
	private Long isWhiteList;
	private Long isConfigPwd;

	public String getPtAccountBlurry() {
		return ptAccountBlurry;
	}

	public void setPtAccountBlurry(String ptAccountBlurry) {
		this.ptAccountBlurry = ptAccountBlurry;
	}

	public String getFfAccountBlurry() {
		return ffAccountBlurry;
	}

	public void setFfAccountBlurry(String ffAccountBlurry) {
		this.ffAccountBlurry = ffAccountBlurry;
	}

	public Long getIsConfigPwd() {
		return isConfigPwd;
	}

	public void setIsConfigPwd(Long isConfigPwd) {
		this.isConfigPwd = isConfigPwd;
	}

	public Long getIsWhiteList() {
		return isWhiteList;
	}

	public void setIsWhiteList(Long isWhiteList) {
		this.isWhiteList = isWhiteList;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPtAccount() {
		return ptAccount;
	}

	public void setPtAccount(String ptAccount) {
		this.ptAccount = ptAccount;
	}

	public Long getFfId() {
		return ffId;
	}

	public void setFfId(Long ffId) {
		this.ffId = ffId;
	}

	public String getPtPasswd() {
		return ptPasswd;
	}

	public void setPtPasswd(String ptPasswd) {
		this.ptPasswd = ptPasswd;
	}

	public String getFfAccount() {
		return ffAccount;
	}

	public void setFfAccount(String ffAccount) {
		this.ffAccount = ffAccount;
	}

	public Date getGmtRegister() {
		return gmtRegister;
	}

	public void setGmtRegister(Date gmtRegister) {
		this.gmtRegister = gmtRegister;
	}

	public Long getFfParentId() {
		return ffParentId;
	}

	public void setFfParentId(Long ffParentId) {
		this.ffParentId = ffParentId;
	}

	public Long getStatus() {
		return status;
	}

	public void setStatus(Long status) {
		this.status = status;
	}

	public String getGameList() {
		return gameList;
	}

	public void setGameList(String gameList) {
		this.gameList = gameList;
	}

	public Long getAvailBal() {
		return availBal;
	}

	public void setAvailBal(Long availBal) {
		this.availBal = availBal;
	}

	public Long getSubId() {
		return subId;
	}

	public void setSubId(Long subId) {
		this.subId = subId;
	}

	public Date getGmtLogin() {
		return gmtLogin;
	}

	public void setGmtLogin(Date gmtLogin) {
		this.gmtLogin = gmtLogin;
	}

	public Date getGmtUpdate() {
		return gmtUpdate;
	}

	public void setGmtUpdate(Date gmtUpdate) {
		this.gmtUpdate = gmtUpdate;
	}

	public Long getRegisterIP() {
		return registerIP;
	}

	public void setRegisterIP(Long registerIP) {
		this.registerIP = registerIP;
	}

	public Long getPtBalMin() {
		return ptBalMin;
	}

	public void setPtBalMin(Long ptBalMin) {
		this.ptBalMin = ptBalMin;
	}

	public Long getPtBalMax() {
		return ptBalMax;
	}

	public void setPtBalMax(Long ptBalMax) {
		this.ptBalMax = ptBalMax;
	}

	public String getOrderStr() {
		return orderStr;
	}

	public void setOrderStr(String orderStr) {
		this.orderStr = orderStr;
	}

	public Long getAgentType() {
		return agentType;
	}

	public void setAgentType(Long agentType) {
		this.agentType = agentType;
	}

	public Integer getUserLvl() {
		return userLvl;
	}

	public void setUserLvl(Integer userLvl) {
		this.userLvl = userLvl;
	}

	public Integer getActive() {
		return active;
	}

	public void setActive(Integer active) {
		this.active = active;
	}

	public String getGmtRegisterStart() {
		return gmtRegisterStart;
	}

	public void setGmtRegisterStart(String gmtRegisterStart) {
		this.gmtRegisterStart = gmtRegisterStart;
	}

	public String getGmtRegisterEnd() {
		return gmtRegisterEnd;
	}

	public void setGmtRegisterEnd(String gmtRegisterEnd) {
		this.gmtRegisterEnd = gmtRegisterEnd;
	}

	public Date getGmtLoginStart() {
		return gmtLoginStart;
	}

	public void setGmtLoginStart(Date gmtLoginStart) {
		this.gmtLoginStart = gmtLoginStart;
	}

	public Date getGmtLoginEnd() {
		return gmtLoginEnd;
	}

	public void setGmtLoginEnd(Date gmtLoginEnd) {
		this.gmtLoginEnd = gmtLoginEnd;
	}

	public Integer getIsInclude() {
		return isInclude;
	}

	public void setIsInclude(Integer isInclude) {
		this.isInclude = isInclude;
	}

	public Integer getStartNo() {
		return startNo;
	}

	public void setStartNo(Integer startNo) {
		this.startNo = startNo;
	}

	public Integer getEndNo() {
		return endNo;
	}

	public void setEndNo(Integer endNo) {
		this.endNo = endNo;
	}

	public Integer getSubusercount() {
		return subusercount;
	}

	public void setSubusercount(Integer subusercount) {
		this.subusercount = subusercount;
	}

	public Long getIsRet() {
		return isRet;
	}

	public void setIsRet(Long isRet) {
		this.isRet = isRet;
	}

	public Long getBalance() {
		return balance;
	}

	public void setBalance(Long balance) {
		this.balance = balance;
	}
}
