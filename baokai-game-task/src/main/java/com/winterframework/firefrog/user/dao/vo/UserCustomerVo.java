package com.winterframework.firefrog.user.dao.vo;

import java.util.Date;

import com.winterframework.orm.dal.ibatis3.BaseEntity;

public class UserCustomerVo extends BaseEntity {

	private static final long serialVersionUID = 4391360208964183516L;

	private Long id;
	private String ptAccount;// PT帳號
	private Long ffId;// FF系統UserId
	private String ptPasswd;// PT密碼
	private String ffAccount;// FF系統帳號
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
	private Long agentType;// 0 未開通 1返点代理、2 分红代理3 全部代理
	private Integer userLvl;
	private Integer active;// 活耀用戶
	private Integer activeTime;// 1 = 次月生效, 2 = 即時生效
	private Long subUserCount;// 下級人數
	private String gmtRegisterStart;// 建立日期起
	private String gmtRegisterEnd;// 建立日期迄
	private String gmtLoginStart;// 登入時間起
	private String gmtLoginEnd;// 登入時間迄
	private Long balance;
	private Integer isModify;// 1 = 月生效, 2 = 即時生效
	private Integer currentStatus;
	private Date gmtEffect;
	private Integer startNo;// 起頁
	private Integer endNo;// 迄頁
	private Integer vip;
	private Long isRet;// 返點,紅利
	private Long isWhiteList;// 1 = 是名單 , 2 = 否
	private Long isConfigPwd;

	public Long getIsConfigPwd() {
		return isConfigPwd;
	}

	public void setIsConfigPwd(Long isConfigPwd) {
		this.isConfigPwd = isConfigPwd;
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

	public Integer getActiveTime() {
		return activeTime;
	}

	public void setActiveTime(Integer activeTime) {
		this.activeTime = activeTime;
	}

	public Long getSubUserCount() {
		return subUserCount;
	}

	public void setSubUserCount(Long subUserCount) {
		this.subUserCount = subUserCount;
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

	public String getGmtLoginStart() {
		return gmtLoginStart;
	}

	public void setGmtLoginStart(String gmtLoginStart) {
		this.gmtLoginStart = gmtLoginStart;
	}

	public String getGmtLoginEnd() {
		return gmtLoginEnd;
	}

	public void setGmtLoginEnd(String gmtLoginEnd) {
		this.gmtLoginEnd = gmtLoginEnd;
	}

	public Long getBalance() {
		return balance;
	}

	public void setBalance(Long balance) {
		this.balance = balance;
	}

	public Integer getIsModify() {
		return isModify;
	}

	public void setIsModify(Integer isModify) {
		this.isModify = isModify;
	}

	public Integer getCurrentStatus() {
		return currentStatus;
	}

	public void setCurrentStatus(Integer currentStatus) {
		this.currentStatus = currentStatus;
	}

	public Date getGmtEffect() {
		return gmtEffect;
	}

	public void setGmtEffect(Date gmtEffect) {
		this.gmtEffect = gmtEffect;
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

	public Integer getVip() {
		return vip;
	}

	public void setVip(Integer vip) {
		this.vip = vip;
	}

	public Long getIsRet() {
		return isRet;
	}

	public void setIsRet(Long isRet) {
		this.isRet = isRet;
	}

	public Long getIsWhiteList() {
		return isWhiteList;
	}

	public void setIsWhiteList(Long isWhiteList) {
		this.isWhiteList = isWhiteList;
	}
}
