package com.winterframework.firefrog.fund.web.dto;

import com.winterframework.firefrog.fund.dao.vo.FundTransferView;

public class FundTransferCountResponse {

	private String account; 
	private String userChain;
	private Long vipLevel;
	private Long sendCount = 0L;//总计转出金额
	private Long rcvCount = 0L;//总计转入金额
	private Long sendTime = 0L;//总计转出次数
	private Long rcvTime = 0L; //总计转入次数

	private Long startDate;
	private Long endDate;
	
	
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getUserChain() {
		return userChain;
	}
	public void setUserChain(String userChain) {
		this.userChain = userChain;
	}
	public Long getVipLevel() {
		return vipLevel;
	}
	public void setVipLevel(Long vipLevel) {
		this.vipLevel = vipLevel;
	}
	public Long getSendCount() {
		return sendCount;
	}
	public void setSendCount(Long sendCount) {
		this.sendCount = sendCount;
	}
	public Long getRcvCount() {
		return rcvCount;
	}
	public void setRcvCount(Long rcvCount) {
		this.rcvCount = rcvCount;
	}
	public Long getSendTime() {
		return sendTime;
	}
	public void setSendTime(Long sendTime) {
		this.sendTime = sendTime;
	}
	public Long getRcvTime() {
		return rcvTime;
	}
	public void setRcvTime(Long rcvTime) {
		this.rcvTime = rcvTime;
	}
	public Long getStartDate() {
		return startDate;
	}
	public void setStartDate(Long startDate) {
		this.startDate = startDate;
	}
	public Long getEndDate() {
		return endDate;
	}
	public void setEndDate(Long endDate) {
		this.endDate = endDate;
	}
	
	public void add(FundTransferView view){
		this.account = view.getAccount();
		if(this.account.equals(view.getRcvAccount())){
			this.rcvCount += view.getTransferAmt();
			this.rcvTime ++;
		}else{
			this.sendCount += view.getTransferAmt();
			this.sendTime ++;
		}
		
		this.vipLevel = view.getVipLevel() >0 ?0L:1L;
		this.userChain = view.getUserChainTure();
	}
	

}
