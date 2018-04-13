package com.winterframework.firefrog.game.web.dto;

import java.io.Serializable;

/** 
* @ClassName: PlansStrucForUI2
* @Description:  
* @author Alan 
* @date 2013-10-15 下午5:05:23 
*  
*/
public class PlansStrucForUI2 implements Serializable {

	private static final long serialVersionUID = -5506966805071902465L;

	private Long planid;
	private String planCode;
	private String lotteryName;
	private Long startIssueCode;
	private String startWebIssueCode;
	private String account;
	private String saleTime;
	private Integer totalIssue;
	private Integer finishIssue;
	private Integer cancelIssue;
	private Long totamount;
	private Long usedAmount;
	private Long canceledAmount;
	private Long totalWin;
	private String stopMode;
	private Long stopParams;
	private String status;
	private String channelVersion;
	private String channelid;
	private Long userid;
	private Long lotteryid;
    private double totamountD;
    private double usedAmountD;
    private double canceledAmountD;
    private double totalWinD;

    public Long getPlanid() {
		return planid;
	}
	public void setPlanid(Long planid) {
		this.planid = planid;
	}
	public String getPlanCode() {
		return planCode;
	}
	public void setPlanCode(String planCode) {
		this.planCode = planCode;
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
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getSaleTime() {
		return saleTime;
	}
	public void setSaleTime(String saleTime) {
		this.saleTime = saleTime;
	}
	public Integer getTotalIssue() {
		return totalIssue;
	}
	public void setTotalIssue(Integer totalIssue) {
		this.totalIssue = totalIssue;
	}
	public Integer getFinishIssue() {
		return finishIssue;
	}
	public void setFinishIssue(Integer finishIssue) {
		this.finishIssue = finishIssue;
	}
	public Integer getCancelIssue() {
		return cancelIssue;
	}
	public void setCancelIssue(Integer cancelIssue) {
		this.cancelIssue = cancelIssue;
	}
	public Long getTotamount() {
		return totamount;
	}
	public void setTotamount(Long totamount) {
		this.totamount = totamount;
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
	public Long getTotalWin() {
		return totalWin;
	}
	public void setTotalWin(Long totalWin) {
		this.totalWin = totalWin;
	}
	public String getStopMode() {
		return stopMode;
	}
	public void setStopMode(String stopMode) {
		this.stopMode = stopMode;
	}
	public Long getStopParams() {
		return stopParams;
	}
	public void setStopParams(Long stopParams) {
		this.stopParams = stopParams;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getChannelVersion() {
		return channelVersion;
	}
	public void setChannelVersion(String channelVersion) {
		this.channelVersion = channelVersion;
	}
	public String getChannelid() {
		return channelid;
	}
	public void setChannelid(String channelid) {
		this.channelid = channelid;
	}
	public Long getUserid() {
		return userid;
	}
	public void setUserid(Long userid) {
		this.userid = userid;
	}
	public Long getLotteryid() {
		return lotteryid;
	}
	public void setLotteryid(Long lotteryid) {
		this.lotteryid = lotteryid;
	}

    public void setTotamountD(double totamountD) {
        this.totamountD = totamountD;
    }

    public double getTotamountD() {
        return totamountD;
    }

    public void setUsedAmountD(double usedAmountD) {
        this.usedAmountD = usedAmountD;
    }

    public double getUsedAmountD() {
        return usedAmountD;
    }

    public void setCanceledAmountD(double canceledAmountD) {
        this.canceledAmountD = canceledAmountD;
    }

    public double getCanceledAmountD() {
        return canceledAmountD;
    }

    public void setTotalWinD(double totalWinD) {
        this.totalWinD = totalWinD;
    }

    public double getTotalWinD() {
        return totalWinD;
    }
}
