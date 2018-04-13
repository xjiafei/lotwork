package com.winterframework.firefrog.game.dao.vo;

import java.io.Serializable;
import java.util.Date;

public class SpiteOrders implements Serializable {

	private static final long serialVersionUID = -4393056380149352705L;

	private Long lotteryid;
	private String orderCode;
	private Long issueCode;
	private Long totamount;
	private Date saleTime;
	private Long userid;
	private String account;
	private Long status;
	private Integer	isFreeze; //1 未冻结 2 已冻结
	private Long orderId;
	public Long getLotteryid() {
		return lotteryid;
	}
	public void setLotteryid(Long lotteryid) {
		this.lotteryid = lotteryid;
	}
	public String getOrderCode() {
		return orderCode;
	}
	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}
	public Long getIssueCode() {
		return issueCode;
	}
	public void setIssueCode(Long issueCode) {
		this.issueCode = issueCode;
	}
	public Long getTotamount() {
		return totamount;
	}
	public void setTotamount(Long totamount) {
		this.totamount = totamount;
	}
	public Long getUserid() {
		return userid;
	}
	public void setUserid(Long userid) {
		this.userid = userid;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public Long getStatus() {
		return status;
	}
	public void setStatus(Long status) {
		this.status = status;
	}
	public Integer getIsFreeze() {
		return isFreeze;
	}
	public void setIsFreeze(Integer isFreeze) {
		this.isFreeze = isFreeze;
	}
	public Date getSaleTime() {
		return saleTime;
	}
	public void setSaleTime(Date saleTime) {
		this.saleTime = saleTime;
	}
	public Long getOrderId() {
		return orderId;
	}
	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}
	
}
