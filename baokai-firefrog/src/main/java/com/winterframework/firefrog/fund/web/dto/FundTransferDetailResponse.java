package com.winterframework.firefrog.fund.web.dto;

import com.winterframework.firefrog.fund.dao.vo.FundTransferView;

public class FundTransferDetailResponse {

	private String account; 
	private String userChain;
	private Long transferAmt;
	private Long transferTime;	
	private String sendAccount;
	private String rcvAccount;
	
	private Long startDate;
	private Long endDate;
	//0 转出 1 转入
	private Long direction;
	
	public FundTransferDetailResponse(){
		
	}
	
	public FundTransferDetailResponse(FundTransferView view){
		this.account = view.getAccount();
		this.transferAmt = view.getTransferAmt();		
		if(this.account.equals(view.getRcvAccount())){
			this.direction = 1L;	
		}else{
			this.direction = 0L;	
		}	
		this.sendAccount = view.getUserAccount();
		this.rcvAccount = view.getRcvAccount();
		this.userChain = view.getUserChainTure();
		if(view.getGmtCreated()!=null){
			this.transferTime = view.getGmtCreated().getTime();
		}
		
	}
	
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
	
	public Long getTransferAmt() {
		return transferAmt;
	}
	public void setTransferAmt(Long transferAmt) {
		this.transferAmt = transferAmt;
	}
	public Long getTransferTime() {
		return transferTime;
	}
	public void setTransferTime(Long transferTime) {
		this.transferTime = transferTime;
	}
	public String getSendAccount() {
		return sendAccount;
	}
	public void setSendAccount(String sendAccount) {
		this.sendAccount = sendAccount;
	}
	public String getRcvAccount() {
		return rcvAccount;
	}
	public void setRcvAccount(String rcvAccount) {
		this.rcvAccount = rcvAccount;
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
	public Long getDirection() {
		return direction;
	}
	public void setDirection(Long direction) {
		this.direction = direction;
	}
	
	

}
