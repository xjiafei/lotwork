package com.winterframework.firefrog.game.web.dto;

import java.io.Serializable;

public class PlansStruc implements Serializable {

	private static final long serialVersionUID = -6061878673854442451L;

	/** 追号编号 */
	private String planCode;
	/** 彩种 */
	private Long lotteryid;
	/**彩种名称*/
	private String lotteryName;
	/** 起始期数 */
	private Long startIssueCode;
	/** 起始Web期号 */
	private String startWebIssueCode;
	/** 已追期号 */
	private Integer finishIssue;
	/** 总期数 */
	private Integer totalIssue;
	/**取消期数*/
	private Integer cancelIssue;
	/** 已下注金额(销售完成总金额soldAmount) */
	private Long usedAmount;
	/**取消总金额*/
	private Long canceledAmount;
	/** 追号总金额 */
	private Long totamount;
	/**总奖金(计算中奖方案得到的奖金数)*/
	private Long totalWin;
	/** 停止方式：0:不停止 1:按累计盈利停止 2:中奖即停 */
	private Integer stopMode;
	/** 停止数量：累计中奖停止金额 */
	private Long stopParms;
	/** 状态 */
	private Integer status;
	/** 追号时间 */
	private Long saleTime;
	/** 追号ID */
	private Long planid;
	/**用户名*/
	private String account;
	/**用户ID*/
	private Long userid;
	/**渠道ID*/
	private Integer channelid;
	/**渠道名*/
	private String  channelName;
	/**渠道版本号*/
	private String channelVersion;
	
	private Boolean canStop;//两个条件：1 状态<2，2：当停止方式不为不停止时，没有待审核的订单

	public Boolean getCanStop() {
		return canStop;
	}

	public void setCanStop(Boolean canStop) {
		this.canStop = canStop;
	}

	public String getChannelName() {
		return channelName;
	}

	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}

	public String getPlanCode() {
		return planCode;
	}

	public void setPlanCode(String planCode) {
		this.planCode = planCode;
	}

	public Long getLotteryid() {
		return lotteryid;
	}

	public void setLotteryid(Long lotteryid) {
		this.lotteryid = lotteryid;
	}
	
	public String getLotteryName() {
		return lotteryName;
	}

	public void setLotteryName(String lotteryName) {
		this.lotteryName = lotteryName;
	}

	public Long getStartIssueCode() {
		return startIssueCode;
	}

	public void setStartIssueCode(Long startIssueCode) {
		this.startIssueCode = startIssueCode;
	}

	public String getStartWebIssueCode() {
		return startWebIssueCode;
	}

	public void setStartWebIssueCode(String startWebIssueCode) {
		this.startWebIssueCode = startWebIssueCode;
	}

	public Integer getFinishIssue() {
		return finishIssue;
	}
	
	public void setFinishIssue(Integer finishIssue) {
		this.finishIssue = finishIssue;
	}

	public Long getUsedAmount() {
		return usedAmount;
	}

	public void setUsedAmount(Long usedAmount) {
		this.usedAmount = usedAmount;
	}

	public Long getCanceledAmount() {
		return canceledAmount;
	}

	public void setCanceledAmount(Long canceledAmount) {
		this.canceledAmount = canceledAmount;
	}

	public Long getTotamount() {
		return totamount;
	}

	public void setTotamount(Long totamount) {
		this.totamount = totamount;
	}

	public Long getTotalWin() {
		return totalWin;
	}

	public void setTotalWin(Long totalWin) {
		this.totalWin = totalWin;
	}

	public Integer getTotalIssue() {
		return totalIssue;
	}

	public Integer getCancelIssue() {
		return cancelIssue;
	}

	public void setCancelIssue(Integer cancelIssue) {
		this.cancelIssue = cancelIssue;
	}

	public void setTotalIssue(Integer totalIssue) {
		this.totalIssue = totalIssue;
	}

	public Integer getStopMode() {
		return stopMode;
	}

	public void setStopMode(Integer stopMode) {
		this.stopMode = stopMode;
	}

	public Long getStopParms() {
		return stopParms;
	}

	public void setStopParms(Long stopParms) {
		this.stopParms = stopParms;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Long getSaleTime() {
		return saleTime;
	}

	public void setSaleTime(Long saleTime) {
		this.saleTime = saleTime;
	}

	public Long getPlanid() {
		return planid;
	}

	public void setPlanid(Long planid) {
		this.planid = planid;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public Long getUserid() {
		return userid;
	}

	public void setUserid(Long userid) {
		this.userid = userid;
	}

	public Integer getChannelid() {
		return channelid;
	}

	public void setChannelid(Integer channelid) {
		this.channelid = channelid;
	}

	public String getChannelVersion() {
		return channelVersion;
	}

	public void setChannelVersion(String channelVersion) {
		this.channelVersion = channelVersion;
	}

}
