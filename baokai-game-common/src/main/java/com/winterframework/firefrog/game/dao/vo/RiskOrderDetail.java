package com.winterframework.firefrog.game.dao.vo;

import java.io.Serializable;
import java.util.Date;

public class RiskOrderDetail implements Serializable {

	private static final long serialVersionUID = 6095864442835338992L;
	private Long id;
	private Long lotteryid;
	private Long issueCode;
	private Long userid;
	private String orderCode;
	private Long orderId;
	private Date saleTime;
	private Long totamount;
	private Long totwin;
	private Long winsRatio;
	private Long maxslipWins;
	private Long slipWinsratio;
	private Integer	parentType;
	private Integer channelId;
	private String channelVersion;
	private Integer status;
	private Long countWin;
	
	private String numberRecord;
	private String apprUser;
	private Date apprTime;
	private String apprMemo;
	
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
	public Long getUserid() {
		return userid;
	}
	public void setUserid(Long userid) {
		this.userid = userid;
	}
	public String getOrderCode() {
		return orderCode;
	}
	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}

	public Long getTotamount() {
		return totamount;
	}
	public void setTotamount(Long totamount) {
		this.totamount = totamount;
	}
	public Long getTotwin() {
		return totwin;
	}
	public void setTotwin(Long totwin) {
		this.totwin = totwin;
	}
	public Long getWinsRatio() {
		return winsRatio;
	}
	public void setWinsRatio(Long winsRatio) {
		this.winsRatio = winsRatio;
	}
	public Long getMaxslipWins() {
		return maxslipWins;
	}
	public void setMaxslipWins(Long maxslipWins) {
		this.maxslipWins = maxslipWins;
	}
	public Long getSlipWinsratio() {
		return slipWinsratio;
	}
	public void setSlipWinsratio(Long slipWinsratio) {
		this.slipWinsratio = slipWinsratio;
	}
	public Integer getParentType() {
		return parentType;
	}
	public void setParentType(Integer parentType) {
		this.parentType = parentType;
	}
	public Integer getChannelId() {
		return channelId;
	}
	public void setChannelId(Integer channelId) {
		this.channelId = channelId;
	}
	public String getChannelVersion() {
		return channelVersion;
	}
	public void setChannelVersion(String channelVersion) {
		this.channelVersion = channelVersion;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Date getSaleTime() {
		return saleTime;
	}
	public void setSaleTime(Date saleTime) {
		this.saleTime = saleTime;
	}
	public Long getCountWin() {
		return countWin;
	}
	public void setCountWin(Long countWin) {
		this.countWin = countWin;
	}
	public String getNumberRecord() {
		return numberRecord;
	}
	public void setNumberRecord(String numberRecord) {
		this.numberRecord = numberRecord;
	}
	public String getApprUser() {
		return apprUser;
	}
	public void setApprUser(String apprUser) {
		this.apprUser = apprUser;
	}
	public Date getApprTime() {
		return apprTime;
	}
	public void setApprTime(Date apprTime) {
		this.apprTime = apprTime;
	}
	public String getApprMemo() {
		return apprMemo;
	}
	public void setApprMemo(String apprMemo) {
		this.apprMemo = apprMemo;
	}
	public Long getOrderId() {
		return orderId;
	}
	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
}
