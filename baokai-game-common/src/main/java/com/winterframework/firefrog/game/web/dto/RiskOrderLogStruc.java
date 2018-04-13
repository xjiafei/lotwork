package com.winterframework.firefrog.game.web.dto;

import java.io.Serializable;

/**
 * 
* @ClassName: RiskOrderLogStruc 
* @Description: 5.2.30	RISK_ORDER_LOG_STRUC（风险方案日志基本结构）CSI030
* @author Richard
* @date 2013-10-12 上午9:56:19 
*
 */
public class RiskOrderLogStruc implements Serializable {

	private static final long serialVersionUID = -5777149971249165787L;

	private Long		lotteryid;
	private Long		issueCode;
	private Long		userId;
	private String		account;
	private Long		countWins;
	private Long		issueWinsRatio;
	private Long		orderwarnContinuousWins;
	private Long		maxslipWins;
	private Integer		status;
	private String	disposeUser;
	private Long		createTime;
	private String		disposeMemo;
	
	public Long getLotteryid() {
		return lotteryid;
	}
	public void setLotteryid(Long lotteryid) {
		this.lotteryid = lotteryid;
	}
	public Long getIssueCode() {
		return issueCode;
	}
	public void setIssueCode(Long issueCode) {
		this.issueCode = issueCode;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public Long getCountWins() {
		return countWins;
	}
	public void setCountWins(Long countWins) {
		this.countWins = countWins;
	}
	public Long getIssueWinsRatio() {
		return issueWinsRatio;
	}
	public void setIssueWinsRatio(Long issueWinsRatio) {
		this.issueWinsRatio = issueWinsRatio;
	}
	public Long getOrderwarnContinuousWins() {
		return orderwarnContinuousWins;
	}
	public void setOrderwarnContinuousWins(Long orderwarnContinuousWins) {
		this.orderwarnContinuousWins = orderwarnContinuousWins;
	}
	public Long getMaxslipWins() {
		return maxslipWins;
	}
	public void setMaxslipWins(Long maxslipWins) {
		this.maxslipWins = maxslipWins;
	}
	
	/**
	 * 
	* @Title: getStatus 
	* @Description:0 待审核 1 已审核 2 未通过审核 
	* @return
	 */
	public Integer getStatus() {
		return status;
	}
	/**
	 * 
	* @Title: setStatus 
	* @Description: 0 待审核 1 已审核 2 未通过审核 
	* @param status
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getDisposeUser() {
		return disposeUser;
	}
	public void setDisposeUser(String disposeUser) {
		this.disposeUser = disposeUser;
	}
	public Long getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}
	public String getDisposeMemo() {
		return disposeMemo;
	}
	public void setDisposeMemo(String disposeMemo) {
		this.disposeMemo = disposeMemo;
	}

}
